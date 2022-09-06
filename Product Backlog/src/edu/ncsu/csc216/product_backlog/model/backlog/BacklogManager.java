package edu.ncsu.csc216.product_backlog.model.backlog;


import java.util.ArrayList;



import edu.ncsu.csc216.product_backlog.model.command.Command;
import edu.ncsu.csc216.product_backlog.model.io.ProductsReader;
import edu.ncsu.csc216.product_backlog.model.io.ProductsWriter;
import edu.ncsu.csc216.product_backlog.model.product.Product;
import edu.ncsu.csc216.product_backlog.model.task.Task;
import edu.ncsu.csc216.product_backlog.model.task.Task.Type;

/**
 * Class that handles the managing of ProductBacklog.
 * In this class this is where the user will do most of the interacting.
 * Users can add, edit, and delete products as well as tasks. They can also completely clear the list of products or reset the manager if there are issues. 
 * Users can also load files with products and task and also save their backlog to a file.
 * @author Daniel Avisse
 *
 */
public class BacklogManager {
	
	/** ArrayList that contains different products **/
	private ArrayList<Product> products;
	
	/** Product that will be used for editing of tasks as well as the product's name **/
	private Product currentProduct;
	
	/** Single instance of the backlogManager **/
	private static BacklogManager backLogMangerInstance;
	
	/**
	 * Constructor that creates a new BacklogManger. 
	 */
	private BacklogManager() {
		products = new ArrayList<Product>();
		currentProduct = null;
	}
	
	/**
	 * Gets the instance of BacklogManger for the GUI to use since the BacklogManager uses a singleton.
	 * @return BacklogManager
	 */
	public static BacklogManager getInstance() {
		if (backLogMangerInstance == null) {
			backLogMangerInstance = new BacklogManager();
		}
		return backLogMangerInstance;
	}
	
