package org.osrs.debug.scripts;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import org.osrs.api.objects.GameObject;
import org.osrs.api.objects.GroundItem;
import org.osrs.api.objects.RSPlayer;
import org.osrs.api.objects.RSTile;
import org.osrs.api.wrappers.InteractableObject;
import org.osrs.util.ScriptDef;

public class InteractableObjectDebug extends ScriptDef{
	private boolean lock=false;
	HashMap<RSTile, ArrayList<GameObject>> tileMap = new HashMap<RSTile, ArrayList<GameObject>>();
	HashMap<RSTile, ArrayList<GameObject>> tileMapBuffer = new HashMap<RSTile, ArrayList<GameObject>>();
	@Override
	public void run() {
		while(true){
			sleep(100);
			if(lock)
				continue;
			lock=true;
			tileMapBuffer.clear();
	        RSPlayer p = methods.players.getLocalPlayer();
	        if (p != null) {
	            RSTile tile = p.getLocation();
	            for (GameObject gi : methods.objects.getAllAt(tile)){
	            	if(!(gi.getAccessor() instanceof InteractableObject))
	            		continue;
	            	if(!tileMapBuffer.containsKey(tile)){
	            		ArrayList<GameObject> temp = new ArrayList<GameObject>();
	            		temp.add(gi);
	            		tileMapBuffer.put(tile, temp);
	            	}
	            	else{
	            		ArrayList<GameObject> temp = tileMapBuffer.get(tile);
	            		temp.add(gi);
	            		tileMapBuffer.put(tile, temp);
	            	}
	            }
	            int di = 1;
	            int dj = 0;
	            int segment_length = 1;
	            int i = tile.getX();
	            int j = tile.getY();
	            int z = tile.getPlane();
	            int segment_passed = 0;
	            for (int k = 0; k < (20*20); ++k) {
	                i += di;
	                j += dj;
	                if(i==-1 || j==-1)
	                	break;
	                ++segment_passed;
	                tile = new RSTile(i, j, z);
		            for (GameObject gi : methods.objects.getAllAt(tile)){
		            	if(!(gi.getAccessor() instanceof InteractableObject))
		            		continue;
		            	if(!tileMapBuffer.containsKey(tile)){
		            		ArrayList<GameObject> temp = new ArrayList<GameObject>();
		            		temp.add(gi);
		            		tileMapBuffer.put(tile, temp);
		            	}
		            	else{
		            		ArrayList<GameObject> temp = tileMapBuffer.get(tile);
		            		temp.add(gi);
		            		tileMapBuffer.put(tile, temp);
		            	}
	                }
	                if (segment_passed == segment_length) {
	                    segment_passed = 0;
	                    int buffer = di;
	                    di = -dj;
	                    dj = buffer;
	                    if (dj == 0) {
	                        ++segment_length;
	                    }
	                }
	            }
	        }
	        tileMap.clear();
	        tileMap.putAll(tileMapBuffer);
	        lock=false;
		}
	}
	@Override
	public Graphics paint(Graphics g){
		int x=5;
		int y=45;
		if(!lock){
			lock=true;
			Set<RSTile> tiles = tileMap.keySet();
			for(RSTile tile : tiles){
				Point pt = methods.calculations.locationToScreen(tile);
				x=pt.x;
				y=pt.y;
				ArrayList<GameObject> objects = tileMap.get(tile);
				for(GameObject object : objects){
					pt=methods.calculations.locationToScreen(object.getLocation());
					x=pt.x;
					y=pt.y;
					g.drawString(""+object.getID()+" ("+object.getName()+")", x, y);
					y+=15;
				}
			}
			lock=false;
		}
		return g;
	}
}
