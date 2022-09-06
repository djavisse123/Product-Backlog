package edu.ncsu.csc216.product_backlog.model.command;

/**
 * Class that helps lead the transitions between states for Task and encapsulates the user's information.
 * @author Daniel Avisse
 *
 */
public class Command {
	
	/** Value of the command to evaluate which state the task will switch to **/
	private CommandValue c;
	
	/** Note of the task that is used to evaluate which state the task will switch to**/
	private String note;
	
	/** Name of the Owner of the task that to evaluate which state the task will switch to.**/
	private String owner;
	
	/** Error message that tells that user of an invalid command **/
	private static final String COMMAND_ERROR_MESSAGE = "Invalid command.";
	
	
	
	/** Constructor for Command with the command value, owner, and note.
	 * Uses these to determined which state the Task will transition to and if one of the values is null then an exception is thrown.
	 * @param c value of the Command that switches the state.
	 * @param owner name of the owner
	 * @param note note the contains the information for the task and state it's in.
	 * @throws IllegalArgumentException if command is invalid.
	 */
	public Command(CommandValue c, String owner, String note) {
		if (c == null || note == null || "".equals(note)) {
			throw new IllegalArgumentException(COMMAND_ERROR_MESSAGE);
		}
		if (c == CommandValue.CLAIM && (owner == null || "".equals(owner))) {
			throw new IllegalArgumentException(COMMAND_ERROR_MESSAGE);
		}
		this.note = note;
		this.owner = owner;
		this.c = c;
	}
	
	/**
	 * Gets the value of the of CommandValue which will be used to decided what state the task will transition to.
	 * @return CommandValue
	 */
	public CommandValue getCommand() {
		return c;
	}

	
	/**
	 * Gets the note that is used in Command
	 * @return the information contained in the note
	 */
	public String getNoteText() {
		return note;
	}


	/**
	 * Gets the name of the owner used in Command
	 * @return the name of the owner
	 */
	public String getOwner() {
		return owner;
	}


	/**
	 * An enumeration that holds of the value of the different states
	 * Each of these values are used to help with a transition from one State to another.
	 * For example, having the value of Process will transition the task to the Processing State.
	 * @author Daniel Avisse
	 *
	 */
	public enum CommandValue { BACKLOG, CLAIM, PROCESS, VERIFY, COMPLETE, REJECT }
	

}
