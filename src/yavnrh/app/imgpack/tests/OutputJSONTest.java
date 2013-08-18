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

import yavnrh.app.imgpack.PackedImagesSerializer;
import yavnrh.app.imgpack.Parameters;
import yavnrh.app.imgpack.packing.ImagePacker;
import yavnrh.app.imgpack.packing.MaxRectsImagePacker;
import yavnrh.app.imgpack.packing.image.FileImage;
import yavnrh.app.imgpack.packing.image.MockImage;

public class OutputJSONTest {
	
	@Test
	public void testOutputForOneImage() {
		Parameters params = new Parameters();
		params.setOutputWidth(128);
		params.setOutputHeight(128);
		
		ImagePacker ip = new MaxRectsImagePacker(params);
		ip.addImage(new MockImage("mock1", 90, 120));
		
		ip.pack();

		PackedImagesSerializer serializer = new PackedImagesSerializer(params, ip.getPackedImages());
		
		assertEquals("{\"width\":128,\"height\":128,\"images\":[{\"name\":\"mock1\",\"x\":0,\"y\":0,\"width\":90,\"height\":120,\"isCropped\":false}]}",
				serializer.getString());
	}
	
	@Test
	public void testOutputForCroppedImage() {
		Parameters params = new Parameters();
		params.setOutputWidth(128);
		params.setOutputHeight(128);
		params.setCrop(true);
		
		ImagePacker ip = new MaxRectsImagePacker(params);
		ip.addImage(new FileImage("test/crop_alot_face.png"));
		
		ip.pack();

		PackedImagesSerializer serializer = new PackedImagesSerializer(params, ip.getPackedImages());
		
		assertEquals("{\"width\":128,\"height\":128,\"images\":[{\"name\":\"test/crop_alot_face.png\",\"x\":0,\"y\":0,\"width\":64,\"height\":59,\"isCropped\":true,\"fullOffsetX\":35,\"fullOffsetY\":34,\"fullWidth\":128,\"fullHeight\":128}]}",
				serializer.getString());		
	}
	
	@Test
	public void testOutputForSeveralImages() {
		Parameters params = new Parameters();
		params.setOutputWidth(128);
		params.setOutputHeight(128);
		
		ImagePacker ip = new MaxRectsImagePacker(params);
		ip.addImage(new MockImage("mock1", 32, 40));
		ip.addImage(new MockImage("mock2", 50, 30));
		ip.addImage(new MockImage("mock3", 64, 64));
		
		ip.pack();

		PackedImagesSerializer serializer = new PackedImagesSerializer(params, ip.getPackedImages());
				
		assertEquals("{\"width\":128,\"height\":128,\"images\":[{\"name\":\"mock3\",\"x\":0,\"y\":0,\"width\":64,\"height\":64,\"isCropped\":false},{\"name\":\"mock2\",\"x\":64,\"y\":0,\"width\":50,\"height\":30,\"isCropped\":false},{\"name\":\"mock1\",\"x\":0,\"y\":64,\"width\":32,\"height\":40,\"isCropped\":false}]}",
				serializer.getString());
	}
}
