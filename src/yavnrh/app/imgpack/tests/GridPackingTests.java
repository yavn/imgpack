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
import static org.junit.Assert.fail;

import org.junit.Test;

import yavnrh.app.imgpack.Main;
import yavnrh.app.imgpack.Parameters;
import yavnrh.app.imgpack.exception.ImagePackingException;
import yavnrh.app.imgpack.packing.GridImagePacker;
import yavnrh.app.imgpack.packing.Image;
import yavnrh.app.imgpack.packing.ImagePacker;

public class GridPackingTests {
	
	@Test
	public void testPackOneImage() {
		Parameters params = new Parameters();
		params.setOutputWidth(128);
		params.setOutputHeight(128);
		
		ImagePacker ip = new GridImagePacker(params);
		ip.addImage(Image.mock("mock1", 64, 64));
		
		ip.pack();

		String expected = Main.concatenate(
				"{0, 0, 64, 64} : mock1\n");
		
		assertEquals(expected, ip.dumpRegions());
	}
	
	@Test
	public void testPackFourImages() {
		Parameters params = new Parameters();
		params.setOutputWidth(128);
		params.setOutputHeight(128);
		params.setBorder(3);
		params.setSpacing(1);
		
		ImagePacker ip = new GridImagePacker(params);
		ip.addImage(Image.mock("mock1", 50, 50));
		ip.addImage(Image.mock("mock2", 48, 48));
		ip.addImage(Image.mock("mock3", 62, 40));
		ip.addImage(Image.mock("mock4", 30, 30));
		
		ip.pack();

		String expected = Main.concatenate(
				"{3, 3, 50, 50} : mock1\n",
				"{54, 3, 48, 48} : mock2\n",
				"{3, 54, 62, 40} : mock3\n",
				"{66, 54, 30, 30} : mock4\n");
		
		assertEquals(expected, ip.dumpRegions());
	}	

	@Test
	public void testPackSeveralImages() {
		Parameters params = new Parameters();
		params.setOutputWidth(128);
		params.setOutputHeight(128);
		
		ImagePacker ip = new GridImagePacker(params);
		ip.addImage(Image.mock("mock1", 64, 64));
		ip.addImage(Image.mock("mock2", 32, 32));
		ip.addImage(Image.mock("mock3", 16, 16));
		ip.addImage(Image.mock("mock4", 16, 16));
		ip.addImage(Image.mock("mock5", 32, 32));
		ip.addImage(Image.mock("mock6", 64, 64));
		
		ip.pack();

		String expected = Main.concatenate(
				"{0, 0, 64, 64} : mock1\n",
				"{64, 0, 32, 32} : mock2\n",
				"{96, 0, 16, 16} : mock3\n",
				"{112, 0, 16, 16} : mock4\n",
				"{0, 64, 32, 32} : mock5\n",
				"{32, 64, 64, 64} : mock6\n");
		
		assertEquals(expected, ip.dumpRegions());
	}
	
	@Test(expected = ImagePackingException.class)
	public void testPackImageThatIsTooBig() {
		Parameters params = new Parameters();
		params.setOutputWidth(128);
		params.setOutputHeight(128);
		
		ImagePacker ip = new GridImagePacker(params);
		ip.addImage(Image.mock("mock1", 128, 150));
		
		ip.pack();
	}	

	@Test
	public void testPackTooManyImages() {
		Parameters params = new Parameters();
		params.setOutputWidth(128);
		params.setOutputHeight(128);
		
		ImagePacker ip = new GridImagePacker(params);
		ip.addImage(Image.mock("mock1", 70, 70));
		ip.addImage(Image.mock("mock2", 80, 80));
		
		try {
			ip.pack();
		} catch (ImagePackingException ex) {
			assertEquals("Not enough space to pack mock2", ex.getMessage());
			return;
		}
		
		fail("Did not throw correct exception");
	}	

}
