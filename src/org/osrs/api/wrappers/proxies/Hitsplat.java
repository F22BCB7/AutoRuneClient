package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="Hitsplat")
public class Hitsplat extends EntityNode implements org.osrs.api.wrappers.Hitsplat{

	@BField
	public int fontID;
	@BGetter
	@Override
	public int fontID(){return fontID;}
	@BField
	public int textColor;
	@BGetter
	@Override
	public int textColor(){return textColor;}
	@BField
	public int iconID;
	@BGetter
	@Override
	public int iconID(){return iconID;}
	@BField
	public int leftSpriteID;
	@BGetter
	@Override
	public int leftSpriteID(){return leftSpriteID;}
	@BField
	public int middleSpriteID;
	@BGetter
	@Override
	public int middleSpriteID(){return middleSpriteID;}
	@BField
	public int rightSpriteID;
	@BGetter
	@Override
	public int rightSpriteID(){return rightSpriteID;}
	@BField
	public int offsetX;
	@BGetter
	@Override
	public int offsetX(){return offsetX;}
	@BField
	public int duration;
	@BGetter
	@Override
	public int duration(){return duration;}
	@BField
	public int offsetY;
	@BGetter
	@Override
	public int offsetY(){return offsetY;}
	@BField
	public int fade;
	@BGetter
	@Override
	public int fade(){return fade;}
	@BField
	public int comparisonType;
	@BGetter
	@Override
	public int comparisonType(){return comparisonType;}
	@BField
	public int spacing;
	@BGetter
	@Override
	public int spacing(){return spacing;}
	@BField
	public int varbitID;
	@BGetter
	@Override
	public int varbitID(){return varbitID;}
	@BField
	public int varpIndex;
	@BGetter
	@Override
	public int varpIndex(){return varpIndex;}
	@BField
	public String amount;
	@BGetter
	@Override
	public String amount(){return amount;}
	@BField
	public int[] transformIDs;
	@BGetter
	@Override
	public int[] transformIDs(){return transformIDs;}
}