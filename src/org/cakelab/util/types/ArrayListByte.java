package org.cakelab.util.types;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Dynamically growing byte array
 * @author homac
 *
 */
public class ArrayListByte {
	
	byte[] data;
	private int size;
	
	public ArrayListByte(int initialCapacity) {
		data = new byte[initialCapacity];
		size = 0;
	}
	
	public void add(byte[] that) {
		add(that, that.length);
	}

	public void add(byte[] that, int length) {
		grow(size + length);
		System.arraycopy(that, 0, data, size, length);
		size += length;
	}


	public byte[] data() {
		return data;
	}

	public int size() {
		return size;
	}

	/** reads all available data from input stream into this buffer 
	 * @throws IOException */
	public void copy(InputStream in) throws IOException {
		byte[] buffer = new byte[1024];
		int size;
		while (0 < (size = in.read(buffer))) {
			add(buffer, size);
		}
	}


	public byte[] toArray() {
		return Arrays.copyOf(data, size);
	}

	public void grow(int newsize) {
		if (newsize > data.length) {
			int capacity = data.length;
			while (capacity <= newsize) capacity *= 2;
			data = Arrays.copyOf(data, capacity);
		}
	}
	
	
}
