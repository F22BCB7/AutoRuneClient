package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="IncomingPacketMeta")
public class IncomingPacketMeta implements org.osrs.api.wrappers.IncomingPacketMeta{

	@BField
	public int id;
	@BGetter
	@Override
	public int id(){return id;}
	@BField
	public int length;
	@BGetter
	@Override
	public int length(){return length;}
}