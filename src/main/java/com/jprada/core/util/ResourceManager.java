package com.jprada.core.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA. User: juankprada Date: 10/3/12 Time: 9:41 PM To
 * change this template use File | Settings | File Templates.
 */
public class ResourceManager {

	public static String resourcePath = "resources/";

	// public static URI loadResourceAsURI(String resource) {
	// URL resUrl = ResourceManager.class.getResource(
	// resourcePath + resource);
	// URI resUri = null;
	//
	// try {
	// resUri = new URI(resUrl.toString());
	// } catch (URISyntaxException e) {
	// return null;
	// }
	//
	// return resUri;
	// }

	// public static URL loadResource(String resource) {
	// return ResourceManager.class.getClassLoader().getResource(
	// resourcePath + resource);
	// }

	public static InputStream loadSprite(String resource) throws IOException {
		return loadResourceAsStream(resourcePath + "sprites/" + resource);
	}
	
	
	public static InputStream loadXMLDefinition(String resource) {
		return loadResourceAsStream(resourcePath + "defs/" + resource);
	}
	
	
	public static String loadXMLDefinitionAsString(String resource) throws IOException {
		return loadStringFile(resourcePath + "defs/" + resource);
	}
	
	public static String loadShader(String resource) throws IOException {
		return loadStringFile(resourcePath +  "shader/" + resource);
	}
	
	private static String loadStringFile(String resPath) throws IOException {

		System.out.println("Path:" + resPath);
		InputStream ins = loadResourceAsStream(resPath);

		
		String shaderSrc = null;
		StringBuffer buffer = new StringBuffer();

		Scanner scanner = new Scanner(ins);

		try {

			while (scanner.hasNextLine()) {

				buffer.append(scanner.nextLine() + "\n");
			}
			
			shaderSrc= buffer.toString();

		} finally {
			scanner.close();
			ins.close();
		}

		return shaderSrc;
	}

//	public static String readFromStream(InputStream ins) throws IOException {
//
//		if (ins == null) {
//
//			throw new IOException("Could not read from stream.");
//
//		}
//
//		StringBuffer buffer = new StringBuffer();
//
//		Scanner scanner = new Scanner(ins);
//
//		try {
//
//			while (scanner.hasNextLine()) {
//
//				buffer.append(scanner.nextLine() + "\n");
//			}
//
//		} finally {
//			scanner.close();
//
//		}
//
//		return buffer.toString();
//
//	}

	public static byte[] parseResourceList(String resource) throws IOException {
		InputStream is = loadResourceAsStream(resource);
		byte[] b = new byte[1024];
		byte[] resultBuff = new byte[0];
		int k = -1;
		while ((k = is.read(b, 0, b.length)) > -1) {
			byte[] tbuff = new byte[resultBuff.length + k];
			System.arraycopy(resultBuff, 0, tbuff, 0, resultBuff.length);
			System.arraycopy(b, 0, tbuff, resultBuff.length, k);
			resultBuff = tbuff;
		}
		return resultBuff;
	}

	private static InputStream loadResourceAsStream(String resource) {
		
		InputStream ins = null;;
		try {
			ins = new FileInputStream(resource);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("Could not read file '"+resource+"'");
			e.printStackTrace();
			
		}
				
		return ins;

	}
}
