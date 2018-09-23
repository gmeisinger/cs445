/** CS 0445 Fall 2017 (Adapted  from Dr. John Ramirez's assignment code)
 This is a partial implementation of the ReallyLongInt class.  You need to
 complete the implementations of the remaining methods.  Also, for this class
 to work, you must complete the implementation of the LinkedDS class.
 See additional comments below.
*/
public class ReallyLongInt 	extends LinkedDS<Integer> 
							implements Comparable<ReallyLongInt>
{
	// Instance variables are inherited.  You may not add any new instance variables
	
	// Default constructor
	private ReallyLongInt()
	{
		super();
	}

	// Note that we are adding the digits here in the FRONT. This is more efficient
	// (no traversal is necessary) and results in the LEAST significant digit first
	// in the list.  It is assumed that String s is a valid representation of an
	// unsigned integer with no leading zeros.
	public ReallyLongInt(String s)
	{
		super();
		char c;
		int digit;
		// Iterate through the String, getting each character and converting it into
		// an int.  Then make an Integer and add at the front of the list.  Note that
		// the addItem() method (from LinkedDS) does not need to traverse the list since
		// it is adding in position 0.  
		for (int i = 0; i < s.length(); i++)
		{
			c = s.charAt(i);
			if (('0' <= c) && (c <= '9'))
			{
				digit = c - '0';
				this.addItem(new Integer(digit));
			}
			else throw new NumberFormatException("Illegal digit " + c);
		}
	}

	// Simple call to super to copy the nodes from the argument ReallyLongInt
	// into a new one.
	public ReallyLongInt(ReallyLongInt rightOp)
	{
		super(rightOp);
	}
	
	// Method to put digits of number into a String.  Since the numbers are
	// stored "backward" (least significant digit first) we first reverse the
	// number, then traverse it to add the digits to a StringBuilder, then
	// reverse it again.  This seems like a lot of work, but given the
	// limitations of the super classes it is what we must do.
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		if (numOfEntries > 0)
		{
			this.reverse();
			for (Node curr = firstNode; curr != null; curr = curr.next)
			{
				sb.append(curr.data);
			}
			this.reverse();
		}
		return sb.toString();
	}

	// You must implement the methods below.  See the descriptions in the
	// assignment sheet

	/**Adds two ReallyLongInt objects.
	 * @param rightOp right operator.
	 */
	public ReallyLongInt add(ReallyLongInt rightOp)
	{
		ReallyLongInt result = new ReallyLongInt();
		int length;
		if (this.size() >= rightOp.size()) {
			length = this.size();
		}
		else length = rightOp.size();
		int lOp;
		int rOp;
		int carry = 0;
		int sum;
		for (int i = 0;i<length;i++) {
			if (this.get(i) != null) lOp = this.get(i);
			else lOp = 0;
			if (rightOp.get(i) != null) rOp = rightOp.get(i);
			else rOp = 0;
			sum = lOp + rOp + carry;
			if (sum > 9) {
				carry = 1;
				sum = sum%10;
			}
			result.addItem(sum);
		}
		if (carry==1) result.addItem(carry);
		result.reverse();
		return result;	
	}

	/**Subtracts one ReallyLongInt from another.
	 * @param rightOp right operator.
	 */
	public ReallyLongInt subtract(ReallyLongInt rightOp)
	{
		ReallyLongInt result = new ReallyLongInt();		
		if (this.compareTo(rightOp) < 0) {
			throw new ArithmeticException("Invalid Difference -- Negative Number");
		}
		else {
			int rOp;
			int lOp;
			int borrow = 0;
			int diff;
			for (int i=0;i<numOfEntries;i++) {
				lOp = this.get(i) - borrow;

				if (rightOp.get(i) != null) rOp = rightOp.get(i);
				else rOp = 0;
				
				diff = lOp - rOp;				
				if (diff < 0) {
					diff = 10 + diff;
					borrow = 1;
				}				
				result.addItem(diff);
			}
			result.reverse();
		}
		// Remove leading zeros
		while (result.get(result.size()-1) == 0) {
			result.removeItem();
		}
		return result;
	}

	/**Compares two ReallyLongInt objects. returns -1 if left op is smaller, 1 if larger, 0 if equal.
	 * @param rightOp right operator in comparison.
	 */
	public int compareTo(ReallyLongInt rightOp)
	{
		int result = 0;
		if (this.size() > rightOp.size()) result = 1;
		else if (this.size() < rightOp.size()) result = -1;
		else {
			this.reverse();
			rightOp.reverse();
			int pos = 0;
			while (result == 0 && pos < numOfEntries) {
				if (this.get(pos) > rightOp.get(pos)) result = 1;
				else if (this.get(pos) < rightOp.get(pos)) result = -1;
				pos++;
			}
			this.reverse();
			rightOp.reverse();
		}
		return result;
	}

	/**Returns true if the two ReallyLongInt objects are equal.
	 * @param rightOp ReallyLongInt to be compared.
	 */
	public boolean equals(Object rightOp)
	{
		ReallyLongInt rOp = (ReallyLongInt) rightOp;
		return this.compareTo(rOp) == 0;
	}

	/**multiplies ReallyLongInt by 10^num
	 * @param num exponent of 10, number of zeros to add.
	 */
	public void multTenToThe(int num)
	{
		for (int i = 0;i<num;i++) {
			this.addItem(new Integer(0));
		}
	}

	/**divides ReallyLongInt by 10^num
	 * @param num exponent of 10, number of places to shift.
	 */
	public void divTenToThe(int num)
	{
		this.rightShift(num);
	}
}
