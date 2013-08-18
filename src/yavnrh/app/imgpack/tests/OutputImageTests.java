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
import yavnrh.app.imgpack.packing.GridImagePacker;
import yavnrh.app.imgpack.packing.ImagePacker;
import yavnrh.app.imgpack.packing.MaxRectsImagePacker;
import yavnrh.app.imgpack.packing.image.MockImage;

public class OutputImageTests {
	
	@Test
	public void testPackOneImage() {
		Parameters params = new Parameters();
		params.setOutputWidth(128);
		params.setOutputHeight(128);
		params.setOutputName("test/test_maxrects_one_image");
		params.setOverwriteOutput(true);
		
		ImagePacker ip = new MaxRectsImagePacker(params);
		ip.addImage(new MockImage("mock1", 90, 120));
		
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
		params.setOutputName("test/test_maxrects_three_images");
		params.setOverwriteOutput(true);
		
		ImagePacker ip = new MaxRectsImagePacker(params);
		ip.addImage(new MockImage("mock1", 40, 70));
		ip.addImage(new MockImage("mock2", 70, 70));
		ip.addImage(new MockImage("mock3", 90, 50));
		
		ip.pack();

		BufferedImage outputImage = ip.getOutputImage();
		assertNotNull(outputImage);
		
		Main.writeOutputImage(outputImage, params);
	}
	
	@Test
	public void testPackFiveImages() {
		Parameters params = new Parameters();
		params.setOutputWidth(128);
		params.setOutputHeight(128);
		params.setOutputName("test/test_maxrects_five_images");
		params.setOverwriteOutput(true);
		
		ImagePacker ip = new MaxRectsImagePacker(params);
		ip.addImage(new MockImage("mock1", 32, 100));
		ip.addImage(new MockImage("mock2", 16, 90));
		ip.addImage(new MockImage("mock3", 20, 70));
		ip.addImage(new MockImage("mock4", 80, 16));
		ip.addImage(new MockImage("mock5", 70, 20));
		
		ip.pack();

		BufferedImage outputImage = ip.getOutputImage();
		assertNotNull(outputImage);
		
		Main.writeOutputImage(outputImage, params);
	}
	
	@Test
	public void testPackManyImages() {
		Parameters params = new Parameters();
		params.setOutputWidth(128);
		params.setOutputHeight(128);
		params.setOutputName("test/test_maxrects_many_images");
		params.setOverwriteOutput(true);
		
		ImagePacker ip = new MaxRectsImagePacker(params);
		ip.addImage(new MockImage("mock1", 32, 32));
		ip.addImage(new MockImage("mock2", 16, 16));
		ip.addImage(new MockImage("mock3", 24, 48));
		ip.addImage(new MockImage("mock4", 50, 30));
		ip.addImage(new MockImage("mock5", 90, 30));
		ip.addImage(new MockImage("mock6", 35, 70));
		ip.addImage(new MockImage("mock7", 8, 67));
		ip.addImage(new MockImage("mock8", 75, 24));
		ip.addImage(new MockImage("mock9", 27, 32));
		ip.addImage(new MockImage("mock10", 20, 35));
		ip.addImage(new MockImage("mock11", 25, 40));
		
		ip.pack();

		BufferedImage outputImage = ip.getOutputImage();
		assertNotNull(outputImage);
		
		Main.writeOutputImage(outputImage, params);
	}
	
	@Test
	public void testPackImagesWithBorderAndSpacing() {
		Parameters params = new Parameters();
		params.setOutputWidth(128);
		params.setOutputHeight(128);
		params.setBorder(14);
		params.setSpacing(6);
		params.setOutputName("test/test_maxrects_border_spacing");
		params.setOverwriteOutput(true);
		
		ImagePacker ip = new MaxRectsImagePacker(params);
		ip.addImage(new MockImage("mock1", 40, 40));
		ip.addImage(new MockImage("mock2", 40, 60));
		ip.addImage(new MockImage("mock3", 100, 30));
		
		ip.pack();

		BufferedImage outputImage = ip.getOutputImage();
		assertNotNull(outputImage);
		
		Main.writeOutputImage(outputImage, params);
	}
	
	@Test
	public void testPackTwoRowsWithGridAlgorithm() {
		Parameters params = new Parameters();
		params.setOutputWidth(128);
		params.setOutputHeight(128);
		params.setOutputName("test/test_grid_two_rows");
		params.setOverwriteOutput(true);
		
		ImagePacker ip = new GridImagePacker(params);
		ip.addImage(new MockImage("mock1", 64, 64));
		ip.addImage(new MockImage("mock2", 32, 32));
		ip.addImage(new MockImage("mock3", 32, 16));
		ip.addImage(new MockImage("mock4", 32, 16));
		ip.addImage(new MockImage("mock5", 32, 32));
		ip.addImage(new MockImage("mock6", 64, 64));
		
		ip.pack();

		BufferedImage outputImage = ip.getOutputImage();
		assertNotNull(outputImage);
		
		Main.writeOutputImage(outputImage, params);
	}	
	
	@Test
	public void testPackTilemapWithGridAlgorithm() {
		Parameters params = new Parameters();
		params.setOutputWidth(128);
		params.setOutputHeight(128);
		params.setBorder(10);
		params.setSpacing(4);
		params.setOutputName("test/test_grid_tilemap");
		params.setOverwriteOutput(true);
		
		ImagePacker ip = new GridImagePacker(params);
		ip.addImage(new MockImage("mock1", 24, 24));
		ip.addImage(new MockImage("mock2", 24, 24));
		ip.addImage(new MockImage("mock3", 24, 24));
		ip.addImage(new MockImage("mock4", 24, 24));
		ip.addImage(new MockImage("mock5", 24, 24));
		ip.addImage(new MockImage("mock6", 24, 24));
		ip.addImage(new MockImage("mock7", 24, 24));
		ip.addImage(new MockImage("mock8", 24, 24));
		ip.addImage(new MockImage("mock9", 24, 24));
		ip.addImage(new MockImage("mock10", 24, 24));
		ip.addImage(new MockImage("mock11", 24, 24));
		ip.addImage(new MockImage("mock12", 24, 24));
		ip.addImage(new MockImage("mock13", 24, 24));
		ip.addImage(new MockImage("mock14", 24, 24));
		ip.addImage(new MockImage("mock15", 24, 24));
		ip.addImage(new MockImage("mock16", 24, 24));
		
		ip.pack();

		BufferedImage outputImage = ip.getOutputImage();
		assertNotNull(outputImage);
		
		Main.writeOutputImage(outputImage, params);
	}
	
}
