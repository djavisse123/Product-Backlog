package edu.ncsu.csc216.product_backlog.model.product;

import java.util.ArrayList;

import edu.ncsu.csc216.product_backlog.model.command.Command;
import edu.ncsu.csc216.product_backlog.model.task.Task;
import edu.ncsu.csc216.product_backlog.model.task.Task.Type;

/**
 * Class for creating products and having tasks that associate with each product.
 * In this class tasks can be stored in each created product and a product can have 0 to many task.
 * This class also contains methods that can help add,edit, and delete task.
 * @author Daniel Avisse
 *
 */
public class Product {
	
	/** Name of the product **/
	private String productName;
	
	/** Counter that is used when assigning taskIds **/
	private int counter;
	
	/** ArrayList the holds task **/
	private ArrayList<Task> tasks;
	
	/**
	 * Constructor a product with a productName.
	 * @param productName Name of the new product
	 * @throws IllegalArgumentException if the productName is invalid.
	 */
	public Product(String productName) {
		setProductName(productName);
		emptyList();
		setTaskCounter();
	}
	
	/**
	 * Sets the productName for product
	 * @param productName Name of the product
	 * @throws IllegalArgumentException if productName is invalid
	 */
	public void setProductName(String productName) {
		if (productName == null || "".equals(productName)) {
			throw new IllegalArgumentException("Invalid product name.");
		}
		this.productName = productName;
	}

	/**
	 * Gets the productName of product
	 * @return the name of the product.
	 */
	public String getProductName() {
		return productName;
	}



	/**
	 * Sets the counter for assigning taskIds
	 */
	private void setTaskCounter() {
		if (tasks.isEmpty()) {
			counter = 1;
		}
		else {
			int max = counter;
			for (int i = 0; i < tasks.size(); i++) {
				if (tasks.get(i).getTaskId() > max) {
					max = tasks.get(i).getTaskId();
					this.counter = max + 1;
				}
				else if (max == 1) {
					this.counter = max + 1;
				}
			}
		}	
	}
	
	/**
	 * Creates an emptyList of tasks.
	 */
	private void emptyList() {
		tasks = new ArrayList<Task>();
	}
	
	/**
	 * Creates a new task that is added to the ArrayList.
	 * @param task The new task that is added.
	 * @throws IllegalArgumentException if a task with the same Id as another is added.
	 */
	public void addTask(Task task) {
		if (tasks.isEmpty()) {
			tasks.add(task);
			setTaskCounter();
		}
		else {
			for (int i = 0; i < tasks.size(); i++) {
				if (task.getTaskId() == tasks.get(i).getTaskId()) {
					throw new IllegalArgumentException("Task cannot be added.");
				}
				if (task.getTaskId() < tasks.get(i).getTaskId() && i == 0) {
					tasks.add(0, task);
					setTaskCounter();
					break;
				}
				else if (task.getTaskId() < tasks.get(i).getTaskId()) {
					tasks.add(i, task);
					setTaskCounter();
					break;
				}
				else if (i == tasks.size() - 1) {
					tasks.add(task);
					setTaskCounter();
					break;
				}
			}	
		}	
	}
	
	/**
	 * Creates a new task and adds it to the ArrayList using the some of the params that make up a task.
	 * @param title Title of the task
	 * @param type Type for the task
	 * @param creator Creator of the task
	 * @param note Note for the task
	 * @throws IllegalArgumentException if any of the fields when adding the task are invalid
	 */
	public void addTask(String title, Type type, String creator, String note) {
		Task task = new Task(counter, title, type, creator, note);
		tasks.add(task);
		counter++;
		
	}

	/**
	 * Gets the ArrayList containing tasks and returns all the task inside.
	 * @return all the tasks in the ArrayList
	 */
	public ArrayList<Task> getTasks() {
		return tasks;
	}
	
	/**
	 * Searches the ArrayList containing tasks and returns the task that matches the id. 
	 * @param id Id of the task.
	 * @return returns the task if it's found
	 */
	public Task getTaskById(int id) {
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).getTaskId() == id) {
				return tasks.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Searches the ArrayList containing tasks by using it's id and then executing the command that will allow the task to transition states.
	 * @param id Id of the task.
	 * @param c Command value
	 */
	public void executeCommand(int id, Command c) {
		if (!getTasks().isEmpty()) {
			Task task = getTaskById(id);
			task.update(c);
		}
	}
	
	/**
	 * Searches the ArrayList containing task and removes the task based of it's id.
	 * @param id Id of the task.
	 */
	public void deleteTaskById(int id) {
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).getTaskId() == id) {
				tasks.remove(i);
			}
		}
	}
	
	
	
	
}
