import java.io.Serializable;
/***********************************************************************
 * Node Class is used in the Ted line editor.  Each node stores a line 
 * of text as well as pointers to the previous and next lines.  The are
 * stored in a linked list.
 * @author Robert Molenhouse
 * @version 1.0
 *
 **********************************************************************/
public class Node implements Serializable {
	/** Line of text entered by the user.*/
    public String data;
    
    /** Next node in the list.*/
    public Node next;
    
    /** Node preceding this one.*/
    public Node prev;

    /*******************************************************************
     * Constructor for Node.
     ******************************************************************/
    public Node() {
        super();
    }

    /*******************************************************************
     * Constructor for node that sets all the data.
     * @param data String of text entered.
     * @param next node in the list.
     * @param prev node in the list.
     ******************************************************************/
    public Node(String data, Node next, Node prev) {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }

    /*******************************************************************
     * Getter for data
     * @return String of data
     ******************************************************************/
    public String getData() {
        return data;
    }

    /*******************************************************************
     * Setter for Data.
     * @param data2 user entered string.
     ******************************************************************/
    public void setData(String data2) {
        this.data = data2;
    }

    /*******************************************************************
     * Getter for next node.
     * @return nect Node in the list.
     ******************************************************************/
    public Node getNext() {
        return next;
    }

    /*******************************************************************
     * Setter for next node.
     * @param next Node in the list.
     ******************************************************************/
    public void setNext(Node next) {
        this.next = next;
    }

    /*******************************************************************
     * Getter for Previous node in the list.
     * @return Previous node.
     ******************************************************************/
    public Node getPrev() {
        return prev;
    }

    /*******************************************************************
     * Setter for previous node.
     * @param prev Node in the list.
     ******************************************************************/
    public void setPrev(Node prev) {
        this.prev = prev;
    }
}