package com.jprada.core.entity.map;

import java.util.HashMap;
import java.util.List;

import com.jprada.core.util.Vector2;

public class Layer {

	private Integer[][] cels;
	public boolean render;
	
	
	public Layer(int hTiles, int vTiles) {
		this.cels = new Integer[hTiles][vTiles];
		this.render = true;
	}
	
	public Layer(int width, int height, int tileWidth, int tileHeight) {	
		int hCels =  Math.round(width / tileWidth);
		int vCels = Math.round(height / tileHeight);
		this.cels = new Integer[hCels][vCels];
	}
	
	
	public void addTile(Integer tileId, int xPos, int yPos) {

		this.cels[xPos][yPos] = tileId;
		
	}
	
	
	public Integer getTileAt(int xPos, int yPos) {
		return this.cels[xPos][yPos];
	}
	
}
