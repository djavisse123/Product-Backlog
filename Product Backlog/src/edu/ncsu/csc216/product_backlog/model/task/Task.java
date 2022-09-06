package edu.ncsu.csc216.product_backlog.model.task;

import java.util.ArrayList;



import edu.ncsu.csc216.product_backlog.model.command.Command;
import edu.ncsu.csc216.product_backlog.model.command.Command.CommandValue;

/**
 * Class for creating tasks and putting them inside products that they associate with.
 * This class also contains 6 private inner classes and an interface which are used for transitioning between states.
 * @author Daniel Avisse
 *
 */
public class Task {
	
	/** Id of the task **/
	private int taskId; 
	
	/** Type for the task **/
	private Type type;
	
	/** The current state of the task **/
	private TaskState currentState;
	
	/** Title for the task **/
	private String title;
	
	/** Creator of the task **/
	private String creator;
	
	/** Owner of the task **/
	private String owner; 
	
	/** Boolean that tells if a task as passed the verified state and is complete **/
	private boolean isVerified; 
	
	/** ArrayList that contains all the notes made for one task **/
	private ArrayList<String> notes;
	
	/** Creates a final instance of the inner class BacklogState **/
	private final TaskState backlogState = new BacklogState();
	
	/** Creates a final instance of the inner class OwnedState **/
	private final TaskState ownedState = new OwnedState();
	
	/** Creates a final instance of the inner class VerifyingState **/
	private final TaskState verifyingState = new VerifyingState();
	
	/** Creates a final instance of the inner class ProcessingState **/
	private final TaskState processingState = new ProcessingState();

	/** Creates a final instance of the inner class DoneState **/
	private final TaskState doneState = new DoneState();
	
	/** Creates a final instance of the inner class RejectedState **/
	private final TaskState rejectedState = new RejectedState();
	
	/** The name of the Backlog state **/
	public static final String BACKLOG_NAME = "Backlog";
	
	/** The name of the Owned state **/
	public static final String OWNED_NAME = "Owned";
	
	/** The name of the Processing state **/
	public static final String PROCESSING_NAME = "Processing";
	
	/** The name of the Verifying state **/
	public static final String VERIFYING_NAME = "Verifying";
	
	/** The name of the Done state **/
	public static final String DONE_NAME = "Done";
	
	/** The name of the Rejected state **/
	public static final String REJECTED_NAME = "Rejected";
	
	/** The name of the Feature type **/
	public static final String FEATURE_NAME = "Feature";
	
	/** The name of the Bug type **/
	public static final String BUG_NAME = "Bug";
	
	/** The name of the Technical_Work type **/
	public static final String TECHNICAL_WORK_NAME = "Technical Work";
	
	/** The name of the Knowledge Acquisition type **/
	public static final String KNOWLEDGE_ACQUISITION_NAME = "Knowledge Acquisition";
	
	/** The short-hand for the Feature type **/
	public static final String T_FEATURE = "F";
	
	/** The short-hand for the Bug type **/
	public static final String T_BUG = "B";
	
	/** The short-hand for the Technical_Work type **/
	public static final String T_TECHNICAL_WORK = "TW";
	
	/** The short-hand for the Knowledge Acquisition type **/
	public static final String T_KNOWLEDGE_ACQUISITION = "KA";

	/** String used when a task has no owner **/
	public static final String UNOWNED = "unowned";
	
	/** 
	 * Constructor for task using the id, title, type, creater, and note
	 * @param taskId Id of the task
	 * @param title Title of the task
	 * @param type Type for the task
	 * @param creator Creator of the task
	 * @param note Note for the task
	 * @throws IllegalArgumentException if any of the fields are invalid.
	 */
	public Task (int taskId, String title, Type type, String creator, String note) {
		setTaskId(taskId);
		setState(BACKLOG_NAME);
		setTitle(title);
		setType(type);
		setCreator(creator);
		setOwner(UNOWNED);
		setVerified("false");
		notes = new ArrayList<String>();
		addNoteToList(note);
		setNotes(notes);
	}
	
