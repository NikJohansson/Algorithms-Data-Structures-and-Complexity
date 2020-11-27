//package lab2;

import java.util.List;

public class WordNode implements Comparable<WordNode> {
	private String word;
	private List<WordNode> neighbours;
	private boolean visited;
	private WordNode pred;

	public WordNode(String word) {
		this.word = word;
	}
	public String getWord() {
		return word;
	}
	
	public WordNode getPred() {
		return pred;
	}
	public List<WordNode> getNeighbours(){
		return neighbours;
	}
	public boolean getVisited() {
		return visited;
	}
	public void setVisited(boolean visited) {
		this.visited=visited;
	}
	public void setPred(WordNode pred) {
		this.pred=pred;
	}
	public void setNeighbours(List<WordNode> neighbours) {
		this.neighbours=neighbours;
	}
	@Override
	public int compareTo(WordNode node) {
		return word.compareTo(node.word);
	}
	public boolean equals(WordNode node){
		return word.equals(node.word);
	}
}
