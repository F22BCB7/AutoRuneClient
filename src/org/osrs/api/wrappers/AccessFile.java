package org.osrs.api.wrappers;

public interface AccessFile{
	public long length();
	public long position();
	public java.io.RandomAccessFile file();
}