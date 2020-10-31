package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="IdentityKit")
public class IdentityKit implements org.osrs.api.wrappers.IdentityKit{

	@BField
	public boolean interfaceDisplayed;
	@BGetter
	@Override
	public boolean interfaceDisplayed(){return interfaceDisplayed;}
	@BField
	public org.osrs.api.wrappers.Skins skins;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Skins skins(){return skins;}
	@BField
	public int partId;
	@BGetter
	@Override
	public int partId(){return partId;}
}