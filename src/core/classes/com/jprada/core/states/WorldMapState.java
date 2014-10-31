package com.jprada.core.states;

import javax.media.opengl.GL;

import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseListener;
import com.jprada.core.entity.Actor;
import com.jprada.core.entity.CharacterXMLImporter;
import com.jprada.core.entity.Collidable;
import com.jprada.core.entity.GameCharacter;
import com.jprada.core.entity.Npc;
import com.jprada.core.entity.map.TileMap;
import com.jprada.core.entity.utils.ObjectInteraction;
import com.jprada.core.graphics.LineBatch;
import com.jprada.core.graphics.SpriteBatch;
import com.jprada.core.particleengine.ParticleEngine;
import com.jprada.core.states.input.WorldMapKeyListener;

public class WorldMapState extends GameState {

	private SpriteBatch spriteBatch;
	private LineBatch lineBatch;

//	public static List<Actor> worldMapObjects = new ArrayList<Actor>();

	private KeyListener keyListener;
	
	ParticleEngine particleEngine;
	ParticleEngine particleEngine2;
	ParticleEngine particleEngine3;

//    Tile[][] tileArray = new Tile[25][19];
	
	public static TileMap currentMap;

	@Override
	public KeyListener getKeyListener() {
		return this.keyListener;
	}

	@Override
	public MouseListener getMouseListener() {
		return null;
	}

	@Override
	public void onInit(GL gl) {
		spriteBatch = new SpriteBatch();
		spriteBatch.setup(gl);

		lineBatch = new LineBatch();
		lineBatch.setup(gl);

		PLAYER = (GameCharacter) CharacterXMLImporter.loadCharacterSheet(
				"main-player.xml", GameCharacter.class);
		GameCharacter p2 = (GameCharacter) CharacterXMLImporter
				.loadCharacterSheet("main-player.xml", GameCharacter.class);
		Npc p3 = (Npc) CharacterXMLImporter
				.loadCharacterSheet("main-player.xml", Npc.class);

		p2.setPosX(200);
		p2.setPosY(200);
		
		p3.setPosX(400);
		p3.setPosY(220);
		p3.setOnInteractScript("Wait(2000)\nSetPlayerPosition(300, 300)");

//		this.worldMapObjects.add(PLAYER);
//		this.worldMapObjects.add(p2);
//		this.worldMapObjects.add(p3);
		
		this.keyListener = new WorldMapKeyListener(PLAYER);
		
		particleEngine = new ParticleEngine(null, 200, 200);
		particleEngine2 = new ParticleEngine(null, 200, 200);
		particleEngine3 = new ParticleEngine(null, 400, 220);

		currentMap = new TileMap();
		if(currentMap.getLayers() != null && !currentMap.getLayers().isEmpty()) {
			PLAYER.setCurrentLayer(0);
			currentMap.getLayers().get(0).addActor(PLAYER);
			p2.setCurrentLayer(0);
			currentMap.getLayers().get(0).addActor(p2);
			p3.setCurrentLayer(0);
			currentMap.getLayers().get(0).addActor(p3);
		}
       

	}

	@Override
	public void onUpdate(GL gl) {
		ObjectInteraction.executeInteractions();

		currentMap.onUpdate();
     
//		for (Actor mo : this.worldMapObjects) {
//			mo.onUpdate();
//		}
//		
//		
//
//		for (Actor mo : WorldMapState.worldMapObjects) {
//			for (Actor mo2 : WorldMapState.worldMapObjects) {
//
//				if (!mo.equals(mo2) && mo.isWantToInteract()
//						&& mo2.getInteractBox().collides(mo.getCollideBox())) {
//					ObjectInteraction.ObjectInteractionList
//							.add(new ObjectInteraction(mo, mo2));
//				}
//			}
//
//			mo.setWantToInteract(false);
//		}
		
		particleEngine.setEmiterLocationX(PLAYER.getPosition().x +24);
		particleEngine.setEmiterLocationY(PLAYER.getPosition().y +32);
		particleEngine.update();
		particleEngine2.update();
		particleEngine3.update();
	}

	@Override
	public GameCharacter getPlayer() {
		return this.PLAYER;
	}

	@Override
	public void onRender(GL gl, double interpolation) {
		// TODO Auto-generated method stub
		
		currentMap.onRender(gl, spriteBatch, interpolation);
		
//		int total = particleEngine.getParticles().size() + particleEngine2.getParticles().size() + particleEngine3.getParticles().size();
		
		
		



//        spriteBatch.begin(gl);
//        for(int i=0; i<25; i++) {
//            for(int j=0; j<19; j++) {
//                tileArray[i][j].onRender(gl, spriteBatch, interpolation);
//            }
//        }
//        spriteBatch.end(gl);
//
        particleEngine.draw(gl, spriteBatch);
        particleEngine2.draw(gl, spriteBatch);
        particleEngine3.draw(gl, spriteBatch);

		
//		this.worldMapObjects = RadixSort.sortEntities(this.worldMapObjects);
		
		

//		spriteBatch.begin(gl);


//		for (Actor mo : this.worldMapObjects) {
//			mo.onRender(gl, spriteBatch, interpolation);
//		}
//		spriteBatch.end(gl);

		
		
		// DEBUG INFO
//		if (GameWindow.ENABLE_DEBUG_INFO) {
//			lineBatch.begin(gl);
//			for (Actor mo : this.worldMapObjects) {
//				mo.onRenderDebug(gl, lineBatch, interpolation);
//			}
//			lineBatch.end(gl);
//		}
		
		
		
	}
	
	

	@Override
	public void onFinish(GL gl) {
		// TODO Auto-generated method stub

	}

}