	/**
	 * Constructor for task using all the fields
	 * @param taskId Id of the task
	 * @param state State of the task
	 * @param title Title of the task
	 * @param type Type for the task
	 * @param creator Creator of the task
	 * @param owner Owner of the task
	 * @param verified True or false for it task is verified
	 * @param notes Note for the task
	 * @throws IllegalArgumentException if any of the fields are invalid
	 */
	public Task (int taskId, String state, String title, String type, String creator, String owner, String verified, ArrayList<String> notes) {
		setTaskId(taskId);
		setState(state);
		setTitle(title);
		setTypeFromString(type);
		setCreator(creator);
		setOwner(owner);
		setVerified(verified);
		setNotes(notes);
	}

	/**
	 * Sets the id of the task
	 * @param taskId Id of the task
	 * @throws IllegalArgumentException if taskId is invalid
	 */
	private void setTaskId(int taskId) {
		if (taskId <= 0) {
			throw new IllegalArgumentException("Invalid task information.");
		}
		this.taskId = taskId;
	}

	/**
	 * Sets the title for the task
	 * @param title Title of the task
	 * @throws IllegalArgumentException if title is invalid
	 */
	private void setTitle(String title) {
		if (title == null || "".equals(title)) {
			throw new IllegalArgumentException("Invalid task information.");
		}
		this.title = title;
	}
	
	/**
	 * Sets the type for the task
	 * @param type Type for the task
	 * @throws IllegalArgumentException if the type is invalid
	 */
	private void setType(Type type) {
		if (type == null) {
			throw new IllegalArgumentException("Invalid task information.");
		}
		this.type = type;
	}

	/**
	 * Sets the creator of the task
	 * @param creator Creator of the task
	 * @throws IllegalArgumentException if the creator is invalid
	 */
	private void setCreator(String creator) {
		if (creator == null || "".equals(creator)) {
			throw new IllegalArgumentException("Invalid task information.");
		}
		this.creator = creator;
	}

	/**
	 * Sets the owner of the task
	 * @param owner Owner of the task
	 * @throws IllegalArgumentException if the owner is invalid
	 */
	private void setOwner(String owner) {
		if (owner == null || "".equals(owner)) {
			throw new IllegalArgumentException("Invalid task information.");
		}
		else if (currentState == backlogState && !UNOWNED.equals(owner)) {
			throw new IllegalArgumentException("Invalid task information.");
		}
		else if (currentState == rejectedState && !UNOWNED.equals(owner)) {
			throw new IllegalArgumentException("Invalid task information.");
		}
		else if (currentState == ownedState && UNOWNED.equals(owner)) {
			throw new IllegalArgumentException("Invalid task information.");
		}
		else if (currentState == verifyingState && UNOWNED.equals(owner)) {
			throw new IllegalArgumentException("Invalid task information.");
		}
		else if (currentState == processingState && UNOWNED.equals(owner)) {
			throw new IllegalArgumentException("Invalid task information.");
		}
		else if (currentState == doneState && UNOWNED.equals(owner)) {
			throw new IllegalArgumentException("Invalid task information.");
		}
		this.owner = owner;
	}

	/**
	 * Sets for if the task has been verified of not
	 * @param isVerified True or false for it task is verified
	 * @throws IllegalArgumentException if isVerified is invalid
	 */
	private void setVerified(String isVerified) {
		if (isVerified == null || "".equals(isVerified)) {
			throw new IllegalArgumentException("Invalid task information.");
		}
		else if (currentState == doneState && !Boolean.valueOf(isVerified) && type != Type.KNOWLEDGE_ACQUISITION) {
			throw new IllegalArgumentException("Invalid task information.");
		}
		else if (currentState == doneState && Boolean.valueOf(isVerified) && type == Type.KNOWLEDGE_ACQUISITION) {
			throw new IllegalArgumentException("Invalid task information.");
		}
		else if (Boolean.valueOf(isVerified)) {
			this.isVerified = true;
		}
		else if (!Boolean.valueOf(isVerified)) {
			if ("false".equals(isVerified.toLowerCase())) {
				this.isVerified = false;
			}
			else {
				throw new IllegalArgumentException("Invalid task information.");
			}
		}
		
		
	}

	/**
	 * Sets the notes of the task
	 * @param notes Notes for the task
	 * @throws IllegalArgumentException if notes are invalid
	 */
	private void setNotes(ArrayList<String> notes) {
		if (notes == null || "".equals(notes.get(0)))  {
			throw new IllegalArgumentException("Invalid task information.");
		}
		this.notes = notes;
	}
	
