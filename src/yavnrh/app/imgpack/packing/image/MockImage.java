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

package yavnrh.app.imgpack.packing.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class MockImage extends Image {
	
	public MockImage(String name, int width, int height) {
		super(name);
		this.image = createMockImage(name, width, height);
	}

	private BufferedImage createMockImage(String name, int width, int height) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g = image.createGraphics();
				
		g.setColor(getMockColor(name, width, height));
		g.fillRect(0, 0, width, height);
		
		g.dispose();
		
		return image;
	}

	private Color getMockColor(String name, int width, int height) {
		String magicName = String.format("%s_%d_%d", name, width, height);
		final int hashCode = magicName.hashCode();
		final int x = 0xff & (hashCode >> (3 * 8));		
		final int xxx = x + (x << 8) + (x << 2 * 8);
		
		int rgb = 0xffffff & hashCode;
		rgb ^= xxx;
		
		return new Color(rgb);
	}

}
