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

import org.junit.Test;

import yavnrh.app.imgpack.Main;
import yavnrh.app.imgpack.Parameters;
import yavnrh.app.imgpack.packing.Image;
import yavnrh.app.imgpack.packing.ImagePacker;
import yavnrh.app.imgpack.packing.MaxRectsImagePacker;

public class MaxRectsPackingTests {
	
	@Test
	public void testPackWithoutImages() {
		Parameters params = new Parameters();
		params.setOutputWidth(256);
		params.setOutputHeight(256);
		
		ImagePacker ip = new MaxRectsImagePacker(params);

		assertEquals("", ip.dumpRegions());		
	}
	
	@Test
	public void testPackOneImage() {
		Parameters params = new Parameters();
		params.setOutputWidth(128);
		params.setOutputHeight(128);
		
		ImagePacker ip = new MaxRectsImagePacker(params);
		ip.addImage(Image.mock("mock1", 90, 120));
		
		ip.pack();

		String expected = Main.concatenate(
				"{0, 0, 90, 120} : mock1\n");
		
		assertEquals(expected, ip.dumpRegions());
	}

	@Test
	public void testPackThreeImages() {
		Parameters params = new Parameters();
		params.setOutputWidth(128);
		params.setOutputHeight(128);
		
		ImagePacker ip = new MaxRectsImagePacker(params);
		ip.addImage(Image.mock("mock1", 40, 70));
		ip.addImage(Image.mock("mock2", 70, 70));
		ip.addImage(Image.mock("mock3", 90, 50));
		
		ip.pack();

		String expected = Main.concatenate(
				"{0, 0, 90, 50} : mock3\n",
				"{0, 50, 40, 70} : mock1\n",
				"{40, 50, 70, 70} : mock2\n");
		
		assertEquals(expected, ip.dumpRegions());
	}
	
	@Test
	public void testPackFiveImages() {
		Parameters params = new Parameters();
		params.setOutputWidth(128);
		params.setOutputHeight(128);
		
		ImagePacker ip = new MaxRectsImagePacker(params);
		ip.addImage(Image.mock("mock1", 32, 100));
		ip.addImage(Image.mock("mock2", 16, 90));
		ip.addImage(Image.mock("mock3", 20, 70));
		ip.addImage(Image.mock("mock4", 80, 16));
		ip.addImage(Image.mock("mock5", 70, 20));
		
		ip.pack();

		String expected = Main.concatenate(
				"{0, 0, 32, 100} : mock1\n",
				"{0, 100, 70, 20} : mock5\n",
				"{32, 0, 16, 90} : mock2\n",
				"{48, 0, 80, 16} : mock4\n",
				"{48, 16, 20, 70} : mock3\n");
		
		assertEquals(expected, ip.dumpRegions());
	}
}
