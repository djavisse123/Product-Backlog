# Overview
The Product Backlog project allows for the user to manage different types of products that are in development and assign different tasks to the product to help with its completion. Each task has its own state to show how far along the task has progressed and also comes with notes for the task and who the owner of the task is. The different states in each task are represented by a Finite State Machine and can only progress in the order of the designed Finite State Machine.
# Usage
This project is written in Java. To run this program you need to have Java installed.

Follow these steps to run the implementation:

1. Either click on the jar file or open command prompt/git bash and type  `java -jar ProductBacklog.jar `

### Creating and using Products: 

When first opening the program, there will be no products. If you want to create a new product, then click the **Add Product** button and type the name of the product. Once created, you can click the **Edit Product** button if you want to change the name or click the **Remove Product** button if you want to delete the product.

### Adding Tasks to a Product:
To add a task to a product, click the **Add Task** button and then add information to the task including the title, task type, creator, and task notes. Each newly added task starts in the Backlog state. If you click the **Edit Task** button then you can add new notes to the task and change the state of the task. If you want to delete the task, then you can click the **Delete Task** button and this will then remove the task from the current product.

### File:
When clicking on File, there are a few options that can be used. The first option is **Load** which allows you to import a file containing products and tasks for each product. The next option is **Save** which allows you to save all products and tasks you created to a file. The next option is **Clear** which clears all products and tasks and returns the program to its initial state. The last option is **Quit** which just exits the program.

# Design Requirements

### Programming Language
The implementation of this project is required to be created in Java.

### UML Diagram
The overall design of this project contains 6 packages with a various amount of classes in each package to make the Product Backlog project work. The project also features the MVC Design Pattern where the non-UI classes are part of the Model View Controller while the UI classes are part of the View Controller. The Model View Controller handles the information processing of the Product Backlog program while View Controller also for the user to interact with the Model View Controller components. The UML below shows all the different classes and each package they belong to. Some classes may contain extra private methods that are not present on the UML. 


![Product BacklogUML](https://user-images.githubusercontent.com/112775148/189508189-31256121-1182-430c-b44f-6f4de6021b1f.png)


### View Controller
**UI**: This package contains the ***ProductManagerGUI*** class. This class is the view controller of the MVC pattern and is designed for users to create products, delete products, edit products, add tasks, delete tasks, and edit tasks. **NOTE: The entirety of this class was provided.**
### Model View Controller
**Backlog**: This package contains the ***BacklogManager*** class. This class defines all of the behaviors of the Manager, including saving to a file, loading from a file, deleting tasks, adding tasks, editing tasks, deleting products, adding products, and editing products. The GUI will delegate the method calls to this class, which delegates the method calls to other classes.

**Product**: This package contains the ***Product*** class. This class represents a product for any company or organization. Each product has a set of tasks and notes.

**IO**: This package contains both the ***ProductsReader*** and ***ProductWriter*** classes which are both used for I/O operations for the Product Backlog program.
* The ***ProductsReader*** class reads a specific text file format containing a list of products, tasks, and notes. If the file format does not match the correct format, then the file contents are not loaded in.
* The ***ProductsWriter*** class saves the list of products, tasks, and notes to a file.

**Command**: This package contains the ***Command*** class and the ***CommandValue*** enumeration for the ***Command*** class. These two handle the commands that a user can issue when they want to update a task.
 * The ***CommandValue*** enumeration defines the commands that a user may input for a task in the progression of the task cycle through a finite state machine.
* The ***Command*** class provides information on who issued the command, what command is being used, and the note following the command. This class is used to drive the task through the finite state machine.

**Task**: This package contains the ***Task***, the ***Type*** enumeration for Task, and all the inner classes that define different states a task can transition into.
* The ***Task*** class defines the properties of a task and all of the possible task states that a task can be in. Each task is assigned to a product and is uniquely identified for an individual product.
* The ***TaskState*** interface defines the behaviors for each of the six task states.
* The ***BacklogState*** class is the state where a new task is created and waits in the backlog until a user becomes the owner of that task.
* The ***OwnedState*** class is the state where the new task has been taken by an owner that will fulfill the task.
* The ***VerifyingState*** class is the state where the task needs to be verified by another member of an organization or company to ensure that the task is successfully completed.
* The ***ProcessingState*** class is the state where the task is in the process of being completed by the owner.
* The ***DoneState*** class is the state where the task has been confirmed to be completed.
* The ***RejectedState*** class is the state where the task has been rejected.
* The ***Type*** enumeration defines the task types possible. Each task is given one of four types, including Technical Work, Bug, Feature, and Knowledge Acquisition.


### Additional Information
This project was implemented individually. No libraries from the Java.util library were used except for reading from and writing to files and throwing exceptions. The only data structures that can be used for this project are ArrayLists. All diagrams and figures used in this README belong to the NCSU CSC Department.
# Implementation
The main goal of this project was to use an FSM and translate the different state transitions into code when working with the Task class. To do this I had to create private inner classes of each of the states that would be featured in the Task class. Each of these private classes had methods to identify which state the Task was in and also had a method that would be used to update the state of the Task and transition it to a new state. The FSM that we used only allowed for certain state transitions when in a certain state. For example, when the Task in the Backlog state it can only go to the Owned state or the Rejected State. So when implementing the different state transitions I had to make sure that if the Task tried to make an illegal transition then an exception would be thrown and the transition would be prevented. The command class was helpful with managing the transitions as anytime the Task would want to update states the correct command value would have to be used or else the transition would be invalid. This project also gave me a chance to work with GUIs. While the GUI was entirely provided, the GUI is dependent on BacklogManager class to function. This class is where I had to implement all the functionality used by GUI like adding products, adding tasks, deleting products, editing products, etc. Once these functionalities were implemented the GUI was able to function.
