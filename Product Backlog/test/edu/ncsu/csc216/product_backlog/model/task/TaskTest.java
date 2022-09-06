package edu.ncsu.csc216.product_backlog.model.task;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.product_backlog.model.command.Command;
import edu.ncsu.csc216.product_backlog.model.command.Command.CommandValue;
import edu.ncsu.csc216.product_backlog.model.task.Task.Type;

/**
 * Tests the Task Class.
 * This will test to see if the task class is able to create tasks with valid values as well as being able to change states.
 * @author Daniel Avisse
 *
 */
class TaskTest {
	
	/** Id of the task **/
	private static final int TASKID = 1;
	
	/** The name of the Backlog state **/
	private static final String BACKLOG_STATE = "Backlog";
	
	/** The name of the Owned state **/
	public static final String OWNED_STATE = "Owned";
	
	/** The name of the Processing state **/
	public static final String PROCESSING_STATE = "Processing";
	
	/** The name of the Verifying state **/
	public static final String VERIFYING_STATE = "Verifying";
	
	/** The name of the Done state **/
	public static final String DONE_STATE = "Done";
	
	/** The name of the Rejected state **/
	public static final String REJECTED_STATE = "Rejected";
	
	/** Type for the task as a string **/
	private static final String TYPE_STRING = "F";
	
	/** Type for the task **/
	private static final Type TYPE = Type.FEATURE;
	
	/** Title of the task **/
	private static final String TITLE = "Double Basket Shopping Carts";
	
	/** Creator of the task **/
	private static final String CREATOR = "Daniel";
	
	/** Name of the owner when the task has no owner **/
	private static final String OWNER = "unowned";
	
	/** Name of the owner of the task when it's claimed **/
	private static final String CLAIMED_OWNER = "Joe";
	
	/** Boolean that tells if a task as passed the verified state and is complete **/
	private static final String ISVERIFIED = "false";
	
	/** Note for the task **/
	private static final String NOTE = "Double Basket allows for the shopper to hold more items.";
	
	/** ArrayList that contains all the notes made for one task **/
	private ArrayList<String> notes = new ArrayList<String>();
	
	/** Command Value that sends the task to the Backlog state **/
	private static final CommandValue COMMAND_VALUE_BACKLOG = CommandValue.BACKLOG;
	
	/** Command Value that sends the task to the Owned state **/
	private static final CommandValue COMMAND_VALUE_CLAIM = CommandValue.CLAIM;
	
	/** Command Value that sends the task to the Processing state **/
	private static final CommandValue COMMAND_VALUE_PROCESS = CommandValue.PROCESS;
	
	/** Command Value that sends the task to the Verifying state **/
	private static final CommandValue COMMAND_VALUE_VERIFY = CommandValue.VERIFY;
	
	/** Command Value that sends the task to the Done state**/
	private static final CommandValue COMMAND_VALUE_COMPLETE = CommandValue.COMPLETE;
	
	/** Command Value that sends the task to the Rejected state **/
	private static final CommandValue COMMAND_VALUE_REJECT = CommandValue.REJECT;
	
	/**
	 * Tests both task constructors for valid construction
	 */
	@Test
	public void testTask() {
		
		//Valid Construction with Constructor with 5 fields
		notes.add("[Backlog] " + NOTE);
		Task task1 = assertDoesNotThrow(
				() -> new Task(TASKID, TITLE, TYPE, CREATOR, NOTE),
				"Should not throw exception");
		assertAll("Task", 
				() -> assertEquals(TASKID, task1.getTaskId()), 
				//() -> assertEquals(STATE, task2.getStateName()),
				() -> assertEquals(TITLE, task1.getTitle()), 
				() -> assertEquals(TYPE, task1.getType()),
				() -> assertEquals(CREATOR, task1.getCreator()),
				() -> assertEquals(OWNER, task1.getOwner()), 
				() -> assertFalse(task1.isVerified()),
				() -> assertEquals(notes, task1.getNotes()));
		
		//Valid Construction with Constructor with all fields
		Task task2 = assertDoesNotThrow(
				() -> new Task(TASKID, BACKLOG_STATE, TITLE, TYPE_STRING, CREATOR, OWNER, ISVERIFIED, notes),
				"Should not throw exception");
		assertAll("Task", 
				() -> assertEquals(TASKID, task2.getTaskId()), 
				//() -> assertEquals(STATE, task2.getStateName()),
				() -> assertEquals(TITLE, task2.getTitle()), 
				() -> assertEquals(TYPE_STRING, task2.getTypeShortName()),
				() -> assertEquals(CREATOR, task2.getCreator()),
				() -> assertEquals(OWNER, task2.getOwner()), 
				() -> assertFalse(task2.isVerified()),
				() -> assertEquals(notes, task2.getNotes()));
	}
	
