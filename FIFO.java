package siaod1lab;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

import siaod1lab.Hash.HashTable;
import siaod1lab.Hash.Link;

//������� ����������� . ������� � �������� �� ������ �������� , ��������� �������� � ������ ����������� adr[0] - ����� ���������� . adr[1] - ����� �������� � ����������
//������
class El<E> {
	private E val;
	private El next;

	El() {
		val = null;
		next = null;
	}

	El(E val, El next) {
		this.val = val;
		this.next = next;
	}

	public E getVal() {
		return val;
	}

	public void setVal(E val) {
		this.val = val;
	}

	public El getNext() {
		return next;
	}

	public void setNext(El next) {
		this.next = next;
	}
}

public class FIFO<E> {
	private El lastMain; // ������ �� ��������� ������� ������� �������(tail)
	private El firstMain; // ������ �� ������ ������� ������� �������(head)
	private int mainSize;// ������ ������� �������
	private int nQueue; // ���������� �����������
	private El[] lasts;// ������ ������ ��������� ��������� �����������(tails)
	private El[] firsts;// ������ ������ ��������� ��������� �����������(tails)
	private int[] lastsSize;// ������� �����������
	private int order;// �� ����� ������� �� �����
	private int nFromMain;// ������� �������� ����� �� ������� ����������
	private final int maxQueueSize = 5; // ������������ ������ ������� �������
	private int mainPriority; // ��������� (���-������� ��������� ����� ����� ��
								// ������� ���������� �� ���� ���� ������)
								// �������
	private int weights[]; // ���� �����������
	private int availables[]; // ������� �������� ����� �� ������ �������

	// ��� - ������� ��������� ����� ������� , �
	public FIFO(int nQueue, int packSize/*
										 * ������� ��������� ������� � ����� ��
										 * ���� �������� �� 1 ���� ������
										 */,
			double mPrior/* ��������� ������� ������� */,
			double[] priorities/* ��������� ����������� */) throws ConcurrentModificationException {
		if (priorities.length != nQueue) // ��������������� ������������
											// ����������� ��������������
											// �����������
			throw new ConcurrentModificationException();
		this.mainPriority = (int) (packSize * mPrior); // ������� �����������
														// ���� ����� ���������
		this.weights = new int[nQueue]; // ������������� ������� �����
		for (int i = 0; i < nQueue; i++) {
			weights[i] = (int) (priorities[i] * packSize); // ���������� �����
															// �����������
		}
		this.availables = Arrays.copyOf(weights, nQueue); // ���������� �������
		order = -1; // ��������� �������� (������� �������)
		nFromMain = mainPriority; // ��������� �������� . ������� ���� ����� ��
									// ������� �������
		mainSize = 0; // ��������� ������ ������� �������
		firstMain = new El(); // ������� ���� ��� head ������1 ��������
		lastMain = firstMain; // head.next=tail
		this.nQueue = nQueue;
		lasts = new El[nQueue];// ������ �������� ��������� ���������
								// �����������
		firsts = new El[nQueue]; // ������ �������� ������ ��������� �����������
		lastsSize = new int[nQueue]; // ������ , � ������� ����������� ������
										// �����������
		// ������������� �������������� ��������
		for (int i = 0; i < nQueue; i++) {
			firsts[i] = new El();
			lasts[i] = firsts[i];
			lastsSize[i] = 0;
		}
	}

	public int size() {
		int res = 0;
		for (int i = 0; i < nQueue; i++) {
			res += lastsSize[i];
		}
		res += mainSize;
		return res;
	}

	public boolean isEmpty() {
		if (mainSize == 0) {
			for (int i = 0; i < nQueue; i++) {
				if (lastsSize[i] != 0) {
					return false;
				}
			}
			return true;
		} else
			return false;
	}

