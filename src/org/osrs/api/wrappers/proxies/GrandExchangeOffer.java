package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="GrandExchangeOffer")
public class GrandExchangeOffer implements org.osrs.api.wrappers.GrandExchangeOffer{

	@BField
	public byte state;
	@BGetter
	@Override
	public byte state(){return state;}
	@BField
	public int itemID;
	@BGetter
	@Override
	public int itemID(){return itemID;}
	@BField
	public int price;
	@BGetter
	@Override
	public int price(){return price;}
	@BField
	public int totalQuantity;
	@BGetter
	@Override
	public int totalQuantity(){return totalQuantity;}
	@BField
	public int quantitySold;
	@BGetter
	@Override
	public int quantitySold(){return quantitySold;}
	@BField
	public int totalSpent;
	@BGetter
	@Override
	public int totalSpent(){return totalSpent;}
}