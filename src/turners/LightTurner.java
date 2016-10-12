package turners;

/**
 * This class is the LightTurner class,
 * a subclass of the Turner class. All of the methods
 * are inherited from there, so please refer there
 * for documentation.
 * @author Abhimanyu S.
 * @see Turner
 */
public class LightTurner extends Turner 
{
	public DarkTurner opposite;
	
	public LightTurner(int x, int y) 
	{
		super(x, y);
	}

}
