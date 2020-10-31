package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="FriendListLink")
public class FriendListLink extends Link implements org.osrs.api.wrappers.FriendListLink{

	@BField
	public short world;
	@BGetter
	@Override
	public short world(){return world;}
	@BField
	public org.osrs.api.wrappers.NameComposite name;
	@BGetter
	@Override
	public org.osrs.api.wrappers.NameComposite name(){return name;}
	@BField
	public int startCycle;
	@BGetter
	@Override
	public int startCycle(){return startCycle;}
}