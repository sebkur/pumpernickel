/*
 * @(#)ColorBandDemo.java
 *
 * $Date: 2015-02-28 15:59:45 -0500 (Sat, 28 Feb 2015) $
 *
 * Copyright (c) 2014 by Jeremy Wood.
 * All rights reserved.
 *
 * The copyright of this software is owned by Jeremy Wood. 
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * Jeremy Wood. For details see accompanying license terms.
 * 
 * This software is probably, but not necessarily, discussed here:
 * https://javagraphics.java.net/
 * 
 * That site should also contain the most recent official version
 * of this software.  (See the SVN repository for more details.)
 */
package com.pump.showcase;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.pump.awt.GradientTexturePaint;
import com.pump.blog.Blurb;
import com.pump.inspector.InspectorGridBagLayout;
import com.pump.swing.ColorWell;
import com.pump.swing.MagnificationPanel;

/** This small demo app features two horizontal gradients, and each shows
 * below it a zoomed-in image highlighting where pixels change.
 */
@Blurb (
filename = "ColorBandDemo",
title = "Gradients: Avoiding Color Banding",
releaseDate = "March 2014",
summary = "This article explores the problem of color banding in linear gradients and presents a partial solution.",
instructions = "This applet shows two horizontal gradients.\n"+
"<p>The first uses Java's default gradient implementation, and the second uses the revised "+
"<code>GradientTexturePaint</code>. Below each is a panel that amplifies where each pixel "+
"change occurs (toggling between black and gray).\n"+
"<p>Drag the scrubber (or play the animation) to observe color banding in progress.\n"+
"<p>This is meant to demonstrate that the second panel shows less severe color banding than Java's "+
"default implementation.",
link = "http://javagraphics.blogspot.com/2014/03/gradients-avoiding-color-banding.html",
sandboxDemo = true
)
public class ColorBandDemo extends JPanel {
	private static final long serialVersionUID = 1L;

	class GradientPanel extends JPanel {

		private static final long serialVersionUID = 1L;
		
		protected GradientPanel() {
			setPreferredSize(new Dimension(300, 400));
		}
		
		protected void paintComponent(Graphics g0) {
			Graphics2D g = (Graphics2D)g0;
			
			Point2D p1 = new Point2D.Double(0,0);
			Point2D p2 = new Point2D.Double(0,getHeight());
			
			Paint p = typeComboBox.getSelectedItem()==GradientType.GRADIENT_TEXTURE_PAINT ? new GradientTexturePaint(p1, well1.getColor(), p2, well2.getColor()) :
				new GradientPaint(p1, well1.getColor(), p2, well2.getColor());
			g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
			g.setPaint(p);
			g.fillRect(0,0,getWidth(),getHeight());
			
			int x = getWidth()/2;
			g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
			g.fillRect(0,0,x,getHeight());
		}
	}
	
	ColorWell well1 = new ColorWell(true, new Color(0x6f00df));
	ColorWell well2 = new ColorWell(true, new Color(0x8f1fff));
	JPanel controls = new JPanel();
	
	
	GradientPanel panel = new GradientPanel();
	MagnificationPanel zoomPanel = new MagnificationPanel(panel, 16, 8, 16);
	
	enum GradientType {
		GRADIENT_TEXTURE_PAINT, TEXTURE_PAINT
	}
	
	
	JFrame frame;
	JComboBox<GradientType> typeComboBox = new JComboBox<>();
	
	public ColorBandDemo(JFrame frame) {
		setLayout(new GridBagLayout());
		this.frame = frame;
		for(GradientType t : GradientType.values()) {
			typeComboBox.addItem(t);	
		}
		
		InspectorGridBagLayout layout = new InspectorGridBagLayout(controls);
		layout.addRow(new JLabel("Color 1:"), well1, false, null);
		layout.addRow(new JLabel("Color 2:"), well2, false, null);
		layout.addRow(new JLabel("Gradient Type:"), typeComboBox, false, null);
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0; 
		c.weightx = 1; c.weighty = 0; c.fill = GridBagConstraints.BOTH;
		add(controls, c);
		
		zoomPanel.setMinimumSize(zoomPanel.getPreferredSize());
		
		c.gridx++; c.weightx = 0;
		add(zoomPanel, c);
		
		JPanel graphicPanel = new JPanel(new GridBagLayout());
		c.gridx = 0; c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1; c.weightx = 1;
		c.gridy++;
		add(graphicPanel, c);
		
		c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0;
		c.weightx = 1; c.weighty = 1; c.gridwidth = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.BOTH;
		graphicPanel.add(panel, c);
		c.gridy++; c.gridwidth = 1;
		c.fill = GridBagConstraints.NONE;
		graphicPanel.add(new JLabel("Quality Hints"), c);
		c.gridx++;
		graphicPanel.add(new JLabel("Speed Hints"), c);
		
		ChangeListener repaintListener = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				panel.repaint();
			}
		};
		
		typeComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.repaint();
			}
		});
		
		well1.addChangeListener(repaintListener);
		well2.addChangeListener(repaintListener);
	}
}