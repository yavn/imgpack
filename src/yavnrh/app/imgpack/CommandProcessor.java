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

public class CommandProcessor {

	private static final String VERSION = "0.1";
	
	private ImagePacker imagePacker;
	private ArrayList<Command> commands;
	private LinkedList<String> args;
	
	private boolean interruptProcessing;
	
	public CommandProcessor(String[] argsArray) {
		commands = new ArrayList<Command>();
		commands.add(CMD_HELP);
		commands.add(CMD_OUTPUT_NAME);
		commands.add(CMD_OUTPUT_SIZE);
		commands.add(CMD_ADD_IMAGE);

		args = new LinkedList<String>();		
		for (String arg : argsArray) {
			args.add(arg);
		}
		
		imagePacker = new ImagePacker();		
	}
	
	private String nextArg() {
		return args.poll();
	}
	
	public void process() {
		String arg;
		interruptProcessing = false;

		if (args.size() == 0) {
			CMD_HELP.execute();
		}
		
		while ((arg = nextArg()) != null && !interruptProcessing) {
			if (isCommandArgument(arg)) {
				Command cmd = decodeCommand(stripLeadingDash(arg));
				cmd.execute();				
			} else {
				log("Ignored argument: ", arg);
			}
		}
	}
	
	private boolean isCommandArgument(String arg) {
		return arg.startsWith("-");
	}
	
	private String stripLeadingDash(String string) {
		return string.substring(1);
	}

	private Command decodeCommand(String commandString) {
		for (Command cmd : commands) {
			if (cmd.matches(commandString)) {
				return cmd;
			}
		}
		throw new RuntimeException("Invalid command " + commandString);
	}

	private void log(Object... objs) {
		System.out.println(concatenate(objs));
	}
	
	private String concatenate(Object... objs) {
		StringBuilder sb = new StringBuilder();
		for (Object o : objs) {
			sb.append(o.toString());
		}
		return sb.toString();
	}
	
	private final Command CMD_ADD_IMAGE = new Command("add") {
		@Override
		public void execute() {
			throw new RuntimeException("Command not yet implemented.");
		}
		
		@Override
		public String help() {
			return concatenate("  ", command, " <file> - add an image to the atlas.");
		}
	};
	
	private final Command CMD_OUTPUT_SIZE = new Command("size") {
		@Override
		public void execute() {
			throw new RuntimeException("Command not yet implemented.");
		}
		
		@Override
		public String help() {
			return concatenate("  ", command, " <width> <height> - size of the output texture image (in pixels).");
		}
	};

	private final Command CMD_OUTPUT_NAME = new Command("name") {
		@Override
		public void execute() {
			throw new RuntimeException("Command not yet implemented.");
		}
		
		@Override
		public String help() {
			return concatenate("  ", command, " <name> - name of the output texture atlas files.");
		}
	};

	private final Command CMD_IMAGE_SPACING = new Command("spacing") {
		@Override
		public void execute() {
			throw new RuntimeException("Command not yet implemented.");
		}
	};

	private final Command CMD_EDGE_BORDER = new Command("border") {
		@Override
		public void execute() {
			throw new RuntimeException("Command not yet implemented.");
		}
	};
	
	private final Command CMD_CROP = new Command("crop") {
		@Override
		public void execute() {
			throw new RuntimeException("Command not yet implemented.");
		}
	};
	
	private final Command CMD_METHOD = new Command("method") {
		@Override
		public void execute() {
			throw new RuntimeException("Command not yet implemented.");
		}
	};
	
	private final Command CMD_HELP = new Command("help") {
		@Override
		public void execute() {
			String arg = nextArg();

			if (arg != null) {
				try {
					Command cmd = decodeCommand(arg);
					log(cmd.help());
				} catch (Exception ex) {
					log("Help: unrecognized command ", arg);
				}
			} else {
				log("imgpack - Dumb image packing tool, version " + VERSION);
				log("Copyright 2013 Maciej Jesionowski");
				log("This program is free software. See GNU GPL license for details.");
				
				for (Command cmd : commands) {
					log(cmd.help());
				}
			}
			
			CommandProcessor.this.interruptProcessing = true;
		}
		
		@Override
		public String help() {
			return "  " + command + " <command> - print help for a command.";
		}
	};

}
