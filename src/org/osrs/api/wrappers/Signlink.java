package org.osrs.api.wrappers;

public interface Signlink{
	public java.lang.Thread thread();
	public boolean closed();
	public Task currentTask();
	public Task cachedTask();
}