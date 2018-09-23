/**
 * LinkedDS is a multiple-type, linked-list data structure with
 * a few built-in management methods
 * 
 * @author George Meisinger
 */

public class LinkedDS<T> implements PrimQ<T>, Reorder {
    protected Node firstNode;
    protected int numOfEntries;

    private class Node {
        
                private T data;
                private Node next; // link to next node
        
                private Node(T dataPortion) {
                    this(dataPortion, null);
                } // end constructor
        
                private Node(T dataPortion, Node nextNode) {
                    data = dataPortion;
                    next = nextNode;
                } // end constructor
            } // end Node

    /**
     * Default constructor, creates empty LinkedDS.
     */
    public LinkedDS() {
        firstNode = null;
        numOfEntries = 0;
    }

    public LinkedDS(LinkedDS<T> oldList) {
        firstNode = null;
        numOfEntries = 0;
        int index = 0;
        Node currentNode = firstNode;
        while (index < oldList.size()) {
            this.addItem(oldList.get(index));
            index++;
            currentNode = currentNode.next;
        }
    }

    /**Returns the data at given index. Useful for 
     * creating a new LinkedDS from an old one without 
     * modifying the old one.
     * @param index index of LinkedDS where desired data is stored. */
    public T get(int index) {
        Node currentNode = firstNode;
        int pos = 0;
        while (pos < index) {
            currentNode = currentNode.next;
        }
        return currentNode.data;
    }

    // Methods needed by the PrimQ interface

    /**Add a new Object to the PrimQ<T> in the next available location.  If 
     * all goes well, return true.
     * @param newEntry object to be added */
    public boolean addItem(T newEntry) {
        Node newNode = new Node(newEntry);
        if (numOfEntries == 0) {
            firstNode = newNode;
            return true;
        }
        Node currentNode = firstNode;
        while (currentNode.next != null) {
            currentNode = currentNode.next;
        }
        currentNode.next = newNode;
        numOfEntries++;
        return true;
    }
    
    /**Remove and return the "oldest" item in the PrimQ.  If the PrimQ
	 * is empty, return null. */
    public T removeItem() {
        T result = firstNode.data;
        firstNode = firstNode.next;
        return result;

    }   
    
    /** Return true if the PrimQ is empty, and false otherwise */
    public boolean empty() {
        return numOfEntries == 0;
    }

    /** Return the number of items currently in the PrimQ */
    public int size() {
        Node currentNode = firstNode;
        int count = 0;
        while (currentNode.next != null) {
            count++;
            currentNode = currentNode.next;
        }
        return count;
    }

    /** Reset the PrimQ to empty status by reinitializing the variables
	 * appropriately */
    public void clear() {
        firstNode = null;
        firstNode.next = null;
        numOfEntries = 0;
    }    

    //Methods needed by Reorder interface

    /** Logically reverse the data in the LinkedDS object. */
    public void reverse() {
        //
    }

    /** Remove last item and put it at the front. */
    public void shiftRight() {
        if (numOfEntries <= 1) {
            return;
        }
        else {
            Node currentNode = firstNode;
            while (currentNode.next != null) {
                currentNode = currentNode.next;
            }
            currentNode.next = firstNode;
            firstNode = currentNode;
        }
    }    
    
    /** Remove first item and put it at the end. */
    public void shiftLeft() {
        if (numOfEntries <= 1) {
            return;
        }
        else {
            Node currentNode = firstNode;
            while (currentNode.next != null) {
                currentNode = currentNode.next;
            }
            currentNode.next = firstNode;
            firstNode = firstNode.next;
        }
    }    

    /** Reorganize items in a pseudo-random way. */
    public void shuffle() {
        //
    }    

    /** Shift contents num places to the left.
     *  @param num how many places to shift.
     */
    public void leftShift(int num) {
        if (empty()) return;
        Node currentNode = firstNode;
        for (int i=0;i<num;i++) {
            currentNode = currentNode.next;
        }
        firstNode = currentNode;
    }    

    /** shift contents num places to right.
     *  @param num how many places to shift.
     */
    public void rightShift(int num) {
        //
    }    

    /** Rotate contents num places to left. Item at the beginning rotates to the end.
     *  @param num number of places to rotate.
     */
    public void leftRotate(int num) {
        for (int i=0;i<num;i++) {
            this.shiftLeft();
        }
    }    

    /** Rotate contents num places to the right. Item at the end goes to beginning.
     *  @param num number of places to rotate.
     */
    public void rightRotate(int num) {
        for (int i=0;i<num;i++) {
            this.shiftRight();
        }
    } 
    
    public String toString()
	{
        String str = "Contents: \n";        
        if (empty()) {
            str = "LinkedDS is empty";
        }
        else {
            Node currentNode = firstNode;
		    while (currentNode != null)
		    {
                str = str + currentNode.data.toString() + " ";
                currentNode = currentNode.next;
            }
        }
		return str;
	}
}