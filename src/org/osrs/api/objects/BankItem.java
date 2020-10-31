package org.osrs.api.objects;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;

import org.osrs.api.constants.WidgetData;
import org.osrs.api.methods.MethodContext;
import org.osrs.api.methods.MethodProvider;
import org.osrs.api.wrappers.ItemDefinition;
import org.osrs.input.mouse.MouseHoverJob;
import org.osrs.input.mouse.MouseJob;
import org.osrs.input.mouse.MouseMoveListener;
import org.osrs.input.mouse.MouseTarget;

public class BankItem extends MethodProvider{
	private String itemName;
	private int id;
	private int itemStackSize;
	private RSWidget itemWidget;
	private ItemDefinition definition;
	public BankItem(MethodContext context, RSWidget item, String name, int id, int stacksize){
		super(context);
		this.itemWidget=item;
		this.itemName=name;
		this.id = id;
		this.definition = getDefinition();
		this.itemStackSize = stacksize;	
	}
	/**
	 * Returns the Items definition
	 * @return itemDefinition
	 */
	public ItemDefinition getDefinition() {
		if(definition!=null)
			return definition;
		definition = methods.itemDefinitionCache.get(id);
		return definition;
	}
	public int getID(){
		return id;
	}
	/**
	 * Returns the item name
	 * @return name
	 */
	public String getName(){
		return itemName;
	}
	/**
	 * Returns the items stack size
	 * @return
	 */
	public int getStackSize(){
		return itemStackSize;
	}
	/**
	 * Returns the Widget that holds this item
	 * @return widget
	 */
	public RSWidget getWidget(){
		return itemWidget;
	}
	public boolean isVisible(){
		Rectangle r = getBounds();
		RSWidget itemSlots = methods.widgets.getChild(WidgetData.BANK_ITEM_SLOTS[0], 10);
		//RSWidget basePos = methods.widgets.getChild(WidgetData.RESIZEMODE_BANK_WINDOW_POSITION[0], WidgetData.RESIZEMODE_BANK_WINDOW_POSITION[1]);
		int y = itemSlots.getAbsoluteY();
		if(methods.game.getClient().resizeMode()){
			//y+=basePos.getAbsoluteY();
		}
		return r.y<itemSlots.getHeight()+y && r.y>y;
	}
	public Rectangle getBounds(){
		RSWidget itemSlots = methods.widgets.getChild(WidgetData.BANK_ITEM_SLOTS[0], WidgetData.BANK_ITEM_SLOTS[1]);
		//RSWidget basePos = methods.widgets.getChild(WidgetData.RESIZEMODE_BANK_WINDOW_POSITION[0], WidgetData.RESIZEMODE_BANK_WINDOW_POSITION[1]);
		int x = itemSlots.getAbsoluteX();
		int y = itemSlots.getAbsoluteY();
		if(methods.game.getClient().resizeMode()){
			//x+=basePos.getAbsoluteX();
			//y+=basePos.getAbsoluteY();
		}
		x+=itemWidget.getRelativeX();
		y+=itemWidget.getRelativeY();
		return new Rectangle(x, y, itemWidget.getWidth(), itemWidget.getHeight());
	}
	public boolean isHovering(){
		return getBounds().contains(methods.mouse.getLocation());
	}
	public boolean isHovering(String action){
		if(getBounds().contains(methods.mouse.getLocation())){
			return methods.menu.contains(action);
		}
		return false;
	}
	public boolean isHovering(String action, String option){
		if(getBounds().contains(methods.mouse.getLocation())){
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
