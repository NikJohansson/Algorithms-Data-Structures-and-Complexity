package lab3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

public class MakingFriends {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		int m = scan.nextInt();
		boolean[] t = new boolean[n];
		TreeSet<Node> q = new TreeSet<Node>();
		List<List<WeightNode>> weights = new ArrayList<List<WeightNode>>(n);
		for (int i = 0; i < n; i++) {
			weights.add(i,new ArrayList<WeightNode>());
		}
		for (int i = 0; i < m; i++) {
			int neighbour1= scan.nextInt();
			int neighbour2= scan.nextInt();
			int w = scan.nextInt();
			weights.get(neighbour1-1).add(new WeightNode(neighbour2,w));
			weights.get(neighbour2-1).add(new WeightNode(neighbour1,w));	
		}
		Node[] nodes = new Node[n];
		for (int i = 1; i < n+1; i++) {
			Node node = new Node(i);
			nodes[i-1]=node;
			q.add(node);
		}
		Node currentNode = q.pollFirst();
		int time=0;
		while (!q.isEmpty()) {
			int idx=currentNode.getIdx()-1;
			t[idx]=true;
			List<WeightNode> neighbours= weights.get(idx);
			for (int i = 0; i < neighbours.size(); i++) {
				idx=neighbours.get(i).getIdx()-1;
				if(!t[idx]) {
					if (neighbours.get(i).getWeight()<nodes[idx].getD()) {
						q.remove(nodes[idx]);
						nodes[idx].setD(neighbours.get(i).getWeight());
						q.add(nodes[idx]);
					}
				}
			}
			currentNode = q.pollFirst();
			time+=currentNode.getD();
		}
		System.out.println(time);
		scan.close();
	}
	public static class Node implements Comparable<Node> {
		private int idx;
		private int d= Integer.MAX_VALUE;

		public Node(int idx) {
			this.idx=idx;
		}
			
		public int getIdx() {
			return idx;
		}
		
		public int getD() {
			return d;
		}
		public void setD(int d) {
			this.d=d;
		}
		@Override
		public int compareTo(Node node) {
			return  d-node.d!=0 ? d-node.d : idx-node.idx;
		}
	}
	public static class WeightNode {
		private int idx;
		private int weight;

		public WeightNode(int idx, int weight) {
			this.idx=idx;
			this.weight=weight;
		}
			
		public int getIdx() {
			return idx;
		}
		
		public int getWeight() {
			return weight;
		}
	}
}
