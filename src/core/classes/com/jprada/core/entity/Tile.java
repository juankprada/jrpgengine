package com.jprada.core.entity;

import javax.media.opengl.GL;

import com.jprada.core.graphics.LineBatch;
import com.jprada.core.graphics.SpriteBatch;

public class Tile extends MapObject{

	@Override
	public void onCollision(Collidable other) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean collides(Collidable other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onInteract(Interactable other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRender(GL gl, SpriteBatch batch, double interpolation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRenderDebug(GL gl, LineBatch batch, double interpolation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpdate() {
		// TODO Auto-generated method stub
		
	}

}
