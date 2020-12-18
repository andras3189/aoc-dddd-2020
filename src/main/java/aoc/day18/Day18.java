package aoc.day18;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import aoc.DayBase;

public class Day18 extends DayBase {

	List<List<Integer>> all;
	Stack<String> s;
	long sum = 0L;

	public static void main(String[] args) {
		Day18 day = new Day18();
		// day.setOnlyDummyInput(true);
		// day.setOnlyRealInput(true);
		day.run();
	}

	@Override
	protected void processInput() {

		all = new ArrayList<>();

		for (int i = 0; i < input.size(); i++) {
			String line = input.get(i);
			List<Integer> lineChars = line.chars().boxed().collect(Collectors.toList());
			lineChars = lineChars.stream().filter(c -> c != ' ').collect(Collectors.toList());
			all.add(lineChars);
		}
	}

	@Override
	protected void puzzle1() {

		for (int i = 0; i < all.size(); i++) {
			List<Integer> chars = all.get(i);
			s = new Stack<>();

			s.push(Character.toString(chars.get(0)));
			for (int j = 0; j < chars.size() - 1; j++) {
				// Disable for solution 2
				// int c = chars.get(j);
				// s.push(Character.toString(c));

				// look ahead if there is a plus with higher precedence
				int cNext = chars.get(j + 1);
				s.push(Character.toString(cNext));

				if (cNext == ')') {
					closeParens();
				}

				if (j == chars.size() - 2)
					s.push("f");

				if (canCalculate(s)) {
					String a, o, b, n;
					n = s.pop();
					b = s.pop();
					o = s.pop();
					a = s.pop();
					String r = doCalculation(a, o, b);
					s.push(r);
					if (!n.equals("f"))
						s.push(n);
				} else {
					continue;
				}
			}
			while (s.size() > 1) {
				String a, o, b;
				int k = 0;
				a = s.elementAt(k);
				o = s.elementAt(k + 1);
				b = s.elementAt(k + 2);
				String r = doCalculation(a, o, b);
				s.remove(k);
				s.remove(k);
				s.remove(k);
				s.insertElementAt(r, 0);
			}
			sum += Long.parseLong(s.peek());
		}
		System.out.println(sum);
	}

	private void closeParens() {
		String closingParens, a1, a2, a3;
		closingParens = s.pop();
		a1 = s.pop();
		a2 = s.pop();
		a3 = s.pop();

		if (a2.equals("(")) {
			s.push(a3);
			s.push(a1);
		} else {
			String r = doCalculation(a3, a2, a1);

			if (s.peek().equals("(")) {
				s.pop();
				s.push(r);
			} else {
				s.push(r);
				s.push(closingParens);
				closeParens();
			}
		}
	}

	private String doCalculation(String a, String o, String b) {
		long A, B, R;

		A = Long.parseLong(a);
		B = Long.parseLong(b);

		R = o.equals("+") ? A + B : A * B;

		return Long.toString(R);
	}

	private boolean canCalculate(Stack<String> s) {
		if (s.size() < 4)
			return false;

		boolean x = false;
		String c, b, a, d;
		d = s.pop();
		c = s.pop();
		b = s.pop();
		a = s.pop();
		if (isLong(c) && b.equals("+") && isLong(a)) {
			x = true;
		}
		if (isLong(c) && b.equals("*") && isLong(a) && !d.equals("+")) {
			x = true;
		}

		s.push(a);
		s.push(b);
		s.push(c);
		s.push(d);
		return x;
	}

	@Override
	protected void reset() {
		sum = 0L;
	}

	@Override
	protected void puzzle2() {

	}
}
