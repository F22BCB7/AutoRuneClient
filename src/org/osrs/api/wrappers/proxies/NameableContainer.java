package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="NameableContainer")
public class NameableContainer implements org.osrs.api.wrappers.NameableContainer{

	@BField
	public org.osrs.api.wrappers.Nameable[] nameables;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Nameable[] nameables(){return nameables;}
	@BField
	public java.util.Comparator comparator;
	@BGetter
	@Override
	public java.util.Comparator comparator(){return comparator;}
	@BField
	public int count;
	@BGetter
	@Override
	public int count(){return count;}
	@BField
	public java.util.HashMap friendNameTable;
	@BGetter
	@Override
	public java.util.HashMap friendNameTable(){return friendNameTable;}
	@BField
	public java.util.HashMap clanmateNameTable;
	@BGetter
	@Override
	public java.util.HashMap clanmateNameTable(){return clanmateNameTable;}
	@BField
	public int maxSize;
	@BGetter
	@Override
	public int maxSize(){return maxSize;}
}