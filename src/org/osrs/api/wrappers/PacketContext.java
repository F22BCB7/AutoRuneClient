package org.osrs.api.wrappers;

public interface PacketContext{
	public AbstractSocket socket();
	public NodeList outgoingPackets();
	public int outgoingBufferSize();
	public ByteBuffer byteBuffer();
	public ISAACCipher cipher();
	public PacketBuffer packetBuffer();
	public IncomingPacketMeta currentIncomingPacket();
	public IncomingPacketMeta secondIncomingPacket();
	public boolean needsProcessing();
	public int readSize();
	public int currentPacketSize();
	public IncomingPacketMeta lastIncomingPacket();
	public int idleReadPulses();
	public IncomingPacketMeta thirdIncomingPacket();
	public void sendPacket(OutgoingPacket packet, byte a);
}