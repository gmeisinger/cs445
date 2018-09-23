/**
 * LinkedDS is a multiple-type, linked-list data structure with
 * a few built-in management methods
 * 
 * @author George Meisinger
 */

public class LinkedDS<T> implements PrimQ<T>, Reorder {
    protected Node firstNode;
    protected int numOfEntries;

    public class Node {
        
        T data;
        Node next; // link to next node
        
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
        int index = oldList.size()-1;
        while (index >= 0) {
            this.addItem(oldList.get(index));
            index--;
        }
    }

    /**Returns the data at given index. Useful for 
     * creating a new LinkedDS from an old one without 
     * modifying the old one.
     * @param index index of LinkedDS where desired data is stored. */
    public T get(int index) {
        if (index >= numOfEntries) return null;
        else {
            Node currentNode = firstNode;
            int pos = 0;
            while (pos < index) {
                currentNode = currentNode.next;
                pos++;
            }
            return currentNode.data;
        }
    }

    // Methods needed by the PrimQ interface

    /**Add a new Object to the PrimQ<T> in the next available location.  If 
     * all goes well, return true.
     * @param newEntry object to be added */
    public boolean addItem(T newEntry) {
        Node newNode = new Node(newEntry, firstNode);
        firstNode = newNode;
        numOfEntries++;
        return true;
    }
    
    /**Remove and return the "oldest" item in the PrimQ.  If the PrimQ
	 * is empty, return null. */
    public T removeItem() {
        if (empty()) return null;
        else if (numOfEntries == 1) {
            T result = firstNode.data;
            firstNode = null;
            numOfEntries--;
            return result;
        }
        else {
            Node currentNode = firstNode;
            while (currentNode.next.next != null) {
                currentNode = currentNode.next;
            }
            T result = currentNode.next.data;
            currentNode.next = null;
            numOfEntries--;
            return result;
        }

    }   
    
    /** Return true if the PrimQ is empty, and false otherwise */
    public boolean empty() {
        return numOfEntries == 0;
    }

    /** Return the number of items currently in the PrimQ */
    public int size() {
        if (empty()) return 0;
        Node currentNode = firstNode;
        int count = 1;
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
        if (numOfEntries <=1) return;
        Node currentNode = firstNode;
        Node nextNode = null;
        Node prevNode = null;
        while (currentNode != null) {
            nextNode = currentNode.next;
            currentNode.next = prevNode;
            prevNode = currentNode;
            currentNode = nextNode;
        }
        firstNode = prevNode;
    }

    /** Remove first item and put it at the end. */
    public void shiftLeft() {
        if (numOfEntries <= 1) {
            return;
        }
        else {
            Node currentNode = firstNode;
            while (currentNode.next.next != null) {
                currentNode = currentNode.next;
            }
            currentNode.next.next = firstNode;
            firstNode = currentNode.next;
            currentNode.next = null;
        }
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
            firstNode = firstNode.next;
            currentNode.next.next = null;
        }
    }    

    /** Reorganize items in a pseudo-random way. */
    public void shuffle() {
        // Not used in this assignment
    }    

    /** Shift contents num places to the right.
     *  @param num how many places to shift.
     */
    public void rightShift(int num) {
        if (empty() || (num <= 0)) return;
        Node currentNode = firstNode;
        for (int i=0;i<num;i++) {
            currentNode = currentNode.next;
        }
        firstNode = currentNode;
        numOfEntries = this.size();
    }    

    /** shift contents num places to left.
     *  @param num how many places to shift.
     */
    public void leftShift(int num) {
        if (empty() || (num <= 0)) return;
        int toShift = numOfEntries - num - 1;
        Node currentNode = firstNode;
        for (int i=0;i<toShift;i++) {
            currentNode = currentNode.next;
        }
        currentNode.next = null;
        numOfEntries = this.size();
    }    

    /** Rotate contents num places to left. Item at the beginning rotates to the end.
     *  @param num number of places to rotate.
     */
    public void leftRotate(int num) {
        if (num >= 0) {
            for (int i=0;i<num;i++) {
                this.shiftLeft();
            }
        }
        else {
            for (int i=0;i>num;i--) {
                this.shiftRight();
            }
        }
    }    

    /** Rotate contents num places to the right. Item at the end goes to beginning.
     *  @param num number of places to rotate.
     */
    public void rightRotate(int num) {
        if (num >= 0) {
            for (int i=0;i<num;i++) {
                this.shiftRight();
            }
        }
        else {
            for (int i=0;i>num;i--) {
                this.shiftLeft();
            }
        }
    } 
    
    public String toString()
	{
        String str = "";        
        Node currentNode = firstNode;
        while (currentNode != null) {
            str = currentNode.data.toString() + " " + str;            
            currentNode = currentNode.next;
        }
        return str;
    }
}