	/**
	 * Tests setTaskId() to make sure it takes valid IDs and throws an exception for invalid IDs
	 */
	@Test
	public void testSetTaskId() {
		//Valid Id
		notes.add(NOTE);
		Task task = assertDoesNotThrow(
				() -> new Task(TASKID, BACKLOG_STATE, TITLE, TYPE_STRING, CREATOR, OWNER, ISVERIFIED, notes),
				"Should not throw exception");
		assertEquals(TASKID, task.getTaskId());
				
		//Invalid Id
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Task(0, BACKLOG_STATE, TITLE, TYPE_STRING, CREATOR, OWNER, ISVERIFIED, notes));
		assertEquals("Invalid task information.", e1.getMessage());
	}
	
	/**
	 * Tests setTitle() to make sure it takes valid titles and throws an exception for invalid titles
	 */
	@Test
	public void testSetTitle() {
		//Valid Title
		notes.add(NOTE);
		Task task = assertDoesNotThrow(
				() -> new Task(TASKID, BACKLOG_STATE, TITLE, TYPE_STRING, CREATOR, OWNER, ISVERIFIED, notes),
				"Should not throw exception");
		assertEquals(TITLE, task.getTitle());
		
		//Invalid Title
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Task(TASKID, BACKLOG_STATE, "", TYPE_STRING, CREATOR, OWNER, ISVERIFIED, notes));
		assertEquals("Invalid task information.", e1.getMessage());
	}
	
	/**
	 * Tests setType() to make sure it takes valid types and throws an exception for invalid types
	 */
	@Test
	public void testSetType() {
		//Valid Type
		Task task = assertDoesNotThrow(
				() -> new Task(TASKID, TITLE, TYPE, CREATOR, NOTE),
				"Should not throw exception");
		assertEquals(TYPE, task.getType());
		
		//Invalid Type
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Task(TASKID, TITLE, null, CREATOR, NOTE));
		assertEquals("Invalid task information.", e1.getMessage());
	}
	
	/**
	 * Tests setCreator() to make sure it takes valid creators and throws an exception for invalid creators
	 */
	@Test
	public void testSetCreator() {
		//Valid Creator
		notes.add(NOTE);
		Task task = assertDoesNotThrow(
				() -> new Task(TASKID, BACKLOG_STATE, TITLE, TYPE_STRING, CREATOR, OWNER, ISVERIFIED, notes),
				"Should not throw exception");
		assertEquals(CREATOR, task.getCreator());
						
		//Invalid Creator
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Task(TASKID, BACKLOG_STATE, TITLE, TYPE_STRING, "", OWNER, ISVERIFIED, notes));
		assertEquals("Invalid task information.", e1.getMessage());
	}
	
	/**
	 * Tests setOwner() to make sure it takes valid owners and throws an exception for invalid owners
	 */
	@Test
	public void testSetOwner() {
		//Valid Owner
		notes.add(NOTE);
		Task task = assertDoesNotThrow(
				() -> new Task(TASKID, BACKLOG_STATE, TITLE, TYPE_STRING, CREATOR, OWNER, ISVERIFIED, notes),
				"Should not throw exception");
		assertEquals(OWNER, task.getOwner());
								
		//Invalid Owner
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Task(TASKID, BACKLOG_STATE, TITLE, TYPE_STRING, CREATOR, "", ISVERIFIED, notes));
		assertEquals("Invalid task information.", e1.getMessage());
	}
	
	/**
	 * Tests setVerified() to make sure that a string that equals false is sets isVerified false and a string that equals true is sets isVerified to true
	 */
	@Test
	public void testSetVerified() {
		//Set to false
		notes.add(NOTE);
		Task task = assertDoesNotThrow(
				() -> new Task(TASKID, BACKLOG_STATE, TITLE, TYPE_STRING, CREATOR, OWNER, ISVERIFIED, notes),
				"Should not throw exception");
		assertFalse(task.isVerified());
		
		//Set to true
		Task task2 = assertDoesNotThrow(
				() -> new Task(TASKID, BACKLOG_STATE, TITLE, TYPE_STRING, CREATOR, OWNER, "True", notes),
				"Should not throw exception");
		assertTrue(task2.isVerified());
		
		//Invalid isVerified
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Task(TASKID, BACKLOG_STATE, TITLE, TYPE_STRING, CREATOR, OWNER, "yes", notes));
		assertEquals("Invalid task information.", e1.getMessage()); 
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Task(TASKID, BACKLOG_STATE, TITLE, TYPE_STRING, CREATOR, OWNER, null, notes));
		assertEquals("Invalid task information.", e2.getMessage()); 
		Exception e3 = assertThrows(IllegalArgumentException.class,
				() -> new Task(TASKID, BACKLOG_STATE, TITLE, TYPE_STRING, CREATOR, OWNER, "", notes));
		assertEquals("Invalid task information.", e3.getMessage()); 
	}
	
	/**
	 * Tests setNotes() to make sure that it takes valid notes and throws an exception for invalid notes
	 */
	@Test
	public void testSetNotes() {
		//Invalid Notes
		notes.add("");
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Task(TASKID, BACKLOG_STATE, TITLE, TYPE_STRING, CREATOR, OWNER, ISVERIFIED, notes));
		assertEquals("Invalid task information.", e1.getMessage());
		
		//Valid Notes
		notes.remove("");
		notes.add(NOTE);
		Task task = assertDoesNotThrow(
				() -> new Task(TASKID, BACKLOG_STATE, TITLE, TYPE_STRING, CREATOR, OWNER, ISVERIFIED, notes),
				"Should not throw exception");
		assertEquals(notes, task.getNotes());
	}
	
	/**
	 * Tests setState() to make sure that it takes valid states and throws an exception for invalid states
	 */
	@Test
	public void testSetState() {
		//Valid State
		notes.add(NOTE);
		Task task = assertDoesNotThrow(
				() -> new Task(TASKID, BACKLOG_STATE, TITLE, TYPE_STRING, CREATOR, OWNER, ISVERIFIED, notes),
				"Should not throw exception");
		assertEquals(BACKLOG_STATE, task.getStateName());
		
		//Invalid States
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Task(TASKID, "ReJECTEd", TITLE, TYPE_STRING, CREATOR, OWNER, ISVERIFIED, notes));
		assertEquals("Invalid task information.", e1.getMessage());
		
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Task(TASKID, "", TITLE, TYPE_STRING, CREATOR, OWNER, ISVERIFIED, notes));
		assertEquals("Invalid task information.", e2.getMessage());
		
		Exception e3 = assertThrows(IllegalArgumentException.class,
				() -> new Task(TASKID, null, TITLE, TYPE_STRING, CREATOR, OWNER, ISVERIFIED, notes));
		assertEquals("Invalid task information.", e3.getMessage());
		
	}
	
	/**
	 * Tests setTypeFromString() to make sure it takes valid types as strings and throws an exception for invalid string types.
	 */
	@Test
	public void testSetTypeFromString() {
		//Valid Type F
		notes.add(NOTE);
		Task task1 = assertDoesNotThrow(
				() -> new Task(TASKID, BACKLOG_STATE, TITLE, TYPE_STRING, CREATOR, OWNER, ISVERIFIED, notes),
				"Should not throw exception");
		assertEquals(TYPE_STRING, task1.getTypeShortName());
				
		//Valid Type B
		Task task2 = assertDoesNotThrow(
				() -> new Task(TASKID, BACKLOG_STATE, TITLE, "B", CREATOR, OWNER, ISVERIFIED, notes),
				"Should not throw exception");
		assertEquals("B", task2.getTypeShortName());
				
		//Valid Type KA
		Task task3 = assertDoesNotThrow(
				() -> new Task(TASKID, BACKLOG_STATE, TITLE, "KA", CREATOR, OWNER, ISVERIFIED, notes),
				"Should not throw exception");
		assertEquals("KA", task3.getTypeShortName());
				
		//Valid Type TW
		Task task4 = assertDoesNotThrow(
				() -> new Task(TASKID, BACKLOG_STATE, TITLE, "TW", CREATOR, OWNER, ISVERIFIED, notes),
				"Should not throw exception");
		assertEquals("TW", task4.getTypeShortName());
				
						
		//Invalid Types
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Task(TASKID, BACKLOG_STATE, TITLE, "G", CREATOR, OWNER, ISVERIFIED, notes));
		assertEquals("Invalid task information.", e1.getMessage());
				
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Task(TASKID, BACKLOG_STATE, TITLE, "", CREATOR, OWNER, ISVERIFIED, notes));
		assertEquals("Invalid task information.", e2.getMessage());
				
		Exception e3 = assertThrows(IllegalArgumentException.class,
				() -> new Task(TASKID, TITLE, null, CREATOR, NOTE));
		assertEquals("Invalid task information.", e3.getMessage());
	}
	
	/**
	 * Tests toString() to make sure it outputs a task and it's fields properly into a comma separated list.
	 */
	@Test
	public void testToString() {
		Task task1 = new Task(TASKID, TITLE, TYPE, CREATOR, NOTE);
		String s1 = "1,Backlog,Double Basket Shopping Carts,F,Daniel,unowned,false\n- [Backlog] Double Basket allows for the shopper to hold more items.";
		assertEquals(s1, task1.toString());
	}
	
	/**
	 * Test update() to see if a task can change states
	 */
	@Test
	public void testUpdate() {
		//Create a valid task
		notes.add(NOTE);
		Task task = assertDoesNotThrow(
				() -> new Task(TASKID, BACKLOG_STATE, TITLE, TYPE_STRING, CREATOR, OWNER, ISVERIFIED, notes),
				"Should not throw exception");
		assertEquals(BACKLOG_STATE, task.getStateName());
		
		//Create a valid command
		Command command = new Command(COMMAND_VALUE_CLAIM, "Joe", "Adding Joe to the owner of this task.");
		
		//Update Task and check state
		task.update(command);
		assertEquals(OWNED_STATE, task.getStateName());
	}
	
	/**
	 * Test updateState() in the BacklogState for all valid transitions and throws an exception for any invalid transitions
	 */
	@Test
	public void testUpdateBacklogState() {
		//Backlog A
		notes.add(NOTE);
		Task task1 = new Task(TASKID, BACKLOG_STATE, TITLE, TYPE_STRING, CREATOR, OWNER, ISVERIFIED, notes);
		Command command1 = new Command(COMMAND_VALUE_CLAIM, CLAIMED_OWNER, "Adding Joe to the owner of this task.");
		task1.update(command1);
		assertEquals(OWNED_STATE, task1.getStateName());
		
		//Backlog B
		Task task2 = new Task(TASKID, BACKLOG_STATE, TITLE, TYPE_STRING, CREATOR, OWNER, ISVERIFIED, notes);
		Command command2 = new Command(COMMAND_VALUE_REJECT, null, "Double Basket Shopping Carts are too big.");
		task2.update(command2);
		assertEquals(REJECTED_STATE, task2.getStateName());
		
		//Invalid Transition
		Task task3 = new Task(TASKID, BACKLOG_STATE, TITLE, TYPE_STRING, CREATOR, OWNER, ISVERIFIED, notes);
		Command command3 = new Command(COMMAND_VALUE_COMPLETE, null, "Double Basket Shopping Carts are cool!");
		Exception e1 = assertThrows(UnsupportedOperationException.class,
				() -> task3.update(command3));
		assertEquals("Invalid transition.", e1.getMessage());
		
	}
	
	/**
	 * Test updateState() in the OwnedState for all valid transitions and throws an exception for any invalid transitions
	 */
	@Test
	public void testUpdateOwnedState() {
		//Owned A
		notes.add(NOTE);
		Task task1 = new Task(TASKID, OWNED_STATE, TITLE, TYPE_STRING, CREATOR, CLAIMED_OWNER, ISVERIFIED, notes);
		Command command1 = new Command(COMMAND_VALUE_PROCESS, null, "Creating designs for Double Basket Shopping Cart.");
		task1.update(command1);
		assertEquals(PROCESSING_STATE, task1.getStateName());
		
		//Owned B
		Task task2 = new Task(TASKID, OWNED_STATE, TITLE, TYPE_STRING, CREATOR, CLAIMED_OWNER, ISVERIFIED, notes);
		Command command2 = new Command(COMMAND_VALUE_REJECT, null, "Double Basket Shopping Carts are too big.");
		task2.update(command2);
		assertEquals(REJECTED_STATE, task2.getStateName());
		
		//Owned C
		Task task3 = new Task(TASKID, OWNED_STATE, TITLE, TYPE_STRING, CREATOR, CLAIMED_OWNER, ISVERIFIED, notes);
		Command command3 = new Command(COMMAND_VALUE_BACKLOG, null, "Removing Joe as owner of this task.");
		task3.update(command3);
		assertEquals(BACKLOG_STATE, task3.getStateName());
		
		//Invalid Transition
		Task task4 = new Task(TASKID, OWNED_STATE, TITLE, TYPE_STRING, CREATOR, CLAIMED_OWNER, ISVERIFIED, notes);
		Command command4 = new Command(COMMAND_VALUE_COMPLETE, null, "Double Basket Shopping Carts are cool!");
		Exception e1 = assertThrows(UnsupportedOperationException.class,
				() -> task4.update(command4));
		assertEquals("Invalid transition.", e1.getMessage());
		
	}

	/**
	 * Test updateState() in the VerifyingState for all valid transitions and throws an exception for any invalid transitions
	 */
	@Test
	public void testUpdateVerifyingState() {
		//Verifying A
		notes.add(NOTE);
		Task task1 = new Task(TASKID, VERIFYING_STATE, TITLE, TYPE_STRING, CREATOR, CLAIMED_OWNER, ISVERIFIED, notes);
		Command command1 = new Command(COMMAND_VALUE_COMPLETE, null, "Designs are good and complete.");
		task1.update(command1);
		assertEquals(DONE_STATE, task1.getStateName());
		
		//Verifying B
		Task task2 = new Task(TASKID, VERIFYING_STATE, TITLE, TYPE_STRING, CREATOR, CLAIMED_OWNER, ISVERIFIED, notes);
		Command command2 = new Command(COMMAND_VALUE_PROCESS, null, "Designs are incomplete");
		task2.update(command2);
		assertEquals(PROCESSING_STATE, task2.getStateName());

		//Invalid Transition
		Task task3 = new Task(TASKID, VERIFYING_STATE, TITLE, TYPE_STRING, CREATOR, CLAIMED_OWNER, ISVERIFIED, notes);
		Command command3 = new Command(COMMAND_VALUE_REJECT, null, "Double Basket Shopping Carts are too big.");
		Exception e1 = assertThrows(UnsupportedOperationException.class,
				() -> task3.update(command3));
		assertEquals("Invalid transition.", e1.getMessage());
		
	}
	
	/**
	 * Test updateState() in the ProcessingState for all valid transitions and throws an exception for any invalid transitions
	 */
	@Test
	public void testUpdateProcessingState() {
		//Processing A
		notes.add(NOTE);
		Task task1 = new Task(TASKID, PROCESSING_STATE, TITLE, TYPE_STRING, CREATOR, CLAIMED_OWNER, ISVERIFIED, notes);
		Command command1 = new Command(COMMAND_VALUE_PROCESS, null, "Adjusting designs for Double Basket Shopping Cart.");
		task1.update(command1);
		assertEquals(PROCESSING_STATE, task1.getStateName());
		
		//Processing B
		Task task2 = new Task(TASKID, PROCESSING_STATE, TITLE, TYPE_STRING, CREATOR, CLAIMED_OWNER, ISVERIFIED, notes);
		Command command2 = new Command(COMMAND_VALUE_VERIFY, null, "Designs are complete. Ready for inspection.");
		task2.update(command2);
		assertEquals(VERIFYING_STATE, task2.getStateName());
		
		//Processing C
		Task task3 = new Task(TASKID, PROCESSING_STATE, TITLE, "KA", CREATOR, CLAIMED_OWNER, ISVERIFIED, notes);
		Command command3 = new Command(COMMAND_VALUE_COMPLETE, null, "Double Basket Shopping Carts are cool!");
		task3.update(command3);
		assertEquals(DONE_STATE, task3.getStateName());
		
		//Processing D
		Task task4 = new Task(TASKID, PROCESSING_STATE, TITLE, TYPE_STRING, CREATOR, CLAIMED_OWNER, ISVERIFIED, notes);
		Command command4 = new Command(COMMAND_VALUE_BACKLOG, null, "Putting this task back in Backlog");
		task4.update(command4);
		assertEquals(BACKLOG_STATE, task4.getStateName());
		
		//Invalid Transition
		Task task5 = new Task(TASKID, PROCESSING_STATE, TITLE, TYPE_STRING, CREATOR, CLAIMED_OWNER, ISVERIFIED, notes);
		Command command5 = new Command(COMMAND_VALUE_REJECT, null, "Double Basket Shopping Carts are too big.");
		Exception e1 = assertThrows(UnsupportedOperationException.class,
				() -> task5.update(command5));
		assertEquals("Invalid transition.", e1.getMessage());
		
	}
	
	/**
	 * Test updateState() in the DoneState for all valid transitions and throws an exception for any invalid transitions
	 */
	@Test
	public void testUpdateDoneState() {
		//Done A
		notes.add(NOTE);
		Task task1 = new Task(TASKID, DONE_STATE, TITLE, TYPE_STRING, CREATOR, CLAIMED_OWNER, "True", notes);
		Command command1 = new Command(COMMAND_VALUE_PROCESS, null, "Designs are incomplete");
		task1.update(command1);
		assertEquals(PROCESSING_STATE, task1.getStateName());
		
		//Done B
		Task task2 = new Task(TASKID, DONE_STATE, TITLE, TYPE_STRING, CREATOR, CLAIMED_OWNER, "True", notes);
		Command command2 = new Command(COMMAND_VALUE_BACKLOG, null, "Moving to Backlog for further implementation.");
		task2.update(command2);
		assertEquals(BACKLOG_STATE, task2.getStateName());

		//Invalid Transition
		Task task3 = new Task(TASKID, DONE_STATE, TITLE, TYPE_STRING, CREATOR, CLAIMED_OWNER, "True", notes);
		Command command3 = new Command(COMMAND_VALUE_REJECT, null, "Double Basket Shopping Carts are too big.");
		Exception e1 = assertThrows(UnsupportedOperationException.class,
				() -> task3.update(command3));
		assertEquals("Invalid transition.", e1.getMessage());
		
	}
	
	/*
	 * Test updateState() in the RejectedState for all valid transitions and throws an exception for any invalid transitions
	 */
	@Test
	public void testUpdateRejectedState() {
		//Rejected
		notes.add(NOTE);
		Task task1 = new Task(TASKID, REJECTED_STATE, TITLE, TYPE_STRING, CREATOR, OWNER, ISVERIFIED, notes);
		Command command1 = new Command(COMMAND_VALUE_BACKLOG, null, "Moving to Backlog");
		task1.update(command1);
		assertEquals(BACKLOG_STATE, task1.getStateName());
		
		//Invalid Transition
		Task task2 = new Task(TASKID, REJECTED_STATE, TITLE, TYPE_STRING, CREATOR, OWNER, ISVERIFIED, notes);
		Command command2 = new Command(COMMAND_VALUE_COMPLETE, null, "Double Basket Shopping Carts are cool!");
		Exception e1 = assertThrows(UnsupportedOperationException.class,
				() -> task2.update(command2));
		assertEquals("Invalid transition.", e1.getMessage());
		
	}
}