	/**
	 * Adds note to the ArrayList of notes for the task and also checks to see if the note is not empty
	 * @param note Notes for the task
	 * @return A value for if the notes are added
	 * @throws IllegalArgumentException if note is invalid.
	 */
	public int addNoteToList(String note) {
		if (note == null || "".equals(note))  {
			throw new IllegalArgumentException("Invalid task information.");
		}
		else if (note.substring(0, 1).equals("[")){
			notes.add(note);
			return notes.size() - 1;
		}
		notes.add("[" + currentState.getStateName() + "] " + note);
		return notes.size() - 1;
	}
	
	
	/**
	 * Gets the id of the task
	 * @return Id of task
	 */
	public int getTaskId() {
		return taskId;
	}
	
	/**
	 * Gets the name of the current state the task is in.
	 * @return Name of the state
	 */
	public String getStateName() {
		return currentState.getStateName();
	}
	
	/**
	 * Sets the state of the task
	 * @param state State of the task
	 * @throws IllegalArgumentException if the state is invalid
	 */
	private void setState(String state) {
		if (state == null || "".equals(state)) {
			throw new IllegalArgumentException("Invalid task information.");
		}
		if (state.equals(BACKLOG_NAME)) {
			currentState = backlogState;
		}
		else if (state.equals(OWNED_NAME)) {
			currentState = ownedState;
		}
		else if (state.equals(PROCESSING_NAME)) {
			currentState = processingState;
		}
		else if (state.equals(VERIFYING_NAME)) {
			currentState = verifyingState;
		}
		else if (state.equals(DONE_NAME)) {
			currentState = doneState;
		}
		else if (state.equals(REJECTED_NAME)) {
			currentState = rejectedState;
		}
		else {
			throw new IllegalArgumentException("Invalid task information.");
		}
		
	}
	
	/**
	 * Sets the type for task
	 * @param type Type for the task
	 * @throws IllegalArgumentException if the type is invalid
	 */
	private void setTypeFromString(String type) {
		if (type == null || "".equals(type)) {
			throw new IllegalArgumentException("Invalid task information.");
		}
		if (type.equals(T_FEATURE)) {
			this.type = Type.FEATURE;
		}
		else if (type.equals(T_BUG)) {
			this.type = Type.BUG;
		}
		else if (type.equals(T_KNOWLEDGE_ACQUISITION)) {
			this.type = Type.KNOWLEDGE_ACQUISITION;
		}
		else if (type.equals(T_TECHNICAL_WORK)) {
			this.type = Type.TECHNICAL_WORK;
		}
		else {
			throw new IllegalArgumentException("Invalid task information.");
		}
	}
	
	/**
	 * Gets the task's type
	 * @return Type for the task
	 */
	public Type getType() {
		return type;
	}
	
	/**
	 * Gets the short name for the current Type in task.
	 * @return Short-hand type name for task
	 */
	public String getTypeShortName() {
		if (type == Type.FEATURE) {
			return T_FEATURE;
		}
		else if (type == Type.BUG) {
			return T_BUG;
		}
		else if (type == Type.KNOWLEDGE_ACQUISITION) {
			return T_KNOWLEDGE_ACQUISITION;
		}
		else if (type == Type.TECHNICAL_WORK) {
			return T_TECHNICAL_WORK;
		}
		else {
			return null;
		}
	}
	
	/**
	 * Gets the full name of the current Type in task.
	 * @return Full name type name for task
	 */
	public String getTypeLongName() {
		if (type == Type.FEATURE) {
			return FEATURE_NAME;
		}
		else if (type == Type.BUG) {
			return BUG_NAME;
		}
		else if (type == Type.KNOWLEDGE_ACQUISITION) {
			return KNOWLEDGE_ACQUISITION_NAME;
		}
		else if (type == Type.TECHNICAL_WORK) {
			return TECHNICAL_WORK_NAME;
		}
		else {
			return null;
		}
	}
	
	/**
	 * Gets the owner of the task
	 * @return Owner of the task
	 */
	public String getOwner() {
		return owner;
	}
	
	/**
	 * Gets the title of the task
	 * @return Title of the task
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Gets the creator of the task
	 * @return Creator of the task
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * Gets the true or false for if the task was verified
	 * @return True or false for verified task
	 */
	public boolean isVerified() {
		return isVerified;
	}

