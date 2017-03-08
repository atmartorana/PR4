public abstract class CarController
{
    public static final Coord WEST = new Coord(0,-1);
    public static final Coord EAST = new Coord(0,1);
    public static final Coord NORTH = new Coord(-1,0);
    public static final Coord SOUTH = new Coord(1,0);
    public static final Coord ORIGIN = new Coord(0,0);
    protected Coord direction;
    protected CoordInfo oracle;
    public CarController(CoordInfo oracle)
    {
        this.oracle = oracle;
        direction = EAST;
    }
    
    public abstract void setDefaultDirection();
   
    // return the direction when roaming 
    public abstract Coord roam(Coord current);

    // return the direction when driving 
    public abstract Coord drive(Coord current, Coord goal);

    public Coord getDirection()
    {
        return direction;
    }
}
/* notes:
* manual driver - drive is whatever the current "straight direction" 
*               - redirect does nothing, only keyboard inputs change
*/

// vim: ts=4:sw=4:et

