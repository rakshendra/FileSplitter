package join;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class JoinImpl implements Join {

    @Override
    public boolean joinParts(File firstPart, String fileName, String fileExt, int numberOfParts, String outputPath) {
        boolean joinSuccessful = true;
        File outputFile = new File(outputPath, (fileName + fileExt));
        try (FileOutputStream outputStream = new FileOutputStream(outputFile, true)) {
            for (int i = 0; i < numberOfParts; i++) {
                String nextPart = fileName + "." + numberOfParts + "." + (i + 1) + fileExt;
                File nextPartFile = new File(firstPart.getParent(), nextPart);
                if (nextPartFile.exists()) {
                    try (FileInputStream inputStream = new FileInputStream(nextPartFile)) {
                        long partsize = nextPartFile.length();
                        byte b[] = new byte[(int) partsize];
                        inputStream.read(b);
                        outputStream.write(b);
                    } catch (IOException e) {
                        joinSuccessful = false;
                    }
                } else {
                    joinSuccessful = false;
                    break;
                }
            }
        } catch (IOException e) {
            joinSuccessful = false;
        }

        if (!joinSuccessful) {
            outputFile.delete();
        }
        return joinSuccessful;
    }
}
