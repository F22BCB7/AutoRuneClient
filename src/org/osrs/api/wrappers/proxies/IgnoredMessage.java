package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="IgnoredMessage")
public class IgnoredMessage extends Nameable implements org.osrs.api.wrappers.IgnoredMessage{

	@BField
	public int listIndex;
	@BGetter
	@Override
	public int listIndex(){return listIndex;}
}