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

public class Packer {

	private Parameters params;
	
	public Packer(Parameters params) {
		this.params = params;
	}
	
	public void start() {
		if (hasWorkToDo()) {
			packImages();
		} else {
			Main.log("No images specified.");
		}
	}

	private boolean hasWorkToDo() {
		return (params.getImages().length > 0);
	}

	
	private void packImages() {
		// TODO Auto-generated method stub
		
	}

}
