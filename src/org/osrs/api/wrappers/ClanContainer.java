package org.osrs.api.wrappers;

public interface ClanContainer extends NameableContainer{
	public String clanName();
	public String clanOwner();
	public JagexLoginType loginType();
}