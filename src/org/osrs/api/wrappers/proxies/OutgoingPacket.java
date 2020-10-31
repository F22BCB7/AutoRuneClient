package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="OutgoingPacket")
public class OutgoingPacket extends Node implements org.osrs.api.wrappers.OutgoingPacket{

	@BField
	public org.osrs.api.wrappers.OutgoingPacketMeta meta;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta meta(){return meta;}
	@BField
	public org.osrs.api.wrappers.PacketBuffer buffer;
	@BGetter
	@Override
	public org.osrs.api.wrappers.PacketBuffer buffer(){return buffer;}
	@BField
	public int size;
	@BGetter
	@Override
	public int size(){return size;}
	@BField
	public int bufferSize;
	@BGetter
	@Override
	public int bufferSize(){return bufferSize;}
}