package com.jprada.core.states;

import com.jogamp.newt.event.MouseListener;
import com.jogamp.opengl.GL;
import com.jprada.core.GameWindow;
import com.jprada.core.entity.CharacterXMLImporter;
import com.jprada.core.entity.GameCharacter;
import com.jprada.core.entity.Npc;
import com.jprada.core.entity.map.TileMap;
import com.jprada.core.entity.utils.ObjectInteraction;
import com.jprada.core.graphics.RenderBatch;
import com.jprada.core.graphics.RenderBatch.RenderMode;
import com.jprada.core.particleengine.ParticleEmitter;
import com.jprada.core.states.input.Keyboard;
import com.jprada.core.states.input.WorldMapKeyListener;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.builder.ControlBuilder;
import de.lessvoid.nifty.builder.EffectBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.screen.DefaultScreenController;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.tools.Color;

public class WorldMapState extends GameState {

	// private SpriteBatch spriteBatch;
	private RenderBatch renderBatch;
	

	// public static List<Actor> worldMapObjects = new ArrayList<Actor>();

	private Keyboard keyListener;

	ParticleEmitter particleEngine;
	ParticleEmitter particleEngine2;
	ParticleEmitter particleEngine3;

	// Tile[][] tileArray = new Tile[25][19];

	public static TileMap currentMap;

	
	private Screen  scr;

	@Override
	public Keyboard getKeyListener() {
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

		

		PLAYER = (GameCharacter) CharacterXMLImporter.loadCharacterSheet("main-player.xml", GameCharacter.class);
		GameCharacter p2 = (GameCharacter) CharacterXMLImporter.loadCharacterSheet("main-player.xml",
				GameCharacter.class);
		Npc p3 = (Npc) CharacterXMLImporter.loadCharacterSheet("main-player.xml", Npc.class);

		p2.setPosX(200);
		p2.setPosY(200);

		p3.setPosX(400);
		p3.setPosY(220);
		p3.setOnInteractScript("Wait(2000)\nSetPlayerPosition(300, 300)\nSetActorPosition(Me, 400, 500)");

		// this.worldMapObjects.add(PLAYER);
		// this.worldMapObjects.add(p2);
		// this.worldMapObjects.add(p3);

		this.keyListener = new WorldMapKeyListener(PLAYER);

		particleEngine = new ParticleEmitter(null, 200, 200);
		particleEngine2 = new ParticleEmitter(null, 200, 200);
		particleEngine3 = new ParticleEmitter(null, 400, 220);
		
		

		currentMap = new TileMap();
		if (currentMap.getLayers() != null && !currentMap.getLayers().isEmpty()) {
			particleEngine.setCurrentLayer(1);
			
			currentMap.getLayers().get(1).addActor(particleEngine);
			PLAYER.setCurrentLayer(1);
			currentMap.getLayers().get(1).addActor(PLAYER);
			
			p2.setCurrentLayer(1);
			currentMap.getLayers().get(1).addActor(p2);
			p3.setCurrentLayer(1);
			currentMap.getLayers().get(1).addActor(p3);
		}

	
		
		
		scr = createIntroScreen(GameWindow.nifty, new MyScreenController());
		
		GameWindow.nifty.gotoScreen("start");
	}

	

	public static class MyScreenController extends DefaultScreenController {
		public String screenID;
		
				
		@NiftyEventSubscriber(id = "exit")
		public void exit(final String id, final Object event) {
			nifty.removeScreen(this.screenID);
		}
	}

	private static Screen createIntroScreen(final Nifty nifty, MyScreenController controller) {
		Screen scr = new ScreenBuilder("start") {
			{
				controller(controller);
				layer(new LayerBuilder("layer") {
					{
						childLayoutAbsolute();
						onStartScreenEffect(new EffectBuilder("move") {
							{
								length(500);
								effectParameter("direction", "top");
								effectParameter("offsetX", "0");
								effectParameter("offsetY", "0");
								effectParameter("mode", "in");
							}
						});
						
						onEndScreenEffect(new EffectBuilder("move") {
							{
								length(500);
								effectParameter("direction", "top");
								effectParameter("offsetX", "0");
								effectParameter("offsetY", "0");
								effectParameter("mode", "out");
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
								control(new ControlBuilder("exit", "Click here to exit!") {
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

		GameWindow.nifty.update();
	}

	@Override
	public GameCharacter getPlayer() {
		return this.PLAYER;
	}

	@Override
	public void onRender(GL gl, double interpolation) {
		// TODO Auto-generated method stub
		
		currentMap.onRender(gl, renderBatch, interpolation);


		// DEBUG INFO
		if (GameWindow.ENABLE_DEBUG_INFO) {		
			renderBatch.setup(gl, RenderMode.LINE);
			currentMap.onRenderDebug(gl, renderBatch, interpolation);
			renderBatch.setup(gl, RenderMode.TEXTURE);	
		}

			GameWindow.nifty.render(false);

		

	}

	@Override
	public void onFinish(GL gl) {
		// TODO Auto-generated method stub
		GameWindow.nifty.exit();
	}

}
