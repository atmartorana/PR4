/** Run a timer (ticker) in a thread
 *  -- needs references to logical grid and the simulation
 * @author Philip Papadopoulos
 * @version CSE11-Winter17-PR4
 */
import java.util.concurrent.TimeUnit;
import java.lang.NullPointerException;
class TimeTick implements Runnable
{
	public final int MILLISWAIT=10;
	private int ticks;
	private boolean stop = false;
	private volatile boolean pause = false;
	private Grid theGrid;
	private Simulation sim;
	/** Constructor for a "ticker"
 	 * @param ticks number of MILLISWAIT (10ms) to go by before invoking grid.update
 	 * @param grid reference the logical grid. grid.update() must be defined
 	 * @param sim reference the overall simulation, sim.update() must be define
	 *        sim may be null.
	 */
	public TimeTick (int ticks, Grid grid, Simulation sim) throws NullPointerException
	{
		
		this.ticks = (ticks > 0 ? ticks : 1);
		theGrid = grid;
		this.sim = sim;
		if (theGrid == null)
			throw new NullPointerException("grid cannot be null");
	}
	/** run thread - exits when TimeTick.stop() is invoked
 	*/ 
	public void run()
	{	
		int curticks = 0;
		// Run until told to stop
		while (!stop)
		{
		 	try { TimeUnit.MILLISECONDS.sleep(MILLISWAIT);}
                	catch (InterruptedException e){};
			// Every MILLISWAIT cycle, invoke sim.update()
			if (sim != null)
				sim.update();	
			// every ticks*MILLISWAIT interval, invoke theGrid.update()
			if ( (curticks++ % ticks) == 0 && !pause)
				theGrid.update();
			curticks %= ticks; // bound curticks to prevent overflow
		}
	}
	/** Stop the TimeTick and have the thread exit
 	 */
	public void stop()
	{
		stop = true;
	}
	/** change the pause/run state of the ticker
 	 *  This effects only updating of the Grid. The Simulation will
 	 *  still receive tick updates for interactivity 
	*/
	public void changeState()
	{
		pause = ! pause;
	}
	/** determine if the ticker is paused or not 
	 * @return true of Grid will be updated on ticks, false otherwise
	*/
	public boolean paused()
	{
		return pause;
	}

	/** Set the number of ticks that most go by before calling Grid.update()
	 * @param ticks, number of MILLISWAIT (10ms) intervals that must occur
	 *               before invoking Grid.update(). non-positive numbers
	 *               sets ticks to 1.
	 * @return what ticks was actually set to internally
	*/
	public int setTicks(int ticks)
	{
		this.ticks = (ticks > 0 ? ticks : 1);
		return this.ticks;
	}
}
