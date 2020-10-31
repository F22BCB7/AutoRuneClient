package org.osrs.debug.scripts;

import java.awt.Graphics;
import java.util.Arrays;

import org.osrs.api.methods.Menu;
import org.osrs.api.wrappers.Client;
import org.osrs.util.ScriptDef;

public class MenuDebug extends ScriptDef{
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
		Menu m = methods.menu;
		g.drawString("Location : "+m.getLocation().toString(), x, y);
		y+=15;
		g.drawString("Height : "+m.getHeight(), x, y);
		y+=15;
		g.drawString("Width : "+m.getWidth(), x, y);
		y+=15;
		g.drawString("Item Count : "+m.getItemCount(), x, y);
		y+=15;
		g.drawString("Top Text : "+m.getTopText(), x, y);
		y+=15;
		g.drawString("Top Action : "+m.getTopAction(), x, y);
		y+=15;
		g.drawString("Top Option : "+m.getTopOption(), x, y);
		y+=15;
		g.drawString("Actions : "+Arrays.toString(m.getActions()), x, y);
		y+=15;
		g.drawString("Options : "+Arrays.toString(m.getOptions()), x, y);
		y+=15;
		Client c = methods.game.getClient();
		if(c!=null){
			g.drawString("Open : "+c.menuOpen(), x, y);
			y+=15;
			g.drawString("Opcodes : "+Arrays.toString(c.menuOpcodes()), x, y);
			y+=15;
			g.drawString("Primary Args : "+Arrays.toString(c.menuPrimaryArgs()), x, y);
			y+=15;
			g.drawString("Secondary Args : "+Arrays.toString(c.menuSecondaryArgs()), x, y);
			y+=15;
			g.drawString("Tertiary Args : "+Arrays.toString(c.menuTertiaryArgs()), x, y);
			y+=15;
			g.drawString("Shift Click Actions : "+Arrays.toString(c.menuShiftClickActions()), x, y);
			y+=15;
			//g.drawString("On Cursor UIDs : "+Arrays.toString(methods.game.getOnCursorUIDs()), x, y);
			//y+=15;
		}
		return g;
	}
}
