package org.osrs.api.wrappers;

public interface DoublyNodeIterator{
	public EntityNode next();
	public EntityNode current();
	public DoublyNode iterableNode();
}