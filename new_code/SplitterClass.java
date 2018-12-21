import java.io.*;
import java.util.*;

class SplitterClass
{
	String filePath;
	String fileName;
	String outPath;
	File f;
	boolean deleteFile;
	int no_of_parts;
	long size_of_part;
	long size_of_last;
	long fileSize;
	SplitterClass(String filePath,String fileName,int no_of_parts,boolean deleteFile,String outPath)
	{
		this.filePath=filePath;
		this.fileName=fileName;
		this.outPath=outPath;
		this.deleteFile=deleteFile;
		this.no_of_parts=no_of_parts;
		this.f=new File(filePath,fileName);
		this.fileSize=f.length();
		this.size_of_part=this.fileSize/this.no_of_parts;
		this.size_of_last=(this.fileSize-this.size_of_part*(no_of_parts-1));
	}
	SplitterClass(String filePath,String fileName,long size_of_part,String dtype,boolean deleteFile,String outPath)
	{
		this.filePath=filePath;
		this.outPath=outPath;
		this.deleteFile=deleteFile;
		this.fileName=fileName;
		if(dtype.equals("b")||dtype.equals("B"))
			this.size_of_part=size_of_part;
		else if(dtype.equals("kb")||dtype.equals("KB")||dtype.equals("Kb"))
			this.size_of_part=size_of_part*1024;
		else if(dtype.equals("mb")||dtype.equals("MB")||dtype.equals("Mb"))
			this.size_of_part=size_of_part*1024*1024;
		this.f=new File(filePath,fileName);
		this.fileSize=f.length();
		this.size_of_last=fileSize%this.size_of_part;
		this.no_of_parts=(int)(fileSize/this.size_of_part);
		if(this.size_of_last>0)
			this.no_of_parts+=1;
		
		
	}
	
	public String splitFile()
	{
		String strToReturn="";
		int flag=1;
		if(size_of_part>524288000||size_of_part<=0)
		{
			strToReturn = "Size of each part should not be greater than 500MB. Create more parts to reduce the size.";
			return strToReturn;
		}
		else if(size_of_part>=fileSize)
		{
			strToReturn = "Size of part can not be greater than file size!!!!";
			return strToReturn;
		}
		
		FileInputStream fi=null;
		FileOutputStream fo=null;
		String partInitName;
		String partExt;
		partInitName=fileName.substring(0,fileName.lastIndexOf("."));
		partExt=fileName.substring(fileName.lastIndexOf(".")+1);
		try
		{
			
			//System.out.println("*****************Creating Object........");
			fi=new FileInputStream(f);
			long offset=0,size_to_write;
			//System.out.println("*****************Creating Parts.........");
			for(int i=0;i<no_of_parts;i++)
			{
				//System.out.println("*****************Creating Part : "+(i+1)+".........");
				String partName=partInitName+"."+no_of_parts+"."+(i+1)+"."+partExt;
				try
				{
					fo=new FileOutputStream(outPath+partName);
					if(i<no_of_parts-1)
						size_to_write=size_of_part;
					else
						size_to_write=size_of_last;
					byte b[]=new byte[(int)size_to_write];
					fi.read(b);
					fo.write(b,0,(int)size_to_write);
				}
				catch(IOException e)
				{
					strToReturn = "Some Error Ocurred!!!!";
					flag=0;
				}
				finally
				{
					try
					{
						if(fo!=null)
							fo.close();
					}
					catch(IOException e)
					{
						strToReturn = "Error Closing file!!!";
						flag=0;
					}
				}
				
			}
			//System.out.println("*****************Process Finished*****************");
			strToReturn = "File Splitted Successfully in "+no_of_parts+" Parts.";
		}
		catch(IOException e)
		{
			strToReturn="Some Error Ocurred!!!";
			flag=0;
		}
		finally
		{
				try
				{
					if(fi!=null)
						fi.close();
				}
				catch(IOException e)
				{
					strToReturn="Error Closing file!!!";
					flag=0;
				}
		}	
		if(deleteFile&&flag==1)
		{
			f.delete();
			strToReturn+="\nOriginal File Deleted!!";
		}
		return strToReturn;
	}
}