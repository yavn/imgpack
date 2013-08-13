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

public interface ScoringFunction {	
	
	int score(Rectangle rect, Image image);

	ScoringFunction BEST_SHORT_SIDE_FIT = new ScoringFunction() {
		@Override
		public int score(Rectangle rect, Image image) {
			final int dx = rect.width - image.getWidth();
			final int dy = rect.height - image.getHeight();
			final int score = Math.min(dx, dy);
			
			if (score < 0) {
				// Not enough room to fit
				return Integer.MAX_VALUE;
			} else {
				return score;
			}
		}
	};
}
