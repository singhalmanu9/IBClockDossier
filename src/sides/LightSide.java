package sides;
import turners.Turner;
import buttons.LightButton;
import clocks.LightClock;

/**
 * This is the DarkSide class, a subclass of 
 * Side. Most important methods are inherited,
 * so please refer to the superclass for
 * documentation.
 * @author Abhimanyu S.
 * @see Side
 */
public class LightSide extends Side
{
	
	public LightSide()
	{
		clocks = new LightClock[9];
		dials = new Turner[4];
		isFront = true;
		initLightSide();
	}
	
	/**
	 * This private method initializes all
	 * of the key elements in the Clock, 
	 * including Clocks, Buttons, and Turners
	 */
	private void initLightSide()
	{
		clocks[0] = new LightClock(1, 1);
		clocks[1] = new LightClock(3, 1);
		clocks[2] = new LightClock(5, 1);
		clocks[3] = new LightClock(1, 3);
		clocks[4] = new LightClock(3, 3);
		clocks[5] = new LightClock(5, 3);
		clocks[6] = new LightClock(1, 5);
		clocks[7] = new LightClock(3, 5);
		clocks[8] = new LightClock(5, 5);
		
		buttons[0] = new LightButton(2, 2);
		buttons[1] = new LightButton(4, 2);
		buttons[2] = new LightButton(2, 4);
		buttons[3] = new LightButton(4, 4);
		
		dials[0] = new Turner(0,0);
		dials[1] = new Turner(6,0);
		dials[2] = new Turner(0,6);
		dials[3] = new Turner(6,6);
	}
}
