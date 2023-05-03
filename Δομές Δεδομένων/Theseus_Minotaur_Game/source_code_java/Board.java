public class Board {
	int N;
	int S;
	int W;
	Tile[] tiles;
	Supply[] supplies;
	
	public Board() {
		N = 0;
		S = 0;
		W = 0;
		tiles = new Tile[0];
		supplies = new Supply[0];
	}
	
	public Board(Board b) {
		this.N = b.N;
		this.tiles = new Tile[b.getTiles().length];
		for (int i = 0; i < b.getTiles().length; i++) {
			this.tiles[i] = new Tile(b.getTiles()[i]);
		}
		this.supplies = new Supply[b.getSupplies().length];
		for (int i = 0; i < b.getSupplies().length; i++) {
			this.supplies[i] = new Supply(b.getSupplies()[i]);
		}
	}
	
	public Board(int n, int s, int www) {
		N = n;
		S = s;
		W = www;
		tiles = new Tile[n*n];
		supplies = new Supply[S];
	}
	
	public int getN() {
		return N;
	}
	
	public Tile[] getTiles() {
		return tiles;
	}
	
	public Supply[] getSupplies() {
		return supplies;
	}
	
	public void setN(int n) {
		N = n;
	}
	
	public void setTiles(Tile[] w) {
		tiles = w;
	}
	
	public void setSupplies(Supply[] s) {
		supplies = s;
	}
	
	void createSupply() {
		for (int i = 0; i < supplies.length ; i++) {
			supplies[i] = new Supply();
		}
		for (int i = 0; i < supplies.length ; i++) {
			int tileId;
			boolean F;
			
			// avoid having a supply on a tile that there is a player
			do {
				F = true;
				tileId = (int)(Math.random()*(N*N));
				if (tileId != 0 && tileId != (N * N/2)) {
					F = false;
				}
				for(int j = 0; j < supplies.length ; j++) {
					if(tileId == supplies[j].getSupplyId()) {
						F = true;
						break;
					}
				}
			}while(F);
			
			// create supply
			int sx = tileId / N;
			int sy = tileId % N;
			supplies[i] = new Supply(i, sx , sy, tileId);
		}	
	}
	
	void createTile() {
		for (int i = 0; i < N*N; i++) {
			boolean up = false;
			boolean down = false;
			boolean left = false;
			boolean right = false;
			if (i >= N * (N-1)) {  //last row
				up = true;
				W = W - 1;
			}
			if (i< N && i != 0) { //first row (&& tile!= 1)
				down = true;
				W = W - 1;
			}
			if(i%N == 0) { // first column
				left = true;
				W = W - 1;
			}
			if(i%N == N-1) { // last column
				right = true;
				W = W - 1;
			}

			// create supply
			int sx = i / N;
			int sy = i % N ;
			tiles[i] = new Tile(i, sx , sy, up, down, left, right);
		}
		tiles[0].setDown(true);
		tiles[0].setLeft(true);
		int wallTile;
		for (int i=1; i< N*N;i++) {
			// Check if round tiles
			wallTile = 0;
			boolean up1 = tiles[i].getUp();
			if (up1) {
				wallTile = wallTile + 1;
				W = W - 1;
			}
			boolean down1 = tiles[i].getDown();
			if (down1) {
				wallTile = wallTile + 1;
				W = W - 1;
			}
			boolean left1 = tiles[i].getLeft();
			if (left1) {
				wallTile = wallTile + 1;
				W = W - 1;
			}
			boolean right1 = tiles[i].getRight();
			if (right1) {
				wallTile = wallTile + 1;
				W = W -1;
			}
			
		
			// Check the neighbors
			int neiWallsL = 0;
			int neiWallsD = 0;
			if (i< N && i != 0) { //first row (&& tile!= 1)
				if(tiles[i-1].getRight()) {
					tiles[i].setLeft(true);
					W = W - 1;
					wallTile = wallTile + 1;
				}else {
					if(tiles[i-1].getUp()) {
						neiWallsL = neiWallsL + 1;
					}
					if(tiles[i-1].getDown()) {
						neiWallsL = neiWallsL + 1;
					}
					if(tiles[i-1].getLeft()) {
						neiWallsL = neiWallsL + 1;
					}
				}
			}else if(i%N == 0 && i != 0){
				if(tiles[i - N].getUp()) {
					tiles[i].setDown(true);
					W = W - 1;
					wallTile = wallTile + 1;
				}else {
					if(tiles[i - N].getRight()) {
						neiWallsD = neiWallsD + 1;
					}
					if(tiles[i - N].getDown()) {
						neiWallsD = neiWallsD + 1;
					}
					if(tiles[i - N].getLeft()) {
						neiWallsD = neiWallsD + 1;
					}
				}
			}else {
				if(tiles[i-1].getRight()) {
					tiles[i].setLeft(true);
					W = W - 1;
					wallTile = wallTile + 1;
				}else {
					if(tiles[i-1].getUp()) {
						neiWallsL = neiWallsL + 1;
					}
					if(tiles[i-1].getDown()) {
						neiWallsL = neiWallsL + 1;
					}
					if(tiles[i-1].getLeft()) {
						neiWallsL = neiWallsL + 1;
					}
				}
				if(tiles[i - N].getUp()) {
					tiles[i].setDown(true);
					W = W - 1;
					wallTile = wallTile + 1;
				}else {
					if(tiles[i - N].getRight()) {
						neiWallsD = neiWallsD + 1;
					}
					if(tiles[i - N].getDown()) {
						neiWallsD = neiWallsD + 1;
					}
					if(tiles[i - N].getLeft()) {
						neiWallsD = neiWallsD + 1;
					}
				}
			}
			boolean oneflag = true;
			// Random Up
			if(tiles[i].getUp() == false && wallTile <= 1 && W > 0 && oneflag) {
				tiles[i].setUp(Math.random() > 0.5);
				if(tiles[i].getUp()) {
					wallTile = wallTile + 1;
					W = W - 1;
					oneflag = false;
				}
			}
//			// Random Down
//			if(tiles[i].getDown() == false && wallTile <= 1 && W > 0 && (neiWallsD + 1) <=2 && oneflag) {
//				tiles[i].setDown(Math.random() > 0.5);
//				if(tiles[i].getDown() ) {
//					wallTile = wallTile + 1;
//					W = W - 1;
//					oneflag = false;
//				}
//			}
//			// Random Left
//			if(tiles[i].getLeft() == false && wallTile <= 1 && W > 0 && (neiWallsL + 1) <=2 && oneflag) {
//				tiles[i].setLeft(Math.random() > 0.5);
//				if(tiles[i].getLeft()) {
//					wallTile = wallTile + 1;
//					W = W - 1;
//					oneflag = false;
//				}
//			}
			// Random Right
			if(tiles[i].getRight()  == false&& wallTile <= 1 && W > 0 && oneflag) {
				tiles[i].setRight(Math.random() > 0.5);
				if(tiles[i].getRight()) {
					wallTile = wallTile + 1;
					W = W - 1;
					oneflag = false;
				}
			}			
		}
	}
	
	public void createBoard() {
		// supplies
		createSupply();
		
		// walls
		createTile();
	}
	
	String[][] getStringRepresentation(int ThP, int MiP){
		String[][] table = new String[2*N + 1][N];
		int count2 = -1;
		for(int i= 0; i < N; i++) {
			for(int j = 0; j < 2; j++ ) {
				int count = N * N - (i+1)  * N;
				count2 = count2 + 1;
				for (int z = 0; z < N; z++) {
					if(j == 0) {
						if (tiles[count].getUp()) {
							table[count2][z] = "+ - - - ";
						}else {
							table[count2][z] = "+       ";
						}
						if(z == N - 1) {
							table[count2][z] = table[count2][z] + "+";
						}
					}else {
						boolean flag = false;
						int tem = -1;
						for(int jj = 0;jj < S;jj++) {
							if(supplies[jj].getSupplyTileId() == count && count > 0) {
								flag = true;
								tem = supplies[jj].getSupplyId();
								break;
							}
						}
						if (count == ThP) {
							if (tiles[count].getLeft() && !flag) {
							table[count2][z] ="|   T   ";
							}else if(tiles[count2].getLeft() &&  flag) {
								table[count2][z] = "| T&S" + tem + "  ";
							}else if (flag){
								table[count2][z] = "  T&S" + tem + "  ";						
							}else {	
								table[count2][z] = "    T   ";
							}
						
						} else if (count == MiP) {
							if (tiles[count].getLeft() && !flag) {
								table[count2][z] ="|   M   ";
								}else if(tiles[count].getLeft() &&  flag) {
									table[count2][z] = "|  M&S" + tem + "  ";
								}else if (flag){
									table[count2][z] = "  M&S" + tem + "  ";						
								}else {	
									table[count2][z] = "    M   ";
								}
						}else {
							if (tiles[count].getLeft() && !flag) {
								table[count2][z] ="|       ";
								}else if(tiles[count].getLeft() &&  flag) {
									table[count2][z] = "|  S" + tem + "   ";
								}else if (flag){
									table[count2][z] = "   S" + tem + "   ";						
								}else {	
									table[count2][z] = "        ";
								}
						}
						
						if(z == N - 1) {
							if (tiles[count].getRight()) {
								table[count2][z] = table[count2][z] + "|";
							}else {
								table[count2][z] = table[count2][z] + " ";
							}
							if(i == N - 1) {
								count2 = count2 + 1;
							}
						}
					}
					count = count + 1;
				}
			}
		}
		for(int j=0; j<N;j++) {
			if (tiles[j].getDown()) {
				table[2*N][j] = "+ - - - ";
			}else {
				table[2*N][j] = "+       ";
			}
			if(j == N - 1) {
				table[2*N][j] = table[2*N][j] + "+";
			}
		}
		return table;
	}

	
}
