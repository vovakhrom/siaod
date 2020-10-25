package siaod1lab;

import java.util.*;

public class Prefix {

	// Функция для проверки , является ли символ оператором
	static boolean isOperator(char c) {
		return (!(c >= 'a' && c <= 'z') && !(c >= '0' && c <= '9') && !(c >= 'A' && c <= 'Z'));
	}

	// функция для определения приоритета оператора
	static int getPriority(char C) {
		if (C == '-' || C == '+')
			return 1;
		else if (C == '*' || C == '/')
			return 2;
		else if (C == '^')
			return 3;
		return 0;
	}

	// функция конвертирования инфикс в префикс
	static String infixToPrefix(String infix) {
		// стек для операторов
		Stack<Character> operators = new Stack<Character>();

		// стек для операндов
		Stack<String> operands = new Stack<String>();

		for (int i = 0; i < infix.length(); i++) {
			// если текущий символ '(' помещаем его в стек операторов
			if (infix.charAt(i) == '(') {
				operators.push(infix.charAt(i));
			}

			/*
			 * Если текущий символ ')' , то выталкиваем из обоих стеков 2
			 * операнда и оператор и результат заносим в стек операндов
			 */
			else if (infix.charAt(i) == ')') {
				while (!operators.empty() && operators.peek() != '(') {

					// 1 операнд
					String op1 = operands.peek();
					operands.pop();

					// 2 операнд
					String op2 = operands.peek();
					operands.pop();

					// оператор
					char op = operators.peek();
					operators.pop();

					// добавить операнды и оператор по форме
					String tmp = op + op2 + op1;
					operands.push(tmp);
				}

				// Выталкивание '('
				operators.pop();
			}

			// если текущий символ операнд заносим в стек операндов
			else if (!isOperator(infix.charAt(i))) {
				operands.push(infix.charAt(i) + "");
			}
			// если текущий символ оператор
			else {
				while (!operators.empty() && getPriority(infix.charAt(i)) <= getPriority(operators.peek())) {

					String op1 = operands.peek();
					operands.pop();

					String op2 = operands.peek();
					operands.pop();

					char op = operators.peek();
					operators.pop();

					String tmp = op + op2 + op1;
					operands.push(tmp);
				}

				operators.push(infix.charAt(i));
			}
		}

		/*
		 * Извлекаем операторы из стека операторов до момента , когда он будет
		 * пуст и добавляем в стек операндов для результата.
		 */
		while (!operators.empty()) {
			String op1 = operands.peek();
			operands.pop();

			String op2 = operands.peek();
			operands.pop();

			char op = operators.peek();
			operators.pop();

			String tmp = op + op2 + op1;
			operands.push(tmp);
		}

		// конечное выражение в стеке операндов
		return operands.peek();
	}

	public static void main(String args[]) {
		Scanner scn = new Scanner(System.in);
		String s = scn.nextLine();
		System.out.println(infixToPrefix(s));
	}
}
