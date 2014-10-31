package com.jprada.core.entity.map;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

import com.jprada.core.entity.Actor;
import com.jprada.core.entity.Collidable;
import com.jprada.core.entity.utils.ObjectCollision;
import com.jprada.core.entity.utils.ObjectInteraction;
import com.jprada.core.graphics.Animation;
import com.jprada.core.graphics.Sprite;
import com.jprada.core.graphics.SpriteBatch;
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
		l.addTile(t1.getId(), 0, 0);
		l.addTile(t1.getId(), 1, 0);
		l.addTile(t1.getId(), 2, 0);
		l.addTile(t1.getId(), 3, 0);
		l.addTile(t1.getId(), 4, 0);
		l.addTile(t1.getId(), 5, 0);
		l.addTile(t1.getId(), 6, 0);
		l.addTile(t1.getId(), 7, 0);
		l.addTile(t1.getId(), 8, 0);
		l.addTile(t1.getId(), 0, 1);
		l.addTile(t1.getId(), 1, 1);
		l.addTile(t1.getId(), 2, 1);
		l.addTile(t1.getId(), 3, 1);
		l.addTile(t1.getId(), 4, 1);
		l.addTile(t1.getId(), 5, 1);
		l.addTile(t1.getId(), 6, 1);
		l.addTile(t1.getId(), 7, 1);
		l.addTile(t1.getId(), 8, 1);
		l.addTile(t1.getId(), 0, 2);
		l.addTile(t1.getId(), 1, 2);
		l.addTile(t1.getId(), 2, 2);
		l.addTile(t1.getId(), 3, 2);
		l.addTile(t1.getId(), 4, 2);
		l.addTile(t1.getId(), 5, 2);
		l.addTile(t1.getId(), 6, 2);
		l.addTile(t1.getId(), 7, 2);
		l.addTile(t1.getId(), 8, 2);
		l.addTile(t1.getId(), 0, 3);
		l.addTile(t1.getId(), 1, 3);
		l.addTile(t1.getId(), 2, 3);
		l.addTile(t1.getId(), 3, 3);
		l.addTile(t1.getId(), 4, 3);
		l.addTile(t1.getId(), 5, 3);
		l.addTile(t1.getId(), 6, 3);
		l.addTile(t1.getId(), 7, 3);
		l.addTile(t1.getId(), 8, 3);
		l.addTile(t1.getId(), 0, 4);
		l.addTile(t1.getId(), 1, 4);
		l.addTile(t1.getId(), 2, 4);
		l.addTile(t1.getId(), 3, 4);
		l.addTile(t1.getId(), 4, 4);
		l.addTile(t1.getId(), 5, 4);
		l.addTile(t1.getId(), 6, 4);
		l.addTile(t1.getId(), 7, 4);
		l.addTile(t1.getId(), 8, 4);

		Layer l2 = new Layer(widthInTiles, heightInTiles);
		for (int y = 0; y < heightInTiles; y++) {
			for (int x = 0; x < widthInTiles; x++) {

				l2.addTile(t1.getId(), x, y);
			}
		}

		Layer l3 = new Layer(widthInTiles, heightInTiles);
		for (int y = 0; y < heightInTiles; y++) {
			for (int x = 0; x < widthInTiles; x++) {

				l3.addTile(t1.getId(), x, y);
			}
		}

		this.layers.add(l2);
		// this.layers.add(l3);
		// this.layers.add(l2);
		// this.layers.add(l2);
		// this.layers.add(l2);
		// this.layers.add(l2);

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
						if (actorOne.equals(actorTwo)) {
							continue;
						}

						if (actorOne.isWantToInteract()
								&& actorTwo.getInteractBox().collides(
										actorOne.getCollideBox())) {
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

	public void onRender(GL gl, SpriteBatch batch, double interpolation) {

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

							t.onRender(gl, batch, interpolation, renderX,
									renderY);
						}
					}
				}
			}

			// Render actors in layer
			if (l.getActors() != null && !l.getActors().isEmpty()) {
			List<Actor> actors = RadixSort.sortEntities(l.getActors());
			for (Actor actor : actors) {
				actor.onRender(gl, batch, interpolation);
			}
			}

		}
		batch.end(gl);

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
