package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="Link")
public class Link implements org.osrs.api.wrappers.Link{

	@BField
	public org.osrs.api.wrappers.Link previous;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Link previous(){return previous;}
	@BField
	public org.osrs.api.wrappers.Link next;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Link next(){return next;}
}