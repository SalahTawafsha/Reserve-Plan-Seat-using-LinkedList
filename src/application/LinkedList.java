package application;

public class LinkedList<T extends Comparable<T>> {
	private Node<T> head;

	public boolean insert(T data) {
		Node<T> add = new Node<T>(data), prev = null, curr = head;

		for (; curr != null && curr.getData().compareTo(data) <= 0; prev = curr, curr = curr.getNext())
			if (curr.getData().compareTo(data) == 0)
				return false;

		if (head == null) {
			head = add;
		} else if (curr == head) {
			add.setNext(head);
			head = add;
		} else {
			add.setNext(curr);
			prev.setNext(add);
		}
		return true;

	}

	public Node<T> search(T data) {
		Node<T> curr = head;
		while (curr != null && curr.getData().compareTo(data) <= 0) {
			if (curr.getData().compareTo(data) == 0)
				return curr;
			curr = curr.getNext();
		}
		return null;
	}

	public void remove(T data) throws Exception {
		Node<T> prev = null, curr = head;

		if (head == null)
			throw new Exception("List is Empty");

		for (; curr != null && curr.getData().compareTo(data) < 0; prev = curr, curr = curr.getNext())
			;

		if (curr == null) {
			throw new Exception("data Not Exist !");
		} else if (curr.getData().compareTo(data) == 0 && prev != null) {
			prev.setNext(curr.getNext());
		} else if (curr.getData().compareTo(data) == 0) {
			head = curr.getNext();
		} else {
			throw new Exception("data Not Exist !");
		}
	}

	public int length() {
		return length(head);
	}

	private int length(Node<T> curr) {
		if (curr == null)
			return 0;
		else
			return 1 + length(curr.getNext());
	}

	@Override
	public String toString() {
		Node<T> curr = head;
		String s = "Head" + " -> ";
		while (curr != null) {
			s += curr.getData() + " -> ";
			curr = curr.getNext();
		}
		s += "NULL";
		return s;
	}

	public Node<T> getHead() {
		return head;
	}
}
