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

import java.awt.image.BufferedImage;

import yavnrh.app.imgpack.packing.Rectangle;

public abstract class Image {

	protected String name;
	protected BufferedImage image;
	
	public Image(String name) {
		this.name = name;
	}
	
	public int getWidth() {
		return image.getWidth();
	}

	public int getHeight() {
		return image.getHeight();
	}

	public String getName() {
		return name;
	}

	public BufferedImage getBufferedImage() {
		return image;
	}
	
	public boolean isCropped() {
		return false;
	}
	
	public Rectangle getCropRect() {
		return new Rectangle(0, 0, getWidth(), getHeight());
	}
	
	public int getFullWidth() {
		return image.getWidth();
	}
	
	public int getFullHeight() {
		return image.getHeight();
	}
	
}
