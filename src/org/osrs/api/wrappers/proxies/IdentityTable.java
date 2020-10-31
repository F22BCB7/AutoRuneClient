package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="IdentityTable")
public class IdentityTable implements org.osrs.api.wrappers.IdentityTable{

	@BField
	public int[] ids;
	@BGetter
	@Override
	public int[] ids(){return ids;}
}