package com.jprada.core.entity.map;

import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.GL;
import com.jprada.core.entity.Actor;
import com.jprada.core.entity.Collidable;
import com.jprada.core.entity.utils.ObjectCollision;
import com.jprada.core.entity.utils.ObjectInteraction;
import com.jprada.core.graphics.Animation;
import com.jprada.core.graphics.RenderBatch;
import com.jprada.core.graphics.Sprite;
import com.jprada.core.graphics.SpriteFrame;
import com.jprada.core.util.algorithm.RadixSort;

public class TileMap {

	private int widthInPx;
	private int heightInPx;
	private int widthInTiles;
	private int heightInTiles;

	private int tileWidth;
	private int tileHeight;

	private List<Tile> tileSet;
	private List<Layer> layers;

	public TileMap() {
		tileSet = new ArrayList<Tile>();
		layers = new ArrayList<Layer>();

		this.widthInTiles = 25;
		this.heightInTiles = 19;
		this.tileWidth = 32;
		this.tileHeight = 32;

		Tile t1 = new Tile();
		t1.setId(0);
		Tile t2 = new Tile();
		t2.setId(1);

		Sprite sp = new Sprite("sandwater.png");
		SpriteFrame frame = new SpriteFrame(sp.getTexture(), 0, 0, 32, 32);

		List<SpriteFrame> frames = new ArrayList<SpriteFrame>();
		frames.add(frame);
		Animation anim = new Animation(frames, Animation.ANIMATION_LOOPING);

		t1.currentAnimation = anim;

		frame = new SpriteFrame(sp.getTexture(), 32, 0, 32, 32);
		frames = new ArrayList<SpriteFrame>();
		frames.add(frame);
		anim = new Animation(frames, Animation.ANIMATION_LOOPING);
		t2.currentAnimation = anim;

		tileSet.add(t1.getId(), t1);
		tileSet.add(t2.getId(), t2);


		

		Layer l = new Layer(widthInTiles, heightInTiles);
		for (int y = 0; y < heightInTiles; y++) {
			for (int x = 0; x < widthInTiles; x++) {

				l.addTile(t1.getId(), x, y);
			}
		}



		this.layers.add(l);



	}

	public void onUpdate() {

		// Update all tiles in map
		for (Tile t : tileSet) {
			t.onUpdate();
		}

		// Update everything else in map
		for (Layer l : layers) {
			if (l.getActors() != null && !l.getActors().isEmpty()) {
				for (Actor actor : l.getActors()) {
					actor.onUpdate();
				}
			}
		}

		// Check for actors collisions
		for (Layer l : layers) {
			if (l.getActors() != null && !l.getActors().isEmpty()) {
				for (Actor actorOne : l.getActors()) {
					for (Actor actorTwo : l.getActors()) {
						if (actorTwo == null) { continue; }
						
						if (actorOne.equals(actorTwo)) {
							continue;
						}

						if (actorOne.isWantToInteract() && actorTwo.getInteractBox() != null && actorOne.getCollideBox() != null && actorTwo.getInteractBox().collides(actorOne.getCollideBox())) {
							
								ObjectInteraction.ObjectInteractionList
										.add(new ObjectInteraction(actorOne,
												actorTwo));
							
						}
					}

					actorOne.setWantToInteract(false);
				}
			}
		}
	}

	public void onRenderDebug(GL gl, RenderBatch batch, double interpolation) {
		
		batch.begin(gl);
		
		for (Layer l : layers) {

			// Render actors in layer
			if (l.getActors() != null && !l.getActors().isEmpty()) {
				List<Actor> actors = RadixSort.sortEntities(l.getActors());
				for (Actor actor : actors) {
					actor.onRenderDebug(gl, batch, interpolation);
				}
			}

		}
		
		batch.end();
	}
	
	public void onRender(GL gl, RenderBatch batch, double interpolation) {

		float renderX, renderY;

		batch.begin(gl);
		// Iterate over layers
		for (Layer l : layers) {

			// Should the layer be rendered?
			if (l.render) {

				// Iterate over all cels in layer
				// We go first through the y positions due to rendering order
				for (int yPos = 0; yPos < this.heightInTiles; yPos++) {
					for (int xPos = 0; xPos < this.widthInTiles; xPos++) {

						Integer index = l.getTileAt(xPos, yPos);

						if (index == null) {
							continue;
						}

						Tile t = this.tileSet.get(index);
						if (t != null) {
							renderY = yPos * tileHeight;
							renderX = xPos * tileWidth;

							t.onRender(batch, interpolation, renderX, renderY);
						}
					}
				}
			}

			// Render actors in layer
			if (l.getActors() != null && !l.getActors().isEmpty()) {
				List<Actor> actors = RadixSort.sortEntities(l.getActors());
				for (Actor actor : actors) {
					actor.onRender(batch, interpolation);
				}
			}

		}
		batch.end();

	}

	public List<Layer> getLayers() {
		return layers;
	}

	public boolean isPosValid(Actor actor) {
		boolean posValid = true;

		Layer l = this.layers.get(actor.getCurrentLayer());

		for (Collidable actorTwo : l.getActors()) {

			if (actor.equals(actorTwo)) {
				continue;
			}

			if (actor.collides(actorTwo)) {
				ObjectCollision.ObjectCollisionList.add(new ObjectCollision(
						actor, actorTwo));
				posValid = false;
			}
		}

		return posValid;
	}

}
