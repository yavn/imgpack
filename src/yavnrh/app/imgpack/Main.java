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

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import yavnrh.app.imgpack.exception.ImagePackingException;
import yavnrh.app.imgpack.packing.GridImagePacker;
import yavnrh.app.imgpack.packing.ImagePacker;
import yavnrh.app.imgpack.packing.MaxRectsImagePacker;

public class Main {

	public static void main(String[] args) {
		CommandProcessor cp = new CommandProcessor(args);
		Parameters params = cp.processArguments();
		
		if (params.isReadyToPack()) {
			ImagePacker ip = getImagePacker(params);

			ip.addImages();
			ip.pack();
			
			writeOutputImage(ip.getOutputImage(), params);
		}
	}
	
	private static ImagePacker getImagePacker(Parameters params) {
		switch (params.getMethod()) {
		case GRID:
			return new GridImagePacker(params);
			
		case MAX_RECTS:
			return new MaxRectsImagePacker(params);
		}
		
		throw new ImagePackingException(params.getMethod().name() + " packing method not implemented.");	
	}

	public static void writeOutputImage(BufferedImage outputImage, Parameters params) {
		try {
			File file = new File(params.getOutputName() + ".png");
			
			if (file.exists() && !params.getOverwriteOutput()) {
				throw new RuntimeException("File " + file.getName() + " exists. Will not overwrite.");
			}
			
			BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file));
			ImageIO.write(outputImage, "png", output);
			output.close();
			
		} catch (FileNotFoundException ex) {
			throw new RuntimeException(ex);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
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
