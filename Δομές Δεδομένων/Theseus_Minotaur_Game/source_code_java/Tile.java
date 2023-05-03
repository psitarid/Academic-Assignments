
public class Tile {
	int tileId;
	int x;
	int y;
	boolean up;
	boolean down;
	boolean left;
	boolean right;
		
	public Tile() {
		tileId = 1;
		x = 0; 
		y = 0;
		up = false;
		down = false;
		left = false;
		right = false;
	}
		
	public Tile(int tId, int wx, int wy, boolean wup, boolean wdown, boolean wleft, boolean wright) {
		tileId = tId;
		x = wx; 
		y = wy;
		up = wup;
		down = wdown;
		left = wleft;
		right = wright;
	}
		
	public Tile(Tile t) {
		tileId = t.tileId;
		x = t.x; 
		y = t.y;
		up = t.up;
		down = t.down;
		left = t.left;
		right = t.right;
	}
		
	public void setTileId(int id) {
		tileId = id;
	}
		
	public void setX(int x1) {
		x = x1;
	}
		
	public void setY(int y1) {
		y = y1;
	}
		
	public void setUp(boolean bup) {
		up = bup;
	}
		
	public void setDown(boolean bdown) {
		down = bdown;
	}
	
	public void setLeft(boolean bleft) {
		left = bleft;
	}
	
	public void setRight(boolean bright) {
		right = bright;
	}
		
	public int getTileId() {
		return tileId;
	}
		
	public int getX() {
		return x;
	}
		
	public int getY() {
		return y;
	}
		
	public boolean getUp() {
		return up;
	}
		
	public boolean getDown() {
		return down;
	}
		
	public boolean getLeft() {
		return left;
	}
		
	public boolean getRight() {
		return right;
	}
}
