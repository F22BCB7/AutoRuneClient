package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BFunction;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import org.osrs.injection.listeners.MessageListener;
import org.osrs.util.Data;
import org.osrs.util.ScriptDef;

import java.util.HashMap;
import java.util.Map;

@BClass(name="ChatboxChannel")
public class ChatboxChannel implements org.osrs.api.wrappers.ChatboxChannel{

	@BField
	public org.osrs.api.wrappers.ChatboxMessage[] messages;
	@BGetter
	@Override
	public org.osrs.api.wrappers.ChatboxMessage[] messages(){return messages;}
	@BField
	public int messageCount;
	@BGetter
	@Override
	public int messageCount(){return messageCount;}

	@BMethod(name="addMessage")
	public ChatboxMessage _addMessage(int a, String b, String c, String d, int e){return null;}
	@BDetour
	public ChatboxMessage addMessage(int a, String b, String c, String d, int e){
		ChatboxMessage message = _addMessage(a, b, c, d, e);
		MessageListener.incomingMessage(message, org.osrs.api.wrappers.proxies.Client.clientInstance);
		return message;
	}
	@BFunction
	@Override
	public ChatboxMessage invokeAddMessage(int a, String b, String c, String d, int e){
		return addMessage(a, b, c, d, e);
	}
}