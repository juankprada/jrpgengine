package com.jprada.core;

import java.awt.Font;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jogamp.newt.Display;
import com.jogamp.newt.MonitorDevice;
import com.jogamp.newt.MonitorMode;
import com.jogamp.newt.NewtFactory;
import com.jogamp.newt.Screen;
import com.jogamp.newt.awt.NewtCanvasAWT;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.newt.util.MonitorModeUtil;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.AnimatorBase;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jprada.core.events.EventManager;
import com.jprada.core.events.JythonManager;
import com.jprada.core.states.GameState;
import com.jprada.core.states.WorldMapState;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.nulldevice.NullSoundDevice;
import de.lessvoid.nifty.render.batch.BatchRenderDevice;
import de.lessvoid.nifty.render.batch.spi.BatchRenderBackend;
import de.lessvoid.nifty.renderer.jogl.input.JoglInputSystem;
import de.lessvoid.nifty.renderer.jogl.render.JoglBatchRenderBackendFactory;
import de.lessvoid.nifty.spi.render.RenderDevice;
import de.lessvoid.nifty.spi.time.impl.AccurateTimeProvider;

public class GameWindow implements GLEventListener {

	// Window size definition
	public static int windowWidth= 800;
	public static int windowHeight = 600;

	public static final int virtualWidth = 640;
	public static final int virtualHeight = 480;

	private static boolean fullscreen = false;

	public static boolean ENABLE_DEBUG_INFO = false;

	// Clear Color definition
	private static float clearColor_r;
	private static float clearColor_g;
	private static float clearColor_b;
	private static float clearColor_a;

	private static String windowTitle = "RPG Game Project";

	private final int TARGET_FPS = 60;

	private GLWindow canvas;
	private boolean running;
	private AnimatorBase animator;
	private KeyListener currentKeyListener;
	private MouseListener currentMouseListener;

	public static EventManager eventManager;
	private JythonManager scriptManager;

	public static Nifty nifty;
	private static final int MAX_FRAMESKIP = 5;

	private static double nextGameTick;
	private static int sleepTime = 0;
	private static double skipTicks = 1000.0 / 25.0;
	private static int loops = 0;
	private static double interpolation = 0;

	private static double fpsTimer;
	private static int fpsFrame = 1;
	private static int fps = 0;

	private int screenIdx = 0;
	// Instance of the game window
	private static GameWindow _instance = null;
	Runtime runtime = Runtime.getRuntime();
	private TextRenderer renderer = new TextRenderer(new Font("SansSerif", Font.BOLD, 12));

	// current game state
	public static GameState currentGameState = null;

	Random randomGenerator = new Random();

	private GameWindow() {
		this.running = true;
	}

	public static double getMilliSeconds() {
		return System.nanoTime() / 1000000.0;
	}

	public static void setGameWindowParameters(int windowWidth, int windowHeight) {
		GameWindow.windowWidth = windowWidth;
		GameWindow.windowHeight = windowHeight;
	}

	public static void setGameWindowParameters(int windowWidth, int windowHeight, String windowTitle) {
		setGameWindowParameters(windowWidth, windowHeight);
		GameWindow.windowTitle = windowTitle;
	}

	public static void setGameWindowParameters(int windowWidth, int windowHeight, String windowTitlem, float r, float g,
			float b) {
		setGameWindowParameters(windowWidth, windowHeight, windowTitle);
		GameWindow.clearColor_r = r;
		GameWindow.clearColor_g = g;
		GameWindow.clearColor_b = b;

	}

	public static void setGameWindowParameters(int windowWidth, int windowHeight, String windowTitlem, float r, float g,
			float b, float a) {
		setGameWindowParameters(windowWidth, windowHeight, windowTitle, r, g, b);
		GameWindow.clearColor_a = a;

	}

	public static GameWindow getGameWindow() {
		if (GameWindow._instance == null) {
			return createGameWindow();
		} else {
			return GameWindow._instance;
		}

	}

	public static GameWindow createGameWindow() {

		if (GameWindow._instance != null) {
			return GameWindow._instance;
		}

		GameWindow._instance = new GameWindow();

		GameWindow._instance.createDisplay();

		interpolation = 1;
		nextGameTick = getMilliSeconds();
		skipTicks = 1000.0 / 25.0;
		fpsTimer = getMilliSeconds() + 1000;
		fpsFrame = 1;
		fps = 0;

		return GameWindow._instance;
	}

