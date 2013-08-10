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
import yavnrh.app.imgpack.Main;
import yavnrh.app.imgpack.exception.InvalidCommandException;

public class CommandHelp extends Command {
	private CommandProcessor cp;
	
	public CommandHelp(CommandProcessor cp) {
		super("help");
		this.cp = cp;
	}

	@Override
	public void execute() {
		String arg = cp.nextArg();

		if (arg != null) {
			try {
				Command cmd = cp.decodeCommand(arg);
				Main.log(cmd.help());
			} catch (InvalidCommandException ex) {
				Main.log("Help: unrecognized command ", arg);
			}
		} else {
			Main.log("imgpack - Dumb image packing tool, version 0.1");
			Main.log("Copyright 2013 Maciej Jesionowski");
			Main.log("This program is free software. See GNU GPL license for details.");
			
			for (Command cmd : cp.getCommands()) {
				Main.log(cmd.help());
			}
		}
		
		cp.stop();
	}
	
	@Override
	public String help() {
		return "  " + command + " <command> - print help for a command.";
	}
	
}
