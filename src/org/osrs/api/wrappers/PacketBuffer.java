package org.osrs.api.wrappers;

public interface PacketBuffer extends ByteBuffer{
	public int bitOffset();
	public ISAACCipher cipher();
}