package org.osrs.debug.scripts;

import java.awt.Graphics;
import org.osrs.api.methods.Camera;
import org.osrs.api.wrappers.Client;
import org.osrs.util.ScriptDef;

public class CameraDebug extends ScriptDef{
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
		Camera cam = methods.camera;
		g.drawString("X : "+cam.getX(), x, y);
		y+=15;
		g.drawString("Y : "+cam.getY(), x, y);
		y+=15;
		g.drawString("Z : "+cam.getZ(), x, y);
		y+=15;
		g.drawString("Pitch : "+cam.getPitch(), x, y);
		y+=15;
		g.drawString("Yaw : "+cam.getYaw(), x, y);
		y+=15;
		Client c = methods.game.getClient();
		if(c!=null){
			g.drawString("Locked : "+c.cameraLocked(), x, y);
			y+=15;
			g.drawString("Map Rotation : "+c.mapRotation(), x, y);
			y+=15;
			g.drawString("Map State : "+c.mapState(), x, y);
			y+=15;
		}
		return g;
	}
}
