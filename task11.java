package siaod1lab;

import java.util.Scanner;

 class Mnog {
	int a;
	int n;
	public Mnog(int a, int n) {
		this.a = a;
		this.n = n;
	}
	public void setA(int a) {
		this.a = a;
	}

	public int getA() {
		return this.a;
	}

	public void setN(int n) {
		this.n = n;
	}

	public int getN() {
		return this.n;
	}
}
  class Single<T> {

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
public class task11 {
	static void Add(Single<Mnog> o1, Single<Mnog> o2, Single<Mnog> o3) {
		int a1;
		int a2 ;
		for (int i = 0, j = 0; i < o1.size() && j < o2.size();) {
			if (o1.get(i).getN() > o2.get(j).getN()) {
				o3.add(new Mnog(o1.get(i).getA(), o1.get(i).getN()));
				i++;
				continue;
			}
			if (o1.get(i).getN() == o2.get(j).getN()) {
				o3.add(new Mnog(o1.get(i).getA()+o2.get(j).getA(), o1.get(i).getN()));
				i++;
				j++;
				continue;
			}
			if (o1.get(i).getN() < o2.get(j).getN()) {
				o3.add(new Mnog(o2.get(j).getA(), o2.get(j).getN()));
				j++;
				continue;
			}
			
		}

		for (int i = 0; i < o3.size(); i++) {
			System.out.print(o3.get(i).getA() + "x^" + o3.get(i).getN() + " ");
		}
	}

	public static void main(String[] args) {
		int a, n = 0;
		Scanner scn = new Scanner(System.in);
		Single<Mnog> num1 = new Single<Mnog>();
		Single<Mnog> num2 = new Single<Mnog>();
		Single<Mnog> sum = new Single<Mnog>();
		System.out.println("Ââåäèòå 1 ìíîãî÷ëåí");
		do {
			System.out.println("Ââåäèòå ai è còåïåíü x .");
			a = scn.nextInt();
			n = scn.nextInt();
			num1.add(new Mnog(a, n));
		} while (n != 0);
		System.out.println("Ââåäèòå 2 ìíîãî÷ëåí");
		do {
			System.out.println("Ââåäèòå ai è còåïåíü x .");
			a = scn.nextInt();
			n = scn.nextInt();
			num2.add(new Mnog(a, n));
		} while (n != 0);
		Add(num1, num2, sum);
	}

}
