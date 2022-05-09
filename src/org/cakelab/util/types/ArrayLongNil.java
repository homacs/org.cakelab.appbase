package org.cakelab.util.types;

import java.util.Arrays;

public class ArrayLongNil {
	private static final int DEFAULT_CAPACITY = 32;
	private static final long DEFAULT_NIL = 0;
	
	
	protected long[] array;

	protected int size;
	private final long nil;

	
	public ArrayLongNil() {
		this(DEFAULT_CAPACITY, DEFAULT_NIL );
	}
	

	/**
	 * 
	 * @param capacity initial capacity (number of elements in array) to be reserved.
	 * @param nil Value, which is supposed to represent Null in this array. 
	 *        This value is returned if a given value is not in the list. 
	 *        Using 0 (default) is fastest, because it's the Java default.
	 */
	public ArrayLongNil(int capacity, long nil) {
		this.size = 0;
		this.nil = nil;
		array = allocate(capacity);
	}

	/** returns the value which represents values Not In the List (NIL) */
	public long nil() {
		return nil;
	}
	
	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	public boolean contains(long value) {
		return indexOf(value) >= 0;
	}

	public long[] toArray() {
		return Arrays.copyOf(array, size);
	}

	public long[] toArray(long[] a) {
		if (a.length == size) {
			System.arraycopy(array, 0, a, 0, size);
			return a;
		} else {
			return toArray();
		}
	}

	public boolean add(long value) {
		growing(1);
		array[size++] = value;
		return true;
	}

	public boolean remove(long value) {
		int i = indexOf(value);
		if (i >= 0) {
			__removeIndex(i);
		}
		return i >= 0;
	}


	public void clear() {
		size = 0;
	}

	public long get(int index) {
		boundaries(index);
		return array[index];
	}


	public long set(int index, long element) {
		boundaries(index);
		long previous = array[index];
		array[index] = element;
		return previous;
	}
	
	
	public void fill(long element) {
		Arrays.fill(array, element);
	}
	
	
	

	public void insertAt(int index, long element) {
		growing(+1);
		__insertIndex(index, element);
	}

	public long removeAt(int index) {
		boundaries(index);
		long previous = array[index];
		__removeIndex(index);
		return previous;
	}

	public int indexOf(long element) {
		for (int i = 0; i < size; i++) {
			if (array[i] == element)
				return i;
		}
		return -1;
	}

	public int lastIndexOf(long element) {
		for (int i = size-1; i >= 0; i--) {
			if (array[i] == element)
				return i;
		}
		return -1;
	}


	protected void __insertIndex(int i, long element) {
		int index = i;
		for (i = size; i > index; i--) {
			array[i] = array[i-1];
		}
		array[index] = element;
		size++;
	}
	
	protected void __removeIndex(int i) {
		size--;
		for (;i < size; i++) {
			array[i] = array[i+1];
		}
	}

	protected void boundaries(int index) {
		if (index < 0 || index >= size) 
			throw new IndexOutOfBoundsException("index " + index + " out of bounds");
	}

	protected void growing(int increment) {
		int newSize = size+increment;
		if (newSize > array.length) {
			int capacity = array.length * 2;
			while(newSize > capacity) capacity *= 2;
			long[] newArray = allocate(capacity);
			System.arraycopy(array, 0, newArray, 0, size);
			Arrays.fill(newArray, size, capacity, nil);
			array = newArray;
		}
	}

	private long[] allocate(int size) {
		long[] array = new long[size];
		if (nil != 0) Arrays.fill(array, nil);
		return array;
	}

}
