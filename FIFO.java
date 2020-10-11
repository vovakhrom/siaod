package siaod1lab;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

import siaod1lab.Hash.HashTable;
import siaod1lab.Hash.Link;


//список
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
	private El lastMain; // ссылка на последний элемент главной очереди(tail)
	private El firstMain; // ссылка на первый элемент главной очереди(head)
	private int mainSize;// размер главной очереди
	private int nQueue; // количество подочередей
	private El[] lasts;// массив ссылок последних элементов подочередей
	private El[] firsts;// массив ссылок первых элементов подочередей
	private int[] lastsSize;// размеры подочередей
	private int order;// из какой очереди мы берем
	private int nFromMain;// сколько осталось взять из главной подочереди
	private final int maxQueueSize = 5; // максимальный размер главной очереди
	private int mainPriority; // приоритет (вес-сколько элементов нужно брать из
								// главной подочереди за один цикл взятия)
								// главной
	private int weights[]; // веса подочередей
	private int availables[]; // сколько осталось взять из каждой очереди

	// вес - сколько элементов будут браться , а
	public FIFO(int nQueue, int packSize/*
										 * сколько элементов берется в сумме из
										 * всех очередей за 1 цикл взятия
										 */,
			double mPrior/* приоритет главной очереди */,
			double[] priorities/* приоритет подочередей */) throws ConcurrentModificationException {
		if (priorities.length != nQueue) // неоднозначность соответствия
											// приоритетов дополнительных
											// подочередей
			throw new ConcurrentModificationException();
		this.mainPriority = (int) (packSize * mPrior); // формула определения
														// веса через приоритет
		this.weights = new int[nQueue]; // инициализация массива весов
		for (int i = 0; i < nQueue; i++) {
			weights[i] = (int) (priorities[i] * packSize); // заполнение весов
															// подочередей
		}
		this.availables = Arrays.copyOf(weights, nQueue); // заполнение массива
		order = -1; // начальное значение (главная очередь)
		nFromMain = mainPriority; // начальное значение . Сколько надо брать из
									// главной очереди
		mainSize = 0; // начальный размер главной очереди
		firstMain = new El(); // создаем узел для head главно1 очередиы
		lastMain = firstMain; // head.next=tail
		this.nQueue = nQueue;
		lasts = new El[nQueue];// массив значений последних элементов
								// подочередей
		firsts = new El[nQueue]; // массив значений первых элементов подочередей
		lastsSize = new int[nQueue]; // массив , в котором указывается размер
										// подочередей
		// инициализация дополнительных очередей
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

	// найти по значению
	private int[] find(Object o) {
		int k = 0;
		// поиск в главной подочереди
		for (El curr = firstMain; !curr.equals(lastMain); curr = curr.getNext()) {
			if (curr.getNext().getVal().equals(o))
				return new int[] { -1, k };
			k++;
			// индекс в подочереди
		}
		// поиск в дополнительных подочередях
		for (int i = 0; i < nQueue; i++) {
			k = 0;
			for (El curr = firsts[i]; !curr.equals(lasts[i]); curr = curr.getNext()) {
				if (curr.getNext().getVal().equals(o))
					return new int[] { i, k };
				k++;
			}
		}
		return new int[] { -1, -1 }; // не нашло
	}

	// содержится ли элемент хоть в какой-то подочереди
	public boolean contains(Object o) {
		return (find(o)[1] != -1);
	}

	//
	public boolean remove(Object o) {
		int adr[] = find(o);// координаты элемента
		if (adr[1] == -1) // если его нет
			return false;
		El curr; // начальный узел
		boolean lst = false; // показывает , полеследний ли элемент в подочереди
		if (adr[0] == -1) { // если значение в главной подочереди
			curr = firstMain; // начием искать из главной подочереди
			mainSize--;// уменьшаем размер заранее
			if (adr[1] == mainSize) // флаг на последний элемент
				lst = true;
		} else {
			// -1 (главная подочередь) 0+(номер доп подочереди)
			curr = firsts[adr[0]];
			lastsSize[adr[0]]--;
			if (adr[1] == lastsSize[adr[0]])
				lst = true; // если элемент последний в дополнительной
							// подочереди
		}
		// нужный элемент в подоочереди adr[1](номер элемента в подочереди)
		for (int i = 0; i < adr[1]; i++) {
			curr = curr.getNext();
		}
		curr.setNext(curr.getNext().getNext()); // удаление элемента
		if (lst) {// если удаляемый элемент - последний...
			if (adr[0] == -1) { // ...в главной подочереди
				lastMain = curr;
			} else { // ...в одной из дополнительных подочередей
				lasts[adr[0]] = curr;
			}
		}

		return true;
	}

	// сброс к изначальным настройкам
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

	// добавление и определение в какую из подочередей будем добавлять
	public void add(Object e) throws ConcurrentModificationException {
		El addition = new El(e, null); // добавляемый элемент
		if (mainSize < maxQueueSize) { // если размер главной подочереди меньше
										// максимально допустимого(в главной
										// подочереди есть место)
			lastMain.setNext(addition);
			lastMain = addition;
			mainSize++;
			return;
		} else {
			// добавляем , где есть место
			for (int i = 0; i < nQueue; i++) {
				if (lastsSize[i] < maxQueueSize) {
					lasts[i].setNext(addition);
					lasts[i] = addition;
					lastsSize[i]++;
					return;
				}
			}
			throw new ConcurrentModificationException(); // нет места
		}
	}

	// метод отлавливания ошибок при добавлении(добавления по возможности)
	public boolean offer(Object e) {
		try {
			add(e);
			return true;
		} catch (ConcurrentModificationException ex) {
			return false;
		}
	}

	// получаем первое значение и удаляем элемент
	public Object remove() throws NoSuchElementException {
		if (size() == 0)
			throw new NoSuchElementException();
		Object res;
		for (;;) {
			if (order == -1) {// надо брать из главной очереди
				if (mainSize != 0) { // в главной очереди есть элементы
					res = firstMain.getNext().getVal();
					mainSize--; // уменьшаем размер главной подочереди
					firstMain.setNext(firstMain.getNext().getNext()); // удаление
																		// значения
					nFromMain--; // количество элементов для взятия
					// если не осталось элементов
					if (mainSize == 0) {
						lastMain = firstMain;
					}
					// нужно брать из следующей подочереди
					if (nFromMain == 0) {
						nFromMain = mainPriority;
						order++;
					}
					return res;
				} else {// в главной очереди нет элементов
					order++;
				}
			} else {// надо брать из побочных очередей
				if (lastsSize[order] != 0) {// есть элементы
					lastsSize[order]--;
					res = firsts[order].getNext().getVal();
					firsts[order].setNext(firsts[order].getNext().getNext()); // удаление
																				// из
																				// подочереди
					if (lastsSize[order] == 0) // если закончились элементы в
												// подочереди
						lasts[order] = firsts[order];
					availables[order]--;
					// если нам больше не нужно брать из этой подочереди .
					// Значит для этой подоочереди возвращаем значение
					// availables на первоночальное
					if (availables[order] == 0) {
						availables[order] = weights[order];
						order++;
						if (order == nQueue - 1) {
							order = -1;
						}
					}
					return res;
				} else {// нет элементов
					if (order == nQueue - 1) {
						order = -1;
					} else
						order++; // идем к следующей подоочереди
				}
			}
		}
	}

	// возвращает первый элемент
	public Object peek() throws NoSuchElementException {
		if (size() == 0)
			throw new NoSuchElementException();
		Object res;
		for (;;) {
			if (order == -1) {// надо брать из главной очереди
				if (mainSize != 0) { // в главной очереди есть элементы
					res = firstMain.getNext().getVal();
					return res;
				} else {// в главной очереди нет элементов
					nFromMain = mainPriority;
					order++;
				}
			} else {// надо брать из побочных очередей
				if (lastsSize[order] != 0) {// есть элементы
					res = firsts[order].getNext().getVal();
					return res;
				} else {// нет элементов
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
      //создается очередь с одной главной и тремя дополнительными подочередями. За одни цикл взятия из неё будет извлекаться 5 элементов: 2 из главной и по одному из дополнительных
		while (true) {
			String s = "";
			System.out.println("Введите номер операции , что вы хотите сделать");
			System.out.println(
					"1) Добавить 2) Найти месторасположение элемента по значению(координаты) 3) удалить элемент по значению 4) удалить элемент по правилу стека  ");
			String choice = scn.nextLine();
			switch (choice) {
			case "1":
				System.out.println("Введите элемент , который хотите добавить");
				s = scn.nextLine();
				f.add(s.trim());
				System.out.println("Элемент успешно добавлен");
				break;
			case "2":
				System.out.println("Введите значение элемента , который вы хотите найти");
				s = scn.nextLine();
				s = s.trim();
				if (f.contains(s) == false) {
					System.out.println("Нельзя найти данный элемент");
				} else {
					int arr[] = Arrays.copyOf(f.find(s), 2);
					if (arr[0] == -1) {
						System.out.println("Элемент найден в главной подочереди с номером " + arr[1]);
					} else {
						System.out.println(
								"Элемент найден в подочереди с номером " + arr[0] + " и номером элемента " + arr[1]);
					}
				}
				break;
			case "3":
				System.out.println("Введите значение элемента которое вы хотетите удалить");
				s=scn.nextLine();
				s=s.trim();
				if (f.remove(s)==true){
					System.out.println("Элмент успешно удален");
				} else{
					System.out.println("Элемент не найден");
				}
				break;
			case "4":
				System.out.println("Удален элемент  "+f.remove());
				break;
			default:
				System.out.println("Неверный номер введен");
			}
		}
	}

}
