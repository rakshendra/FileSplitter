import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;

class TabbedPaneExample		extends 	JFrame
{
	private		JTabbedPane tabbedPane;
	private		JPanel		panel1;
	private		JPanel		panel2;
	private		JPanel		panel3;
	JFileChooser chooser=new JFileChooser();

	public TabbedPaneExample()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle( "RHS Split-Join Application" );
		setSize( 600, 600 );
		setBackground( Color.gray );

		JPanel topPanel = new JPanel();
		topPanel.setLayout( new BorderLayout(0,2) );
		getContentPane().add( topPanel );

		// Create the tab pages
		createPage1();
		createPage2();
		createPage3();

		// Create a tabbed pane
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab( "Split", panel1 );
		tabbedPane.addTab( "Join", panel2 );
		tabbedPane.addTab( "Help", panel3 );
		topPanel.add( tabbedPane, BorderLayout.CENTER );
	}

	public void createPage1()
	{
		panel1 = new JPanel();
		panel1.setLayout( null );

		JButton chooseFile=new JButton("Choose File to Split");
		JButton chooseFolder=new JButton("Browse Output Folder");
		JButton splitButton=new JButton("SPLIT");
		
		JLabel lb1=new JLabel("equal-size parts.");
		JLabel lb2=new JLabel("Source File :");
		JLabel lb3=new JLabel("Output Directory :");
		
		final JRadioButton equalParts=new JRadioButton("Split into ");
		final JRadioButton specifiedSize=new JRadioButton("Split after every ");
		
		final JTextField fileAdd=new JTextField(35);
		final JTextField folderAdd=new JTextField(35);
		final JTextField noOfParts=new JTextField(4);
		final JTextField sizeOfPart=new JTextField(10);
		
		String memtype[]={"B","KB","MB"};
		final JComboBox memType=new JComboBox(memtype);
		final JCheckBox delFile=new JCheckBox("Delete Original File After Splitting");
    
		
        noOfParts.setEnabled(false);
		sizeOfPart.setEnabled(false);
		memType.setEnabled(false);
		equalParts.setActionCommand("EQUAL");
		specifiedSize.setActionCommand("SPECIFIED");
		lb2.setBounds(10,20,200,25);
		panel1.add(lb2);
		fileAdd.setBounds(10,50,400,25);
        panel1.add(fileAdd);
		chooseFile.setBounds(415,50,160,25);
        panel1.add(chooseFile);
		//add(dummy1);
		lb3.setBounds(10,100,200,25);
		panel1.add(lb3);
		folderAdd.setBounds(10,130,400,25);
        panel1.add(folderAdd);
		chooseFolder.setBounds(415,130,160,25);
		panel1.add(chooseFolder);
		equalParts.setBounds(10,210,100,25);
        panel1.add(equalParts);
		noOfParts.setBounds(130,210,50,25);
		panel1.add(noOfParts);
		lb1.setBounds(190,210,100,25);
		panel1.add(lb1);
		specifiedSize.setBounds(10,260,150,25);
		panel1.add(specifiedSize);
		sizeOfPart.setBounds(170,260,50,25);
		panel1.add(sizeOfPart);
		memType.setBounds(250,260,50,25);
		panel1.add(memType);
		delFile.setBounds(10,310,250,25);
		panel1.add(delFile);
		splitButton.setBounds(420,400,100,100);
		panel1.add(splitButton);
        ButtonGroup bg=new ButtonGroup();
		bg.add(equalParts);
		bg.add(specifiedSize);
		chooseFile.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
				final JFileChooser fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(TabbedPaneExample.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) 
				{
					File file = fc.getSelectedFile();
					//System.out.println(file.getName());
					fileAdd.setText("");
					fileAdd.setText(fileAdd.getText()+file.getAbsolutePath());
				}
				else
				{
				}
            }
        });
        equalParts.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
				noOfParts.setEnabled(true);
				sizeOfPart.setEnabled(false);
				memType.setEnabled(false);
				
            }
        });
		specifiedSize.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
				sizeOfPart.setEnabled(true);
				memType.setEnabled(true);
				noOfParts.setEnabled(false);
				
            }
        });
        chooseFolder.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                int result;
				chooser = new JFileChooser(); 
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Select Target Folder");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);
				if (chooser.showOpenDialog(TabbedPaneExample.this) == JFileChooser.APPROVE_OPTION) 
				{
					folderAdd.setText("");
					folderAdd.setText(folderAdd.getText()+chooser.getSelectedFile());
					//System.out.println("getCurrentDirectory(): " +  chooser.getCurrentDirectory());
					//System.out.println("getSelectedFile() : " +  chooser.getSelectedFile());
				}
				else
				{
					System.out.println("No Selection ");
				}  
            };
        });
        
        splitButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                String filePath=fileAdd.getText();
                String fileName=filePath.substring(filePath.lastIndexOf('\\')+1);
                filePath=filePath.substring(0,filePath.lastIndexOf('\\'));
				//System.out.println(filePath);
                //System.out.println(fileName);
				String outPath=folderAdd.getText();
				outPath+="\\";
                SplitterClass splitobj;
				String ans;
				if(equalParts.isSelected())
				{
					int no_of_parts=Integer.parseInt(noOfParts.getText());
                	splitobj=new SplitterClass(filePath,fileName,no_of_parts,delFile.isSelected(),outPath);
					ans = splitobj.splitFile();
					JOptionPane.showMessageDialog(panel1, ans,"RESULT", JOptionPane.INFORMATION_MESSAGE);
				}
				else if(specifiedSize.isSelected())
				{
					long partsize=Long.parseLong(sizeOfPart.getText());
					String dtype=(String)memType.getSelectedItem();
					splitobj=new SplitterClass(filePath,fileName,partsize,dtype,delFile.isSelected(),outPath);
					ans = splitobj.splitFile();
					JOptionPane.showMessageDialog(panel1, ans,"RESULT", JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					JOptionPane.showMessageDialog(panel1, "Select all the Options!!!","RESULT", JOptionPane.INFORMATION_MESSAGE);
				}
				
            }
        });	
		
	}

	public void createPage2()
	{
		panel2 = new JPanel();
		panel2.setLayout( null);

		JButton chooseFile=new JButton("Choose File to Join");
		JButton chooseFolder=new JButton("Browse Output Folder");
		JButton joinButton=new JButton("JOIN");
		
		JLabel lb2=new JLabel("First Part of the File :");
		JLabel lb3=new JLabel("Output Directory :");
		
		final JTextField fileAdd=new JTextField(35);
		final JTextField folderAdd=new JTextField(35);
		final JCheckBox delFile=new JCheckBox("Delete Parts After Joining");
    
		lb2.setBounds(10,20,200,25);
		panel2.add(lb2);
		fileAdd.setBounds(10,50,400,25);
        panel2.add(fileAdd);
		chooseFile.setBounds(415,50,160,25);
        panel2.add(chooseFile);
		lb3.setBounds(10,100,200,25);
		panel2.add(lb3);
		folderAdd.setBounds(10,130,400,25);
        panel2.add(folderAdd);
		chooseFolder.setBounds(415,130,160,25);
		panel2.add(chooseFolder);
		delFile.setBounds(10,250,250,25);
		panel2.add(delFile);
		joinButton.setBounds(420,400,100,100);
		panel2.add(joinButton);
        
		chooseFile.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
				final JFileChooser fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(TabbedPaneExample.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) 
				{
					File file = fc.getSelectedFile();
					//System.out.println(file.getName());
					fileAdd.setText("");
					fileAdd.setText(fileAdd.getText()+file.getAbsolutePath());
				}
				else
				{
				}
            }
        });
        
        chooseFolder.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                int result;
				chooser = new JFileChooser(); 
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Select Target Folder");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);
				if (chooser.showOpenDialog(TabbedPaneExample.this) == JFileChooser.APPROVE_OPTION) 
				{
					folderAdd.setText("");
					folderAdd.setText(folderAdd.getText()+chooser.getSelectedFile());
					//System.out.println("getCurrentDirectory(): " +  chooser.getCurrentDirectory());
					//System.out.println("getSelectedFile() : " +  chooser.getSelectedFile());
				}
				else
				{
					JOptionPane.showMessageDialog(panel1, "No Selection!!","RESULT", JOptionPane.INFORMATION_MESSAGE);
				}  
            };
        });
        
        joinButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                String filePath=fileAdd.getText();
                String fileName=filePath.substring(filePath.lastIndexOf('\\')+1);
                filePath=filePath.substring(0,filePath.lastIndexOf('\\'));
				//System.out.println(filePath);
                //System.out.println(fileName);
				String outPath=folderAdd.getText();
				outPath+="\\";
                JoinerClass joinobj=new JoinerClass(filePath,fileName,outPath,delFile.isSelected());
				String ans =joinobj.joinFile();
				JOptionPane.showMessageDialog(panel1, ans,"RESULT", JOptionPane.INFORMATION_MESSAGE);
				
            }
        });
	}

	public void createPage3()
	{
		panel3 = new JPanel();
		panel3.setLayout( null);
		JLabel lb=new JLabel("Sorry, We can't help you :((");
		lb.setBounds(200,230,200,25);
		panel3.add(lb);
	}

    // Main method to get things started
	public static void main( String args[] )
	{
		// Create an instance of the test application
		TabbedPaneExample mainFrame	= new TabbedPaneExample();
		//mainframe.setDefaultCloseOperation(EXIT_ON_CLOSE);
		mainFrame.setVisible( true );
	}
}