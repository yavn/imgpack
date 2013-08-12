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
import yavnrh.app.imgpack.packing.BinTreeImagePacker;
import yavnrh.app.imgpack.packing.Image;
import yavnrh.app.imgpack.packing.ImagePacker;

public class BinTreePackingTests {
	
	@Test
	public void testPackWithoutImages() {
		Parameters params = new Parameters();
		params.setOutputWidth(256);
		params.setOutputHeight(256);
		
		ImagePacker ip = new BinTreeImagePacker(params);

		assertEquals("{0, 0, 256, 256} : -\n", ip.dumpRegions());		
	}
	
	@Test
	public void testPackOneImage() {
		Parameters params = new Parameters();
		params.setOutputWidth(128);
		params.setOutputHeight(128);
		
		ImagePacker ip = new BinTreeImagePacker(params);
		ip.addImage(Image.mock("mock1", 90, 120));
		
		ip.pack();

		String expected = Main.concatenate(
				"{0, 0, 90, 128} : mock1\n",
				"{90, 0, 38, 128} : -\n");
		
		assertEquals(expected, ip.dumpRegions());
	}
	
	@Test
	public void testPackThreeImages() {
		Parameters params = new Parameters();
		params.setOutputWidth(128);
		params.setOutputHeight(128);
		
		ImagePacker ip = new BinTreeImagePacker(params);
		ip.addImage(Image.mock("mock1", 40, 70));
		ip.addImage(Image.mock("mock2", 70, 70));
		ip.addImage(Image.mock("mock3", 90, 50));
		
		ip.pack();

		String expected = Main.concatenate(
				"{0, 0, 128, 50} : mock3\n",
				"{0, 50, 40, 78} : mock1\n",
				"{40, 50, 70, 78} : mock2\n",
				"{110, 50, 18, 78} : -\n");
		
		assertEquals(expected, ip.dumpRegions());
	}
}
