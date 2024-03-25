
/** Defines SearchEngine class
 * @author Rimon Yacoub
 * CIS 22C: Final Project 
 */
import java.util.ArrayList;

public class HashTable<T> {

	private int numElements;
	private ArrayList<LinkedList<T>> Table;

	/**
	 * Constructor for the HashTable class. Initializes the Table to be sized
	 * according to value passed in as a parameter Inserts size empty Lists into the
	 * table. Sets numElements to 0
	 * 
	 * @param size the table size
	 * @precondition size > 0
	 * @throws IllegalArgumentException when size < 0
	 */
	public HashTable(int size) throws IllegalArgumentException {
		if (size <= 0) {
			throw new IllegalArgumentException("Default Constr.: Size too small");
		}
		Table = new ArrayList<LinkedList<T>>(size);
		for (int i = 0; i < size; i++) {
			Table.add(new LinkedList<T>());
		}
		numElements = 0;
	}

	/**
	 * Constructor for HashTable class Inserts the contents of the given array into
	 * the Table at the appropriate indices
	 * 
	 * @param array an array of elements to insert
	 * @param size  the size of the Table
	 * @precondition size > 0
	 * @throws IllegalArgumentException when size < 0
	 */
	public HashTable(T[] array, int size) throws IllegalArgumentException {
		if (size <= 0) {
			throw new IllegalArgumentException("Array Constr.: Size too small");
		} else if (array == null || array.length == 0) {
			return;
		}

		Table = new ArrayList<LinkedList<T>>(size);
		for (int i = 0; i < size; i++) {
			Table.add(new LinkedList<T>());
		}
		numElements = 0;

		for (int i = 0; i < array.length; i++) {
			add(array[i]);
		}
	}

	/** Accessors */

	/**
	 * returns the hash value in the Table for a given Object
	 * 
	 * @param t the Object
	 * @return the index in the Table
	 */
	private int hash(T t) {
		int code = t.hashCode();
		return Math.abs(code) % Table.size();
	}

	/**
	 * Counts the number of elements at this index
	 * 
	 * @param index the index in the Table
	 * @precondition index <= size
	 * @return the count of elements at this index
	 * @throws IndexOutOfBoundsException when the precondition is violated
	 */
	public int countBucket(int index) throws IndexOutOfBoundsException {
		if (index > Table.size()) {
			throw new IndexOutOfBoundsException("countBucket: Index is out of bounds");
		}
		return Table.get(index).getLength();
	}

	/**
	 * Determines total number of elements in the Table
	 * 
	 * @return total number of elements
	 */
	public int getNumElements() {
		return numElements;
	}


	/**
	 * Accesses a specified element in the Table
	 * 
	 * @param t the element to locate
	 * @return the element index or -1 if it is not found
	 * @precondition t != null
	 * @throws NullPointerException when the precondition is violated
	 */
	public int find(T t) throws NullPointerException {
		if (t == null) {
			throw new NullPointerException("search: element cannot be null");
		} else if (contains(t) == false) {
			return -1;
		} else {
			int index = hash(t);
			int bucket = Table.get(index).findIndex(t);
			Table.get(index).positionIterator();
			Table.get(index).advanceIteratorToIndex(bucket);
			return Table.get(index).getIteratorIndex();
		}
	}
	/**
	 * Accesses a specified element in the Table
	 * 
	 * @param t the element to locate
	 * @return the element or null if it is not found
	 * @precondition t != null
	 * @throws NullPointerException when the precondition is violated
	 */
	public T findObj(T t) throws NullPointerException {
		if (t == null) {
			throw new NullPointerException("search: element cannot be null");
		} else if (contains(t) == false) {
			return null;
		} else {
			int index = hash(t);
			int bucket = Table.get(index).findIndex(t);
			Table.get(index).positionIterator();
			Table.get(index).advanceIteratorToIndex(bucket);
			return Table.get(index).getIterator();
		}
	}


	
	/**
	 * Determines whether a specified element is in the Table
	 * 
	 * @param t the element to locate
	 * @return whether the element is in the Table
	 * @precondition t != null
	 * @throws NullPointerException when the precondition is violated
	 */
	public boolean contains(T t) throws NullPointerException {
		if (t == null) {
			throw new NullPointerException("contains: data cannot be null");
		}
		int index = hash(t);
		if (Table.get(index).findIndex(t) == -1) {
			return false;
		} else {
			return true;
		}
	}

