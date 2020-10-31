package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="FriendManager")
public class FriendManager implements org.osrs.api.wrappers.FriendManager{

	@BField
	public org.osrs.api.wrappers.FriendContainer friendContainer;
	@BGetter
	@Override
	public org.osrs.api.wrappers.FriendContainer friendContainer(){return friendContainer;}
	@BField
	public org.osrs.api.wrappers.IgnoreContainer ignoreContainer;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IgnoreContainer ignoreContainer(){return ignoreContainer;}
	@BField
	public int status;
	@BGetter
	@Override
	public int status(){return status;}
	@BField
	public org.osrs.api.wrappers.JagexLoginType loginType;
	@BGetter
	@Override
	public org.osrs.api.wrappers.JagexLoginType loginType(){return loginType;}
}