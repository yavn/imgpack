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

public class BinTree {

	private Image image;
	private Rectangle region;	
	private BinTree left;
	private BinTree right;
	
	public BinTree(Rectangle region) {
		this.region = region;
	}
	
	public boolean isLeaf() {
		return (left == null) && (right == null);
	}
	
	public Image getImage() {
		return image;
	}
	
	public void setImage(Image image) {
		this.image = image;
	}
	
	public boolean hasImage() {
		return image != null;
	}
	
	public boolean canFitImage(Image image) {
		return (image.getWidth() <= region.width) && (image.getHeight() <= region.height);
	}

	public boolean canFitImageExactly(Image image) {
		return (region.width == image.getWidth()) && (region.height == image.getHeight());
	}
	
	public Rectangle getRegion() {
		return region;
	}
	
	public BinTree getLeft() {
		return left;
	}
	
	public BinTree getRight() {
		return right;
	}
		
	public void addChildrenWithSubregions(Rectangle leftRegion, Rectangle rightRegion) {
		left = new BinTree(leftRegion);
		right = new BinTree(rightRegion);
	}

	public void clear() {
		image = null;
		left = null;
		right = null;
	}
}
