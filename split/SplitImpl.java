package split;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SplitImpl implements Split {

    @Override
    public boolean splitFile(File file, String outputPath, long partSize) throws InvalidArgumentException {
        long fileSize = file.length();
        if (partSize > fileSize) {
            String[] msg = {"Part size cannot be greater than file size. Part Size: " + partSize + ", File Size: " + fileSize};
            throw new InvalidArgumentException(msg);
        }

        String fileName = file.getName();
        long numberOfParts = fileSize / partSize;
        long lastPartSize = fileSize - numberOfParts * partSize;
        String partInitName = fileName.substring(0, fileName.lastIndexOf("."));
        String partExt = fileName.substring(fileName.lastIndexOf(".") + 1);

        return isSplitSuccessful(file, outputPath, partSize, numberOfParts + 1,
                lastPartSize, partInitName, partExt);
    }

    @Override
    public boolean splitFileInEqualParts(File file, String outputPath, int numberOfParts) throws InvalidArgumentException {
        if (numberOfParts > 100) {
            String[] msg = {"Only 100 parts can be created."};
            throw new InvalidArgumentException(msg);
        }

        long fileSize = file.length();
        long partSize = fileSize / numberOfParts;
        String fileName = file.getName();

        long lastPartSize = (fileSize - numberOfParts * partSize) + partSize;
        String partInitName = fileName.substring(0, fileName.lastIndexOf("."));
        String partExt = fileName.substring(fileName.lastIndexOf(".") + 1);

        return isSplitSuccessful(file, outputPath, partSize, numberOfParts, lastPartSize,
                partInitName, partExt);
    }

    private boolean isSplitSuccessful(File file,
                                      String outputPath,
                                      long partSize,
                                      long numberOfParts,
                                      long lastPartSize,
                                      String partInitName,
                                      String partExt) {
        boolean splitSuccessful = true;
        List<String> partsCreated = new ArrayList<>();
        try (FileInputStream inputStream = new FileInputStream(file)) {
            long toWrite = partSize;
            for (int partNumber = 0; partNumber < numberOfParts; partNumber++) {
                String partName = partInitName + "." + numberOfParts + "." + (partNumber + 1) + "." + partExt;
                try (FileOutputStream outputStream = new FileOutputStream(outputPath + "\\"+partName)) {
                    if (partNumber == numberOfParts - 1) {
                        toWrite = lastPartSize;
                    }
                    byte[] bytesToWrite = new byte[(int) toWrite];
                    inputStream.read(bytesToWrite);
                    outputStream.write(bytesToWrite, 0, (int) toWrite);
                } catch (IOException e) {
                    splitSuccessful = false;
                    break;
                }
                partsCreated.add(partName);
            }
        } catch (IOException e) {
            splitSuccessful = false;
        }finally {
            if(!splitSuccessful){
                for(String partName : partsCreated){
                    File part = new File(outputPath + "\\"+partName);
                    part.delete();
                }
            }
        }
        return splitSuccessful;
    }
}
