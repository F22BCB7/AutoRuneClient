package org.osrs.debug.scripts;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import org.osrs.api.objects.GroundItem;
import org.osrs.api.objects.RSPlayer;
import org.osrs.api.objects.RSTile;
import org.osrs.util.ScriptDef;

public class GroundItemDebug extends ScriptDef{
	HashMap<RSTile, ArrayList<GroundItem>> tileMap = new HashMap<RSTile, ArrayList<GroundItem>>();
	HashMap<RSTile, ArrayList<GroundItem>> tileMapBuffer = new HashMap<RSTile, ArrayList<GroundItem>>();
	@Override
	public void run() {
		while(true){
			sleep(100);
			tileMapBuffer.clear();
	        RSPlayer p = methods.players.getLocalPlayer();
	        if (p != null) {
	            RSTile tile = p.getLocation();
	            for (GroundItem gi : methods.groundItems.getItemsAt(tile.getX(), tile.getY(), tile.getPlane())){
	            	if(!tileMapBuffer.containsKey(tile)){
	            		ArrayList<GroundItem> temp = new ArrayList<GroundItem>();
	            		temp.add(gi);
	            		tileMapBuffer.put(tile, temp);
	            	}
	            	else{
	            		ArrayList<GroundItem> temp = tileMapBuffer.get(tile);
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
	                for (GroundItem gi : methods.groundItems.getItemsAt(tile.getX(), tile.getY(), tile.getPlane())){
		            	if(!tileMapBuffer.containsKey(tile)){
		            		ArrayList<GroundItem> temp = new ArrayList<GroundItem>();
		            		temp.add(gi);
		            		tileMapBuffer.put(tile, temp);
		            	}
		            	else{
		            		ArrayList<GroundItem> temp = tileMapBuffer.get(tile);
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
		}
	}
	@Override
	public Graphics paint(Graphics g){
		int x=5;
		int y=45;
		for(RSTile tile : tileMap.keySet()){
			Point pt = methods.calculations.locationToScreen(tile);
			x=pt.x;
			y=pt.y;
			ArrayList<GroundItem> items = tileMap.get(tile);
			for(GroundItem item : items){
				g.drawString(""+item.getID()+" ("+item.getName()+") x "+item.getStackSize(), x, y);
				y+=15;
			}
		}
		return g;
	}
}
