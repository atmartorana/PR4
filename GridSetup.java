import java.io.*;
import java.util.ArrayList;

// Reads a text file to interpret enabling simple parsing of Grid config
// file
// -- What is understood
// dimension <rows> <cols> 
// obstacle <row> <col>
// rider <row> <col>
// player <row> <col>
// robocar <row> <col> <controller>
public class GridSetup 
{
	private Coord dimension;
	private Coord player;
	private Coord rider;
	private ArrayList<Coord> obstacles;
	private ArrayList<Coord> robocars;
	private ArrayList<String> controllers;
	public GridSetup(String fname)
	{
		obstacles = new ArrayList<Coord>();
		robocars = new ArrayList<Coord>();
		controllers = new ArrayList<String>();
		BufferedReader inputStream; 
		String [] parts;
		try
		{
			inputStream = new BufferedReader(new FileReader(fname));
			String line = inputStream.readLine();
			while (line != null)
			{
				try {
					parts = line.trim().split("\\s+");
					int row = Integer.parseInt(parts[1]);
					int col = Integer.parseInt(parts[2]);

					switch (parts[0].toLowerCase()) {
						case "dimension":
							dimension = new Coord(row,col);
							break;
						case "rider":
							rider = new Coord(row,col);
							break;
						case "player":
							player = new Coord(row,col);
							break;
						case "obstacle":
							obstacles.add(new Coord(row,col));
							break;
						case "robocar":
							controllers.add(parts[3]);
							robocars.add(new Coord(row,col));
							break;
						default:
							break;
					}
				}
				catch (NullPointerException e) {}
				catch (IndexOutOfBoundsException e){}
				catch (NumberFormatException e){}
				line = inputStream.readLine();
			}
		}
		catch(FileNotFoundException e) { }
		catch(IOException e) {}
	}

	/** get player
 	 * @return null if no player, else Coord of player
	*/
	Coord getPlayer() { return player; }

	/** get dimension
 	 * @return null if no dimension, else Coord of dimension
	*/
	Coord getDimension() { return dimension; }

 	/** get rider 
 	 * @return null if no rider, else Coord of rider
	*/
	Coord getRider() { return rider; }

	/** get robocars 
 	 * @return robocars coordinates, 0-length if no robocars 
	*/
	Coord [] getRobocars() { return robocars.toArray(new Coord [0]); }

	/** get obstacle 
 	 * @return obstacles coordinates, 0-length if no obstacles 
	*/
	Coord [] getObstacles() { return obstacles.toArray(new Coord [0]); }

	/** get controllers 
 	 * @return controllers names, 0-length if no robocars 
	*/
	String [] getControllers() { return controllers.toArray(new String [0]); }
}
// vim: ts=4:sw=4

