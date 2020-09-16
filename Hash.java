package siaod1lab;

import java.util.Scanner;

public class Hash {
	static class Link {
		private String s;
		public Link next;

		public Link(String l) {
			s = l;
		}

		public String getS() {
			return s;
		}

		public void displayLink() {
			System.out.print(s + " ");
		}

	}

	static class SortedList {
		private Link first;

		public void SortedList() {
			first = null;
		}

		public void insert(Link theLink) {
			int key=theLink.getS().length();
			Link previous = null;
			Link current = first;
			while (current != null &&  key > current.getS().length() ) {
				previous = current;
				current = current.next;
			}
			if (previous == null)
				first = theLink;
			else
				previous.next = theLink;
			theLink.next = current;
		}

		public void delete(String s) {
			Link previous = null;
			Link current = first;
			while (current != null &&  !s.equals(current.getS()) ) {
				previous = current;
				current = current.next;
			}
			if (previous == null)
				first = first.next;
			else
				previous.next = current.next;
		}

		public Link find(String s) {
			Link current = first;
			while (current != null) {
				if (current.getS().equals(s)  &&  current.getS().length() <= s.length())
					return current;
				current = current.next;
			}
			return null;
		}

		public void displayList() {
			System.out.print("List : ");
			Link current = first;
			while (current != null) {
				current.displayLink();
				current = current.next;
			}
			System.out.println("");
		}

	}

	static class HashTable {
		private SortedList[] hashArray;
		private int arraySize;

		public HashTable(int size) {
			arraySize = size;
			hashArray = new SortedList[arraySize];
			for (int j = 0; j < arraySize; j++)
				hashArray[j] = new SortedList();
		}

		public void displayTable() {
			for (int j = 1; j < arraySize; j++) {
				System.out.print(j + ". ");
				hashArray[j].displayList();
			}
		}

		public int hashFunc(String s) {
			return s.length()%arraySize;
		}

		public void insert(Link theLink) {
			String key = theLink.getS();
			int hashVal = hashFunc(key);
			hashArray[hashVal].insert(theLink);
		}

		public void delete(String key) {
			int hashVal = hashFunc(key);
			hashArray[hashVal].delete(key);
		}

		public Link find(String key) {
			int hashVal = hashFunc(key);
			Link theLink = hashArray[hashVal].find(key);
			return theLink;
		}
	}

	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		String aKey;
		Link aDataItem;
		int n = 0;
		int size = 0;
		System.out.println("Insert size of hashmap");
		size=scn.nextInt();
		System.out.println("Insert amount of elements");
		n = scn.nextInt();
		System.out.println("Insert English words");
		HashTable theHashTable = new HashTable(size);
		for (int j = 0; j <= n; j++) {
			aKey = scn.nextLine();
			aDataItem = new Link(aKey);
			theHashTable.insert(aDataItem);
		}
		while (true) {
			System.out.print("Enter first and second letter of ");
			System.out.print("show, insert, delete, or find: ");
			String choice = scn.nextLine();
			switch (choice) {
			case "sh":
				theHashTable.displayTable();
				break;
			case "in":
				System.out.print("Enter key value to insert: ");
				aKey = scn.nextLine();
				aDataItem = new Link(aKey);
				theHashTable.insert(aDataItem);
				break;
			case "de":
				System.out.print("Enter key value to delete: ");
				aKey = scn.nextLine();
				theHashTable.delete(aKey);
				break;
			case "fi":
				System.out.print("Enter key value to find: ");
				aKey = scn.nextLine();
				aDataItem = theHashTable.find(aKey);
				if (aDataItem != null)
					System.out.println("Found " + aKey);
				else
					System.out.println("Could not find " + aKey);
				break;
			default:
				System.out.print("Invalid entry\n");
			}
		}
	}

}
