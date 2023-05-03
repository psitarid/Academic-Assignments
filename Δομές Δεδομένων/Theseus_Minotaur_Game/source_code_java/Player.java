public class Player {
	int playerId;
	String name;
	Board board;
	Game game;
	int score;
	int x;
	int y;
	
	public Player() {
		playerId = 0;
		name = "";
		score = 0;
		x = 0;
		y = 0;
		game = null;
	}
	
	public Player(int id, String n, Board b, int s, int px, int py , Game game) {
		playerId = id;
		name = n;
		board = b;
		score = s;
		x = px;
		y = py;
		this.game = game;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int newX) {
		x = newX;
	}
	
	public void setY(int newY) {
		y = newY;
	}
	
	public String getName() {
		return name;
	}
	
	public int getScore() {
		return score;
	}
	
	public int whereTo(int dice, int id, Board board) {
		int res = id;
		if (dice == 1 && board.getTiles()[id].up == false) {
			res = id + board.getN();
		}else if (dice == 3 && board.getTiles()[id].right == false) {
			res = id + 1;
		}else if (dice == 5 && board.getTiles()[id].down == false) {
			res = id - board.getN();
		}else if (dice == 7 && board.getTiles()[id].left == false) {
			res = id - 1;
		}else {
			System.out.println("The number of Dice is: " + dice + ". I can't move there is a wall blocking me");
		}
		return res;
	}
	
	int[] move(int id,int pid, int dice, Board board) {
		int[] result = new int[4];
		for (int i = 0; i < result.length; i++) {
			result[i] = 0;
		}
		result[0] = whereTo(dice,id,board);
		for(int i = 0; i<board.getSupplies().length;i++) {
			if(board.getSupplies()[i].getSupplyTileId() == result[0] && pid == 0) {
				System.out.println("I found the supply number " + result[3] + " in the tile " + result[0]);
				board.getSupplies()[i].setSupplyTileId(-1);
				board.getSupplies()[i].setX(-1);
				board.getSupplies()[i].setY(-1);
				score = score + 1;
				result[3] = board.getSupplies()[i].getSupplyId();
			}
		}
		
		result[1] = result[0] / board.getN();
		result[2] = result[0] % board.getN();
		return result;
	}
	
	//Gets as parameter the coordinates of the player and returns the id of the tile he is in.
	public int getPlayerTileId(int x , int y) {
		int id = 0;
		for(int i = 0 ; i < board.getTiles().length ; i++) {
			if (x == board.getTiles()[i].getX() && y == board.getTiles()[i].getY()) {
				id = board.getTiles()[i].getTileId();
			}
		}
		return id;
	}	
		
}
	

