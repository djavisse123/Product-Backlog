package edu.ncsu.csc216.product_backlog.model.product;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.product_backlog.model.command.Command;
import edu.ncsu.csc216.product_backlog.model.command.Command.CommandValue;
import edu.ncsu.csc216.product_backlog.model.task.Task;
import edu.ncsu.csc216.product_backlog.model.task.Task.Type;

/**
 * Test the Product class.
 * Tests different methods to see if a valid product can be created, if it can hold tasks, and if the tasks can be added, removed, or transition states.
 * @author Daniel Avisse
 */
class ProductTest {
	
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
	
	/**
	 * Tests the product constructor and setProductName to see if a valid product can be created.
	 */
	@Test
	public void testProductANDSetProductName() {
		//Valid Product Name
		Product product = assertDoesNotThrow(
				() -> new Product(PRODUCT_NAME),
				"Should not throw exception");
		assertEquals(PRODUCT_NAME, product.getProductName());
		
		//Invalid Product Names
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Product(""));
		assertEquals("Invalid product name.", e1.getMessage());
		
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Product(null));
		assertEquals("Invalid product name.", e2.getMessage());
	}
	
	/**
	 * Tests the addTask method with fields to see if a task can be added to a product properly.
	 */
	@Test
	public void testAddTaskWithFields() {
		//Adding a product and checking if the ID is correct
		Product product = new Product(PRODUCT_NAME);
		product.addTask(TITLE, TYPE, CREATOR, NOTE);
		assertEquals(1, product.getTasks().size());
		assertEquals(1, product.getTasks().get(0).getTaskId());
		
		//Do it again for a few more tasks
		product.addTask("Triple Bakset Shopping Carts", TYPE, CREATOR, "Allows for more space than a Double Basket.");
		assertEquals(2, product.getTasks().size());
		assertEquals(2, product.getTasks().get(1).getTaskId());
		product.addTask("Tiny Shopping Carts", TYPE, CREATOR, "Smaller shopping carts for people who don’t need a regular one.");
		assertEquals(3, product.getTasks().size());
		assertEquals(3, product.getTasks().get(2).getTaskId());
	}
	
	/**
	 * Tests the addTask method to see if a task can be added correctly.
	 */
	@Test
	public void testAddTask(){
		//Create a product and add a task
		Product product = new Product(PRODUCT_NAME);
		Task task1 = new Task(7, "Tall Basket Shopping Cart", TYPE, CREATOR, "A shopping cart will a taller basket.");
		product.addTask(task1);
		assertEquals(1, product.getTasks().size());
				
		//Adding Task with same ID as another
		Task task2 = new Task(7, "Short Basket Shopping Cart", TYPE, CREATOR, "A shopping cart will a shorter basket.");
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> product.addTask(task2));
		assertEquals("Task cannot be added.", e1.getMessage());
		
		//Adding a task with a lower ID than an existing task
		Task task3 = new Task(1, TITLE, TYPE, CREATOR, NOTE);
		product.addTask(task3);
		
		
		//Adding a few more tasks
		Task task4 = new Task(4, "Rocket Powered Shopping Cart", TYPE, CREATOR, "Shopping Carts with Rockets to move faster.");
		product.addTask(task4);
		Task task5 = new Task(10, "Triple Basket Shopping Cart", TYPE, CREATOR, "Allows for more space than a Double Basket.");
		product.addTask(task5);
		Task task6 = new Task(5, "Tinhy Basket Shopping Cart", TYPE, CREATOR, "Smaller shopping carts for people who don’t need a regular one.");
		product.addTask(task6);		
	}
	
	/**
	 * Tests both addTask methods together.
	 */
	@Test
	public void testAddTaskCombined() {
		//Create a product and add a task
		Product product = new Product(PRODUCT_NAME);
		Task task1 = new Task(1, "Test task 1", Type.BUG, "sesmith5", "note 1");
		product.addTask(task1);
		assertEquals(1, product.getTasks().size());
		assertEquals(1, product.getTaskById(1).getTaskId());
		
		//Add another task using the addTask method with fields
		product.addTask("title2", Type.BUG, "creator2", "note2");
		assertEquals(2, product.getTasks().size());
		assertEquals(2, product.getTaskById(2).getTaskId());
		
		//Create a task and add the task to product
		Task task2 = new Task(7, "Test task 2", Type.KNOWLEDGE_ACQUISITION, "sesmith", "note 2");
		product.addTask(task2);
		assertEquals(3, product.getTasks().size());
		assertEquals(7, product.getTaskById(7).getTaskId());
		
		//Add another task using the addTask method with fields
		product.addTask("title3", Type.FEATURE, "creator3", "note3");
		assertEquals(4, product.getTasks().size());
		assertEquals(8, product.getTaskById(8).getTaskId());
		
		//Get a task that doesn't exist in the product.
		assertEquals(null, product.getTaskById(5));
		
		//Create the task and add it to product.
		Task task3 = new Task(5, "Test task 5", Type.FEATURE, "sesmith5", "note 5");
		product.addTask(task3);
		assertEquals(5, product.getTasks().size());
		assertEquals(5, product.getTaskById(5).getTaskId());
	}
	
	/**
	 * Test the getTaskById method to see if a task can be returned using it's Id.
	 */
	@Test
	public void testGetTaskById() {
		//Create task for comparison
		Task task = new Task(TASKID, TITLE, TYPE, CREATOR, NOTE);
		
		//Add Task to Product
		Product product = new Product(PRODUCT_NAME);
		product.addTask(TITLE, TYPE, CREATOR, NOTE);
		assertEquals(1, product.getTasks().size());
		assertEquals(1, product.getTasks().get(0).getTaskId());
		
		//Use GetTaskById and Compare different fields
		assertEquals(task.getTaskId(), product.getTaskById(TASKID).getTaskId());
		assertEquals(task.getTitle(), product.getTaskById(TASKID).getTitle());
		assertEquals(task.getType(), product.getTaskById(TASKID).getType());
		assertEquals(task.getCreator(), product.getTaskById(TASKID).getCreator());
		assertEquals(task.getNotes(), product.getTaskById(TASKID).getNotes());
	}
	
	/**
	 * Tests the executeCommand method to make sure that product is able to make a task transition states.
	 */
	@Test
	public void testExecuteCommand() {
		//Adding a product and checking if the ID is correct
		Product product = new Product(PRODUCT_NAME);
		product.addTask(TITLE, TYPE, CREATOR, NOTE);
		assertEquals(1, product.getTasks().size());
		assertEquals(1, product.getTasks().get(0).getTaskId());
				
		//Do it again for a few more tasks
		product.addTask("Triple Bakset Shopping Carts", TYPE, CREATOR, "Allows for more space than a Double Basket.");
		assertEquals(2, product.getTasks().size());
		assertEquals(2, product.getTasks().get(1).getTaskId());
		product.addTask("Tiny Shopping Carts", TYPE, CREATOR, "Smaller shopping carts for people who don’t need a regular one.");
		assertEquals(3, product.getTasks().size());
		assertEquals(3, product.getTasks().get(2).getTaskId());
		
		Command command = new Command(COMMAND_VALUE_CLAIM, CLAIMED_OWNER, "Adding Joe to the owner of this task");
		
		product.executeCommand(2, command);
		Task task = product.getTaskById(2);
		assertEquals("Owned", task.getStateName());
	}
	
	/**
	 * Tests the deleteTaskById method to see if a task can be deleted using it's Id.
	 */
	@Test
	public void testDeleteTaskById() {
		//Adding a product and some tasks
		Product product = new Product(PRODUCT_NAME);
		product.addTask(TITLE, TYPE, CREATOR, NOTE);
		product.addTask("Triple Bakset Shopping Carts", TYPE, CREATOR, "Allows for more space than a Double Basket.");
		product.addTask("Tiny Shopping Carts", TYPE, CREATOR, "Smaller shopping carts for people who don’t need a regular one.");
		assertEquals(3, product.getTasks().size());
		
		//Remove a task and check
		product.deleteTaskById(1);
		assertEquals(2, product.getTasks().size());
		
		//Remove another
		product.deleteTaskById(3);
		assertEquals(1, product.getTasks().size());
	}

}
