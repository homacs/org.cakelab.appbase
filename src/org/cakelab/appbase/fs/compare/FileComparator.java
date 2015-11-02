package org.cakelab.appbase.fs.compare;

import java.io.File;
import java.util.Comparator;

public class FileComparator implements Comparator<File>{

	private Comparator<String> pathComparator;
	
	
	public static FileComparator CASE_SENSITIVE_ASCENDING = new FileComparator(new PathComparatorCaseSensitive(true));
	public static FileComparator CASE_SENSITIVE_DECENDING = new FileComparator(new PathComparatorCaseSensitive(false));
	
	public static FileComparator CASE_INSENSITIVE_ASCENDING = new FileComparator(new PathComparatorCaseInsensitive(true));
	public static FileComparator CASE_INSENSITIVE_DECENDING = new FileComparator(new PathComparatorCaseInsensitive(false));
	
	
	public FileComparator(Comparator<String> pathComparator) {
		this.pathComparator = pathComparator;
	}
	
	public int compare(File f1, File f2) {
		int diff = attributes(f2) - attributes(f1);
		return diff == 0 ? pathComparator.compare(f1.getPath(), f2.getPath()) : diff;
	}

	private int attributes(File file) {
		int attr = 0;
		attr |= (file.isHidden()) ? 0 : 0x10; // non hidden files first
		attr |= (file.isDirectory()) ? 0x01 : 0; // and directories first
		return attr;
	}

}
