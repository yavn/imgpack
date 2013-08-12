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

public class Image {
	private String name;
	private int width;
	private int height;
	private boolean isMock;
	
	private Image(String name) {
		this.name = name;
		isMock = false;
	}
	
	public static Image mock(String name, int width, int height) {
		Image image = new Image(name);
		
		image.width = width;
		image.height = height;
		image.isMock = true;
		
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
}
