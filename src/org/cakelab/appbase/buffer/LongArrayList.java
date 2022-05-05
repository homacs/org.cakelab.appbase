package org.cakelab.appbase.buffer;

import java.util.Arrays;


public class LongArrayList implements Comparable<LongArrayList>{
	protected long[] buffer;
	protected int size;
	private int capacity;
	

	public LongArrayList(int size, int capacity) {
		this.size = size;
		this.capacity = capacity;
		buffer = new long[capacity];
	}
	
	public LongArrayList(LongArrayList a) {
		size = a.size;
		capacity = a.capacity;
		buffer = Arrays.copyOf(a.buffer, a.buffer.length);
	}


	public LongArrayList(long[] buffer) {
		this.size = buffer.length;
		this.capacity = this.size;
		this.buffer = buffer;
	}

	
	public long get(int i) {
		return buffer[i];
	}
	
	public void set(int i, long v) {
		buffer[i] = v;
	}
	
	public void add(long v) {
		grow(size+1);
		buffer[size] = v;
	}
	
	@Override
	public int compareTo(LongArrayList other) {
		LongArrayList that = (LongArrayList)other;
		long v = 0;
		int len = Math.min(capacity, other.capacity);
		for (int i = 0; v == 0 && i < len; i++) {
			v = that.buffer[i] - buffer[i];
		}
		if (v == 0) v = that.capacity - this.capacity;
		return v == 0 ? 0 : v > 0 ? +1 : -1;
	}

	public String toString() {
		return Arrays.toString(buffer);
	}

	public long[] getBuffer() {
		return buffer;
	}

	public int size() {
		return size;
	}

	public void arraycopy(int start, LongArrayList target, int target_start, int len) {
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
