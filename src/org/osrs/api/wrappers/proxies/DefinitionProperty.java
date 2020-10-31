package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="DefinitionProperty")
public class DefinitionProperty extends EntityNode implements org.osrs.api.wrappers.DefinitionProperty{

	@BField
	public char type;
	@BGetter
	@Override
	public char type(){return type;}
	@BField
	public int defaultInteger;
	@BGetter
	@Override
	public int defaultInteger(){return defaultInteger;}
	@BField
	public String defaultString;
	@BGetter
	@Override
	public String defaultString(){return defaultString;}
	@BField
	public boolean deleteOnUse;
	@BGetter
	@Override
	public boolean deleteOnUse(){return deleteOnUse;}
}