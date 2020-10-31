package org.osrs.api.wrappers;

public interface UrlRequester{
	public boolean closed();
	public java.util.Queue requests();
	public java.lang.Thread thread();
}