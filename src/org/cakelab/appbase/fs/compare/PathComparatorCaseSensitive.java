package org.cakelab.appbase.fs.compare;


public class PathComparatorCaseSensitive extends PathComparatorBase {

	
	private int sign;

	
	public PathComparatorCaseSensitive(boolean ascending) {
		if (ascending) sign = 1;
		else sign = -1;
		
	}
	
	public int compare(String s1, String s2) {
		
		int n1 = s1.length();
		int n2 = s2.length();
		int min = Math.min(n1, n2);
		for (int i = 0; i < min; i++) {
			char c1 = valueOf(s1, i);
			char c2 = valueOf(s2, i);
			if (c1 != c2) {
				return sign * (c1 - c2);
			}
		}
		return sign * (n1 - n2);
	}
}
