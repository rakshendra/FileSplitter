import java.io.*;
import java.util.*;
class JoinerClass
{
	String filePath;
	String firstPartName;
	String fileName;
	String outPath;
	String fileExt;
	boolean delete_parts;
	File f;
	int no_of_parts;
	
	JoinerClass(String filePath,String firstPartName,String outPath,boolean delete_parts)
	{
		this.filePath=filePath;
		this.outPath=outPath;
		this.firstPartName=firstPartName;
		this.fileExt=this.firstPartName.substring(this.firstPartName.lastIndexOf('.'));
		String str=firstPartName.substring(0,firstPartName.lastIndexOf('.'));
		str=str.substring(0,str.lastIndexOf('.'));
		this.fileName=str.substring(0,str.lastIndexOf('.'));
		str=str.substring(str.lastIndexOf('.')+1);
		this.no_of_parts=Integer.parseInt(str);
		this.delete_parts=delete_parts;
	}
	public String joinFile()
	{
		long partsize=0;
		int n,flag=1;
		String str;
		String strToReturn="";
		f=new File(outPath,(fileName+fileExt));
		FileInputStream fi=null;
		FileOutputStream fo=null;
		try{
			fo=new FileOutputStream(f,true);
			for(int i=0;i<no_of_parts;i++)
			{
				str=fileName+"."+no_of_parts+"."+(i+1)+fileExt;
				//System.out.println(str);
				File fp=new File(filePath,str);
				if(fp.exists())
				{
					try
					{
						fi=new FileInputStream(fp);
						partsize=fp.length();
						byte b[]=new byte[(int)partsize];
						fi.read(b);
						fo.write(b);
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
					
				}
				else
				{
					strToReturn="**PART-"+(i+1)+"** is absent. Unable to construct the file";
					flag=0;
					break;
				}
			}
			if(flag==1)
				strToReturn=no_of_parts+" Parts Joined Successfully.";
			
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
				if(fo!=null)
					fo.close();
			}
			catch(IOException e)
			{
				strToReturn="Error Closing file!!!";
				flag=0;
			}
		}
		if(flag==0)
			f.delete();
		else
		{
			if(delete_parts)
			{
				for(int i=0;i<no_of_parts;i++)
				{
					str=fileName+"."+no_of_parts+"."+(i+1)+fileExt;
					//System.out.println(str+" DELETED!!!!!");
					File fp=new File(filePath,str);
					fp.delete();
				}
				strToReturn+="\n Parts Deleted.";
			}			
		}
		return strToReturn;
	}
}