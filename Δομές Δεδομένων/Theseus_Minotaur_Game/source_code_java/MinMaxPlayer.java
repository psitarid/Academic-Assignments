import java.util.ArrayList;
import java.util.Random; 

public class MinMaxPlayer extends Player{
	
	ArrayList<Integer[]> path;
	//Counts the number of movements Theseus has done in every direction.
		int up_num;
		int down_num;
		int right_num;
		int left_num;
	
	public MinMaxPlayer(){
		super();
		path = new ArrayList<Integer[]>();
		up_num = 0;
		down_num = 0;
		right_num = 0;
		left_num = 0;
	}
	
	//Initializes the variables of the class with the specific given values.
	public MinMaxPlayer(int id, String n, Board b, int s, int px, int py, int k, Game game){
		super(id, n, b, s, px, py, game);
		path = new ArrayList<Integer[]>(k);
		up_num = 0;
		down_num = 0;
		right_num = 0;
		left_num = 0;
	}
	
	//Gets as parameters the distance of the nearest supply and the opponent and returns the value of the move.
	public double getMoveValue(double NearSupplies,double NearOpponent) {
		double f = (NearSupplies * 0.46) + (NearOpponent *  0.54);
		return f;
	}
	
	//Evaluates a move of each player depending on the playerId and determines how this affects Theseus.
	double evaluate(int currentPos, int dice, Board board , int MinId , int playerId) {
		double evaluation = 0;
		double nearSupply = 0;
		double OpponentDistance = 0;
		if(playerId == 0) { //Evaluation of Theseus' move
			if(dice == 1) {
				//Checks if there is a supply between Theseus and Minotaur, and prevents Theseus from taking the risk to collect it
				//except for the case there is only one supply left.
				for(int j = 0 ; j < board.getSupplies().length ; j++) {
					if(MinId == currentPos + (2 * board.getN()) && board.getSupplies()[j].getSupplyTileId() == currentPos + board.getN() && score != 3){
						OpponentDistance = -1;
						nearSupply = 0;
						break;
					}
					//Checks if there is Minotaur in the next three tiles and gives the right value to the opponentDistance.
					else{
						if(MinId == currentPos + board.getN()) {
							OpponentDistance = -1;
						}
						else if(MinId == currentPos + 2 * board.getN()) {
							OpponentDistance = -0.5;
						}
						else if(MinId == currentPos + 3 * board.getN()) {
							OpponentDistance = -0.3;
						}
						else if(MinId == currentPos - board.getN()) {
							OpponentDistance = 1;
						}
						else if(MinId == currentPos - 2 * board.getN()) {
							OpponentDistance = 0.5;
						}
						else if(MinId == currentPos - 3 * board.getN()) {
							OpponentDistance = 0.3;
						}
						//Checks if there is any supply in the next three tiles and gives the right value to the nearSupply.
						for(int i = 0 ; i< board.getSupplies().length ; i++) {
							if(board.getSupplies()[i].getSupplyTileId() == currentPos + board.getN()) {
								nearSupply = +1;
							}
							else if(board.getSupplies()[i].getSupplyTileId() == currentPos + 2 * board.getN()) {
								nearSupply = +0.5;
							}
							else if(board.getSupplies()[i].getSupplyTileId() == currentPos + 3 * board.getN()){
								nearSupply = +0.3;
							}
							else if(board.getSupplies()[i].getSupplyTileId() == currentPos - board.getN()) {
								nearSupply = -1;
							}
							else if(board.getSupplies()[i].getSupplyTileId() == currentPos - 2 * board.getN()) {
								nearSupply = -0.5;
							}
							else if(board.getSupplies()[i].getSupplyTileId() == currentPos - 3 * board.getN()){
								nearSupply = -0.3;
							}
						}
					}
				}
				evaluation = getMoveValue(nearSupply, OpponentDistance);
			}
			if(dice == 3) {
				for(int j = 0 ; j < board.getSupplies().length ; j++) {
					if(MinId == currentPos + 2 && board.getSupplies()[j].getSupplyTileId() == currentPos + 1 && score != 3){
						OpponentDistance = -1;
						nearSupply = 0;
						break;
					}
					else{
						if(MinId == currentPos + 1) {
							OpponentDistance = -1;
						}
						else if(MinId == currentPos + 2) {
							OpponentDistance = -0.5;
						}
						else if(MinId == currentPos + 3) {
							OpponentDistance = -0.3;
						}
						else if(MinId == currentPos - 1) {
							OpponentDistance = 1;
						}
						else if(MinId == currentPos - 2) {
							OpponentDistance = 0.5;
						}
						else if(MinId == currentPos - 3) {
							OpponentDistance = 0.3;
						}
						for(int i = 0 ; i< board.getSupplies().length ; i++) {
							if(board.getSupplies()[i].getSupplyTileId() == currentPos + 1) {
								nearSupply = +1;
							}
							else if(board.getSupplies()[i].getSupplyTileId() == currentPos + 2) {
								nearSupply = +0.5;
							}
							else if(board.getSupplies()[i].getSupplyTileId() == currentPos + 3){
								nearSupply= +0.3;
							}
							else if(board.getSupplies()[i].getSupplyTileId() == currentPos - 1) {
								nearSupply = -1;
							}
							else if(board.getSupplies()[i].getSupplyTileId() == currentPos - 2) {
								nearSupply = -0.5;
							}
							else if(board.getSupplies()[i].getSupplyTileId() == currentPos - 3){
								nearSupply= -0.3;
							}
						}
					}
				}
				evaluation = getMoveValue(nearSupply, OpponentDistance);
			}
			if(dice == 5) {
				for(int j = 0 ; j < board.getSupplies().length ; j++) {
					if(MinId == currentPos - (2 * board.getN()) && board.getSupplies()[j].getSupplyTileId() == currentPos - board.getN() && score != 3){
						OpponentDistance = -1;
						nearSupply = 0;
						break;
					}
					else{
						if(MinId == currentPos - board.getN()) {
							OpponentDistance = -1;
						}
						else if(MinId == currentPos - 2 * board.getN()) {
							OpponentDistance = -0.5;
						}
						else if(MinId == currentPos - 3 * board.getN()) {
							OpponentDistance = -0.3;
						}
						else if(MinId == currentPos + board.getN()) {
							OpponentDistance = 1;
						}
						else if(MinId == currentPos + 2 * board.getN()) {
							OpponentDistance = 0.5;
						}
						else if(MinId == currentPos + 3 * board.getN()) {
							OpponentDistance = 0.3;
						}
						for(int i = 0 ; i< board.getSupplies().length ; i++) {
							if(board.getSupplies()[i].getSupplyTileId() == currentPos - board.getN()) {
								nearSupply= +1;
							}
							else if(board.getSupplies()[i].getSupplyTileId() == currentPos - 2 * board.getN()) {
								nearSupply= +0.5;
							}
							else if(board.getSupplies()[i].getSupplyTileId() == currentPos - 3 * board.getN()){
								nearSupply= +0.3;
							}
							else if(board.getSupplies()[i].getSupplyTileId() == currentPos + board.getN()) {
								nearSupply= -1;
							}
							else if(board.getSupplies()[i].getSupplyTileId() == currentPos + 2 * board.getN()) {
								nearSupply= -0.5;
							}
							else if(board.getSupplies()[i].getSupplyTileId() == currentPos + 3 * board.getN()){
								nearSupply= -0.3;
							}
						}
					}
				}
				evaluation = getMoveValue(nearSupply, OpponentDistance);
			}
			if(dice == 7) {
				for(int j = 0 ; j < board.getSupplies().length ; j++) {
					if(MinId == currentPos - 2 && board.getSupplies()[j].getSupplyTileId() == currentPos - 1 && score != 3){
						OpponentDistance = -1;
						nearSupply = 0;
						break;
					}
					else{
						if(MinId == currentPos - 1) {
							OpponentDistance = -1;
						}
						else if(MinId == currentPos - 2) {
							OpponentDistance = -0.5;
						}
						else if(MinId == currentPos - 3) {
							OpponentDistance = -0.3;
						}
						else if(MinId == currentPos + 1) {
							OpponentDistance = 1;
						}
						else if(MinId == currentPos + 2) {
							OpponentDistance = 0.5;
						}
						else if(MinId == currentPos + 3) {
							OpponentDistance = 0.3;
						}
						for(int i = 0 ; i< board.getSupplies().length ; i++) {
							if(board.getSupplies()[i].getSupplyTileId() == currentPos - 1) {
								nearSupply = +1;
							}
							else if(board.getSupplies()[i].getSupplyTileId() == currentPos - 2) {
								nearSupply = +0.5;
							}
							else if(board.getSupplies()[i].getSupplyTileId() == currentPos - 3){
								nearSupply= +0.3;
							}
							else if(board.getSupplies()[i].getSupplyTileId() == currentPos + 1) {
								nearSupply = -1;
							}
							else if(board.getSupplies()[i].getSupplyTileId() == currentPos + 2) {
								nearSupply = -0.5;
							}
							else if(board.getSupplies()[i].getSupplyTileId() == currentPos + 3){
								nearSupply= -0.3;
							}
						}
					}
				}
				evaluation = getMoveValue(nearSupply, OpponentDistance);
			}
		}
		else { //Evaluation of Minotaur's move and how this affects Theseus.
			if(dice == 1) {
				//Checks if there is Minotaur in the next three tiles and gives the right value to the opponentDistance.
				if(MinId == currentPos - board.getN()) {
					OpponentDistance = 1;
				}
				else if(MinId == currentPos - 2 * board.getN()) {
					OpponentDistance = 0.5;
				}
				else if(MinId == currentPos - 3 * board.getN()) {
					OpponentDistance = 0.3;
				}
				else if(MinId == currentPos + board.getN()) {
					OpponentDistance = -1;
				}
				else if(MinId == currentPos + 2 * board.getN()) {
					OpponentDistance = -0.5;
				}
				else if(MinId == currentPos + 3 * board.getN()) {
					OpponentDistance = -0.3;
				}
				evaluation = getMoveValue(nearSupply, OpponentDistance);
			}
			if(dice == 3) {
				if(MinId == currentPos - 1) {
					OpponentDistance = 1;
				}
				else if(MinId == currentPos - 2) {
					OpponentDistance = 0.5;
				}
				else if(MinId == currentPos - 3) {
					OpponentDistance = 0.3;
				}
				else if(MinId == currentPos + 1) {
					OpponentDistance = -1;
				}
				else if(MinId == currentPos + 2) {
					OpponentDistance = -0.5;
				}
				else if(MinId == currentPos + 3) {
					OpponentDistance = -0.3;
				}
				evaluation = getMoveValue(nearSupply, OpponentDistance);
			}
			if(dice == 5) {
				if(MinId == currentPos + board.getN()) {
					OpponentDistance = 1;
				}
				else if(MinId == currentPos + 2 * board.getN()) {
					OpponentDistance = 0.5;
				}
				else if(MinId == currentPos + 3 * board.getN()) {
					OpponentDistance = 0.3;
				}
				else if(MinId == currentPos - board.getN()) {
					OpponentDistance = -1;
				}
				else if(MinId == currentPos - 2 * board.getN()) {
					OpponentDistance = -0.5;
				}
				else if(MinId == currentPos - 3 * board.getN()) {
					OpponentDistance = -0.3;
				}
				evaluation = getMoveValue(nearSupply, OpponentDistance);
			}
			if(dice == 7) {
				if(MinId == currentPos + 1) {
					OpponentDistance = 1;
				}
				else if(MinId == currentPos + 2) {
					OpponentDistance = 0.5;
				}
				else if(MinId == currentPos + 3) {
					OpponentDistance = 0.3;
				}
				else if(MinId == currentPos - 1) {
					OpponentDistance = -1;
				}
				else if(MinId == currentPos - 2) {
					OpponentDistance = -0.5;
				}
				else if(MinId == currentPos - 3) {
					OpponentDistance = -0.3;
				}
				evaluation = getMoveValue(nearSupply, OpponentDistance);
			}
		}
		return evaluation;
	}
	
