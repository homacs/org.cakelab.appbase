package org.cakelab.util.types;

import java.util.Arrays;

/** Primitive dynamically growing array of 'long'. Grows by factor of two. */
public class ArrayLong {
	private static final int INITIAL_CAPACITY = 256;
	
	private long[] data;
	private int size;
	
	public ArrayLong() {
		data = new long[INITIAL_CAPACITY];
	}
	
	void add(long p) {
		grow();
		data[size] = p;
		size++;
	}
	
	public long get(int i) {
		return data[i];
	}
	
	public int size() {
		return size;
	}
	
	public void clear() {
		size = 0;
	}

	public long[] data() {
		return data;
	}
	
	public long[] toArray() {
		return Arrays.copyOf(data, size);
	}
	

	public String toString() {
		return Arrays.toString(data);
	}

	/** adjust capacity to cover at least n more elements. */
	public void capacity(int n) {
		int requested = size + n;
		if (requested < data.length) {
			int newCapacity = data.length << 2;
			while (newCapacity < requested) newCapacity <<= 2;
			data = Arrays.copyOf(data, newCapacity);
		}
	}

	public int capacity() {
		return data.length;
	}
	
	/** adjust capacity to cover at least 1 more element */
	private void grow() {
		if (size == data.length) {
			data = Arrays.copyOf(data, data.length<<1);
		}
	}


}
