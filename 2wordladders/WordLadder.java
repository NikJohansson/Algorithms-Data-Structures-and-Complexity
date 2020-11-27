//package lab2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class WordLadder {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		int q = scan.nextInt();
		Map<String, WordNode> wordMap = new HashMap<>();
		for (int i = 0; i < n; i++) {
			String word = scan.next();
			wordMap.put(word, new WordNode(word));
		}
		for (String w1 : wordMap.keySet()) {
			String[] lastFour = w1.substring(1).split("");
			List<WordNode> neighbours = new ArrayList<>();
			for (String w2 : wordMap.keySet()) {
				if (!w1.equals(w2)) {
					String tempWord = new String(w2);
					for (int i = 0; i < 4; i++) {
						if (!tempWord.contains(lastFour[i])) {
							break;
						}
						tempWord = tempWord.replaceFirst(lastFour[i], " ");
						if (i==3) {
							neighbours.add(wordMap.get(w2));
						}
					}
				}
			}
			wordMap.get(w1).setNeighbours(neighbours);
		}
		for (int i = 0; i < q; i++) {
			String startWord = scan.next();
			String endWord = scan.next();
			WordLadder.BFS(wordMap, startWord, endWord);
		}
	}
	public static void BFS(Map<String, WordNode> wordMap, String startWord, String endWord) {
		if (startWord.equals(endWord)) {
			System.out.println(0);
			return;
		}
		for (WordNode node : wordMap.values()) {
			node.setVisited(false);
		}
		wordMap.get(startWord).setVisited(true);
		Queue<WordNode> queue = new LinkedList<>();
		queue.add(wordMap.get(startWord));
		while (!queue.isEmpty()) {
			WordNode node = queue.poll();
			for (WordNode neighbour : node.getNeighbours()) {
				if (!neighbour.getVisited()) {
					neighbour.setVisited(true);
					queue.add(neighbour);
					neighbour.setPred(node);
					if (neighbour.equals(wordMap.get(endWord))) {
						int level = 1;
						while (!neighbour.getPred().equals(wordMap.get(startWord))) {
							level++;
							neighbour = neighbour.getPred();
						}
						System.out.println(level);
						return;
					}
				}
			}
		}
		System.out.println("Impossible");
	}
}
