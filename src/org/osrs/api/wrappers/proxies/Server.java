package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="Server")
public class Server implements org.osrs.api.wrappers.Server{

	@BField
	public int number;
	@BGetter
	@Override
	public int number(){return number;}
	@BField
	public int members;
	@BGetter
	@Override
	public int members(){return members;}
	@BField
	public int population;
	@BGetter
	@Override
	public int population(){return population;}
	@BField
	public String domain;
	@BGetter
	@Override
	public String domain(){return domain;}
	@BField
	public String activity;
	@BGetter
	@Override
	public String activity(){return activity;}
	@BField
	public int location;
	@BGetter
	@Override
	public int location(){return location;}
	@BField
	public int index;
	@BGetter
	@Override
	public int index(){return index;}
}