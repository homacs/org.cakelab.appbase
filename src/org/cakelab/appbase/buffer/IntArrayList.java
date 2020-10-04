package org.cakelab.appbase.buffer;

import java.util.Arrays;


public class IntArrayList implements Comparable<IntArrayList>{
	protected int[] buffer;
	protected int size;
	private int capacity;
	

	public IntArrayList(int size, int capacity) {
		this.size = size;
		this.capacity = capacity;
		buffer = new int[capacity];
	}
	
	public IntArrayList(IntArrayList a) {
		size = a.size;
		capacity = a.capacity;
		buffer = Arrays.copyOf(a.buffer, a.buffer.length);
	}


	public IntArrayList(int[] buffer) {
		this.size = buffer.length;
		this.capacity = this.size;
		this.buffer = buffer;
	}

	
	public int get(int i) {
		return buffer[i];
	}
	
	public void set(int i, int v) {
		buffer[i] = v;
	}
	
	public void add(int v) {
		grow(size+1);
		buffer[size] = v;
	}
	
	@Override
	public int compareTo(IntArrayList other) {
		IntArrayList that = (IntArrayList)other;
		int v = 0;
		int len = Math.min(capacity, other.capacity);
		for (int i = 0; v == 0 && i < len; i++) {
			v = that.buffer[i] - buffer[i];
		}
		if (v == 0) v = that.capacity - this.capacity;
		return v;
	}

	public String toString() {
		return Arrays.toString(buffer);
	}

	public int[] getBuffer() {
		return buffer;
	}

	public int size() {
		return size;
	}

	public void arraycopy(int start, IntArrayList target, int target_start, int len) {
		int target_minsize = target_start + len;
		target.grow(target_minsize);
		System.arraycopy(buffer, start, target.buffer, target_start, len);
		if (target.size() < target_minsize) target.size = target_minsize;
	}

	private void grow(int minsize) {
		if (capacity < minsize) {
			capacity = minsize;
			buffer = Arrays.copyOf(buffer, capacity);
		}
	}
	
}
