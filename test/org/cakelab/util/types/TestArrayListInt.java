package org.cakelab.util.types;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestArrayListInt {

	@Test
	void testGet() {
		ArrayListInt a = new ArrayListInt();
		a.add(1);
		a.add(2);
		a.add(3);

		assertEquals(1, a.get(0));
		assertEquals(2, a.get(1));
		assertEquals(3, a.get(2));
		assertEquals(3, a.size());
	}


}