	/**
	 * Gets the ArrayList of notes for task
	 * @return Notes for the task
	 */
	public ArrayList<String> getNotes() {
		return notes;
	}
	
	/**
	 * Gets all of the notes of task in a single string
	 * @return Notes for the task
	 */
	public String getNotesList() {
		String s = "";
		for (int i = 0; i < notes.size(); i++) {
			s += "\n- " + notes.get(i);
		}
		return s;
	}
	
	/**
	 * Returns a comma separated value String of all task fields
	 * @return String representation of task
	 */
	@Override
	public String toString() {
		return taskId + "," + getStateName() + "," + title + "," + getTypeShortName() + "," + creator + "," + owner + "," + String.valueOf(isVerified)
		 +  getNotesList();
	}

	/**
	 * Updates the command value when switching states
	 * @param c Command value
	 * @throws UnsupportedOperationException if the command value is invalid in a certain state
	 */
	public void update(Command c) {
		currentState.updateState(c);
	}
	
	/**
	 * Gets all of the notes of task in an array
	 * @return Notes for the task
	 */
	public String[] getNotesArray() {
		String[] notesArray = new String[notes.size()];
		notesArray = notes.toArray(notesArray);
		return notesArray;
	}
	
	/**
	 * Interface for states in the Task State Pattern.  All 
	 * concrete task states must implement the TaskState interface.
	 * 
	 * @author Dr. Sarah Heckman (sarah_heckman@ncsu.edu) 
	 */
	private interface TaskState {
		
		/**
		 * Update the Task based on the given Command
		 * An UnsupportedOperationException is thrown if the Command is not a
		 * is not a valid action for the given state.  
		 * @param c Command describing the action that will update the Task
		 * state.
		 * @throws UnsupportedOperationException if the Command is not a valid action
		 * for the given state.
		 */
		void updateState(Command c);
		
		/**
		 * Returns the name of the current state as a String.
		 * @return the name of the current state as a String.
		 */
		String getStateName();
	
	}
	
	/**
	 * Inner class that updates the state of task when it's in the Backlog State
	 * @author Daniel Avisse
	 */
	private class BacklogState implements TaskState {
		
		/**
		 * Constructor that creates a new BacklogState
		 */
		private BacklogState() {
			
		}
		
		/**
		 * Method that uses the command value to transition to a new state.
		 * @param c Command value
		 */
		public void updateState(Command c) {
			if(c.getCommand() == CommandValue.CLAIM) {
				currentState = ownedState;
				setOwner(c.getOwner());
				addNoteToList(c.getNoteText());
			}
			else if(c.getCommand() == CommandValue.REJECT) {
				currentState = rejectedState;
				addNoteToList(c.getNoteText());
			}
			else {
				throw new UnsupportedOperationException("Invalid transition.");
			}
		}
		
		/**
		 * Gets the name of the BacklogState
		 * @return Name of BacklogState
		 */
		public String getStateName() {
			return BACKLOG_NAME;
		}
	}
	
	/**
	 * Inner class that updates the state of task when it's in the Owned State
	 * @author Daniel Avisse
	 */
	private class OwnedState implements TaskState {
		
		/**
		 * Constructor that creates a new OwnedState
		 */
		private OwnedState() {
			
		}
		
		/**
		 * Method that uses the command value to transition to a new state.
		 * @param c Command value
		 */
		public void updateState(Command c) {
			if(c.getCommand() == CommandValue.REJECT) {
				currentState = rejectedState;
				setOwner(UNOWNED);
				addNoteToList(c.getNoteText());
			}
			else if(c.getCommand() == CommandValue.PROCESS) {
				currentState = processingState;
				addNoteToList(c.getNoteText());
			}
			else if(c.getCommand() == CommandValue.BACKLOG) {
				currentState = backlogState;
				setOwner(UNOWNED);
				addNoteToList(c.getNoteText());
			}
			else {
				throw new UnsupportedOperationException("Invalid transition.");
			}
		}
		
		/**
		 * Gets the name of the OwnedState
		 * @return Name of OwnedState
		 */
		public String getStateName() {
			return OWNED_NAME;
		}
	}
	
	/**
	 * Inner class that updates the state of task when it's in the Verifying State
	 * @author Daniel Avisse
	 */
	private class VerifyingState implements TaskState {
		
