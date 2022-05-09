package org.cakelab.util.types;

import java.util.Arrays;

/**
 * Dynamically growing float array
 * @author homac
 *
 */
public class ArrayListFloat {
	private float[] data;
	private int size;
	
	public ArrayListFloat(int initialCapacity) {
		data = new float[initialCapacity];
		size = 0;
	}

	public float[] data() {
		return data;
	}

	public int size() {
		return size;
	}
	
	public void size(int newsize) {
		capacity(newsize);
		this.size = newsize;
	}

	public void clear() {
		size(0);
	}

	public int capacity() {
		return data.length;
	}
	
	public void capacity(int newsize) {
		if (newsize > data.length) {
			int capacity = data.length;
			while (capacity <= newsize) capacity *= 2;
			data = Arrays.copyOf(data, capacity);
		}
	}

	public float[] toArray() {
		return Arrays.copyOf(data, size);
	}



}
