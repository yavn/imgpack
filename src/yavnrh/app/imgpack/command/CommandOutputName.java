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

public class CommandOutputName extends Command {

	private ImagePacker ip;
	private CommandProcessor cp;
	
	public CommandOutputName(CommandProcessor cp, ImagePacker ip) {
		super("name");
		this.cp = cp;
		this.ip = ip;
	}

	@Override
	public void execute() {
		String outputName = cp.nextArg();
		
		validateArgument(outputName);
		
		ip.setOutputImageName(outputName);
	}

	private void validateArgument(String outputName) {
		if (outputName == null) {
			throw new MissingArgumentException("No output name");
		}
	}
	
	@Override
	public String help() {
		return Main.concatenate("  ", command, " <name> - name of the output texture atlas files.");
	}
}