		/**
		 * Constructor that creates a new VerifyingState
		 */
		private VerifyingState() {
			
		}
		
		/**
		 * Method that uses the command value to transition to a new state.
		 * @param c Command value
		 */
		public void updateState(Command c) {
			if(c.getCommand() == CommandValue.COMPLETE) {
				currentState = doneState;
				addNoteToList(c.getNoteText());
				setVerified("true");
			}
			else if(c.getCommand() == CommandValue.PROCESS) {
				currentState = processingState;
				addNoteToList(c.getNoteText());
			}
			else {
				throw new UnsupportedOperationException("Invalid transition.");
			}
		}
		
		/**
		 * Gets the name of the VerifyingState
		 * @return Name of VerifyingState
		 */
		public String getStateName() {
			return VERIFYING_NAME;
		}
	}
	
	/**
	 * Inner class that updates the state of task when it's in the Processing State
	 * @author Daniel Avisse
	 */
	private class ProcessingState implements TaskState {
		
		/**
		 * Constructor that creates a new ProcessingState
		 */
		private ProcessingState() {
			
		}
		
		/**
		 * Method that uses the command value to transition to a new state.
		 * @param c Command value
		 */
		public void updateState(Command c) {
			if(c.getCommand() == CommandValue.VERIFY && type != Type.KNOWLEDGE_ACQUISITION) {
				currentState = verifyingState;
				addNoteToList(c.getNoteText());
			}
			else if(c.getCommand() == CommandValue.PROCESS) {
				currentState = processingState;
				addNoteToList(c.getNoteText());
			}
			else if(c.getCommand() == CommandValue.BACKLOG) {
				currentState = backlogState;
				setOwner(UNOWNED);
				addNoteToList(c.getNoteText());
			}
			else if(c.getCommand() == CommandValue.COMPLETE && type == Type.KNOWLEDGE_ACQUISITION) {
				currentState = doneState;
				addNoteToList(c.getNoteText());
			}
			else {
				throw new UnsupportedOperationException("Invalid transition.");
			}
		}
		
		/**
		 * Gets the name of the ProcessingState
		 * @return Name of ProcessingState
		 */
		public String getStateName() {
			return PROCESSING_NAME;
		}
	}
	
	/**
	 * Inner class that updates the state of task when it's in the Done State
	 * @author Daniel Avisse
	 */
	private class DoneState implements TaskState {
		
		/**
		 * Constructor that creates a new DoneState
		 */
		private DoneState() {
			
		}
		
		/**
		 * Method that uses the command value to transition to a new state.
		 * @param c Command value
		 */
		public void updateState(Command c) {
			if(c.getCommand() == CommandValue.PROCESS) {
				currentState = processingState;
				setVerified("false");
				addNoteToList(c.getNoteText());
			}
			else if(c.getCommand() == CommandValue.BACKLOG) {
				currentState = backlogState;
				setOwner(UNOWNED);
				setVerified("false");
				addNoteToList(c.getNoteText());
			}
			else {
				throw new UnsupportedOperationException("Invalid transition.");
			}
		}
		
		/**
		 * Gets the name of the DoneState
		 * @return Name of DoneState
		 */
		public String getStateName() {
			return DONE_NAME;
		}
	}
	
	/**
	 * Inner class that updates the state of task when it's in the Rejected State
	 * @author Daniel Avisse
	 */
	private class RejectedState implements TaskState {
		
		/**
		 * Constructor that creates a new RejectedState
		 */
		private RejectedState() {
			
		}
		
		/**
		 * Method that uses the command value to transition to a new state.
		 * @param c Command value
		 */
		public void updateState(Command c) {
			if (c.getCommand() == CommandValue.BACKLOG) {
				currentState = backlogState;
				addNoteToList(c.getNoteText());
			}
			else {
				throw new UnsupportedOperationException("Invalid transition.");
			}
		}
		
		/**
		 * Gets the name of the RejectedState
		 * @return Name of RejectedState
		 */
		public String getStateName() {
			return REJECTED_NAME;
		}
	}
	
	/**
	 * An enumeration that holds of the value of different types
	 * @author Daniel Avisse
	 */
	public enum Type { FEATURE, BUG, TECHNICAL_WORK, KNOWLEDGE_ACQUISITION }

}
