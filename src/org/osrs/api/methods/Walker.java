package org.osrs.api.methods;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

import org.osrs.api.objects.RSPlayer;
import org.osrs.api.objects.RSTile;
import org.osrs.util.Data;

/**
 * A threaded 'walker'.
 */
public class Walker extends MethodDefinition implements Runnable {
	public RSTile tile = null;
	public boolean done = false;
	public int movementTimer = 10000;
	public int distanceTo = 6;
	public Walker(MethodContext context) {
		super(context);
	}
	public void run() {
		done=false;
		System.out.println("Started walker thread!");
		long timer = System.currentTimeMillis();
		RSPlayer pl = methods.players.getLocalPlayer();
		RSTile lastTile = pl.getLocation();
		int randomReturn = new Random().nextInt(3)+5;
		this.tile = path[path.length - 1];
		while (methods.calculations.distanceTo(tile) > distanceTo && (Data.currentScripts.get(methods.botInstance))!=null && !done) {
			if((Data.currentScripts.get(methods.botInstance)).isPaused){
				sleep(500);
				continue;
			}
			if(!methods.walking.isRunEnabled() && methods.walking.getEnergy()>50){
				methods.walking.setRun(true);
				for(int i=0;i<20;++i){
					if(methods.walking.isRunEnabled())
						break;
					sleep(new Random().nextInt(50)+150);
				}
			}
			if (!pl.isMoving() || (methods.game.getDestinationX() == -1 || methods.game.getDestinationY()==-1) || methods.calculations.distanceTo(methods.walking.getDestination()) < randomReturn) {
				RSTile nextTile = nextTile(path);
				if(nextTile.equals(new RSTile(-1, -1)))
					continue;
				if ((methods.walking.hasDestination()) && methods.calculations.distanceBetween(methods.walking.getDestination(), nextTile) <= distanceTo) {
					continue;
				}
				methods.region.clickMap(nextTile);
				if(nextTile.equals(tile))
					done = true;
				if(!waitToMove(new Random().nextInt(400)+800)){
					System.out.println("[Walker] Failed to walk to tile "+nextTile.toString());
					return;
				}
				randomReturn = new Random().nextInt(3)+5;
			}
			RSTile myLoc = pl.getLocation();
			if(!myLoc.equals(lastTile)){
				timer = System.currentTimeMillis();
				lastTile = myLoc;
			}
			if (System.currentTimeMillis() - timer > movementTimer) {
				System.out.println("[Walker] Timeout exit.");
				return;
			}
			sleep(new Random().nextInt(20)+20);
		}
		if (methods.calculations.distanceTo(tile) <= distanceTo) {
			done = true;
		}
	}
	public Thread walker = null;
	public RSTile[] path = null;
	private static void sleep(int i){
		try {
			Thread.sleep(i);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}		
	public static void wait(int toSleep) {
		try {
			long start = System.currentTimeMillis();
			Thread.sleep(toSleep);
			long now;
			while (start + toSleep > (now = System.currentTimeMillis())) {
				Thread.sleep(start + toSleep - now);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public boolean waitToMove(int timeout){
		long start = System.currentTimeMillis();
		RSPlayer myPlayer = methods.players.getLocalPlayer();
		while (System.currentTimeMillis() - start < timeout) {
			if (myPlayer.isMoving())
				return true;
			wait(15);
		}
		return false;
	}
	/**
	 * Used to draw the current walking data to
	 * the minimap.
	 * @param graphics
	 */
	public void drawMap(Graphics g) {
		if (walker != null && walker.isAlive()) {
			g.setColor(Color.RED);
			RSTile loc = methods.players.getLocalPlayer().getLocation();
			Point myTile = methods.calculations.locationToMinimap(loc);
			Point center = new Point(myTile.x + 2, myTile.y + 2);
			g.drawOval(center.x - 70, center.y - 70, 140, 140);
			if (path == null) return;
			for (int i = 0; i < path.length; i++) {
				RSTile tile = path[i];
				Point p = methods.calculations.locationToMinimap(tile);
				if (p.x != -1 && p.y != -1) {
					g.setColor(Color.BLACK);
					g.fillRect(p.x + 1, p.y + 1, 3, 3);
					if (i > 0) {
						final Point p1 = methods.calculations.locationToMinimap(path[i - 1].getX(), path[i - 1].getY());
						g.setColor(Color.ORANGE);
						if (p1.x != -1 && p1.y != -1)
							g.drawLine(p.x + 2, p.y + 2, p1.x + 2, p1.y + 2);
					}
				}
			}
			RSTile next = nextTile(path);
			Point tile = methods.calculations.locationToMinimap(next);
			g.setColor(Color.RED);
			if (tile.x != -1 && tile.y != -1) {
				g.fillRect(tile.x + 1, tile.y + 1, 3, 3);
			}
			g.setColor(Color.BLACK);
		}
	}
	public boolean walkTo(RSTile[] path, boolean waitUntilDest){
		if(!methods.walking.isPathLocal(path)){
			System.out.println("[Walker] Path not local. Not walking.");
			return false;
		}
		this.tile = path[path.length - 1];
		this.path = path;
		walker = new Thread(this);
		if(walker==null)return false;
		walker.start();
		waitToMove(new Random().nextInt(400)+800);
		if (waitUntilDest) {
			while (walker.isAlive()) {
				sleep(new Random().nextInt(300)+300);
			}
			walker=null;
			return done;
		}
		walker=null;
		return true;
	}
	/**
	 * Grabs the next visible on minimap tile we should walk to
	 * within a path.
	 * @param path
	 * @return nextTile
	 */
	public RSTile getClosestTileOnMap(RSTile tile) {
		if (!methods.calculations.onMap(tile)) {
			try {
				RSTile loc = methods.players.getLocalPlayer().getLocation();
				RSTile walk = new RSTile((loc.getX() + tile.getX()) / 2, (loc.getY() + tile.getY()) / 2);
				return methods.calculations.onMap(walk) ? walk : getClosestTileOnMap(walk);
			} catch (@SuppressWarnings("unused") final Exception e) { 
			}
		}
		return tile;
	}
	/**
	 * Grabs the next visible tile we should walk to
	 * within a path.
	 * @param path
	 * @return nextTile
	 */
	public RSTile nextTile(RSTile[] path) {
		for (int i = path.length - 1; i >= 0; i--) {
			if (methods.calculations.onMap(path[i])) {
				return path[i];
			}
		}
		return new RSTile(-1,-1);
	}

}