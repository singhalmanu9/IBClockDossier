package essentials;

import buttons.Button;
import clocks.Clock;

/**
 * The class in the Rubik's Clock Dossier Program that does the 
 * algorithm to solve the Clock. This class is essential to the 
 * proper function of the program.
 * @author Abhimanyu S.
 * @see TheClock, Turner, Clock
 */
public class Logic 
{
	// requires a TheClock object
	private TheClock myClock;
	
	public Logic(TheClock clock) // constructor
	{
		myClock = clock;
	}

	/**
	 * The primary method that is called to solve the clock.
	 * Refers to the solveFirstCross() and solveSecondSide() methods below.
	 * @return - a String that contains all the instructions to solve the clock.
	 */
	public String solveClock()
	{
		// instantiates the output String
		String out = "";
		
		// sets the initial boolean solved state
		boolean isSolved = myClock.isSolved();
		// instantiates a counter to terminate
		int terminalTries = 0;
		
		// while the clock is not solved,
		while(!isSolved)
		{
			// add the instructions for the first cross
			// and solve the first cross
			out += solveFirstCross();
			myClock.flip(); // flip the clock over for the second side
			out += "\nTurn the clock over.\n"; // instructions for a user
			
			// add the instructions to finish off the clock
			// and solve it simultaneously.
			out += "\n" + solveSecondSide(); 
			
			// check to see if the clock has been solved
			isSolved = myClock.isSolved();
			
			// if it's been too long, terminate and tell the user that
			// the clock was unsolvable
			if(terminalTries > 3)
			{
				System.out.println("Clock could not be solved.");
				return "";
			}
			terminalTries ++;
		}
		
		// if it gets this far, the program will confirm that the clock was solved
		System.out.println("Clock was solved and instructions to solve were written to the file.");
		return out;
	}
	
	/**
	 * The first step in solving the cross, this method lines the
	 * 4 cross clocks to the center clock one-by-one. The method pushes a 
	 * button to turn the center clock to line up with another cross clock.
	 * At the end of the method, all clocks are turned to 12.
	 * @return - the String of instructions that tell the user how to solve the first cross.
	 */
	public String solveFirstCross()
	{
		// initialize the output string
		String output = "";
		
		// set the cross clocks and the center clock for easy access
		Clock center = myClock.whichOneIsUp().clockAt(3, 3);
		Clock left = myClock.whichOneIsUp().clockAt(1, 3);
		Clock right = myClock.whichOneIsUp().clockAt(5, 3);
		Clock up = myClock.whichOneIsUp().clockAt(3, 1);
		Clock down = myClock.whichOneIsUp().clockAt(3, 5);
		
		// set the 4 buttons for easy access
		Button upLeft = myClock.whichOneIsUp().buttonAt(2, 2);
		Button upRight = myClock.whichOneIsUp().buttonAt(4, 2);
		Button downLeft = myClock.whichOneIsUp().buttonAt(2, 4);
		Button downRight = myClock.whichOneIsUp().buttonAt(4, 4);
		
		// pushes the buttons down one by one
		for(Button b : myClock.whichOneIsUp().buttons)
		{
			if(b.isUp)
				myClock.push(b);
		}
		
		// tells the user to do so as well
		output += "Push all the buttons down.";
		
		// checks for a special case of all cross clocks are pointing the same direction
		// called from the TheClock class
		if(myClock.isCrossFinished(myClock.whichOneIsUp()))
		{
			// pushes all the buttons up
			myClock.push(upLeft);
			myClock.push(upRight);
			myClock.push(downLeft);
			myClock.push(downRight);
			output += "\nPush them all back up.";
			
			// turns the cross clocks to 12
			output += "\nTurn all the clocks " + (12 - center.pointsTo) + " times.";
			myClock.turn(12 - center.pointsTo, 0);
		}
		
		else // the more general case...
		{
			// pushes the lower left button and turns 
			// the center clock to match the up cross clock
			// Tells the user to do so as well.
			myClock.push(downLeft);
			if(up.pointsTo - center.pointsTo != 0)
			{
				output += "\nPush the lower left button up and turn the lower left turner " + (up.pointsTo - center.pointsTo) + " times.";
				output += "\nThen push it back down.";
			}
			myClock.turn(up.pointsTo - center.pointsTo, 2);
			myClock.push(downLeft);
			
			// same pattern as above: upper left button and right cross clock
			myClock.push(upLeft);
			if(right.pointsTo - center.pointsTo != 0)
			{
				output += "\nPush the upper left button up and turn the upper left turner " + (right.pointsTo - center.pointsTo) + " times.";
				output += "\nThen push it back down.";
			}
			myClock.turn(right.pointsTo - center.pointsTo, 0);
			myClock.push(upLeft);
			
			// upper right button and left cross clock
			// but the upper right should NOT be pushed back
			myClock.push(upRight);
			if(left.pointsTo - center.pointsTo != 0)
			{
				output += "\nPush the upper right button up and turn the upper right turner " + (left.pointsTo - center.pointsTo) + " times.";
				output += "\nBut DON'T push it back down!";
			}
			myClock.turn(left.pointsTo - center.pointsTo, 1);
			
			// upper right AND upper left to finish the down cross clock
			myClock.push(upLeft);
			if(down.pointsTo - center.pointsTo != 0)
			{
				output += "\nPush the upper left button up and turn the upper left turner " + (down.pointsTo - center.pointsTo) + " times.";
				output += "\nBut DON'T push it back down!";
			}
			myClock.turn(down.pointsTo - center.pointsTo, 0);
			
			// push up the remaining buttons
			// and move all clocks to 12
			myClock.push(downRight);
			myClock.push(downLeft);
			if(12 - center.pointsTo != 0)
				output += "\nPush all the buttons up and turn any turner " + (12 - center.pointsTo) + " times.";
			myClock.turn(12 - center.pointsTo, 0);
		}
		
		return output;
	}
	
