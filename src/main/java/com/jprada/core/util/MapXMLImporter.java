package com.jprada.core.util;



public class MapXMLImporter {
//
//	private static GameMap openedMap = null;
//
//	private static Tile currentTile = null;
//
//	public static GameMap parseMapXML(File fileToOpen, GL gl) {
//		InputStream is = null;
//		try {
//			is = new FileInputStream(fileToOpen);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return MapXMLImporter.parseMapXML(is, gl);
//		
//	}
//	
//	public static GameMap parseMapXML(InputStream is, GL gl) {
//
//		/** DECODER */
////		InputStream is;
//		int currentLayer = 0; // 0 = ground
//		int currentTile = 0;
//
//		GLContext context = gl.getContext();
//		context.makeCurrent();
//
//		XMLInputFactory xif = XMLInputFactory.newInstance();
//		try {
////			is = new FileInputStream(fileToOpen);
//
//			GZIPInputStream gzipIn = new GZIPInputStream(is);
//
//			XMLStreamReader xmlsr = xif.createXMLStreamReader(gzipIn);
//
//			while (xmlsr.hasNext()) {
//				int event = xmlsr.next();
//				if (event == XMLStreamConstants.END_DOCUMENT) {
//					xmlsr.close();
//					break;
//				}
//				if (event == XMLStreamConstants.START_ELEMENT) {
//					System.out.println(xmlsr.getLocalName());
//
//					switch (xmlsr.getLocalName()) {
//					case "map":
//						processMapElement(xmlsr);
//						break;
//
//					case "tileset":
//						processTilesetElement(xmlsr);
//						break;
//
//					case "layer":
//						currentLayer = Integer.parseInt(xmlsr.getAttributeValue(null, "id"));
//						currentTile = 0;
//						break;
//
//					case "tile":
//						processTileElement(xmlsr, currentTile);
//						currentTile++;
//						break;
//
//					case "pos":
//						processTilePos(xmlsr, currentLayer);
//						break;
//
//					case "blockpos":
//						processBlockPos(xmlsr);
//						break;
//						
//					default:
//						break;
//					}
//				}
//			}
//
//			context.release();
//			gzipIn.close();
//
//		} catch (IOException | XMLStreamException e) {
//			// TODO Auto-generated catch block
//
//			e.printStackTrace();
//			return null;
//		}
//
//		return openedMap;
//	}
//
//	private static void processTilesetElement(XMLStreamReader parser) {
//		if (openedMap != null) {
//			String tilesetName = parser.getAttributeValue(null, "name");
//			int tilesetId = Integer.parseInt(parser.getAttributeValue(null,
//					"id"));
//
//			openedMap.addTileset(tilesetName);
//			openedMap.getTilesets().put(tilesetName, tilesetId);
//
//		}
//	}
//
//	private static void processMapElement(XMLStreamReader parser) {
//
//		int mapWidth = Integer
//				.parseInt(parser.getAttributeValue(null, "width"));
//		int mapHeight = Integer.parseInt(parser.getAttributeValue(null,
//				"height"));
//		String mapName = parser.getAttributeValue(null, "name");
//		openedMap = new GameMap(mapWidth, mapHeight);
//		openedMap.setMapName(mapName);
//	}
//
//	private static void processTileElement(XMLStreamReader parser,
//			int currentTile) throws XMLStreamException {
//
//		int tileType = Integer.parseInt(parser.getAttributeValue(null, "type"));
//		boolean walkable = Boolean.parseBoolean(parser.getAttributeValue(null,
//				"walkable"));
//		int tileset = Integer
//				.parseInt(parser.getAttributeValue(null, "tilset"));
//		float regionU = Float.parseFloat(parser.getAttributeValue(null,
//				"regionU"));
//		float regionV = Float.parseFloat(parser.getAttributeValue(null,
//				"regionV"));
//		float regionU2 = Float.parseFloat(parser.getAttributeValue(null,
//				"regionU2"));
//		float regionV2 = Float.parseFloat(parser.getAttributeValue(null,
//				"regionV2"));
//		float regionWidth = Float.parseFloat(parser.getAttributeValue(null,
//				"regionWidth"));
//		float regionHeight = Float.parseFloat(parser.getAttributeValue(null,
//				"regionHeight"));
//
//		Texture tex = TextureManager.loadTexture(openedMap
//				.getTilesetName(tileset));
//
//		TextureRegion region = new TextureRegion(tex, regionU, regionV,
//				regionU2, regionV2, regionWidth, regionHeight);
//
//		if (tileType == Tile.TILE_TYPE_ANIMATED) {
//			System.out.println("Created Animated Tile");
//			MapXMLImporter.currentTile = new AnimatedTile(region, 3, true);
//		} else {
//			System.out.println("Creates Static Tile");
//			MapXMLImporter.currentTile = new StaticTile(region);
//		}
//
//		MapXMLImporter.currentTile.setTilesetId(tileset);
//
//	}
//
//	private static void processBlockPos(XMLStreamReader parser) {
//
//		int x = Integer.parseInt(parser.getAttributeValue(null, "x"));
//		int y = Integer.parseInt(parser.getAttributeValue(null, "y"));
//
//        openedMap.getBlocks().getLayerTiles()[y][x] = StaticTile.BLOCK_TILE;
//
//	}
//	private static void processTilePos(XMLStreamReader parser, int currentLayer) {
//
//		int x = Integer.parseInt(parser.getAttributeValue(null, "x"));
//		int y = Integer.parseInt(parser.getAttributeValue(null, "y"));
//
//        openedMap.getLayers()[currentLayer].getLayerTiles()[y][x] = currentTile;
//
//	}
}