	//Both createMySubtree and createOpponentSubtree create the tree.
	void createMySubtree(int currentPos, int opponentCurrentPos, Node root,int depth) {
		if(board.getTiles()[currentPos].getUp() == false) {
			Node T1;
			T1 = new Node(root , depth , root.getNodeBoard() , 0);
			T1.setNodeMove(getX() , getY() , 1);
			root.setChildren(T1);
			T1.setNodeEvaluation(evaluate(currentPos , 1 , T1.getNodeBoard() , opponentCurrentPos , 0));
			move(currentPos , 0 , 1 , T1.getNodeBoard());
			createOpponentSubtree(currentPos , opponentCurrentPos , T1 , depth + 1 , T1.getNodeEvaluation());
			}
		if(board.getTiles()[currentPos].getRight() == false) {
			Node T2;
			T2 = new Node(root , depth , root.getNodeBoard() , 0);
			T2.setNodeMove(getX() , getY() , 3);
			root.setChildren(T2);
			T2.setNodeEvaluation(evaluate(currentPos , 3 , T2.getNodeBoard() , opponentCurrentPos , 0));
			move(currentPos , 0 , 3 , T2.getNodeBoard());
			createOpponentSubtree(currentPos , opponentCurrentPos , T2 , depth + 1 , T2.getNodeEvaluation());
		}
		if(board.getTiles()[currentPos].getDown() == false) {
			Node T3;
			T3 = new Node(root , depth , root.getNodeBoard() , 0);
			T3.setNodeMove(getX() , getY() , 5);
			root.setChildren(T3);
			T3.setNodeEvaluation(evaluate(currentPos , 5 , T3.getNodeBoard() , opponentCurrentPos , 0));
			move(currentPos , 0 , 5 , T3.getNodeBoard());
			createOpponentSubtree(currentPos , opponentCurrentPos , T3 , depth + 1 , T3.getNodeEvaluation());
		}
		if(board.getTiles()[currentPos].getLeft() == false) {
			Node T4;
			T4 = new Node(root , depth , root.getNodeBoard() , 0);
			T4.setNodeMove(getX() , getY() , 7);
			root.setChildren(T4);
			T4.setNodeEvaluation(evaluate(currentPos , 7 , T4.getNodeBoard() , opponentCurrentPos , 0));
			move(currentPos , 0 , 7 , T4.getNodeBoard());
			createOpponentSubtree(currentPos , opponentCurrentPos , T4 , depth + 1 , T4.getNodeEvaluation());
		}
	}
	
