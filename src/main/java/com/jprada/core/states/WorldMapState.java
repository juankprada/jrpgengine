package com.jprada.core.states;

import com.jogamp.newt.event.MouseListener;
import com.jogamp.opengl.GL;
import com.jprada.core.entity.CharacterXMLImporter;
import com.jprada.core.entity.GameCharacter;
import com.jprada.core.entity.Npc;
import com.jprada.core.entity.map.TileMap;
import com.jprada.core.entity.utils.ObjectInteraction;
import com.jprada.core.graphics.LineBatch;
import com.jprada.core.graphics.SpriteBatch;
import com.jprada.core.states.input.Keyboard;
import com.jprada.core.states.input.WorldMapKeyListener;
import com.jprada.core.util.GLColor;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.builder.EffectBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.controls.ButtonClickedEvent;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.screen.DefaultScreenController;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.tools.Color;

public class WorldMapState extends GameState {

	// private SpriteBatch spriteBatch;
	private SpriteBatch spriteBatch;
	private LineBatch lineBatch;

	// public static List<Actor> worldMapObjects = new ArrayList<Actor>();

	private Keyboard keyListener;

//	ParticleEmitter particleEngine;
//	ParticleEmitter particleEngine2;
//	ParticleEmitter particleEngine3;

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

		spriteBatch = new SpriteBatch(gl);
		spriteBatch.setup(gl);

		lineBatch = new LineBatch(gl);
                lineBatch.setup(gl);
                

		PLAYER = (GameCharacter) CharacterXMLImporter.loadCharacterSheet("main-player.xml", GameCharacter.class);
		GameCharacter p2 = (GameCharacter) CharacterXMLImporter.loadCharacterSheet("main-player.xml",
				GameCharacter.class);
		Npc p3 = (Npc) CharacterXMLImporter.loadCharacterSheet("main-player.xml", Npc.class);

		p2.setPosX(200);
		p2.setPosY(200);

		p3.setPosX(400);
		p3.setPosY(220);
		p3.setOnInteractScript("Wait(2000)\nSetPlayerPosition(300, 300)\nSetActorPosition(Me, 400, 500)");

		this.keyListener = new WorldMapKeyListener(PLAYER);
//
//		particleEngine = new ParticleEmitter(null, 200, 200);
//		particleEngine2 = new ParticleEmitter(null, 200, 200);
//		particleEngine3 = new ParticleEmitter(null, 400, 220);
		
		

		currentMap = new TileMap();
		if (currentMap.getLayers() != null && !currentMap.getLayers().isEmpty()) {
//			particleEngine.setCurrentLayer(0);
			
//			currentMap.getLayers().get(0).addActor(particleEngine);
			PLAYER.setCurrentLayer(0);
			currentMap.getLayers().get(0).addActor(PLAYER);
			
			p2.setCurrentLayer(0);
			currentMap.getLayers().get(0).addActor(p2);
			p3.setCurrentLayer(0);
			currentMap.getLayers().get(0).addActor(p3);
		}



		
//		scr = createIntroScreen(GameWindow.nifty, new MyScreenController());


               
//		GameWindow.nifty.gotoScreen("start");
//                 GameWindow.nifty.resolutionChanged();
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

//		GameWindow.nifty.update();
	}

	@Override
	public GameCharacter getPlayer() {
		return this.PLAYER;
	}

	@Override
	public void onRender(GL gl, double interpolation) {
		// TODO Auto-generated method stub
		
		currentMap.onRender(gl, spriteBatch, interpolation);

                 lineBatch.begin(gl);
                 lineBatch.draw(100,100, 400, 100, new GLColor(1f, 0f, 0f, 1f));
                 lineBatch.end();
	
                 // DEBUG INFO
//		if (GameWindow.ENABLE_DEBUG_INFO) {
//			spriteBatch.setup(gl, RenderMode.LINE);
//			currentMap.onRenderDebug(gl, spriteBatch, interpolation);
//			spriteBatch.setup(gl, RenderMode.TEXTURE);
//		}








//		GameWindow.nifty.render(false);

	}

	@Override
	public void onFinish(GL gl) {
		// TODO Auto-generated method stub
//		GameWindow.nifty.exit();
	}

}
