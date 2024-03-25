
/**
 * Defines a doubly-linked list class
 * @author Rimon Yacoub
 * CIS 22C, Final Project
 */

import java.util.NoSuchElementException;

public class LinkedList<T> {
	private class Node {
		private T data;
		private Node next;
		private Node prev;

		public Node(T data) {
			this.data = data;
			this.next = null;
			this.prev = null;
		}
	}

	private int length;
	private Node first;
	private Node last;
	private Node iterator;

	/**** CONSTRUCTORS ****/

	/**
	 * Instantiates a new LinkedList with default values
	 * 
	 * @postcondition New linked list object with default values
	 */
	public LinkedList() {
		length = 0;
		first = null;
		last = null;
		iterator = null;
	}

	/**
	 * Converts the given array into a LinkedList
	 * 
	 * @param array the array of values to insert into this LinkedList
	 * @postcondition Make a linked list with the array parameter
	 */
	public LinkedList(T[] array) {
		this();
		for (int i = 0; i < array.length; i++) {
			addLast(array[i]);
		}
		// length = array.length;
	}

	/**
	 * Instantiates a new LinkedList by copying another List
	 * 
	 * @param original the LinkedList to copy
	 * @postcondition a new List object, which is an identical, but separate, copy
	 *                of the LinkedList original
	 */
	public LinkedList(LinkedList<T> original) {
		if (original == null) {
			return;
		}
		if (original.length == 0) {
			length = 0;
			first = null;
			last = null;
			iterator = null;
		} else {
			Node temp = original.first;
			while (temp != null) {
				addLast(temp.data);
				temp = temp.next;
			}
			iterator = null;
			// this.length = original.length;
		}
	}

	/**** ACCESSORS ****/

	/**
	 * Returns the value stored in the first node
	 * 
	 * @precondition length != 0
	 * @return the value stored at node first
	 * @throws NoSuchElementException when length == 0
	 */
	public T getFirst() throws NoSuchElementException {
		if (length == 0) {
			throw new NoSuchElementException("getFirst: " + "List is empty!");
		}
		return first.data;
	}

	/**
	 * Returns the value stored in the last node
	 * 
	 * @precondition length != 0
	 * @return the value stored in the node last
	 * @throws NoSuchElementException when length == 0
	 */
	public T getLast() throws NoSuchElementException {
		if (length == 0) {
			throw new NoSuchElementException("getLast: " + "List is empty!");
		}
		return last.data;
	}

	/**
	 * Returns the data stored in the iterator node
	 * 
	 * @precondition iterator != null
	 * @throw NullPointerException when iterator == null
	 */
	public T getIterator() throws NullPointerException {
		if (offEnd()) {
			throw new NullPointerException("getIterator: " + "Iterator is null!");
		}
		return iterator.data;
	}

	/**
	 * Returns the current length of the LinkedList
	 * 
	 * @return the length of the LinkedList from 0 to n
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Returns whether the LinkedList is currently empty
	 * 
	 * @return whether the LinkedList is empty
	 */
	public boolean isEmpty() {
		return (length == 0);
	}

	/**
	 * Returns whether the iterator is offEnd, i.e. null
	 * 
	 * @return whether the iterator is null
	 */
	public boolean offEnd() {
		return (iterator == null);
	}

	/**** MUTATORS ****/

	/**
	 * Creates a new first element
	 * 
	 * @param data the data to insert at the front of the LinkedList
	 * @postcondition creates a new first node from the data in the parameter
	 */
	public void addFirst(T data) {
		if (length == 0) {
			first = last = new Node(data);
		} else {
			Node n = new Node(data);
			n.next = first;
			first.prev = n;
			first = n;
		}
		length++;
	}

	/**
	 * Creates a new last element
	 * 
	 * @param data the data to insert at the end of the LinkedList
	 * @postcondition creates a new last node from the data in the parameter
	 */
	public void addLast(T data) {
		if (length == 0) {
			first = last = new Node(data);
		} else {
			Node n = new Node(data);
			n.prev = last;
			last.next = n;
			last = n;
		}
		length++;
	}

	/**
	 * Inserts a new element after the iterator
	 * 
	 * @param data the data to insert
	 * @precondition iterator != null
	 * @throws NullPointerException when iterator == null
	 */
	public void addIterator(T data) throws NullPointerException {
		if (offEnd()) {
			throw new NullPointerException("getIterator: " + "Iterator is null!");
		} else if (iterator == last) {
			addLast(data);
		} else {
			Node n = new Node(data);
			n.next = iterator.next;
			n.prev = iterator;
			iterator.next.prev = n;
			iterator.next = n;
		}
		length++;
	}

