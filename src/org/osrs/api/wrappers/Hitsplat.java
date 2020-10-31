package org.osrs.api.wrappers;

public interface Hitsplat extends EntityNode{
	public int fontID();
	public int textColor();
	public int iconID();
	public int leftSpriteID();
	public int middleSpriteID();
	public int rightSpriteID();
	public int offsetX();
	public int duration();
	public int offsetY();
	public int fade();
	public int comparisonType();
	public int spacing();
	public int varbitID();
	public int varpIndex();
	public String amount();
	public int[] transformIDs();
}