package package1;

public class DNode<E> {
	private E data;
	private DNode next;
	private DNode prev;

	public DNode getPrev() {
		return prev;
	}

	public void setPrev(DNode prev) {
		this.prev = prev;
	}

	public DNode(E data, DNode next, DNode prev) {
		this.data = data;
		this.next = next;
		this.prev = prev;
	}

	public DNode() {
		data = null;
		next = null;
		prev = null;
	}

	public E getData() {
		return data;
	}

	public void setData(E data) {
		this.data = data;
	}

	public void setNext(DNode next) {
		this.next = next;
	}

	public DNode getNext() {
		return next;
	}
}
