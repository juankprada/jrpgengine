package com.jprada.core.entity.map;

public class TilePosition {

	public static final int MAX_LAYERS = 5;
	
	public static final int GROUND_LAYER = 0;
	public static final int MASK_ONE_LAYER = 1;
	public static final int MASK_TWO_LAYER = 2;
	public static final int FRINGE_ONE_LAYER = 3;
	public static final int FRINGE_TWO_LAYER = 4;
	
	private Integer[] tiles = new Integer[MAX_LAYERS];
	
	private int x;
	private int y;
	
	public TilePosition(int x, int y) {
		this.x = x;
		this.y = y;
		
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public Integer getTileAtLayer(int layer) {
		return tiles[layer];
	}
	
	public void setTileAtLayer(Integer tile, int layer) {
		this.tiles[layer] = tile;
	}
	
}
