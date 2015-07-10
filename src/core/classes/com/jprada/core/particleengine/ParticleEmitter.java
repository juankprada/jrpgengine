package com.jprada.core.particleengine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.media.opengl.GL;

import com.jprada.core.entity.Actor;
import com.jprada.core.entity.Collidable;
import com.jprada.core.entity.Interactable;
import com.jprada.core.graphics.RenderBatch;
import com.jprada.core.graphics.Sprite;
import com.jprada.core.util.GLColor;

public class ParticleEmitter extends Actor {

	private Random random;
	private float emiterLocationX;
	private float emiterLocationY;
	private List<Particle> particles;
	private Sprite particleSprite;

	
	public ParticleEmitter(List<Sprite> textures, float posx, float posy) {
		this.emiterLocationX = posx;
		this.emiterLocationY = posy;
		this.particles = new ArrayList<Particle>();
		this.random = new Random();
		this.particleSprite = new Sprite("flash.png");
	}
	
	public List<Particle> getParticles() {
		return particles;
	}

	public void setParticles(List<Particle> particles) {
		this.particles = particles;
	}

	public void update() {
		int total = 5;
		
		for(int i=0; i<total; i++) {
			particles.add(generateNewParticle());
		}
		
		Iterator<Particle> it = particles.iterator();
		while(it.hasNext()) {
			Particle p = it.next();
			
			p.update();
			if(p.getTtl() <=0) {
				it.remove();
			}
		}		
		
		
	}
	
	
	private Particle generateNewParticle() {
		int partSprite = random.nextInt(3);
		Sprite sp = this.particleSprite;
		
		
		float posX = this.emiterLocationX;
		float posY = this.emiterLocationY;
		
		float speedX = 0.5f * (float)(random.nextDouble() * 2 -random.nextDouble());
		float speedY = 1f * (float)(random.nextDouble() * 2);
		
		float angle = random.nextInt(360);

		float angularVelocity = 5 * (float)(random.nextDouble() * 2 -1);
		GLColor color = new GLColor(0.596f, 0.164f, 0.0f, random.nextFloat());

		float size = 1.0f;
		int ttl = 60 + random.nextInt(20);
		
		return new Particle(sp, posX, posY, speedX, speedY, angle, angularVelocity, color, size, ttl);
	}
	
	
	public void draw(GL gl, RenderBatch batch) {
		
		batch.setBlendFunc(GL.GL_SRC_ALPHA,GL.GL_ONE);
		batch.begin(gl);
		
		for(int i=0; i< particles.size(); i++) {
			particles.get(i).draw(batch);
		}
		
		batch.end();
		batch.setBlendFunc(GL.GL_SRC_ALPHA,GL.GL_ONE_MINUS_SRC_ALPHA);
		
	}

	public float getEmiterLocationX() {
		return emiterLocationX;
	}

	public void setEmiterLocationX(float emiterLocationX) {
		this.emiterLocationX = emiterLocationX;
	}

	public float getEmiterLocationY() {
		return emiterLocationY;
	}

	public void setEmiterLocationY(float emiterLocationY) {
		this.emiterLocationY = emiterLocationY;
	}

	@Override
	public void onCollision(Collidable other) {
		
	}

	@Override
	public boolean collides(Collidable other) {
		return false;
	}

	@Override
	public boolean onInteract(Interactable other) {
		return false;
	}

	@Override
	public void onCreate() {
	
	}

	@Override
	public void onDestroy() {
				
	}

	@Override
	public void onRender(RenderBatch batch, double interpolation) {
		batch.setBlendFunc(GL.GL_SRC_ALPHA,GL.GL_ONE);
		
		for(int i=0; i< particles.size(); i++) {
			particles.get(i).draw(batch);
		}
		
		
		batch.setBlendFunc(GL.GL_SRC_ALPHA,GL.GL_ONE_MINUS_SRC_ALPHA);
	}

	@Override
	public void onRenderDebug(GL gl, RenderBatch batch, double interpolation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpdate() {
		// TODO Auto-generated method stub
		this.update();
	}
	
	
}
