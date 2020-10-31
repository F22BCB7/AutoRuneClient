package org.osrs.api.wrappers;

import org.osrs.api.wrappers.ChatboxMessage;

public interface ChatboxChannel{
	public ChatboxMessage[] messages();
	public int messageCount();
	public ChatboxMessage invokeAddMessage(int a, String b, String c, String d, int e);
}