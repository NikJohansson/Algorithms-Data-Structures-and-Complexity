package lab5;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Gorilla {
	private static String characters, s1, s2;
	private static int[][] costs;
	private static int[][] optMatrix;
	private static int alpha;
	private static Map<Character, Integer> charMap;
	

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		characters = String.join("", scan.nextLine().split(" "));
		int k = characters.length();
		charMap = new HashMap<Character,Integer>();
		for (int i = 0; i < k; i++) {
			charMap.put(characters.charAt(i), i);
		}
		costs = new int[k][k];
		for (int i = 0; i < k; i++) {
			for (int j = 0; j < k; j++) {
				costs[i][j] = scan.nextInt();
			}
		}
		int q = scan.nextInt();
		scan.nextLine();
		for (int i = 0; i < q; i++) {
			String[] s = scan.nextLine().split(" ");
			s1 = s[0];
			s2 = s[1];
			optMatrix = makeTable(s[0], s[1]);
			System.out.println(opt(s[0].length(), s[1].length()));
		}
		scan.close();
	}

	public static String align(String s1, String s2) {

		return "";
	}

	public static int[][] makeTable(String s1, String s2) {
		int n = s1.length();
		int m = s2.length();
		int[][] opt = new int[n + 1][m + 1];
		for (int i = 0; i < n + 1; i++) {
			opt[i][0] = -4 * i;
		}
		for (int j = 0; j < m + 1; j++) {
			opt[0][j] = -4 * j;
		}

		for (int i = 1; i < n + 1; i++) {
			for (int j = 1; j < m + 1; j++) {
				int alpha = costs[charMap.get(s1.charAt(i - 1))][charMap.get(s2.charAt(j - 1))];
				opt[i][j] = Math.max(alpha + opt[i - 1][j - 1], Math.max(-4 + opt[i][j - 1], -4 + opt[i - 1][j]));
			}
		}
		return opt;
	}

	public static String opt(int i, int j) {
		boolean cond = true;
		while (cond) {
			if (i == 0) {
				return "*".repeat(j) + s1 + " " + s2;
			}
			if (j == 0) {
				return s1 + " " + "*".repeat(i) + s2;
			}
			alpha = costs[charMap.get(s1.charAt(i - 1))][charMap.get(s2.charAt(j - 1))];
			if (optMatrix[i][j - 1] - 4 > Math.max(optMatrix[i - 1][j - 1] + alpha, optMatrix[i - 1][j] - 4)) {
				s1 = s1.substring(0, i) + "*" + s1.substring(i);
				i++;
			} else if (optMatrix[i - 1][j] - 4 > optMatrix[i - 1][j - 1] + alpha) {
				s2 = s2.substring(0, j) + "*" + s2.substring(j);
				j++;
			}
			i--;
			j--;
		}
		return "";
	}
	
}