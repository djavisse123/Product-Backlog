package edu.ncsu.csc216.product_backlog.model.backlog;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.product_backlog.model.command.Command;
import edu.ncsu.csc216.product_backlog.model.command.Command.CommandValue;
import edu.ncsu.csc216.product_backlog.model.task.Task.Type;

/**
 * Tests the BacklogManger class
 * Tests different methods to make sure that the user is able to interact with different products and tasks.
 * @author Daniel Avisse
 */
class BacklogManagerTest {
	
	/** BacklogManager we will use to run tests. **/
	private BacklogManager manager;
	
	/** Name of the product **/
	private static final String PRODUCT_NAME = "Shopping Cart Simulation";
	
	/** Id of the task **/
	private static final int TASKID = 1;
	
	/** Type for the task **/
	private static final Type TYPE = Type.FEATURE;
	
	/** Title of the task **/
	private static final String TITLE = "Double Basket Shopping Carts";
	
	/** Creator of the task **/
	private static final String CREATOR = "Daniel";
	
	/** Note for the task **/
	private static final String NOTE = "Double Basket allows for the shopper to hold more items.";
	
	/** Command Value that sends the task to the Owned state **/
	private static final CommandValue COMMAND_VALUE_CLAIM = CommandValue.CLAIM;
	
	/** Name of the owner of the task when it's claimed **/
	private static final String CLAIMED_OWNER = "Joe";
	
	/** Location of a valid file **/
	private static final String FILE_LOCATION = "test-files/tasks1.txt";


	/**
	 * Resets the manager and gets a new instance of the manger before each test.
	 * @throws Exception if something goes wrong when getting the instance or reseting.
	 */
	@BeforeEach
	public void setUp() throws Exception {
		manager = BacklogManager.getInstance();
		manager.resetManager();
	}
	
	/**
	 * Tests the saveToFile method to see if it can save a Product Backlog to a file.
	 */
	@Test
	public void testSaveToFile() {
		//Create a product and add some tasks.
		manager.addProduct("Product");
		manager.loadProduct("Product");
		manager.addTaskToProduct("title1", Type.BUG, "creator1", "note1");
		manager.addTaskToProduct("title2", Type.FEATURE, "creator2", "note2");
		manager.addTaskToProduct("title3", Type.KNOWLEDGE_ACQUISITION, "creator3", "note3");
		
		//Create another product and add some more tasks
		manager.addProduct("A Product");
		manager.loadProduct("A Product");
		manager.addTaskToProduct("title01", Type.TECHNICAL_WORK, "creator01", "note01");
		manager.addTaskToProduct("title02", Type.FEATURE, "creator02", "note02");
		
		//Create the file can compare with expected.
		manager.saveToFile("test-files/actual_product_backlog.txt");
		checkFiles("test-files/exp_task_backlog.txt", "test-files/actual_product_backlog.txt");
		
		manager.loadFromFile("test-files/actual_product_backlog.txt");
		assertEquals(2, manager.getProductList().length);
	} 
	
	/**
	 * Test the loadProduct method to see if it can set a product in the Backlog as the current working product.
	 */
	@Test
	public void testLoadProduct() {
		//First load a file with some products and check to see if the products are in the products ArrayList
		manager.loadFromFile(FILE_LOCATION);
		assertEquals(2, manager.getProductList().length);
		
		//Load the products from the file and check the names.
		manager.loadProduct(PRODUCT_NAME);
		assertEquals(PRODUCT_NAME, manager.getProductName());
		manager.loadProduct("WolfScheduler");
		assertEquals("WolfScheduler", manager.getProductName());
	}
	
