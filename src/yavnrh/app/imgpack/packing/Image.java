/*
 * imgpack - Dumb image packing tool
 * Copyright 2013 Maciej Jesionowski
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package yavnrh.app.imgpack.packing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import yavnrh.app.imgpack.exception.ImageIOException;

public class Image {
	
	private String name;
	private int width;
	private int height;
	private BufferedImage image;
	
	private Image(String name) {
		this.name = name;
	}
	
	public static Image mock(String name, int width, int height) {
		Image image = new Image(name);
		
		image.width = width;
		image.height = height;
		image.createMockImage();
		
		return image;
	}
	
	public static Image fromFile(String name) {
		Image image = new Image(name);
		
		image.createBufferedImageFromFile();
		image.width = image.image.getWidth();
		image.height = image.image.getHeight();
		
		return image;
	}
		
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	public String getName() {
		return name;
	}
	
	public BufferedImage getBufferedImage() {
		return image;
	}

	private void createBufferedImageFromFile() {
		try {
			BufferedInputStream input = new BufferedInputStream(new FileInputStream(name));			
			BufferedImage image = ImageIO.read(input);
			
			input.close();
			
			if (image != null) {
				this.image = image;
			} else {
				throw new ImageIOException("Can not read image " + name);
			}			
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	private void createMockImage() {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		this.image = image;
		
		Graphics2D g = image.createGraphics();
				
		g.setColor(getMockColor());
		g.fillRect(0, 0, width, height);
		
		g.dispose();
	}

	private Color getMockColor() {
		String magicName = String.format("%s_%d_%d", name, width, height);
		final int hashCode = magicName.hashCode();
		final int x = 0xff & (hashCode >> (3 * 8));		
		final int xxx = x + (x << 8) + (x << 2 * 8);
		
		int rgb = 0xffffff & hashCode;
		rgb ^= xxx;
		
		return new Color(rgb);
	}
}
