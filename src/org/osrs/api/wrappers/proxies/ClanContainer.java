package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="ClanContainer")
public class ClanContainer extends NameableContainer implements org.osrs.api.wrappers.ClanContainer{

	@BField
	public String clanName;
	@BGetter
	@Override
	public String clanName(){return clanName;}
	@BField
	public String clanOwner;
	@BGetter
	@Override
	public String clanOwner(){return clanOwner;}
	@BField
	public org.osrs.api.wrappers.JagexLoginType loginType;
	@BGetter
	@Override
	public org.osrs.api.wrappers.JagexLoginType loginType(){return loginType;}
}