	/**
	 * Tests the loadFromFile method to see if it can load a valid file of products.
	 */
	@Test
	public void testLoadFromFile() {
		//Test with a file that doesn't exists and see if it throws.
		assertThrows(IllegalArgumentException.class, () -> manager.loadFromFile("test-files/tasks0.txt"));
		assertEquals(0, manager.getProductList().length);
		
		//Test with an invalid file and see the there are zero products.
		manager.loadFromFile("test-files/tasks3.txt");
		assertEquals(0, manager.getProductList().length);
		
		//Load a valid file and check how many products there are.
		manager.loadFromFile(FILE_LOCATION);
		assertEquals(2, manager.getProductList().length);
	}
	
	
	/**
	 * Tests the getTaskArray method to see if it can output tasks correctly in an array.
	 */
	@Test
	public void testGetTaskArray() {
		//Load a valid file and check to see if there are products.
		manager.loadFromFile(FILE_LOCATION);
		assertEquals(2, manager.getProductList().length);
		
		//Load one of the products and check one of the tasks
		manager.loadProduct(PRODUCT_NAME);
		assertEquals(6, manager.getTasksAsArray().length);
		assertEquals("1", manager.getTasksAsArray()[0][0]);
		assertEquals("Backlog", manager.getTasksAsArray()[0][1]);
		assertEquals("Feature", manager.getTasksAsArray()[0][2]);
		assertEquals("Express Carts", manager.getTasksAsArray()[0][3]);
		
		//Load another product and check another task
		manager.loadProduct("WolfScheduler");
		assertEquals(3, manager.getTasksAsArray().length);
		assertEquals("2", manager.getTasksAsArray()[0][0]);
		assertEquals("Rejected", manager.getTasksAsArray()[0][1]);
		assertEquals("Feature", manager.getTasksAsArray()[0][2]);
		assertEquals("Weekly Repeat", manager.getTasksAsArray()[0][3]);
	}
	
	/**
	 * Tests the getTaskById method to make sure it can return a task using it's Id.
	 */
	@Test
	public void testGetTaskById() {
		//Load a valid file and check for products
		manager.loadFromFile(FILE_LOCATION);
		assertEquals(2, manager.getProductList().length);
		
		//Load one of the products and get a task by it's id and check if the name is correct.
		manager.loadProduct(PRODUCT_NAME);
		assertEquals(1, manager.getTaskById(1).getTaskId());
		assertEquals("Express Carts", manager.getTaskById(1).getTitle());
		
		//Load another product and get a task by it's id and check if the name is correct.
		manager.loadProduct("WolfScheduler");
		assertEquals(2, manager.getTaskById(2).getTaskId());
		assertEquals("Weekly Repeat", manager.getTaskById(2).getTitle());
	}
	
	/**
	 * Tests the excecuteCommand method to make sure that the BacklogManger can make a task transition states.
	 */
	@Test
	public void testExecuteCommand() {
		//Create a new product and load it.
		manager.addProduct(PRODUCT_NAME);
		assertEquals(1, manager.getProductList().length);
		manager.loadProduct(PRODUCT_NAME);
		
		//Add a task to the product
		manager.addTaskToProduct(TITLE, TYPE, CREATOR, NOTE);
		assertEquals(1, manager.getTaskById(1).getTaskId());
		assertEquals(TITLE, manager.getTaskById(1).getTitle());
		
		//Create a command for transtion.
		Command command = new Command(COMMAND_VALUE_CLAIM, CLAIMED_OWNER, "Adding Joe to this task");
		
		//Execute command and check if the task moved states.
		manager.executeCommand(1, command);
		assertEquals("Owned", manager.getTaskById(TASKID).getStateName());
		assertEquals(CLAIMED_OWNER, manager.getTaskById(TASKID).getOwner());
	}
	
	/**
	 * Tests the deleteTaskById method to make sure that it can delete a task using it's Id.
	 */
	@Test
	public void testDeleteTaskById() {
		//Create a new products and load it.
		manager.addProduct(PRODUCT_NAME);
		assertEquals(1, manager.getProductList().length);
		manager.loadProduct(PRODUCT_NAME);
		
		//Add a new task to it.
		manager.addTaskToProduct(TITLE, TYPE, CREATOR, NOTE);
		
		//Delete the task and check the product has zero tasks.
		manager.deleteTaskById(TASKID);
		assertEquals(0, manager.getTasksAsArray().length);
	}
	
	/**
	 * Tests the addTaskToProduct method to make sure it can add new tasks to a product.
	 */
	@Test
	public void testAddTaskToProduct() {
		//Create a new product and load it.
		manager.addProduct(PRODUCT_NAME);
		assertEquals(1, manager.getProductList().length);
		manager.loadProduct(PRODUCT_NAME);
		
		//Add a new task to product and check that it was correctly added.
		manager.addTaskToProduct(TITLE, TYPE, CREATOR, NOTE);
		assertEquals(TASKID, manager.getTaskById(TASKID).getTaskId());
		assertEquals(TITLE, manager.getTaskById(TASKID).getTitle());
		assertEquals(1, manager.getTasksAsArray().length);
		
	}
	
