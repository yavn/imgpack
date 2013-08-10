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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import yavnrh.app.imgpack.CommandProcessor;

public class CommandTests {

	private final ByteArrayOutputStream out = new ByteArrayOutputStream();
	private CommandProcessor cp;
	
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
		cp.process();
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
}
