package siaod1lab;

import java.util.InputMismatchException;
import java.util.Scanner;

//Класс-человек
class Person {
	private String name; // Имя
	private String surname; // Фамилия
	private String patronym; // Отчество
	private String phoneNumber; // Номер телефона

	public Person next; // Ссылка на следующего

	// Конструктор
	public Person(String name, String surname, String patronym, String phoneNumber) throws InputMismatchException {
		this.name = name;
		this.surname = surname;
		this.patronym = patronym;
		// Проверка ввода номера телефона
		if (phoneNumber.matches("\\d{7}"))
			this.phoneNumber = phoneNumber;
		else
			throw new InputMismatchException();
		next = null;
	}

	public Person(Person obj) {
		this.name = obj.name;
		this.surname = obj.surname;
		this.patronym = obj.patronym;
		this.phoneNumber = obj.phoneNumber;
		next = null;
	}

	// Геттеры и сеттеры
	////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////
	public String getPatronym() {
		return patronym;
	}

	public void setPatronym(String patronym) {
		this.patronym = patronym;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////
}

// Класс одностороннего списка
class List {
	// Корень списка
	private Person first;
	// Текущий размер списка
	private int size;

	// Конструктор для создания пустого списка
	public List() {
		first = null;
		size = 0;
	}

	// Метод для добавления нового человека в список
	public void addNewPerson(Person obj) {
		// Если список пуст, то выбираем новый корень
		if (size == 0)
			first = obj;
		else {
			// Становимся в начало
			Person curr = first, prev = curr;
			// Обеспечиваем лексикографический порядок вставки
			while (curr != null && (curr.getSurname().compareTo(obj.getSurname()) < 0
					|| (curr.getSurname().compareTo(obj.getSurname()) == 0
							&& curr.getName().compareTo(obj.getName()) < 0)
					|| (curr.getSurname().compareTo(obj.getSurname()) == 0
							&& curr.getName().compareTo(obj.getName()) == 0
							&& curr.getPatronym().compareTo(obj.getPatronym()) < 0))) {
				// Передвигаемся вперед по списку
				prev = curr;
				curr = curr.next;
			}

			// Вставка перед "начальным" элементом
			if (curr == first) {
				obj.next = first;
				first = obj;
			} else {
				// Вставка где-то в середине списка (конце)
				obj.next = curr;
				prev.next = obj;
			}
		}
		// Увеличиваем размер
		size++;
	}

	// Получаем список телефоных номеров по фамилии

	// Метод вывода всего списка
	public void show() {
		if (size == 0)
			System.out.println("Список пуст!");
		else {
			int index = 1;
			Person curr = first;

			while (curr != null) {
				System.out.println(index + ". " + curr.getSurname() + " " + curr.getName() + " " + curr.getPatronym()
						+ " " + curr.getPhoneNumber());
				curr = curr.next;
				index++;
			}
		}
	}
}

public class FirstPhoneNumbers {

	private static Scanner in = new Scanner(System.in);


	// Вспомогательный метод для прочтения телефонного номера
	private static String getPhoneNumber() {
		System.out.println("Введите номер телефона:");
		String buf;
		buf = in.nextLine();
		if (buf.matches("\\d{7}"))
			return buf;
		else
			throw new NumberFormatException();

	}

	// Чтение полей очередного создаваемого человека
	private static Person instantiatePerson() {
		String name, surname, patronym, phoneNumber;

		System.out.println("Введите имя человека: ");
		name = in.nextLine();
		System.out.println("Введите фамилию человека: ");
		surname = in.nextLine();
		System.out.println("Введите отчество человека: ");
		patronym = in.nextLine();
		System.out.println("Введите номер телефона человека: ");
		phoneNumber = in.nextLine();

		Person obj = new Person(name, surname, patronym, phoneNumber);
		return obj;
	}

	// Вывод меню
	private static void printMenu() {
		System.out.println("Меню:");
		System.out.println("1. Добавить человека в список");
		System.out.println("2. Вывод всего спика");
	}

	// Метод main
	public static void main(String[] args) {

		printMenu();
		int command = -1;
		List list = new List();

		while (true) {
			try {
				System.out.println("Введите команду: ");
				command = Integer.valueOf(in.nextLine());

				switch (command) {

				case 1:
					list.addNewPerson(instantiatePerson());
					break;

				case 2:
					list.show();
					break;

				}

			} catch (InputMismatchException | NumberFormatException a) {
				System.out.println("Проверьте корректность ввода!");
			}

		}

	}

}
