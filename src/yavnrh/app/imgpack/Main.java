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

public class Main {

	public static void main(String[] args) {
		CommandProcessor cp = new CommandProcessor(args);
		Parameters params = cp.processArguments();
		
		Packer packer = new Packer(params);
		packer.start();
	}

	public static void log(Object... objs) {
		System.out.println(concatenate(objs));
	}
	
	public static String concatenate(Object... objs) {
		StringBuilder sb = new StringBuilder();
		for (Object o : objs) {
			sb.append(o.toString());
		}
		return sb.toString();
	}	
}
