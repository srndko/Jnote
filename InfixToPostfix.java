import java.util.*;
import javax.swing.*;

public class InfixToPostfix {

	public static void main(String[] args) {
		run();
	}

	public static void run() {
		String input = JOptionPane.showInputDialog("Enter a expression");
		String output = convert(input);
		JOptionPane.showMessageDialog(null, output, "Result", JOptionPane.INFORMATION_MESSAGE);
	}

	public static String convert(String input) {
		Stack<Character> stack = new Stack<Character>();
		StringBuilder output = new StringBuilder();
		for (int i = 0; i < input.length(); i++) {
			char current = input.charAt(i);
			if (Character.isLetterOrDigit(current)) {
				output.append(current);
			} else if (current == '(') {
				stack.push(current);
			} else if (current == ')') {
				//while (!stack.isEmpty() && stack.peek() != '(') {
				while (stack.peek() != '(') {
					output.append(stack.pop());
				}
				stack.pop(); // pop '('
			} else {
				while (!stack.isEmpty() && prec(current) <= prec(stack.peek())) {
					output.append(stack.pop());
				}
				stack.push(current);	
			}
		}
		while (!stack.isEmpty()) {
			output.append(stack.pop());
		}
		return output.toString();
	}

	private static int prec(char op) {
		if (op == '^') {
			return 3;
		} else if (op == '*' || op == '/') {
			return 2;
		} else if (op == '-' || op == '+') {
			return 1;
		} else {
			return -1;
		}
	}
}
