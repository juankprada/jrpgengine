package com.jprada.core;


import com.jogamp.newt.*;
import com.jogamp.newt.awt.NewtCanvasAWT;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.newt.util.MonitorModeUtil;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.AnimatorBase;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jprada.core.events.EventManager;
import com.jprada.core.events.JythonManager;
import com.jprada.core.states.GameState;
import com.jprada.core.states.WorldMapState;

import javax.media.opengl.*;
import java.awt.*;
import java.util.List;
import java.util.Random;




public class GameWindow implements GLEventListener {

    // Window size definition
    private static int windowHeight = 800;
    private static int windowWidth = 600;

    private static boolean fullscreen=false;
    
    public static boolean ENABLE_DEBUG_INFO = false;

    // Clear Color definition
    private static float clearColor_r;
    private static float clearColor_g;
    private static float clearColor_b;
    private static float clearColor_a;

    private static String windowTitle = "RPG Game Project";

    private final int TARGET_FPS = 60;

    private GLWindow canvas;
    private  boolean running;
    private AnimatorBase animator;
    private KeyListener currentKeyListener;
    private MouseListener currentMouseListener;
    
    public static EventManager eventManager;
    private JythonManager scriptManager;
//    private Frame frame;


    private static final int MAX_FRAMESKIP = 5;

    private static double nextGameTick;
    private static int sleepTime = 0;
    private static double  skipTicks = 1000.0/25.0;
    private static int loops = 0;
    private static double interpolation = 0;

    private static double fpsTimer;
    private static int fpsFrame = 1;
    private static int fps=0;

    private int screenIdx = 0;
    // Instance of the game window
    private static GameWindow _instance = null;
    Runtime runtime = Runtime.getRuntime();
    private TextRenderer renderer = new TextRenderer(new Font("SansSerif", Font.BOLD, 12));

    // current game state
    public static GameState currentGameState = null;

    Random randomGenerator = new Random();

    private Frame frame;

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

    public static void setGameWindowParameters(int windowWidth, int windowHeight, String windowTitlem, float r, float g, float b) {
        setGameWindowParameters(windowWidth, windowHeight, windowTitle);
        GameWindow.clearColor_r = r;
        GameWindow.clearColor_g = g;
        GameWindow.clearColor_b = b;

    }

    public static void setGameWindowParameters(int windowWidth, int windowHeight, String windowTitlem, float r, float g, float b, float a) {
        setGameWindowParameters(windowWidth, windowHeight, windowTitle, r, g, b);
        GameWindow.clearColor_a = a;

    }

    public static GameWindow getGameWindow() {
        if( GameWindow._instance == null)  {
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

    public static int getWindowHeight() {
        return windowHeight;
    }

    public static int getWindowWidth() {
        return windowWidth;
    }

    public void createDisplay() {

        GLProfile glp = GLProfile.get(GLProfile.GL2);

        GLCapabilities caps = new GLCapabilities(glp);

        Display dpy = NewtFactory.createDisplay(null);
        Screen screen = NewtFactory.createScreen(dpy, screenIdx);



//        List<MonitorMode> modes =  screen.getMonitorModes();
//        for(MonitorMode m : modes) {
//            System.out.println(""
//                    +m.getSizeAndRRate().surfaceSize.getResolution().getWidth()
//                    + "x"
//                    +m.getSizeAndRRate().surfaceSize.getResolution().getHeight());
//        }
        caps.setOnscreen(true);
        canvas = GLWindow.create(screen, caps);
        canvas.setSize(windowWidth, windowHeight);
        canvas.addGLEventListener(this);
        canvas.setTitle(windowTitle);


        if (fullscreen) {
            canvas.setFullscreen(fullscreen);
            canvas.setUndecorated(true);
            canvas.setVisible(true);

            MonitorDevice monitor = canvas.getMainMonitor();
            MonitorMode mmCurrent = monitor.queryCurrentMode();
            MonitorMode mmOrig = monitor.getOriginalMode();
            System.err.println("[0] orig   : " + mmOrig);
            System.err.println("[0] current: " + mmCurrent);

            List<MonitorMode> monitorModes = monitor.getSupportedModes();
            if (null == monitorModes) {
                System.err.println("Your platform has no ScreenMode change support, Sorry");
                return;
            }

            monitorModes = MonitorModeUtil.filterByFlags(monitorModes, 0); // no interlace, double-scan etc
            monitorModes = MonitorModeUtil.filterByRotation(monitorModes, 0);
            monitorModes = MonitorModeUtil.filterByResolution(monitorModes, new javax.media.nativewindow.util.Dimension(windowWidth + 1, windowHeight + 1));
            monitorModes = MonitorModeUtil.filterByRate(monitorModes, mmOrig.getRefreshRate());
            monitorModes = MonitorModeUtil.getHighestAvailableBpp(monitorModes);
            MonitorMode mm = monitorModes.get(0);
            System.err.println("[0] set current: " + mm);
            monitor.setCurrentMode(mm);

            System.err.print("[0] post setting .. wait <");

        } else {
            canvas.setFullscreen(false);



            NewtCanvasAWT canvasAWT = new NewtCanvasAWT(canvas);
            canvasAWT.setVisible(true);
            canvasAWT.setSize(windowWidth, windowHeight);

            frame = new Frame(windowTitle);
            frame.add(canvasAWT);
//            frame.setSize(windowWidth, windowHeight);
            frame.setResizable(false);
            frame.pack();
            frame.setVisible(true);
            
            canvas.setVisible(true);

        }





//        frame = new Frame(windowTitle);
//
//        frame.setSize(windowWidth, windowHeight);
//        frame.setResizable(false);
//        frame.setUndecorated(true);


        // Get the screen size
//        GraphicsConfiguration gc = frame.getGraphicsConfiguration();
//        Rectangle bounds = gc.getBounds();

        canvas.addWindowListener(new WindowAdapter() {
            public void windowDestroyNotify(WindowEvent e) {
//                canvas.destroy();
            	animator.stop();
                System.exit(0);
            }
        });

        Animator ani = new Animator(canvas);
        ani.setRunAsFastAsPossible(true);
        animator = ani;
//        FPSAnimator fpsanimator = new FPSAnimator(TARGET_FPS);
       
//        animator = fpsanimator;
//        animator.add(canvas);
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
//                System.out.println("LLego");
            }
           
            
            nextGameTick += skipTicks;
            loops++;
        }

       
        interpolation = (getMilliSeconds() + skipTicks - nextGameTick) / skipTicks;


        gl.glClear(GL.GL_COLOR_BUFFER_BIT);


        // Render scene
        if(currentGameState != null) {
            currentGameState.onRender(gl, interpolation);
        }
        
        
        if (getMilliSeconds() < fpsTimer) {
            fpsFrame++;
        } else {
            fps = fpsFrame;
            fpsFrame = 1;
            fpsTimer = getMilliSeconds() + 1000;

        }
        
//        System.out.println("Render");
        renderer.beginRendering(windowWidth, windowHeight);
        renderer.draw("Frames:"+fps + "/s", 10, windowHeight -20);

        renderer.endRendering();
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {

    }

    public GLWindow getCanvas() {
        return canvas;
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        gl.glDisable(GL.GL_DEPTH_TEST);

        // Disable VSync
        gl.setSwapInterval(0);

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

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
    }



  
    
}
