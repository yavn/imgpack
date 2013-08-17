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

package yavnrh.app.imgpack.packing.image;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import yavnrh.app.imgpack.exception.ImageIOException;

public class FileImage extends Image {
	
	public FileImage(String name) {
		super(name);
		this.image = createBufferedImageFromFile(name);
	}

	private BufferedImage createBufferedImageFromFile(String name) {
		try {
			BufferedInputStream input = new BufferedInputStream(new FileInputStream(name));			
			BufferedImage image = ImageIO.read(input);
			
			input.close();
			
			if (image != null) {
				return image;
			} else {
				throw new ImageIOException("Can not read image " + name);
			}			
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

}
