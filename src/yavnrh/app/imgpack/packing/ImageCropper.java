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

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class ImageCropper {

	private static final int ALPHA_THRESHOLD = 1;
	
	private BufferedImage image;
	private int width;
	private int height;
	private int[] alphaArray;
	
	public ImageCropper(BufferedImage image) {
		this.image = image;
		width = image.getWidth();
		height = image.getHeight();
		alphaArray = new int[width * height];
	}
	
	public Rectangle getCropRect() {
		WritableRaster alphaRaster = image.getAlphaRaster();
		
		if (alphaRaster == null) {
			return new Rectangle(0, 0, width, height);
		}
		
		alphaRaster.getSamples(0, 0, width, height, 0, alphaArray);
		
		final int x1 = trimFromLeft();
		final int x2 = trimFromRight();
		final int y1 = trimFromTop();
		final int y2 = trimFromBottom();
				
		return new Rectangle(x1, y1, x2 - x1, y2 - y1);
	}
	
	private int trimFromBottom() {
		for (int y = height - 1; y >= 0; y--) {
			if (scanArrayHorizontal(y)) {
				return y + 1;
			}
		}
		return height;
	}

	private int trimFromTop() {
		for (int y = 0; y < height; y++) {
			if (scanArrayHorizontal(y)) {
				return y;
			}
		}
		return 0;
	}

	private int trimFromRight() {
		for (int x = width - 1; x >= 0; x--) {
			if (scanArrayVertical(x)) {
				return x + 1;
			}
		}
		return width;
	}

	private int trimFromLeft() {
		for (int x = 0; x < width; x++) {
			if (scanArrayVertical(x)) {
				return x;
			}
		}
		return 0;
	}

	/**
	 * Return true if any pixel in y-row has alpha value equal to threshold or above.
	 */
	private boolean scanArrayHorizontal(int y) {
		int offset = y * width;
		for (int x = 0; x < width; x++) {
			if (alphaArray[offset] >= ALPHA_THRESHOLD) {
				return true;
			}
			offset += 1;
		}
		return false;
	}

	/**
	 * Return true if any pixel in x-column has alpha value equal to threshold or above.
	 */
	private boolean scanArrayVertical(int x) {
		int offset = x;
		for (int y = 0; y < height; y++) {
			if (alphaArray[offset] >= ALPHA_THRESHOLD) {
				return true;
			}
			offset += width;
		}
		return false;
	}

}
