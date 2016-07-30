package eeggui;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class EEGDataFilter extends FileFilter {

	public boolean accept(File f) {
		if (f.isDirectory()) return true;
		
		String name = f.getName();
		
		String extension = Utils.getFileExtension(name);
//		if (extension == null) return false;
		if (extension.equals("exp")) return true;
		return false;
	}

	public String getDescription() {
		return "EEG Exported Data Files (*.exp)";
	}
}
