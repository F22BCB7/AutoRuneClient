package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="DoublyNodeIterator")
public class DoublyNodeIterator implements org.osrs.api.wrappers.DoublyNodeIterator{

	@BField
	public org.osrs.api.wrappers.EntityNode next;
	@BGetter
	@Override
	public org.osrs.api.wrappers.EntityNode next(){return next;}
	@BField
	public org.osrs.api.wrappers.EntityNode current;
	@BGetter
	@Override
	public org.osrs.api.wrappers.EntityNode current(){return current;}
	@BField
	public org.osrs.api.wrappers.DoublyNode iterableNode;
	@BGetter
	@Override
	public org.osrs.api.wrappers.DoublyNode iterableNode(){return iterableNode;}
}