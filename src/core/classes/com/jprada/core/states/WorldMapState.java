package com.jprada.core.states;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.media.opengl.GL;

import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.newt.opengl.GLWindow;
import com.jprada.core.GameWindow;
import com.jprada.core.entity.CharacterXMLImporter;
import com.jprada.core.entity.GameCharacter;
import com.jprada.core.entity.Npc;
import com.jprada.core.entity.map.TileMap;
import com.jprada.core.entity.utils.ObjectInteraction;
import com.jprada.core.graphics.LineBatch;
import com.jprada.core.graphics.RenderBatch;
import com.jprada.core.particleengine.ParticleEmitter;
import com.jprada.core.states.input.WorldMapKeyListener;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.builder.EffectBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.controls.ButtonClickedEvent;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.nulldevice.NullSoundDevice;
import de.lessvoid.nifty.render.batch.BatchRenderDevice;
import de.lessvoid.nifty.renderer.jogl.input.JoglInputSystem;
import de.lessvoid.nifty.renderer.jogl.render.JoglBatchRenderBackendFactory;
import de.lessvoid.nifty.screen.DefaultScreenController;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.spi.render.RenderDevice;
import de.lessvoid.nifty.spi.time.impl.AccurateTimeProvider;
import de.lessvoid.nifty.tools.Color;
import de.lessvoid.nifty.tools.SizeValue;

public class WorldMapState extends GameState {

	// private SpriteBatch spriteBatch;
	private RenderBatch renderBatch;
	private LineBatch lineBatch;

	// public static List<Actor> worldMapObjects = new ArrayList<Actor>();

	private KeyListener keyListener;

	ParticleEmitter particleEngine;
	ParticleEmitter particleEngine2;
	ParticleEmitter particleEngine3;

	// Tile[][] tileArray = new Tile[25][19];

	public static TileMap currentMap;

	private Nifty nifty;
	private Screen  scr;

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
		// spriteBatch = new SpriteBatch();
		// spriteBatch.setup(gl);
		renderBatch = new RenderBatch(gl);
		renderBatch.setup(gl);

		lineBatch = new LineBatch();
		lineBatch.setup(gl);

		PLAYER = (GameCharacter) CharacterXMLImporter.loadCharacterSheet("main-player.xml", GameCharacter.class);
		GameCharacter p2 = (GameCharacter) CharacterXMLImporter.loadCharacterSheet("main-player.xml",
				GameCharacter.class);
		Npc p3 = (Npc) CharacterXMLImporter.loadCharacterSheet("main-player.xml", Npc.class);

		p2.setPosX(200);
		p2.setPosY(200);

		p3.setPosX(400);
		p3.setPosY(220);
		p3.setOnInteractScript("Wait(2000)\nSetPlayerPosition(300, 300)");

		// this.worldMapObjects.add(PLAYER);
		// this.worldMapObjects.add(p2);
		// this.worldMapObjects.add(p3);

		this.keyListener = new WorldMapKeyListener(PLAYER);

		particleEngine = new ParticleEmitter(null, 200, 200);
		particleEngine2 = new ParticleEmitter(null, 200, 200);
		particleEngine3 = new ParticleEmitter(null, 400, 220);

		currentMap = new TileMap();
		if (currentMap.getLayers() != null && !currentMap.getLayers().isEmpty()) {
			PLAYER.setCurrentLayer(0);
			currentMap.getLayers().get(0).addActor(PLAYER);
			p2.setCurrentLayer(0);
			currentMap.getLayers().get(0).addActor(p2);
			p3.setCurrentLayer(0);
			currentMap.getLayers().get(0).addActor(p3);
		}

		JoglInputSystem inputsystem = new JoglInputSystem(GameWindow.getGameWindow().getCanvas());
		GameWindow.getGameWindow().getCanvas().addMouseListener(inputsystem);
//		GameWindow.getGameWindow().getCanvas().addKeyListener(inputsystem);
		RenderDevice renderDevice = new BatchRenderDevice(JoglBatchRenderBackendFactory.create());
		
		this.nifty = new Nifty(renderDevice, new NullSoundDevice(), inputsystem, new AccurateTimeProvider());
		Logger.getLogger("de.lessvoid.nifty").setLevel(Level.SEVERE); 
		Logger.getLogger("NiftyInputEventHandlingLog").setLevel(Level.SEVERE); 
		nifty.loadStyleFile("nifty-default-styles.xml");
		nifty.loadControlFile("nifty-default-controls.xml");
		scr = createIntroScreen(nifty, new MyScreenController());
		
