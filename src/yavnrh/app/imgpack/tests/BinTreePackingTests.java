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
	public void testPackOneImage() {
		Parameters params = new Parameters();
		params.setOutputWidth(128);
		params.setOutputHeight(128);
		
		ImagePacker ip = new BinTreeImagePacker(params);
		ip.addImage(Image.mock("mock1", 90, 120));

		String expected = Main.concatenate(
				"{0, 0, 90, 120} : mock1\n",
				"{90, 0, 38, 120} : -\n",
				"{0, 120, 128, 8} : -\n");
		
		assertEquals(expected, ip.dumpRegions());
	}
}
