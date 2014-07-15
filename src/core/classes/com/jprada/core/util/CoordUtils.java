package com.jprada.core.util;





import java.awt.*;

public class CoordUtils {

	
	public static Point calculateRealCoords(int x, int y, 
											float xOffset, float yOffset, 
											float zoomlevel) {
		int tileWidth = 32;
		int tileHeight = 32;

		double realX = x / tileWidth;
		if ((x - (long) realX) > 0) {
			realX = (long) realX;
			realX++;
		} else {
			realX = (long) realX;
		}

		double realY = y / tileHeight;
		if ((y - (long) realY) > 0) {
			realY = (long) realY;
			realY++;
		} else {
			realY = (long) realY;
		}


		int rectX = (int) (((realX - 1) * tileWidth) * zoomlevel);
		int rectY = (int) (((realY - 1) * tileHeight) * zoomlevel);

		int xTile = (int) (((rectX + xOffset) / 32) * zoomlevel);
		int yTile = (int) (((rectY + yOffset) / 32) * zoomlevel);

		return new Point(xTile, yTile);
	}
}