	/**
	 * Uses the ProductsWriter class to output the ProductBacklog to a new file.
	 * @param filename Name of the file
	 * @throws IllegalArgumentException if unable to output to a new file
	 */
	public void saveToFile(String filename) {
		if (!products.isEmpty() && getTasksAsArray().length != 0) {
			try {
				ProductsWriter.writeProductsToFile(filename, products);
			}
			catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("Unable to save file.");
			}
		}
		else {
			throw new IllegalArgumentException("Unable to save file.");
		}
	}
	
	/**
	 * Uses the ProductsReader class to load a valid file and import the products and tasks into the ProductBacklog.
	 * @param filename Name of the file
	 * @throws IllegalArgumentException if unable to load a file.
	 */
	public void loadFromFile(String filename) {
		try {
			products = ProductsReader.readProductsFile(filename);
		}
		catch(IllegalArgumentException e) {
			throw new IllegalArgumentException("Unable to load file.");
		}
		if (!products.isEmpty()) {
			currentProduct = products.get(0);
		}
	}
	
	/**
	 * Searches the ArrayList of products using productName and then loads the selected product making it the currentProduct.
	 * @param productName Name of the product
	 * @throws IllegalArgumentException if product is not in the list
	 */
	public void loadProduct(String productName) {
		if (products.isEmpty()) {
			throw new IllegalArgumentException("Product not available.");
		}
		for (int i = 0; i < products.size(); i++) {
			if (productName.equals(products.get(i).getProductName())) {
				currentProduct = products.get(i);
			}
		}
	}
	
	/**
	 * Method that checks to see if two of the same products exist.
	 * @param productName Name of the Product
	 * @throws IllegalArgumentException if a product has the same name as another.
	 */
	private void isDuplicateProduct(String productName) {
		for(int i = 0; i < products.size(); i++) {
			if (productName.equals(products.get(i).getProductName())) {
				throw new IllegalArgumentException("This product has the same name as another.");
			}
		}
	}
	
	/**
	 * Gets the task in the current product and displays them in an 2D array which the GUI will use to display the tasks.
	 * @return the tasks inside the currentProduct
	 */
	public String[][] getTasksAsArray() {
		if (currentProduct == null) {
			return null;
		}
		String[][] taskArray = new String [currentProduct.getTasks().size()][4];
		for (int i = 0; i < currentProduct.getTasks().size(); i++) {
			Task task = currentProduct.getTasks().get(i);
			taskArray[i][0] = String.valueOf(task.getTaskId());
			taskArray[i][1] = task.getStateName(); 
			taskArray[i][2] = task.getTypeLongName();
			taskArray[i][3] = task.getTitle();
		}
		return taskArray;
	}
	
	/**
	 * Searches the ArrayList containing tasks and returns the task that matches the id. 
	 * @param idx Index of the task.
	 * @return returns the task if it's found
	 */
	public Task getTaskById(int idx) {
		if (currentProduct == null) {
			return null;
		}
		return currentProduct.getTaskById(idx);
	}

	/**
	 * Searches the ArrayList containing tasks by using it's id and then executing the command that will allow the task to transition states.
	 * @param num Number of the task.
	 * @param c Command value
	 */
	public void executeCommand (int num, Command c) {
		if (currentProduct != null) {
			currentProduct.executeCommand(num, c);
		}
		
	}
	
	/**
	 * Searches the ArrayList containing task and removes the task based of it's id.
	 * @param idx Index of the task.
	 */
	public void deleteTaskById(int idx) {
		currentProduct.deleteTaskById(idx);
	}
	
	/**
	 * Creates a new task and adds it to the ArrayList of the currentProduct using the some of the params that make up a task.
	 * @param title Title of the task
	 * @param type Type for the task
	 * @param creator Creator of the task
	 * @param note Note for the task
	 */
	public void addTaskToProduct(String title, Type type, String creator, String note) {
		if (currentProduct != null) {
			currentProduct.addTask(title, type, creator, note);
		}
	}
	
	/**
	 * Gets the name of the currentProduct
	 * @return the name of the product
	 */
	public String getProductName() {
		if (currentProduct == null) {
			return null;
		}
		return currentProduct.getProductName();
	}
	
	/**
	 * Gets the ArrayList of products and returns all the products in the list.
	 * @return all the products in the ArrayList
	 */
	public String[] getProductList() {
		String[] productsArray = new String[products.size()];
		for (int i = 0; i < products.size(); i++) {
			productsArray[i] = products.get(i).getProductName();
		}
		return productsArray;
	}
	
	/**
	 * Clears the ArrayList of products by creating a new empty ArrayList
	 */
	public void clearProducts( ) {
		currentProduct = null;
		products = new ArrayList<Product>();
	}
	
	/**
	 * Edits the product by giving it a new name.
	 * @param updateName The new name of the product
	 * @throws IllegalArgumentException if productName is invalid
	 */
	public void editProduct(String updateName) {
		isDuplicateProduct(updateName);
		if (currentProduct == null) {
			throw new IllegalArgumentException("No product selected.");
		}
		currentProduct.setProductName(updateName);
	}
	
	/**
	 * Adds a new product to the products ArrayList
	 * @param productName Name of the product
	 * @throws IllegalArgumentException if productName is invalid
	 */
	public void addProduct(String productName) {
		isDuplicateProduct(productName);
		Product product = new Product(productName);
		products.add(product);
		loadProduct(productName);
	}
	
	/**
	 * Removes a product from the products ArrayList
	 * @throws IllegalArgumentException if no product is selected
	 */
	public void deleteProduct() {
		if (currentProduct == null) {
			throw new IllegalArgumentException("No product selected.");
		}
		products.remove(currentProduct);
		if (products.isEmpty()) {
			currentProduct = null;
		}
		else {
			currentProduct = products.get(0);
		}
	}
	
	/**
	 * Resets the BacklogManager.
	 */
	protected void resetManager() {
		backLogMangerInstance = null;
	}

}