	/**
	 * Test getProductName method to make sure it can return the name of the currentProduct.
	 */
	@Test
	public void testgetProductName() {
		//Load a valid file of products
		manager.loadFromFile(FILE_LOCATION);
		assertEquals(2, manager.getProductList().length);
		
		//Load one of the products and check the name.
		manager.loadProduct(PRODUCT_NAME);
		assertEquals(PRODUCT_NAME, manager.getProductName());
		
		//Load the other product and check the name.
		manager.loadProduct("WolfScheduler");
		assertEquals("WolfScheduler", manager.getProductName());
	}
	
	/**
	 * Tests the getProductList method to see if it can output a list of products.
	 */
	@Test
	public void testGetProductList() {
		//Load a valid file of products
		manager.loadFromFile(FILE_LOCATION);
		assertEquals(2, manager.getProductList().length);
		
		//Add a couple more products.
		manager.addProduct("Grade Calculator");
		manager.addProduct("Sandwich Shop");
		manager.addProduct("Slot Machine");
		manager.addProduct("Ice Cream Maker");
		
		//Check the size of the list and make sure that each product is in the productList.
		assertEquals(6, manager.getProductList().length);
		assertEquals(PRODUCT_NAME, manager.getProductList()[0]);
		assertEquals("WolfScheduler", manager.getProductList()[1]);
		assertEquals("Grade Calculator", manager.getProductList()[2]);
		assertEquals("Sandwich Shop", manager.getProductList()[3]);
		assertEquals("Slot Machine", manager.getProductList()[4]);
		assertEquals("Ice Cream Maker", manager.getProductList()[5]);		
	}
	
	/**
	 * Tests the clearProducts method to make sure it can clear a list of products.
	 */
	@Test
	public void testClearProducts() {
		//Load a valid file of products
		manager.loadFromFile(FILE_LOCATION);
		assertEquals(2, manager.getProductList().length);
		
		//Add a couple more products
		manager.addProduct("Grade Calculator");
		manager.addProduct("Sandwich Shop");
		manager.addProduct("Slot Machine");
		manager.addProduct("Ice Cream Maker");
		assertEquals(6, manager.getProductList().length);
		
		//Clear the the products and check that there are no products.
		manager.clearProducts();
		assertEquals(0, manager.getProductList().length);
		assertEquals(null, manager.getProductName());
	}
	
	/**
	 * Test the editProduct method to see if the user can edit the name of the product.
	 */
	@Test
	public void testEditProduct() {
		//Add a product
		manager.addProduct(PRODUCT_NAME);
		assertEquals(1, manager.getProductList().length);
		
		//Edit the name to a duplicate name
		assertThrows(IllegalArgumentException.class, () -> manager.editProduct(PRODUCT_NAME));
		assertEquals(PRODUCT_NAME, manager.getProductName());
		
		//Edit the name to a different name
		manager.editProduct("Shopping Cart Sim");
		assertEquals("Shopping Cart Sim", manager.getProductName());
		
	}
	
	/**
	 * Test the addProduct method to see if the user can add new products.
	 */
	@Test
	public void testAddProduct() {
		//Add a couple of new products
		manager.addProduct("Grade Calculator");
		manager.addProduct("Sandwich Shop");
		manager.addProduct("Slot Machine");
		manager.addProduct("Ice Cream Maker");
		
		//Check that these products were added correctly. 
		assertEquals(4, manager.getProductList().length);
		assertEquals("Grade Calculator", manager.getProductList()[0]);
		assertEquals("Sandwich Shop", manager.getProductList()[1]);
		assertEquals("Slot Machine", manager.getProductList()[2]);
		assertEquals("Ice Cream Maker", manager.getProductList()[3]);		
	}
	
	/**
	 * Tests the deleteProducts method to see if it can delete existing products.
	 */
	@Test
	public void testDeleteProducts() {
		//Add a couple of products
		manager.addProduct("Grade Calculator");
		manager.addProduct("Sandwich Shop");
		manager.addProduct("Slot Machine");
		manager.addProduct("Ice Cream Maker");
		assertEquals(4, manager.getProductList().length);
		
		//Load a product and delete it and check the size of productList.
		manager.loadProduct("Grade Calculator");
		manager.deleteProduct();
		assertEquals(3, manager.getProductList().length);
		
		//Load and delete another product.
		manager.loadProduct("Slot Machine");
		manager.deleteProduct();
		assertEquals(2, manager.getProductList().length);
		
		//Check the other two products remain.
		assertEquals("Sandwich Shop", manager.getProductList()[0]);
		assertEquals("Ice Cream Maker", manager.getProductList()[1]);
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
