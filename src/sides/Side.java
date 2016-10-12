package sides;
import turners.Turner;
import buttons.Button;
import clocks.Clock;

/**
 * This is the Side class, the class that holds
 * many of the important elements in the Clock.
 * This is the superclass of DarkSide and LightSide,
 * so please refer here for documentation.
 * @author Abhimanyu S.
 *
 */
public class Side 
{
	// instance variables
	// note that these are all elements
	public Clock[] clocks;
	public boolean isFront; // well, except for this
	public Button[] buttons;
	public Turner[] dials;
	
	public Side()
	{
		clocks = new Clock[9];
		isFront = true;
		buttons = new Button[4];
		dials = new Turner[4];
	}
	
	/**
	 * Gets the clock at the location specified.
	 * @param x - the x coordinate of the clock's position
	 * @param y - y oordinate of the position
	 * @return - the Clock at (x,y)
	 */
	public Clock clockAt(int x, int y)
	{
		for(Clock c : clocks) // searches through the clocks
		{
			// if it matches
			if(c.loc.x == x && c.loc.y == y)
				return c; // return immediately
		}
		// if it isn't found, an error will occur
		return null;
	}
	
	/**
	 * Gets the button at the specified location
	 * @param x - the x coordinate of the position
	 * @param y - y coordinate of the position
	 * @return - the Button at (x,y)
	 */
	public Button buttonAt(int x, int y)
	{
		// searches through all the buttons
		for(Button b : buttons)
		{
			// if the button is found
			if(b.loc.x == x && b.loc.y == y)
				return b; // return it
		}
		return null;
	}
}
