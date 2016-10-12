package clocks;
import java.util.ArrayList;

import essentials.Location;

/**
 * This class is the Clock class, the base element
 * of the Rubik's Clock. It is a short class with very
 * important variables and methods that make the
 * Clock work. It is the superclass for LightClock
 * and DarkClock, so please refer here for documentation.
 * @author Abhimanyu S.
 * 
 */
public class Clock 
{
	// instance variables
	public int pointsTo; // the number the Clock is pointing to
	
	public boolean isActive; // if the clock would be turned by an
	// active turner
	public Location loc; // the Location of the Clock
	
	public Clock(int x, int y) // constructor
	{
		pointsTo = 12;
		isActive = false;
		loc = new Location(x, y);
	}

	/**
	 * This method is the basis of the whole
	 * Clock, and, arguably, is the most important
	 * method in the whole project. pointsTo is simply
	 * added to and adjusted if the 
	 * number is too high or too low.
	 * @param timesTurned - the number of times this clock
	 * should turn.
	 */
	public void turn(int timesTurned)
	{
		// increment pointsTo
		pointsTo += timesTurned;
		
		// adjust pointsTo if it is not within [1,12]
		if(pointsTo > 12)
			pointsTo = pointsTo % 12;
		if(pointsTo <= 0)
			pointsTo += 12;
	}
	/**
	 * This method is almost the same method as in 
	 * the other turnManyClocks(), except with
	 * Arrays instead of ArrayLists.
	 * @param clocks
	 * @param timesTurned
	 */
	public static void turnManyClocks(Clock[] clocks, int timesTurned)
	{
		for(Clock c : clocks)
		{
			c.turn(timesTurned);
		}
	}

	/**
	 * This method calls the turn() method over
	 * and over for through the ArrayList of Clocks.
	 * @param clocks - the ArrayList holding all the clocks
	 * that need to be turned.
	 * @param timesTurned - the times these Clocks should turn.
	 */
	public static void turnManyClocks(ArrayList<Clock> clocks, int timesTurned)
	{
		for(Clock c : clocks) // for each clock in clocks
		{
			// turn it timesTurned times
			c.turn(timesTurned);
		}
	}
}
