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
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import yavnrh.app.imgpack.Parameters;
import yavnrh.app.imgpack.exception.ImagePackingException;
import yavnrh.app.imgpack.packing.image.Image;

/**
 * MaxRects packing algorithm.
 * 
 * Packs images into a rectangle area, keeping track of free space
 * as maximal rectangles ("max rects") and splitting them into smaller ones
 * when necessary.
 * 
 * The method has some flexibility points; scoring method can be defined
 * to determine which image should be placed first and in which free rectangle. 
 * 
 * Based on the paper
 * "A Thousand Ways to Pack the Bin - A Practical Approach to Two-Dimensional Rectangle Bin Packing"
 * by Jukka Jylänki, Feb 27, 2010.
 * 
 */
public class MaxRectsImagePacker extends ImagePacker {
	
	private LinkedList<Image> images;
	
	// state used during the packing algorithm
	
	private LinkedList<Image> imagesToPack;
	private LinkedList<PackedImage> packedImages;
	private LinkedList<Rectangle> freeRects;	
	private LinkedList<ScoredPlacement> imagePlacementScores;
	private ScoringFunction scoringFunction;


	public MaxRectsImagePacker(Parameters params) {
		super(params);
		
		images = new LinkedList<Image>();
		imagesToPack = new LinkedList<Image>(images);
		packedImages = new LinkedList<PackedImage>();
		freeRects = new LinkedList<Rectangle>();
		imagePlacementScores = new LinkedList<ScoredPlacement>();
		scoringFunction = ScoringFunction.BEST_SHORT_SIDE_FIT;
	}
	
	@Override
	public List<PackedImage> getPackedImages() {
		return new ArrayList<PackedImage>(packedImages);
	}

	@Override
	public void addImage(Image image) {
		images.add(image);
	}

	@Override
	public void pack() {
		imagesToPack.clear();
		packedImages.clear();
		freeRects.clear();
		imagePlacementScores.clear();
		
		imagesToPack.addAll(images);		
		addInitialFreeRect();

		while (thereAreImagesToPack()) {
			computePlacementScoreForEachImageInEachFreeRectangle();
			
			ScoredPlacement bestPlacement = getBestPlacement(); 
			PackedImage packedImage = packImage(bestPlacement);
			
			splitFreeRectsThatOverlapWithRect(packedImage.rectangle);
			removeRedundantFreeRects();
		}
	}

	private void addInitialFreeRect() {
		final int border = params.getBorder();
		final int width = Math.max(0, params.getOutputWidth() - border);
		final int height = Math.max(0, params.getOutputHeight() - border);
		
		freeRects.add(new Rectangle(border, border, width, height));
	}

	private void splitFreeRectsThatOverlapWithRect(Rectangle used) {
		Rectangle[] rectsToProcess = freeRects.toArray(new Rectangle[freeRects.size()]);
		
		for (Rectangle rect : rectsToProcess) {
			if (rectsAreIntersecting(rect, used)) {
				ArrayList<Rectangle> split = splitRectangle(rect, used);

				freeRects.remove(rect);
				freeRects.addAll(split);
			}
		}
	}

	private boolean rectsAreIntersecting(Rectangle a, Rectangle b) {
		return (a.x2 > b.x) && (a.x < b.x2) &&
			   (a.y2 > b.y) && (a.y < b.y2); 
	}

	private ArrayList<Rectangle> splitRectangle(Rectangle rect, Rectangle used) {
		final int dx1 = used.x  - rect.x;
		final int dx2 = rect.x2 - used.x2;
		final int dy1 = used.y  - rect.y;
		final int dy2 = rect.y2 - used.y2;

		ArrayList<Rectangle> splitRects = new ArrayList<Rectangle>(4);

		// rect on the left
		if (dx1 > 0) {
			splitRects.add(new Rectangle(rect.x, rect.y, dx1, rect.height));
		}
		
		// rect on the right
		if (dx2 > 0) {
			splitRects.add(new Rectangle(rect.x2 - dx2, rect.y, dx2, rect.height));
		}
		
		// rect on the top
		if (dy1 > 0) {
			splitRects.add(new Rectangle(rect.x, rect.y, rect.width, dy1));
		}
		
		// rect on the bottom
		if (dy2 > 0) {
			splitRects.add(new Rectangle(rect.x, rect.y2 - dy2, rect.width, dy2));
		}
		
		return splitRects;
	}

