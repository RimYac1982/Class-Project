/**
 * BST.java
 * @author Rimon Yacoub
 * CIS 22C, Final Project
 */

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class BST<T extends Comparable<T>> {
	private class Node {
		private T data;
		private Node left;
		private Node right;

		public Node(T data) {
			this.data = data;
			left = null;
			right = null;
		}
	}

	private Node root;

	/*** CONSTRUCTORS ***/

	/**
	 * Default constructor for BST sets root to null
	 */
	public BST() {
		root = null;
	}

	/**
	 * Copy constructor for BST
	 * 
	 * @param bst the BST to make a copy of
	 */
	public BST(BST<T> bst) {
		if (bst == null) {
			return;
		}
		if (bst.getSize() == 0) {
			root = null;
		} else {
			copyHelper(bst.root);
		}
	}

	/**
	 * Helper method for copy constructor
	 * 
	 * @param node the node containing data to copy
	 */
	private void copyHelper(Node node) {
		if (node == null) {
			return;
		} else {
			insert(node.data);
			copyHelper(node.left);
			copyHelper(node.right);

		}
	}

	/**
	 * Creates a BST of minimal height from an array of values
	 * 
	 * @param array the list of values to insert
	 * @precondition array must be sorted in ascending order
	 * @throws IllegalArgumentException when the array is unsorted
	 */
	public BST(T[] array) throws IllegalArgumentException {
		if (array == null) {
			return;
		}
		if (array.length == 0) {
			return;
		}
		if (!isSorted(array)) {
			throw new IllegalArgumentException("BST(T[] array): Array is not sorted");
		} else {
			root = arrayHelper(0, array.length - 1, array);
		}
	}

	/**
	 * Private helper method for array constructor to check for a sorted array
	 * 
	 * @param array the array to check
	 * @return whether the array is sorted
	 */
	private boolean isSorted(T[] array) {
		for (int i = 0; i < array.length - 1; i++) {
			if (array[i].compareTo(array[i + 1]) > 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Recursive helper for the array constructor
	 * 
	 * @param begin beginning array index
	 * @param end   ending array index
	 * @param array array to search
	 * @return the newly created Node
	 */

	private Node arrayHelper(int begin, int end, T[] array) {
		if (begin > end) {
			return null;
		}
		int mid = begin + (end - begin) / 2;
		Node temp = new Node(array[mid]);
		temp.left = arrayHelper(begin, mid - 1, array);
		temp.right = arrayHelper(mid + 1, end, array);
		return temp;
	}

	/*** ACCESSORS ***/

	/**
	 * Returns the data stored in the root
	 * 
	 * @precondition !isEmpty()
	 * @return the data stored in the root
	 * @throws NoSuchElementException when precondition is violated
	 */
	public T getRoot() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException("getRoot(): the tree is empty");
		}
		return root.data;
	}

	/**
	 * Determines whether the tree is empty
	 * 
	 * @return whether the tree is empty
	 */
	public boolean isEmpty() {
		return this.root == null;
	}

	/**
	 * Returns the current size of the tree (number of nodes)
	 * 
	 * @return the size of the tree
	 */
	public int getSize() {
		return getSize(root);
	}

	/**
	 * Helper method for the getSize method
	 * 
	 * @param node the current node to count
	 * @return the size of the tree
	 */

	private int getSize(Node node) {
		if (node == null)
			return 0;
		else
			return 1 + getSize(node.left) + getSize(node.right);
	}

	/**
	 * Returns the height of tree by counting edges.
	 * 
	 * @return the height of the tree
	 */
	public int getHeight() {
		if (root == null) {
			return -1;
		} else {
			return getHeight(root);
		}
	}

	/**
	 * Helper method for getHeight method
	 * 
	 * @param node the current node whose height to count
	 * @return the height of the tree
	 */
	private int getHeight(Node node) {
		if (node == null) {
			return -1;
		}
		int rCount = getHeight(node.right);
		int lCount = getHeight(node.left); // 0

		if (rCount > lCount) {
			return rCount + 1;
		} else {
			return lCount + 1;
		}
	}

	/**
	 * Returns the smallest value in the tree
	 * 
	 * @precondition !isEmpty()
	 * @return the smallest value in the tree
	 * @throws NoSuchElementException when the precondition is violated
	 */
	public T findMin() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException("findMin(): the tree is empty");
		}
		return findMin(this.root);
	}

	/**
	 * Helper method to findMin method
	 * 
	 * @param node the current node to check if it is the smallest
	 * @return the smallest value in the tree
	 */

	private T findMin(Node node) {
		if (node.left == null) {
			return node.data;
		}
		return findMin(node.left);
	}

	/**
	 * Returns the largest value in the tree
	 * 
	 * @precondition !isEmpty()
	 * @return the largest value in the tree
	 * @throws NoSuchElementException when the precondition is violated
	 */
	public T findMax() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException("findMax(): the tree is empty");
		}
		return findMax(this.root);

	}

	/**
	 * Helper method to findMax method
	 * 
	 * @param node the current node to check if it is the largest
	 * @return the largest value in the tree
	 */

	private T findMax(Node node) {
		if (node.right == null) {
			return node.data;
		}
		return findMax(node.right);
	}

	/*** MUTATORS ***/

	/**
	 * Inserts a new node in the tree
	 * 
	 * @param data the data to insert
	 */
	public void insert(T data) {
		if (root == null) {
			root = new Node(data);
		} else {
			insert(data, root);
		}
	}

	/**
	 * Helper method to insert Inserts a new value in the tree
	 * 
	 * @param data the data to insert
	 * @param node the current node in the search for the correct location in which
	 *             to insert
	 */
	private void insert(T data, Node node) {
		if (data.compareTo(node.data) <= 0) {
			if (node.left == null) {
				node.left = new Node(data);
			} else {
				insert(data, node.left);
			}
		} else {
			if (node.right == null) {
				node.right = new Node(data);
			} else {
				insert(data, node.right);
			}
		}
	}

	/**
	 * Removes a value from the BST
	 * 
	 * @param data the value to remove
	 */
	public void remove(T data) {
		root = remove(data, root);
	}

	/**
	 * Helper method to the remove method
	 * 
	 * @param data the data to remove
	 * @param node the current node
	 * @return an updated reference variable
	 */

	private Node remove(T data, Node node) {
		if (node == null) {
			return node;
		} else if (data.compareTo(node.data) < 0) {
			node.left = remove(data, node.left);
		} else if (data.compareTo(node.data) > 0) {
			node.right = remove(data, node.right);

		} else {
			if (node.left == null && node.right == null) {
				node = null;
			} else if (node.left != null && node.right == null) {
				node = node.left;
			} else if (node.right != null && node.left == null) {
				node = node.right;
			} else {
				node.data = findMin(node.right);
				node.right = remove(node.data, node.right);
			}
		}
		return node;
	}

	/*** ADDITIONAL OPERATIONS ***/

	/**
	 * Searches for a specified value in the tree
	 * 
	 * @param data the value to search for
	 * @return whether the value is stored in the tree
	 */
	public boolean search(T data) {
		if (root == null) {
			return false;
		} else {
			return search(data, root);
		}
	}

	/**
	 * Helper method for the search method
	 * 
	 * @param data the data to search for
	 * @param node the current node to check
	 * @return whether the data is stored in the tree
	 */
	private boolean search(T data, Node node) {
		if (data.compareTo(node.data) == 0) {
			return true;
		} else if (data.compareTo(node.data) < 0) {
			if (node.left == null) {
				return false;
			} else { 
				return search(data, node.left);
			}
		} else {
			if (node.right == null) {
				return false;				
			} else {
				return search(data, node.right);
			}
		}
	}

	/**
	 * Returns a String containing the data in post order
	 * 
	 * @return a String of data in post order
	 */
	public String preOrderString() {
		StringBuilder temp = new StringBuilder();
		preOrderString(root, temp);
		return temp + "\n";
	}

	/**
	 * Helper method to preOrderString Inserts the data in pre order into a String
	 * 
	 * @param node     the current Node
	 * @param preOrder a String containing the data
	 */
	private void preOrderString(Node node, StringBuilder preOrder) {
		if (node == null) {
			return;
		}
		preOrder.append(node.data + " ");
		preOrderString(node.left, preOrder);
		preOrderString(node.right, preOrder);

	}

	/**
	 * Returns a String containing the data in order
	 * 
	 * @return a String of data in order
	 */
	public String inOrderString() {
		StringBuilder temp = new StringBuilder();
		inOrderString(root, temp);
		return temp + "\n";

	}

	/**
	 * Helper method to inOrderString Inserts the data in order into a String
	 * 
	 * @param node    the current Node
	 * @param inOrder a String containing the data
	 */

	private void inOrderString(Node node, StringBuilder inOrder) {
		if (node == null) {
			return;
		}
		inOrderString(node.left, inOrder);
		inOrder.append(node.data + " ");
		inOrderString(node.right, inOrder);
	}

	/**
	 * Returns a String containing the data in order
	 * 
	 * @return a String of data in order
	 */
	public void inOrderList(ArrayList<T> List) {
		inOrderList(root, List);
		return;

	}

	/**
	 * Helper method to inOrderString Inserts the data in order into a String
	 * 
	 * @param node    the current Node
	 * @param inOrder a String containing the data
	 */

	private void inOrderList(Node node, ArrayList<T> List) {
		if (node == null) {
			return;
		}
		inOrderList(node.left, List);
		List.add(node.data);
		inOrderList(node.right, List);
	}
	/**
	 * Returns a String containing the data in post order
	 * 
	 * @return a String of data in post order
	 */
	public String postOrderString() {
		if (root == null) {
			return "\n";
		}
		StringBuilder temp = new StringBuilder();
		postOrderString(root, temp);
		return temp + "\n";
	}

	/**
	 * Helper method to postOrderString Inserts the data in post order into a String
	 * 
	 * @param node      the current Node
	 * @param postOrder a String containing the data
	 */

	void postOrderString(Node node, StringBuilder postOrder) {
		if (node == null) {
			return;
		}
		postOrderString(node.left, postOrder);
		postOrderString(node.right, postOrder);
		postOrder.append(node.data + " ");
	}

	/**
	 * Returns the data of the Node who is the shared precursor to the two Nodes
	 * containing the given data If the either data1 or data2 is a duplicate value,
	 * the method will find the precursor of the duplicate with greatest height
	 * 
	 * @param data1 the data contained in one Node of the tree
	 * @param data2 the data contained in one Node of the tree
	 * @return the data stored by the shared precursor or null if no precursor
	 *         exists.
	 * @precondition data1 and data2 must exist in the BST
	 * @throws IllegalArgumentException when one or both values do not exist in the
	 *                                  BST
	 */
	public T sharedPrecursor(T data1, T data2) throws IllegalArgumentException {
		if (search(data1) == false || search(data2) == false) {
			throw new IllegalArgumentException(
					"sharedPrecursor(T data1, T data2): both " + "values do not exist in the BST");
		}
		return sharedPrecursor(data1, data2, root);
	}

	/**
	 * Private helper method to sharedPrecursor, which recursively locates the
	 * shared precursor
	 * 
	 * @param data1     the data contained in one Node of the tree
	 * @param data2     the data contained in one Node of the tree
	 * @param currLevel the current Node
	 * @return the data stored by the shared precursor
	 */
	private T sharedPrecursor(T data1, T data2, Node currLevel) {
		if (currLevel == null) {
			return null;
		}
		if (data1.compareTo(currLevel.data) < 0 && data2.compareTo(currLevel.data) < 0) {
			return sharedPrecursor(data1, data2, currLevel.left);
		}
		if (data1.compareTo(currLevel.data) > 0 && data2.compareTo(currLevel.data) > 0) {
			return sharedPrecursor(data1, data2, currLevel.right);
		}
		return currLevel.data;

	}

}