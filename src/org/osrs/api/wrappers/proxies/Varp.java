package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="Varp")
public class Varp extends EntityNode implements org.osrs.api.wrappers.Varp{

	@BField
	public int configID;
	@BGetter
	@Override
	public int configID(){return configID;}
	@BField
	public int leastSignificantBit;
	@BGetter
	@Override
	public int leastSignificantBit(){return leastSignificantBit;}
	@BField
	public int mostSignificantBit;
	@BGetter
	@Override
	public int mostSignificantBit(){return mostSignificantBit;}
}