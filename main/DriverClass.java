package main;

import split.SplitterJoinerUtility;

import java.util.*;

class DriverClass {
    public static void main(String args[]) {
        boolean flag = true;
        SplitterJoinerUtility splitobj = new SplitterJoinerUtility();
        Scanner in = new Scanner(System.in);
        do {
            System.out.println("Enter your choice :");
            System.out.println("1.Split");
            System.out.println("2.Join");
            System.out.println("3.Exit");
            int ch = in.nextInt();
            in.nextLine();
            switch (ch) {
                case 1:
                    System.out.println("Enter File Path : ");
                    String fpath = in.nextLine();
                    System.out.println("Options:\n1.Divide in Equal parts\n2.Divide with specified File Size-->");
                    int chin = in.nextInt();
                    switch (chin) {
                        case 1:
                            System.out.print("Enter number of parts : ");
                            int noOfParts = in.nextInt();
                            splitobj.splitFile(fpath, noOfParts, 0, true);
                            break;
                        case 2:
                            in.nextLine();
                            long manualSize = in.nextLong();
                            splitobj.splitFile(fpath, 0, manualSize, false);
                            break;
                        default:
                            System.out.println("Wrong Choice!!!!!");
                    }
                    break;
                case 2:
                    System.out.print("Enter path of first part : ");
                    fpath = in.nextLine();
                    System.out.print("Delete parts : ");
                    boolean del = in.nextBoolean();
                    splitobj.joinFiles(fpath, del);
                    break;
                case 3:
                    flag = false;
                    break;
                default:
                    System.out.println("Wrong Choice");
                    break;
            }
        } while (flag);
    }
}