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

import javax.swing.JComponent;
import javax.swing.UIManager;

import com.pump.blog.Blurb;
import com.pump.plaf.ThrobberUI;

/** This is a simple animated component used to indicate activity when a
 * <code>JProgressBar</code> is either not useful (because a task is indeterminate)
 * or too large (because a task must be expressed in a tiny amount of space). Here
 * are some possible UI implementations:
 * <p><table summary="Sample ThrobberUIs" cellpadding="10"><tr>
 * <td><img src="https://raw.githubusercontent.com/mickleness/pumpernickel/master/pump-release/resources/throbber/AquaThrobberUIx2.gif" alt="Sample of AquaThrobberUI"></td>
 * <td><img src="https://raw.githubusercontent.com/mickleness/pumpernickel/master/pump-release/resources/throbber/ChasingArrowsThrobberUIx2.gif" alt="Sample of ChasingArrowsThrobberUI"></td>
 * <td><img src="https://raw.githubusercontent.com/mickleness/pumpernickel/master/pump-release/resources/throbber/PulsingCirclesThrobberUIx2.gif" alt="Sample of PulsingCirclesThrobberUI"></td>
 * </tr></table>
 * <p><a href="https://developer.apple.com/library/mac/documentation/UserExperience/Conceptual/AppleHIGuidelines/Controls/Controls.html#//apple_ref/doc/uid/TP30000359-TPXREF106">Apple's OSX Human Interface Guidelines</a> call this an "Asynchronous Progress Indicator",
 * and describe it as follows:
 * <blockquote>An asynchronous progress indicator provides feedback on an ongoing process.
 * <p>Asynchronous progress indicators are available in Interface Builder. In the Attributes pane of the inspector, select Spinning for the style and be sure the Indeterminate checkbox is selected. To create an asynchronous progress indicator using AppKit programming interfaces, use the NSProgressIndicator class with style NSProgressIndicatorSpinningStyle.
 * <p>
 * <b>Appearance and Behavior</b>
 * <p>The appearance of the asynchronous progress indicator is provided automatically. The 
 * asynchronous progress indicator always spins at the same rate.
 * <p><b>Guidelines</b>
 * <p>Use an asynchronous progress indicator when space is very constrained, such as in a text 
 * field or near a control. Because this indicator is small and unobtrusive, it is especially 
 * useful for asynchronous events that take place in the background, such as retrieving messages 
 * from a server.
 * <p>If the process might change from indeterminate to determinate, start with an indeterminate 
 * progress bar. You don't want to start with an asynchronous progress indicator because the 
 * determinate progress bar is a different shape and takes up much more space. Similarly, if the 
 * process might change from indeterminate to determinate, use an indeterminate progress bar instead 
 * of an asynchronous progress indicator, because it is the same shape and size as the determinate 
 * progress bar.
 * <p>In general, avoid supplying a label. Because an asynchronous progress indicator typically 
 * appears when the user initiates a process, a label is not usually necessary. If you decide to 
 * provide a label that appears with the indicator, create a complete or partial sentence that 
 * briefly describes the process that is occurring. You should use sentence-style capitalization 
 * (for more information on this style, see "Capitalizing Labels and Text") and you can end the 
 * label with an ellipsis (...) to emphasize the ongoing nature of the processing.</blockquote>
 * <p>Alternatively, <a href="http://en.wikipedia.org/wiki/Throbber">Wikipedia</a> describes a throbber as:
 * <p><blockquote>A throbber is a graphic found in a graphical user interface of a computer program 
 * that animates to show the user that the program is performing an action in the background 
 * (such as downloading content, conducting intensive calculations or communicating with an 
 * external device). In contrast to a progress bar, a throbber does not convey how much of the 
 * action has been completed.</blockquote>
 */
@Blurb (
imageName = "JThrobber.png",
title = "Implementing a JThrobber ",
releaseDate = "March 2014",
summary = "This introduces <a href=\"https://javagraphics.java.net/doc/com/bric/swing/JThrobber.html\">a new component</a> for "+
"showing asynchronous indeterminate feedback.\n"+
"<p>They're vector-based, so while they're originally designed to be less than 20x20 pixels: "+
"they scale well if you need them to. The article/demo app also include some fun non-standard "+
"alternative UIs (that were fun to design, but I don't necessarily recommend).",
article = "http://javagraphics.blogspot.com/2014/03/implementing-jthrobber.html"
)
public class JThrobber extends JComponent {
	private static final long serialVersionUID = 1L;

	private static final String uiClassID = "ThrobberUI";
	
	public static final String KEY_ACTIVE = JThrobber.class.getName()+"#active";
	
	public JThrobber() {
		updateUI();
	}
	
    @Override
	public String getUIClassID() {
        return uiClassID;
    }
	
    @Override
	public void updateUI() {
    	if(UIManager.getDefaults().get(uiClassID)==null) {
    		UIManager.getDefaults().put(uiClassID, "com.pump.plaf.ThrobberUI");
    	}
    	setUI((ThrobberUI)UIManager.getUI(this));
    }
	
	public void setUI(ThrobberUI ui) {
        super.setUI(ui);
	}
	
	public ThrobberUI getUI() {
		return (ThrobberUI)ui;
	}

	public boolean isActive() {
		Boolean b = (Boolean)getClientProperty(KEY_ACTIVE);
		if(b==null) return true;
		return b;
	}
	
	/** An inactive throbber doesn't paint itself. This is sometimes
	 * useful (instead of simply toggling the visibility of this throbber on/off)
	 * because it maintains the same size in the UI, so other elements won't
	 * constantly shift around but the widget can still effectively be hidden.
	 */
	public void setActive(boolean b) {
		putClientProperty(KEY_ACTIVE, new Boolean(b));
	}
	
}