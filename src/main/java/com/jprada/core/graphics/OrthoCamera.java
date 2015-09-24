package com.jprada.core.graphics;


import com.jprada.core.GameWindow;

public class OrthoCamera {

	private static OrthoCamera _instance = null;

	private float posx;
	private float posy;

//	private GameMap currentMap;
	private int maxWidth;
	private int maxHeight;
	
	private boolean centerScreen = true;

	private OrthoCamera() {

	}

	public boolean isCenterScreen() {
		return centerScreen;
	}

	public void setCenterScreen(boolean centerScreen) {
		this.centerScreen = centerScreen;
	}

	public static OrthoCamera createCameraForMap(int maxWidth, int maxHeight) {
	
		
		getInstance().maxWidth = maxWidth;
		getInstance().maxHeight = maxHeight;
		
		return getInstance();
		
	}
	
	public static OrthoCamera getInstance() {
		if (_instance == null) {
			_instance = new OrthoCamera();
		}

		return _instance;
	}

	public void setPosition(float posx, float posy) {
		if (centerScreen) {
			int w = GameWindow.getWindowWidth();
			int h = GameWindow.getWindowHeight();
			
			int wHalved = (w/2);
			int hHalved = (h/2);

			if(posx < wHalved) {
				this.posx = 0;
			} else if(posx > (maxWidth - wHalved)) {
				
				this.posx = (wHalved) - (maxWidth - wHalved);
			}
			else {
				this.posx = (wHalved) - posx;
			}
			
			
			if(posy < hHalved) {
				this.posy = 0;
			} else if (posy > (maxHeight - hHalved)) {
				this.posy = (hHalved) - (maxHeight - hHalved);
			}
			else {
				this.posy = (hHalved) - posy;
			}
			
//			float cameraPosY = (hHalved) - posy;
//
//			this.posx = cameraPosX;
//			this.posy = cameraPosY;
//			System.out.println("X:"+this.posx+" Y:"+this.posy);
			
		} else {
			this.posx = posx;
			this.posy = posy;
		}
	}

	public float getPosx() {
		return posx;
	}

	public void setPosx(float posx) {
		this.posx = posx;
	}

	public float getPosy() {
		return posy;
	}

	public void setPosy(float posy) {
		this.posy = posy;
	}

}
