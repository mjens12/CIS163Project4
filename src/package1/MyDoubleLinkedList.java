package package1;

import java.io.Serializable;

public class MyDoubleLinkedList<E> implements Serializable {
	private DNode<E> top;
	private DNode<E> tail;

	public MyDoubleLinkedList() {
		top = null;
		tail = null;
	}

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

	public void clear() {
		top = null;
		tail = null;
	}

	public void add(E t) {
		// add at the end.
		DNode<E> newNode = new DNode<E>(t, null, null);
		if (top == null) {
			tail = newNode;
			top = newNode;
		} else {
			newNode.setPrev(tail);
			tail.setNext(newNode);
			tail = newNode;
		}
	}

	public void addFirst(E t) {
		DNode<E> newNode = new DNode<E>(t, null, null);
		if (top == null) {
			tail = newNode;
			top = newNode;
		} else {
			newNode.setNext(top);
			top.setPrev(newNode);
			top = newNode;
		}
	}

	public E remove(int index) {
		// remove first occurrence.
		DNode<E> n1 = top;
		DNode<E> n2 = null;
		E returned = null;

		if ((index < 0) || (index >= size())) {
			throw new IllegalArgumentException();
		} else {
			if (top == tail) {
				returned = top.getData();
				top = null;
				tail = null;
			} else {
				if (index == (size() - 1)) {
					// gets to next to last index
					for (int i = 0; i < (index - 1); i++) {
						n1 = n1.getNext();
					}
					returned = n1.getData();
					n1.setNext(null);
					tail = n1;
				}

				else {
					n1 = top;
					n2 = top;
					// gets to the index to set returned
					for (int i = 0; i < (index); i++) {
						n1 = n1.getNext();
					}
					returned = n1.getData();

					n1 = top;

					for (int i = 0; i < (index - 1); i++) {
						n1 = n1.getNext();
					}
					// gets to index+1
					for (int i = 0; i < (index + 1); i++) {
						n2 = n2.getNext();
					}
					n1.setNext(n2);
					n2.setPrev(n1);
				}
			}
		}
		return returned;
	}

	public boolean removeAll(E t) {
		// removes all instances of the passed object
		return true;
	}

	public E get(int index) {
		DNode<E> temp = top;
		for (int i = 0; i < index; i++) {
			temp = temp.getNext();
		}
		return temp.getData();
	}

	public int find(E t) {
		// return index if found, -1 otherwise
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
