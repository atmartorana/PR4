import java.awt.Color;
public class SharedCar extends GridObject
{
    private CarController controller;
    private Coord riderLoc;
    private GridInfo grid;
    private boolean wantRider;
    private int nRiders; 
    public SharedCar(CarController ctrl, GridInfo grid)
    {
        controller = ctrl;
        wantRider = false;
        location = new Coord(0,0);
        this.grid = grid;
        nRiders = 0;
        setSymbol("C");
        setColor(Color.BLUE);
    }
    public void newRider(Coord loc)
    {
        riderLoc = new Coord(loc);
        wantRider = true;
    }
    public synchronized void drive()
    {
        Coord direction; 
        Coord newLoc;

        if (wantRider)
        {
            direction = controller.drive(location,riderLoc); 
            newLoc = location.add(direction);
            if (grid.claim(this,newLoc) ) {
                location = newLoc;
            }
        }
        else
        {
            direction = controller.roam(location);
            newLoc = location.add(direction);
            if (grid.claim(this,newLoc) ) {
                location = newLoc;
            }
        }
        if (wantRider && location.equals(riderLoc) && grid.riderLoaded(this))
        {
            roam();
        } 
        setDir(direction);
    }		
    public void roam()
    {
        wantRider = false;
        controller.setDefaultDirection();
    }
    private void setDir(Coord direction)
    {
        if (direction.equals(CarController.WEST)) dir = LEFT;
        if (direction.equals(CarController.EAST)) dir = RIGHT;
        if (direction.equals(CarController.NORTH)) dir = UP;
        if (direction.equals(CarController.SOUTH)) dir = DOWN;
    }
        
}	
// vim: ts=4:sw=4:et 
