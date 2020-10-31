package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="NameableComparator")
public class NameableComparator implements org.osrs.api.wrappers.NameableComparator{

	@BField
	public java.util.Comparator comparator;
	@BGetter
	@Override
	public java.util.Comparator comparator(){return comparator;}
}