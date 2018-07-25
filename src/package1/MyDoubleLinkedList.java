package package1;

import java.io.Serializable;

/**********************************************************************
 * Class that manages a doubly linked list
 * 
 * @author Max Jensen and Monica Klosin
 * @version 1.0
 *********************************************************************/
public class MyDoubleLinkedList<E> implements Serializable {

	/** The top/first node in the list */
	private DNode<E> top;

	/** The tail/last node in the list */
	private DNode<E> tail;

	/******************************************************************
	 * Default constructor that sets the top and tail of the doubly
	 * linked list to null
	 *****************************************************************/
	public MyDoubleLinkedList() {
		top = null;
		tail = null;
	}

	/******************************************************************
	 * Method that returns the size of the doubly linked list
	 * 
	 * @return Int The size of the list
	 *****************************************************************/
	public int size() {
		int count = 1;

		DNode temp = top;

		if (temp == null)
			return 0;

		else {
			while (temp.getNext() != null) {
				temp = temp.getNext();
				count++;
			}
		}

		return count;
	}

	/******************************************************************
	 * Method that removes all the contents of the doubly linked list
	 * by setting the top and tail to null
	 *****************************************************************/
	public void clear() {
		top = null;
		tail = null;
	}

	/******************************************************************
	 * Method that adds the passed Object to the list at the end
	 * 
	 * @param t
	 *            The data element for the new node
	 *****************************************************************/
	public void add(E t) {
		DNode<E> newNode = new DNode<E>(t, null, null);

		// If the list is empty adds the new node
		if (top == null) {
			tail = newNode;
			top = newNode;
		}

		// If the list is not empty, adds the new node at the end of
		// the list
		else {
			newNode.setPrev(tail);
			tail.setNext(newNode);
			tail = newNode;
		}
	}

	/******************************************************************
	 * Method that adds the passed Object to the list at the beginning
	 * 
	 * @param t
	 *            The data element for the new node
	 *****************************************************************/
	public void addFirst(E t) {
		DNode<E> newNode = new DNode<E>(t, null, null);

		// If the list is empty adds the new node
		if (top == null) {
			tail = newNode;
			top = newNode;
		}

		// If the list isn't empty adds the new node at the beginning
		// of the list
		else {
			newNode.setNext(top);
			top.setPrev(newNode);
			top = newNode;
		}
	}

	/******************************************************************
	 * Method that removes the node at the passed index
	 * 
	 * @param index
	 *            The index of the node to be removed
	 * @return E The node that was removed
	 * @throws IllegalArgumentException
	 *             If the passed index is less than 0 or greater than
	 *             the size of the list
	 *****************************************************************/
	public E remove(int index) {
		DNode<E> n1 = top;
		DNode<E> n2 = null;
		E returned = null;

		// If the passed index is less than 0 or greater than the size
		// of the list throws an exception
		if ((index < 0) || (index >= size())) {
			throw new IllegalArgumentException();
		} else {

			// If the list is one element long, removes the entire list
			if (top == tail) {
				returned = top.getData();
				top = null;
				tail = null;
			} else {

				// If the index is the last one in the list, removes
				// the node there
				if (index == (size() - 1)) {
					for (int i = 0; i < (index - 1); i++) {
						n1 = n1.getNext();
					}
					returned = n1.getData();
					n1.setNext(null);
					tail = n1;
				}

				else {

					// If the node is somewhere in the middle of the
					// list, handles its removal appropriately
					n1 = top;
					n2 = top;

					// gets to the index to set returned
					for (int i = 0; i < (index); i++) {
						n1 = n1.getNext();
					}
					returned = n1.getData();

					n1 = top;
					// gets to index-1
					for (int i = 0; i < (index - 1); i++) {
						n1 = n1.getNext();
					}
					// gets to index+1
					for (int i = 0; i < (index + 1); i++) {
						n2 = n2.getNext();
					}

					// Sets nodes appropriately
					n1.setNext(n2);
					n2.setPrev(n1);
				}
			}
		}
		return returned;
	}

	/******************************************************************
	 * Method that removes all the occurences of the passed Object in
	 * the list
	 * 
	 * @param t
	 *            The element to be removed
	 * @return boolean True if the element was successfully removed
	 *****************************************************************/
	public boolean removeAll(E t) {

		// If the list is empty returns false
		if (top == null || tail == null)
			return false;
		else {

			// If the list is not empty runs through the list, removing
			// any nodes that match the passed one
			int removeCount = 0;
			int index = 0;
			DNode<E> temp = top;
			for (int i = 0; i < size(); i++) {
				if (temp == t) {
					remove(index);
					removeCount++;
				}
				index++;
				temp = temp.getNext();
			}

			// Returns true if at least one element was removed
			if (removeCount > 0)
				return true;
			else
				return false;
		}
	}

	/******************************************************************
	 * Method that returns the list element at the passed index
	 * 
	 * @param index
	 *            The index of the node to be returned
	 *****************************************************************/
	public E get(int index) {
		DNode<E> temp = top;
		for (int i = 0; i < index; i++) {
			temp = temp.getNext();
		}
		return temp.getData();
	}

	/******************************************************************
	 * Method that finds the index of the first occurence of the passed
	 * Object in the list
	 * 
	 * @param t
	 *            The data element to find
	 * @return int The index of the found object, -1 if not found
	 *****************************************************************/
	public int find(E t) {
		DNode<E> temp = top;
		int index = 0;
		for (int i = 0; i < size(); i++) {
			if (temp == t) {
				return index;
			}
			temp = temp.getNext();
			index++;
		}
		return (-1);
	}
}
