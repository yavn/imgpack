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

package yavnrh.app.imgpack;

import java.util.ArrayList;
import java.util.LinkedList;

import yavnrh.app.imgpack.command.Command;
import yavnrh.app.imgpack.command.CommandAddImage;
import yavnrh.app.imgpack.command.CommandHelp;
import yavnrh.app.imgpack.command.CommandOutputName;
import yavnrh.app.imgpack.command.CommandOutputSize;

public class CommandProcessor {
	private ImagePacker imagePacker;
	private ArrayList<Command> commands;
	private LinkedList<String> args;
	
	private boolean interruptProcessing = false;
	
	public CommandProcessor(String[] argsArray) {
		args = new LinkedList<String>();		
		for (String arg : argsArray) {
			args.add(arg);
		}
		
		imagePacker = new ImagePacker();
		commands = new ArrayList<Command>();

		addCommands();
	}

	private void addCommands() {
		commands.add(new CommandHelp(this));
		commands.add(new CommandOutputName());
		commands.add(new CommandOutputSize());
		commands.add(new CommandAddImage());
		// spacing
		// border
		// crop
		// method
	}
	
	public void process() {
		if (args.size() > 0) {
			processArguments();
		} else {
			displayHelp();
		}
	}

	private void displayHelp() {
		Command cmd = new CommandHelp(this);
		cmd.execute();
	}

	private void processArguments() {
		String arg;

		while ((arg = nextArg()) != null && !interruptProcessing) {
			if (isCommandArgument(arg)) {
				Command cmd = decodeCommand(stripLeadingDash(arg));
				cmd.execute();				
			} else {
				Main.log("Ignored argument: ", arg);
			}
		}
	}

	public String nextArg() {
		return args.poll();
	}
	
	private boolean isCommandArgument(String arg) {
		return arg.startsWith("-");
	}
	
	private String stripLeadingDash(String string) {
		return string.substring(1);
	}

	public Command decodeCommand(String commandString) {
		for (Command cmd : commands) {
			if (cmd.matches(commandString)) {
				return cmd;
			}
		}
		throw new RuntimeException("Invalid command " + commandString);
	}

	public Command[] getCommands() {
		Command[] result = new Command[commands.size()];
		return commands.toArray(result);
	}
	
	public void stop() {
		interruptProcessing = true;
	}
}
