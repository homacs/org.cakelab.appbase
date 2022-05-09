package org.cakelab.util.types;


public class HashMapLongLongNil {
	static final long DEFAULT_NIL = -1;

	static class Bucket extends ArrayLongLongNil {
		public Bucket(int capacity, long nil) {
			super(capacity, nil);
		}
	}
	
	
	private static final int DEFAULT_CAPACITY = 32;
	private static final int DEFAULT_BUCKET_CAPACITY = 4;
	
	private Bucket[] buckets;
	private int size;
	private final int bucketCapacity;
	private final long nil;
	
	
	public HashMapLongLongNil() {
		this(DEFAULT_CAPACITY, DEFAULT_BUCKET_CAPACITY, DEFAULT_NIL);
	}
	
	public HashMapLongLongNil(int capacity, int bucketCapacity, long nil) {
		this.bucketCapacity = bucketCapacity;
		this.nil = nil;
		this.buckets = new Bucket[capacity];
	}


	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size()==0;
	}

	public long put(long key, long value) {
		Bucket bucket = findBucket(key, true);
		int index = bucket.indexOf(key);
		if (index >= 0) {
			return bucket.set(index, key, value);
		} else {
			size++;
			bucket.add(key, value);
			return DEFAULT_NIL;
		}
	}


	public long get(long key) {
		Bucket bucket = findBucket(key, false);
		if (bucket != null) 
			return bucket.get(key);
		else
			return DEFAULT_NIL;
	}

	public boolean remove(long key) {
		Bucket bucket = findBucket(key, false);
		if (bucket != null && bucket.remove(key)) {
			size--;
			return true;
		}
		else
		{
			return false;
		}
	}

	public void clear() {
		for (Bucket bucket : buckets) {
			if (bucket != null)
				bucket.clear();
		}
		size = 0;
	}

	public boolean containsKey(long key) {
		return get(key) != DEFAULT_NIL;
	}

	/** Hash function used on given keys. 
	 * Defaults to Long.hash(long) */
	protected int keyhash(long key) {
		return Long.hashCode(key);
	}

	private Bucket findBucket(long key, boolean createIfNotFound) {
		int i = keyhash(key) % buckets.length;
		Bucket bucket = buckets[i];
		if (createIfNotFound && bucket == null) {
			bucket = new Bucket(bucketCapacity, nil);
			buckets[i] = bucket;
		}
		return bucket;
	}


}