	//Creates the second level of the tree for one possible move of Theseus.
	void createOpponentSubtree(int currentPos, int opponentCurrentPos, Node parent,int depth, double parentEval) {
		if(board.getTiles()[opponentCurrentPos].getUp() == false) {
			Node M1;
			M1 = new Node(parent , depth , parent.getNodeBoard() , 0);
			M1.setNodeMove(super.getX() , super.getY(), 1);
			parent.setChildren(M1);
			M1.setNodeEvaluation(parentEval - evaluate(currentPos , 1 , M1.getNodeBoard() , opponentCurrentPos , 1));
			move(opponentCurrentPos , 1 , 1 , M1.getNodeBoard());
		}
		if(board.getTiles()[opponentCurrentPos].getRight() == false) {
			Node M2;
			M2 = new Node(parent , depth , parent.getNodeBoard() , 0);
			M2.setNodeMove(super.getX() , super.getY(), 3);
			parent.setChildren(M2);
			M2.setNodeEvaluation(parentEval - evaluate(currentPos , 3 , M2.getNodeBoard() , opponentCurrentPos , 1));
			move(opponentCurrentPos , 1 , 3 , M2.getNodeBoard());
		}
		if(board.getTiles()[opponentCurrentPos].getDown() == false) {
			Node M3;
			M3 = new Node(parent , depth , parent.getNodeBoard() , 0);
			M3.setNodeMove(super.getX() , super.getY(), 5);
			parent.setChildren(M3);
			M3.setNodeEvaluation(parentEval - evaluate(currentPos , 5 , M3.getNodeBoard() , opponentCurrentPos , 1));
			move(opponentCurrentPos , 1 , 5 , M3.getNodeBoard());
		}
		if(board.getTiles()[opponentCurrentPos].getLeft() == false) {
			Node M4;
			M4 = new Node(parent , depth , parent.getNodeBoard() , 0);
			M4.setNodeMove(super.getX() , super.getY(), 7);
			parent.setChildren(M4);
			M4.setNodeEvaluation(parentEval - evaluate(currentPos , 7 , M4.getNodeBoard() , opponentCurrentPos , 1));
			move(opponentCurrentPos , 1 , 7 , M4.getNodeBoard());
		}
	}
	
