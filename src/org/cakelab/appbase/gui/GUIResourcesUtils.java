package org.cakelab.appbase.gui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;



public class GUIResourcesUtils {
	
	public static InputStream asInputStream(String resource) {
		return 	GUIUtils.class.getClassLoader().getResourceAsStream(resource);
	}
	public static BufferedImage asImage(String resource) throws IOException {
		return ImageIO.read(asInputStream(resource));
	}
	public static Icon asIcon(String resource) throws IOException {
		return new ImageIcon(asImage(resource));
	}
	public static URL getURL(String resource) {
		return GUIResourcesUtils.class.getClassLoader().getResource(resource);
	}
}
