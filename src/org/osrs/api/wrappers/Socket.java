package org.osrs.api.wrappers;

public interface Socket extends AbstractSocket{
	public AsynchronousOutputStream output();
	public java.net.Socket socket();
	public AsynchronousInputStream input();
}