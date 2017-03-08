import java.awt.Color;
public class Rider extends GridObject
{
	private SharedCar driver;
	private boolean wait = true;
	public Rider()
	{
		this.setSymbol("R");
		setColor(Color.YELLOW);
		driver = null;
	}
	public boolean waiting() {
		return wait;
	}
	public void pickUp(SharedCar c)	{
		driver = c;
		wait = false;
	}
	public SharedCar getDriver() {
		return driver;
	}
	
}
