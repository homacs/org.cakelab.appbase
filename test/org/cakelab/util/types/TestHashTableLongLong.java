package org.cakelab.util.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestHashTableLongLong {

	@Test
	public void testConstructor() {
		HashMapLongLongNil map = new HashMapLongLongNil();
		assertTrue(map.size() == 0);
	}

	@Test
	public void testPut() {
		HashMapLongLongNil map = new HashMapLongLongNil();

		map.put(1, 4);
		assertEquals(1, map.size());
		
		map.put(2, 3);
		assertEquals(2, map.size());
		
		map.put(3, 2);
		assertEquals(3, map.size());
		
		map.put(4, 1);
		assertEquals(4, map.size());
		
	}
	
	@Test
	public void testGet() {
		HashMapLongLongNil map = createRange(1, 6);

		assertEquals(6L, map.get(1));
		assertEquals(5L, map.get(2));
		assertEquals(4L, map.get(3));
		assertEquals(3L, map.get(4));
		assertEquals(2L, map.get(5));
		assertEquals(1L, map.get(6));
		
	}
	
	@Test
	public void testRemove() {
		HashMapLongLongNil map = createRange(1, 6);
		assertEquals(6, map.size());

		map.remove(1);
		assertEquals(5, map.size());
		assertFalse(map.containsKey(1));
		
		map.remove(2);
		assertEquals(4, map.size());
		assertFalse(map.containsKey(2));
		
		map.remove(3);
		assertEquals(3, map.size());
		assertFalse(map.containsKey(3));
		
		map.remove(4);
		assertEquals(2, map.size());
		assertFalse(map.containsKey(4));
		
		map.remove(5);
		assertEquals(1, map.size());
		assertFalse(map.containsKey(5));
		
		map.remove(6);
		assertEquals(0, map.size());
		assertFalse(map.containsKey(6));
		
		
	}
	
	

	private HashMapLongLongNil createRange(int first, int last) {
		HashMapLongLongNil map = new HashMapLongLongNil();
		for (long key = first; key <= last; key++) {
			long value = last - key + first;
			map.put(key, value);
		}
		return map;
	}
	
	
	
	
}
