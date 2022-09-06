package edu.ncsu.csc216.product_backlog.model.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.product_backlog.model.product.Product;
import edu.ncsu.csc216.product_backlog.model.task.Task;

/**
 * Class that reads a valid file and creates products and tasks using that files.
 * Has two helper methods that help tokenize products and task correctly.
 * If a file is invalid no tasks or products will be added.
 * @author Daniel Avisse
 *
 */
public class ProductsReader {
		
	/**
	 * Reads in the file that will be used to import products and tasks.
	 * @param fileName Name of the file
	 * @return returns an ArrayList containing products and their associated tasks
	 * @throws IllegalArgumentException if unable to read file
	 */
	public static ArrayList<Product> readProductsFile(String fileName) {
		ArrayList<Product> products = new ArrayList<Product>();
		try {
			Scanner fileReader = new Scanner(new FileInputStream(fileName));
			String contentsOfFile = "";
			int indexOfProduct = 0;
			int taskId = 0;
			int noteIdx = 0;
			//Is used to avoid notes of a task with a duplicate id.
			boolean invalidNote = false;

			while (fileReader.hasNextLine()) {
				try {
					contentsOfFile += fileReader.nextLine() + "\n";
				}
				catch(IllegalArgumentException e){
					fileReader.close();
					throw new IllegalArgumentException("Unable to read file.");
				}
			}
			Scanner scanner = new Scanner(contentsOfFile);
			String nextLine = "";
			while (scanner.hasNext()) {
				try {
					nextLine = scanner.nextLine();
					if(nextLine.startsWith("#")) {
						Product product = processProduct(nextLine);
						products.add(product);
						indexOfProduct = products.size() - 1;
						nextLine = scanner.nextLine();
					}
					if (nextLine.startsWith("*")) {
						Task task = processTask(nextLine);
						taskId = task.getTaskId();
						nextLine = scanner.nextLine();
						try {
							if (!products.isEmpty()) {
								products.get(indexOfProduct).addTask(task);
								task.getNotes().remove("Temp String");
								invalidNote = false;
							}							
						}
						catch (IllegalArgumentException e) {
							//Catches an IllegalArgumentException that is thrown when a duplicate task is added and skips it.
							//Sets invalidNote boolean to true which will skip the notes of the invalid task.
							invalidNote = true;
						}
						 
					}
					 if (nextLine.startsWith("-")) {
						 nextLine = nextLine.substring(1, nextLine.length()).trim();
						 if (!products.isEmpty() && !products.get(indexOfProduct).getTasks().isEmpty() && !invalidNote) {
							noteIdx = products.get(indexOfProduct).getTaskById(taskId).addNoteToList(nextLine);
						 }						

					 }
					 if (Character.isLetter(nextLine.charAt(0)) && !products.isEmpty() && !products.get(indexOfProduct).getTasks().isEmpty() 
							 && !products.get(indexOfProduct).getTaskById(taskId).getNotes().isEmpty()) {
						String note = products.get(indexOfProduct).getTaskById(taskId).getNotes().get(noteIdx);
						products.get(indexOfProduct).getTaskById(taskId).getNotes().remove(note);
						note += "\n" + nextLine;
						products.get(indexOfProduct).getTaskById(taskId).addNoteToList(note);						 
					 }
				 }
				catch (InputMismatchException e) {
					//Do nothing 
				}
				catch (NoSuchElementException e) {
					//Do nothing
				}
				catch (IllegalArgumentException e) {
					//Do nothing
				}
			 }
			 scanner.close();
			 fileReader.close();
		}
		catch (FileNotFoundException e){
			throw new IllegalArgumentException("Unable to load file.");
		}
		for(int i = 0; i < products.size(); i++) {
			if (products.get(i).getTasks().size() == 0) {
				products.remove(i);
			}
			else {
				for (int j = 0; j < products.get(i).getTasks().size(); j++) {
					if (products.get(i).getTasks().get(i).getNotes().size() == 0) {
						products.remove(i);
					}
				}
			}
		}
		 return products;
	}
	
	/**
	 * Helper method that reads in information relating to the product and tokenizes it.
	 * @param nextLine The nextLine of the file containing product information
	 * @return returns a product that is added to the ArrayList of products
	 * @throws IllegalArgumentException if product is not tokenized correctly 
	 */
	private static Product processProduct(String nextLine) {
		String productString = nextLine.substring(1, nextLine.length()).trim();
		try {
			Product product = new Product(productString);
			return product;
		}
		catch(IllegalArgumentException e) {
			throw new IllegalArgumentException("Unable to load file.");
		}
		
	}
	
	/**
	 * Helper method that reads in information relating to the tasks and tokenizes it.
	 * @param nextLine The nextLine of the file containing task information
	 * @return returns a task and is added to the current product
	 * @throws IllegalArgumentException if tasks is not tokenized correctly
	 */
	private static Task processTask(String nextLine) {
		String taskString = nextLine.substring(1, nextLine.length()).trim();
		Scanner taskScanner = new Scanner(taskString);
		taskScanner.useDelimiter(",");
		int taskId = 0;
		String taskState = "";
		String taskTitle = "";
		String taskType = "";
		String taskCreator = "";
		String taskOwner = "";
		String taskisVerified = "";
		try {
			if (taskScanner.hasNext()) {
				taskId = taskScanner.nextInt();
			}
			if (taskScanner.hasNext()) {
				taskState = taskScanner.next();
			}
			if (taskScanner.hasNext()) {
				 taskTitle = taskScanner.next();
			}
			if (taskScanner.hasNext()) {
				 taskType = taskScanner.next();
			}
			if (taskScanner.hasNext()) {
				 taskCreator = taskScanner.next();
			}
			if (taskScanner.hasNext()) {
				 taskOwner = taskScanner.next();
			}
			if (taskScanner.hasNext()) {
				 taskisVerified = taskScanner.next();
			}
			taskScanner.close();
			ArrayList<String> notes = new ArrayList<String>();
			notes.add("Temp String");
			Task task = new Task(taskId, taskState, taskTitle, taskType, taskCreator, taskOwner, taskisVerified, notes);
			return task;
			
		}
		catch(IllegalArgumentException e) {
			throw new IllegalArgumentException("Unable to load file.");
		}
	}
}
