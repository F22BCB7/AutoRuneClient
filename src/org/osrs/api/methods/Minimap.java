package org.osrs.api.methods;

import java.awt.Point;
import java.awt.Rectangle;

import org.osrs.api.constants.WidgetData;
import org.osrs.api.objects.RSTile;
import org.osrs.api.objects.RSWidget;

public class Minimap extends MethodDefinition{
	public Minimap(MethodContext context){
		super(context);
	}
	public boolean isOnMap(RSTile tile){
		return methods.calculations.onMap(tile);
	}
	public Point locationToMinimap(RSTile tile){
		return methods.calculations.locationToMinimap(tile);
	}
	public Rectangle getMinimapBounds(){
		if(methods.game.getClient().resizeMode()){
			RSWidget w = methods.widgets.getChild(WidgetData.RESIZEMODE_MINIMAP[0], WidgetData.RESIZEMODE_MINIMAP[1]);
			if(w!=null){
				return w.getBounds();
			}
		}
		return new Rectangle(643, 84, 0, 0);
	}
}
