package org.osrs.debug.scripts;

import java.awt.Graphics;
import java.util.Arrays;

import org.osrs.api.objects.RSPlayer;
import org.osrs.api.wrappers.Client;
import org.osrs.util.ScriptDef;

public class LocationDebug extends ScriptDef{
	@Override
	public void run() {
		while(true){
			sleep(100);
		}
	}
	@Override
	public Graphics paint(Graphics g){
		int x=5;
		int y=45;
		RSPlayer p = methods.players.getLocalPlayer();
		if(p!=null){
			g.drawString("Location : "+p.getLocation().toString(), x, y);
			y+=15;
			g.drawString("Destination : "+methods.walking.getDestination().toString(), x, y);
			y+=15;
		}
		Client c = methods.game.getClient();
		if(c!=null){
			g.drawString("Selected Tile : "+c.selectedRegionTileX()+", "+c.selectedRegionTileY(), x, y);
			y+=15;
			g.drawString("Map Base : "+c.mapBaseX()+", "+c.mapBaseY(), x, y);
			y+=15;
			g.drawString("Region IDs : "+Arrays.toString(c.mapRegionIDs()), x, y);
			y+=15;
		}
		return g;
	}
}
