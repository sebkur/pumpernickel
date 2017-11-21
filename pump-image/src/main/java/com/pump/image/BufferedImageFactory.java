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

/**
 * This creates BufferedImage.
 */
public interface BufferedImageFactory {
	
	/**
	 * Create a BufferedImage.
	 * 
	 * @param width the width of the image.
	 * @param height the height of the image.
	 * @param type the type of the image. (One of the BufferedImage TYPE_ constants.)
	 * 
	 * @return a new BufferedImage.
	 */
	public BufferedImage create(int width,int height,int type);

	public BufferedImage create(int width, int height, int typeByteIndexed,
			IndexColorModel indexModel);
}