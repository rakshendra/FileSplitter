package main;

import join.JoinerClass;
import split.SplitterUtility;

import java.util.*;

class DriverClass
{
	public static void main(String args[])
	{
		int no_of_parts,size_of_part,size_of_last;
		long manual_size;
		boolean flag=true;
		String fname,fpath,dtype;
		long heapSize = Runtime.getRuntime().totalMemory();
         
        //Print the jvm heap size.
        System.out.println("Heap Size = " + heapSize);
		Scanner in=new Scanner(System.in);
		do
		{
			System.out.println("Enter your choice :");
			System.out.println("1.Split");
			System.out.println("2.Join");
			System.out.println("3.Exit");
			int ch=in.nextInt();
			in.nextLine();
			switch(ch)
			{
				case 1:	System.out.print("Enter File Path : ");
						fpath=in.nextLine();
						System.out.print("Enter File Name : ");
						fname=in.nextLine();
						SplitterUtility splitobj;
						System.out.println("Options:\n1.Divide in Equal parts\n2.Divide with specified File Size-->");
						int chin=in.nextInt();
						//in.nextLine();
						switch(chin)
						{
							case 1:	System.out.print("Enter number of parts : ");
									no_of_parts=in.nextInt();
									splitobj=new SplitterUtility(fpath,fname,no_of_parts);
									splitobj.splitFile();
									break;
							case 2: in.nextLine();
									System.out.print("Enter Unit(b,kb,mb) : ");
									dtype=in.nextLine();
									System.out.print("Enter size of each part(<=500MB) : ");
									manual_size=in.nextLong();
									
									splitobj=new SplitterUtility(fpath,fname,manual_size,dtype);
									splitobj.splitFile();
									break;
							default:System.out.println("Wrong Choice!!!!!");
									flag=false;
						}
						
						break;
				case 2:	System.out.print("Enter path : ");
						fpath=in.nextLine();
						System.out.print("Enter Name of first part : ");
						fname=in.nextLine();
						System.out.print("Delete parts : ");
						boolean del=in.nextBoolean();
						
						JoinerClass joinobj=new JoinerClass(fpath,fname,del);
						joinobj.joinFile();
						break;
				case 3: flag=false;
						break;
				default:System.out.println("Wrong Choice");
						break;
			}
		}while(flag==true);
	}
}