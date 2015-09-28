package com.jprada.core.entity.map;

import java.util.HashMap;
import java.util.Map;

import com.jogamp.opengl.GL;
import com.jprada.core.entity.Actor.Direction;
import com.jprada.core.entity.Collidable;
import com.jprada.core.entity.Interactable;
import com.jprada.core.entity.utils.CollideBox;
import com.jprada.core.entity.utils.InteractBox;
import com.jprada.core.graphics.Animation;
import com.jprada.core.graphics.RenderBatch;
import com.jprada.core.graphics.SpriteBatch;

/**
 * Created by Juan Camilo Prada on 17/10/2014.
 */
public class Tile implements Collidable, Interactable{

	protected Map<String, Animation> animations = new HashMap<String, Animation>();
	protected Animation currentAnimation;
	
	private Integer id;
	
	protected CollideBox collideBox;
	protected InteractBox interactBox;
	
    

    public Map<String, Animation> getAnimations() {
		return animations;
	}


	public void setAnimations(Map<String, Animation> animations) {
		this.animations = animations;
	}


	public Animation getCurrentAnimation() {
		return currentAnimation;
	}


	public void setCurrentAnimation(Animation currentAnimation) {
		this.currentAnimation = currentAnimation;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Tile() {
    
    }

 
    public void onRender(SpriteBatch batch, double interpolation, float posX, float posY) {
        batch.draw(this.currentAnimation.getNextFrame(), posX, posY);
    }


    public void onRenderDebug(GL gl, RenderBatch batch, double interpolation) {

    }


    public void onUpdate() {
    	
    }
    

    public void onCollision(Collidable other) {

    }


	@Override
	public boolean onInteract(Interactable other) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public InteractBox getInteractBox() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setFacingDirection(Direction direction) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean collides(Collidable other) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public CollideBox getCollideBox() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Direction getFacingDirection() {
		// TODO Auto-generated method stub
		return null;
	}

}
