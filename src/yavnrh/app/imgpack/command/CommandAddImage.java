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

package yavnrh.app.imgpack.command;

import yavnrh.app.imgpack.CommandProcessor;
import yavnrh.app.imgpack.Parameters;
import yavnrh.app.imgpack.Main;
import yavnrh.app.imgpack.exception.CommandArgumentException;

public class CommandAddImage extends Command {

	private Parameters params;
	private CommandProcessor cp;

	public CommandAddImage(CommandProcessor cp, Parameters params) {
		super("add");
		this.cp = cp;
		this.params = params;
	}

	@Override
	public void execute() {
		String imageFilename = cp.nextArg();
		
		validateArgument(imageFilename);

		params.addImage(imageFilename);
	}

	private void validateArgument(String imageFilename) {
		if (imageFilename == null || imageFilename.length() == 0) {
			throw new CommandArgumentException("No image specified");
		}
	}
	
	@Override
	public String help() {
		return Main.concatenate("  ", command, " <file> - add an image to the atlas.");
	}
}
