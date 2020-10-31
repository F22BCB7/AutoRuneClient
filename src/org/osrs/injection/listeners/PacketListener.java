package org.osrs.injection.listeners;

import java.util.Arrays;

import org.osrs.api.methods.MethodContext;
import org.osrs.api.wrappers.ByteBuffer;
import org.osrs.api.wrappers.IncomingPacketMeta;
import org.osrs.api.wrappers.OutgoingPacketMeta;
import org.osrs.api.wrappers.PacketBuffer;
import org.osrs.util.Data;
import org.osrs.util.ScriptDef;

public class PacketListener {
	public static void logPacket(Object packetContext, Object clientInstance){
		if(packetContext==null || clientInstance==null)
			return;
		Object packetData = ((org.osrs.api.wrappers.PacketContext)packetContext).currentIncomingPacket();
		PacketBuffer packetBuffer = ((org.osrs.api.wrappers.PacketContext)packetContext).packetBuffer();
		
		MethodContext context = Data.clientContexts.get(clientInstance);
		if(context==null)
			return;
		ScriptDef script = Data.currentScripts.get(clientInstance);
		if(packetData instanceof IncomingPacketMeta){
			IncomingPacketMeta packet = (IncomingPacketMeta)packetData;
			
			if(script!=null)
				script.recievePacket(packet);
		}
		else if(packetData instanceof OutgoingPacketMeta){
			OutgoingPacketMeta packet = (OutgoingPacketMeta)packetData;
			if(script!=null)
				script.recievePacket(packet);
		}
	}
}
