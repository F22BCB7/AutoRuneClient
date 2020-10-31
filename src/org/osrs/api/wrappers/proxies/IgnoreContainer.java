package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="IgnoreContainer")
public class IgnoreContainer extends NameableContainer implements org.osrs.api.wrappers.IgnoreContainer{

	@BField
	public org.osrs.api.wrappers.JagexLoginType loginType;
	@BGetter
	@Override
	public org.osrs.api.wrappers.JagexLoginType loginType(){return loginType;}
}