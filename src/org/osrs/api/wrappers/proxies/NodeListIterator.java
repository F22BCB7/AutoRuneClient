package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="NodeListIterator")
public class NodeListIterator implements org.osrs.api.wrappers.NodeListIterator{

	@BField
	public org.osrs.api.wrappers.Node current;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Node current(){return current;}
	@BField
	public org.osrs.api.wrappers.NodeList list;
	@BGetter
	@Override
	public org.osrs.api.wrappers.NodeList list(){return list;}
	@BField
	public org.osrs.api.wrappers.Node next;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Node next(){return next;}
}