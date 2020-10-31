package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="RuneScript")
public class RuneScript extends EntityNode implements org.osrs.api.wrappers.RuneScript{

	@BField
	public int intStackCount;
	@BGetter
	@Override
	public int intStackCount(){return intStackCount;}
	@BField
	public int[] intOperands;
	@BGetter
	@Override
	public int[] intOperands(){return intOperands;}
	@BField
	public int localStringCount;
	@BGetter
	@Override
	public int localStringCount(){return localStringCount;}
	@BField
	public int localIntCount;
	@BGetter
	@Override
	public int localIntCount(){return localIntCount;}
	@BField
	public org.osrs.api.wrappers.FixedSizeDeque[] switches;
	@BGetter
	@Override
	public org.osrs.api.wrappers.FixedSizeDeque[] switches(){return switches;}
	@BField
	public int[] instructions;
	@BGetter
	@Override
	public int[] instructions(){return instructions;}
	@BField
	public int stringStackCount;
	@BGetter
	@Override
	public int stringStackCount(){return stringStackCount;}
	@BField
	public String[] stringOperands;
	@BGetter
	@Override
	public String[] stringOperands(){return stringOperands;}
}