	private ScoredPlacement getBestPlacement() {
		ScoredPlacement placement = imagePlacementScores.getFirst();
		
		final boolean canFitWidth = placement.rectangle.width >= placement.image.getWidth();
		final boolean canFitHeight = placement.rectangle.height >= placement.image.getHeight();
		
		if (!(canFitWidth && canFitHeight)) {
			throw new ImagePackingException("Not enough space to pack " + placement.image.getName());
		}
		
		return placement;
	}

	private void removeRedundantFreeRects() {
		Rectangle[] rectsToProcess = freeRects.toArray(new Rectangle[freeRects.size()]);
		
		final int INDEX_NONE = -1;
		
		for (int i = 0; true; i++) {
			i = indexOfNextNonNullObjectInArray(i, rectsToProcess);
			
			if (i == INDEX_NONE) break;
			
			Rectangle ithRect = rectsToProcess[i];

			for (int j = 0; true; j++) {
				j = indexOfNextNonNullObjectInArray(j, rectsToProcess);
				
				if (j == i) continue;
				if (j == INDEX_NONE) break;
				
				Rectangle jthRect = rectsToProcess[j];
				
				if (rectangleContainsSubrectangle(ithRect, jthRect)) {					
					rectsToProcess[j] = null;					
					continue;
				}
			}
		}
		
		freeRects.clear();
		addNonNullRectsToList(rectsToProcess, freeRects);
	}
	
	private void addNonNullRectsToList(Rectangle[] arrayWithNulls, LinkedList<Rectangle> list) {
		for (Rectangle rect : arrayWithNulls) {
			if (rect != null) {
				list.add(rect);
			}
		}
	}

	private boolean rectangleContainsSubrectangle(Rectangle rect, Rectangle subRect) {
		return (rect.x <= subRect.x) && (rect.x2 >= subRect.x2) &&
			   (rect.y <= subRect.y) && (rect.y2 >= subRect.y2);
	}

	private int indexOfNextNonNullObjectInArray(int startIndex, Object[] array) {
		if (startIndex >= array.length) {
			return -1;
		}
		
		for (int i = startIndex; i < array.length; i++) {
			if (array[i] != null) {
				return i;
			}
		}
		
		return -1;
	}

	private PackedImage packImage(ScoredPlacement bestPlacement) {
		final int spacing = params.getSpacing();
		
		Rectangle usedRect = new Rectangle(
				bestPlacement.rectangle.x,
				bestPlacement.rectangle.y,
				bestPlacement.image.getWidth() + spacing,
				bestPlacement.image.getHeight() + spacing);
		
		PackedImage packed = new PackedImage(usedRect, bestPlacement.image);

		imagesToPack.remove(bestPlacement.image);
		packedImages.add(packed);

		return packed;
	}

	private boolean thereAreImagesToPack() {
		return imagesToPack.size() > 0;
	}

	private void computePlacementScoreForEachImageInEachFreeRectangle() {
		imagePlacementScores.clear();		
		for (Rectangle rect : freeRects) {
			for (Image image : imagesToPack) {
				computePlacementScore(rect, image);
			}
		}
		Collections.sort(imagePlacementScores);
	}

	private void computePlacementScore(Rectangle rect, Image image) {
		final int score = scoringFunction.score(rect, image);
		imagePlacementScores.add(new ScoredPlacement(rect, image, score));
	}
	
}
