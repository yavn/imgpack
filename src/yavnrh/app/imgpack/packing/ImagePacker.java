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

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

import yavnrh.app.imgpack.Parameters;

public abstract class ImagePacker {

	protected Parameters params;
	
	public ImagePacker(Parameters params) {
		this.params = params;
	}

	/**
	 * Add images specified in parameters.
	 */
	public void addImages() {
		for (String imageName : params.getImages()) {
			addImage(Image.fromFile(imageName));
		}
	}
	
	public String dumpRegions() {
		List<PackedImage> regions = getPackedImages();		
		StringBuilder sb = new StringBuilder();
		
		for (PackedImage region : regions) {
			sb.append(String.format("{%d, %d, %d, %d} : ",
					region.rectangle.x, region.rectangle.y, region.rectangle.width, region.rectangle.height));
			
			if (region.image != null) {
				sb.append(region.image.getName());
			} else {
				sb.append("-");
			}
			
			sb.append("\n");
		}
		
		return sb.toString();
	}

	public BufferedImage getOutputImage() {
		List<PackedImage> packedImages = getPackedImages();
		
		if (packedImages.size() == 0) {
			return null;
		}
		
		BufferedImage outputImage = new BufferedImage(params.getOutputWidth(), params.getOutputHeight(), BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g = outputImage.createGraphics();
		
		for (PackedImage image : packedImages) {
			g.drawImage(image.image.getBufferedImage(),
						image.rectangle.x,
						image.rectangle.y,
						null);
		}
		
		g.dispose();
		
		return outputImage;
	}
	
	public abstract List<PackedImage> getPackedImages();
	public abstract void addImage(Image image);
	public abstract void pack();

}
