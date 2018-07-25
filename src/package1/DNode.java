package package1;

import java.io.Serializable;

/**********************************************************************
 * Node class for a doubly linked list
 * 
 * @author Max Jensen and Monica Klosin
 * @version 1.0
 *********************************************************************/

public class DNode<E> implements Serializable {
	/** Generic variable to hold the node's data */
	private E data;

	/** The next node in the list */
	private DNode next;

	/** The previous node in the list */
	private DNode prev;

	/******************************************************************
	 * Default constructor that accepts parameters to set the data,
	 * prev, and next of the constructed node
	 * 
	 * @param data
	 *            The data contined within the node
	 * @param next
	 *            The next node in the list
	 * @param prev
	 *            The previous node in the list
	 *****************************************************************/
	public DNode(E data, DNode next, DNode prev) {
		this.data = data;
		this.next = next;
		this.prev = prev;
	}

	/******************************************************************
	 * Default constructor that sets the node's variables to null
	 *****************************************************************/
	public DNode() {
		data = null;
		next = null;
		prev = null;
	}

	/******************************************************************
	 * Getter for previous node
	 * 
	 * @return DNode The previous node
	 *****************************************************************/
	public DNode getPrev() {
		return prev;
	}

	/******************************************************************
	 * Setter for previous node
	 * 
	 * @param prev
	 *            The previous node
	 *****************************************************************/
	public void setPrev(DNode prev) {
		this.prev = prev;
	}

	/******************************************************************
	 * Getter for node's data
	 * 
	 * @return E The data within the node
	 *****************************************************************/
	public E getData() {
		return data;
	}

	/******************************************************************
	 * Setter for node's data
	 * 
	 * @param E
	 *            The data within the node
	 *****************************************************************/
	public void setData(E data) {
		this.data = data;
	}

	/******************************************************************
	 * Getter for next node
	 * 
	 * @return DNode The next node
	 *****************************************************************/
	public DNode getNext() {
		return next;
	}

	/******************************************************************
	 * Setter for next node
	 * 
	 * @param next
	 *            The next node
	 *****************************************************************/
	public void setNext(DNode next) {
		this.next = next;
	}
}
