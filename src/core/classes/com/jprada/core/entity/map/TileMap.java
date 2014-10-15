package com.jprada.core.entity.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.media.opengl.GL;

import com.jprada.core.entity.Tile;
import com.jprada.core.graphics.SpriteBatch;

public class TileMap {
	
	private int widthInTiles;
	private int heightInTiles;
	
	private String mapName;
	
	Map<Integer, Tile> tileSet = new HashMap<Integer, Tile>();
	
	List<TilePosition> positions;

	public TileMap(int widthInTiles, int heightInTiles) {
		this.widthInTiles = widthInTiles;
		this.heightInTiles = heightInTiles;
		
		positions = new ArrayList<TilePosition>(widthInTiles * heightInTiles);
	}

	public int getWidthInTiles() {
		return widthInTiles;
	}

	public void setWidthInTiles(int widthInTiles) {
		this.widthInTiles = widthInTiles;
	}

	public int getHeightInTiles() {
		return heightInTiles;
	}

	public void setHeightInTiles(int heightInTiles) {
		this.heightInTiles = heightInTiles;
	}

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
	
	public void onUpdate() {
		for(TilePosition tp : positions) {
			Integer id = tp.getTileAtLayer(TilePosition.GROUND_LAYER);
			
			if(id != null) {
				tileSet.get(id.intValue()).onUpdate();
			}
			
			id = tp.getTileAtLayer(TilePosition.MASK_ONE_LAYER);
			
			if(id != null) {
				tileSet.get(id.intValue()).onUpdate();
			}
			
			id = tp.getTileAtLayer(TilePosition.MASK_TWO_LAYER);
			
			if(id != null) {
				tileSet.get(id.intValue()).onUpdate();
			}
			
			id = tp.getTileAtLayer(TilePosition.FRINGE_ONE_LAYER);
			
			if(id != null) {
				tileSet.get(id.intValue()).onUpdate();
			}
			
			id = tp.getTileAtLayer(TilePosition.FRINGE_TWO_LAYER);
			
			if(id != null) {
				tileSet.get(id.intValue()).onUpdate();
			}
			
		}
	}
	
	
	public void onRender(GL gl, SpriteBatch batch, float interpolation) {
		
		batch.begin(gl);
		for(TilePosition tp : positions) {
			
			Integer id = tp.getTileAtLayer(TilePosition.GROUND_LAYER);
			
			if(id != null) {
				tileSet.get(id.intValue()).onRender(gl, batch, interpolation);
			}
			
			id = tp.getTileAtLayer(TilePosition.MASK_ONE_LAYER);
			
			if(id != null) {
				tileSet.get(id.intValue()).onRender(gl, batch, interpolation);
			}
			
			id = tp.getTileAtLayer(TilePosition.MASK_TWO_LAYER);
			
			if(id != null) {
				tileSet.get(id.intValue()).onRender(gl, batch, interpolation);
			}
			
			id = tp.getTileAtLayer(TilePosition.FRINGE_ONE_LAYER);
			
			if(id != null) {
				tileSet.get(id.intValue()).onRender(gl, batch, interpolation);
			}
			
			id = tp.getTileAtLayer(TilePosition.FRINGE_TWO_LAYER);
			
			if(id != null) {
				tileSet.get(id.intValue()).onRender(gl, batch, interpolation);
			}
			
		}
		
		batch.end(gl);
	}
	

	
}
