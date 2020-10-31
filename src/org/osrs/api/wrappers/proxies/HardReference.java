package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="HardReference")
public class HardReference extends Reference implements org.osrs.api.wrappers.HardReference{

	@BField
	public java.lang.Object hardReference;
	@BGetter
	@Override
	public java.lang.Object hardReference(){return hardReference;}
}