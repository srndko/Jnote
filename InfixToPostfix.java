import java.util.*;
import javax.swing.*;
import java.awt.*;

public class InfixToPostfix {
	JFrame frame;

	InfixToPostfix() {
		initComponent();
	}

	private void initComponent() {
		frame = new JFrame("ToPostfix");
		
		JLabel input_label = new JLabel();
		JTextField input_box = new JTextField();


		frame.add(input_box);
		frame.add(input_box);
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
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

	public static void main(String[] args) {
		new InfixToPostfix();
	}
}
