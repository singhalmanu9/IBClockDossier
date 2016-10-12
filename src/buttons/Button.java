package buttons;
import essentials.Location;

/**
 * This class contains the code for a Button.
 * This is a very short class that is a key part
 * of the Clock itself. Please refer here for
 * LightButton and DarkButton documentation,
 * as they inherit all methods from this superclass.
 * @author Abhimanyu Singhal
 *
 */
public class Button 
{
	// instance variables
	public boolean isUp;
	public Button opposite;
	public Location loc;
	
	public Button(int x, int y)
	{
		isUp = false;
		loc = new Location(x, y);
	}
	
	/**
	 * Sets the opposite variable for 
	 * this Button
	 * @param opp - the Button that is supposed to be
	 * the opposite of this one
	 */
	public void setOpposite(Button opp)
	{
		opposite = opp;
	}
}
