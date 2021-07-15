package programes;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Activity1 {

	static ArrayList<String> list;

	@BeforeEach
	void setUp() throws Exception {

		// Initialize a new ArrayList
		list = new ArrayList<String>();

		// Add values to the list
		list.add("alpha"); // at index 0
		list.add("beta"); // at index 1
	}

	@Test
	void insertTest() {
		// Assert size of list
		assertEquals(2, list.size(), "Wrong size");
		// add new item to list
		list.add(2, "gamma");
		// assert size of list
		assertEquals(3, list.size(), "Wrong size");

		// assert individual elements
		assertEquals("alpha", list.get(0), "Elements not matching");
		assertEquals("beta", list.get(1), "Elements not matching");
		assertEquals("gamma", list.get(2), "Elements not matching");

	}

	@Test
	void replaceTest() {

		// Assert size of list
		assertEquals(2, list.size(), "Wrong size");

		// add new item to list
		list.add(2, "delta");
		// assert size of list
		assertEquals(3, list.size(), "Wrong size");

		// replace list value at index 1
		list.set(1, "theta");

		// assert individual elements
		assertEquals("alpha", list.get(0), "Elements not matching");
		assertEquals("theta", list.get(1), "Elements not matching");
		assertEquals("delta", list.get(2), "Elements not matching");

	}
}