	//Chooses the best move for Theseus through the tree, taking into account Minotaur's move.
	int chooseMinMaxMove(Node root) {
		double min = 0;		//The minimum evaluation of the nodes of the second level.
		double max = 0;		//The maximum evaluation of the nodes of the first level.
		for(int i = 0 ; i < root.getChildren().size() ; i++) {	//For each root's children.
			min  = root.getChildren().get(i).getChildren().get(0).getNodeEvaluation();
			for(int j = 0 ; j < root.getChildren().get(i).getChildren().size() ; j++) {		//Finds the minimum evaluation.
				if(root.getChildren().get(i).getChildren().get(j).getNodeEvaluation() < min) {
					min = root.getChildren().get(i).getChildren().get(j).getNodeEvaluation();
				}
			}
			root.getChildren().get(i).setNodeEvaluation(min);
		}
		
		max = root.getChildren().get(0).getNodeEvaluation();
		for(int i = 0 ; i < root.getChildren().size() ; i++) {		//Finds the maximum evaluation.
			if(root.getChildren().get(i).getNodeEvaluation() > max) {
				max = root.getChildren().get(i).getNodeEvaluation();
			}
		}
		
		ArrayList<Integer> bestMoves;	//Contains Theseus' best possible moves.
		bestMoves = new ArrayList<Integer>(1);
		for(int i = 0 ; i < root.getChildren().size() ; i++) {
			if(root.getChildren().get(i).getNodeEvaluation() == max) {		//Finds Theseus' equally best moves.
				bestMoves.add(root.getChildren().get(i).getNodeMove()[2]);
			}
		}
		
		//In case there are two or more moves with the best evaluation, chooses randomly one of them.
		Random rand = new Random();
		int index = rand.nextInt(bestMoves.size());
		int new_dice = bestMoves.get(index);
		root.setNodeMove(getX() , getY() , new_dice);
		return new_dice;
	}
	
