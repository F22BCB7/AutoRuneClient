package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="JagexLoginType")
public class JagexLoginType implements org.osrs.api.wrappers.JagexLoginType{

	@BField
	public int key;
	@BGetter
	@Override
	public int key(){return key;}
	@BField
	public String identifier;
	@BGetter
	@Override
	public String identifier(){return identifier;}
}