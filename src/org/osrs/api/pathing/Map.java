package org.osrs.api.pathing;

/**
 * Represents a map, this could be a region or just the worldmap
 */
public class Map {
	
	public Map(int x, int y) {
		flags = new byte[x][y];
	}

	/**
	 * Clipping flags
	 * 0x1 west wall		0000 0001
	 * 0x2 east wall		0000 0010
	 * 0x4 north wall		0000 0100
	 * 0x8 south wall		0000 1000
	 * 0x10 solid object	0001 0000
	 * 0x20	reserved		0010 0000 You could use 2 bits for height levels, this will save you 
	 * 0x40	reserved		0100 0000 from using 4 times as much memory by creating 4 worldmaps
	 * 0x80 reserved		1000 0000
	 */
	private byte[][] flags;

	public byte[][] getFlags() {
		return flags;
	}
	
	public void addSolidObjectFlag(int x, int y) {
		flags[x][y] |= 0x10; //To remove flags (deleting objects) you can do flags[x][y] &= ~0x10;
	}

	public void addWestWallFlag(int x, int y) { //Walls, doors...
		flags[x][y] |= 0x1;
		//Also adds a east flag to the western sq
		if(x > 0) //It might be better to check this in the formula incase you have 2 different wall objects facing eachother, and one of them gets removed, this system wouldn't know there were 2 and remove both of them!
			flags[x -1][y] |= 0x2;
	}

	public void addEastWallFlag(int x, int y) {
		flags[x][y] |= 0x2;
		if(x < flags.length)
			flags[x +1][y] |= 0x1;
	}

	public void addNorthWallFlag(int x, int y) {
		flags[x][y] |= 0x4;
		if(y < flags.length)
			flags[x][y +1] |= 0x8;
	}

	public void addSouthWallFlag(int x, int y) {
		flags[x][y] |= 0x8;
		if(y > 0)
			flags[x][y -1] |= 0x4;
	}
	
	
	/**
	 * Calculate the x or y coordinate inside a region based, this is always between 48 and 56 because the region is always centered around you
	 * @param coord The x or y coordinate to get the local coord for
	 * @return The x or y coordinate in the region
	 */
	public static int getLocalCoord(int coord) {
		return coord - getRegionBaseCoord(coord);
	}
	
	/**
	 * Calculates the coordinate where a region starts (south-western corner)
	 * Every 8 tiles your region changes, the 8x8 square you are in is the center of a region
	 * @param coord Either the absolute x or y coord
	 * @return The absolute x or y base coordinate of this region
	 */
	public static int getRegionBaseCoord(int coord) {
		return ((coord/8)-6)*8;
	}
	
}