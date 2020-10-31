package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="ObjectNode")
public class ObjectNode extends Node implements org.osrs.api.wrappers.ObjectNode{

	@BField
	public java.lang.Object value;
	@BGetter
	@Override
	public java.lang.Object value(){return value;}
}