	/** Mutators */

	/**
	 * Inserts a new element in the Table at the end of the chain of the correct
	 * bucket
	 * 
	 * @param t the element to insert
	 * @precondition t != null
	 * @throws NullPointerException when the precondition is violated
	 */
	public void add(T t) throws NullPointerException {
		if (t == null) {
			throw new NullPointerException("add: data cannot be null");
		} else {
			int bucket = hash(t);
			Table.get(bucket).addLast(t);
			numElements++;
		}
	}

	/**
	 * Removes the given element from the Table
	 * 
	 * @param t the element to remove
	 * @precondition t != null
	 * @return whether t exists and was removed from the Table
	 * @throws NullPointerException when the precondition is violated
	 */
	public boolean delete(T t) throws NullPointerException {
		if (t == null) {
			throw new NullPointerException("delete: data cannot be null");
		} else {
			int bucket = hash(t);
			int index = Table.get(bucket).findIndex(t);
			if (index != -1) {
				Table.get(bucket).positionIterator();
				Table.get(bucket).advanceIteratorToIndex(index);
				Table.get(bucket).removeIterator();
				numElements--;
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * Resets the hash table back to the empty state, as if the one argument
	 * constructor has just been called.
	 */
	public void clear() {
		int size = Table.size();
		Table = new ArrayList<LinkedList<T>>(size);
		for (int i = 0; i < size; i++) {
			Table.add(new LinkedList<T>());
		}
		numElements = 0;
	}

	/** Additional Methods */

	/**
	 * Computes the load factor
	 * 
	 * @return the load factor
	 */
	public double getLoadFactor() {
		double elements = numElements;
		return elements / Table.size();
	}

	/**
	 * Creates a String of all elements at a given bucket
	 * 
	 * @param bucket the index in the Table
	 * @return a String of elements, separated by spaces with a new line character
	 *         at the end
	 * @precondition <you fill in here>
	 * @throws IndexOutOfBoundsException when bucket is out of bounds
	 */
	public String bucketToString(int bucket) throws IndexOutOfBoundsException {
		StringBuilder temp = new StringBuilder();
		Table.get(bucket).positionIterator();
		for (int i = 0; i < countBucket(bucket); i++) {
			temp.append(Table.get(bucket).getIterator() + " ");
			Table.get(bucket).advanceIterator();
		}
		temp.append("\n");
		return temp.toString();
	}

	/**
	 * Creates a String of the bucket number followed by a colon followed by the
	 * first element at each bucket followed by a new line For empty buckets, add
	 * the bucket number followed by a colon followed by empty
	 * 
	 * @return a String of all first elements at each bucket
	 */
	public String rowToString() {
		StringBuilder temp = new StringBuilder();
		for (int i = 0; i < Table.size(); i++) {
			temp.append("Bucket " + i + ": ");
			if (countBucket(i) == 0) {
				temp.append("empty\n");
			} else {
				temp.append(Table.get(i).getFirst().toString() + "\n");
			}
		}
		return temp.toString();
	}

	/**
	 * Starting at the 0th bucket, and continuing in order until the last
	 * bucket,concatenates all elements at all buckets into one String, with a new
	 * line between buckets and one more new line at the end of the entire String
	 */
	@Override
	public String toString() {
		StringBuilder temp = new StringBuilder();
		for (int i = 0; i < Table.size(); i++) {
			if (countBucket(i) != 0) {
				temp.append(bucketToString(i));
			}
		}
		return temp.toString() + "\n";
	}
	/**
	 * Adds all elements in Hash Table to an array list and returns list
	 *@return  ArrayList containing all elements in the Hash Table 
	 */
	public ArrayList<T> listAllElements(){
		ArrayList<T> list = new ArrayList<T>();
		for (int i = 0; i < Table.size(); i++) {
			if (!Table.get(i).isEmpty()) {
				Table.get(i).positionIterator();
				for (int j = 1; j <= Table.get(i).getLength(); j++) {
					list.add(Table.get(i).getIterator());
					Table.get(i).advanceIterator();
				}
			}
		}
		return list;
	}
}// END CLASS

