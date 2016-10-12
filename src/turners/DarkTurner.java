package turners;

/**
 * This class is the DarkTurner class,
 * a subclass of the Turner class. All of the methods
 * are inherited from there, so please refer there
 * for documentation.
 * @author Abhimanyu S.
 * @see Turner
 */
public class DarkTurner extends Turner
{
	
	public LightTurner opposite;
	
	public DarkTurner(int x, int y) 
	{
		super(x, y);
	}

}
