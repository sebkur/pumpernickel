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
package com.pump.swing;

import java.awt.Color;

import javax.swing.UIManager;

import com.pump.blog.Blurb;
import com.pump.blog.ResourceSample;
import com.pump.plaf.ColorWellUI;

/** This is a rectangular panel used to render one color.
 * <P>When the user interacts with this component (either by
 * the mouse or keyboard) he/she should usually be able to
 * choose another color.
 * 
 * 
 * <!-- ======== START OF AUTOGENERATED SAMPLES ======== -->
 * <p><img src="https://raw.githubusercontent.com/mickleness/pumpernickel/master/pump-release/resources/samples/ColorWell/sample.png" alt="new&#160;com.pump.swing.ColorWell(&#160;java.awt.Color.blue&#160;)">
 * <!-- ======== END OF AUTOGENERATED SAMPLES ======== -->
 */
@Blurb (
imageName = "Colors.png",
title = "Colors: a Good GUI for Selecting Colors",
releaseDate = "January 2010",
summary = "This jar includes <i>all</i> my color-related GUI components: "+
"the <code><a href=\"https://javagraphics.java.net/doc/com/bric/swing/ColorPicker.html\">ColorPicker</a></code>, "+
"the <code><a href=\"https://javagraphics.java.net/doc/com/bric/swing/ColorWell.html\">ColorWell</a></code>, and "+
"the <code><a href=\"https://javagraphics.java.net/doc/com/bric/swing/ColorPalette.html\">ColorPalette</a></code>.\n"+
"<p>I'm not going to argue that this will meet your needs and save the world; but it includes a lot of useful tools "+
"and it might help (save the world).",
article = "http://javagraphics.blogspot.com/2010/01/colors-good-gui-for-selecting-colors.html"
)
@ResourceSample( sample = {"new com.pump.swing.ColorWell( java.awt.Color.blue )"} )
public class ColorWell extends ColorComponent {
	private static final long serialVersionUID = 1L;
    private static final String uiClassID = "ColorWellUI";
  
    public static final String KEY_OPAQUE_COLORS = ColorWell.class.getName()+"#opaqueColor";

	public ColorWell(boolean isOpaque) {
        updateUI();
        setOpaqueColors(isOpaque);
        setRequestFocusEnabled(true);
        setFocusable(true);
	}
	
	public boolean isOpaqueColors() {
		Boolean b = (Boolean)getClientProperty(KEY_OPAQUE_COLORS);
		if(b==null)
			return false;
		return b.booleanValue();
	}
	
	public void setOpaqueColors(boolean isOpaque) {
		putClientProperty(KEY_OPAQUE_COLORS, isOpaque);
	}

	public ColorWell(boolean isOpaque,Color initialColor) {
		this(isOpaque);
		setColor(initialColor);
	}
	
    @Override
	public String getUIClassID() {
        return uiClassID;
    }
	
    @Override
	public void updateUI() {
    	if(UIManager.getDefaults().get(uiClassID)==null) {
    		UIManager.getDefaults().put(uiClassID, "com.pump.plaf.AquaColorWellUI");
    	}
    	setUI((ColorWellUI)UIManager.getUI(this));
    }
	
	public void setUI(ColorWellUI ui) {
        super.setUI(ui);
	}
	
	public ColorWellUI getUI() {
		return (ColorWellUI)ui;
	}
}