	/**
	 * removes the element at the front of the LinkedList
	 * 
	 * @precondition length != 0
	 * @postcondition removes first node of the linked list
	 * @throws NoSuchElementException when length == 0
	 */
	public void removeFirst() throws NoSuchElementException {
		if (length == 0) { // precondition
			throw new NoSuchElementException("removeFirst(): Cannot remove from an empty List!");
		} else if (length == 1) { // edge case #1
			first = last = iterator = null;
		} else {
			if (iterator == first) { // edge case #2
				iterator = null;
			}
			first = first.next;
			first.prev = null;
		}
		length--;
	}

	/**
	 * removes the element at the end of the LinkedList
	 * 
	 * @precondition length != 0
	 * @postcondition removes last node of the linked list
	 * @throws NoSuchElementException when length == 0
	 */
	public void removeLast() throws NoSuchElementException {
		if (length == 0) { // precondition
			throw new NoSuchElementException("removeFirst(): Cannot remove from an empty List!");
		} else if (length == 1) { // edge case #1
			first = last = iterator = null;
		} else {
			if (iterator == last) { // edge case #2
				iterator = null;
			}
			last = last.prev;
			last.next = null;
		}
		length--;
	}

	/**
	 * removes the element referenced by the iterator
	 * 
	 * @precondition iterator != null
	 * @postcondition element referenced by the iterator is removed
	 * @throws NullPointerException when iterator == null
	 */
	public void removeIterator() throws NullPointerException {
		if (offEnd()) {
			throw new NullPointerException("removeIterator(): Iterator is null!");
		} else if (length == 1) {
			first = last = iterator = null;
		} else {
			if (iterator == first) {
				first = iterator.next;
				first.prev = null;
				iterator = first;
			} else if (iterator == last) {
				last = iterator.prev;
				last.next = null;
				iterator = last;
			} else {
				iterator.prev.next = iterator.next;
				iterator.next.prev = iterator.prev;
			}
			iterator = null;
		}
		length--;
	}

	/**
	 * places the iterator at the first node
	 * 
	 * @postcondition iterator == first
	 */
	public void positionIterator() {
		iterator = first;
	}

	/**
	 * Moves the iterator one node towards the last
	 * 
	 * @precondition !offEnd()
	 * @postcondition iterator moves one node towards last
	 * @throws NullPointerException when iterator == null
	 */
	public void advanceIterator() throws NullPointerException {
		// ? iterator == last throw exception => offend will not be true when go through
		// a linkedlist
		if (offEnd()) {
			throw new NullPointerException("addIterator(): " + "iIterator is null. Cannot advance.");
		} else {
			iterator = iterator.next; // general case
		}
	}

	/**
	 * Moves the iterator one node towards the first
	 * 
	 * @precondition !offEnd()
	 * @postcondition iterator moves one node towards first
	 * @throws NullPointerException when iterator == null
	 */
	public void reverseIterator() throws NullPointerException {
		// ? iterator == first throw exception => offend will not be true when go
		// through a linkedlist
		if (offEnd()) {
			throw new NullPointerException("addIterator(): " + "Iterator is null");
		} else {
			iterator = iterator.prev;
		}
	}

	/**** ADDITIONAL OPERATIONS ****/

