package join;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.io.File;

public interface Join {
    boolean joinParts(File firstPart, String fileName, String fileExt, int numberOfParts, String outputPath);
}
