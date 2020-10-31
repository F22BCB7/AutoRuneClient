package org.osrs.api.pathing;

/**
 * World map class, loads the map and distributes requested regions
 * @author Vincent
 *
 */
public class WorldMap {
	
	private static Map map;

	public static void load() {
		map = new Map(10816, 10816); //Not sure on the  highest possible coords
		//just an example of how to add objects when you load them
		//DO NOT MAKE A HUGE LIST OF THIS LIKE I DID THIS IS JUST FOR TESTING PURPOSES
		//This is some of the lumbridge castle
		WorldMap.map.addEastWallFlag(3216, 3212);//wall
		WorldMap.map.addEastWallFlag(3216, 3213);//wall
		WorldMap.map.addEastWallFlag(3216, 3214);//wall
		WorldMap.map.addEastWallFlag(3216, 3215);//wall
		WorldMap.map.addEastWallFlag(3216, 3216);//wall
		WorldMap.map.addEastWallFlag(3216, 3217);//wall
		WorldMap.map.addSouthWallFlag(3216, 3218);//door
		WorldMap.map.addNorthWallFlag(3216, 3219);//door
		WorldMap.map.addEastWallFlag(3216, 3220);//wall
		WorldMap.map.addEastWallFlag(3216, 3221);//wall
		WorldMap.map.addEastWallFlag(3216, 3222);//wall
		WorldMap.map.addEastWallFlag(3216, 3223);//wall
		WorldMap.map.addEastWallFlag(3216, 3224);//wall
		WorldMap.map.addSolidObjectFlag(3217, 3220);//statue
		WorldMap.map.addSolidObjectFlag(3218, 3220);//bush
		WorldMap.map.addSolidObjectFlag(3219, 3220);//bush
		WorldMap.map.addSolidObjectFlag(3220, 3220);//bush
		WorldMap.map.addSolidObjectFlag(3223, 3220);//bush
		WorldMap.map.addSolidObjectFlag(3217, 3217);//statue
		WorldMap.map.addSolidObjectFlag(3218, 3217);//bush
		WorldMap.map.addSolidObjectFlag(3219, 3217);//bush
		WorldMap.map.addSolidObjectFlag(3220, 3217);//bush
		WorldMap.map.addSolidObjectFlag(3223, 3217);//bush
		//TODO load worldmap here
	}
	
	/**
	 * Gets a 104x104 consisting of 169 8x8 regions, the x and y are in the center 8x8
	 */
	public static Map getRegion(int x, int y) {
		Map region = new Map(104, 104);
		int baseX = ((x / 8) - 6) * 8;
		int baseY = ((y / 8) - 6) * 8;
		for(int i = 0; i < 104; i++) {
			for(int j = 0; j < 104; j++) {
				region.getFlags()[i][j] = map.getFlags()[baseX +i][baseY +j];
			}
		}
		return region;
	}
	
	public static Map getMap() {
		return map;
	}
	
}