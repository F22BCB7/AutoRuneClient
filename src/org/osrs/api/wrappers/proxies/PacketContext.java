package org.osrs.api.wrappers.proxies;

import org.osrs.api.methods.MethodContext;
import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BSetterDetour;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BGetterDetour;
import org.osrs.injection.bytescript.BVar;
import org.osrs.util.BufferTracker;
import org.osrs.util.Data;
import org.osrs.util.ScriptDef;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@BClass(name="PacketContext")
public class PacketContext implements org.osrs.api.wrappers.PacketContext{

	@BField
	public org.osrs.api.wrappers.AbstractSocket socket;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AbstractSocket socket(){return socket;}
	@BField
	public org.osrs.api.wrappers.NodeList outgoingPackets;
	@BGetter
	@Override
	public org.osrs.api.wrappers.NodeList outgoingPackets(){return outgoingPackets;}
	@BField
	public int outgoingBufferSize;
	@BGetter
	@Override
	public int outgoingBufferSize(){return outgoingBufferSize;}
	@BField
	public org.osrs.api.wrappers.ByteBuffer byteBuffer;
	@BGetter
	@Override
	public org.osrs.api.wrappers.ByteBuffer byteBuffer(){return byteBuffer;}
	@BField
	public org.osrs.api.wrappers.ISAACCipher cipher;
	@BGetter
	@Override
	public org.osrs.api.wrappers.ISAACCipher cipher(){return cipher;}
	@BField
	public org.osrs.api.wrappers.PacketBuffer packetBuffer;
	@BGetter
	@Override
	public org.osrs.api.wrappers.PacketBuffer packetBuffer(){return packetBuffer;}
	@BField
	public org.osrs.api.wrappers.IncomingPacketMeta currentIncomingPacket;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta currentIncomingPacket(){return currentIncomingPacket;}
	@BGetterDetour
	public org.osrs.api.wrappers.IncomingPacketMeta get_currentIncomingPacket(){
		packetBuffer.initTracker();
		return currentIncomingPacket;
	}
	@BSetterDetour
	public void set_currentIncomingPacket(org.osrs.api.wrappers.IncomingPacketMeta meta){
		if(meta!=null){
			//packetBuffer.initTracker();
		}
		else{
			ScriptDef script = Data.currentScripts.get(Client.clientInstance);
			if(script!=null){
				script.recievePacketData(this, currentIncomingPacket);
			}
			//System.out.println(meta.id()+":"+Arrays.toString(packetBuffer.getTracker().trackedData));
		}
		currentIncomingPacket = meta;
	}
	@BField
	public org.osrs.api.wrappers.IncomingPacketMeta secondIncomingPacket;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta secondIncomingPacket(){return secondIncomingPacket;}
	@BField
	public boolean needsProcessing;
	@BGetter
	@Override
	public boolean needsProcessing(){return needsProcessing;}
	@BField
	public int readSize;
	@BGetter
	@Override
	public int readSize(){return readSize;}
	@BField
	public int currentPacketSize;
	@BGetter
	@Override
	public int currentPacketSize(){return currentPacketSize;}
	@BField
	public org.osrs.api.wrappers.IncomingPacketMeta lastIncomingPacket;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta lastIncomingPacket(){return lastIncomingPacket;}
	@BField
	public int idleReadPulses;
	@BGetter
	@Override
	public int idleReadPulses(){return idleReadPulses;}
	@BField
	public org.osrs.api.wrappers.IncomingPacketMeta thirdIncomingPacket;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta thirdIncomingPacket(){return thirdIncomingPacket;}
	@BMethod(name="sendPacket")
	public void _sendPacket(org.osrs.api.wrappers.OutgoingPacket packet, int a){}
	@BDetour
	@Override
	public void sendPacket(org.osrs.api.wrappers.OutgoingPacket packet, int a){
		Object scriptDef = Data.currentScripts.get(Client.clientInstance);
		if(scriptDef!=null){
			((ScriptDef)scriptDef).recievePacketData(packet);
		}
		BufferTracker tracker = packet.buffer().getTracker();
		if(tracker!=null)
			tracker.clearBuffer();
		_sendPacket(packet, a);
	}
}