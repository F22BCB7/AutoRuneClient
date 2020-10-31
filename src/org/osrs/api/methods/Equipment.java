package org.osrs.api.methods;

import java.util.ArrayList;

import org.osrs.api.constants.WidgetData;
import org.osrs.api.objects.RSInterface;
import org.osrs.api.objects.RSWidget;
import org.osrs.api.objects.WidgetItem;

public class Equipment extends MethodDefinition{
	public Equipment(MethodContext context){
		super(context);
	}
	/* Widget Indexs */
	public static final int INTERFACE_EQUIPMENT = 387;
	public static final int ITEM_SLOTS = 12;
	
	public static final int HELMET = 6;
	public static final int CAPE = 7;
	public static final int NECK = 8;
	public static final int WEAPON = 9;
	public static final int BODY = 10;
	public static final int SHIELD = 11;
	public static final int LEGS = 12;
	public static final int HANDS = 13;
	public static final int FEET = 14;
	public static final int RING = 15;
	public static final int POCKET = 16;
	public static final int AMMO = 17;
	public static final int[] childItemSlots = new int[]{HELMET, CAPE, NECK, WEAPON, BODY, SHIELD, LEGS, HANDS, FEET, RING, POCKET, AMMO};
	/**
	 * Gets the equipment array.
	 * @return An array containing all equipped items
	 */
	public WidgetItem[] getItems() {
		RSInterface iface = methods.widgets.get(WidgetData.EQUIPMENT_INTERFACE[0]);
		if(iface!=null){
			RSWidget[] equip = iface.getChildren();
			ArrayList<WidgetItem> items = new ArrayList<WidgetItem>();
			for(int index : childItemSlots){
				RSWidget ic = equip[index];
				for(RSWidget w : ic.getChildren()){
					if(w.getInternal().itemID()!=-1){
						items.add(new WidgetItem(methods, ic, ic.getInternal().targetVerb(), w.getInternal().itemID(), w.getInternal().itemQuantity()));
						break;
					}
				}
			}
			return items.toArray(new WidgetItem[]{});
		}
		return new WidgetItem[]{};
	}
	/**
	 * Returns the number of items equipped excluding stack sizes.
	 * @return Amount of items currently equipped.
	 */
	public int getCount() {
		return ITEM_SLOTS - getCount(-1);
	}
	/**
	 * Returns the number of items matching a given ID equipped excluding stack
	 * sizes.
	 * @param itemID The item ID to count. Same as the equipment/item id in the
	 *               inventory.
	 * @return Amount of specified item currently equipped.
	 * @see #getItems()
	 */
	public int getCount(final int itemID) {
		int count = 0;
		for (WidgetItem item : getItems()) {
			if (item.getID() == itemID) {
				count++;
			}
		}
		return count;
	}
	/**
	 * Checks whether the player has all of the given items equipped.
	 * @param items The item ID to check for. Same as the equipment/item id in the
	 *              inventory.
	 * @return <tt>true</tt> if specified item is currently equipped; otherwise
	 *         <tt>false</tt>.
	 */
	public boolean containsAll(final int... items) {
		WidgetItem[] equips = getItems();
		int count = 0;
		for (int item : items) {
			for (WidgetItem equip : equips) {
				if (equip.getID() == item) {
					count++;
					break;
				}
			}
		}
		return count == items.length;
	}
	/**
	 * Checks if the player has one (or more) of the given items equipped.
	 * @param items The IDs of items to check for.
	 * @return <tt>true</tt> if the player has one (or more) of the given items
	 *         equipped; otherwise <tt>false</tt>.
	 */
	public boolean containsOneOf(final int... items) {
		for (WidgetItem item : getItems()) {
			for (int id : items) {
				if (item.getID() == id) {
					return true;
				}
			}
		}
		return false;
	}
}
