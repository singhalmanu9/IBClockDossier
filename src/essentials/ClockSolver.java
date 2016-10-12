package essentials;

import java.util.Random;

import sides.Side;
import clocks.Clock;

/**
 * This class is the runner class for the Rubik's Clock Dossier Project.
 * It contains the code to solve the Clock, referring to the Logic class.
 * It also contains code to write the messages to a file.
 * It requires a TheClock object and stores it for use in the Logic class.
 * It is used by the GUI class to call the clock solver program to work.
 * @author Abhimanyu S.
 * @see Writer, Logic, TheClock, GUI
 */
public class ClockSolver 
{
	// instance variables,
	// necessary for the program to function
	public TheClock clock;
	private Logic algo;
	
	public ClockSolver(TheClock c, Logic log) // constructor
	{
		clock = c;
		algo = log;
	}
	
	/**
	 * The over arching method to solve the Clock.
	 * This is called by the writeToFile() method.
	 * Refer to the Logic class for inner workings.
	 * @return - the String containing the steps to
	 * solve the clock.
	 */
	public String solveClock()
	{
		return algo.solveClock();
	}
	
	/**
	 * The method that creates the file and writes in the steps
	 * to solve the clock.
	 * @param name - the name of the file to be created/written to.
	 */
	public void writeToFile(String name)
	{
		// creates an Writer object and the txt file to write to
		Writer writer = new Writer(name);
		
		// Instantiates the message string
		String message = "";
		
		// Adds the disclaimer and instructions on how to use the instructions
		message += "This is the Rubik's Clock Solver. Below is the scrambled clock, then the steps"
				+ " necessary to solve the clock. Please note that a positive number indicates a "
				+ "clockwise movement and vice versa. \n";
		
		// first prints the scrambled clock
		for(Side s : clock.twoSides)
		{
			for(int i = 0; i < 9; i += 3)
			{
				message += "\n" + s.clocks[i].pointsTo + " " + s.clocks[i + 1].pointsTo +
						" " + s.clocks[i + 2].pointsTo; // prints 3 clocks in a row
			}
			
			message += "\n"; // used to separate the two sides of the clock
		}
		
		// solves the clock, and adds the instructions to the message
		message += "\n" + this.solveClock() + "\n";
		
		// finally, add the solved clock (hopefully, all 12's)
		for(Side s : clock.twoSides)
		{
			for(int i = 0; i < 9; i += 3)
			{
				message += "\n" + s.clocks[i].pointsTo + " " + s.clocks[i + 1].pointsTo +
						" " + s.clocks[i + 2].pointsTo;
			}
			
			message += "\n";
		}
		
		// puts the message into the file.
		writer.println(message);
		// closes the file to ensure that the message has been written
		writer.close();
	}
}