	//With the help of the previous functions, chooses the best move, moves the player, updates path, and returns the best move.
	int[] getNextMove(int currentPos, int opponentCurrentPos) {
		Node root;
		root = new Node(null , 0 , board , 0);
		createMySubtree(currentPos , opponentCurrentPos , root , 1);
		Integer p[];  //Contains path's information for one move.
		p = new Integer[4];
		p[0] = chooseMinMaxMove(root);
		
		//Gives value to the elements of p. (Checking in front and behind the player.)
		if (p[0] == 1) {
			for(int i = 0 ; i < board.getSupplies().length ; i++) {
				if(board.getSupplies()[i].getSupplyTileId() == currentPos + board.getN() && board.getTiles()[currentPos].getUp() == false && opponentCurrentPos != currentPos + (2 * board.getN())){
					p[1] = 1;
					p[2] = 0;
					break;
				}
				else{
					p[1] = 0;
				}
				if(board.getSupplies()[i].getSupplyTileId() == currentPos + 2 * board.getN() && board.getTiles()[currentPos].getUp() == false && board.getTiles()[currentPos + board.getN()].getUp() == false) {
					p[2] = 1;
					break;
				}
				else if(board.getSupplies()[i].getSupplyTileId() == currentPos + 3 * board.getN() && board.getTiles()[currentPos].getUp() == false && board.getTiles()[currentPos + board.getN()].getUp() == false && board.getTiles()[currentPos + (2 * board.getN())].getUp() == false) {
					p[2] = 2;
					break;
				}
				else if(board.getSupplies()[i].getSupplyTileId() == currentPos - board.getN() && board.getTiles()[currentPos].getDown() == false) {
					p[2] = 2;
					break;
				}
				else if(board.getSupplies()[i].getSupplyTileId() == currentPos - 2 * board.getN() && board.getTiles()[currentPos].getDown() == false && board.getTiles()[currentPos - board.getN()].getDown() == false) {
					p[2] = 3;
					break;
				}
				else p[2] = -1;
			}
			
			if(opponentCurrentPos == currentPos + 3 * board.getN() && board.getTiles()[currentPos].getUp() == false && board.getTiles()[currentPos + board.getN()].getUp() == false && board.getTiles()[currentPos + (2 * board.getN())].getUp() == false){
				p[3] = 2;
			}
			else if(opponentCurrentPos == currentPos - board.getN() && board.getTiles()[currentPos].getDown() == false){
				p[3] = 2;
			}
			else if(opponentCurrentPos == currentPos - 2 * board.getN() && board.getTiles()[currentPos].getDown() == false && board.getTiles()[currentPos - board.getN()].getDown() == false){
				p[3] = 3;
			}
			else {
				p[3] = -1;
			}
		}
		
		if (p[0] == 3) {
			for(int i = 0 ; i < board.getSupplies().length ; i++) {
				if(board.getSupplies()[i].getSupplyId() == currentPos + 1 && board.getTiles()[currentPos].getRight() == false && opponentCurrentPos != currentPos + 2){
					p[1] = 1;
					p[2] = 0;
					break;
				}
				else{
					p[1] = 0;
				}
				if(board.getSupplies()[i].getSupplyTileId() == currentPos + 2 && board.getTiles()[currentPos].getRight() == false && board.getTiles()[currentPos + 1].getRight() == false) {
					p[2] = 1;
					break;
				}
				else if(board.getSupplies()[i].getSupplyTileId() == currentPos + 3 && board.getTiles()[currentPos].getRight() == false && board.getTiles()[currentPos + 1].getRight() == false && board.getTiles()[currentPos + 2].getRight() == false) {
					p[2] = 2;
					break;
				}
				else if(board.getSupplies()[i].getSupplyTileId() == currentPos - 1 && board.getTiles()[currentPos].getLeft() == false) {
					p[2] = 2;
					break;
				}
				else if(board.getSupplies()[i].getSupplyTileId() == currentPos - 2 && board.getTiles()[currentPos].getLeft() == false && board.getTiles()[currentPos - 1].getLeft() == false) {
					p[2] = 3;
					break;
				}
				else {
					p[2] = -1;
				}
			}
			
			if(opponentCurrentPos == currentPos + 3 && board.getTiles()[currentPos].getRight() == false && board.getTiles()[currentPos + 1].getRight() == false && board.getTiles()[currentPos + 2].getRight() == false){
				p[3] = 2;
			}
			else if(opponentCurrentPos == currentPos - 1 && board.getTiles()[currentPos].getLeft() == false){
				p[3] = 2;
			}
			else if(opponentCurrentPos == currentPos - 2 && board.getTiles()[currentPos].getLeft() == false && board.getTiles()[currentPos - 1].getLeft() == false){
				p[3] = 3;
			}
			else {
				p[3] = -1;
			}
		}
		
		if (p[0] == 5) {
			for(int i = 0 ; i < board.getSupplies().length ; i++) {
				if(board.getSupplies()[i].getSupplyTileId() == currentPos - board.getN() && board.getTiles()[currentPos].getDown() == false && opponentCurrentPos != currentPos - (2 * board.getN())){
					p[1] = 1;
					p[2] = 0;
					break;
				}
				else{
					p[1] = 0;
				}
				if(board.getSupplies()[i].getSupplyTileId() == currentPos - 2 * board.getN() && board.getTiles()[currentPos].getDown() == false && board.getTiles()[currentPos - board.getN()].getDown() == false) {
					p[2] = 1;
					break;
				}
				else if(board.getSupplies()[i].getSupplyTileId() == currentPos - 3 * board.getN() && board.getTiles()[currentPos].getDown() == false && board.getTiles()[currentPos - board.getN()].getDown() == false && board.getTiles()[currentPos - (2 * board.getN())].getDown() == false) {
					p[2] = 2;
					break;
				}
				else if(board.getSupplies()[i].getSupplyTileId() == currentPos + board.getN() && board.getTiles()[currentPos].getUp() == false) {
					p[2] = 2;
					break;
				}
				else if(board.getSupplies()[i].getSupplyTileId() == currentPos + 2 * board.getN() && board.getTiles()[currentPos].getUp() == false && board.getTiles()[currentPos + board.getN()].getUp() == false) {
					p[2] = 3;
					break;
				}
				else p[2] = -1;
			}
			if(opponentCurrentPos == currentPos - 3 * board.getN() && board.getTiles()[currentPos].getDown() == false && board.getTiles()[currentPos - board.getN()].getDown() == false && board.getTiles()[currentPos - (2 * board.getN())].getDown() == false){
				p[3] = 2;
			}
			else if(opponentCurrentPos == currentPos + board.getN() && board.getTiles()[currentPos].getUp() == false){
				p[3] = 2;
			}
			else if(opponentCurrentPos == currentPos + 2 * board.getN() && board.getTiles()[currentPos].getUp() == false && board.getTiles()[currentPos + board.getN()].getUp() == false){
				p[3] = 3;
			}
			else {
				p[3] = -1;
			}
		}
			
		if (p[0] == 7) {
			for(int i = 0 ; i < board.getSupplies().length ; i++) {
				if(board.getSupplies()[i].getSupplyId() == currentPos - 1 && board.getTiles()[currentPos].getLeft() == false && opponentCurrentPos != currentPos - 2){
					p[1] = 1;
					p[2] = 0;
					break;
				}
				else{
					p[1] = 0;
				}
				if(board.getSupplies()[i].getSupplyTileId() == currentPos - 2 && board.getTiles()[currentPos].getLeft() == false && board.getTiles()[currentPos - 1].getLeft() == false) {
					p[2] = 1;
					break;
				}
				else if(board.getSupplies()[i].getSupplyTileId() == currentPos - 3 && board.getTiles()[currentPos].getLeft() == false && board.getTiles()[currentPos - 1].getLeft() == false && board.getTiles()[currentPos - 2].getLeft() == false) {
					p[2] = 2;
					break;
				}
				else if(board.getSupplies()[i].getSupplyTileId() == currentPos + 1 && board.getTiles()[currentPos].getRight() == false) {
					p[2] = 2;
					break;
				}
				else if(board.getSupplies()[i].getSupplyTileId() == currentPos + 2 && board.getTiles()[currentPos].getRight() == false && board.getTiles()[currentPos + 2].getRight() == false) {
					p[2] = 3;
					break;
				}
				else p[2] = -1;
			}
			if(opponentCurrentPos == currentPos - 3 && board.getTiles()[currentPos].getLeft() == false && board.getTiles()[currentPos - 1].getLeft() == false && board.getTiles()[currentPos - 2].getLeft() == false){
				p[3] = 2;
			}
			else if(opponentCurrentPos == currentPos + 1 && board.getTiles()[currentPos].getRight() == false){
				p[3] = 2;
			}
			else if(opponentCurrentPos == currentPos + 2 && board.getTiles()[currentPos].getRight() == false && board.getTiles()[currentPos + 1].getRight() == false){
				p[3] = 3;
			}
			else {
				p[3] = -1;
			}
		}
		
		if(p[0] == 1) {
			up_num++;
		}
		else if(p[0] == 3){
			right_num++;
		}
		else if(p[0] == 5) {
			down_num++;
		}
		else if(p[0] == 7) {
			left_num++;
		}
		
		path.add(p);
		return move(currentPos , 0 , chooseMinMaxMove(root) , board);
	}
	
