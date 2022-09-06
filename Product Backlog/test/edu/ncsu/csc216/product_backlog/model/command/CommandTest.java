package edu.ncsu.csc216.product_backlog.model.command;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


import edu.ncsu.csc216.product_backlog.model.command.Command.CommandValue;

/**
 * Tests the Command class.
 * These tests are to see which values would be valid or invalid for a command.
 * @author Daniel Avisse
 */
class CommandTest {
	
	/** Command Value that sends the task to the Owned state **/
	private static final CommandValue COMMAND_VALUE = CommandValue.CLAIM;
	
	/** Note for the command **/
	private static final String NOTE = "Double Basket allows for the shopper to hold more items.";
	
	/** Name of the owner of the task when it's claimed **/
	private static final String OWNER = "Joe";

	/**
	 * Tests the command constructor for valid construction.
	 */
	@Test
	public void testCommand() {
		//Valid Command
		Command command = assertDoesNotThrow(
				() -> new Command(COMMAND_VALUE, OWNER, NOTE),
				"Should not throw exception");
		assertEquals(COMMAND_VALUE, command.getCommand());
		assertEquals(NOTE, command.getNoteText());
		assertEquals(OWNER, command.getOwner());
		
		//Invalid CommandValue
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Command(null, OWNER, NOTE));
		assertEquals("Invalid command.", e1.getMessage());
		
		//Invalid Owner
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Command(COMMAND_VALUE, "", NOTE));
		assertEquals("Invalid command.", e2.getMessage());
		
		//Invalid Note
		Exception e3 = assertThrows(IllegalArgumentException.class,
				() -> new Command(COMMAND_VALUE, OWNER, ""));
		assertEquals("Invalid command.", e3.getMessage());
	}

}
