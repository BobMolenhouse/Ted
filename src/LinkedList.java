import java.io.Serializable;
/***********************************************************************
 * Linked List.  Stores a series of node objects and maintains them in
 * a linked list structure.
 * @author Robert Molenhouse
 * @version 1.0
 *
 **********************************************************************/
public class LinkedList implements Serializable {
    //private static final long serialVersionUID = 1L;
	/** The first node in the list */
    protected Node top;
    
    /** The current node in the list */
    protected Node cursor; 

    /*******************************************************************
     * Constructor for LinkedList
     ******************************************************************/
    public LinkedList() {
        top = null;
        cursor = null;
    }

    /*******************************************************************
     * Moves the cursor to the top.
     * @throws NullPointerException if list is empty.
     ******************************************************************/
    public void first() {
        cursor = top;
        if (cursor == null)
            throw new NullPointerException();
    }

    /*******************************************************************
     * Moves the cursor to the bottom of the list.
     ******************************************************************/
    public void last() {
        cursor = top;
        if (cursor == null)
            return;

        while (cursor.getNext() != null)
            cursor = cursor.getNext();
    }

    /*******************************************************************
     * moves the cursor down one space.
     ******************************************************************/
    public void next() {
    	if(getLineNumber() < (size()))
        cursor = cursor.getNext();
    }

    /*******************************************************************
     * moves the cursor up one space.
     ******************************************************************/
    public void previous() {
    	if(getLineNumber() > 0)
        cursor = cursor.getPrev();
    }
    
    /*******************************************************************
     * moves the cursor down a specified number of nodes.
     * @param num of nodes to move.
     ******************************************************************/
    public void moveDown(int num){
    	if(num < (size() - getLineNumber())){
    		for (int i = 0; i < num; i++)
    			cursor = cursor.getNext();
    	}
    	else{
    		for (int i = 0; i < (size() - getLineNumber()); i++)
    			cursor = cursor.getNext();
    	}
    }
    
    /*******************************************************************
     * moves the cursor up a specified number of nodes.
     * @param num of nodes to move.
     ******************************************************************/
    public void moveUp(int num){
    	if(num <= getLineNumber()){
    		for (int i = 0; i < num; i++)
    			cursor = cursor.getPrev();
    	}
    	else{
    		for (int i = 0; i < getLineNumber(); i++)
                cursor = cursor.getPrev();
    	}
    }
    
    /*******************************************************************
     * Returns whether the LinkedList is empty or not.
     * @return true if empty.
     ******************************************************************/
    public boolean isEmpty(){
    	if(top == null)
    		return true;
    	else
    		return false;
    }

    /*******************************************************************
     * removes the currently selected node.
     ******************************************************************/
    public void remove() {
       if(cursor == null)
    	   System.out.println("There is nothing here!");
       else if(cursor.getNext() != null){
    	   if(cursor.getPrev() != null){
    		   cursor.getPrev().setNext(cursor.getNext());
    		   cursor.getNext().setPrev(cursor.getPrev());
    		   cursor = cursor.getNext();
    	   }
    	   else{
    		   cursor.getNext().setPrev(null);
    		   top = cursor.getNext();
    		   cursor = cursor.getNext();
    	   }
       }
       else if((cursor.getNext() == null)&&(cursor.getPrev() != null)){
    	   cursor.getPrev().setNext(null);
    	   cursor = cursor.getPrev();
       }
       else
    	   top = cursor = null;
    }

    /*******************************************************************
     * Add a new node before the currently selected node.
     * @param element. data for the new node to store.
     ******************************************************************/
    public void addBefore(String element) { 
    	//If the list is empty...
    			if (top == null)
    				cursor = top = new Node(element, null, null);
    				
    			

    			//If adding to the top of the list, 
    			else if(cursor == top) {
    				
    				//make new node, set top to it.
    				Node temp = new Node(element, top, null);
    				cursor = top = temp;
    				
    			}
    			//if adding to middle
    			else{
    				//put new node before cursor.
    				Node temp = new Node(element, cursor,
    												cursor.getPrev());
    				cursor.getPrev().setNext(temp);
    				cursor.setPrev(temp);
    				cursor = temp;
    				}
    }
    
