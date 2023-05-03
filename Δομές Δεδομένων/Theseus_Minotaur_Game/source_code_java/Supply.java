public class Supply {
	int supplyId;
	int x;
	int y;
	int supplyTileId;
	
	public Supply() {
		supplyId = 1;
		x = 0; 
		y = 0;
		supplyTileId = 0;
	}
	
	public Supply(int sId, int sx, int sy, int sTId) {
		supplyId = sId;
		x = sx;
		y = sy;
		supplyTileId = sTId;
	}
	
	public Supply(Supply s) {
		supplyId = s.supplyId;
		x = s.x;
		y = s.y;
		supplyTileId = s.supplyTileId;
	}
	
	public void setSupplyId(int id) {
		supplyId = id;
	}
	
	public void setX(int x1) {
		x = x1;
	}
	
	public void setY(int y1) {
		y = y1;
	}
	
	public void setSupplyTileId(int tid) {
		supplyTileId = tid;
	}
	
	public int getSupplyId() {
		return supplyId;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getSupplyTileId() {
		return supplyTileId;
	}
}
