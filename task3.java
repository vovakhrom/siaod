package siaod1lab;

import java.util.Scanner;
import java.lang.String;

public class task3 {
	// метод для сравнения
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
		label: while (true) { // метка и бесконечный цикл
			System.out.println("Что вы хотите?");
			System.out.println("-----------------------------------");
			System.out.println("Введите 1, если Вам нужно добавить обонента в базу в базу(в конец или по индексу)");
			System.out.println("Введите 2, если Вам нужен список всех абонентов");
			System.out.println("Введите 3, если Вам нужно удалить абонента из базы");
			System.out.println("Введите 4, если Вам нужно заменить запись");
			System.out.println("Введите 5, если Вам нужно определить по номеру телефона фамилии");
			System.out.println("Введите 6, если Вам нужно по фамилии определить список номеров телефонов");
			boolean f = true;
			String s = ""; // хранит номер операции
			while (f) {
				s = scn.nextLine().trim(); // считывает строку и удаляет пробелы
											// в начале и конце строки . В
											// дальнейшем не будет поясняться
				if (s.matches("[1-6]")) { // если происходит соответствие
					f = false;
				} else {
					System.out.println("Неккоректный ввод . Введите ещё раз");
				}
			}
			if (s.equals("1")) {

				System.out.println("Введите ФИО (на английском) или введите home , чтобы вернуться в меню");
				String name = "";
				f = true;
				while (f == true) {
					name = scn.nextLine().trim(); // ввод и удаление пробелов в
													// начале и в конце строки
					// возврат в меню
					if (name.equals("home"))
						continue label;
					// проверка на корректность ввода
					else if (Abonent.checkName(name)) {

						// подгоняем введенные данные под шаблон
						f = false;
						// оставляем только один пробел между словами
						name = name.replaceAll("\\s+", " ");
						// делаем первые буквы большими а остальные маленькими
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
						System.out.println("Некорректный ввод . Попробуте ещё раз ");
					}
				}
				System.out.println("Введите 7 значный номер  или введите home , чтобы вернуться в меню");
				String number = "";
				f = true;
				while (f == true) {
					number = scn.nextLine().trim();
					// возврат в меню
					if (number.equals("home"))
						continue label;
					// проверка на корректность
					else if (Abonent.checknumber(number)) {
						f = false;
					} else {
						System.out.println("Некорректный ввод . Попробуте ещё раз ");
					}
				}

				System.out.println(
						"Введите 1, чтобы добавить абонента в конец списка; введите 2, чтобы добавить абонента по индексу. Если хотите вернуться в меню, введите home");
				String s2 = ""; // строка, хранящая номер команды
				f = true;
				// проверка на корректность
				while (f) {
					s2 = scn.nextLine().trim();
					// проверка строки c помощью регулярного выражения
					if (s2.equals("home")) {
						continue label;
					} else if (s2.matches("[1-2]")) {
						f = false;
					} else {
						System.out.println("Неккоректный ввод. Попробуйте ещё раз");
					}
				}

				if (s2.equals("1")) {
					iter.add(new Abonent(name, number));
				}
				// запись по индексу
				if (s2.equals("2")) {
					System.out.println(
							"Введите индекс , по которому вы хотите добавить абонента.Нумерация начинается с 0 .Если хотите вернуться в меню, введите home");
					f = true;
					// добавляем абонента по индексу , если это
					// возможно
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
									.println("Произошла ошибка в связи отсутствием данного номера. Попробуйте ещё раз");
						}
					}

				}
				System.out.println("Абонент успешно добавлен");

			}
			if (s.equals("2")) {
				// сортировка

				boolean fd = true; // объявление флага
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

				System.out.printf("|%-30s|%14s|\n", "ФИО", "Номер телефона");
				for (int i = 0; i < iter.size(); i++) {
					System.out.println("|------------------------------+--------------|");
					System.out.printf("|%-30s|%14s|\n", iter.get(i).getname(), iter.get(i).getnumber());
				}
				System.out.println("|______________________________|______________|");
			}
			if (s.equals("3")) {
				if (iter.size() == 0) {
					System.out.println("База пуста");
					continue;
				}
				System.out.println(
						"Введите номер абонента, которого нужно удалить. Нумерация начинается с 0. Если хотите вернуться в меню, введите home");
				String s3 = ""; // номер элемента, который нужно
								// удалить
				f = true;

				while (f) {
					s3 = scn.nextLine().trim();
					if (s3.equals("home")) {
						continue label;
					} else if (s3.matches("\\d+") && Integer.parseInt(s3) < iter.size() && Integer.parseInt(s3) >= 0) {
						// удаление
						iter.remove(Integer.parseInt(s3));
						System.out.println("Абонент удален успешно");
						f = false;
					} else {
						System.out.println("Произошла ошибка из-за некорректного номера. Попробуйте ещё раз");
					}
				}
			}
			if (s.equals("4")) {
				if (iter.size() == 0) {
					System.out.println("База пуста");
					continue;
				}
				System.out.println(
						"Введите номер записи, которую нужно заменить.Нумерация начинается с 0. Если хотите вернуться в меню, введите home");
				f = true;
				String s4 = ""; // эта строка будет хранить номер
								// детали, которую нужно заменить
				while (f) {
					s4 = scn.nextLine().trim();
					if (s4.equals("home")) {
						continue label;
						// else и следует проверка на корректность
					} else if (s4.matches("\\d+") && Integer.parseInt(s4) < iter.size() && Integer.parseInt(s4) >= 0) {
						// ввод информации о новой детали
						System.out.println("Введите информацию об абоненте:");
						System.out.println("Введите ФИО или введите home , чтобы вернуться в меню");
						String name = "";
						f = true;
						while (f == true) {
							name = scn.nextLine().trim(); // ввод и удаление
															// пробелов в
															// начале и в конце
															// строки
							// возврат в меню
							if (name.equals("home"))
								continue label;
							// проверка на корректность ввода
							else if (Abonent.checkName(name)) {
								f = false;
							} else {
								System.out.println("Некорректный ввод . Попробуте ещё раз ");
							}
						}
						System.out
								.println("Введите 7 значный номер телефона  или введите home , чтобы вернуться в меню");
						String number = "";
						f = true;
						while (f == true) {
							number = scn.nextLine().trim();
							// возврат в меню
							if (number.equals("home"))
								continue label;
							// проверка на корректность
							else if (Abonent.checknumber(number)) {
								f = false;
							} else {
								System.out.println("Некорректный ввод . Попробуте ещё раз ");
							}
						}

						// замена объекта
						iter.set(Integer.parseInt(s4), new Abonent(name, number));
						System.out.println("Абонент успешно заменен");

					} else {
						System.out.println("Невозможно добавить , такого номера не существует . Введите ещё раз");
					}
				}
			}
			if (s.equals("5")) {
				System.out.println("Введите номер телефона");
				String number = "";
				f = true;
				while (f == true) {
					number = scn.nextLine().trim();
					// возврат в меню
					if (number.equals("home"))
						continue label;
					// проверка на корректность
					else if (Abonent.checknumber(number)) {
						f = false;
					} else {
						System.out.println("Некорректный ввод . Попробуте ещё раз ");
					}
				}
				System.out.printf("|%14s|%-15s|\n", "Номер телефона", "Фамилия");
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
				System.out.println("Введите Фамилию (на английском)");
				String name = "";
				f = true;
				while (f == true) {
					name = scn.nextLine().trim();
					// возврат в меню
					if (name.equals("home"))
						continue label;
					// проверка на корректность
					else if (Abonent.check(name)) {
						f = false;
					} else {
						System.out.println("Некорректный ввод . Попробуте ещё раз ");
					}
				}
				System.out.printf("|%15s|%14s|\n", "ФИО", "Номер телефона");
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