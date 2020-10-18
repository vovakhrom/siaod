package siaod1lab;

import java.util.*;

public class Infix {
	// функци€, возвращающа€ приоритет заданного оператора
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

	// ќсновной метод, преобразующий данное инфиксное выражение в постфиксное
	// выражение.
	static String infixToPostfix(String exp) {
		// инициализируем пустую строку дл€ результата
		String result = new String("");

		// инициализаци€ пустого стека
		Stack<Character> stack = new Stack<>();

		for (int i = 0; i < exp.length(); ++i) {
			char c = exp.charAt(i);

			// ≈сли отсканированный символ €вл€етс€ операндом, добавить его в
			// вывод.
			if (Character.isLetterOrDigit(c))
				result += c;

			// ≈сли отсканированный символ представл€ет собой '(', помещаем его
			// в стек.
			else if (c == '(')
				stack.push(c);

			// ≈сли отсканированный символ - ')', проделываем выталкивание пока
			// не встретитс€ символ '('.
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
