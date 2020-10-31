package org.osrs.api.objects;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;

import org.osrs.api.methods.MethodContext;
import org.osrs.api.methods.MethodProvider;
import org.osrs.api.wrappers.ItemDefinition;
import org.osrs.input.mouse.MouseTarget;

public class WidgetItem extends MethodProvider{
	private String itemName;
	private int id;
	private int itemStackSize;
	private RSWidget itemWidget;
	private ItemDefinition definition;
	public WidgetItem(MethodContext context, RSWidget item, String name, int id, int stacksize){
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
	public Rectangle getBounds(){
		return itemWidget.getBounds();
	}
	public boolean isHovering(){
		return itemWidget.isHovering();
	}
	public boolean isHovering(String action){
		return itemWidget.isHovering(action);
	}
	public boolean isHovering(String action, String option){
		return itemWidget.isHovering(action, option);
	}
	public boolean hover(){
		return itemWidget.hover();
	}
	public boolean hover(String action){
		return itemWidget.hover(action);
	}
	public boolean hover(String action, String option){
		return itemWidget.hover(action, option);
	}
	public boolean click(){
		return itemWidget.click();
	}
	public boolean click(String action){
		return itemWidget.click(action);
	}
	public boolean click(String action, String option){
		return itemWidget.click(action, option);
	}
	public Point getRandomScreenPoint() {
		return itemWidget.getRandomScreenPoint();
	}
}
