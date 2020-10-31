package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="PlayerType")
public class PlayerType implements org.osrs.api.wrappers.PlayerType{

	@BField
	public int id;
	@BGetter
	@Override
	public int id(){return id;}
	@BField
	public int chatBadgeID;
	@BGetter
	@Override
	public int chatBadgeID(){return chatBadgeID;}
	@BField
	public boolean tradable;
	@BGetter
	@Override
	public boolean tradable(){return tradable;}
	@BField
	public boolean moderator;
	@BGetter
	@Override
	public boolean moderator(){return moderator;}
}