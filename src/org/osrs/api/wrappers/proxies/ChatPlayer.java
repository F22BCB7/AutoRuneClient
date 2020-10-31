package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="ChatPlayer")
public class ChatPlayer extends Nameable implements org.osrs.api.wrappers.ChatPlayer{

	@BField
	public int world;
	@BGetter
	@Override
	public int world(){return world;}
	@BField
	public int listIndex;
	@BGetter
	@Override
	public int listIndex(){return listIndex;}
	@BField
	public int rank;
	@BGetter
	@Override
	public int rank(){return rank;}
}