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

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;

import com.pump.io.SuffixFilenameFilter;

/**
 * A collection of static methods related to file dialogs.
 */
public class FileDialogUtils {

	/**
	 * Show a "Open File" file dialog.
	 * 
	 * @param f the frame that contains this dialog.
	 * @param title the dialog title (required). This may be null
	 * @param extensions the optional file extensions that are supported.
	 * @return the File the user asked to open, or null if the user cancelled.
	 */
	public static File showOpenDialog(Frame f,String title,String... extensions) {
		FileDialog fd = new FileDialog(f, title);
		fd.setMode(FileDialog.LOAD);
		if(extensions!=null && extensions.length>0)
			fd.setFilenameFilter(new SuffixFilenameFilter(extensions));
		fd.pack();
		fd.setLocationRelativeTo(null);
		fd.setVisible(true);
		if(fd.getFile()==null)
			return null;
		return new File(fd.getDirectory() + fd.getFile());
	}

	/**
	 * Show a "Save As" file dialog.
	 * 
	 * @param f the frame that contains this dialog.
	 * @param title the dialog title (required)
	 * @param extension the file extension (required)
	 * @return the File the user asked to save to, or null if the user cancelled.
	 */
	public static File showSaveDialog(Frame f,String title,String extension) {
		return showSaveDialog(f, title, null, extension);
	}

	/**
	 * Show a "Save As" file dialog.
	 * 
	 * @param f the frame that contains this dialog.
	 * @param title the dialog title (required)
	 * @param fileName an optional starting file name (may be null)
	 * @param extension the file extension (required)
	 * @return the File the user asked to save to, or null if the user cancelled.
	 */
	public static File showSaveDialog(Frame f,String title,String fileName,String extension) {
		if(extension.startsWith("."))
			extension = extension.substring(1);
		
		FileDialog fd = new FileDialog(f, title);
		fd.setMode(FileDialog.SAVE);
		fd.setFilenameFilter(new SuffixFilenameFilter(extension));
		if(fileName!=null) {
			fd.setFile(fileName+'.'+extension);
		} else {
			fd.setFile("Untitled."+extension);
		}
		fd.pack();
		fd.setLocationRelativeTo(null);
		fd.setVisible(true);

		String s = fd.getFile();
		if(s==null)
			return null;
		
		if(s.toLowerCase().endsWith("."+extension)) {
			return new File(fd.getDirectory() + s);
		}
		
		return new File(fd.getDirectory() + fd.getFile()+"."+extension);
	}
}