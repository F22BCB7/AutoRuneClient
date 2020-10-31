package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="ScriptState")
public class ScriptState implements org.osrs.api.wrappers.ScriptState{

	@BField
	public int[] localInts;
	@BGetter
	@Override
	public int[] localInts(){return localInts;}
	@BField
	public int stackIndex;
	@BGetter
	@Override
	public int stackIndex(){return stackIndex;}
	@BField
	public String[] localStrings;
	@BGetter
	@Override
	public String[] localStrings(){return localStrings;}
	@BField
	public org.osrs.api.wrappers.RuneScript invokedFromScript;
	@BGetter
	@Override
	public org.osrs.api.wrappers.RuneScript invokedFromScript(){return invokedFromScript;}
}