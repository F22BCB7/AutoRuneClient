package org.osrs.api.methods;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class Menu extends MethodDefinition{
	public Menu(MethodContext context){
		super(context);
	}
	/**
	 * Attempts to click the given menu action
	 * @param action
	 * @return true if clicked
	 */
	public boolean click(String action) {
		return clickIndex(getIndex(action));
	}
	/**
	 * Attempts to click the given menu action+option
	 * @param action
	 * @param option
	 * @return true if action+option is clicked
	 */
	public boolean click(String action, String option) {
		return clickIndex(getIndex(action, option));
	}
	/**
	 * Attempts to click the given menu index.
	 * @param index
	 * @return true if the menu index was clicked
	 */
	public boolean clickIndex(int index) {
		try{
			if(index<0 || index>=getItemCount()){
				//System.out.println("[clickIndex] index invalid : "+index+":"+getItemCount());
				return false;
			}
			if(index==0){
				//System.out.println("[clickIndex] 0 index action.");
				methods.mouse.click();
				//sleep(300, 600);
				return true;
			}
			String action = getActions()[index];
			String option = getOptions()[index];
			if(!isOpen()){
				//System.out.println("[clickIndex] Right clicking...");
				methods.mouse.rightClick();
				for(int i=0;i<20;++i){
					sleep(50, 200);
					if(isOpen())
						break;
				}
				sleep(50, 200);
			}
			if(isOpen()) {
				//System.out.println("[clickIndex] Menu opened.");
				Point clickPoint = getClickPoint(index);
				methods.mouse.move(clickPoint);
				sleep(50, 300);
				if(isHoveringIndex(index)){
					methods.mouse.click();
					for(int j=0;j<20;++j){
						sleep(50, 200);
						if(!isOpen())
							break;
					}
					sleep(50, 200);
					return true;
				}
				//System.out.println("Not hovering index! : "+index+":"+action+":"+option);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * Checks to see if the menu contains the specified string.
	 * @param string
	 * @return true if menu contains the string
	 */
	public boolean contains(String action){
		return getIndex(action)!=-1;
	}
	/**
	 * Checks to see if the menu contains the specified action+option.
	 * @param action
	 * @param option
	 * @return true if menu contains the action+option
	 */
	public boolean contains(String action, String option){
		return getIndex(action, option)!=-1;
	}
	/**
	 * Retrieves the array of menu actions.
	 * @return menuActions
	 */	
	public String[] getActions() {
		try{
			int itemCount = getItemCount();
			String[] actions = methods.game.getClient().menuActions();
			String[] targets = new String[itemCount];
			int real=0;
			if(actions.length<itemCount)itemCount=actions.length;
			for(int i = 0;real < itemCount && i<actions.length;i++) {
				if(actions[i] != null && !actions[i].equals("null")) {
					targets[real]=actions[i];
					real++;
				}
			}
			if(real==0)return new String[]{};
			String[] ret = new String[real];
			int k = real-1;
			for(String st : targets){
				if(k<0)
					break;
				ret[k]=st;
				k--;
			}
			return ret;
		}
		catch(@SuppressWarnings("unused") Exception e){
		}
		return new String[]{};
	}
	@SuppressWarnings("unused")
	private static ArrayList<String> reverse(ArrayList<String> list) {
	    for(int i = 0, j = list.size() - 1; i < j; i++) {
	        list.add(i, list.remove(j));
	    }
	    return list;
	}
	/**
	 * Gets the current bounds of the menu.
	 *
	 * @return The menu bounds.
	 */
	public Rectangle getBounds() {
		return new Rectangle(methods.game.getClient().menuX(), methods.game.getClient().menuY(), methods.game.getClient().menuWidth(), methods.game.getClient().menuHeight());
	}
	/**
	 * Returns the 2D screen-space bounds of a specified action/option index
	 * from the menu if the menu is open.
	 * @param index
	 * @return the bounds of the specified menu index
	 */
	public Rectangle getBounds(int index){
		if (index == -1 || !isOpen() || index > getItemCount()) 
			return new Rectangle(-1, -1, 0, 0);
		return new Rectangle(getX()+1, getY() + 18 + (index * 15), getWidth()-2, 16);
	}
	/**
	 * Gets a clickable point at a certain index on the menu.
	 *
	 * @param index The slot to click.
	 * @return The point.
	 */
	public Point getClickPoint(int index) {
		Rectangle r = getBounds(index);
		int xOff = new Random().nextInt(getWidth()-5)+5;
		int yOff = new Random().nextInt((int)r.getHeight()-5)+5;
		return new Point(r.x + xOff, r.y + yOff);
	}
	/**
	 * Gets the Menu screen height.
	 * @return height
	 */
	public int getHeight(){
		return methods.game.getClient().menuHeight();
	}
	/**
	 * Gets the index of the given action in the menu
	 * if it exists.
	 * @param action
	 * @return index of the action; -1 if menu does not contain
	 */
	public int getIndex(String action) {
		action=action.toLowerCase();
		String[] choices = getActions();
		for (int i=0;i<choices.length;++i){
			if(choices[i]==null)continue;
			if(choices[i].toLowerCase().equals(action.toLowerCase())){
				return i;
			}
		}
		return -1;
	}
	/**
	 * Gets the index of the given action+option in the menu
	 * if it exists.
	 * @param action
	 * @param option
	 * @return index of the action+option; -1 if menu does not contain
	 */
	public int getIndex(String action, String option) {
		action=action.toLowerCase();
		String[] actions = getActions();
		String[] options = getOptions();
		for (int i=0;i<Math.min(actions.length, options.length);++i){
			if(actions[i]==null)continue;
			if(actions[i].toLowerCase().equals(action.toLowerCase()) &&
					options[i].toLowerCase().equals(option.toLowerCase()))
				return i;
		}
		return -1;
	}
	/**
	 * Gets the Menu item count.
	 * @return itemCount
	 */
	public int getItemCount(){
		return methods.game.getClient().menuItemCount();
	}
	public String[] getItems(){
		String[] actions = getActions();
		String[] options = getOptions();
		String[] items = new String[Math.min(actions.length, options.length)];
		for(int i=0;i<items.length;++i){
			items[i]=actions[i]+" "+options[i];
		}
		return items;
	}
	/**
	 * Returns the menu screen location.
	 * @return point
	 */
	public Point getLocation(){
		if(isOpen())
			return new Point(methods.game.getClient().menuX(), methods.game.getClient().menuY());
		return methods.mouse.getLocation();
	}
	/**
	 * Grabs the menu options.
	 * @return menuOptions
	 */
	public String[] getOptions(){
		try{
			int itemCount = getItemCount();
			String[] s = methods.game.getClient().menuTargets();
			String[] targets = new String[itemCount];
			if(s.length<itemCount)itemCount=s.length;
			int real=0;
			for(int i = 0;real < itemCount && i<s.length;i++) {
				if(s[i] != null && !s[i].equals("null")) {
					targets[real]=s[i];
					real++;
				}
			}
			String[] ret = new String[real];
			int k = ret.length-1;
			for(String st : targets){
				if(st.startsWith("<col="))
					ret[k]=st.substring(st.indexOf(">")+1);
				else
					ret[k]=st;
				k--;
			}
			return ret;
		}
		catch(@SuppressWarnings("unused") Exception e){
		}
		return new String[]{};
	}
	/**
	 * Grabs the top-most menu action
	 * @return topAction
	 */
	public String getTopAction(){
		String[] actions = methods.game.getClient().menuActions();
		if(actions!=null && actions.length>0)
			return actions[0];
		return "";
	}
	/**
	 * Grabs the top-most menu option
	 * @return topOption
	 */
	public String getTopOption(){
		String[] options = methods.game.getClient().menuTargets();
		if(options!=null && options.length>0)
			return options[0];
		return "";
	}
	/**
	 * Grabs the top-most menu text
	 * @return topOption
	 */
	public String getTopText(){
		return getTopAction()+" "+getTopOption();
	}
	/**
	 * Gets the Menu screen width.
	 * @return width
	 */
	public int getWidth(){
		return methods.game.getClient().menuWidth();
	}
	/**
	 * Gets the Menu screen locations' X coordinate.
	 * @return point.x
	 */
	public int getX(){
		return methods.game.getClient().menuX();
	}
	/**
	 * Gets the Menu screen locations' Y coordinate.
	 * @return point.y
	 */
	public int getY(){
		return methods.game.getClient().menuY();
	}
	/**
	 * Returns true if menu is open
	 * and mouse is hovering over desired index.
	 * @param index
	 * @return isHovering
	 */
	public boolean isHoveringIndex(int index){
		if(isOpen()){
			int mouseX = methods.game.getMouseX();
			int mouseY = methods.game.getMouseY();
			/*
			int menuX = methods.game.getMenuX();
			int menuY = methods.game.getMenuY();
			int menuWidth = methods.game.getMenuWidth();
			int menuTextY = menuY + 31 + (methods.game.getMenuItemCount() - 1 - index) * 15;
            if(mouseX > menuX && mouseX < menuX + menuWidth && mouseY > menuTextY - 13 && mouseY < menuTextY + 3) {
            	return true;
            }*/
			return getBounds(index).contains(mouseX, mouseY);
		}
		return false;
	}
	/**
	 * Returns whether or not the Menu is open.
	 * @return true if menu is open
	 */
	public boolean isOpen(){
		return methods.game.getClient().menuOpen();
	}
}
