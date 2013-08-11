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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import yavnrh.app.imgpack.CommandProcessor;
import yavnrh.app.imgpack.ImagePacker;
import yavnrh.app.imgpack.exception.DuplicateImageException;
import yavnrh.app.imgpack.exception.InvalidCommandException;
import yavnrh.app.imgpack.exception.InvalidPackingMethodException;
import yavnrh.app.imgpack.exception.MissingArgumentException;

public class CommandTests {

	private final ByteArrayOutputStream out = new ByteArrayOutputStream();
	private CommandProcessor cp;
	private ImagePacker ip;
	
	@Before
	public void setUp() {
	    System.setOut(new PrintStream(out));
	}
	
	@After
	public void tearDown() {
		System.setOut(null);
	    out.reset();
	}
	
	private void runWithCommandLine(String commandLine) {
		if (commandLine.length() > 0) {
			cp = new CommandProcessor(commandLine.split(" "));
		} else {
			cp = new CommandProcessor(new String[0]);
		}
		cp.start();
		
		ip = cp.getImagePacker();
	}

	@Test
	public void testHelp() {
		runWithCommandLine("");
		
		assertTrue(out.toString().startsWith(
				"imgpack - Dumb image packing tool, version 0.1\n"
				+ "Copyright 2013 Maciej Jesionowski\n"
				+ "This program is free software. See GNU GPL license for details."));
	}

	@Test 
	public void testHelpHelp() {
		runWithCommandLine("-help help");
		
		assertEquals("  help <command> - print help for a command.\n", out.toString());
	}
	
	@Test(expected = InvalidCommandException.class)
	public void testInvalidCommand() {
		runWithCommandLine("-dupa");
	}
	
	@Test
	public void testGarbageArguments() {
		runWithCommandLine("cows from space");
		
		assertTrue(out.toString().startsWith("Ignored argument:"));
	}
	
	@Test
	public void testOutputName() {
		runWithCommandLine("-name output");
		
		assertEquals("output", ip.getOutputName());
	}
	
	@Test(expected = MissingArgumentException.class)
	public void testMissingOutputName() {
		runWithCommandLine("-name");
	}

	@Test
	public void testOverwriteOutput() {
		runWithCommandLine("-overwrite");
		
		assertTrue(ip.getOverwriteOutput());
	}

	@Test
	public void testOverwriteOutputShouldBeFalseByDefault() {
		runWithCommandLine("");
		
		assertFalse(ip.getOverwriteOutput());
	}

	@Test
	public void testOutputSize() {
		runWithCommandLine("-size 512 256");
		
		assertEquals(512, ip.getOutputWidth());
		assertEquals(256, ip.getOutputHeight());
	}
	
	@Test(expected = MissingArgumentException.class)
	public void testMissingOutputSize() {
		runWithCommandLine("-size 128");
	}
	
	@Test
	public void testAddImage() {
		runWithCommandLine("-add image01.png");
		List<String> images = Arrays.asList(ip.getImages());
		
		assertTrue(images.contains("image01.png"));
	}

	@Test(expected = DuplicateImageException.class)
	public void testAddDuplicateImage() {
		runWithCommandLine("-add image01.png -add image01.png");
	}
	
	@Test
	public void testAddMultipleImages() {
		runWithCommandLine("-add image01.png -add image02.png -add image03.png");
		List<String> images = Arrays.asList(ip.getImages());
		
		assertEquals(3, images.size());
		assertTrue(images.contains("image01.png"));
		assertTrue(images.contains("image02.png"));
		assertTrue(images.contains("image03.png"));
	}
	
	@Test
	public void testAddImagesPreservesOrder() {
		runWithCommandLine("-add monkey.png -add apple.png -add pear.png -add elephant.png");
		String[] images = ip.getImages();
		
		assertEquals("monkey.png", images[0]);
		assertEquals("apple.png", images[1]);
		assertEquals("pear.png", images[2]);
		assertEquals("elephant.png", images[3]);
	}
	
	@Test
	public void testSpacing() {
		runWithCommandLine("-spacing 4");
		
		assertEquals(4, ip.getSpacing());
	}

	@Test
	public void testSpacingShouldBeZeroByDefault() {
		runWithCommandLine("");
		
		assertEquals(0, ip.getSpacing());
	}
	
	@Test
	public void testBorder() {
		runWithCommandLine("-border 3");
		
		assertEquals(3, ip.getBorder());
	}
	
	@Test
	public void testBorderShouldBeZeroByDefault() {
		runWithCommandLine("");
		
		assertEquals(0, ip.getBorder());
	}
	
	@Test
	public void testCrop() {
		runWithCommandLine("-crop");
		
		assertTrue(ip.getCrop());
	}

	@Test
	public void testCropShouldBeFalseByDefault() {
		runWithCommandLine("");
		
		assertFalse(ip.getCrop());
	}
	
	@Test
	public void testMethodGrid() {
		runWithCommandLine("-method grid");
		
		assertEquals(ImagePacker.PackingMethod.GRID, ip.getMethod());
	}

	@Test
	public void testMethodBinaryTree() {
		runWithCommandLine("-method bintree");
		
		assertEquals(ImagePacker.PackingMethod.BINARY_TREE, ip.getMethod());
	}
	
	@Test
	public void testMethodShouldBeBinaryTreeByDefault() {
		runWithCommandLine("");
		
		assertEquals(ImagePacker.PackingMethod.BINARY_TREE, ip.getMethod());
	}
	
	@Test(expected = InvalidPackingMethodException.class)
	public void testInvalidMethod() {
		runWithCommandLine("-method derp");
	}
}
