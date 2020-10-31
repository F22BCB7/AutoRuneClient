package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="Skins")
public class Skins extends Node implements org.osrs.api.wrappers.Skins{

	@BField
	public int[][] skinList;
	@BGetter
	@Override
	public int[][] skinList(){return skinList;}
	@BField
	public int count;
	@BGetter
	@Override
	public int count(){return count;}
	@BField
	public int id;
	@BGetter
	@Override
	public int id(){return id;}
	@BField
	public int[] opcodes;
	@BGetter
	@Override
	public int[] opcodes(){return opcodes;}
}