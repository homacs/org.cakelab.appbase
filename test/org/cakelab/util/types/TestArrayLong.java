package org.cakelab.util.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestArrayLong {

	private ArrayLongNil createList(int first, int last) {
		ArrayLongNil list = new ArrayLongNil(1, 0);
		for (int i = first; i <= last; i++) {
			list.add((long)i);
		}
		
		return list;
	}
	
	private boolean containsRange(ArrayLongNil list, int first, int last) {
		boolean result = true;
		for (int i = first; i <= last; i++) {
			result = result && list.contains((long)i);
		}
		return result;
	}
	
	
	@Test
	public void testAdd() {
		ArrayLongNil list = new ArrayLongNil(1, 0);

		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);

		assertTrue(containsRange(list, 1, 5));
		
		assertTrue(!list.contains(0L));
	}
	
	@Test
	public void testSetGet() {
		ArrayLongNil list = createList(1,6);


		list.set(0, 4);
		list.set(1, 3);
		list.set(2, 2);
		list.set(3, 1);
		assertEquals(4L, list.get(0));
		assertEquals(3L, list.get(1));
		assertEquals(2L, list.get(2));
		assertEquals(1L, list.get(3));
		
		assertTrue(containsRange(list, 1, 6));

	}

	@Test
	public void testNil() {
		ArrayLongNil list = createList(1,6);

		list.add(list.nil());
		
		assertTrue(list.contains(list.nil()));
		assertTrue(containsRange(list, 1, 6));
	}

	@Test
	public void testInsertAt() {
		ArrayLongNil list = createList(1,6);
		int size = list.size();
		
		list.insertAt(3, 23);
		
		assertEquals(size+1 , list.size());
		assertEquals(23, list.get(3));
		assertEquals(3, list.indexOf(23));
		assertTrue(containsRange(list, 1, 6));
	}

	@Test
	public void testRemove() {
		ArrayLongNil list = createList(1,6);

		list.remove(3L);
		assertFalse(list.contains(3));
		assertTrue(containsRange(list, 1, 2));
		assertTrue(containsRange(list, 4, 6));
	}
	
	
	
}
