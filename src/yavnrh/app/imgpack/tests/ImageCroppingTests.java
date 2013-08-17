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

package yavnrh.app.imgpack.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import yavnrh.app.imgpack.Main;
import yavnrh.app.imgpack.Parameters;
import yavnrh.app.imgpack.packing.GridImagePacker;
import yavnrh.app.imgpack.packing.ImagePacker;
import yavnrh.app.imgpack.packing.MaxRectsImagePacker;
import yavnrh.app.imgpack.packing.image.CroppedImage;
import yavnrh.app.imgpack.packing.image.FileImage;
import yavnrh.app.imgpack.packing.image.Image;
import yavnrh.app.imgpack.packing.image.MockImage;

public class ImageCroppingTests {
	
	@Test
	public void testCropImage() {
		Image image = new FileImage("test/crop_me_guy.png");
		Image cropped = CroppedImage.from(image);
		
		assertEquals("{19, 18, 66, 146}", cropped.getCropRect().toString());
	}
	
	@Test
	public void testCropAlreadyCroppedImage() {
		Image image = new FileImage("test/crop_me_guy.png");		
		Image cropped = CroppedImage.from(image);
		Image croppedAgain = CroppedImage.from(cropped);

		assertTrue(cropped == croppedAgain);
	}
	
	@Test
	public void testCantCropAnything() {
		Image image = new MockImage("mock1", 32, 32);
		Image cropped = CroppedImage.from(image);
		
		assertTrue(image == cropped);
	}
	
	@Test
	public void testPackUsingGridCroppedImageThatWouldNotFitOtherwise() {
		Parameters params = new Parameters();
		params.setOutputWidth(80);
		params.setOutputHeight(80);
		params.setCrop(true);
		
		ImagePacker ip = new GridImagePacker(params);
		ip.addImage(new FileImage("test/crop_alot_face.png"));
		
		ip.pack();

		String expected = Main.concatenate(
				"{0, 0, 64, 59} : test/crop_alot_face.png\n");
		
		assertEquals(expected, ip.dumpRegions());
	}

	@Test
	public void testPackUsingMaxRectsCroppedImageThatWouldNotFitOtherwise() {
		Parameters params = new Parameters();
		params.setOutputWidth(80);
		params.setOutputHeight(80);
		params.setCrop(true);
		
		ImagePacker ip = new MaxRectsImagePacker(params);
		ip.addImage(new FileImage("test/crop_alot_face.png"));
		
		ip.pack();

		String expected = Main.concatenate(
				"{0, 0, 64, 59} : test/crop_alot_face.png\n");
		
		assertEquals(expected, ip.dumpRegions());
	}
	
}
