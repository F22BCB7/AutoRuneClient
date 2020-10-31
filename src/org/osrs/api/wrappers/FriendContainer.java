package org.osrs.api.wrappers;

public interface FriendContainer extends NameableContainer{
	public int size();
	public JagexLoginType loginType();
	public LinkedList friendUpdateList();
}