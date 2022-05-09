package org.cakelab.util.types;

import java.util.Arrays;

/** An array of key value pairs of type long. 
 * <b>Reserves one value to indicate NIL!</b> */
public class ArrayLongLongNil {
	private static final int DEFAULT_CAPACITY = 32;
	private static final long DEFAULT_NIL = 0;
	
	protected long[] array;

	protected int capacity;
	protected int size;
	private final long nil;

	public ArrayLongLongNil() {
		this(DEFAULT_CAPACITY, DEFAULT_NIL);
	}

	public ArrayLongLongNil(int capacity, long nil) {
		this.size = 0;
		this.capacity = capacity*2;
		this.nil = nil;
		array = allocate(this.capacity);
	}

	
	public int size() {
		return size/2;
	}

	/** returns value, which is used to indicate, that an entry (key,value) is Not In List (NIL) */
	public long nil() {
		return nil;
	}

	public boolean add(long key, long value) {
		growing(+2);
		int i = size;
		size += 2;
		__set(i, key, value);
		return true;
	}

	public long put(long key, long value) {
		int i = __indexOf(key);
		if (i >= 0) {
			long previous = array[i+1];
			array[i+1] = value;
			return previous;
		} else {
			add(key, value);
			return nil;
		}
	}

	
	
	
	/** overrides tuple at index */
	public long set(int index, long key, long value) {
		boundaries(index*2);
		return __set(index*2, key, value);
	}
	
	/** searches and returns value for key or NIL */
	public long get(long key) {
		int i = __indexOf(key);
		if (i >= 0) {
			return array[i+1];
		} else {
			return nil;
		}
	}
	
	public long getKey(int index) {
		index *= 2;
		boundaries(index);
		return array[index];
	}
	
	public long getValue(int index) {
		index *= 2;
		boundaries(index+1);
		return array[index+1];
	}
	
	
	public boolean contains(long key) {
		return __indexOf(key) >= 0;
	}
	
	/**
	 * removes key and entry
	 */
	public boolean remove(long key) {
		int i = __indexOf(key);
		if (i >= 0) {
			__removeIndex(i);
			return true;
		} else {
			return false;
		}
	}

	
	public void removeAt(int index) {
		index *= 2;
		boundaries(index);
		__removeIndex(index);
	}
	
	/** 
	 * finds index of key or returns -1.
	 */
	public int indexOf(long key) {
		int i =  __indexOf(key);
		if (i > 0) i /= 2;
		return i;
	}
	
	public void clear() {
		size = 0;
		if (nil != 0) Arrays.fill(array, nil);
	}


	protected int __indexOf(long key) {
		for (int i = 0; i < size; i += 2) {
			if (array[i] == key)
				return i;
		}
		return -1;
	}


	protected void __insertIndex(int i, long element) {
		// move entry behind last position to make room
		__set(size, array[i], array[i+1]);
		array[i] = element;
		array[i+1] = nil;
		size += 2;
	}
	
	protected void __removeIndex(int i) {
		size -= 2;
		// move last entry to removed index
		__set(i, array[size], array[size+1]);
		array[size] = nil;
		array[size+1] = nil;
	}
	
	protected long __set(int index, long key, long value) {
		long previous = array[index+1];
		array[index] = key;
		array[index+1] = value;
		return previous;
	}

	protected void growing(int increment) {
		int newSize = size+increment;
		if (newSize > capacity) {
			do {
				capacity *= 2;
			} while(newSize > capacity);
			long[] newArray = allocate(capacity);
			System.arraycopy(array, 0, newArray, 0, size);
			Arrays.fill(newArray, size, capacity, nil);
			array = newArray;
		}
	}

	private void boundaries(int index) {
		if (index < 0 || index >= size) 
			throw new IndexOutOfBoundsException("index " + index + " out of bounds");
	}
	
	private long[] allocate(int size) {
		long[] array = new long[size];
		if (nil != 0) Arrays.fill(array, nil);
		return array;
	}

}
