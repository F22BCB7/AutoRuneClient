package org.osrs.api.objects;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.Random;

import org.osrs.api.constants.WidgetData;
import org.osrs.api.methods.MethodContext;
import org.osrs.api.methods.MethodProvider;
import org.osrs.api.wrappers.Cache;
import org.osrs.api.wrappers.Item;
import org.osrs.api.wrappers.ItemDefinition;
import org.osrs.api.wrappers.Model;
import org.osrs.api.wrappers.Node;
import org.osrs.input.mouse.MouseHoverJob;
import org.osrs.input.mouse.MouseJob;
import org.osrs.input.mouse.MouseMoveListener;
import org.osrs.input.mouse.MouseTarget;

public class GroundItem extends MethodProvider{
	private RSTile location;
	private Item itemNode;
	private ItemDefinition definition;
	private RSModel model;
	public GroundItem(MethodContext context, RSTile t, Item item){
		super(context);
		this.location=t;
		this.itemNode=item;
		definition = methods.itemDefinitionCache.get(getID());
	}
	public ItemDefinition getDefinition(){
		if(definition==null)
			definition = methods.itemDefinitionCache.get(getID());
		if(definition==null){
			Cache cache = methods.game.getClient().itemDefinitionCache();
			if(cache!=null){
				Node node = methods.nodes.lookup(cache.table(), getID());
				if(node!=null && node instanceof ItemDefinition){
					definition = (ItemDefinition)node;
				}
			}
		}
		return definition;
	}
	public RSModel getModel(){
		if(model!=null)
			return model;
		definition = getDefinition();
		if(definition==null)
			return null;
		int id = getID();
		if(itemNode.quantity()>1){
			int stackIndex = -1;
			int[] amounts = definition.stackAmounts();
			if(amounts!=null){
				for(int amount : amounts){
					if(itemNode.quantity()>=amount)
						stackIndex++;
					else
						break;
				}
			}
			if(stackIndex!=-1){
				int[] stackIDs = definition.stackIDs();
				if(stackIDs!=null){
					if(stackIDs.length>stackIndex)
						id = stackIDs[stackIndex];
				}
			}
		}
		Cache cache = methods.game.getClient().itemModelCache();
		if(cache!=null){
			Node node = methods.nodes.lookup(cache.table(), id);
			if(node!=null && node instanceof Model){
				model = new RSModel(methods, (Model)node);
			}
		}
		return model;
	}
	public int getID(){
		if(itemNode==null)
			return -1;
		return itemNode.id();
	}
	public Item getItemNode(){
		return itemNode;
	}
	public String getName(){
		if(definition==null)
			definition=getDefinition();
		if(definition!=null)
			return definition.name();
		return "null_definition";
	}
	public int getStackSize(){
		return itemNode.quantity();
	}
	public RSTile getLocation(){
		return location;
	}
	public double getLocationX(){
		return location.getX();
	}
	public int getLocalX(){
		return location.getX()-methods.game.getMapBaseX();
	}
	public int getLocalY(){
		return location.getY()-methods.game.getMapBaseY();
	}
	public double getLocationY(){
		return location.getY();
	}
	public int getPlane(){
		return location.getPlane();
	}
	public boolean click(String action){
		if(isHovering() && methods.menu.isOpen()){
			if(methods.menu.contains(action)){
				if(methods.menu.click(action)){
        			return true;
    			}
				return false;
			}
			methods.menu.click("Cancel");
			return false;
		}
		boolean[] ret = new boolean[]{false};
        MouseHoverJob mouseHoverJob = methods.mouse.mouseHandler.createMouseHoverJob(new MouseMoveListener() {
            int count = 0;
            public void onMouseOverTarget(MouseJob mouseJob) {
                count++;
                if(count > methods.calculations.random(3, 16)){
        			if(methods.menu.click(action)){
            			int state=methods.game.getClient().mouseCrosshairState();
            			for(int i=0;i<20 && state==0;++i){
            				methods.sleep(50);
            				state=methods.game.getClient().mouseCrosshairState();
            			}
        				ret[0]=state==2;
        			}
	                mouseJob.stop();
	                return;
                }
            }
            public void onFinished(MouseJob mouseJob) {
            }
        }, getTarget());
        mouseHoverJob.start();
        mouseHoverJob.join();
        return ret[0];
	}
	public boolean click(String action, String option){
		if(isHovering() && methods.menu.isOpen()){
			if(methods.menu.contains(action, option)){
				if(methods.menu.click(action, option)){
        			return true;
    			}
				return false;
			}
			methods.menu.click("Cancel");
			return false;
		}
		boolean[] ret = new boolean[]{false};
        MouseHoverJob mouseHoverJob = methods.mouse.mouseHandler.createMouseHoverJob(new MouseMoveListener() {
        	int count = 0;
            public void onMouseOverTarget(MouseJob mouseJob) {
                count++;
                if(count > methods.calculations.random(3, 16)){
        			if(methods.menu.click(action, option)){
            			int state=methods.game.getClient().mouseCrosshairState();
            			for(int i=0;i<20 && state==0;++i){
            				methods.sleep(50);
            				state=methods.game.getClient().mouseCrosshairState();
            			}
        				ret[0]=state==2;
        			}
	                mouseJob.stop();
	                return;
                }
            }
            public void onFinished(MouseJob mouseJob) {
            }
        }, getTarget());
        mouseHoverJob.start();
        mouseHoverJob.join();
        return ret[0];
	}
	public boolean click(){
		boolean[] ret = new boolean[]{false};
        MouseHoverJob mouseHoverJob = methods.mouse.mouseHandler.createMouseHoverJob(new MouseMoveListener() {
            int count = 0;
            public void onMouseOverTarget(MouseJob mouseJob) {
                count++;
                if(count > methods.calculations.random(3, 16)){
        			methods.mouse.click();
        			int state=methods.game.getClient().mouseCrosshairState();
        			for(int i=0;i<20 && state==0;++i){
        				methods.sleep(50);
        				state=methods.game.getClient().mouseCrosshairState();
        			}
    				ret[0]=state==2;
	                mouseJob.stop();
	                return;
                }
            }
			public void onFinished(MouseJob mouseJob) {
			}
        }, getTarget());
        mouseHoverJob.start();
        mouseHoverJob.join();
        return ret[0];
	}
	public boolean isHovering(){
		/*RSTile loc = getLocation();
		int localX = loc.getX()-methods.game.getMapBaseX();
		int localY = loc.getY()-methods.game.getMapBaseY();
		for(int i=0;i<methods.menu.getItemCount();++i){
			if(methods.game.getMenuPrimaryArgs()[i]==itemNode.id()){
				if(methods.game.getMenuSecondaryArgs()[i]==localX &&
				methods.game.getMenuTertiaryArgs()[i]==localY){
					return true;
				}
			}
		}*/
		RSModel model = this.getModel();
		if(model!=null){
			model.getWireframe(getLocation());//so mouse is checked
			return model.containsMouse;
		}
		return false;
	}
	public boolean isHovering(String action){
		if(isHovering()){
			return methods.menu.contains(action);
		}
		return false;
	}
	public boolean isHovering(String action, String option){
		if(isHovering()){
			return methods.menu.contains(action, option);
		}
		return false;
	}
	public boolean hover(String action){
		boolean[] ret = new boolean[]{false};
        MouseHoverJob mouseHoverJob = methods.mouse.mouseHandler.createMouseHoverJob(new MouseMoveListener() {
            int count = 0;
            public void onMouseOverTarget(MouseJob mouseJob) {
                count++;
                if(count > methods.calculations.random(3, 16)){
                	if(isHovering(action))
                		ret[0]=true;
	                mouseJob.stop();
	                return;
                }
            }
            public void onFinished(MouseJob mouseJob) {
            }
        }, getTarget());
        mouseHoverJob.start();
        mouseHoverJob.join();
        return ret[0];
	}
	public boolean hover(String action, String option){
		boolean[] ret = new boolean[]{false};
        MouseHoverJob mouseHoverJob = methods.mouse.mouseHandler.createMouseHoverJob(new MouseMoveListener() {
            int count = 0;
            public void onMouseOverTarget(MouseJob mouseJob) {
                count++;
                if(count > methods.calculations.random(3, 16)){
                	if(isHovering(action, option))
                		ret[0]=true;
	                mouseJob.stop();
	                return;
                }
            }
            public void onFinished(MouseJob mouseJob) {
            }
        }, getTarget());
        mouseHoverJob.start();
        mouseHoverJob.join();
        return ret[0];
	}
	public boolean hover(){
		boolean[] ret = new boolean[]{false};
        MouseHoverJob mouseHoverJob = methods.mouse.mouseHandler.createMouseHoverJob(new MouseMoveListener() {
            int count = 0;
            public void onMouseOverTarget(MouseJob mouseJob) {
                count++;
                if(count > methods.calculations.random(3, 16)){
	                mouseJob.stop();
	                return;
                }
            }
            public void onFinished(MouseJob mouseJob) {
            	if(isHovering())
            		ret[0]=true;
            }
        }, getTarget());
        mouseHoverJob.start();
        mouseHoverJob.join();
        return ret[0];
	}
	public Point getRandomScreenPoint(){
		Polygon[] p = getWireframe();
		if(p.length<1)
			return new Point(-1, -1);
		Polygon pl = p[methods.calculations.random(p.length)];
		Rectangle r = pl.getBounds();
		return new Point(r.x+(methods.calculations.random(Math.abs(r.width)+1)), r.y+(methods.calculations.random(Math.abs(r.height)+1)));
	}
	public Point getScreenLocation(){
		return methods.calculations.locationToScreen(location);
	}
	public MouseTarget getTarget() {
		if (getModel() != null) {
		    return new MouseTarget() {
		    	Point target = getRandomScreenPoint();
		        public Point get() {
		        	return target;
		        }
		        public boolean isOver(int posX, int posY) {
		            return getModel().containsPoint(new Point(posX, posY), location);
		        }
		    };
		}
		return new MouseTarget() {
	    	Point target = getRandomScreenPoint();
	        public Point get() {
	        	return target;
	        }
		    public boolean isOver(int posX, int posY) {
		    	Point p = get();
		        return new Rectangle(p.x - 2, p.y - 2, 4, 4).contains(posX, posY);
		    }
		};
	}
	public boolean isOnMap(){
		Point p = methods.calculations.locationToMinimap(location);
		RSWidget w = methods.game.getClient().resizeMode()?
				methods.widgets.getChild(WidgetData.RESIZEMODE_MINIMAP[0], WidgetData.RESIZEMODE_MINIMAP[1]):
					methods.widgets.getChild(WidgetData.MINIMAP[0], WidgetData.MINIMAP[1]);
		if(!p.equals(new Point(-1, -1)) && (w!=null && w.getBounds().contains(p)))
			return true;
		return false;
	}
	public boolean isVisible(){
		Point pt = methods.calculations.locationToScreen(location);
		RSWidget viewport = null;
		if(methods.game.getClient().resizeMode()){
			viewport = methods.widgets.getChild(164, 7);
		}
		else{
			viewport = methods.widgets.getChild(163, 0);
		}
		return viewport!=null && viewport.getBounds().contains(pt);
	}
	public Point[] projectVertices(){
		RSModel model = getModel();
		if(model!=null){
			return model.projectVertices(location);
		}
		return new Point[]{};
	}
	public Polygon[] getWireframe(){
		RSModel model = getModel();
		if(model!=null){
			return model.getWireframe(location);
		}
		return new Polygon[]{};
	}
	public boolean getDisableRenderState(){
		if(itemNode!=null)
			return itemNode.getDisableRenderState();
		return false;
	}
	public void setDisableRenderState(){
		if(itemNode!=null)
			itemNode.setDisableRenderState();
	}
}
