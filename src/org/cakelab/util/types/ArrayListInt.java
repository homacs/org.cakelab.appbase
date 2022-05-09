package org.cakelab.util.types;

import java.util.Arrays;


public class ArrayListInt implements Comparable<ArrayListInt> {
	private static final int DEFAULT_INITIAL_CAPACITY = 256;
	protected int[] data;
	protected int size;

	
	public ArrayListInt() {
		this(DEFAULT_INITIAL_CAPACITY);
	}
	
	public ArrayListInt(int initialCapacity) {
		this(0, initialCapacity);
	}
	
	public ArrayListInt(int initialSize, int initialCapacity) {
		this.size = initialSize;
		data = new int[initialCapacity];
	}
	
	public ArrayListInt(ArrayListInt a) {
		size = a.size;
		data = Arrays.copyOf(a.data, a.data.length);
	}


	public ArrayListInt(int[] buffer) {
		this.size = buffer.length;
		this.data = buffer;
	}
	
	public int get(int i) {
		return data[i];
	}
	
	public void set(int i, int v) {
		data[i] = v;
	}
	
	public void add(int v) {
		capacity(size+1);
		data[size++] = v;
	}
	
	
	public void clear() {
		size = 0;
	}
	
	public int size() {
		return size;
	}
	
	public int[] data() {
		return data;
	}

	@Override
	public int compareTo(ArrayListInt other) {
		ArrayListInt that = (ArrayListInt)other;
		int v = 0;
		int len = Math.min(data.length, other.data.length);
		for (int i = 0; v == 0 && i < len; i++) {
			v = that.data[i] - data[i];
		}
		if (v == 0) v = that.data.length - this.data.length;
		return v;
	}

	public String toString() {
		return Arrays.toString(data);
	}

	public void arraycopy(int start, ArrayListInt target, int target_start, int len) {
		int target_minsize = target_start + len;
		target.capacity(target_minsize);
		System.arraycopy(data, start, target.data, target_start, len);
		if (target.size() < target_minsize) target.size = target_minsize;
	}

	public void capacity(int minsize) {
		if (data.length < minsize) {
			int capacity = data.length * 2;
			while (capacity < minsize) capacity *= 2;
			data = Arrays.copyOf(data, capacity);
		}
	}
	
}
