package org.osrs.debug.scripts;

import java.awt.Graphics;
import java.util.Arrays;

import org.osrs.api.methods.Mouse;
import org.osrs.api.wrappers.Client;
import org.osrs.api.wrappers.MouseTracker;
import org.osrs.util.ScriptDef;

public class MouseDebug extends ScriptDef{
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
		Mouse m = methods.mouse;
		g.drawString("Location : "+m.getLocation().toString(), x, y);
		y+=15;
		g.drawString("Real Location : "+m.getRealLocation().toString(), x, y);
		y+=15;
		Client c = methods.game.getClient();
		if(c!=null){
			g.drawString("Crosshair State : "+c.mouseCrosshairState(), x, y);
			y+=15;
			g.drawString("Idle Ticks : "+c.mouseIdleTicks(), x, y);
			y+=15;
			g.drawString("Client X/Y : "+c.mouseX()+", "+c.mouseY(), x, y);
			y+=15;
			g.drawString("Current Button : "+c.currentMouseButton(), x, y);
			y+=15;
			g.drawString("Last Button : "+c.lastMouseButton(), x, y);
			y+=15;
			g.drawString("Last Click Time : "+c.lastMouseClickTime(), x, y);
			y+=15;
			g.drawString("Last Move Time : "+c.lastMouseMoveTime(), x, y);
			y+=15;
			g.drawString("Pending Click : "+c.pendingMouseClickX()+", "+c.pendingMouseClickY(), x, y);
			y+=15;
			g.drawString("Pending Move : "+c.pendingMouseX()+", "+c.pendingMouseY(), x, y);
			y+=15;
			
			MouseTracker mt = c.mouseTracker();
			if(mt!=null){
				g.drawString("Tracker Running : "+mt.isRunning(), x, y);
				y+=15;
				g.drawString("Tracked X : "+Arrays.toString(mt.trackedX()), x, y);
				y+=15;
				g.drawString("Tracked Y : "+Arrays.toString(mt.trackedY()), x, y);
				y+=15;
				g.drawString("Index : "+mt.index(), x, y);
				y+=15;
			}
		}
		return g;
	}
}