	// ����� �� ��������
	private int[] find(Object o) {
		int k = 0;
		// ����� � ������� ����������
		for (El curr = firstMain; !curr.equals(lastMain); curr = curr.getNext()) {
			if (curr.getNext().getVal().equals(o))
				return new int[] { -1, k };
			k++;
			// ������ � ����������
		}
		// ����� � �������������� �����������
		for (int i = 0; i < nQueue; i++) {
			k = 0;
			for (El curr = firsts[i]; !curr.equals(lasts[i]); curr = curr.getNext()) {
				if (curr.getNext().getVal().equals(o))
					return new int[] { i, k };
				k++;
			}
		}
		return new int[] { -1, -1 }; // �� �����
	}

	// ���������� �� ������� ���� � �����-�� ����������
	public boolean contains(Object o) {
		return (find(o)[1] != -1);
	}

	//
	public boolean remove(Object o) {
		int adr[] = find(o);// ���������� ��������
		if (adr[1] == -1) // ���� ��� ���
			return false;
		El curr; // ��������� ����
		boolean lst = false; // ���������� , ����������� �� ������� � ����������
		if (adr[0] == -1) { // ���� �������� � ������� ����������
			curr = firstMain; // ������ ������ �� ������� ����������
			mainSize--;// ��������� ������ �������
			if (adr[1] == mainSize) // ���� �� ��������� �������
				lst = true;
		} else {
			// -1 (������� ����������) 0+(����� ��� ����������)
			curr = firsts[adr[0]];
			lastsSize[adr[0]]--;
			if (adr[1] == lastsSize[adr[0]])
				lst = true; // ���� ������� ��������� � ��������������
							// ����������
		}
		// ������ ������� � ����������� adr[1](����� �������� � ����������)
		for (int i = 0; i < adr[1]; i++) {
			curr = curr.getNext();
		}
		curr.setNext(curr.getNext().getNext()); // �������� ��������
		if (lst) {// ���� ��������� ������� - ���������...
			if (adr[0] == -1) { // ...� ������� ����������
				lastMain = curr;
			} else { // ...� ����� �� �������������� �����������
				lasts[adr[0]] = curr;
			}
		}

		return true;
	}

	// ����� � ����������� ����������
	public void clear() {
		order = -1;
		nFromMain = mainPriority;
		availables = Arrays.copyOf(weights, nQueue);
		mainSize = 0;
		lastMain = firstMain;
		firstMain.setNext(null);
		for (int i = 0; i < nQueue; i++) {
			firsts[i].setNext(null);
			lasts[i] = firsts[i];
			lastsSize[i] = 0;
		}
	}

	// ���������� � ����������� � ����� �� ����������� ����� ���������
	public void add(Object e) throws ConcurrentModificationException {
		El addition = new El(e, null); // ����������� �������
		if (mainSize < maxQueueSize) { // ���� ������ ������� ���������� ������
										// ����������� �����������(� �������
										// ���������� ���� �����)
			lastMain.setNext(addition);
			lastMain = addition;
			mainSize++;
			return;
		} else {
			// ��������� , ��� ���� �����
			for (int i = 0; i < nQueue; i++) {
				if (lastsSize[i] < maxQueueSize) {
					lasts[i].setNext(addition);
					lasts[i] = addition;
					lastsSize[i]++;
					return;
				}
			}
			throw new ConcurrentModificationException(); // ��� �����
		}
	}

	// ����� ������������ ������ ��� ����������(���������� �� �����������)
	public boolean offer(Object e) {
		try {
			add(e);
			return true;
		} catch (ConcurrentModificationException ex) {
			return false;
		}
	}

