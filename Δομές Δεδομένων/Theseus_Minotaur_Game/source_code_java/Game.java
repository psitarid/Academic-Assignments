
public class Game {
	int round;
	
	public Game() {
		round = 0;
	}
	
	public Game(int r) {
		round = r;
	}
	
	public int getRound() {
		return round;
	}
	
	public void setRound(int r) {
		this.round = r;
	}
	
	public static void main(String args[]) {
		int N = 15;
		int n = 1000;
		int numsup = 4;
		Game game = new Game(1);
		Board board = new Board(N, numsup, (N * N * 3 + 1) / 2 ); //, (N*N +1)/4
		board.createBoard();
		Player[] players = new Player[1];
		MinMaxPlayer[] MMplayer = new MinMaxPlayer[1];  //Creates an array which contains a MinMaxPlayer object.
		MMplayer[0] = new MinMaxPlayer(0, "Theseus", board, 0, 0, 0, 1, game);  //Creates Theseus object and initializes his variables.
		players[0] = new Player(1, "Minotaur", board, 0, N/2, N/2, game);
		int[] currentPosition = new int[2];
		int newPosition = 0;
		String winnerName = "No one";
		currentPosition[0] = 0;
		currentPosition[1] = N * N / 2;
		String[][]x = board.getStringRepresentation(currentPosition[0], currentPosition[1]);
		for(int i = 0; i< 2*N + 1;i++) {
			for(int j=0; j< N;j++) {
				if(j == N - 1) {
					System.out.println(x[i][j]);
				}
				else {
					System.out.print(x[i][j]);
				}
			}
		}
		
		System.out.println("*********** The game begins **********");
		System.out.println();
		game.round = 0;
		boolean minFlag = false;
		boolean thFlag = false;
		
		for (;;) {
			game.round++;
			System.out.println("********************************** Round " + game.round + " **********************************");
			for (int i = 0; i < 2; i++) {  //Sets i = 0 for Theseus and i = 1 for Minotaur.
				if(i == 0) {
					System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!! Player: " + MMplayer[i].getName() + " !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				}
				else if(i == 1) {
					System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!! Player: " + players[i - 1].getName() + " !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				}
				if(i == 1) {  //Minotaur's move.
					int dice = 0;
					do {
						dice = (int) (Math.random()*8);
					}while(dice % 2 != 1);
					newPosition = players[i - 1].move(currentPosition[i], i, dice, board)[0];
					if (i == 1){
						if(newPosition == currentPosition[i-1]) {
							winnerName = players[i - 1].getName();
							minFlag= true;
							break;
						}
					}
				}
				else if(i == 0) {  //Theseus' move.
					newPosition = MMplayer[i].getNextMove(currentPosition[i], players[0].getPlayerTileId(players[0].getX(), players[0].getY()))[0];
				}
				currentPosition[i] = newPosition;
				if(i == 0) {  //Renews Theseus' coordinates.
					MMplayer[i].setX(newPosition / N);
					MMplayer[i].setY(newPosition % N);
				}
				if (MMplayer[0].getScore() == numsup) {
					winnerName = MMplayer[0].getName();
					thFlag= true;
					break;
				}
				if (i == 0){
					if(newPosition == currentPosition[i+1]) {
						winnerName = players[i].getName();
						minFlag= true;
						break;
					}
				}
				if (i == 0) {
					System.out.println(" Current Position: " + currentPosition[i] + " New Position: " + newPosition
							+ " Player Score: " + MMplayer[i].getScore());
					
				}else {
					System.out.println("Player: " + players[i - 1].getName() + " Current Position: " + currentPosition[i] + " New Position: " + newPosition);

				}
				if(i == 1) {  //Renews Minotaur's coordinates.
					players[i - 1].setX(newPosition/N);
					players[i - 1].setY(newPosition%N);
				}
			}
			String[][]x2 = board.getStringRepresentation(currentPosition[0], currentPosition[1]);
			for(int ii = 0; ii< 2*N + 1;ii++) {
				for(int j=0; j< N;j++) {
					if(j == N - 1) {
						System.out.println(x2[ii][j]);
					}else {
						System.out.print(x2[ii][j]);
					}
				}
			}
			if (game.round >= n || minFlag || thFlag) {
				break;
			}
			MMplayer[0].statistics(false);  //Prints information for every round.
		}
		
		System.out.println();
		MMplayer[0].statistics(true);  //Prints information for the whole game.
		System.out.println();
		System.out.println("Rounds played: " + game.round);
		if(game.round >= 2*n) {
			System.out.println("The game is a tie!!!");
		}else {
			System.out.println(winnerName + " won the game!!!");
		}
	}
}
