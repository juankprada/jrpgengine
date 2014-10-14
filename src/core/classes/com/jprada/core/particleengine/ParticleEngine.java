package com.jprada.core.particleengine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.media.opengl.GL;

import com.jprada.core.graphics.Sprite;
import com.jprada.core.graphics.SpriteBatch;
import com.jprada.core.util.GLColor;

public class ParticleEngine {

	private Random random;
	private float emiterLocationX;
	private float emiterLocationY;
	private List<Particle> particles;
//	private List<Sprite> textures;
	
	public ParticleEngine(List<Sprite> textures, float posx, float posy) {
//		this.textures = textures;
		this.emiterLocationX = posx;
		this.emiterLocationY = posy;
		this.particles = new ArrayList<Particle>();
		this.random = new Random();
	}
	
	public void update() {
		int total = 10;
		
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
		Sprite sp = null;
		switch(partSprite) {
		case 0:
			sp = new Sprite("star.png");
			break;
		case 1:
			sp = new Sprite("circle.png");
			break;
		case 2: 
			sp = new Sprite("diamond.png");
			break;
		default:
			sp = new Sprite("circle.png");
			break;
			
		}
		
		
		float posX = this.emiterLocationX;
		float posY = this.emiterLocationY;
		
		float speedX = 1f * (float)(random.nextDouble() * 2 -1);
		float speedY = 1f * (float)(random.nextDouble() * 2 -1);
		
		float angle = 0;
//		float angularVelocity = 20.0f;
		float angularVelocity = 5 * (float)(random.nextDouble() * 2 -1);
		GLColor color = new GLColor(random.nextFloat(), random.nextFloat(), random.nextFloat(), random.nextFloat());
//		GLColor color = new GLColor(0.5f, 0.5f, 0.5f, 0.5f);
		float size = (float)random.nextDouble();
		int ttl = 20 + random.nextInt(40);
		
		return new Particle(sp, posX, posY, speedX, speedY, angle, angularVelocity, color, size, ttl);
	}
	
	
	public void draw(GL gl, SpriteBatch batch) {
		batch.begin(gl);
		
		for(int i=0; i< particles.size(); i++) {
			particles.get(i).draw(gl, batch);
		}
		
		batch.end(gl);
		
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
	
	
}
