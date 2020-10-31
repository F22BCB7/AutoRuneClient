package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="Inflater")
public class Inflater implements org.osrs.api.wrappers.Inflater{

	@BField
	public java.util.zip.Inflater inflator;
	@BGetter
	@Override
	public java.util.zip.Inflater inflator(){return inflator;}
}