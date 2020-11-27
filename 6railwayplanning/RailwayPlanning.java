package lab6;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class RailwayPlanning {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		int m = scan.nextInt();
		int c = scan.nextInt();
		int p = scan.nextInt();
		Node[] nodes = new Node[n];
		for (int i = 0; i < n; i++) {
			nodes[i] = new Node(i);
		}
		int[][] routes = new int[m][3];
		for (int i = 0; i < m; i++) {
			int u = scan.nextInt();
			int v = scan.nextInt();
			int ci = scan.nextInt();
			Edge edge = new Edge(ci);
			nodes[u].getNeighbours().put(nodes[v], edge);
			nodes[v].getNeighbours().put(nodes[u], edge);
			routes[i][0] = u;
			routes[i][1] = v;
			routes[i][2]=ci;
		}
		int[] orderOfRemove=new int[p];
		for (int i = 0; i < p; i++) {
			int r=scan.nextInt();
			orderOfRemove[p-i-1]=r;
			int u = routes[r][0];
			int v = routes[r][1];
			nodes[u].getNeighbours().remove(nodes[v]);
			nodes[v].getNeighbours().remove(nodes[u]);
		}
		int flow=0;
		int delta = BFS(nodes);
		while (delta > 0) {
			flow += delta;
			delta = BFS(nodes);
		}
		for (int i = 0 ; i < p; i++) {
			int r=orderOfRemove[i];
			int u = routes[r][0];
			int v = routes[r][1];
			int ci = routes[r][2];
			Edge edge = new Edge(ci);
			nodes[u].getNeighbours().put(nodes[v], edge);
			nodes[v].getNeighbours().put(nodes[u], edge);
			delta = BFS(nodes);
			while (delta > 0) {
				flow += delta;
				delta = BFS(nodes);
			}
			if (flow>=c) {
				System.out.println(p-i-1 + " " + flow);
				break;
			}
		}
		scan.close();
	}

	public static int BFS(Node[] nodes) {
		for (Node node : nodes) {
			node.setVisited(false);
		}
		nodes[0].setVisited(true);
		Queue<Node> queue = new LinkedList<>();
		queue.add(nodes[0]);
		while (!queue.isEmpty()) {
			Node node = queue.poll();
			HashMap<Node, Edge> neighbours = node.getNeighbours();
			for (Node neighbour : neighbours.keySet()) {
				Edge edge = neighbours.get(neighbour);
				if (!neighbour.getVisited() && edge.getFlow() < edge.getCapacity()) {
					neighbour.setVisited(true);
					queue.add(neighbour);
					neighbour.setPred(node);
					if (neighbour.getIdx() == nodes.length - 1) {
						int delta = edge.getCapacity();
						while (neighbour.getIdx() != nodes[0].getIdx()) {
							Node pred = neighbour.getPred();
							edge = pred.getNeighbours().get(neighbour);
							if (edge.getCapacity() - edge.getFlow() < delta) {
								delta = edge.getCapacity() - edge.getFlow();
							}
							neighbour = neighbour.getPred();
						}
						node = nodes[nodes.length - 1];
						while (node.getIdx() != nodes[0].getIdx()) {
							Node pred = node.getPred();
							edge = pred.getNeighbours().get(node);
							edge.setFlow(delta+edge.getFlow());
							node = node.getPred();
						}

						return delta;
					}
				}
			}
		}
		return 0;
	}

	public static class Node {
		private int idx;
		private boolean visited;
		private Node pred;
		private HashMap<Node, Edge> neighbours = new HashMap<Node, Edge>();
		
		public Node(int idx) {
			this.idx = idx;
		}

		public int getIdx() {
			return idx;
		}

		public HashMap<Node, Edge> getNeighbours() {
			return neighbours;
		}

		public boolean getVisited() {
			return visited;
		}

		public void setVisited(boolean visited) {
			this.visited = visited;
		}

		public Node getPred() {
			return pred;
		}

		public void setPred(Node pred) {
			this.pred = pred;
		}
	}

	public static class Edge {
		private int flow, c;

		public Edge(int c) {
			this.c = c;
		}

		public int getCapacity() {
			return c;
		}

		public int getFlow() {
			return flow;
		}

		public void setFlow(int flow) {
			this.flow = flow;
		}

	}

}
