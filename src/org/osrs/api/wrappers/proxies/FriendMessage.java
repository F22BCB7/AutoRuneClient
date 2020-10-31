package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="FriendMessage")
public class FriendMessage extends ChatPlayer implements org.osrs.api.wrappers.FriendMessage{

	@BField
	public boolean isReferred;
	@BGetter
	@Override
	public boolean isReferred(){return isReferred;}
	@BField
	public boolean isReferrer;
	@BGetter
	@Override
	public boolean isReferrer(){return isReferrer;}
}