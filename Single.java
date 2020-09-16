package siaod1lab;


public class Single<T> {

	private static class Node<T> {
		T item;
		Node<T> next;

		public Node(T item, Node<T> next) {
			this.item = item;
			this.next = next;
		}
	}

	private int size = 0;
	private final Node<T> first;

	public Single() {
		first = new Node<T>(null, null);
	}

	public Node<T> move(int pos) {
		Node<T> n = first;
		for (int i = 0; i <= pos; i++) {
			n = n.next;
		}
		return n;
	}

	public void add(int pos, T elt) {
		Node<T> n = move(pos - 1);
		Node<T> newNode = new Node(elt, null);
		newNode.next = n.next;
		n.next = newNode;
		size++;
	}

	public void add(T elt) {
		Node n = move(size - 1);
		Node newNode = new Node(elt, null);
		newNode.next = n.next;
		n.next = newNode;
		size++;
	}

	public T remove(int pos) {
		Node<T> n = move(pos - 1);
		T item = n.next.item;
		n.next = n.next.next;
		size--;
		return item;
	}

	public T set(int pos, T elt) {
		if (pos < 0 || pos > size - 1) {
			throw new IndexOutOfBoundsException(pos + " is out of bounds");
		}
		Node<T> n = move(pos);
		T prev = n.item;
		n.item = elt;
		return prev;
	}
	public T get(int pos) {
		Node<T> n = move(pos);
	    return n.item;
	}
	public int size() {
		return size;
	}
}
