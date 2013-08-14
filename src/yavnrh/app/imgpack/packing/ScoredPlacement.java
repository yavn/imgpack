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

package yavnrh.app.imgpack.packing;

public class ScoredPlacement implements Comparable<ScoredPlacement> {
	
	public final Image image;
	public final Rectangle rectangle;
	public final int score;

	public ScoredPlacement(Rectangle rect, Image image, int score) {
		this.image = image;
		this.rectangle = rect;
		this.score = score;
	}

	@Override
	public int compareTo(ScoredPlacement other) {
		if (score < other.score) {
			return -1;
		} else if (score > other.score){
			return 1;
		} else {
			return 0;
		}
	}
}
