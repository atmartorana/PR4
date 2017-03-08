public interface CoordInfo
{
	/** Determine if a Coordinate is free
 	 * @param loc location to query
 	 * @return  true if loc is in bounds and available
 	 *          else false.  
 	 *          return false if loc is null
	*/
	public boolean coordFree(Coord loc);
}
