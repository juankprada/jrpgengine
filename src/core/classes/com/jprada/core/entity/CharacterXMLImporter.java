package com.jprada.core.entity;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.jprada.core.entity.utils.CollideBox;
import com.jprada.core.entity.utils.InteractBox;
import com.jprada.core.graphics.Animation;
import com.jprada.core.graphics.Sprite;
import com.jprada.core.graphics.SpriteFrame;
import com.jprada.core.util.ResourceManager;

public class CharacterXMLImporter {

	private static Map<String, Sprite> sprites = null;
	private static Map<String, Animation> animations = null;
	

	public static Entity loadCharacterSheet(String charSheet, Class<? extends Entity> type) {
		
		// Initialize cointainers for a new load
		CharacterXMLImporter.sprites = new HashMap<String, Sprite>();
		CharacterXMLImporter.animations = new HashMap<String, Animation>();
		Entity character = null;
		if (type.equals(GameCharacter.class) || type.equals(Npc.class)) {
			character = loadCharacterFromXML(charSheet, type);
			character.fixStandingDireciton();
		}

		return character;
	}
	
	private static XMLStreamReader loadXMLFile(String charSheet) throws XMLStreamException {
		InputStream xmlDefinition = ResourceManager.loadXMLDefinition(charSheet);

		XMLInputFactory xif = XMLInputFactory.newInstance();
		
		return xif.createXMLStreamReader(xmlDefinition);
	}
	
	private static Entity loadCharacterFromXML(String charSheet, Class<? extends Entity> type) {
		
		Entity character;
		try {
			character = type.getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
//		GameCharacter character = new GameCharacter();
		
		try {
			XMLStreamReader xmlsr = loadXMLFile(charSheet);
			
			Integer currentAnimation = null;
			
			// Move through the xml source
			while (xmlsr.hasNext()) {
				
				// Get next element
				int element = xmlsr.next();
				
				if (element == XMLStreamConstants.END_DOCUMENT) {
					xmlsr.close();
					break;
				}

				if (element == XMLStreamConstants.START_ELEMENT) {
					switch (xmlsr.getLocalName()) {
						case "sprite":
							parseSpriteInfo(xmlsr);
							break;
						case "anim":
							parseAnimationInfo(xmlsr);
							break;
						default:
							break;

					}
				}
			}

		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		character.setAnimations(CharacterXMLImporter.animations);
		character.setSprites(CharacterXMLImporter.sprites);
		character.setCollideBox(new CollideBox(0, 0, 64, 64, 20, 40, 20, 5));
		character.setInteractBox(new InteractBox(0, 0, 64, 64, 10, 30, 10, -5));
		
		CharacterXMLImporter.animations = null;
		CharacterXMLImporter.sprites = null;
			
		
		return character;
	}
	
	
	public static void parseAnimationInfo(XMLStreamReader xmlsr) throws XMLStreamException {
		
		String name = xmlsr.getAttributeValue(null, "name");
		int animType = Integer.parseInt(xmlsr.getAttributeValue(null, "anim-type"));
		
		List<SpriteFrame> frames = new ArrayList<SpriteFrame>();
		
		while(xmlsr.hasNext()) {
			// Get next element
			int element = xmlsr.next();
			
			if (element == XMLStreamConstants.END_DOCUMENT) {
				xmlsr.close();
				break;
			}
			
			if (element == XMLStreamConstants.END_ELEMENT && xmlsr.getLocalName().equals("anim")) {
				break;
			}
			
			if( element == XMLStreamConstants.START_ELEMENT && xmlsr.getLocalName().equals("frame")) {
				int id = Integer.parseInt(xmlsr.getAttributeValue(null, "id"));
				int startx = Integer.parseInt(xmlsr.getAttributeValue(null, "startx"));
				int starty = Integer.parseInt(xmlsr.getAttributeValue(null, "starty"));
				float width = Float.parseFloat(xmlsr.getAttributeValue(null, "width"));
				float height =Float.parseFloat(xmlsr.getAttributeValue(null, "height"));
				long frameDuration = Long.parseLong(xmlsr.getAttributeValue(null, "frame-duration")); 
				String spriteName = xmlsr.getAttributeValue(null, "sprite-id");
				
				SpriteFrame frame = CharacterXMLImporter.sprites.get(spriteName).getFrame(startx, starty, width, height);
				frame.setDuration(frameDuration);
				
				frames.add(id, frame);
			}
		}
		
		Animation anim = new Animation(frames, animType);
		CharacterXMLImporter.animations.put(name, anim);

	}
	
	
	public static void parseSpriteInfo(XMLStreamReader xmlsr) {
		String name = xmlsr.getAttributeValue(null, "name");
		String path = xmlsr.getAttributeValue(null, "path");
		
		sprites.put(name,  new Sprite(path));
	}

}
