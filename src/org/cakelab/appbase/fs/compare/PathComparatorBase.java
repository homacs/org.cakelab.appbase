package org.cakelab.appbase.fs.compare;

import java.util.ArrayList;
import java.util.Comparator;

public abstract class PathComparatorBase implements Comparator<String> {

	@SuppressWarnings("serial")
	static class Mappings extends ArrayList<Range>{
		int nextMapStart = 0;
		
		public void addRange(Range r) {
			r.shift = r.start - nextMapStart;
			add(r);
			nextMapStart += r.end - r.start;
		}
		
		
		public char map(char c) {
			for (Range r : this) {
				if (c >= r.start && c < r.end) {
					return (char)(c + r.shift);
				}
			}
			return c;
		}
		

	}

	static class Range {

		int start;
		int end;
		private int shift;

		public Range(int start, int end, int shift) {
			this.start = start;
			this.end = end;
			this.shift = shift;
		}

		public Range(int start, int end) {
			this.start = start;
			this.end = end;
		}

	}

	static Mappings mappings;

	static {
		mappings = new Mappings();
		
		// Numbers
		Range numbers = new Range(48, 58);
		mappings.addRange(numbers);

		// lower case
		Range lowercase = new Range(97, 123);
		mappings.addRange(lowercase);
		
		// upper case
		Range uppercase = new Range(65, 91);
		mappings.addRange(uppercase);
		
		// control codes -> up-to numbers
		Range control = new Range(0, numbers.start);
		mappings.addRange(control);
		
		// numbers to upper case
		Range numbersToUpper = new Range(numbers.end, uppercase.start);
		mappings.addRange(numbersToUpper);
		
		// upper case to lower case
		Range upperToLower = new Range(uppercase.end, lowercase.start);
		mappings.addRange(upperToLower);
		
		// remaining characters will be mapped onto itself
	}
	
	char valueOf(String s1, int i) {
		return mappings.map(s1.charAt(i));
	}
}
