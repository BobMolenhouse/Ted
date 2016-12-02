import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
/***********************************************************************
 * A simple line editor.  Allows the user to insert lines of text and
 * change them in many ways.  Files can be saved and loaded.  Uses a 
 * linked list to store its data.
 * @author Robert Molenhouse
 * @version 1.0
 *
 **********************************************************************/
public class Editor implements IEditor {

	/** stores the data in the users doc.*/
	private LinkedList myDoc;
	
	/**the clipboard that stores the current cut */
	private LinkedList cutList;
	
	/** list of undo commands*/
	ArrayList<String> undoList;
	
	/** keeps track of if a command that can be undone is used*/
	boolean undo;
	
	/** true if the doc is saved*/
	private boolean saved;

	/*******************************************************************
	 * Constructor for the Editor.  Instantiates all the various lists.
	 ******************************************************************/
	public Editor() {
		myDoc = new LinkedList();
		cutList = new LinkedList();
		saved = false;
		undoList = new ArrayList<String>();
	}
	
	/*******************************************************************
	 * Main method.  Creates an editor and runs it.
	 * @param args
	 ******************************************************************/
	public static void main(String[] args) {
		Editor v = new Editor();
		v.runEditor();
	}
	
	/*******************************************************************
	 * Processes the user input and calls the correct command.
	 * @param command. User input command
	 ******************************************************************/
	@Override
	public boolean processCommand(String command) {
		Scanner scan = new Scanner(command);

		String line ;
		char cmd = command.trim().charAt(0);
		
		//insert before line
		if(cmd == 'b'){
			line = scan.nextLine();
			myDoc.addBefore(line.substring(1).trim());
			undo = true;
		}

		//insert after line
		else if(cmd == 'i'){
			 line = scan.nextLine();
			 myDoc.addAfter(line.substring(1).trim());
			 undo = true;
		}
		
		//insert at end
		else if(cmd == 'e'){
			line = scan.nextLine();
			myDoc.addEnd(line.substring(1).trim());
			undo = true;
		}
		
		//move up lines
		else if(cmd == 'm'){
			line = scan.nextLine().trim();

			if ((line.length()) == 1) {
				myDoc.next();
			} else {
				try {
					String num = line.substring(1).trim();
					int n = Integer.parseInt(num);
					myDoc.moveDown(n);

				} catch (NumberFormatException e) {
					System.out.println("Invalid Format. try m #");
				}
			}

			undo = true;
		}

		//undo
		else if((command.trim().charAt(0) == 'u') && 
				(command.length() >= 2) &&
				(command.trim().charAt(1) == 'd')){
			if(!undoList.isEmpty())
				undo();
			else
				System.out.println("Nothing to undo");
			
			undo = false;
		}

		// move up lines
		else if (cmd == 'u') {
			line = scan.nextLine().trim();

			if (line.length() == 1) {
				myDoc.previous();

			} else {
				try {
					String num = line.substring(1).trim();
					int n = Integer.parseInt(num);
					myDoc.moveUp(n);
					
				} catch (NumberFormatException e) {
					System.out.println("Invalid Format. Use u # #");
				}
			}

			undo = true;
		}
		//remove lines
		else if(cmd == 'r'){
			line = scan.nextLine().trim();
			
			if(line.length() != 1){
			try{
				String num = line.substring(1).trim();
				int n = Integer.parseInt(num);
				for(int i = 0; i < n; i++)
					myDoc.remove();
			}
			catch(NumberFormatException e){
				System.out.println("Invalid Format. Use r #, or r");
				
			}
			}
			else
			 myDoc.remove();
			
			undo = true;
		}
		
		//cut out lines and copy to clipboard.
		else if((cmd == 'c') && (command.length() >= 3) &&
				(command.trim().charAt(1) == 'u') &&
				(command.trim().charAt(2) == 't')){
				try{
					line = scan.nextLine();
					String [] cmds = line.trim().split(" ");
					if(cmds.length == 3){
					int start = Integer.parseInt(cmds[1]);
					int end = Integer.parseInt(cmds[2]);
					cut(start, end);
					}
					else
						System.out.println("Invalid format. Please use"
				                                 + " \"cut # #\".");
				}catch(Exception e){
					System.out.println("Invalid format. Please use" +
				                                  " \"cut # #\".");
			}
				undo = true;
		}
		
		//paste contents of clipboard above current line.
		else if((command.trim().charAt(0) == 'p') && 
				(command.length() >= 3) &&
				(command.trim().charAt(1) == 'a') &&
				(command.trim().charAt(2) == 's')){
			if(!cutList.isEmpty()){
				paste();
				undo = true;
			}
			else{
				System.out.println("Nothing in clipboard to paste");
				undo = false;
			}
		}

		// clear the doc
		else if (cmd == 'c') {
			if (saved) {
				myDoc.clear();
				undo = true;
			} else {
				System.out.println("Please save before clearing");
				undo = false;
			}
		}
		
		//save file
		else if(cmd == 's'){
			line = scan.nextLine();
			if (saveFile(line.substring(1).trim())) {
				System.out.println("Saved!");
				undo = false;
			}
		}
		
		//load file
		else if(cmd == 'l'){
			line = scan.nextLine();
			if (loadFile(line.substring(1).trim()))
				System.out.println("Loaded!");
			undo = false;
		}
		
		//display the doc
		else if(cmd == 'd'){
			try {
				line = scan.nextLine();
				String[] cmds = line.trim().split(" ");
				if(cmds.length == 1){
					System.out.println(myDoc.toString());
				}
				else{
					int start = Integer.parseInt(cmds[1]);
					int stop = Integer.parseInt(cmds[2]);
					System.out.println(myDoc.display(start, (stop + 1)));
				}
			} catch (Exception e) {
				System.out.println("Invalid format. Please use" +
				" \"d # #\". \n Or just type \"d\" for complete file.");
			}
			undo = false;
		}
		
		//show help
		else if(cmd == 'h'){
			showHelp();
			undo = true;
		}

		//exit
		else if(cmd == 'x'){
			if(saved || myDoc.isEmpty()){
				System.out.println("Exiting");
				System.exit(0);
			}
			else{
				System.out.println("Please save before exiting");
			}
			undo = false;
		}
		
		//if you get here, the command entered is wrong.
		else{
			System.out.println("Not a command!");
			undo = false;
		}


		return true;
	}

