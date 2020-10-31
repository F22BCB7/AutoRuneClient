package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="PendingSpawn")
public class PendingSpawn extends Node implements org.osrs.api.wrappers.PendingSpawn{

	@BField
	public int hash;
	@BGetter
	@Override
	public int hash(){return hash;}
	@BField
	public int face;
	@BGetter
	@Override
	public int face(){return face;}
	@BField
	public int configID;
	@BGetter
	@Override
	public int configID(){return configID;}
	@BField
	public int level;
	@BGetter
	@Override
	public int level(){return level;}
	@BField
	public int type;
	@BGetter
	@Override
	public int type(){return type;}
	@BField
	public int x;
	@BGetter
	@Override
	public int x(){return x;}
	@BField
	public int y;
	@BGetter
	@Override
	public int y(){return y;}
	@BField
	public int id;
	@BGetter
	@Override
	public int id(){return id;}
	@BField
	public int modelID;
	@BGetter
	@Override
	public int modelID(){return modelID;}
	@BField
	public int orientation;
	@BGetter
	@Override
	public int orientation(){return orientation;}
	@BField
	public int delay;
	@BGetter
	@Override
	public int delay(){return delay;}
	@BField
	public int hitpoints;
	@BGetter
	@Override
	public int hitpoints(){return hitpoints;}
}