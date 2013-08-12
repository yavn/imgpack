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

import yavnrh.app.imgpack.Parameters;
import yavnrh.app.imgpack.Main;

public class CommandOverwriteOutput extends Command {

	private Parameters params;
	
	public CommandOverwriteOutput(Parameters params) {
		super("overwrite");
		this.params = params;
	}

	@Override
	public void execute() {
		params.setOverwriteOutput(true);
	}
	
	@Override
	public String help() {
		return Main.concatenate("  ", command, " - overwrite output files if they exist.");
	}
}
