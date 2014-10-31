package com.jprada.core.entity.map;

import com.jogamp.opengl.math.VectorUtil;
import com.jprada.core.entity.Collidable;
import com.jprada.core.entity.Interactable;
import com.jprada.core.entity.Actor;
import com.jprada.core.graphics.*;
import com.jprada.core.util.Vector2;

import javax.media.opengl.GL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Juan Camilo Prada on 17/10/2014.
 */
public class Tile {

	protected Map<String, Animation> animations = new HashMap<String, Animation>();
	protected Animation currentAnimation;
	
	private Integer id;
	
    

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

 
    public void onRender(GL gl, SpriteBatch batch, double interpolation, float posX, float posY) {
        batch.draw(gl, this.currentAnimation.getNextFrame(), posX, posY);
    }


    public void onRenderDebug(GL gl, LineBatch batch, double interpolation) {

    }


    public void onUpdate() {
    	
    }
    

    public void onCollision(Collidable other) {

    }

}
