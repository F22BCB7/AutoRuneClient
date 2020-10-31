package org.osrs.api.wrappers;

public interface UrlRequest{
	public boolean done();
	public byte[] response();
	public java.net.URL url();
}