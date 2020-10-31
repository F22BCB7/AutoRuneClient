package org.osrs.debug.scripts;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.beans.MethodDescriptor;

import org.osrs.api.objects.InventoryItem;
import org.osrs.api.objects.RSWidget;
import org.osrs.api.wrappers.ItemDefinition;
import org.osrs.util.ScriptDef;

public class InventoryDebug extends ScriptDef{
	private InventoryItem[] inventory = new InventoryItem[28];
	@Override
	public void run() {
		while(true){
			sleep(100);
			inventory = methods.inventory.getItems();
		}
	}
	@Override
	public Graphics paint(Graphics g){
		int x=5;
		int y=45;
		g.drawString("Is Open : "+methods.inventory.isDisplayed(), x, y);
		y+=15;
		//Rectangle bounds = methods.inventory.getBounds();
		//g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
		//164, 53 inv tab
		//164, 64 inv screen
		if(methods.inventory.isDisplayed()){
			for(InventoryItem item : inventory){
				if(item==null)
					continue;
				Rectangle r = item.getBounds();
				g.drawRect(r.x, r.y, r.width, r.height);
				g.drawString((methods.game.getClient().itemSelectionState()==1?(methods.game.getClient().lastSelectedItemIndex()==item.getIndex()?"*":""):"")+item.getID(), r.x, r.y+14);
			}
		}
		return g;
	}
}