	/*******************************************************************
	 * returns String of specified line.
	 * @return contents of line.
	 ******************************************************************/
	@Override
	public String getLine(int lineNbr) {
		return myDoc.get(lineNbr);  
	}

	/*******************************************************************
	 * Returns String of current line.
	 * @return contents of line.
	 ******************************************************************/
	@Override
	public String getCurrentLine() {
		return myDoc.get();  
	}

	/*******************************************************************
	 * Runs the editor.  Asks for input and executes the commands.
	 * Loops until closed.
	 ******************************************************************/
	private void runEditor() {
		String command = "";
		boolean exitCond;
		Scanner scan = new Scanner(System.in);
		do {
			System.out.println("Command:");
			try{
				command = scan.nextLine();
				exitCond = processCommand(command);
				if(undo){
					fillUndo(command);
				}
			}catch(Exception e){
				System.out.println("Please enter a command");
			}
			
			
		} while (!command.equalsIgnoreCase("z"));
	}
	
	/*******************************************************************
	 * Fills the list of undo commands.
	 * @param command to put into undo list.
	 ******************************************************************/
	private void fillUndo(String command){
		undoList.add(command);
	}
	
	/*******************************************************************
	 * removes previous command operation.
	 ******************************************************************/
	private void undo(){
		myDoc.clear();
		undoList.remove((undoList.size() - 1));
		
		for(int i = 0; i < undoList.size(); i++){
			processCommand(undoList.get(i));
		}
	}
	
