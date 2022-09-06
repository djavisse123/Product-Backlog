package edu.ncsu.csc216.product_backlog.model.io;


import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.product_backlog.model.product.Product;
import edu.ncsu.csc216.product_backlog.model.task.Task;
import edu.ncsu.csc216.product_backlog.model.task.Task.Type;

/**
 * Tests the ProductsWriter class.
 * @author Daniel Avisse
 *
 */
class ProductsWriterTest {
	
	/**
	 * Tests the writeProductsToFile method to see if it can correctly output some products and tasks to a new file.
	 */
	@Test
	public void testWriteProductsToFile() {
		//Creating the ArrayList
		ArrayList<Product> products = new ArrayList<Product>();
		
		//Creating a Product and its tasks
		Product product1 = new Product("Product");
		Task task1 = new Task(1, "title1", Type.BUG, "creator1", "note1");
		Task task2 = new Task(2, "title2", Type.FEATURE, "creator2", "note2");
		Task task3 = new Task(3, "title3", Type.KNOWLEDGE_ACQUISITION, "creator3", "note3");
		product1.addTask(task1);
		product1.addTask(task2);
		product1.addTask(task3);
		products.add(product1);
		
		//Creating another Product and its tasks
		Product product2 = new Product("A Product");
		Task task4 = new Task(1, "title01", Type.TECHNICAL_WORK, "creator01", "note01");
		Task task5 = new Task(2, "title02", Type.FEATURE, "creator02", "note02");
		product2.addTask(task4);
		product2.addTask(task5);
		products.add(product2);
		
		//Outputting products to a new file
		try {
			ProductsWriter.writeProductsToFile("test-files/actual_product_records.txt", products);
		} catch (IllegalArgumentException e) {
			fail("Cannot write to course records file");
		}
		
		//Checking to see if files match
		checkFiles("test-files/exp_task_backlog.txt", "test-files/actual_product_records.txt");
	}
	
	/**
	 * Helper method to compare two files for the same contents
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try (Scanner expScanner = new Scanner(new File(expFile));
			 Scanner actScanner = new Scanner(new File(actFile));) {
			
			while (expScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			}
			
			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}

}