	public void createDisplay() {

		GLProfile glp = GLProfile.get(GLProfile.GL2);

		GLCapabilities caps = new GLCapabilities(glp);

		Display dpy = NewtFactory.createDisplay(null);
		Screen screen = NewtFactory.createScreen(dpy, screenIdx);

		caps.setOnscreen(true);
		canvas = GLWindow.create(screen, caps);

		canvas.setTitle(windowTitle);

		if (fullscreen) {
			canvas.setFullscreen(fullscreen);
			canvas.setUndecorated(true);
			canvas.setVisible(true);

			MonitorDevice monitor = canvas.getMainMonitor();
			MonitorMode mmCurrent = monitor.queryCurrentMode();
			MonitorMode mmOrig = monitor.getOriginalMode();

			List<MonitorMode> monitorModes = monitor.getSupportedModes();
			if (null == monitorModes) {
				System.err.println("Your platform has no ScreenMode change support, Sorry");
				return;
			}

			monitorModes = MonitorModeUtil.filterByFlags(monitorModes, 0); // no
																			// interlace,
																			// double-scan
																			// etc
			monitorModes = MonitorModeUtil.filterByRotation(monitorModes, 0);
			// monitorModes = MonitorModeUtil.filterByResolution(monitorModes,
			// Dimension(windowWidth + 1, windowHeight + 1));
			monitorModes = MonitorModeUtil.filterByRate(monitorModes, mmOrig.getRefreshRate());
			monitorModes = MonitorModeUtil.getHighestAvailableBpp(monitorModes);

			MonitorMode mm = monitorModes.get(0);

			GameWindow.windowWidth = mm.getRotatedWidth();
			GameWindow.windowHeight = mm.getRotatedHeight();

			System.out.println("[0] set current: " + mm);
			monitor.setCurrentMode(mm);

			System.out.print("[0] post setting .. wait <");

		} else {
			canvas.setSize(windowWidth, windowHeight);
			canvas.setFullscreen(false);

			//
			// NewtCanvasAWT canvasAWT = new NewtCanvasAWT(canvas);
			// canvasAWT.setVisible(true);
			// canvasAWT.setSize(windowWidth, windowHeight);

			canvas.setVisible(true);

		}
		canvas.addGLEventListener(this);
		canvas.addWindowListener(new WindowAdapter() {
			public void windowDestroyNotify(WindowEvent e) {
				animator.stop();
				System.exit(0);
			}
		});

		// Animator ani = new Animator(canvas);
		// ani.setRunAsFastAsPossible(true);
		// animator = ani;
		FPSAnimator fpsanimator = new FPSAnimator(TARGET_FPS);
		animator = fpsanimator;
		animator.add(canvas);
		animator.start();

	}

	public void setCanvas(GLWindow canvas) {
		this.canvas = canvas;
	}

	// Main Loop of the game
	@Override
	public void display(GLAutoDrawable drawable) {

		GL gl = drawable.getGL().getGL2();

		loops = 0;

		while (getMilliSeconds() > nextGameTick && loops < MAX_FRAMESKIP) {
			// update game state

			if (currentGameState != null) {

				eventManager.update();

				currentGameState.onUpdate(gl);

			}

			nextGameTick += skipTicks;
			loops++;
		}

		interpolation = (getMilliSeconds() + skipTicks - nextGameTick) / skipTicks;

		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		// Render scene
		if (currentGameState != null) {

			currentGameState.onRender(gl, interpolation);
		}
		renderer.beginRendering(vp_width, vp_height);
		renderer.draw("FPS:" + fps, 10, 0);

		renderer.endRendering();

		if (getMilliSeconds() < fpsTimer) {
			fpsFrame++;
		} else {
			fps = fpsFrame;
			fpsFrame = 1;
			fpsTimer = getMilliSeconds() + 1000;

		}

	}

	@Override
	public void dispose(GLAutoDrawable drawable) {

	}

	public GLWindow getCanvas() {
		return canvas;
	}

	public static int vp_x;
	public static int vp_y;
	public static int vp_width;
	public static int vp_height;