	/*******************************************************************
	 * Load a saved file.
	 * @param fileName. File to load
	 * @return true if successful.
	 ******************************************************************/
	private boolean loadFile(String fileName) {
		try {
			Scanner sc = new Scanner (new File(fileName));
			myDoc.clear();
			while (sc.hasNextLine()) {
				myDoc.addAfter(sc.nextLine());
			}
			myDoc.first();
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			return false;
		}
		return true;
	}
	
	/*******************************************************************
	 * Saves current file
	 * @param fileName. name for the file.
	 * @return true if successful. 
	 ******************************************************************/
	private boolean saveFile(String fileName) {
		PrintWriter p = null;
		try {
			p=new PrintWriter(new BufferedWriter
									(new FileWriter(fileName)));
			Node temp = myDoc.getTop();
			while (temp != null){
				p.println(temp.getData());
				temp = temp.getNext();
			}
			p.close();
		} catch (IOException e) {
			System.out.println("Error! File was not saved");
			return false;
		}
		saved = true;
		return true;
	}
	
	/*******************************************************************
	 * Cuts specified lines out at puts into clipboard.
	 * @param start first line to cut
	 * @param end last line to cut.
	 ******************************************************************/
	private void cut(int start, int end){
		myDoc.first();
		// Finding Starting Position
		for(int i = 1; i <= start; i++) {
			if(myDoc.getCursor().getNext() != null) {
				myDoc.next();
			}
			else {
				System.out.println("Invalid Starting Point!");
			}
		}
		// After Start has been found, cut lines into cutList
		for(int i = start; i <= end; i++) {
			if(myDoc.getCursor() != null) {
				cutList.addAfter(myDoc.get());
				myDoc.remove();
			}
			else {
				System.out.println("Invalid Ending Point!");
			}
		}
	}
	
	/*******************************************************************
	 * Pastes the contents of the clipboard above the current line.
	 ******************************************************************/
	private void paste(){
		String data;
		cutList.first();
		
		//if myDoc is empty
		if(myDoc.getTop() == null) {
			while(cutList.getCursor() != null) {
				data = cutList.get();
				myDoc.addAfter(data);
				cutList.next();
			}
		}
		
		//if pasting in the middle
		else if(myDoc.getCursor().getPrev() != null) {
			myDoc.previous();
			while(cutList.getCursor() != null) {
				data = cutList.get();
				myDoc.addAfter(data);
				cutList.next();
			}
		} 
		
		//if pasting at the end
		else {
			data = cutList.get();
			myDoc.addBefore(data);
			cutList.next();
			while(cutList.getCursor() != null) {
				data = cutList.get();
				myDoc.addAfter(data);
				cutList.next();
			}
		}
		
		// return cursor to original pre-paste command position.
		if (myDoc.getCursor().getNext() != null)
			myDoc.next();
	}
	
	/*******************************************************************
	 * Print help menu.
	 ******************************************************************/
	private void showHelp(){
		System.out.println("Welcome to Ted!  The Following commands"
				+ " can be used\nto navigate in and edit your file:\n"
				+ "b <sentence> = insert line before the current line\n"
				+ "i <sentence> = insert after the current line\n"
				+ "e <sentence> = insert at the end of the document\n"
				+ "d = display the buffer contents\n"
				+ "d # # = display the buffer contents from line #"
				+ " to line #\n"
				+ "m = move the current line down 1 position\n"
				+ "m # = move the current line down # positions\n"
				+ "u = move the current line up 1 position\n"
				+ "u # = move the current line up 1 positions\n"
				+ "r = remove the current line\n"
				+ "r # = remove # lines starting at the current line\n"
				+ "s <filename> = save the contents to a text file\n"
				+ "l <filename> = load contents of a text file\n"
				+ "c = clear the buffer contents\n"
				+ " cut # # = cut line # to # and add to clipboard\n"
				+ "pas = paste contents of the clipboard before\n"
				+ "current position"
				+ "ud = undo previous operation"
				+ "h = show a help menu of editor commands\n"
				+ "x = exit the editor");
	}
}