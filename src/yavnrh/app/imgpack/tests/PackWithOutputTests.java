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

import static org.junit.Assert.assertNotNull;

import java.awt.image.BufferedImage;

import org.junit.Test;

import yavnrh.app.imgpack.Main;
import yavnrh.app.imgpack.Parameters;
import yavnrh.app.imgpack.packing.Image;
import yavnrh.app.imgpack.packing.ImagePacker;
import yavnrh.app.imgpack.packing.MaxRectsImagePacker;

public class PackWithOutputTests {
	
	@Test
	public void testPackOneImage() {
		Parameters params = new Parameters();
		params.setOutputWidth(128);
		params.setOutputHeight(128);
		params.setOutputName("test_maxrects_one_image");
		params.setOverwriteOutput(true);
		
		ImagePacker ip = new MaxRectsImagePacker(params);
		ip.addImage(Image.mock("mock1", 90, 120));
		
		ip.pack();
		
		BufferedImage outputImage = ip.getOutputImage();
		assertNotNull(outputImage);
		
		Main.writeOutputImage(outputImage, params);
	}

	@Test
	public void testPackThreeImages() {
		Parameters params = new Parameters();
		params.setOutputWidth(128);
		params.setOutputHeight(128);
		params.setOutputName("test_maxrects_three_images");
		params.setOverwriteOutput(true);
		
		ImagePacker ip = new MaxRectsImagePacker(params);
		ip.addImage(Image.mock("mock1", 40, 70));
		ip.addImage(Image.mock("mock2", 70, 70));
		ip.addImage(Image.mock("mock3", 90, 50));
		
		ip.pack();

		BufferedImage outputImage = ip.getOutputImage();
		assertNotNull(outputImage);
		
		Main.writeOutputImage(outputImage, params);
	}
	
}
