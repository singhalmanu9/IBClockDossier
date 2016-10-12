package essentials;
import java.util.ArrayList;

import sides.DarkSide;
import sides.LightSide;
import sides.Side;
import turners.Turner;
import buttons.Button;
import clocks.Clock;

/**
 * The main clock class, this holds all the data required
 * for the solver to function. It holds two Sides, which hold
 * all the clocks, buttons, and turners. This class refers to 
 * many other classes to function properly.
 * 
 * @author Abhimanyu S.
 * @see Button, Clock, Side, Turner
 */
public class TheClock 
{
	public Side[] twoSides; // the two sides to the clock, front and back
	public boolean isSolved; // true if the clock is solved (all 12's)
	// false otherwise
	
	public TheClock() // constructor "creates" a TheClock object
	{
		twoSides = new Side[2];
		initSides();
		setOpposites();
		isSolved = true;
	}
	
	/**
	 * Determines if the Clock has been solved.
	 * @return - has the Clock been solved?
	 */
	public boolean isSolved()
	{
		// if ANY clock is not at 12, the Clock is not solved
		for(Clock c : twoSides[0].clocks)
		{
			if(c.pointsTo != 12)
				return false;
		}
		
		for(Clock c : twoSides[1].clocks)
		{
			if(c.pointsTo != 12)
				return false;
		}
		
		// if the method gets to here, all clocks
		// are at 12 and the Clock is solved
		return true;
	}
	
	/**
	 * Pushes the button b
	 * @param b - the button to be pushed
	 */
	public void push(Button b)
	{
		// if b is up, after being pushed, it HAS to the opposite of up
		b.isUp = !b.isUp;
		// ditto for b's opposite
		b.opposite.isUp = !b.opposite.isUp;
		// refresh the isActive for all the clocks
		refreshClocks();
	}
	
	/**
	 * The over arching method that turns the clocks. 
	 * Refers almost entirely on the turn method in Turner
	 * @param timesToTurn - the number of times we want to turn
	 * @param positionOfTurner - where the turner is located
	 */
	public void turn(int timesToTurn, int positionOfTurner)
	{
		// firstly, find the Turner we want
		Turner turner = whichOneIsUp().dials[positionOfTurner];
		// then, tell it to turn however much we want
		turner.turn(this, timesToTurn);
	}
	
	/**
	 * Basic searcher method to find which side is up or facing the user.
	 * Refer to this method for whichOneIsDown()
	 * @return - the side that is facing the user
	 */
	public Side whichOneIsUp()
	{
		// looks through both sides
		for(Side s : twoSides)
		{
			// if true, it is at the front
			// don't even have to check the other one
			if(s.isFront)
				return s;
		}
		// if we didn't find it, something went WAY wrong
		return null;
	}
	
	/**
	 * Same as whichOneIsUp(), except looks for the down face.
	 * @return - whichever side is facing away from the user
	 */
	public Side whichOneIsDown()
	{
		for(Side s : twoSides)
		{
			if(!s.isFront)
				return s;
		}
		return null;
	}
	
	/**
	 * Used to check and see if a special case exists.
	 * Determines if the cross is already solved.
	 * This method is called in the Logic class.
	 * @param s - the side to look at
	 * @return - true if the cross is solved, false if not
	 */
	public boolean isCrossFinished(Side s)
	{
		// variables for easy access to the 5 cross clocks
		Clock center = s.clockAt(3, 3);
		Clock left = s.clockAt(1, 3);
		Clock right = s.clockAt(5, 3);
		Clock up = s.clockAt(3, 1);
		Clock down = s.clockAt(3, 5);
		
		// if all of them are pointing to the same number,
		if(center.pointsTo == right.pointsTo && center.pointsTo == left.pointsTo
				&& center.pointsTo == down.pointsTo && center.pointsTo == up.pointsTo)
			return true; // cross is solved
		else
			return false; // false if not
	}
	
	/**
	 * This method determines if the clocks should be active or not.
	 * The logic used to determine if the activeness of the clocks is
	 * somewhat simple: First make all the not active clocks set to false,
	 * then set the active ones to true. This requires running through 
	 * all the buttons twice to ensure accuracy.
	 * Critical method in success of the clock solver, this is where
	 * most errors in a failed solve result from.
	 */
	public void refreshClocks()
	{
		for(Side s : twoSides) // looks through both sides
		{
			for(Button b : s.buttons) // runs through every button
			{
				// first checks if the button is down
				if(!b.isUp)
				{
					// if so, change all the adjacent clocks to false
					s.clockAt(b.loc.x - 1, b.loc.y - 1).isActive = false;
					s.clockAt(b.loc.x + 1, b.loc.y - 1).isActive = false;
					s.clockAt(b.loc.x - 1, b.loc.y + 1).isActive = false;
					s.clockAt(b.loc.x + 1, b.loc.y + 1).isActive = false;
				}
			}
			
			for(Button b : s.buttons) // run through all the buttons again
			{
				// but this time check if it IS up
				if(b.isUp)
				{
					// if so, change the surrounding clocks to true
					s.clockAt(b.loc.x - 1, b.loc.y - 1).isActive = true;
					s.clockAt(b.loc.x + 1, b.loc.y - 1).isActive = true;
					s.clockAt(b.loc.x - 1, b.loc.y + 1).isActive = true;
					s.clockAt(b.loc.x + 1, b.loc.y + 1).isActive = true;
				}
			}
		}
	}
	
