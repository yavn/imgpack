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
import yavnrh.app.imgpack.command.CommandBorder;
import yavnrh.app.imgpack.command.CommandCrop;
import yavnrh.app.imgpack.command.CommandHelp;
import yavnrh.app.imgpack.command.CommandMethod;
import yavnrh.app.imgpack.command.CommandOutputName;
import yavnrh.app.imgpack.command.CommandOutputSize;
import yavnrh.app.imgpack.command.CommandOverwriteOutput;
import yavnrh.app.imgpack.command.CommandSpacing;
import yavnrh.app.imgpack.exception.InvalidCommandException;

public class CommandProcessor {
	private Parameters params;
	private ArrayList<Command> commands;
	private LinkedList<String> args;
	
	private boolean interruptProcessing = false;
	
	public CommandProcessor(String[] argsArray) {
		args = new LinkedList<String>();		
		for (String arg : argsArray) {
			args.add(arg);
		}
		
		params = new Parameters();
		commands = new ArrayList<Command>();

		addCommands();
	}

	private void addCommands() {
		commands.add(new CommandHelp(this));
		commands.add(new CommandOutputName(this, params));
		commands.add(new CommandOutputSize(this, params));
		commands.add(new CommandAddImage(this, params));
		commands.add(new CommandSpacing(this, params));
		commands.add(new CommandBorder(this, params));
		commands.add(new CommandCrop(params));
		commands.add(new CommandMethod(this, params));
		commands.add(new CommandOverwriteOutput(params));
	}
	
	public void start() {
		if (args.size() > 0) {
			processAllArguments();
		} else {
			displayHelp();
		}
	}

	private void displayHelp() {
		Command cmd = new CommandHelp(this);
		cmd.execute();
	}

	private void processAllArguments() {
		String arg;

		while ((arg = nextArg()) != null && !interruptProcessing) {
			processArgument(arg);
		}
	}

	private void processArgument(String arg) {
		if (isCommandArgument(arg)) {
			Command cmd = decodeCommand(stripLeadingDash(arg));
			cmd.execute();				
		} else {
			Main.log("Ignored argument: ", arg);
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
		throw new InvalidCommandException("Invalid command " + commandString);
	}

	public void stop() {
		interruptProcessing = true;
	}

	public Command[] getCommands() {
		Command[] result = new Command[commands.size()];
		return commands.toArray(result);
	}
	
	public Parameters getParameters() {
		return params;
	}
}
