package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="LinkedList")
public class LinkedList implements org.osrs.api.wrappers.LinkedList{

	@BField
	public org.osrs.api.wrappers.Link pointer;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Link pointer(){return pointer;}
	@BField
	public org.osrs.api.wrappers.Link head;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Link head(){return head;}
}