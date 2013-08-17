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
import java.util.LinkedList;
import java.util.List;

import yavnrh.app.imgpack.Parameters;
import yavnrh.app.imgpack.exception.ImagePackingException;
import yavnrh.app.imgpack.packing.image.Image;

/**
 * A very basic packing scheme which preserves images order.
 * Each image is put in a row until there's no more room for a next image.
 * Then a new row is created and so on.
 * 
 * This packing method might be useful for some sprite animation purposes
 * or tilemaps.
 */
public class GridImagePacker extends ImagePacker {
	
	private LinkedList<Image> images;
	
	// state used during the packing algorithm
	
	private LinkedList<Image> imagesToPack;
	private LinkedList<PackedImage> packedImages;
	private int insertX;
	private int insertY;
	private int rowHeight;
	
	
	public GridImagePacker(Parameters params) {
		super(params);

		images = new LinkedList<Image>();
		imagesToPack = new LinkedList<Image>();
		packedImages = new LinkedList<PackedImage>();
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
		packedImages.clear();
		insertX = params.getBorder();
		insertY = params.getBorder();
		rowHeight = 0;
		
		prepareCroppedImagesIfNeeded(images, imagesToPack);
		
		while (thereAreImagesToPack()) {
			Image image = getNextImage();
			packImage(image);
		}
	}

	private boolean thereAreImagesToPack() {
		return imagesToPack.size() > 0;
	}

	private Image getNextImage() {
		return imagesToPack.poll();
	}

	private void packImage(Image image) {
		if (!canFitImage(image)) {
			goToNextRow();
		}
		
		if (canFitImage(image)) {		
			insertImage(image);
		} else {
			throw new ImagePackingException("Not enough space to pack " + image.getName());
		}
	}

	private boolean canFitImage(Image image) {
		final int freeWidth = params.getOutputWidth() - params.getBorder() - insertX;
		final int freeHeight = params.getOutputHeight() - params.getBorder() - insertY;

		return (image.getWidth() <= freeWidth) && (image.getHeight() <= freeHeight);
	}

	private void goToNextRow() {
		insertX = params.getBorder();
		insertY += rowHeight + params.getSpacing();
		rowHeight = 0;
	}

	private void insertImage(Image image) {
		Rectangle used = new Rectangle(insertX, insertY, image.getWidth(), image.getHeight());
		packedImages.add(new PackedImage(used, image));
		
		insertX += image.getWidth() + params.getSpacing();
		rowHeight = Math.max(rowHeight, image.getHeight());
	}

}