	// �������� ������ �������� � ������� �������
	public Object remove() throws NoSuchElementException {
		if (size() == 0)
			throw new NoSuchElementException();
		Object res;
		for (;;) {
			if (order == -1) {// ���� ����� �� ������� �������
				if (mainSize != 0) { // � ������� ������� ���� ��������
					res = firstMain.getNext().getVal();
					mainSize--; // ��������� ������ ������� ����������
					firstMain.setNext(firstMain.getNext().getNext()); // ��������
																		// ��������
					nFromMain--; // ���������� ��������� ��� ������
					// ���� �� �������� ���������
					if (mainSize == 0) {
						lastMain = firstMain;
					}
					// ����� ����� �� ��������� ����������
					if (nFromMain == 0) {
						nFromMain = mainPriority;
						order++;
					}
					return res;
				} else {// � ������� ������� ��� ���������
					order++;
				}
			} else {// ���� ����� �� �������� ��������
				if (lastsSize[order] != 0) {// ���� ��������
					lastsSize[order]--;
					res = firsts[order].getNext().getVal();
					firsts[order].setNext(firsts[order].getNext().getNext()); // ��������
																				// ��
																				// ����������
					if (lastsSize[order] == 0) // ���� ����������� �������� �
												// ����������
						lasts[order] = firsts[order];
					availables[order]--;
					// ���� ��� ������ �� ����� ����� �� ���� ���������� .
					// ������ ��� ���� ����������� ���������� ��������
					// availables �� ��������������
					if (availables[order] == 0) {
						availables[order] = weights[order];
						order++;
						if (order == nQueue - 1) {
							order = -1;
						}
					}
					return res;
				} else {// ��� ���������
					if (order == nQueue - 1) {
						order = -1;
					} else
						order++; // ���� � ��������� �����������
				}
			}
		}
	}

	// ���������� ������ �������
	public Object peek() throws NoSuchElementException {
		if (size() == 0)
			throw new NoSuchElementException();
		Object res;
		for (;;) {
			if (order == -1) {// ���� ����� �� ������� �������
				if (mainSize != 0) { // � ������� ������� ���� ��������
					res = firstMain.getNext().getVal();
					return res;
				} else {// � ������� ������� ��� ���������
					nFromMain = mainPriority;
					order++;
				}
			} else {// ���� ����� �� �������� ��������
				if (lastsSize[order] != 0) {// ���� ��������
					res = firsts[order].getNext().getVal();
					return res;
				} else {// ��� ���������
					if (order == nQueue - 1) {
						order = -1;
					} else
						order++;
				}
			}
		}
	}

	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		FIFO f = new FIFO(3,5,0.4,new double[]{0.2,0.2,0.2});
      //��������� ������� � ����� ������� � ����� ��������������� ������������. �� ���� ���� ������ �� �� ����� ����������� 5 ���������: 2 �� ������� � �� ������ �� ��������������
		while (true) {
			String s = "";
			System.out.println("������� ����� �������� , ��� �� ������ �������");
			System.out.println(
					"1) �������� 2) ����� ����������������� �������� �� ��������(����������) 3) ������� ������� �� �������� 4) ������� ������� �� ������� �����  ");
			String choice = scn.nextLine();
			switch (choice) {
			case "1":
				System.out.println("������� ������� , ������� ������ ��������");
				s = scn.nextLine();
				f.add(s.trim());
				System.out.println("������� ������� ��������");
				break;
			case "2":
				System.out.println("������� �������� �������� , ������� �� ������ �����");
				s = scn.nextLine();
				s = s.trim();
				if (f.contains(s) == false) {
					System.out.println("������ ����� ������ �������");
				} else {
					int arr[] = Arrays.copyOf(f.find(s), 2);
					if (arr[0] == -1) {
						System.out.println("������� ������ � ������� ���������� � ������� " + arr[1]);
					} else {
						System.out.println(
								"������� ������ � ���������� � ������� " + arr[0] + " � ������� �������� " + arr[1]);
					}
				}
				break;
			case "3":
				System.out.println("������� �������� �������� ������� �� �������� �������");
				s=scn.nextLine();
				s=s.trim();
				if (f.remove(s)==true){
					System.out.println("������ ������� ������");
				} else{
					System.out.println("������� �� ������");
				}
				break;
			case "4":
				System.out.println("������ �������  "+f.remove());
				break;
			default:
				System.out.println("�������� ����� ������");
			}
		}
	}

}
