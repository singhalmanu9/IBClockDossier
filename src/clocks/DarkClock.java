package clocks;

/**
 * This class is a subclass of Clock.
 * For more extensive documentation, please
 * refer to the Clock class.
 * @author Abhimanyu S.
 * @see Clock
 */
public class DarkClock extends Clock
{
	public DarkClock(int x, int y)
	{
		super(x, y);
		pointsTo = 12;
		isActive = false;
	}
}
