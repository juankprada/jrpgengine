package com.jprada.core.entity.map;

import java.util.ArrayList;
import java.util.List;

import com.jprada.core.entity.Actor;

public class Layer {

	private List<Actor> actorsInLayer;
	
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
	
	public void addActor(Actor actor) {
		if(this.actorsInLayer == null) {
			this.actorsInLayer = new ArrayList<Actor>();
		}
		this.actorsInLayer.add(actor);
	}
	
	public List<Actor> getActors() {
		return actorsInLayer;
	}
	
	public void addTile(Integer tileId, int xPos, int yPos) {

		this.cels[xPos][yPos] = tileId;
		
	}
	
	
	public Integer getTileAt(int xPos, int yPos) {
		return this.cels[xPos][yPos];
	}
	
}
