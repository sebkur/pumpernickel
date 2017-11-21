/**
 * This software is released as part of the Pumpernickel project.
 * 
 * All com.pump resources in the Pumpernickel project are distributed under the
 * MIT License:
 * https://raw.githubusercontent.com/mickleness/pumpernickel/master/License.txt
 * 
 * More information about the Pumpernickel project is available here:
 * https://mickleness.github.io/pumpernickel/
 */
package com.pump.image;

import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;

public class BasicBufferedImageFactory implements BufferedImageFactory {

	@Override
	public BufferedImage create(int width, int height, int type) {
		return new BufferedImage(width, height, type);
	}

	@Override
	public BufferedImage create(int width, int height, int typeByteIndexed,
			IndexColorModel indexModel) {
		return new BufferedImage(width, height, typeByteIndexed, indexModel);
	}

}