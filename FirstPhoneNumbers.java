package siaod1lab;

import java.util.InputMismatchException;
import java.util.Scanner;

//�����-�������
class Person {
	private String name; // ���
	private String surname; // �������
	private String patronym; // ��������
	private String phoneNumber; // ����� ��������

	public Person next; // ������ �� ����������

	// �����������
	public Person(String name, String surname, String patronym, String phoneNumber) throws InputMismatchException {
		this.name = name;
		this.surname = surname;
		this.patronym = patronym;
		// �������� ����� ������ ��������
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

	// ������� � �������
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

// ����� �������������� ������
class List {
	// ������ ������
	private Person first;
	// ������� ������ ������
	private int size;

	// ����������� ��� �������� ������� ������
	public List() {
		first = null;
		size = 0;
	}

	// ����� ��� ���������� ������ �������� � ������
	public void addNewPerson(Person obj) {
		// ���� ������ ����, �� �������� ����� ������
		if (size == 0)
			first = obj;
		else {
			// ���������� � ������
			Person curr = first, prev = curr;
			// ������������ ������������������ ������� �������
			while (curr != null && (curr.getSurname().compareTo(obj.getSurname()) < 0
					|| (curr.getSurname().compareTo(obj.getSurname()) == 0
							&& curr.getName().compareTo(obj.getName()) < 0)
					|| (curr.getSurname().compareTo(obj.getSurname()) == 0
							&& curr.getName().compareTo(obj.getName()) == 0
							&& curr.getPatronym().compareTo(obj.getPatronym()) < 0))) {
				// ������������� ������ �� ������
				prev = curr;
				curr = curr.next;
			}

			// ������� ����� "���������" ���������
			if (curr == first) {
				obj.next = first;
				first = obj;
			} else {
				// ������� ���-�� � �������� ������ (�����)
				obj.next = curr;
				prev.next = obj;
			}
		}
		// ����������� ������
		size++;
	}

	// �������� ������ ��������� ������� �� �������

	// ����� ������ ����� ������
	public void show() {
		if (size == 0)
			System.out.println("������ ����!");
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


	// ��������������� ����� ��� ��������� ����������� ������
	private static String getPhoneNumber() {
		System.out.println("������� ����� ��������:");
		String buf;
		buf = in.nextLine();
		if (buf.matches("\\d{7}"))
			return buf;
		else
			throw new NumberFormatException();

	}

	// ������ ����� ���������� ������������ ��������
	private static Person instantiatePerson() {
		String name, surname, patronym, phoneNumber;

		System.out.println("������� ��� ��������: ");
		name = in.nextLine();
		System.out.println("������� ������� ��������: ");
		surname = in.nextLine();
		System.out.println("������� �������� ��������: ");
		patronym = in.nextLine();
		System.out.println("������� ����� �������� ��������: ");
		phoneNumber = in.nextLine();

		Person obj = new Person(name, surname, patronym, phoneNumber);
		return obj;
	}

	// ����� ����
	private static void printMenu() {
		System.out.println("����:");
		System.out.println("1. �������� �������� � ������");
		System.out.println("2. ����� ����� �����");
	}

	// ����� main
	public static void main(String[] args) {

		printMenu();
		int command = -1;
		List list = new List();

		while (true) {
			try {
				System.out.println("������� �������: ");
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
				System.out.println("��������� ������������ �����!");
			}

		}

	}

}
