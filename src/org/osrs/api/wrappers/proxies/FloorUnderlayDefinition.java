package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="FloorUnderlayDefinition")
public class FloorUnderlayDefinition extends EntityNode implements org.osrs.api.wrappers.FloorUnderlayDefinition{

	@BField
	public int rgbColor;
	@BGetter
	@Override
	public int rgbColor(){return rgbColor;}
	@BField
	public int hueMultiplier;
	@BGetter
	@Override
	public int hueMultiplier(){return hueMultiplier;}
	@BField
	public int saturation;
	@BGetter
	@Override
	public int saturation(){return saturation;}
	@BField
	public int lightness;
	@BGetter
	@Override
	public int lightness(){return lightness;}
	@BField
	public int hue;
	@BGetter
	@Override
	public int hue(){return hue;}
}