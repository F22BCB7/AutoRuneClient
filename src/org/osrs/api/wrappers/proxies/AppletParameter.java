package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="AppletParameter")
public class AppletParameter implements org.osrs.api.wrappers.AppletParameter{

	@BField
	public String key;
	@BGetter
	@Override
	public String key(){return key;}
}