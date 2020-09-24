package siaod1lab;

import java.util.Scanner;
import java.lang.String;

public class task3 {
	// ����� ��� ���������
	static boolean cmp(Abonent o1, Abonent o2) {
		int len;
		if (o1.getname().length() > o2.getname().length()) {
			len = o2.getname().length();
		} else {
			len = o1.getname().length();
		}
		for (int i = 0; i < len; i++) {
			while (o1.getname().charAt(i) == o2.getname().charAt(i))
				i++;
			if (o1.getname().charAt(i) > o2.getname().charAt(i))
				return false;
		}
		return true;
	}

	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		Single<Abonent> iter = new Single<Abonent>();
		label: while (true) { // ����� � ����������� ����
			System.out.println("��� �� ������?");
			System.out.println("-----------------------------------");
			System.out.println("������� 1, ���� ��� ����� �������� �������� � ���� � ����(� ����� ��� �� �������)");
			System.out.println("������� 2, ���� ��� ����� ������ ���� ���������");
			System.out.println("������� 3, ���� ��� ����� ������� �������� �� ����");
			System.out.println("������� 4, ���� ��� ����� �������� ������");
			System.out.println("������� 5, ���� ��� ����� ���������� �� ������ �������� �������");
			System.out.println("������� 6, ���� ��� ����� �� ������� ���������� ������ ������� ���������");
			boolean f = true;
			String s = ""; // ������ ����� ��������
			while (f) {
				s = scn.nextLine().trim(); // ��������� ������ � ������� �������
											// � ������ � ����� ������ . �
											// ���������� �� ����� ����������
				if (s.matches("[1-6]")) { // ���� ���������� ������������
					f = false;
				} else {
					System.out.println("������������ ���� . ������� ��� ���");
				}
			}
			if (s.equals("1")) {

				System.out.println("������� ��� (�� ����������) ��� ������� home , ����� ��������� � ����");
				String name = "";
				f = true;
				while (f == true) {
					name = scn.nextLine().trim(); // ���� � �������� �������� �
													// ������ � � ����� ������
					// ������� � ����
					if (name.equals("home"))
						continue label;
					// �������� �� ������������ �����
					else if (Abonent.checkName(name)) {

						// ��������� ��������� ������ ��� ������
						f = false;
						// ��������� ������ ���� ������ ����� �������
						name = name.replaceAll("\\s+", " ");
						// ������ ������ ����� �������� � ��������� ����������
						name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
						for (int i = 1; i < name.length(); i++) {
							if (name.charAt(i) == ' ') {
								name = name.substring(0, i + 1) + Character.toUpperCase(name.charAt(i + 1))
										+ name.substring(i + 2);
								i++;
							} else {
								name = name.substring(0, i) + Character.toLowerCase(name.charAt(i))
										+ name.substring(i + 1);
							}
						}

					} else {
						System.out.println("������������ ���� . ��������� ��� ��� ");
					}
				}
				System.out.println("������� 7 ������� �����  ��� ������� home , ����� ��������� � ����");
				String number = "";
				f = true;
				while (f == true) {
					number = scn.nextLine().trim();
					// ������� � ����
					if (number.equals("home"))
						continue label;
					// �������� �� ������������
					else if (Abonent.checknumber(number)) {
						f = false;
					} else {
						System.out.println("������������ ���� . ��������� ��� ��� ");
					}
				}

				System.out.println(
						"������� 1, ����� �������� �������� � ����� ������; ������� 2, ����� �������� �������� �� �������. ���� ������ ��������� � ����, ������� home");
				String s2 = ""; // ������, �������� ����� �������
				f = true;
				// �������� �� ������������
				while (f) {
					s2 = scn.nextLine().trim();
					// �������� ������ c ������� ����������� ���������
					if (s2.equals("home")) {
						continue label;
					} else if (s2.matches("[1-2]")) {
						f = false;
					} else {
						System.out.println("������������ ����. ���������� ��� ���");
					}
				}

				if (s2.equals("1")) {
					iter.add(new Abonent(name, number));
				}
				// ������ �� �������
				if (s2.equals("2")) {
					System.out.println(
							"������� ������ , �� �������� �� ������ �������� ��������.��������� ���������� � 0 .���� ������ ��������� � ����, ������� home");
					f = true;
					// ��������� �������� �� ������� , ���� ���
					// ��������
					while (f) {
						s2 = scn.nextLine().trim();
						if (s2.equals("home")) {
							continue label;
						} else if (s2.matches("\\d+") && Integer.parseInt(s2) <= iter.size()
								&& Integer.parseInt(s2) >= 0) {
							iter.add(Integer.parseInt(s2), new Abonent(name, number));
							f = false;
						} else {
							System.out
									.println("��������� ������ � ����� ����������� ������� ������. ���������� ��� ���");
						}
					}

				}
				System.out.println("������� ������� ��������");

			}
			if (s.equals("2")) {
				// ����������

				boolean fd = true; // ���������� �����
				for (int i = 1; i < iter.size() && fd; i++) {
					fd = false;
					for (int j = iter.size() - 1; j >= i; j--) {
						if (cmp(iter.get(j - 1), iter.get(j)) == false) {
							fd = true;
							Abonent buf = iter.get(j - 1);
							iter.set(j - 1, iter.get(j));
							iter.set(j, buf);
						}
					}
				}

				System.out.printf("|%-30s|%14s|\n", "���", "����� ��������");
				for (int i = 0; i < iter.size(); i++) {
					System.out.println("|------------------------------+--------------|");
					System.out.printf("|%-30s|%14s|\n", iter.get(i).getname(), iter.get(i).getnumber());
				}
				System.out.println("|______________________________|______________|");
			}
			if (s.equals("3")) {
				if (iter.size() == 0) {
					System.out.println("���� �����");
					continue;
				}
				System.out.println(
						"������� ����� ��������, �������� ����� �������. ��������� ���������� � 0. ���� ������ ��������� � ����, ������� home");
				String s3 = ""; // ����� ��������, ������� �����
								// �������
				f = true;

				while (f) {
					s3 = scn.nextLine().trim();
					if (s3.equals("home")) {
						continue label;
					} else if (s3.matches("\\d+") && Integer.parseInt(s3) < iter.size() && Integer.parseInt(s3) >= 0) {
						// ��������
						iter.remove(Integer.parseInt(s3));
						System.out.println("������� ������ �������");
						f = false;
					} else {
						System.out.println("��������� ������ ��-�� ������������� ������. ���������� ��� ���");
					}
				}
			}
			if (s.equals("4")) {
				if (iter.size() == 0) {
					System.out.println("���� �����");
					continue;
				}
				System.out.println(
						"������� ����� ������, ������� ����� ��������.��������� ���������� � 0. ���� ������ ��������� � ����, ������� home");
				f = true;
				String s4 = ""; // ��� ������ ����� ������� �����
								// ������, ������� ����� ��������
				while (f) {
					s4 = scn.nextLine().trim();
					if (s4.equals("home")) {
						continue label;
						// else � ������� �������� �� ������������
					} else if (s4.matches("\\d+") && Integer.parseInt(s4) < iter.size() && Integer.parseInt(s4) >= 0) {
						// ���� ���������� � ����� ������
						System.out.println("������� ���������� �� ��������:");
						System.out.println("������� ��� ��� ������� home , ����� ��������� � ����");
						String name = "";
						f = true;
						while (f == true) {
							name = scn.nextLine().trim(); // ���� � ��������
															// �������� �
															// ������ � � �����
															// ������
							// ������� � ����
							if (name.equals("home"))
								continue label;
							// �������� �� ������������ �����
							else if (Abonent.checkName(name)) {
								f = false;
							} else {
								System.out.println("������������ ���� . ��������� ��� ��� ");
							}
						}
						System.out
								.println("������� 7 ������� ����� ��������  ��� ������� home , ����� ��������� � ����");
						String number = "";
						f = true;
						while (f == true) {
							number = scn.nextLine().trim();
							// ������� � ����
							if (number.equals("home"))
								continue label;
							// �������� �� ������������
							else if (Abonent.checknumber(number)) {
								f = false;
							} else {
								System.out.println("������������ ���� . ��������� ��� ��� ");
							}
						}

						// ������ �������
						iter.set(Integer.parseInt(s4), new Abonent(name, number));
						System.out.println("������� ������� �������");

					} else {
						System.out.println("���������� �������� , ������ ������ �� ���������� . ������� ��� ���");
					}
				}
			}
			if (s.equals("5")) {
				System.out.println("������� ����� ��������");
				String number = "";
				f = true;
				while (f == true) {
					number = scn.nextLine().trim();
					// ������� � ����
					if (number.equals("home"))
						continue label;
					// �������� �� ������������
					else if (Abonent.checknumber(number)) {
						f = false;
					} else {
						System.out.println("������������ ���� . ��������� ��� ��� ");
					}
				}
				System.out.printf("|%14s|%-15s|\n", "����� ��������", "�������");
				for (int i = 0; i < iter.size(); i++) {
					if (number.equals(iter.get(i).number)) {
						String s3 = iter.get(i).getname();
						int ind = s3.indexOf(' ');
						System.out.println("|--------------+---------------|");
						System.out.printf("|%14s|%-15s|\n", iter.get(i).getnumber(), s3.substring(0, ind));
					}
				}
				System.out.println("|______________|_______________|");
			}
			if (s.equals("6")) {
				System.out.println("������� ������� (�� ����������)");
				String name = "";
				f = true;
				while (f == true) {
					name = scn.nextLine().trim();
					// ������� � ����
					if (name.equals("home"))
						continue label;
					// �������� �� ������������
					else if (Abonent.check(name)) {
						f = false;
					} else {
						System.out.println("������������ ���� . ��������� ��� ��� ");
					}
				}
				System.out.printf("|%15s|%14s|\n", "���", "����� ��������");
				for (int i = 0; i < iter.size(); i++) {
					String s3 = iter.get(i).getname();
					int d = s3.indexOf(' ');
					s3 = s3.substring(0, d);
					if (name.equals(s3)) {
						System.out.println("|---------------+--------------|");
						System.out.printf("|%-15s|%14s|\n", s3, iter.get(i).getnumber());
					}
				}
				System.out.println("|_______________|______________|");
			}
		}
	}
}