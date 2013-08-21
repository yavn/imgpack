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

import java.util.List;

import yavnrh.app.imgpack.packing.PackedImage;
import yavnrh.app.imgpack.packing.Rectangle;

public class PackedImagesSerializer {

	public static final String KEY_ATLAS_WIDTH  = "width";
	public static final String KEY_ATLAS_HEIGHT = "height";
	public static final String KEY_ATLAS_IMAGES = "images";
	
	public static final String KEY_IMAGE_NAME          = "name";
	public static final String KEY_IMAGE_RECT_X        = "x";
	public static final String KEY_IMAGE_RECT_Y        = "y";
	public static final String KEY_IMAGE_RECT_WIDTH    = "width";
	public static final String KEY_IMAGE_RECT_HEIGHT   = "height";
	public static final String KEY_IMAGE_IS_CROPPED    = "isCropped";
	public static final String KEY_IMAGE_FULL_WIDTH    = "fullWidth";
	public static final String KEY_IMAGE_FULL_HEIGHT   = "fullHeight";
	public static final String KEY_IMAGE_FULL_OFFSET_X = "fullOffsetX";
	public static final String KEY_IMAGE_FULL_OFFSET_Y = "fullOffsetY";
	
	private Parameters params;
	private List<PackedImage> packedImages;
	
	private StringBuilder out;
	private int indentation;
	private int indentationLevelSize;
	private String space;
	private String newline;
	private StringBuilder spaceBuffer;
	
	
	public PackedImagesSerializer(Parameters params, List<PackedImage> packedImages) {
		this.params = params;
		this.packedImages = packedImages;
		spaceBuffer = new StringBuilder(32);
	}
	
	public String getString() {
		return getString(0);
	}
	
	public String getString(int indentationSize) {
		out = new StringBuilder();
		indentationLevelSize = indentationSize;
		indentation = 0;
		space = (indentationSize > 0) ? " " : "";
		newline = (indentationSize > 0) ? "\n" : "";
		
		beginDictionary();
		{
			putKeyValue(KEY_ATLAS_WIDTH, params.getOutputWidth());
			nextItem();

			putKeyValue(KEY_ATLAS_HEIGHT, params.getOutputHeight());
			nextItem();

			putKey(KEY_ATLAS_IMAGES);
			beginArray();
			{
				outputImages();
			}
			endArray();

		}
		endDictionary();
		
		return out.toString();
	}
	
	private void outputImages() {		
		final int lastImageIndex = packedImages.size() - 1;
		int imageIndex = -1;
		
		for (PackedImage image : packedImages) {
			imageIndex++;
			
			beginDictionary();
			{
				putKeyValue(KEY_IMAGE_NAME, image.image.getName());
				nextItem();

				putKeyValue(KEY_IMAGE_RECT_X, image.rectangle.x);
				nextItem();

				putKeyValue(KEY_IMAGE_RECT_Y, image.rectangle.y);
				nextItem();
				
				putKeyValue(KEY_IMAGE_RECT_WIDTH, image.image.getWidth());
				nextItem();

				putKeyValue(KEY_IMAGE_RECT_HEIGHT, image.image.getHeight());
				nextItem();

				putKeyValue(KEY_IMAGE_IS_CROPPED, image.image.isCropped());

				if (image.image.isCropped()) {
					nextItem();

					Rectangle cropRect = image.image.getCropRect();

					putKeyValue(KEY_IMAGE_FULL_OFFSET_X, cropRect.x);
					nextItem();

					putKeyValue(KEY_IMAGE_FULL_OFFSET_Y, cropRect.y);
					nextItem();

					putKeyValue(KEY_IMAGE_FULL_WIDTH, image.image.getFullWidth());
					nextItem();

					putKeyValue(KEY_IMAGE_FULL_HEIGHT, image.image.getFullHeight());
				}			
			}
			endDictionary();
			
			if (imageIndex < lastImageIndex) {
				nextItem();
			}
		}
	}
	
	private void putKey(String key) {
		out.append(indent());
		out.append(String.format("\"%s\":%s", key, space));
	}

	private void putKeyValue(String key, int value) {
		out.append(indent());
		out.append(String.format("\"%s\":%s%d", key, space, value));
	}

	private void putKeyValue(String key, String value) {
		out.append(indent());
		out.append(String.format("\"%s\":%s\"%s\"", key, space, value));
	}

	private void putKeyValue(String key, boolean value) {
		String booleanValue = value ? "true" : "false";
		out.append(indent());
		out.append(String.format("\"%s\":%s%s", key, space, booleanValue));
	}

	private void beginDictionary() {
		out.append(indent());
		out.append("{");
		out.append(newline);
		
		indentation += indentationLevelSize;
	}

	private void endDictionary() {
		indentation -= indentationLevelSize;

		out.append(newline);
		out.append(indent());
		out.append("}");		
	}
		
	private void nextItem() {
		out.append(",");
		out.append(newline);
	}

	private void beginArray() {
		out.append("[");
		out.append(newline);
		
		indentation += indentationLevelSize;
	}

	private void endArray() {
		indentation -= indentationLevelSize;

		out.append(newline);
		out.append(indent());
		out.append("]");
	}

	private String indent() {
		if (indentationLevelSize > 0) {
			while (spaceBuffer.length() < indentation) {
				spaceBuffer.append(" ");
			}
			return spaceBuffer.substring(0, indentation);
			
		} else {
			return "";
		}
	}
}
