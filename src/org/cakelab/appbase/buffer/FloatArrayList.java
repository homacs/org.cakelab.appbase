package org.cakelab.appbase.buffer;

import java.util.Arrays;

/**
 * Dynamically growing float array
 * @author homac
 *
 */
public class FloatArrayList {
	float[] buffer;
	private int size;
	
	public FloatArrayList(int initialCapacity) {
		buffer = new float[initialCapacity];
		size = 0;
	}
	
	
	protected void grow(int newsize) {
		if (newsize > buffer.length) {
			int capacity = buffer.length;
			while (capacity <= newsize) capacity *= 2;
			float[] tmp = new float[capacity];
			System.arraycopy(buffer, 0, tmp, 0, size);
			buffer = tmp;
		}
	}
	


	public float[] getBuffer() {
		return buffer;
	}


	public int getSize() {
		return size;
	}


	public float[] toArray() {
		return Arrays.copyOf(buffer, size);
	}



	
	
}
