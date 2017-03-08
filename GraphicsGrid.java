import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.event.*;

/** Class to draw blocks within a Panel
 *  Can draw colored squares, or directional colored triangles
 *  Draws based on a list of GridObjects 
 */
public class GraphicsGrid extends JPanel implements ComponentListener {
	private int width, height, pixels;
	private int rows, cols;
	private ArrayList<GridObject> cells;
	private final int PAD = 3;
	private final int MINPIXEL = 3;
	private Polygon[] sprites;

	/** Constructor
 	 * @param rows - number of rows in the grid
 	 * @param cols - number of columns in the grid
 	 * @param pixels - width/height in pixels of each element
 	 */
	public GraphicsGrid (int rows, int cols, int pixels) {
		
		this.rows = (rows > 0 ? rows : 1);
		this.cols = (cols > 0 ? cols : 1);
		this.pixels = (pixels >= MINPIXEL ? pixels : MINPIXEL);
		width = this.cols * this.pixels;
		height =this.rows * this.pixels;
		cells = new ArrayList<GridObject>();
		setSize(width,height);
		createPolys();
		addComponentListener(this);
	}

	/** Remove  GridObject
     * @param gobj  GridObject to remove from canvas 
	/** Create polygons the represent the shapes */
	private void createPolys()
	{
		sprites = new Polygon[5];
		int [] x;  
		int [] y;
		// square polygon
		x = new int [] {0,pixels,pixels,0}; 
		y = new int [] {0,0,pixels,pixels}; 
		sprites[GridObject.NONE] = new Polygon(x,y,x.length);
		// up triangle
		x = new int [] {0,pixels,pixels/2}; 
		y = new int [] {pixels,pixels,0}; 
		sprites[GridObject.UP] = new Polygon(x,y,x.length);
		// Down triangle
		x = new int [] {0,pixels,pixels/2}; 
		y = new int [] {0,0,pixels}; 
		sprites[GridObject.DOWN] = new Polygon(x,y,x.length);
		// Right triangle
		x = new int [] {0,0,pixels}; 
		y = new int [] {0,pixels,pixels/2}; 
		sprites[GridObject.RIGHT] = new Polygon(x,y,x.length);
		// Left triangle
		x = new int [] {pixels,pixels,0}; 
		y = new int [] {0,pixels,pixels/2}; 
		sprites[GridObject.LEFT] = new Polygon(x,y,x.length);
	}
		
		

	@Override
	/** How large in pixels. Add padding
 	 *@return size, in pixels of this JPanel
	*/
	public Dimension getPreferredSize()
	{
		return new Dimension(width+PAD,height+PAD);
	}
	
	/* ComponentListener Interface */
	/* Handle the case when the panel is resized */
 	public void componentResized(ComponentEvent e) {
		Dimension d = getSize();
		int w = d.width;
		int h = d.height;
		int xPix = Math.max(w/cols,MINPIXEL);
		int yPix = Math.max(h/rows,MINPIXEL);
		pixels = Math.min(xPix,yPix);
		width = cols * pixels;
		height = rows * pixels;
		createPolys();
    }
    public void componentHidden(ComponentEvent e) {}
    public void componentMoved(ComponentEvent e) {}
    public void componentShown(ComponentEvent e) {}

	@Override
	/* our special painting */
	protected synchronized void paintComponent(Graphics g) {
		super.paintComponent(g);
		Dimension d = null;
		d = getSize(d);
		int offX = ((d.width) - width)/2;
		int offY = ((d.height) - height)/2;
		for (GridObject obj : cells) {
			Polygon p = sprites[obj.getDir()];
			Coord loc = obj.getLocation();
			int tx = loc.col * pixels + offX;
			int ty = loc.row * pixels + offY;
			g.translate(tx,ty);
			g.setColor(obj.getColor());
			g.fillPolygon(p);
			g.translate(-tx,-ty);
		}
		g.setColor(Color.BLACK);
		g.drawRect(offX, offY,  width, height);

		for (int i = offX; i < width + offX; i += pixels) {
			g.drawLine(i, 0 + offY, i, height + offY);
		}

		for (int i = offY; i < height + offY; i += pixels) {
			g.drawLine(offX, i, width + offX, i);
		}
	}

	/** Add an GridObject to be drawn. Will only
     * add the same reference once
     * @param gobj  GridObject to add for painting
     */
	public synchronized void addGridObject(GridObject gobj) {
		if (gobj == null) return;
		if (!cells.contains(gobj))
			cells.add(gobj);
		repaint();
	}
	/** Add an Array of  GridObjects to be drawn. Will only
     * add the same reference once
     * @param gobjs Array of GridObjects to add for painting
     */
	public synchronized void addGridObject(GridObject[] gobjs) {
		for (GridObject gobj : gobjs)
			addGridObject(gobj);
	}

	/** Remove  GridObject
     * @param gobj  GridObject to remove from canvas 
     */
	public synchronized void removeGridObject(GridObject gobj) {
		if (cells.contains(gobj))
			cells.remove(gobj);
		repaint();
	}

	/** Remove  GridObject
     * @param gobjs  Array of GridObjects to remove from canvas 
	*/
	public synchronized void removeGridObject(GridObject[] gobjs) {
		for (GridObject gobj : gobjs)
			removeGridObject(gobj);
	}

	/** Remove all GridObjects
 	 */
	public synchronized void clearObjects() {
		cells.clear();
	}
}
	

// vim: ts=4:sw=4:tw=78
