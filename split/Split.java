package split;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.io.File;

public interface Split {
    boolean splitFile(File file, String outputPath, long partSize) throws InvalidArgumentException;
    boolean splitFileInEqualParts(File file, String outputPath, int numberOfParts) throws InvalidArgumentException;
}
