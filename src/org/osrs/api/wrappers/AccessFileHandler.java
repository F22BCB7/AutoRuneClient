package org.osrs.api.wrappers;

public interface AccessFileHandler{
	public int currentReadPosition();
	public long startReadPosition();
	public byte[] readPayload();
	public byte[] writePayload();
	public long length();
	public long startWritePosition();
	public int currentWritePosition();
	public long filePointer();
	public AccessFile accessFile();
	public long capacity();
	public long readPosition();
}