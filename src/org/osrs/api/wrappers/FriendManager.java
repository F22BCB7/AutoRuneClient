package org.osrs.api.wrappers;

public interface FriendManager{
	public FriendContainer friendContainer();
	public IgnoreContainer ignoreContainer();
	public int status();
	public JagexLoginType loginType();
}