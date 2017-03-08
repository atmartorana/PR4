import java.awt.Color;
public abstract class GridObject
{
	/* Directions that GridObjects can point */
	public static final int NONE = 0;
	public static final int UP = 1;
	public static final int DOWN = 2;
	public static final int LEFT =  3;
	public static final int RIGHT =  4;
	protected String [] dirSymbols = {"#","^","v","<",">"}; 

        protected Coord location;
        protected String symbol = null;
        protected Color color;	
	protected int dir = NONE;

        public GridObject()
        {
            color = Color.WHITE;
            location = new Coord(0,0);
        }
        public void setLocation(Coord loc)
        {
                location = new Coord(loc);
        }
        public synchronized Coord getLocation()
        {
                return location;
        }
        public Color getColor()
        {
            return color;
        }
        public void setColor( Color c)
        {
            color = c;
        }
        public String getSymbol()
        {
	    if (symbol == null || dir != NONE) return dirSymbols[dir];
            return symbol;
        }
        public void setSymbol( String c)
        {
            symbol = c;
        }
	public int getDir()
	{
		return dir;
	}
}
	
//vim:ts=4:sw=4:et