	@Override
	public void init(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();

		JoglInputSystem inputsystem = new JoglInputSystem(GameWindow.getGameWindow().getCanvas());
		GameWindow.getGameWindow().getCanvas().addMouseListener(inputsystem);

		//
		targetAspectRatio = (float) virtualWidth / (float) virtualHeight;

		vp_width = GameWindow.windowWidth;
		vp_height = (int) ((float) vp_width / (float) (targetAspectRatio) + 0.5f);

		if (vp_height > GameWindow.windowHeight) {
			// It doesnt fit our height, we must switch to pillarbox then
			vp_height = GameWindow.windowHeight;
			vp_width = (int) ((float) vp_height * (float) (targetAspectRatio) + 0.5f);
		}

		// setup the new viewport centered in the backbuffer
		vp_x = (int) (((float) GameWindow.windowWidth / 2.0f) - ((float) vp_width / 2.0f));
		vp_y = (int) (((float) GameWindow.windowHeight / 2.0f) - ((float) vp_height / 2.0f));
		System.out.println("Reso:" + vp_x + "," + vp_y + "," + vp_width + "," + vp_height);
		
		drawable.getGL().glViewport(GameWindow.vp_x, GameWindow.vp_y, GameWindow.vp_width, GameWindow.vp_height);
		//

		// BatchRenderBackend backend =
		// JoglBatchRenderBackendFactory.create(this.canvas);

		// JoglBatchRenderBackendFactory.create(this.canvas);
		// RenderDevice renderDevice = new BatchRenderDevice(backend);
		//
		// GameWindow.nifty = new Nifty(renderDevice, new NullSoundDevice(),
		// inputsystem, new AccurateTimeProvider());
		// Logger.getLogger("de.lessvoid.nifty").setLevel(Level.SEVERE);
		// Logger.getLogger("NiftyInputEventHandlingLog").setLevel(Level.SEVERE);
		// nifty.loadStyleFile("nifty-default-styles.xml");
		// nifty.loadControlFile("nifty-default-controls.xml");

		gl.glDisable(GL.GL_DEPTH_TEST);

		// Disable VSync
		// gl.setSwapInterval(0);

		gl.glClearColor(clearColor_r, clearColor_g, clearColor_b, clearColor_a);

		currentGameState = new WorldMapState();
		currentGameState.onInit(gl);

		canvas.addKeyListener(currentGameState.getKeyListener());

		eventManager = EventManager.getEventManager();
		scriptManager.initJythonInterpreter();

	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public void replaceMouseListener(MouseListener mouseListener) {
		if (this.currentMouseListener != null) {
			this.canvas.removeMouseListener(this.currentMouseListener);
		}

		this.currentMouseListener = mouseListener;
		this.canvas.addMouseListener(this.currentMouseListener);
	}

	public void replaceKeyListener(KeyListener keyListener) {
		if (this.currentKeyListener != null) {
			this.canvas.removeKeyListener(this.currentKeyListener);
		}

		this.currentKeyListener = keyListener;
		this.canvas.addKeyListener(this.currentKeyListener);
	}
	
	public static float targetAspectRatio;

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {

		targetAspectRatio = (float) virtualWidth / (float) virtualHeight;

		GameWindow.windowWidth = w;
		GameWindow.windowHeight = h;

		vp_width = GameWindow.windowWidth;
		vp_height = (int) ((float) vp_width / (float) (targetAspectRatio) + 0.5f);

		if (vp_height > GameWindow.windowHeight) {
			// It doesnt fit our height, we must switch to pillarbox then
			vp_height = GameWindow.windowHeight;
			vp_width = (int) ((float) vp_height * (float) (targetAspectRatio) + 0.5f);
		}

		// setup the new viewport centered in the backbuffer
		vp_x = (int) (((float) GameWindow.windowWidth / 2.0f) - ((float) vp_width / 2.0f));
		vp_y = (int) (((float) GameWindow.windowHeight / 2.0f) - ((float) vp_height / 2.0f));
		System.out.println("Reso:" + vp_x + "," + vp_y + "," + vp_width + "," + vp_height);
		
		drawable.getGL().glViewport(GameWindow.vp_x, GameWindow.vp_y, GameWindow.vp_width, GameWindow.vp_height);
	}

}
