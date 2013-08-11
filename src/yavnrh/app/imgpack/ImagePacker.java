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

package yavnrh.app.imgpack;

public class ImagePacker {

	private String outputImageName;
	private int outputImageWidth;
	private int outputImageHeight;
	
	public String getOutputImageName() {
		return outputImageName;
	}

	public void setOutputImageName(String outputName) {
		this.outputImageName = outputName;
	}

	public int getOutputImageWidth() {
		return outputImageWidth;
	}

	public int getOutputImageHeight() {
		return outputImageHeight;
	}

	public void setOutputImageWidth(int width) {
		outputImageWidth = width;
	}

	public void setOutputImageHeight(int height) {
		outputImageHeight = height;
	}
}