	/**
	 * The second part of the solving algorithm, this method
	 * calls the solveFirstCross() method to do the first part, then 
	 * systematically finishes the rest of the clock. The last part pushes
	 * all but the corner button we want to solve, then match it up. Doing this
	 * for all 4 corners will resolve in a solved Clock.
	 * @return - the String of instructions to finish the Clock off
	 */
	public String solveSecondSide()
	{
		// initializes the output String
		String output = "";
		
		// Defines Clocks for quick access
		Clock hiLeft = myClock.whichOneIsUp().clockAt(1, 1);
		Clock hiRight = myClock.whichOneIsUp().clockAt(5, 1);
		Clock loLeft = myClock.whichOneIsUp().clockAt(1, 5);
		Clock loRight = myClock.whichOneIsUp().clockAt(5, 5);
		Clock center = myClock.whichOneIsUp().clockAt(3, 3);
		
		// Defines buttons for easy access
		Button upLeft = myClock.whichOneIsUp().buttonAt(2, 2);
		Button upRight = myClock.whichOneIsUp().buttonAt(4, 2);
		Button downLeft = myClock.whichOneIsUp().buttonAt(2, 4);
		Button downRight = myClock.whichOneIsUp().buttonAt(4, 4);
		
		// runs the solveFirstCross to finish first part
		// the String it returns in incorporated in this method's
		// output String
		output += solveFirstCross();
		
		// pushes all the buttons up one-by-one
		for(Button b : myClock.whichOneIsUp().buttons)
		{
			if(!b.isUp)
				myClock.push(b);
		}
		// tells the user to do so as well
		output += "\n\nPush all the buttons up.";
		
		// Pushed the lower left button down and turned the OTHER clocks
		// to match the lower left corner clock
		// Tells the user to do so as well
		myClock.push(downLeft);
		if(loLeft.pointsTo - center.pointsTo != 0)
		{
			output += "\nPush the lower left button down and turn the upper left turner " + (loLeft.pointsTo - center.pointsTo) + " times.";
			output += "\nPush it back up.";
		}
		myClock.turn(loLeft.pointsTo - center.pointsTo, 1);
		myClock.push(downLeft);
		
		// Similar to above: upper left button and upper left corner clock
		myClock.push(upLeft);
		if(hiLeft.pointsTo - center.pointsTo != 0)
		{
			output += "\nPush the upper left button down and turn the upper right turner " + (hiLeft.pointsTo - center.pointsTo) + " times.";
			output += "\nPush it back up.";
		}
		myClock.turn(hiLeft.pointsTo - center.pointsTo, 3);
		myClock.push(upLeft);
		
		// upper right button and upper right corner clock
		myClock.push(upRight);
		if(hiRight.pointsTo - center.pointsTo != 0)
		{
			output += "\nPush the upper right button down and turn the upper left turner " + (hiRight.pointsTo - center.pointsTo) + " times.";
			output += "\nPush it back up.";
		}
		myClock.turn(hiRight.pointsTo - center.pointsTo, 0);
		myClock.push(upRight);
		
		// lower right button and lower right clock
		myClock.push(downRight);
		if(loRight.pointsTo - center.pointsTo != 0)
		{
			output += "\nPush the lower right button down and turn the upper left turner " + (loRight.pointsTo - center.pointsTo) + " times.";
			output += "\nPush it back up.";
		}
		myClock.turn(loRight.pointsTo - center.pointsTo, 0);
		myClock.push(downRight);

		// THE LAST STEP!!!
		// Finishes the clock by turning all clocks to 12
		if(12 - center.pointsTo != 0)
			output += "\nTurn the upper left turner " + (12 - center.pointsTo) + " times.";
		myClock.turn(12 - center.pointsTo, 0);
		
		return output;
	}
}
