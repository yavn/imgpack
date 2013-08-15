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

public class CommandOutputSize extends Command {

	private CommandProcessor cp;
	private Parameters params;

	public CommandOutputSize(CommandProcessor cp, Parameters params) {
		super("size");
		this.cp = cp;
		this.params = params;
	}

	@Override
	public void execute() {
		String widthString = cp.nextArg();
		String heightString = cp.nextArg();
		
		validateArguments(widthString, heightString);
		
		params.setOutputWidth(Integer.parseInt(widthString));
		params.setOutputHeight(Integer.parseInt(heightString));
	}

	private void validateArguments(String widthString, String heightString) {
		if (widthString == null) {
			throw new CommandArgumentException("No width specified");
		}
		
		if (heightString == null) {
			throw new CommandArgumentException("No height specified");
		}
		
		final int width = Integer.parseInt(widthString);
		final int height = Integer.parseInt(heightString);
		
		if (width <= 0) {
			throw new CommandArgumentException("Output width must be greater than zero");
		}
		
		if (height <= 0) {
			throw new CommandArgumentException("Output height must be greater than zero");
		}
	}
	
	@Override
	public String help() {
		return Main.concatenate("  ", command, " <width> <height> - size of the output texture image (in pixels).");
	}
}
