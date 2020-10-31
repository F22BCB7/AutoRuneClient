package org.osrs.debug.scripts;

import java.awt.Graphics;
import java.awt.Point;
import org.osrs.api.objects.RSNpc;
import org.osrs.util.ScriptDef;

public class NPCDebug extends ScriptDef{
	RSNpc[] npcs = new RSNpc[]{};
	@Override
	public void run() {
		while(true){
			sleep(100);
			npcs = methods.npcs.getAll();
		}
	}
	@Override
	public Graphics paint(Graphics g){
		int x=5;
		int y=45;
		for(RSNpc npc : npcs){
			y=45;
			if(npc!=null){
				Point p = methods.calculations.locationToScreen(npc.getLocation());
				g.drawString(""+npc.getID()+":"+npc.getName()+":"+npc.accessor.get().interactingID(), p.x, p.y);
				y+=15;
				g.drawString(""+npc.getAccessor().uid(), x, y);
			}
		}
		return g;
	}
}
