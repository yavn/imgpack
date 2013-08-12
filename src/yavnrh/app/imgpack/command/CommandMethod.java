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
import yavnrh.app.imgpack.Parameters.PackingMethod;
import yavnrh.app.imgpack.Main;
import yavnrh.app.imgpack.exception.InvalidPackingMethodException;
import yavnrh.app.imgpack.exception.MissingArgumentException;

public class CommandMethod extends Command {

	private Parameters params;
	private CommandProcessor cp;
	
	public CommandMethod(CommandProcessor cp, Parameters params) {
		super("method");
		this.cp = cp;
		this.params = params;
	}

	@Override
	public void execute() {
		String methodString = cp.nextArg();
		
		validateArgument(methodString);
		
		params.setMethod(methodFromString(methodString));
	}

	private PackingMethod methodFromString(String methodString) {
		if (methodString.equals("bintree")) {
			return PackingMethod.BINARY_TREE;
		} else if (methodString.equals("grid")) {
			return PackingMethod.GRID;
		} else {
			throw new InvalidPackingMethodException(methodString); 
		}
	}

	private void validateArgument(String arg) {
		if (arg == null) {
			throw new MissingArgumentException("No packing method specified");
		}
	}
	
	@Override
	public String help() {
		return Main.concatenate("  ", command, " <name> - name of the packing method. Possible values are:\n",
				"    bintree (default) - binary tree packing which minimizes wasted space,\n",
				"    grid - preserve images order and try to pack them in a grid.");
	}
}
