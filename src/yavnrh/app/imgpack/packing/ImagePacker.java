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

import java.util.ArrayList;
import java.util.List;

public abstract class ImagePacker {
	
	protected ArrayList<Image> images;
	
	public ImagePacker() {
		images = new ArrayList<Image>();
	}
	
	public void addImage(Image image) {
		images.add(image);
	}

	public final String dumpRegions() {
		List<ImageRegion> regions = getImageRegions();		
		StringBuilder sb = new StringBuilder();
		
		for (ImageRegion region : regions) {
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

	protected abstract List<ImageRegion> getImageRegions();
	
	public abstract void pack();
}
