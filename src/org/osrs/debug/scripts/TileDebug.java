package org.osrs.debug.scripts;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import org.osrs.api.objects.GroundItem;
import org.osrs.api.objects.RSPlayer;
import org.osrs.api.objects.RSTile;
import org.osrs.util.ScriptDef;

public class TileDebug extends ScriptDef{
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
        if (p != null) {
            RSTile tile = p.getLocation();
            Point pt = methods.calculations.locationToScreen(tile);
            g.drawString(tile.toString(), pt.x, pt.y);
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
                pt = methods.calculations.locationToScreen(tile);
                g.drawString(tile.toString(), pt.x, pt.y);
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
		return g;
	}
}
