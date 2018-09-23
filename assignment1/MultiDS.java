import java.util.*;
/**
 * MultiDS is a multiple-type data structure with
 * a few built-in management methods
 * 
 * @author George Meisinger
 */

public class MultiDS<T> implements PrimQ<T>, Reorder 
{
	static final int DEFAULT_ARR_SIZE = 10;
	private T[] arr;// = (T[])new Object[DEFAULT_ARR_SIZE];
	
	/**
	 * Constructors
	 * 
	 * @param size The size of the MultiDS. Default is 10
	 */
	public MultiDS()
	{
		this.arr = (T[])new Object[DEFAULT_ARR_SIZE];
	}
	public MultiDS(int size) 
	{
		this.arr = (T[])new Object[size];
	}
	
	/*methods required by the PrimQ interface*/
	
	/**
	 * Add a new Object to the PrimQ<T> in the next available location.  If	
	 * all goes well, return true.  If there is no room in the PrimQ for
	 * the new item, return false (you should NOT resize it)
	 * @param item Item to be added. must be same type as MultiDS.
	 */
	 public boolean addItem(T item)
	{
		if (this.full())
			return false;
		else
		{
			int openIndex = this.size();
			arr[openIndex] = item;
			return true;
		}
	}
	
	/** 
	 * Remove and return the "oldest" item in the PrimQ.  If the PrimQ
	 * is empty, return null.
	 */
	public T removeItem()
	{
		if (this.empty()) return null;
		else
		{
			T oldest = arr[0];
			int size = this.size();
			for (int i=0;i<size-1;i++)
			{
				arr[i] = arr[i+1];
			}
			arr[size-1] = null;
			return oldest;
		}
	}
		
	
	/** 
	 * Returns the "oldest" item in the PrimQ.  If the PrimQ
	 * is empty, return null.
	 */
	public T top()
	{
		if (this.empty()) return null;
		else return arr[0];
	}

	/**
	 * Returns the most recent item in the PrimQ. If the PrimQ
	 * is empty, return null.
	 */
	public T bottom()
	{
		if (this.empty()) return null;
		else return arr[this.size()-1];
	}
		
	/**
	 * Return true if the PrimQ is full, and false otherwise
	 */
	public boolean full()
	{
		for (int i=0;i<arr.length;i++)
		{
			if ( arr[i] == null )
				return false;
		}
		return true;

	}
	
	/**
	 * Return true if the PrimQ is empty, and false otherwise 
	 */
	public boolean empty()
	{
		for (int i=0;i<arr.length;i++)
		{
			if ( arr[i] != null )
				return false;
		}
		return true;
	}
	
	/** 
	 * Return the number of items currently in the PrimQ
	 */
	public int size()
	{
		int num = 0;
		for (int i=0; i<arr.length;i++)
		{
			if (arr[i] != null)
				num++;
		}
		return num;
	}
	

	/** 
	 * Reset the PrimQ to empty status by reinitializing the variables
	 * appropriately
	 */
	public void clear()
	{
		for (int i=0;i<arr.length;i++)
		{
			arr[i] = null;
		}
	}
	
	/**
	 * methods required by the Reorder interface
	 *
	 * Logically reverse the data in the Reorder object so that the item
	 * that was logically first will now be logically last and vice
	 * versa.  The physical implementation of this can be done in 
	 * many different ways, depending upon how you actually implemented
	 * your physical MultiDS<T> class
	 * */
	public void reverse()
	{
		T[] newArr = (T[])new Object[this.size()];
		for (int i=0;i<newArr.length;i++)
		{
			newArr[i] = arr[newArr.length - i - 1];
		}
		for (int i=0;i<newArr.length;i++)
		{
			arr[i] = newArr[i];
		}
	}

	/**
	 * Remove the logical last item of the DS and put it at the 
	 * front.  As with reverse(), this can be done physically in
	 * different ways depending on the underlying implementation. 
	 * */ 
	public void shiftRight()
	{
		T[] newArr = (T[])new Object[this.size()];
		T lastItem = arr[newArr.length-1];
		for (int i=1;i<newArr.length;i++)
		{
			newArr[i] = arr[i-1];
		}
		newArr[0] = lastItem;
		for (int i=0;i<newArr.length;i++)
		{
			arr[i] = newArr[i];
		}
	}

	/** 
	 * Remove the logical first item of the DS and put it at the
	 * end.  As above, this can be done in different ways.
	 * */
	public void shiftLeft()
	{
		T[] newArr = (T[])new Object[this.size()];
		T firstItem = arr[0];
		for (int i=1;i<newArr.length;i++)
		{
			newArr[i-1] = arr[i];
		}
		newArr[newArr.length -1] = firstItem;
		for (int i=0;i<newArr.length;i++)
		{
			arr[i] = newArr[i];
		}
	}

	/** 
	 * Reorganize the items in the object in a pseudo-random way.  The exact
	 * way is up to you but it should utilize a Random object (see Random in 
	 * the Java API).  Thus, after this operation the "oldest" item in the
	 * DS could be arbitrary.
	 */	
	public void shuffle()
	{
		//try creating a random permutation of the range 0-this.size()
		/* 	Create an array to hold the random ints
			for each spot in array
				while spot = null
					generate int
					for each spot in array
						if int = array[i]
							boolean used = true
					if used = false
						spot = int
		*/
		T[] newArr = (T[])new Object[this.size()];
		Random generator = new Random();
		Integer[] usedIndex = new Integer[newArr.length];
		
		for (int i=0;i<usedIndex.length;i++)
		{
			while (usedIndex[i]==null)
			{
				int r = generator.nextInt(usedIndex.length);
				boolean used = false;

				for (int j=0;j<i;j++)
				{
					if (r==usedIndex[j]) used = true;
				}
				if (!(used))
					usedIndex[i] = r;
			}
			newArr[i] = arr[usedIndex[i]];
		}
		
		for (int i=0;i<newArr.length;i++)
		{
			arr[i] = newArr[i];
		}
	}

	public String toString()
	{
		String str = "Contents: \n";
		for (int i=0;i<arr.length;i++)
		{
			if (arr[i] != null) str = str +  arr[i] + " ";
		}
		return str;
	}
	
	
}