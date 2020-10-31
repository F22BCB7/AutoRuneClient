package org.osrs.injection.listeners;

import org.osrs.api.wrappers.ChatboxMessage;
import org.osrs.util.Data;
import org.osrs.util.ScriptDef;

public class MessageListener {
	public static void incomingMessage(Object message, Object client){
		if(message instanceof ChatboxMessage){
			ChatboxMessage m = (ChatboxMessage)message;
			ScriptDef script = Data.currentScripts.get(client);
			if(script!=null)
				script.recieveChatMessage(m);
		}
	}
}
