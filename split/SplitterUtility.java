package split;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.io.*;
import java.util.*;

public class SplitterUtility
{
	private final Split split;
	SplitterUtility()
	{
		this.split = new SplitImpl();
	}

	public String splitFile(String filePath, String outputPath, int numberOfParts, int partSize, boolean splitToEqual)
	{
		boolean splitSuccesful;
		try {
			if(splitToEqual){
				splitSuccesful = split.splitFileInEqualParts(new File(filePath), outputPath, numberOfParts);
			}else {
				splitSuccesful = split.splitFile(new File(filePath), outputPath, partSize);
			}
		} catch (InvalidArgumentException e) {
			return e.getRealMessage();
		}
		if(splitSuccesful){
			return "Done.";
		}else{
			return "Something terrible happened. Cannot split the files";
		}
	}
}