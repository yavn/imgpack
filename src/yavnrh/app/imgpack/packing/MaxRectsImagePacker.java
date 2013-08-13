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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import yavnrh.app.imgpack.Parameters;
import yavnrh.app.imgpack.exception.ImagePackingException;

public class MaxRectsImagePacker extends ImagePacker {
	
	// input
	
	private LinkedList<Image> images;
	private int width;
	private int height;
	
	// state used during the packing algorithm
	
	private LinkedList<Image> imagesToPack;
	private LinkedList<PackedImage> packedImages;
	private LinkedList<Rectangle> freeRects;	
	private LinkedList<PackingScore> imageScores;
	private ScoringFunction scoringFunction;
	
	public MaxRectsImagePacker(Parameters params) {
		images = new LinkedList<Image>();
		width = params.getOutputWidth();
		height = params.getOutputHeight();
	}
	
	@Override
	public List<PackedImage> getImageRegions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addImage(Image image) {
		images.add(image);
	}

	@Override
	public void pack() {
		imagesToPack = new LinkedList<Image>(images);
		packedImages = new LinkedList<PackedImage>();
		freeRects = new LinkedList<Rectangle>();
		imageScores = new LinkedList<PackingScore>();
		scoringFunction = ScoringFunction.BEST_SHORT_SIDE_FIT;
		
		freeRects.add(new Rectangle(0, 0, width, height));

		while (thereAreImagesToPack()) {
			computePlacementScoreForEachImageInEachFreeRectangle();			
			PackingScore bestPlacement = getBestPlacement(); 
			PackedImage packedImage = packImage(bestPlacement);			
			splitRectIntoNewFreeRects(bestPlacement.rectangle, packedImage.rectangle);
			reduceFreeRects();
		}
	}

	private PackingScore getBestPlacement() {
		PackingScore placement = imageScores.getFirst();
		
		final boolean canFitWidth = placement.rectangle.width >= placement.image.getWidth();
		final boolean canFitHeight = placement.rectangle.height >= placement.image.getHeight();
		
		if (!(canFitWidth && canFitHeight)) {
			throw new ImagePackingException("Not enough space to pack " + placement.image.getName());
		}
		
		return placement;
	}

	private void reduceFreeRects() {
		// TODO implement
		// remove rects completely inside other rects
		// join rects that can form a bigger rect
	}

	private PackedImage packImage(PackingScore bestPlacement) {
		Rectangle occupiedRect = new Rectangle(
				bestPlacement.rectangle.x,
				bestPlacement.rectangle.y,
				bestPlacement.image.getWidth(),
				bestPlacement.image.getHeight());
		
		PackedImage packed = new PackedImage(occupiedRect, bestPlacement.image);

		imagesToPack.remove(bestPlacement.image);
		packedImages.add(packed);
		
		return packed;
	}

	private void splitRectIntoNewFreeRects(Rectangle rect, Rectangle used) {
		final int deltaWidth = rect.width - used.width;
		final int deltaHeight = rect.height - used.height;

		// Add up to two free rects. Note they may overlap.
		
		if (deltaWidth > 0) {
			freeRects.add(new Rectangle(
					rect.x + used.width, rect.y,
					deltaWidth, rect.height));
		}
		
		if (deltaHeight > 0) {
			freeRects.add(new Rectangle(
					rect.x, rect.y + used.height,
					rect.width, deltaHeight));
		}
	}

	private boolean thereAreImagesToPack() {
		return imagesToPack.size() > 0;
	}

	private void computePlacementScoreForEachImageInEachFreeRectangle() {
		imageScores.clear();		
		for (Rectangle rect : freeRects) {
			for (Image image : imagesToPack) {
				computePlacementScore(rect, image);
			}
		}
		Collections.sort(imageScores);
	}

	private void computePlacementScore(Rectangle rect, Image image) {
		final int score = scoringFunction.score(rect, image);
		imageScores.add(new PackingScore(rect, image, score));
	}
}
