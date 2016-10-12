package turners;
import buttons.Button;
import clocks.Clock;
import essentials.Location;
import essentials.TheClock;

/**
 * The Turner class contains the code to move the clocks
 * in order to solve the clock. The LightTurner and DarkTurner
 * inherit all methods from this class. Refer back to this class
 * for the documentation for those methods.
 * 
 * @author Abhimanyu S.
 * @see TheClock, Clock
 */
public class Turner 
{
	// the location of the turner on a Side
	public Location loc;
	
	public Turner(int x, int y) // constructor
	{
		loc = new Location(x, y);
	}
	
	/**
	 * This method is the main methods for the 
	 * solver to work. The method turns all pertinent clocks
	 * in a fairly simple fashion.
	 * @param clock - the TheClock object that the turner
	 * is part of
	 * @param timesTurn - the number of times to turn the turner
	 */
	public void turn(TheClock clock, int timesTurn)
	{
		// no matter what, the nearest Clocks to the turner will
		// turn however much it is required to. The down side is negative
		// because it always turns the opposite amount, e.g. if the turner
		// is turned once, then the front clock will move one spot and
		// the back clock will move -1 spot.
		getNearestClock(clock.whichOneIsUp().clocks).turn(timesTurn);
		getNearestClock(clock.whichOneIsDown().clocks).turn(-timesTurn);
		
		// Special case: if there are no active clocks on the
		// front face, turn all the corners on the front face 
		// and all the clocks on the back face.
		// Note that the two turned above need
		// to be turned back, otherwise they will have moved twice
		if(clock.getActiveTopClocks().size() == 0)
		{
			clock.whichOneIsUp().clockAt(1, 1).turn(timesTurn);
			clock.whichOneIsUp().clockAt(1, 5).turn(timesTurn);
			clock.whichOneIsUp().clockAt(5, 1).turn(timesTurn);
			clock.whichOneIsUp().clockAt(5, 5).turn(timesTurn);
			getNearestClock(clock.whichOneIsUp().clocks).turn(-timesTurn);
			getNearestClock(clock.whichOneIsDown().clocks).turn(timesTurn);
			
			Clock.turnManyClocks(clock.whichOneIsDown().clocks, timesTurn);
		}
		else // otherwise (the more general case)
		{
			// if the nearest button is up,
			if(getNearestButton(clock.whichOneIsUp().buttons).isUp)
			{
				// turn all the active top clocks and all the bottom NOT active clocks
				Clock.turnManyClocks(clock.getActiveTopClocks(), timesTurn);
				Clock.turnManyClocks(clock.getNotDownClocks(), -timesTurn);
			}
			else // otherwise (if the nearest button is down)
			{
				// turn the NOT active top clocks and all the active bottom clocks
				Clock.turnManyClocks(clock.getNotTopClocks(), timesTurn);
				Clock.turnManyClocks(clock.getActiveDownClocks(), -timesTurn);
			}
			
			// put the two corners back, otherwise they will have moved twice
			getNearestClock(clock.whichOneIsUp().clocks).turn(-timesTurn);
			getNearestClock(clock.whichOneIsDown().clocks).turn(timesTurn);
		}
	}
	
	/**
	 * Finds the nearest clock to this turner.
	 * See the distTo(Clock c) and distTo(Button b) methods
	 * @param allClocks - all of the clocks ON ONE FACE
	 * @return - the nearest clock to this turner
	 */
	public Clock getNearestClock(Clock[] allClocks)
	{
		// sets a nearest clock
		Clock nearest = allClocks[0];
		for(Clock c : allClocks) // for each loop to go through all the clocks
		{
			// compares the distances of the clocks
			if(distTo(c) < distTo(nearest))
				nearest = c; // if this one is greater
			// make it the new nearest clock
		}
		return nearest;
	}
	
	/**
	 * Similar method to above, finds the nearest
	 * button
	 * @param allButtons - all the buttons on this face
	 * @return - the nearest button
	 */
	public Button getNearestButton(Button[] allButtons)
	{
		// set a base nearest button
		Button nearest = allButtons[0];
		for(Button b : allButtons)
		{
			// compare the distance to the buttons
			if(distTo(b) < distTo(nearest))
				nearest = b; // if this one is less
			// make it the new nearest button
		}
		return nearest;
	}
	
	/**
	 * Uses the Pythagorean Theorem to calculate
	 * the distance between the turner and a Clock
	 * @param c - the certain Clock
	 * @return - the distance to c
	 */
	public double distTo(Clock c)
	{
		// gets the difference in x positions
		int xDist = Math.abs(loc.x - c.loc.x);
		// gets the difference in y positions
		int yDist = Math.abs(loc.y - c.loc.y);
		// takes the square root of the sum of the squares to 
		// obtain the diagonal distance
		return Math.sqrt(xDist * xDist + yDist * yDist);
	}
	
	/**
	 * Exactly the same as above, 
	 * but for buttons
	 * @param b - the specified button
	 * @return - the distance to that button
	 */
	public double distTo(Button b)
	{
		int xDist = Math.abs(loc.x - b.loc.x);
		int yDist = Math.abs(loc.y - b.loc.y);
		return Math.sqrt(xDist * xDist + yDist * yDist);
	}
}
