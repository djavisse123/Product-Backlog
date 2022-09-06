package edu.ncsu.csc216.product_backlog.model.io;

import java.io.File;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import edu.ncsu.csc216.product_backlog.model.product.Product;

/**
 * Class that outputs a ProductBacklog to a new file.
 * @author Daniel Avisse
 *
 */
public class ProductsWriter {
	
	/**
	 * Outputs the current ProductBacklog with all the tasks and products to a new file.
	 * @param filename Name of the file.
	 * @param products ArrayList containing the products that will be outputted.
	 * @throws IllegalArgumentException if unable to output to a new file
	 */
	public static void writeProductsToFile(String filename, ArrayList<Product> products)  {
		try {
			PrintStream fileWriter = new PrintStream(new File(filename));
			for (int i = 0; i < products.size(); i++) {
			    fileWriter.println("# " + products.get(i).getProductName());
			    for (int j = 0; j < products.get(i).getTasks().size(); j++) {
			    	fileWriter.println("* " + products.get(i).getTasks().get(j).toString());
			    }
			}
			fileWriter.close();
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Cannont save file");
		}
		
	}
}