		nifty.gotoScreen("start");
	}

	

	public static class MyScreenController extends DefaultScreenController {
		public String screenID;
		
				
		@NiftyEventSubscriber(id = "exit")
		public void exit(final String id, final ButtonClickedEvent event) {
			nifty.removeScreen(this.screenID);
		}
	}

	private static Screen createIntroScreen(final Nifty nifty, MyScreenController controller) {
		Screen scr = new ScreenBuilder("start") {
			{
				controller(controller);
				layer(new LayerBuilder("layer") {
					{
						childLayoutCenter();
						onStartScreenEffect(new EffectBuilder("fade") {
							{
								length(500);
								effectParameter("start", "#0");
								effectParameter("end", "#f");
							}
						});
						
						onEndScreenEffect(new EffectBuilder("fade") {
							{
								length(500);
								effectParameter("start", "#f");
								effectParameter("end", "#0");
							}
						});
//						onActiveEffect(new EffectBuilder("gradient") {
//							{
//								effectValue("offset", "0%", "color", "#333f");
//								effectValue("offset", "100%", "color", "#ffff");
//							}
//						});
						panel(new PanelBuilder() {
							{
								childLayoutVertical();
								text(new TextBuilder() {
									{
										text("Label Text");
										style("base-font");
										color(Color.BLACK);
										alignLeft();
										valignCenter();
									}
								});
//								panel(new PanelBuilder() {
//									{
//										height(SizeValue.px(10));
//									}
//								});
								control(new ButtonBuilder("exit", "Click here to exit!") {
									{
										alignCenter();
										valignCenter();
									}
								});
							}
						});
					}
				});
			}
		}.build(nifty);
		
		controller.screenID = scr.getScreenId();
		
		return scr;
	}

	@Override
	public void onUpdate(GL gl) {
		ObjectInteraction.executeInteractions();

		currentMap.onUpdate();

		// for (Actor mo : this.worldMapObjects) {
		// mo.onUpdate();
		// }
		//
		//
		//
		// for (Actor mo : WorldMapState.worldMapObjects) {
		// for (Actor mo2 : WorldMapState.worldMapObjects) {
		//
		// if (!mo.equals(mo2) && mo.isWantToInteract()
		// && mo2.getInteractBox().collides(mo.getCollideBox())) {
		// ObjectInteraction.ObjectInteractionList
		// .add(new ObjectInteraction(mo, mo2));
		// }
		// }
		//
		// mo.setWantToInteract(false);
		// }

		particleEngine.setEmiterLocationX(PLAYER.getPosition().x + 24);
		particleEngine.setEmiterLocationY(PLAYER.getPosition().y + 32);
		particleEngine.update();
		particleEngine2.update();
		particleEngine3.update();

		nifty.update();
	}

	@Override
	public GameCharacter getPlayer() {
		return this.PLAYER;
	}

	@Override
	public void onRender(GL gl, double interpolation) {
		// TODO Auto-generated method stub

		currentMap.onRender(gl, renderBatch, interpolation);

		// int total = particleEngine.getParticles().size() +
		// particleEngine2.getParticles().size() +
		// particleEngine3.getParticles().size();

		// spriteBatch.begin(gl);
		// for(int i=0; i<25; i++) {
		// for(int j=0; j<19; j++) {
		// tileArray[i][j].onRender(gl, spriteBatch, interpolation);
		// }
		// }
		// spriteBatch.end(gl);
		//
		particleEngine.draw(gl, renderBatch);
		particleEngine2.draw(gl, renderBatch);
		particleEngine3.draw(gl, renderBatch);

		// this.worldMapObjects = RadixSort.sortEntities(this.worldMapObjects);

		// spriteBatch.begin(gl);

		// for (Actor mo : this.worldMapObjects) {
		// mo.onRender(gl, spriteBatch, interpolation);
		// }
		// spriteBatch.end(gl);

		// DEBUG INFO
		// if (GameWindow.ENABLE_DEBUG_INFO) {
		// lineBatch.begin(gl);
		// for (Actor mo : this.worldMapObjects) {
		// mo.onRenderDebug(gl, lineBatch, interpolation);
		// }
		// lineBatch.end(gl);
		// }

		nifty.render(false);

		

	}

	@Override
	public void onFinish(GL gl) {
		// TODO Auto-generated method stub
		nifty.exit();
	}

}
