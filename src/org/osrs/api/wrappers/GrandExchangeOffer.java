package org.osrs.api.wrappers;

public interface GrandExchangeOffer{
	public byte state();
	public int itemID();
	public int price();
	public int totalQuantity();
	public int quantitySold();
	public int totalSpent();
}