	/**
	 * Gets all the active top clocks. Refer to this method
	 * for documentation on the other getActiveDownClocks method.
	 * @return - all top clocks that are active
	 */
	public ArrayList<Clock> getActiveTopClocks()
	{
		// instantiates the ArrayList
		ArrayList<Clock> active = new ArrayList<Clock>();
		// defines the up side for easy access
		Side up = whichOneIsUp();
		
		// basic search loop to find and store the active clocks
		for(Clock c : up.clocks)
		{
			if(c.isActive)
				active.add(c);
		}
		// return the whole list
		return active;
	}
	
	/**
	 * Gets the not active CORNER clocks on the top side.
	 * Uses different logic than above to get the pertinent clocks.
	 * Instead of all clocks, only the unactive corners are moved
	 * in a turner turn. So, instead of going through all clocks,
	 * the method only goes through the corners.
	 * Refer to this method for the below getNotDownClocks method.
	 * @return - gets all the not active clocks on top
	 */
	public ArrayList<Clock> getNotTopClocks()
	{
		// initializes the ArrayList and defines up for convenience
		ArrayList<Clock> notActive = new ArrayList<Clock>();
		Side up = whichOneIsUp();
		
		// defines the corner clocks
		Clock upRight = up.clockAt(1, 1);
		Clock downRight = up.clockAt(1, 5);
		Clock upLeft = up.clockAt(5, 1);
		Clock downLeft = up.clockAt(5, 5);
		
		// if one isn't active, add it to the list
		if(!upRight.isActive)
			notActive.add(upRight);
		if(!upLeft.isActive)
			notActive.add(upLeft);
		if(!downRight.isActive)
			notActive.add(downRight);
		if(!downLeft.isActive)
			notActive.add(downLeft);
		
		return notActive;
	}
	
	/**
	 * Refer to the above getActiveTopClocks() method.
	 * @return - the active down clocks
	 */
	public ArrayList<Clock> getActiveDownClocks()
	{
		ArrayList<Clock> active = new ArrayList<Clock>();
		Side down = whichOneIsDown();
		for(Clock c : down.clocks)
		{
			if(c.isActive)
				active.add(c);
		}
		return active;
	}
	
	/**
	 * Refer to the above getNotTopClocks() method
	 * for documentation.
	 * @return - all not active clocks on the bottom face
	 */
	public ArrayList<Clock> getNotDownClocks()
	{
		ArrayList<Clock> notActive = new ArrayList<Clock>();
		Side down = whichOneIsDown();
		
		Clock upRight = down.clockAt(1, 1);
		Clock downRight = down.clockAt(1, 5);
		Clock upLeft = down.clockAt(5, 1);
		Clock downLeft = down.clockAt(5, 5);
		
		if(!upRight.isActive)
			notActive.add(upRight);
		if(!upLeft.isActive)
			notActive.add(upLeft);
		if(!downRight.isActive)
			notActive.add(downRight);
		if(!downLeft.isActive)
			notActive.add(downLeft);
		
		return notActive;
	}
	
	/**
	 * Gets the specific clock at the Location loc.
	 * Uses a search method to find the Clock.
	 * Refer to this method for the below getButtonAt method.
	 * @param s - the Side to look through
	 * @param loc - the Location we're looking for
	 * @return - the clock at Location loc.
	 */
	public Clock getClockAt(Side s, Location loc)
	{
		for(Clock c : s.clocks) // goes through all clocks on that Side
		{
			// if the coordinates match, return the match
			if(c.loc.x == loc.x && c.loc.y == loc.y)
				return c; // don't need to check anymore because this
			// is the unique clock at the location
		}
		// results in problems if it wasn't found
		return null;
	}
	
	/**
	 * Refer to the method getClockAt for documentation.
	 * @param s - the Side to look in
	 * @param loc - the location the button we are looking for is in
	 * @return - the Button at the Location loc
	 */
	public Button getButtonAt(Side s, Location loc)
	{
		Button found = s.buttons[0];
		for(Button b : s.buttons)
		{
			if(b.loc.x == loc.x && b.loc.y == loc.y)
				b = found;
		}
		return found;
	}
	
	/**
	 * Flips the Clock by making the front side the back side
	 * and the back side the front side.
	 */
	public void flip()
	{
		// interchanges the two sides
		twoSides[0].isFront = !twoSides[0].isFront;
		twoSides[1].isFront = !twoSides[1].isFront;
	}
	
	/**
	 * Private method that initializes the two sides.
	 */
	private void initSides()
	{
		// constructs new Sides for TheClock
		twoSides[0] = new LightSide();
		twoSides[1] = new DarkSide();
	}
	
	/**
	 * Sets the opposite buttons for all buttons. Important step
	 * in ensuring clock works as specified.
	 */
	private void setOpposites()
	{
		// uses the setOpposite method in the Button class
		twoSides[0].buttonAt(2, 2).setOpposite(twoSides[1].buttonAt(4, 2));
		twoSides[0].buttonAt(4, 2).setOpposite(twoSides[1].buttonAt(2, 2));
		twoSides[0].buttonAt(2, 4).setOpposite(twoSides[1].buttonAt(4, 4));
		twoSides[0].buttonAt(4, 4).setOpposite(twoSides[1].buttonAt(2, 4));
		
		twoSides[1].buttonAt(2, 2).setOpposite(twoSides[0].buttonAt(4, 2));
		twoSides[1].buttonAt(4, 2).setOpposite(twoSides[0].buttonAt(2, 2));
		twoSides[1].buttonAt(2, 4).setOpposite(twoSides[0].buttonAt(4, 4));
		twoSides[1].buttonAt(4, 4).setOpposite(twoSides[0].buttonAt(2, 4));
	}
}
