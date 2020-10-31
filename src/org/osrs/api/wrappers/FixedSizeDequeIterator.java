package org.osrs.api.wrappers;

public interface FixedSizeDequeIterator{
	public Node current();
	public Node next();
	public int size();
	public FixedSizeDeque deque();
}