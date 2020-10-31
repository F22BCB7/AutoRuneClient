package org.osrs.api.wrappers;

public interface Task{
	public Task task();
	public int status();
	public int type();
	public int intArgument();
	public java.lang.Object objectArgument();
	public java.lang.Object value();
}