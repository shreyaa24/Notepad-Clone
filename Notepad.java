package notepad_clone;

import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Notepad 
{
	JFrame frame;
	JTextArea textArea;
	JMenuBar menuBar;
	JMenu fileMenu, langMenu, formatMenu, commandPromptMenu;
	JMenuItem itemNew, itemNewWindow, itemOpen, itemSaveAs, itemSave, itemExit, itemWordWrap, itemFont, itemFontSize , itemCMD, itemJava, itemC, itemCpp, itemHTML;
	String openPath = null;
	String openFileName = null;
	boolean wordWrap = false;
	Font arial, newRoman, consolas;
	String fontStyle = "Arial";
	//String [] keywords = {""};
	
	public static void main(String[] args) 
	{
		Notepad n1 = new Notepad(); 						
	}
	public Notepad()														//constructor
	{
		createFrame();
		createTextArea();
		createScrollBar();
		createMenuBar();
		createFileMenuItems();
		createLangMenuItems();
		createFormatMenuItems();
		createCommandPromptItem();
		
	}
	
	public void createFrame()
	{
		frame = new JFrame("Notepad");
		
		frame.setSize(700,600);
		
		Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\SHREYA RALE\\OneDrive\\Desktop\\NotepadLogo.jpg");
		
		frame.setIconImage(icon);
		
		frame.setVisible(true);
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void createTextArea()
	{
		textArea = new JTextArea();
		textArea.setFont(new Font("Arial", Font.PLAIN, 30));
		frame.add(textArea);
	}
							
	public void createScrollBar()
	{
		JScrollPane scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		frame.add(scroll);	
	}
	
	// Creating Menu Bar and adding contents to it
	public void createMenuBar()
	{
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		langMenu = new JMenu("Language");
		menuBar.add(langMenu);
		
		formatMenu = new JMenu("Format");
		menuBar.add(formatMenu);
		
		commandPromptMenu = new JMenu("Command Prompt");
		menuBar.add(commandPromptMenu);
	}

	//File Menu Items
	public void createFileMenuItems()
	{
		
		itemNew = new JMenuItem("New");
		fileMenu.add(itemNew);
		
		itemNew.addActionListener(new ActionListener() 
		{	
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				textArea.setText("");
				frame.setTitle("Untitled Notepad");
				
				openFileName = null;
				openPath = null;
			}
		});
		
		itemNewWindow = new JMenuItem("New Window");
		fileMenu.add(itemNewWindow);
		itemNewWindow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				Notepad n1 = new Notepad();	
				n1.frame.setTitle("Untitled");				// Whenever opening new window then set name as default "Untitled"
			}
		});
		
		itemOpen = new JMenuItem("Open");
		fileMenu.add(itemOpen);
		itemOpen.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				FileDialog fd = new FileDialog(frame, "Open", FileDialog.LOAD);       // For openening a file 
				fd.setVisible(true);
				
				String fileName = fd.getFile();	 			// Reading FileName
				String path = fd.getDirectory();					// Getting the Path of file
				
				if(fileName != null)
				{
					frame.setTitle(path + fileName);
					
					openFileName = fileName; 
					openPath = path;
					
				}
			
				System.out.println(path+fileName);
				
				
				BufferedReader br=null;
				try 
				{
					br = new BufferedReader(new FileReader(path+fileName));
					
					String sentence = br.readLine();					// Reading line 
					textArea.setText("");
					
					while(sentence != null)
					{
						textArea.append(sentence+"\n");				
						sentence = br.readLine(); 					// Update sentence
					}
				} 
				catch (FileNotFoundException e1) 
				{
					System.out.println("File Not Found");
				} 
				catch (IOException e1) 							// Handling the exception for readLine()
				{
					System.out.println("Data cannot be read.");
				}
				catch(NullPointerException ne)					// If we opened null file .
				{
					
				}
				finally
				{
					try 
					{
						br.close();
					} 
					catch (IOException e1) 
					{
						System.out.println("Cannot close the file");
					}
					catch(NullPointerException ne)					// If we opened null file .
					{
						
					}
				}
			}
		});
		
		itemSave = new JMenuItem("Save");
		fileMenu.add(itemSave);
		
		itemSave.addActionListener(new ActionListener() 
		{
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if(openFileName != null && openPath != null)
				{
					writeDataToFile(openFileName, openPath);
				}
				else
				{
					FileDialog fd = new FileDialog(frame, "Save As", FileDialog.SAVE);
					fd.setVisible(true);
					
					String path = fd.getDirectory();
					String fileName = fd.getFile();
					
					if(fileName != null && path !=null)
					{
						writeDataToFile(fileName, path);
						
						openFileName = fileName;
						openPath = path;
						
						frame.setTitle(openFileName);
					}
					
				}
			}
		});
		
		itemSaveAs = new JMenuItem("Save As");
		fileMenu.add(itemSaveAs);
		
		itemSaveAs.addActionListener(new ActionListener() 
		{
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				FileDialog fd = new FileDialog(frame, "Save As", FileDialog.SAVE);
				fd.setVisible(true);
				
				String path = fd.getDirectory();
				String fileName = fd.getFile();
				
				if(fileName != null && path !=null)
				{
					writeDataToFile(fileName, path);
					openFileName = fileName;
					openPath = path;
					
					frame.setTitle(openFileName);
				}
				
			}
		});
		
	
		itemExit = new JMenuItem("Exit");
		fileMenu.add(itemExit);
		itemExit.addActionListener(new ActionListener() 
		{	
			public void actionPerformed(ActionEvent e) 
			{
				frame.dispose();
			}
		});
	}
	
	public void createLangMenuItems()
	{
		itemJava = new JMenuItem("Java");
		langMenu.add(itemJava);
		
		itemJava.addActionListener(new ActionListener() 
		{
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				setLanguage("Java");
				openPath = null;
				openFileName = null;
			}
		});
		
		itemC = new JMenuItem("C");
		langMenu.add(itemC);
		
		itemC.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				setLanguage("C");
				openPath = null;
				openFileName = null;
			}
		});
		
		itemCpp = new JMenuItem("C++");
		langMenu.add(itemCpp);
		
		itemCpp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				setLanguage("C++");
				openPath = null;
				openFileName = null;
			}
		});
		
		itemHTML = new JMenuItem("HTML");
		langMenu.add(itemHTML);
		
		itemHTML.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				setLanguage("HTML");
				openPath = null;
				openFileName = null;
			}
		});
	}
	
	public void setLanguage(String lang)
	{
		BufferedReader br=null;
		try 
		{
			br = new BufferedReader(new FileReader("D:\\filehandle\\" + lang + "Format.txt"));
			String sentence = br.readLine();					// Reading line 
			textArea.setText("");
			
			while(sentence != null)
			{
				textArea.append(sentence+"\n");				
				sentence = br.readLine(); 					// Update sentence
			}
		} 
		catch (FileNotFoundException e1) 
		{
			System.out.println("File Not Found");
		} 
		catch (IOException e1) 							// Handling the exception for readLine()
		{
			System.out.println("Data cannot be read.");
		}
		catch(NullPointerException ne)					// If we opened null file .
		{
			
		}
		finally
		{
			try 
			{
				br.close();
			} 
			catch (IOException e1) 
			{
				System.out.println("Cannot close the file");
			}
			catch(NullPointerException ne)					// If we opened null file .
			{
				
			}
		}
	}
	
	// Format Menu Items
	public void createFormatMenuItems()
	{
		itemWordWrap = new JMenuItem("WordWrap: Off");
		formatMenu.add(itemWordWrap);
		
		itemWordWrap.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				 textArea.setLineWrap(true);
		         textArea.setWrapStyleWord(true);

				if(wordWrap == false)
				{
					textArea.setLineWrap(true);				
					textArea.setWrapStyleWord(true);
					
					wordWrap = true; 
					itemWordWrap.setText("WordWrap: On");
				}
				else
				{
					textArea.setLineWrap(false);
					textArea.setWrapStyleWord(false);
					
					wordWrap = false;
					itemWordWrap.setText("WordWrap: Off");
				}
				
			}
		});
		
		itemFont = new JMenu("Font");
		
		JMenuItem itemArial = new JMenuItem("Arial");
		itemFont.add(itemArial);
		
		itemArial.addActionListener(new ActionListener() 
		{
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				setFontType("Arial");
				
			}
		});
		
		JMenuItem itemTimesNewRoman = new JMenuItem("Times New Roman");
		itemFont.add(itemTimesNewRoman);
		
		itemTimesNewRoman.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				setFontType("Times New Roman");
				
			}
		});
		
		JMenuItem itemConsolas = new JMenuItem("Consolas");
		itemFont.add(itemConsolas);
	
		itemConsolas.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				setFontType("Consolas");
				
			}
		});
		
		formatMenu.add(itemFont);
		
		itemFontSize = new JMenu("Font Size");
		formatMenu.add(itemFontSize);
		
		JMenuItem item8 = new JMenuItem("8");
		itemFontSize.add(item8);
		
		item8.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				setFontSize(8);
			}
		});
		
		JMenuItem item10 = new JMenuItem("10");
		
		item10.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				setFontSize(10);
			}
		});

		itemFontSize.add(item10);
	
		
		
		JMenuItem item16 = new JMenuItem("16");
		
		item16.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				setFontSize(16);
			}
		});
		
		itemFontSize.add(item16);
		
		JMenuItem item20 = new JMenuItem("20");
		
		item20.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				setFontSize(20);
			}
		});
		
		itemFontSize.add(item20);
		
		JMenuItem item22 = new JMenuItem("22");
		
		item22.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				setFontSize(22);
			}
		});
		
		itemFontSize.add(item22);
		
		JMenuItem item26 = new JMenuItem("26");
		
		item26.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				setFontSize(26);
			}
		});
		
		itemFontSize.add(item26);
		
		JMenuItem item30 = new JMenuItem("30");
		
		item30.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				setFontSize(30);
			}
		});
		
		itemFontSize.add(item30);
		
		JMenuItem item36 = new JMenuItem("36");
		
		item36.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				setFontSize(36);
			}
		});
		
		itemFontSize.add(item36);
		
		JMenuItem item40 = new JMenuItem("40");
		
		item40.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				setFontSize(40);
			}
		});
		
		itemFontSize.add(item40);
		
		JMenuItem item48 = new JMenuItem("48");
		
		item48.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				setFontSize(48);
			}
		});
		
		itemFontSize.add(item48);
		
		JMenuItem item72 = new JMenuItem("72");
			
		item72.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				setFontSize(72);
			}
		});
		
		itemFontSize.add(item72);
		
	}
	
	public void setFontSize(int size)
	{
		arial = new Font("Arial", Font.PLAIN, size);
		
		newRoman = new Font("Times New Roman", Font.PLAIN, size);
		
		consolas = new Font("Consolas", Font.PLAIN, size);
		
		setFontType(fontStyle);
	}
	
	public void setFontType(String fontName)
	{
		fontStyle = fontName;
		switch (fontName) 
		{
			case "Arial" :
			{
				textArea.setFont(arial);
				break;
			}
			case "Times New Roman" :
			{
				textArea.setFont(newRoman);
				break;
			}
			case "Consolas" :
			{
				textArea.setFont(consolas);
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: "+ fontName);
		}
	}
	
	public void createCommandPromptItem()
	{
		itemCMD = new JMenuItem("Open Cmd");
		
		itemCMD.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if(openPath != null) {
				try 
				{
						Runtime.getRuntime().exec(new String[] {"cmd", "/K", "start"}, null,new File(openPath));
				} 
				
				
				catch (IOException e1) 
				{
					try {
						Runtime.getRuntime().exec(new String[] {"cmd", "/K", "start"}, null,null);
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					System.out.println("Could not launch cmd");
				}
				
				catch(NullPointerException e2)
				{
					
				}
			}
		}
			});
		commandPromptMenu.add(itemCMD);
	}
	
	public void writeDataToFile(String fileName, String path)
	{
		BufferedWriter bw = null;
		try 
		{
			bw = new BufferedWriter(new FileWriter(path + fileName));
			String text = textArea.getText();
			bw.write(text);
			
		} 
		catch (IOException e1) 
		{
			System.out.println();
		}
		finally
		{
			try 
			{
				bw.close();
			} 
			catch (IOException e2) 
			{
				System.out.println("File Cannot be closed");
			}
			catch(NullPointerException ne)
			{
				System.out.println("Cannot be null");
			}
		}
	}

}


