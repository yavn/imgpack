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

public class CommandSpacing extends Command {

	private Parameters params;
	private CommandProcessor cp;
	
	public CommandSpacing(CommandProcessor cp, Parameters params) {
		super("spacing");
		this.cp = cp;
		this.params = params;
	}

	@Override
	public void execute() {
		String spacingString = cp.nextArg();
		
		validateArgument(spacingString);
		
		params.setSpacing(Integer.parseInt(spacingString));
	}

	private void validateArgument(String arg) {
		if (arg == null) {
			throw new CommandArgumentException("No spacing value specified");
		}
		
		final int spacing = Integer.parseInt(arg);
		
		if (spacing < 0) {
			throw new CommandArgumentException("Spacing value is negative");
		}
	}
	
	@Override
	public String help() {
		return Main.concatenate("  ", command, " <value> - minimal distance in pixels between images in the texture.");
	}
}
