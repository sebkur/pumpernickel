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
package com.pump.animation.capture;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

/** This implementation of {@link ScreenCapture} relies on the
 * <code>java.awt.Component.paint(Graphic)</code> method.
 * <p>This is safer than the <code>RobotScreenCapture</code> (which involves
 * higher security levels), but as a result: this can't capture as much.
 * For example: if tooltips or palettes or menus float above a component,
 * then they probably won't be captured by this recording. Also this will
 * continue recording the component in question even if the user switches
 * windows/applications, but the Robot would capture exactly what the user
 * sees on their display.
 */
public class ComponentPaintCapture extends ScreenCapture {
	
	Component comp;
	
	/**
	 * 
	 * @param c the component to invoke <code>paint(Graphics)</code>.
	 * This should probably be a <code>java.awt.Frame</code>, or some other
	 * important container.
	 */
	public ComponentPaintCapture(Component c) {
		super(getBounds(c));
		comp = c;
	}
	
	/** Return the bounds (in screen coordinates) of a component. */
	private static Rectangle getBounds(Component c) {
		Point topLeft = new Point(0,0);
		SwingUtilities.convertPointToScreen(topLeft, c);
		return new Rectangle(topLeft.x, topLeft.y, c.getWidth(), c.getHeight());
	}
	
	BufferedImage bi;
	
	@Override
	protected synchronized File capture(final Rectangle r) throws Exception {
		Runnable runnable = new Runnable() {
			public void run() {
				bi = new BufferedImage(r.width, r.height, BufferedImage.TYPE_INT_RGB);
				Point topLeft = new Point(r.x, r.y);
				Point bottomRight = new Point(r.x + r.width, r.y + r.height);
				SwingUtilities.convertPointFromScreen(topLeft, comp);
				SwingUtilities.convertPointFromScreen(bottomRight, comp);
				Rectangle compRect = new Rectangle(topLeft.x, topLeft.y, bottomRight.x - topLeft.x, bottomRight.y - topLeft.y);
				
				Graphics2D g = bi.createGraphics();
				g.setColor(Color.black);
				g.translate(-compRect.x, -compRect.y);
				comp.paint(g);
				g.dispose();
			}
		};
		if(SwingUtilities.isEventDispatchThread()) {
			runnable.run();
		} else {
			SwingUtilities.invokeAndWait(runnable);
		}
		File file = File.createTempFile("componentCapture", ".png", tempDir);
		file.deleteOnExit();
		file.createNewFile();
		ImageIO.write(bi, "png", file);
		return file;
	}

}