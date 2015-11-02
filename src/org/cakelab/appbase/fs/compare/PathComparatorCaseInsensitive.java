package org.cakelab.appbase.fs.compare;


public class PathComparatorCaseInsensitive extends PathComparatorBase {

	
	private int sign;

	
	public PathComparatorCaseInsensitive(boolean ascending) {
		if (ascending) sign = 1;
		else sign = -1;
	}
	
    public int compare(String s1, String s2) {
        int n1 = s1.length();
        int n2 = s2.length();
        int min = Math.min(n1, n2);
        for (int i = 0; i < min; i++) {
            char c1 = s1.charAt(i);
            char c2 = s2.charAt(i);
            if (c1 != c2) {
                int m1 = mappings.map(Character.toUpperCase(c1));
                int m2 = mappings.map(Character.toUpperCase(c2));
                if (m1 != m2) {
                	m1 = mappings.map(Character.toLowerCase(c1));
                	m2 = mappings.map(Character.toLowerCase(c2));
                    if (m1 != m2) {
                        // No overflow because of numeric promotion
                        return sign * (m1 - m2);
                    }
                }
            }
        }
        return sign * (n1 - n2);
    }
}
