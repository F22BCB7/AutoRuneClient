package org.osrs.api.objects;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;

import org.osrs.api.methods.MethodContext;
import org.osrs.api.methods.MethodProvider;
import org.osrs.input.mouse.MouseTarget;

public class Tab extends MethodProvider{
	public String name=null;
	public int index=-1;
	private int[] resizeModeWidgetIndexs = new int[]{-1, -1};
	private int[] fixedModeWidgetIndexs = new int[]{-1, -1};
	public Tab(MethodContext context, int resize_interfaceIndex, int resize_childIndex, int fixed_interfaceIndex, int fixed_childIndex, String tabName, int index) {
		super(context);
		resizeModeWidgetIndexs[0]=resize_interfaceIndex;
		resizeModeWidgetIndexs[1]=resize_childIndex;
		fixedModeWidgetIndexs[0]=fixed_interfaceIndex;
		fixedModeWidgetIndexs[1]=fixed_childIndex;
		this.name=tabName;
		this.index=index;
	}
	public boolean isSelected(){
		RSWidget widget = getWidget();
		if(widget!=null){
			if(widget.getInternal().spriteID()==1181)//find your selected tab
				return true;//and return it
		}
		return false;
	}
	public String getName(){
		return name;
	}
	public int getIndex(){
		return index;
	}
	public RSWidget getWidget(){
		RSWidget widget = null;
		if(methods.game.getClient().resizeMode()){
			widget = methods.widgets.getChild(resizeModeWidgetIndexs[0], resizeModeWidgetIndexs[1]);
		}
		else{
			widget = methods.widgets.getChild(fixedModeWidgetIndexs[0], fixedModeWidgetIndexs[1]);
		}
		return widget;
	}
	public Rectangle getBounds(){
		RSWidget widget = getWidget();
		if(widget!=null){
			return new Rectangle(widget.getAbsoluteX(), widget.getAbsoluteY(), widget.getWidth(), widget.getHeight());
		}
		return new Rectangle(-1, -1, 0, 0);
	}
	public boolean isHovering(){
		RSWidget widget = getWidget();
		if(widget!=null){
			return widget.isHovering();
		}
		return false;
	}
	public void hover(){
		RSWidget widget = getWidget();
		if(widget!=null){
			widget.hover();
		}
	}
	public boolean click(){
		RSWidget widget = getWidget();
		if(widget!=null){
			return widget.click();
		}
		return false;
	}
	public Point getScreenLocation(){
		return getBounds().getLocation();
	}
	public Point getRandomScreenPoint() {
		RSWidget widget = getWidget();
		if(widget!=null){
			return widget.getRandomScreenPoint();
		}
		return new Point(-1, -1);
	}
	public MouseTarget getTarget() {
	    return new MouseTarget() {
	    	Point target = getRandomScreenPoint();
	        public Point get() {
	        	return target;
	        }
	        public boolean isOver(int posX, int posY) {
	        	Rectangle bounds = getBounds();
	            return bounds.contains(new Point(posX, posY));
	        }
	    };
	}
	@Override
	public boolean equals(Object o){
		return o instanceof Tab && ((Tab)o).index==index;
	}
}
