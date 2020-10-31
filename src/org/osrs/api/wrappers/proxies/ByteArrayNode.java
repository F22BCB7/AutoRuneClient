package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="ByteArrayNode")
public class ByteArrayNode extends Node implements org.osrs.api.wrappers.ByteArrayNode{

	@BField
	public byte[] bytes;
	@BGetter
	@Override
	public byte[] bytes(){return bytes;}
}