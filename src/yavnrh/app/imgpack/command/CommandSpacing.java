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
import yavnrh.app.imgpack.ImagePacker;
import yavnrh.app.imgpack.Main;
import yavnrh.app.imgpack.exception.MissingArgumentException;

public class CommandSpacing extends Command {

	private ImagePacker ip;
	private CommandProcessor cp;
	
	public CommandSpacing(CommandProcessor cp, ImagePacker ip) {
		super("spacing");
		this.cp = cp;
		this.ip = ip;
	}

	@Override
	public void execute() {
		String spacingString = cp.nextArg();
		
		validateArgument(spacingString);
		
		ip.setImageSpacing(Integer.parseInt(spacingString));
	}

	private void validateArgument(String arg) {
		if (arg == null) {
			throw new MissingArgumentException("No spacing value specified");
		}
	}
	
	@Override
	public String help() {
		return Main.concatenate("  ", command, " <value> - minimal distance in pixels between images in the texture.");
	}
}
