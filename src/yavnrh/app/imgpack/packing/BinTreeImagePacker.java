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

import java.util.LinkedList;
import java.util.List;

import yavnrh.app.imgpack.Parameters;
import yavnrh.app.imgpack.exception.ImagePackingException;

public class BinTreeImagePacker extends ImagePacker {

	private LinkedList<Image> images;
	private BinTree tree;
	
	public BinTreeImagePacker(Parameters params) {
		tree = new BinTree(new Rectangle(0, 0, params.getOutputWidth(), params.getOutputHeight()));
		images = new LinkedList<Image>();
	}

	@Override
	public void addImage(Image image) {
		images.add(image);
	}
	
	@Override
	public List<ImageRegion> getImageRegions() {
		List<ImageRegion> result = new LinkedList<ImageRegion>();
		walkTreeAndCollectImageRegions(result, tree);		
		return result;
	}

	private void walkTreeAndCollectImageRegions(List<ImageRegion> output, BinTree node) {
		if (node.isLeaf()) {
			output.add(new ImageRegion(node.getRegion(), node.getImage()));
		} else {
			walkTreeAndCollectImageRegions(output, node.getLeft());
			walkTreeAndCollectImageRegions(output, node.getRight());
		}
	}
	
	@Override
	public void pack() {
		LinkedList<Image> workingImages = new LinkedList<Image>(images);		
		int attempts = workingImages.size();
		
		for (;;) {
			boolean failed = false;
			Image lastImage = null;
			
			for (Image image : workingImages) {
				lastImage = image;
				BinTree insertNode = insertImage(image, tree);
				
				if (insertNode == null) {
					failed = true;
					break;
				}
			}
			
			if (failed) {
				if (attempts > 1) {
					// packing failed
					// try once again, but starting with the image that has failed
					
					tree.clear();
					attempts--;
					workingImages.remove(lastImage);
					workingImages.addFirst(lastImage);
					
				} else {
					throw new ImagePackingException("Not enough space to fit image " + lastImage);
				}
			} else {
				// packing successful
				break;
			}
		}
		
		workingImages.clear();
	}
	
	/**
	 * Try to pack an image into the tree.
	 * Returns the leaf node into which an image was inserted,
	 * or null if there was not enough space to fit the image.
	 */
	private BinTree insertImage(Image image, BinTree node) {
		if (node.isLeaf()) {
			if (node.hasImage()) {
				return null;
			}
			
			if (!node.canFitImage(image)) {
				return null;
			}
			
			if (node.canFitImageExactly(image)) {
				node.setImage(image);
				return node;
			}
			
			return createChildNodesAndInsertImage(image, node);
			
		} else {
			BinTree insertNode = insertImage(image, node.getLeft());
			
			if (insertNode != null) {
				return insertNode;
			}
			
			insertNode = insertImage(image, node.getRight());
			
			return insertNode;
		}
	}

	private BinTree createChildNodesAndInsertImage(Image image, BinTree parent) {
		Rectangle region = parent.getRegion();
		final int imageWidth = image.getWidth();
		final int imageHeight = image.getHeight();
		final int remainingWidth = parent.getRegion().width - imageWidth;
		final int remainingHeight = parent.getRegion().height - imageHeight;
		
		if (remainingWidth > remainingHeight) {
			// split vertically
			Rectangle leftRegion = new Rectangle(region.x, region.y, imageWidth, region.height);
			Rectangle rightRegion = new Rectangle(region.x + imageWidth, region.y, region.width - imageWidth, region.height);
			
			parent.addChildrenWithSubregions(leftRegion, rightRegion);
			
		} else {
			// split horizontally
			Rectangle leftRegion = new Rectangle(region.x, region.y, region.width, imageHeight);
			Rectangle rightRegion = new Rectangle(region.x, region.y + imageHeight, region.width, region.height - imageHeight);
			
			parent.addChildrenWithSubregions(leftRegion, rightRegion);
		}
		
		BinTree leftChild =	parent.getLeft();
		leftChild.setImage(image);
		
		return leftChild;
	}
}