    /*******************************************************************
     * Add a new node to the end of the list.
     * @param element. data for the node to store.
     ******************************************************************/
    public void addEnd(String element){
    	if(top == null)
    		cursor = top = new Node(element, null, null);
    	
    	else{
    		while(cursor.getNext() != null){
    			cursor = cursor.getNext();
    		}
    		cursor.setNext(new Node(element, null, cursor.getPrev()));
    		cursor = cursor.getNext();
    	}
    }
    
    /*******************************************************************
     * Add a new node after the currently selected one.
     * @param element. data for the new node to store.
     ******************************************************************/
    public void addAfter(String element) { 
    	//If the list is empty...
    			if (cursor == null)
    				cursor = top = new Node(element, null, null);
    				
    			//If adding to the bottom of the list, 
    			else if(cursor.getNext() == null) {
    				
    				//make new node, add to the bottom.
    				Node temp = new Node(element, null, cursor);
    				cursor.setNext(temp);
    				cursor = temp;
    				
    			}
    			//if adding to middle
    			else{
    				//put new node after cursor.
    				Node temp = new Node(element, cursor.getNext(),
    												cursor);
    				cursor.getNext().setPrev(temp);
    				cursor.setNext(temp);
    				cursor = temp;
    				}
    }

    /*******************************************************************
     * Return the size of the list.
     * @return int size of the list.
     ******************************************************************/
    public int size() {
        Node cursorTemp = top;
        int count = 0;

        while (cursorTemp != null) {
            count++;
            cursorTemp = cursorTemp.getNext();
        }
        return count;
    }

    /*******************************************************************
     * return data in the current node.
     * @return string data.
     ******************************************************************/
    public String get() {
        return cursor.getData();
    }

    /*******************************************************************
     * get the data from a specified node, move cursor to it.
     * @param position of node to get.
     * @return data stored.
     ******************************************************************/
    public String get(int position) {
        cursor = top;
        for (int i = 0; i < position; i++)
            cursor = cursor.getNext();
        return cursor.getData();

    }
    
    /*******************************************************************
     * get data of node in specified position without moving the cursor.
     * @param position to get data from.
     * @return data stored.
     ******************************************************************/
    public String getString(int position) {
        Node temp = top;
        for (int i = 0; i < position; i++)
            temp = temp.getNext();
        return temp.getData();
    }
    
    /*******************************************************************
     * Return a string representation of the whole list and its data.
     * @return all the nodes data in the list.
     ******************************************************************/
    public String toString() {  
    	String returnString = new String("");
    	for(int i =0; i < size(); i ++){
    		//check if current node is cursor, if so, display that.
    		if(getNode(i) == cursor)
    			returnString += (i + " > " + getString(i) + "\n");
    	   
    		else
    		returnString += (i + "   " + getString(i) + "\n");
    	}
        return returnString;
    }
    
    /*******************************************************************
     * return specified node based on position.
     * @param position of the node to get.
     * @return node at the position.
     ******************************************************************/
    private Node getNode(int position) {
        Node temp = top;
        for (int i = 0; i < position; i++)
            temp = temp.getNext();
        return temp;
    }
   
    /*******************************************************************
     * return the cursor.
     * @return node at cursor.
     ******************************************************************/
	public Node getCursor() {
		return cursor;
	}

	/*******************************************************************
	 * set the cursor position.
	 * @param cursor node to set as cursor.
	 ******************************************************************/
	public void setCursor(Node cursor) {
		this.cursor = cursor;
	}

	/*******************************************************************
	 * Display data of nodes in the list specified by the user.
	 * @param start index of node to start display.
	 * @param stop index of node to stop display.
	 * @return data of the selected nodes.
	 ******************************************************************/
	public String display(int start, int stop) {
		String returnString = "";
		for (int i = start; i < stop; i++) {
			if (getNode(i) == cursor)
				returnString += (i + " > " + getString(i) + "\n");

			else
				returnString += (i + "   " + getString(i) + "\n");
		}
		return returnString;
    }


	/*******************************************************************
	 * returns current line number
	 * @return int of line number.
	 ******************************************************************/
    public int getLineNumber() {
        int line = 0;
        Node cur = top;

        while ((cur != cursor) && (cur.getNext() != null)) {
            cur = cur.getNext();
            line++;
        }
        return line;
    }

    /*******************************************************************
     * Clear the list.
     ******************************************************************/
    public void clear(){
    	cursor = top = null;
    }

    /*******************************************************************
     * return node at top.
     * @return top node.
     ******************************************************************/
	public Node getTop() {
		return top;
	}
}