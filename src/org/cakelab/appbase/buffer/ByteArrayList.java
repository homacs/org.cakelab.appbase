package org.cakelab.appbase.buffer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Dynamically growing byte array
 * @author homac
 *
 */
public class ByteArrayList {
	byte[] buffer;
	private int size;
	
	public ByteArrayList(int initialCapacity) {
		buffer = new byte[initialCapacity];
		size = 0;
	}
	
	
	protected void grow(int newsize) {
		if (newsize > buffer.length) {
			int capacity = buffer.length;
			while (capacity <= newsize) capacity *= 2;
			byte[] tmp = new byte[capacity];
			System.arraycopy(buffer, 0, tmp, 0, size);
			buffer = tmp;
		}
	}
	
	public void add(byte[] that) {
		add(that, that.length);
	}

	public void add(byte[] that, int length) {
		grow(size + length);
		System.arraycopy(that, 0, buffer, size, length);
		size += length;
	}


	public byte[] getBuffer() {
		return buffer;
	}


	public int getSize() {
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
		return Arrays.copyOf(buffer, size);
	}



	
	
}
