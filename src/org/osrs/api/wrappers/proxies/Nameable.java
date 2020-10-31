package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="Nameable")
public class Nameable implements org.osrs.api.wrappers.Nameable{

	@BField
	public org.osrs.api.wrappers.NameComposite previousName;
	@BGetter
	@Override
	public org.osrs.api.wrappers.NameComposite previousName(){return previousName;}
	@BField
	public org.osrs.api.wrappers.NameComposite name;
	@BGetter
	@Override
	public org.osrs.api.wrappers.NameComposite name(){return name;}
}