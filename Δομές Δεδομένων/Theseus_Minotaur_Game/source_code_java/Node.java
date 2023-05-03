import java.util.ArrayList;

public class Node {

	Node parent;
	ArrayList<Node> children;
	int nodeDepth;
	int[] nodeMove;
	Board nodeBoard;
	double nodeEvaluation;
	
	//Initializes the variables of the class.
	public Node() {
		parent = null;
		children = new ArrayList<Node>();
		nodeDepth = 0;
		nodeMove = new int[3];
		nodeBoard = new Board();
		nodeEvaluation = 0;
	}
	
	//Initializes the variables of the class with the specific given values.
	public Node(Node parent , int nodeDepth , Board nodeBoard , double nodeEvaluation) {
		this.parent = parent;
		children = new ArrayList<Node>(1);
		this.nodeDepth = nodeDepth;
		nodeMove = new int[3];
		this.nodeBoard = nodeBoard;
		this.nodeEvaluation = nodeEvaluation;
	}
	
	//Getters and setters for each corresponding variable in Node class.
	Node getParent() {
		return parent;
	}
	
	void setParent(Node parent) {
		this.parent = parent;
	}
	
	ArrayList<Node> getChildren(){
		return children;
	}
	
	void setChildren(Node child) {
		children.add(child);
	}
	
	int getNodeDepth() {
		return nodeDepth;
	}
	
	void setNodeDepth(int nodeDepth) {
		this.nodeDepth = nodeDepth;
	}
	
	int[] getNodeMove() {
		return nodeMove;
	}
	
	void setNodeMove(int x , int y , int dice) {
		nodeMove[0] = x;
		nodeMove[1] = y;
		nodeMove[2] = dice;
	}
	
	Board getNodeBoard() {
		return nodeBoard;
	}
	
	void setNodeBoard(Board nodeBoard) {
		this.nodeBoard = nodeBoard;
	}
	
	double getNodeEvaluation() {
		return nodeEvaluation;
	}
	
	void setNodeEvaluation(double nodeEvaluation) {
		this.nodeEvaluation = nodeEvaluation;
	}
}
