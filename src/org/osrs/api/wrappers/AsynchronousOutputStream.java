package org.osrs.api.wrappers;

public interface AsynchronousOutputStream{
	public java.lang.Thread thread();
	public byte[] buffer();
	public java.io.OutputStream output();
	public java.io.IOException exception();
	public boolean stopped();
	public int offset();
	public int writeIndex();
	public int size();
}