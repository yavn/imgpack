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

import java.util.LinkedHashSet;

import yavnrh.app.imgpack.exception.DuplicateImageException;

public class Parameters {

	public static enum PackingMethod {
		MAX_RECTS,
		GRID,
	};
		
	private String outputName;
	private int outputWidth;
	private int outputHeight;
	private boolean overwriteOutput;
	
	private LinkedHashSet<String> images;
	private int spacing;
	private int border;
	private boolean crop;
	private PackingMethod method;
	
	public Parameters() {
		images = new LinkedHashSet<String>();
		
		outputName = "atlas";
		outputWidth = 1024;
		outputHeight = 1024;
		overwriteOutput = false;
		
		spacing = 0;
		border = 0;
		crop = false;
		method = PackingMethod.MAX_RECTS;
	}
	
	public String getOutputName() {
		return outputName;
	}

	public void setOutputName(String outputName) {
		this.outputName = outputName;
	}

	public int getOutputWidth() {
		return outputWidth;
	}

	public int getOutputHeight() {
		return outputHeight;
	}

	public void setOutputWidth(int width) {
		outputWidth = width;
	}

	public void setOutputHeight(int height) {
		outputHeight = height;
	}

	public String[] getImages() {
		String result[] = new String[images.size()];
		return images.toArray(result);
	}

	public void addImage(String image) {
		if (isNewImage(image)) {
			images.add(image);
		} else {
			throw new DuplicateImageException("Duplicate image " + image);
		}
	}

	private boolean isNewImage(String image) {
		return !images.contains(image);
	}

	public boolean getOverwriteOutput() {
		return overwriteOutput;
	}

	public void setOverwriteOutput(boolean b) {
		overwriteOutput = b;
	}

	public void setSpacing(int spacing) {
		this.spacing = spacing;
	}
	
	public int getSpacing() {
		return spacing;
	}

	public void setBorder(int border) {
		this.border = border;
	}
	
	public int getBorder() {
		return border;
	}

	public void setCrop(boolean b) {
		crop = b;
	}
	
	public boolean getCrop() {
		return crop;
	}
	
	public void setMethod(PackingMethod method) {
		this.method = method;
	}

	public PackingMethod getMethod() {
		return method;
	}
}
