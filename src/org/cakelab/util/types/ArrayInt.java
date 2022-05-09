package org.cakelab.util.types;

import java.util.Arrays;

/** Primitive dynamically growing array of 'long'. Grows by factor of two. */
public class ArrayInt {
	private static final int INITIAL_CAPACITY = 256;
	
	private int[] data;
	private int size;
	
	public ArrayInt() {
		data = new int[INITIAL_CAPACITY];
	}
	
	public void add(int p) {
		grow();
		data[size] = p;
		size++;
	}
	
	public int get(int i) {
		return data[i];
	}

	public int size() {
		return size;
	}

	public int[] data() {
		return data;
	}
	
	public int[] toArray() {
		return Arrays.copyOf(data, size);
	}
	
	public String toString() {
		StringBuffer b = new StringBuffer();
		b.append('[');
		int i = 0;
		if (size > 0) {
			b.append(data[i]);
			for (++i; i < size; i++) {
				b.append(", ");
				b.append(data[i]);
			}
		}
		b.append(']');
		return b.toString();
	}

	public void clear() {
		size = 0;
	}
	
	
	/** adjust capacity to cover at least 1 more element */
	public void grow() {
		if (size == data.length) {
			data = Arrays.copyOf(data, data.length<<1);
		}
	}

	/** adjust capacity to cover at least n more elements. */
	public void grow(int n) {
		int requested = size + n;
		if (requested < data.length) {
			int newCapacity = data.length << 2;
			while (newCapacity < requested) newCapacity <<= 2;
			data = Arrays.copyOf(data, newCapacity);
		}
	}

}
