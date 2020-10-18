package siaod1lab;

import java.util.*;

public class Infix {
	// функция, возвращающая приоритет заданного оператора
	static int Prec(char ch) {
		switch (ch) {
		case '+':
		case '-':
			return 1;

		case '*':
		case '/':
			return 2;

		case '^':
			return 3;
		}
		return -1;
	}

	// Основной метод, преобразующий данное инфиксное выражение в постфиксное
	// выражение.
	static String infixToPostfix(String exp) {
		// инициализируем пустую строку для результата
		String result = new String("");

		// инициализация пустого стека
		Stack<Character> stack = new Stack<>();

		for (int i = 0; i < exp.length(); ++i) {
			char c = exp.charAt(i);

			// Если отсканированный символ является операндом, добавить его в
			// вывод.
			if (Character.isLetterOrDigit(c))
				result += c;

			// Если отсканированный символ представляет собой '(', помещаем его
			// в стек.
			else if (c == '(')
				stack.push(c);

			// Если отсканированный символ - ')', проделываем выталкивание пока
			// не встретится символ '('.
			else if (c == ')') {
				while (!stack.isEmpty() && stack.peek() != '(')
					result += stack.pop();

				stack.pop();
			} else // обнаружен оператор
			{
				while (!stack.isEmpty() && Prec(c) <= Prec(stack.peek())) {
					result += stack.pop();
				}
				stack.push(c);
			}

		}

		// извлекаем все операторы из стека
		while (!stack.isEmpty()) {

			result += stack.pop();
		}
		return result;
	}

	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		String exp = scn.nextLine();
		System.out.println(infixToPostfix(exp));
	}
}
