package org.osrs.api.wrappers;

public interface ChatboxMessage extends EntityNode{
	public NameComposite nameComposite();
	public int id();
	public int cycle();
	public int type();
	public String name();
	public String sender();
	public String message();
	public ChatSetting chatSetting1();
	public ChatSetting chatSetting2();
}