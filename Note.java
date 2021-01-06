import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.plaf.basic.BasicLookAndFeel;
import javax.swing.text.SimpleAttributeSet;

import java.io.*;

public class Note implements ActionListener {
	private JFrame frame;
	private JTextPane textpane;
	private JScrollPane scrollpane;

	Note() {
		// Main Frame
		JFrame frame = new JFrame("Test Frame");


		// Theme

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {

		}

		// Menu Bar
		JMenuBar menubar = new JMenuBar();

		// File
		JMenu mFile = new JMenu("File");
		JMenuItem miNew = new JMenuItem("New");
		JMenuItem miOpen = new JMenuItem("Open...");
		JMenuItem miSave = new JMenuItem("Save");
		JMenuItem miSaveAs = new JMenuItem("Save As...");
		JMenuItem miPrint = new JMenuItem("Print...");
		JMenuItem miExit = new JMenuItem("Exit");

		miNew.addActionListener(this);
		miOpen.addActionListener(this);
		miSave.addActionListener(this);
		miSaveAs.addActionListener(this);
		miPrint.addActionListener(this);
		miExit.addActionListener(this);

		mFile.setMnemonic(KeyEvent.VK_M);

		mFile.addMenuListener(null);
		mFile.add(miNew);
		mFile.add(miOpen);
		mFile.add(miSave);
		mFile.add(miSaveAs);
		mFile.add(miPrint);
		mFile.add(miExit);

		// Edit
		JMenu mEdit = new JMenu("Edit");
		JMenuItem miCut = new JMenuItem("Cut");
		JMenuItem miCopy = new JMenuItem("Copy");
		JMenuItem miPaste = new JMenuItem("Paste");
		JMenuItem miDelete = new JMenuItem("Delete");

		miCut.addActionListener(this);
		miCopy.addActionListener(this);
		miPaste.addActionListener(this);
		miDelete.addActionListener(this);

		mEdit.add(miCut);
		mEdit.add(miCopy);
		mEdit.add(miPaste);
		mEdit.add(miDelete);
		
		
		// Format
		JMenu mFormat = new JMenu("Format");
		JMenuItem miFont = new JMenuItem("Font");
		
		miFont.addActionListener(this);
		
		mFormat.add(miFont);

		menubar.add(mFile);
		menubar.add(mEdit);
		menubar.add(mFormat);

		// Text area
		textpane = new JTextPane();
		scrollpane = new JScrollPane(textpane);

		SimpleAttributeSet attributeSet = new SimpleAttributeSet();
		textpane.setCharacterAttributes(attributeSet, true);

		frame.add(scrollpane);
		frame.setJMenuBar(menubar);
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		System.out.println("Hit! : " + command);

		if (command.equals("Exit")) {
			System.out.println("Exit completed.");
			System.exit(0);
		} else if (command.equals("New")) {
			System.out.println("New() activate!");
			if (!textpane.getText().isEmpty()) {
				System.out.println("Text pane not empty!");
				System.out.println("Do something before set text to empty.");
				textpane.setText("");
			} else {
				System.out.println("Text pane is empty!");
				System.out.println("And do nothing...");
			}
		} else if (command.equals("Open...")) {
			openFile();
		} else if (command.equals("Save")) {
			if (!textpane.getText().isEmpty()) {
				saveFile();
			} else {
				saveAsFile();
			}
		} else if (command.equals("Save As...")) {
			System.out.println("Save as... working...");
			saveAsFile();
		} else if (command.equals("Cut")) {
			System.out.println("Cut() activate!");
			System.out.println("Clipboard : " + textpane.getText());
			textpane.cut();
		} else if (command.equals("Copy")) {
			System.out.println("Copy() activate!");
			textpane.copy();
		} else if (command.equals("Paste")) {
			System.out.println("Paste() activate!");
			textpane.paste();
		} else if (command.equals("Delete")) {
			System.out.println("Delete() activate!");
		} else if (command.equals("Print...")) {
			printFile();
		}
	}

	private void openFile() {
		System.out.println("Open() activate!");
		String currentDir = System.getProperty("user.home");
//			System.out.println(currentDir);
		JFileChooser chooser = new JFileChooser(currentDir);
		int opened = chooser.showOpenDialog(null);
		if (opened == chooser.APPROVE_OPTION) {
			System.out.println("Opened...");
			try {
				File file = chooser.getSelectedFile();
				BufferedReader bufferedreader = new BufferedReader(new FileReader(file));
				StringBuilder output = new StringBuilder();
				String line = bufferedreader.readLine();
				System.out.println("Reading file...");
				while (line != null) {
//					System.out.println("Writing..");
//					System.out.println(output.toString());
					System.out.print(line);
					output.append(line);
					output.append(System.lineSeparator());
					line = bufferedreader.readLine();
				}
				textpane.setText(output.toString());
				bufferedreader.close();
				System.out.println("Reading file completed");
			} catch (FileNotFoundException ex) {
				System.out.println("Show Error Messsage :" + ex.getMessage());
				ex.printStackTrace();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} else {
			System.out.println("Do nothing...");
		}
	}

	private void saveFile() {
			
	}

	private void saveAsFile() {
		JFileChooser chooser = new JFileChooser("c:");
		int opened = chooser.showOpenDialog(null);
		if (opened == chooser.APPROVE_OPTION) {
			File file = new File(chooser.getSelectedFile().getAbsolutePath());
			try {
				FileWriter filewriter = new FileWriter(file, false);
				BufferedWriter bufferedwriter = new BufferedWriter(filewriter);
				bufferedwriter.write(textpane.getText());
				bufferedwriter.flush();
				bufferedwriter.close();
			} catch (Exception ex) {

			}
		}
	}

	private void printFile() {
		System.out.println("Print() activate!");
		try {
			textpane.print();
			System.out.println("Print completed");
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Note note = new Note();
	}
}