	/**
	 * Converts the LinkedList to a String, with each value separated by a blank
	 * line At the end of the String, place a new line character
	 * 
	 * @return the LinkedList as a String
	 */
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		Node temp = first;
		while (temp != null) {
			result.append(temp.data.toString() + " ");
			temp = temp.next;
		}
		return result.toString() + "\n";
	}

	/**
	 * Determines whether the given Object is another LinkedList, containing the
	 * same data in the same order
	 * 
	 * @param o another Object
	 * @return whether there is equality
	 */
	@SuppressWarnings("unchecked") // good practice to remove warning here
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof LinkedList)) {
			return false;
		} else {
			LinkedList<T> L = (LinkedList<T>) o;
			if (L.length != this.length) {
				return false;
			} else {
				Node temp1 = this.first;
				Node temp2 = L.first;
				while (temp1 != null) {
					if (temp1.data.equals(temp2.data)) {
						temp1 = temp1.next;
						temp2 = temp2.next;
					} else {
						return false;
					}
				}
				return true;
			}
		}
	}

	/** CHALLENGE METHODS */

	/**
	 * Moves all nodes in the list towards the end of the list the number of times
	 * specified Any node that falls off the end of the list as it moves forward
	 * will be placed the front of the list For example: [1, 2, 3, 4, 5], numMoves =
	 * 2 -> [4, 5, 1, 2 ,3] For example: [1, 2, 3, 4, 5], numMoves = 4 -> [2, 3, 4,
	 * 5, 1] For example: [1, 2, 3, 4, 5], numMoves = 7 -> [4, 5, 1, 2 ,3]
	 * 
	 * @param numMoves the number of times to move each node.
	 * @precondition numMoves >= 0
	 * @postcondition iterator position unchanged (i.e. still referencing the same
	 *                node in the list, regardless of new location of Node)
	 * @throws IllegalArgumentException when numMoves < 0
	 */
	public void revolvingList(int numMoves) throws IllegalArgumentException {
		if (numMoves < 0) {
			throw new IllegalArgumentException("revolvingList(): numMoves should not less than zero. ");
		} else if (numMoves == 0 || length == 1 || length == 0) {
			return;
		} else {
			for (int i = 1; i <= (numMoves % length); i++) {
				Node temp = last;
				boolean iteratorAtLast = false;
				if (iterator == last) {
					iteratorAtLast = true;
				}
				removeLast();
				addFirst(temp.data);
				if (iteratorAtLast) {
					positionIterator();
				}
			}
		}
	}

	/**
	 * Splices together two LinkedLists to create a third List which contains
	 * alternating values from this list and the given parameter For example:
	 * [1,2,3] and [4,5,6] -> [1,4,2,5,3,6] For example: [1, 2, 3, 4] and [5, 6] ->
	 * [1, 5, 2, 6, 3, 4] For example: [1, 2] and [3, 4, 5, 6] -> [1, 3, 2, 4, 5, 6]
	 * 
	 * @param list the second LinkedList
	 * @return a new LinkedList, which is the result of interlocking this and list
	 * @postcondition this and list are unchanged
	 */
	public LinkedList<T> interlockLists(LinkedList<T> list) {
		if (list == null || list.length == 0) {
			return new LinkedList<>(this);
		} else if (this.length == 0) {
			return new LinkedList<>(list);
		} else {
			LinkedList<T> result = new LinkedList<>();
			this.positionIterator();
			list.positionIterator();
			while (this.iterator != null || list.iterator != null) { // ?
				if (this.iterator != null) {
					result.addLast(this.getIterator());
					this.iterator = this.iterator.next;
				}
				if (list.iterator != null) {
					result.addLast(list.getIterator());
					list.iterator = list.iterator.next;
				}
			}
			return result;
		}
	}

	/**
	 * Determines at which index the iterator is located Indexed from 0 to length -
	 * 1
	 * 
	 * @return the index number of the iterator
	 * @precondition iterator != null
	 * @throws NullPointerException when precondition is violated
	 */
	public int getIteratorIndex() throws NullPointerException {
		if (offEnd()) {
			throw new NullPointerException("getIteratorIndex: " + "Iterator is null!");
		}
		Node temp = first;
		int index = 0;
		while (temp != null) {
			if (temp.data.equals(iterator.data)) {
				break;
			}
			temp = temp.next;
			index++;
		}
		return index;
	}

	/**
	 * Searches the LinkedList for a given element's index
	 * 
	 * @param data the data whose index to locate
	 * @return the index of the data or -1 if the data is not contained in the
	 *         LinkedList
	 */
	public int findIndex(T data) {
		Node temp = first;
		int index = 0;
		while (temp != null) {
			if (temp.data.equals(data)) {
				return index;
			}
			temp = temp.next;
			index++;
		}
		return -1;
	}

	/**
	 * Advances the iterator to location within the LinkedList specified by the
	 * given index
	 * 
	 * @param index the index at which to place the iterator.
	 * @precondition iterator != null && 0 <= index <= length-1
	 * @throws NullPointerException when the precondition is violated
	 */
	public void advanceIteratorToIndex(int index) throws NullPointerException {
		if (offEnd()) {
			throw new NullPointerException("advanceIteratorToIndex: " + "Iterator is null!");
		} else if (index < 0 || index > length - 1) {
			throw new NullPointerException("advanceIteratorToIndex: " + "index is invalid");
		}
		positionIterator();
		for (int i = 0; i < index; i++) {
			iterator = iterator.next;
		}
	}
}
