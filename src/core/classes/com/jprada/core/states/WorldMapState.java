package com.jprada.core.states;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;
import javax.script.ScriptException;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseListener;
import com.jprada.core.GameWindow;
import com.jprada.core.entity.CharacterXMLImporter;
import com.jprada.core.entity.GameCharacter;
import com.jprada.core.entity.MapObject;
import com.jprada.core.entity.MapObject.Direction;
import com.jprada.core.entity.Npc;
import com.jprada.core.entity.utils.ObjectInteraction;
import com.jprada.core.events.JythonManager;
import com.jprada.core.graphics.LineBatch;
import com.jprada.core.graphics.SpriteBatch;
import com.jprada.core.particleengine.ParticleEngine;
import com.jprada.core.states.input.WorldMapKeyListener;
import com.jprada.core.util.GLColor;
import com.jprada.core.util.algorithm.RadixSort;

public class WorldMapState extends GameState {

	private SpriteBatch spriteBatch;
	private LineBatch lineBatch;

	public static List<MapObject> worldMapObjects = new ArrayList<MapObject>();

	private KeyListener keyListener;
	
	ParticleEngine particleEngine;

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

		this.worldMapObjects.add(PLAYER);
		this.worldMapObjects.add(p2);
		this.worldMapObjects.add(p3);
		
		this.keyListener = new WorldMapKeyListener(PLAYER);
		
		particleEngine = new ParticleEngine(null, 200, 200);

	}

	@Override
	public void onUpdate(GL gl) {
		ObjectInteraction.executeInteractions();

		for (MapObject mo : this.worldMapObjects) {
			mo.onUpdate();
		}
		
		

		for (MapObject mo : WorldMapState.worldMapObjects) {
			for (MapObject mo2 : WorldMapState.worldMapObjects) {

				if (!mo.equals(mo2) && mo.isWantToInteract()
						&& mo2.getInteractBox().collides(mo.getCollideBox())) {
					ObjectInteraction.ObjectInteractionList
							.add(new ObjectInteraction(mo, mo2));
				}
			}

			mo.setWantToInteract(false);
		}
		
		particleEngine.setEmiterLocationX(PLAYER.getPosX() +24);
		particleEngine.setEmiterLocationY(PLAYER.getPosY() +32);
		particleEngine.update();
	}

	@Override
	public GameCharacter getPlayer() {
		return this.PLAYER;
	}

	@Override
	public void onRender(GL gl, double interpolation) {
		// TODO Auto-generated method stub
		
		particleEngine.draw(gl, spriteBatch);
		this.worldMapObjects = RadixSort.sortEntities(this.worldMapObjects);
		
		
//		spriteBatch.setRenderColor(1f, 1f, 1f, 1f);
		spriteBatch.begin(gl);
		for (MapObject mo : this.worldMapObjects) {
			mo.onRender(gl, spriteBatch, interpolation);
		}
		spriteBatch.end(gl);

		
		
		// DEBUG INFO
		if (GameWindow.ENABLE_DEBUG_INFO) {
			lineBatch.begin(gl);
			for (MapObject mo : this.worldMapObjects) {
				mo.onRenderDebug(gl, lineBatch, interpolation);
			}
			lineBatch.end(gl);
		}
		
		
		
	}

	@Override
	public void onFinish(GL gl) {
		// TODO Auto-generated method stub

	}

}
