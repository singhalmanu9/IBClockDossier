package sides;
import turners.Turner;
import buttons.DarkButton;
import clocks.DarkClock;

/**
 * This is the DarkSide class, a subclass of 
 * Side. Most important methods are inherited,
 * so please refer to the superclass for
 * documentation.
 * @author Abhimanyu S.
 * @see Side
 */
public class DarkSide extends Side
{
	
	public DarkSide() // constructor
	{
		clocks = new DarkClock[9];
		isFront = false;
		dials = new Turner[4];
		
		initDarkSide();
	}
	
	/**
	 * This private method initializes all
	 * of the key elements in the Clock, 
	 * including Clocks, Buttons, and Turners
	 */
	private void initDarkSide()
	{
		clocks[0] = new DarkClock(1, 1);
		clocks[1] = new DarkClock(3, 1);
		clocks[2] = new DarkClock(5, 1);
		clocks[3] = new DarkClock(1, 3);
		clocks[4] = new DarkClock(3, 3);
		clocks[5] = new DarkClock(5, 3);
		clocks[6] = new DarkClock(1, 5);
		clocks[7] = new DarkClock(3, 5);
		clocks[8] = new DarkClock(5, 5);
		
		buttons[0] = new DarkButton(2, 2);
		buttons[1] = new DarkButton(4, 2);
		buttons[2] = new DarkButton(2, 4);
		buttons[3] = new DarkButton(4, 4);
		
		dials[0] = new Turner(0,0);
		dials[1] = new Turner(6,0);
		dials[2] = new Turner(0,6);
		dials[3] = new Turner(6,6);
	}
}
