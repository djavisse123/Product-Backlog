package edu.ncsu.csc216.product_backlog.model.io;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

/**
 * Tests the ProductsReader class.
 * Will test different valid and invalid files to see if the reader can read the valid ones properly and ignore the invalid ones.
 * @author Daniel Avisse
 */
class ProductsReaderTest {
	
	/**
	 * Tests the readProductsFromFile method using valid files. These files should return some products.
	 */
	@Test
	public void testValidReadProductsFromFile() {
		try {
			assertDoesNotThrow(() -> ProductsReader.readProductsFile("test-files/tasks1.txt"));
			assertEquals(2, ProductsReader.readProductsFile("test-files/tasks1.txt").size());
			assertEquals(1, ProductsReader.readProductsFile("test-files/tasks2.txt").size());
		}
		catch(Exception e){
			fail("Unexpected error reading ");
		}
	}
	
	/**
	 * Tests the readProductsFromFile method using invalid files. These files should not return any products.
	 */
	@Test
	public void testInvalidReadProductsFromFile() {
		assertThrows(IllegalArgumentException.class, () -> ProductsReader.readProductsFile("test-files/tasks0.txt"));
		assertEquals(0, ProductsReader.readProductsFile("test-files/tasks3.txt").size());
		assertEquals(0, ProductsReader.readProductsFile("test-files/tasks4.txt").size());
		assertEquals(0, ProductsReader.readProductsFile("test-files/tasks5.txt").size());
		assertEquals(0, ProductsReader.readProductsFile("test-files/tasks6.txt").size());
		assertEquals(0, ProductsReader.readProductsFile("test-files/tasks7.txt").size());
		assertEquals(0, ProductsReader.readProductsFile("test-files/tasks8.txt").size());
		assertEquals(0, ProductsReader.readProductsFile("test-files/tasks9.txt").size());
		assertEquals(0, ProductsReader.readProductsFile("test-files/tasks10.txt").size());
		assertEquals(0, ProductsReader.readProductsFile("test-files/tasks11.txt").size());
		assertEquals(0, ProductsReader.readProductsFile("test-files/tasks12.txt").size());
		assertEquals(0, ProductsReader.readProductsFile("test-files/tasks13.txt").size());
		assertEquals(0, ProductsReader.readProductsFile("test-files/tasks14.txt").size());
		assertEquals(0, ProductsReader.readProductsFile("test-files/tasks15.txt").size());
		assertEquals(0, ProductsReader.readProductsFile("test-files/tasks16.txt").size());
		assertEquals(0, ProductsReader.readProductsFile("test-files/tasks17.txt").size());
		assertEquals(0, ProductsReader.readProductsFile("test-files/tasks18.txt").size());
		assertEquals(0, ProductsReader.readProductsFile("test-files/tasks19.txt").size());
		assertEquals(0, ProductsReader.readProductsFile("test-files/tasks20.txt").size());
		assertEquals(0, ProductsReader.readProductsFile("test-files/tasks21.txt").size());
		assertEquals(0, ProductsReader.readProductsFile("test-files/tasks22.txt").size());
		assertEquals(0, ProductsReader.readProductsFile("test-files/tasks23.txt").size());
		assertEquals(0, ProductsReader.readProductsFile("test-files/tasks24.txt").size());
		assertEquals(0, ProductsReader.readProductsFile("test-files/tasks25.txt").size());	
	}
}
