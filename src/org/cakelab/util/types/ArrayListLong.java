package org.cakelab.util.types;

import java.util.Arrays;


public class ArrayListLong implements Comparable<ArrayListLong>{
	protected long[] data;
	protected int size;
	

	public ArrayListLong(int size, int capacity) {
		this.size = size;
		data = new long[capacity];
	}
	
	public ArrayListLong(ArrayListLong a) {
		size = a.size;
		data = Arrays.copyOf(a.data, a.data.length);
	}

	public ArrayListLong(long[] buffer) {
		this.size = buffer.length;
		this.data = buffer;
	}

	
	public long get(int i) {
		return data[i];
	}
	
	public void set(int i, long v) {
		data[i] = v;
	}
	
	public void add(long v) {
		capacity(size+1);
		data[size++] = v;
	}
	
	public long[] data() {
		return data;
	}

	public int size() {
		return size;
	}

	@Override
	public int compareTo(ArrayListLong other) {
		ArrayListLong that = (ArrayListLong)other;
		long v = 0;
		int len = Math.min(data.length, other.data.length);
		for (int i = 0; v == 0 && i < len; i++) {
			v = that.data[i] - data[i];
		}
		if (v == 0) v = that.data.length - data.length;
		return v == 0 ? 0 : v > 0 ? +1 : -1;
	}

	public String toString() {
		return Arrays.toString(data);
	}

	public void arraycopy(int start, ArrayListLong target, int target_start, int len) {
		int target_minsize = target_start + len;
		target.capacity(target_minsize);
		System.arraycopy(data, start, target.data, target_start, len);
		if (target.size() < target_minsize) target.size = target_minsize;
	}

	public int capacity() {
		return data.length;
	}
	
	public void capacity(int minsize) {
		if (data.length < minsize) {
			int capacity = data.length;
			while(capacity < minsize) capacity *= 2;
			data = Arrays.copyOf(data, capacity);
		}
	}
	
}
