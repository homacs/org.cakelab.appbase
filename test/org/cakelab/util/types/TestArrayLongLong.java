package org.cakelab.util.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestArrayLongLong {

	private ArrayLongLongNil createList(int first, int last) {
		ArrayLongLongNil list = new ArrayLongLongNil(1, 0);
		for (int i = first; i <= last; i++) {
			long j = last - i + first; 
			list.add((long)i, j);
		}
		
		return list;
	}
	
	private boolean containsRange(ArrayLongLongNil list, int first, int last) {
		boolean result = true;
		for (int i = first; i <= last; i++) {
			long j = last - i + first; 
			result = result && j == list.get((long)i);
		}
		return result;
	}
	
	
	@Test
	public void testAdd() {
		ArrayLongLongNil list = new ArrayLongLongNil(1, 0);

		list.add(1,5);
		list.add(2,4);
		list.add(3,3);
		list.add(4,2);
		list.add(5,1);

		assertTrue(containsRange(list, 1, 5));
		
		assertTrue(!list.contains(0L));
	}
	
	@Test
	public void testSetGet() {
		ArrayLongLongNil list = createList(1,6);


		list.set(0, 4, 1);
		list.set(1, 3, 2);
		list.set(2, 2, 3);
		list.set(3, 1, 4);
		assertEqualsEntry(list, 0, 4, 1);
		assertEqualsEntry(list, 1, 3, 2);
		assertEqualsEntry(list, 2, 2, 3);
		assertEqualsEntry(list, 3, 1, 4);
		
		assertEquals(2L, list.get(5L));
		assertEquals(1L, list.get(6L));

	}

	private void assertEqualsEntry(ArrayLongLongNil list, int index, long key, long value) {
		assertEquals(key, list.getKey(index));
		assertEquals(value, list.getValue(index));
		
	}

	@Test
	public void testNil() {
		ArrayLongLongNil list = createList(1,6);

		list.add(23, list.nil());
		
		assertEquals(list.nil(), list.get(23));
		assertTrue(containsRange(list, 1, 6));
	}

	@Test
	public void testInsertAt() {
		ArrayLongLongNil list = createList(1,6);
		int size = list.size();
		
		list.add(23L, 4711);
		
		assertEquals(size+1 , list.size());
		assertEquals(4711, list.get(23));
		assertTrue(containsRange(list, 1, 6));
	}

	@Test
	public void testRemove() {
		ArrayLongLongNil list = createList(1,6);

		list.remove(3L);
		assertFalse(list.contains(3));
		assertEquals(6L, list.get(1));
		assertEquals(5L, list.get(2));

		assertEquals(3L, list.get(4));
		assertEquals(2L, list.get(5));
		assertEquals(1L, list.get(6));
	}
	
	@Test
	public void testClear() {
		ArrayLongLongNil list = createList(1,6);
		list.clear();
		
		assertTrue(list.size() == 0);
		assertFalse(list.contains(1L));
	}
	
}