	//Chooses what to print depending on boolean variable's value.
		public void statistics(boolean x) {
			if(path.size() == 1) {  //After every round.
				System.out.println("Theseus at round " + game.getRound() + " set the dice " + path.get(path.size() - 1)[0] + ".");
			}
			else {
				if(path.get(path.size() - 2)[2] > path.get(path.size() - 1)[2] && path.get(path.size() - 2)[3] < path.get(path.size() - 1)[3]) {
					System.out.println("Theseus at round " + game.getRound() + " set the dice " + path.get(path.size() - 1)[0] + ", got closer to a supply and got away from Minotaur.");
				}
				else if(path.get(path.size() - 2)[2] > path.get(path.size() - 1)[2]) {
					System.out.println("Theseus at round " + game.getRound() + " set the dice " + path.get(path.size() - 1)[0] + " and got closer to a supply.");
				}
				else if(path.get(path.size() - 2)[3] < path.get(path.size() - 1)[3]) {
					System.out.println("Theseus at round " + game.getRound() + " set the dice " + path.get(path.size() - 1)[0] + " and got away from Minotaur.");
				}
				else {
					System.out.println("Theseus at round " + game.getRound() + " set the dice " + path.get(path.size() - 1)[0] + ".");
				}
			}
			System.out.println("The number of supplies Theseus has got is " + score);
			System.out.println("The distance of Theseus to the closest supply is " + path.get(path.size() - 1)[2]);
			System.out.println("The distance of Theseus to Minotaur is " + path.get(path.size() - 1)[3]);
			if(x) {  //When game is over.
				System.out.println("*********** The game is over ***********\n");
				System.out.println("Theseus went up " + up_num + " times.");
				System.out.println("Theseus went right " + right_num + " times.");
				System.out.println("Theseus went down " + down_num + " times.");
				System.out.println("Theseus went left " + left_num + " times.");
			}
		}
}