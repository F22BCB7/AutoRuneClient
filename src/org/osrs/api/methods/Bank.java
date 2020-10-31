package org.osrs.api.methods;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import org.osrs.api.constants.WidgetData;
import org.osrs.api.objects.BankItem;
import org.osrs.api.objects.InventoryItem;
import org.osrs.api.objects.RSWidget;

public class Bank extends MethodDefinition{
	public Bank(MethodContext context){
		super(context);
	}
	/**
	 * Attempts to close the bank
	 * @return bankClosed
	 */
	public boolean close(){
		int[] wi = WidgetData.BANK_CLOSE;
		RSWidget w = methods.widgets.getChild(wi[0],  wi[1],  wi[2]);
		if(w!=null){
			if(w.click("Close")){
				for(int i=0;i<20;++i){
					sleep(50);
					if(!isOpen())
						break;
				}
				return !isOpen();
			}
		}
		return false;
	}
	public boolean depositEquipment(){
		RSWidget button = methods.widgets.getChild(12, 44);
		if(button!=null){
			return button.click("Deposit worn items");
		}
		return false;
	}
	public boolean depositInventory(){
		RSWidget button = methods.widgets.getChild(12, 42);
		if(button!=null){
			return button.click("Deposit inventory");
		}
		return false;
	}
	public boolean depositItem(int itemID, int amount){
		if(itemID==-1 || amount==-1)
			return false;
		if(isOpen()){
			InventoryItem item = methods.inventory.getItem(itemID);
			if(item!=null){
				String action = "";
				if(methods.inventory.getCount(itemID)>1 || item.getStackSize()>1){
					if(amount==0)
						action="Deposit-All";
					else if(amount==5 || amount==10)
						action="Deposit-"+amount;
					else{
						Point p = item.getRandomScreenPoint();
						methods.mouse.move(p);
						try {
							Thread.sleep(new Random().nextInt(100)+50);
						} catch (InterruptedException e) {
						}
						methods.mouse.rightClick();
						try {
							for(int i=0;i<20;++i){
								Thread.sleep(50);
								if(methods.menu.isOpen())
									break;
							}
						} catch (InterruptedException e) {
						}
						if(!methods.menu.isOpen())
							return false;
						if(methods.menu.contains("Deposit-"+amount))
							return methods.menu.click("Deposit-"+amount);
						if(!methods.menu.click("Deposit-X"))
							return false;
						try {
							Thread.sleep(1500);//should be changed to check interfaces
						} catch (InterruptedException e) {
						}
						methods.keyboard.sendKeys(""+amount);
						methods.keyboard.sendKey((char) KeyEvent.VK_ENTER);
						return true;
					}
				}
				else
					action = "Deposit";
				if(action!="")
					return item.click(action);
			}
		}
		return false;
	}
	public boolean depositItem(String name, int amount){
		return depositItem(methods.inventory.getItemID(name), amount);
	}
	/**
	 * Grabs a specified item by id from the bank.
	 * Will be NULL if none found.
	 * @param id
	 * @return interfaceItem
	 */
	public BankItem getItem(int id){
		for(BankItem item : getItems())
			if(item.getID()==id)
				return item;
		return null;
	}
	/**
	 * Grabs the stack size of a given item by id from
	 * the bank.
	 * @param id
	 * @return itemCount
	 */
	public int getItemCount(int id){
		BankItem item = getItem(id);
		if(item!=null)
			return item.getStackSize();
		return 0;
	}
	/**
	 * Grabs all items from the bank.
	 * Does not return NULL, but rather BankItem[0].
	 * @return bankItems
	 */
	public BankItem[] getItems(){
		RSWidget bankSlots = methods.widgets.getChild(WidgetData.BANK_ITEM_SLOTS[0], WidgetData.BANK_ITEM_SLOTS[1]);
		if(bankSlots!=null){
			ArrayList<BankItem> items = new ArrayList<BankItem>();
			for(RSWidget item : bankSlots.getChildren()){
				if(item!=null && item.getInternal()!=null && item.getInternal().itemID()!=-1 && item.getInternal().itemID()!=6512){
					items.add(new BankItem(methods, item, item.getInternal().targetVerb(), item.getInternal().itemID(), item.getInternal().itemQuantity()));
				}
			}
			return items.toArray(new BankItem[]{});
		}
		return new BankItem[]{};
	}	
	/**
	 * Grabs the max bank size (how many items your bank will hold).
	 * Returns -1 if widget not found (outdated).
	 * @return maxBankSize
	 */
	public int getMaxBankSize(){
		RSWidget w = methods.widgets.getChild(WidgetData.BANK_MAX_SPACE[0], WidgetData.BANK_MAX_SPACE[1]);
		if(w!=null){
			return Integer.parseInt(w.getInternal().disabledText());
		}
		return -1;
	}
	public RSWidget[] getTabs(){
		RSWidget w = methods.widgets.getChild(WidgetData.BANK_TABS[0],  WidgetData.BANK_TABS[1]);
		if(w!=null){
			ArrayList<RSWidget> temp = new ArrayList<RSWidget>();
			for(RSWidget w2 : w.getChildren()){
				if(w2.getInternal().isHidden())
					continue;
				temp.add(w2);
			}
			return temp.toArray(new RSWidget[]{});
		}
		return new RSWidget[]{};
	}
	/**
	 * Grabs the bank size (how many items your bank has).
	 * Returns -1 if widget not found (outdated).
	 * @return bankSize
	 */
	public int getUsedBankspaceCount(){
		RSWidget w = methods.widgets.getChild(WidgetData.BANK_ITEM_COUNT[0], WidgetData.BANK_ITEM_COUNT[1]);
		if(w!=null){
			return Integer.parseInt(w.getInternal().disabledText());
		}
		return -1;
	}
	/**
	 * Checks to see if the bank is full
	 * @return bankFull
	 */
	public boolean isBankFull(){
		return getUsedBankspaceCount()>=getMaxBankSize();
	}
	/**
	 * Checks to see if the bank is open
	 * @return bankOpen
	 */
	public boolean isOpen() {
		RSWidget ic = methods.widgets.getChild(WidgetData.BANK_WINDOW[0], WidgetData.BANK_WINDOW[1]);
		if(ic!=null){
			return true;
		}
		return false;
	}
}
