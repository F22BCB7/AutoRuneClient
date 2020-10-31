package org.osrs.api.wrappers;

public interface OutgoingPacket extends Node{
	public OutgoingPacketMeta meta();
	public PacketBuffer buffer();
	public int size();
	public int bufferSize();
}