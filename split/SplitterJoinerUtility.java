package split;

import com.sun.javaws.exceptions.InvalidArgumentException;
import join.Join;
import join.JoinImpl;

import java.io.*;
import java.util.*;

public class SplitterJoinerUtility {
    private final Split split;
    private final Join join;

    public SplitterJoinerUtility() {
        this.split = new SplitImpl();
        this.join = new JoinImpl();
    }

    public String splitFile(String filePath, int numberOfParts, long partSize, boolean splitToEqual) {
        boolean splitSuccessful;
        try {
            File file = new File(filePath);
            String outputPath = file.getParent();
            if (splitToEqual) {
                splitSuccessful = split.splitFileInEqualParts(file, outputPath, numberOfParts);
            } else {
                splitSuccessful = split.splitFile(file, outputPath, partSize);
            }
        } catch (InvalidArgumentException e) {
            return e.getRealMessage();
        }
        if (splitSuccessful) {
            return "Done.";
        } else {
            return "Something terrible happened. Cannot split the files";
        }
    }

    public String joinFiles(String firstFilePath, boolean deleteParts) {
        boolean joinSuccessful;
        File firstPart = new File(firstFilePath);
        String firstPartName = firstPart.getName();
        String fileExt = firstPartName.substring(firstPartName.lastIndexOf('.'));

        String tempString = firstPartName.substring(0, firstPartName.lastIndexOf('.'));
        tempString = tempString.substring(0, tempString.lastIndexOf('.'));
        String fileName = tempString.substring(0, tempString.lastIndexOf('.'));

        tempString = tempString.substring(tempString.lastIndexOf('.') + 1);
        int numberOfParts = Integer.parseInt(tempString);

        joinSuccessful = join.joinParts(firstPart, fileName, fileExt, numberOfParts, firstPart.getParent());

        if (joinSuccessful) {
            if (deleteParts) {
                for (int i = 0; i < numberOfParts; i++) {
                    tempString = fileName + "." + numberOfParts + "." + (i + 1) + fileExt;
                    File fp = new File(firstPart.getParent(), tempString);
                    fp.delete();
                }
            }
            return "Done.";
        } else {
            return "Something terrible happened. Cannot split the files";
        }
    }
}