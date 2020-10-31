package org.osrs.api.methods;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;

import org.osrs.api.objects.RSPlayer;
import org.osrs.api.objects.RSTile;
import org.osrs.api.objects.RSWidget;
import org.osrs.api.pathing.Map;
import org.osrs.api.pathing.WorldMap;

public class Walking extends MethodDefinition{
	public Walker walker = null;
	public Walking(MethodContext context){
		super(context);
		walker = new Walker(methods);
	}
	/**
	 * Gets the current destination tile.
	 * @return currentDestination
	 */
	public RSTile getDestination(){
		if(methods.game.getDestinationX()<=0 || methods.game.getDestinationX()<=0)
			return new RSTile(-1, -1, -1);
		return new RSTile(methods.game.getDestinationX()+methods.game.getMapBaseX(), methods.game.getDestinationY()+methods.game.getMapBaseY(), methods.game.getCurrentPlane());
	}
	/**
	 * Gets the current run energy
	 * @return currentEnergy
	 */
	public int getEnergy(){
		return methods.game.getClient().runEnergy();
	}
	/**
	 * Checks to see if there is a destination flag currently
	 * @return true if its up
	 */
	public boolean hasDestination(){
		return !(getDestination().equals(new RSTile(-1, -1)));
	}
	/**
	 * Reverses the order of a given RSTile path.
	 * @param path
	 * @return reversedPath
	 */
	public RSTile[] invertPath(RSTile[] path) {
		RSTile[] invertedPath = new RSTile[path.length];
		for (int i = 0; i < path.length; i++) {
			invertedPath[i] = path[path.length - 1 - i];
		}
		return invertedPath;
	}
	/**
	 * Checks to see if a path is local. This means
	 * if any one RSTile is visible on the minimap, it is 
	 * local.
	 * @param path
	 * @return true if path is local
	 */
	public boolean isPathLocal(RSTile[] path){
		for(RSTile tile : path){
			if(methods.calculations.onMap(tile))
				return true;
		}
		return false;
	}
	/**
	 * Checks to see if running is enabled.
	 * @return true if enabled
	 */
	public boolean isRunEnabled(){
		int[] settings = methods.game.getClient().varps();
		if(settings.length==0)
			return false;
		return settings[173]==1;
	}
	/**
	 * Checks to see if the local player is walking/running.
	 * @return true if moving
	 */
	public boolean isWalking(){
		RSPlayer p = methods.players.getLocalPlayer();
		if(p!=null)
			return p.getAccessor().movementSpeed()>0;
			return false;
	}
	/**
	 * Turns running on if param is true, off if false
	 * @param on(true)/off(false)
	 */
	public void setRun(boolean input){
		if(isRunEnabled()==input)
			return;
		RSWidget w = methods.game.getClient().resizeMode()?methods.widgets.getChild(160, 22):methods.widgets.getChild(160, 24);
		if(w!=null){
			if(methods.game.getClient().resizeMode()){
				Rectangle r =  w.getBounds();
				r.y+=5;
				Point pt = new Point(r.x+new Random().nextInt(r.width), r.y+new Random().nextInt(r.height));
				methods.mouse.move(pt);
				try {
					Thread.sleep(new Random().nextInt(50)+50);
				} catch (@SuppressWarnings("unused") Exception e) {
				}
				if(r.contains(methods.mouse.getLocation()))
					methods.mouse.click();
			}
			else
				w.click();
			for(int i=0;i<20;++i){
				try {
					Thread.sleep(new Random().nextInt(40)+10);
				} catch (@SuppressWarnings("unused") Exception e) {
				}
				if(isRunEnabled())
					break;
			}
		}
	}
	/**
	 * Starts a walking thread that attempts to traverse
	 * a path of RSTiles.
	 * @param path
	 * @return true if destination reached
	 */
	public boolean walkPath(RSTile[] path) {
		if (!isWalking() || methods.calculations.distanceTo(getDestination()) <= 5)
			return walker.walkTo(path, true);
		return false;
	}
	/**
	 * Attempts to walk to a tile; if the tile is visible within
	 * the viewport, it will interact there; if not visible in the viewport
	 * but on the minimap, will interact there. 
	 * NOTE : This will NOT generate a path of RSTiles to traverse
	 * @param tile
	 * @return true if we walked to the tile
	 */
	public boolean walkTile(RSTile tile){
		try{
			RSPlayer localPlayer = methods.players.getLocalPlayer();
			if(localPlayer!=null){
				if(methods.calculations.onViewport(tile)){
					//return tile.doAction("Walk here");
				}
				else if(methods.calculations.onMap(tile)){
					methods.region.clickMap(tile);
					return true;
				}
			}
		}
		catch(@SuppressWarnings("unused") Exception e){

		}
		return false;
	}
	/**
	 * Specifies to walk to a tile via the minimap
	 * if it is visible there.
	 * @param tile
	 * @return true if we walked to the tile
	 */
	public boolean walkTileOnMap(RSTile tile){
		if(methods.calculations.onMap(tile)){
			methods.region.clickMap(tile);
			for(int i=0;i<20;++i){
				try {
					Thread.sleep(new Random().nextInt(40)+10);
				} catch (@SuppressWarnings("unused") InterruptedException e) {
				}
				if(isWalking())
					break;
			}
			return true;
		}
		return false;
	}
	//An actionwalk means we clicked a player, object, npc, ... so we can end next to the target location instead of on it TODO implement
	//Offsets, the size of this npc, object, ... TODO implement
	public void findPath(RSPlayer p, int tX, int tY, int offsetX, int offsetY, boolean actionWalk) {
		//Gets the map we are going to use
		Map map = WorldMap.getRegion(p.getLocation().getX(), p.getLocation().getY());
		
		//Calculates our positions in this map
		int startX = Map.getLocalCoord(p.getLocation().getX());
		int startY = Map.getLocalCoord(p.getLocation().getY());
		int targetX = startX + (tX - p.getLocation().getX());
		int targetY = startY + (tY - p.getLocation().getY());

		int[][] distances = new int[104][104]; //Only used to determine the size of our path
		int[][] parentDir = new int[104][104]; //Direction to parent, used for backtracking & marking tiles as visited
		parentDir[startX][startY] = -1; //Mark it as visited, the number doesn't matter much
		//The tile we are currently doing a calculation for
		int currentX = startX;
		int currentY = startY;
		int[] queueX = new int[4096]; //Although there are 10816 tiles in a 104x104 mapregion it will
		int[] queueY = new int[4096]; //just overwrite the array again when the last index is reached
		int queueRead = 0;
		queueX[0] = startX;
		queueY[0] = startY;
		int queueWrite = 1;
		boolean routeExists = false;

		//The queueRead will always increase by 1 every iteration
		//The queueWrite will increase by 0 to 8 every iteration, the amount of unvisited, reachable neighboring tiles
		//	If the queueInsert doesn't increase anymore it means there is no route, queueRead will still increase and the loop will break because of condition
		while(queueRead != queueWrite) {
			currentX = queueX[queueRead];
			currentY = queueY[queueRead];
			queueRead = (queueRead + 1) & 0xfff; //When the queueRead gets over 4095 it is reset and we will recycle the start indexes, the queueWrite will also do this

			if(currentX == targetX && currentY == targetY) {
				//Path is found, break the loop
				routeExists = true;
				break;
			}
			//Todo check offsets & actionWalk stuff...

			//The distance to the next square will be the distance to this square +1
			int distance = distances[currentX][currentY] +1;

			//Checks if you can move to adjacent tiles, and adds them to the queue if needed
			//west, checks if we can move to the west
			if(currentX > 0 && parentDir[currentX -1][currentY] == 0 && (map.getFlags()[currentX -1][currentY] & 0x12) == 0) {
				queueX[queueWrite] = currentX -1;
				queueY[queueWrite] = currentY;
				distances[currentX -1][currentY] = distance;
				parentDir[currentX -1][currentY] = 0x2;
				queueWrite = (queueWrite +1) & 0xfff;
			}
			//east
			if(currentX < 103 && parentDir[currentX +1][currentY] == 0 && (map.getFlags()[currentX +1][currentY] & 0x11) == 0) {
				queueX[queueWrite] = currentX +1;
				queueY[queueWrite] = currentY;
				distances[currentX +1][currentY] = distance;
				parentDir[currentX +1][currentY] = 0x1;
				queueWrite = (queueWrite +1) & 0xfff;
			}
			//south
			if(currentY > 0 && parentDir[currentX][currentY -1] == 0 && (map.getFlags()[currentX][currentY -1] & 0x14) == 0) {
				queueX[queueWrite] = currentX;
				queueY[queueWrite] = currentY -1;
				distances[currentX][currentY -1] = distance;
				parentDir[currentX][currentY -1] = 0x4;
				queueWrite = (queueWrite +1) & 0xfff;
			}
			//north
			if(currentY < 103 && parentDir[currentX][currentY +1] == 0 && (map.getFlags()[currentX][currentY +1] & 0x18) == 0) {
				queueX[queueWrite] = currentX;
				queueY[queueWrite] = currentY +1;
				distances[currentX][currentY +1] = distance;
				parentDir[currentX][currentY +1] = 0x8;
				queueWrite = (queueWrite +1) & 0xfff;
			}

			//southwest, in these directions we must check if its possible to go south AND west AND southwest
			if(currentX > 0 && currentY > 0 && parentDir[currentX -1][currentY -1] == 0 && (map.getFlags()[currentX][currentY -1] & 0x14) == 0 && (map.getFlags()[currentX -1][currentY] & 0x12) == 0 && (map.getFlags()[currentX -1][currentY -1] & 0x16) == 0) {
				queueX[queueWrite] = currentX -1;
				queueY[queueWrite] = currentY -1;
				distances[currentX -1][currentY -1] = distance;
				parentDir[currentX -1][currentY -1] = 0x6;
				queueWrite = (queueWrite +1) & 0xfff;
			}

			//southeast
			if(currentX < 103 && currentY > 0 && parentDir[currentX +1][currentY -1] == 0 && (map.getFlags()[currentX][currentY -1] & 0x14) == 0 && (map.getFlags()[currentX +1][currentY] & 0x11) == 0 && (map.getFlags()[currentX +1][currentY -1] & 0x15) == 0) {
				queueX[queueWrite] = currentX +1;
				queueY[queueWrite] = currentY -1;
				distances[currentX +1][currentY -1] = distance;
				parentDir[currentX +1][currentY -1] = 0x5;
				queueWrite = (queueWrite +1) & 0xfff;
			}

			//northwest
			if(currentX > 0 && currentY < 103 && parentDir[currentX -1][currentY +1] == 0 && (map.getFlags()[currentX][currentY +1] & 0x18) == 0 && (map.getFlags()[currentX -1][currentY] & 0x12) == 0 && (map.getFlags()[currentX -1][currentY +1] & 0x1A) == 0) {
				queueX[queueWrite] = currentX -1;
				queueY[queueWrite] = currentY +1;
				distances[currentX -1][currentY +1] = distance;
				parentDir[currentX -1][currentY +1] = 0xA;
				queueWrite = (queueWrite +1) & 0xfff;
			}
			//northeast
			if(currentX < 103 && currentY < 103 && parentDir[currentX +1][currentY +1] == 0 && (map.getFlags()[currentX][currentY +1] & 0x18) == 0 && (map.getFlags()[currentX +1][currentY] & 0x11) == 0 && (map.getFlags()[currentX +1][currentY +1] & 0x19) == 0) {
				queueX[queueWrite] = currentX +1;
				queueY[queueWrite] = currentY +1;
				distances[currentX +1][currentY +1] = distance;
				parentDir[currentX +1][currentY +1] = 0x9;
				queueWrite = (queueWrite +1) & 0xfff;
			}

		}
		if(!routeExists) {
			return;
			//This happens when you click in unreachable places (such as just black void, rivers, closed buildings...)
			//TODO: Should calculate an alternative path here
		}

		//Backtracking our path

		int size = distances[currentX][currentY];	//the number of steps in the path equals the distance to the target location
		queueWrite = 0;
		while(currentX != startX || currentY != startY) {
			int dir = parentDir[currentX][currentY];
			if ((dir & 0x2) != 0) {
				currentX++;
			} else if ((dir & 0x1) != 0) {
				currentX--;
			}
			if ((dir & 0x4) != 0) {
				currentY++;
			} else if((dir & 0x8) != 0) {
				currentY--;
			}
			//Invert the path because we are backtracking it
			queueX[size - 1 - queueWrite] = currentX; //overwrite the queueX, we dont need it anymore
			queueY[size - 1 - queueWrite] = currentY;
		}
		//Actually add it to the walking queue
		for(int i = 0; i < size; i++) {//TODO add to path
			//p.getWalkingQueue().addStepToWalkingQueue(queueX[i], queueY[i], queueDir[i]);
		}
	}
}
