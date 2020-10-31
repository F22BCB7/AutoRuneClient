package org.osrs.api.objects;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;

import org.osrs.api.methods.MethodContext;
import org.osrs.api.methods.MethodProvider;
import org.osrs.api.wrappers.Cache;
import org.osrs.api.wrappers.HardReference;
import org.osrs.api.wrappers.ItemDefinition;
import org.osrs.api.wrappers.Node;
import org.osrs.api.wrappers.SoftReference;
import org.osrs.input.mouse.MouseHoverJob;
import org.osrs.input.mouse.MouseJob;
import org.osrs.input.mouse.MouseMoveListener;
import org.osrs.input.mouse.MouseTarget;

public class InventoryItem extends MethodProvider{
	private int id;
	private int inventoryIndex;
	private int stacksize;
	private ItemDefinition definition;
	public InventoryItem(MethodContext context, int id, int stacksize, int index) {
		super(context);
		this.id = id;
		this.stacksize = stacksize;	
		this.definition = getDefinition();
		this.inventoryIndex = index;
	}
	/**
	 * Returns the Items definition
	 * @return itemDefinition
	 */
	public ItemDefinition getDefinition() {
		if(definition!=null)
			return definition;
		definition = methods.itemDefinitionCache.get(id);
		if(definition!=null)
			return definition;
		Cache cache = methods.game.getClient().itemDefinitionCache();
		if(cache!=null){
			Node node = methods.nodes.lookup(cache.table(), id);
			if(node instanceof SoftReference){
				definition = (ItemDefinition)((SoftReference)node).softReference().get();
			}
			else if(node instanceof HardReference){
				definition = (ItemDefinition)((HardReference)node).hardReference();
			}
		}
		return definition;
	}
	/**
	 * Returns the item ID
	 * @return id
	 */
	public int getID(){
		return id;
	}
	/**
	 * Returns the item name if definition is not null.
	 * Returns null_def if no definition is found.
	 * @return name
	 */
	public String getName(){
		ItemDefinition def = getDefinition();
		if(def!=null)
			return def.name();
		return "null_def";
	}
	/**
	 * Returns the item inventory index
	 * @return index
	 */
	public int getIndex(){
		return inventoryIndex;
	}
	/**
	 * Returns the items stack size
	 * @return
	 */
	public int getStackSize(){
		return stacksize;
	}
	public Point getScreenLocation(){
		return getBounds().getLocation();
	}
	public Rectangle getBounds(){
		if(methods.game.getClient().resizeMode()){
			Rectangle r = methods.inventory.getBounds();
			int startX = r.x;
			int startY = r.y;
			int width = 36;
			int height = 32;
			int wPad = 6;
			int hPad = 4;
			int currentIndex=0;
			Rectangle r2 = new Rectangle();
			for(int y=0;y<7;++y){
				for(int x=0;x<4;++x){
					if(currentIndex==inventoryIndex){
						r2 = new Rectangle(startX+((width+wPad)*x), startY+((height+hPad)*y), width, height);
					}
					currentIndex++;
				}
			}
			//Trim the bounds....
			Rectangle r3 = new Rectangle(r2.x+3, r2.y+2, r2.width-6, r2.height-2);
			return r3;
		}
		int startX = 563;
		int startY = 213;
		int width = 36;
		int height = 32;
		int wPad = 6;
		int hPad = 4;
		int currentIndex=0;
		for(int y=0;y<7;++y){
			for(int x=0;x<4;++x){
				if(currentIndex==inventoryIndex){
					return new Rectangle(startX+((width+wPad)*x)+1, startY+((height+hPad)*y)+2, width-6, height-2);
				}
				currentIndex++;
			}
		}
		return new Rectangle(-1, -1, -1, -1);
	}
	public boolean isHovering(){
		for(int i=0;i<methods.menu.getItemCount();++i){
			if(methods.game.getMenuPrimaryArgs()[i]==id){
				if(methods.game.getMenuSecondaryArgs()[i]==inventoryIndex){
					return true;
				}
			}
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
                	if(isHovering())
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
	public boolean click(String action){
		boolean[] ret = new boolean[]{false};
        MouseHoverJob mouseHoverJob = methods.mouse.mouseHandler.createMouseHoverJob(new MouseMoveListener() {
            int count = 0;
            public void onMouseOverTarget(MouseJob mouseJob) {
                count++;
                if(count > methods.calculations.random(3, 16)){
                	ret[0] = (methods.menu.click(action));
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
		boolean[] ret = new boolean[]{false};
        MouseHoverJob mouseHoverJob = methods.mouse.mouseHandler.createMouseHoverJob(new MouseMoveListener() {
        	int count = 0;
            public void onMouseOverTarget(MouseJob mouseJob) {
                count++;
                if(count > methods.calculations.random(3, 16)){
                	ret[0] = (methods.menu.click(action, option));
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
	public Point getRandomScreenPoint() {
		Rectangle bounds = getBounds();
		Point start = new Point(bounds.x + 4, bounds.y + 4);
		return new Point(start.x + methods.calculations.random(bounds.width-4)
				, start.y + methods.calculations.random(bounds.height-4));
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
}
