package org.osrs.api.wrappers;

public interface AsynchronousInputStream{
	public java.io.InputStream input();
	public java.lang.Thread thread();
	public byte[] buffer();
	public java.io.IOException exception();
	public int readIndex();
	public int offset();
	public int size();
}