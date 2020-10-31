package org.osrs.api.wrappers;

public interface ClassInfo extends Node{
	public byte[][][] args();
	public java.lang.reflect.Method[] methods();
	public int[] types();
	public int[] errorIdentifiers();
	public java.lang.reflect.Field[] fields();
	public int[] fieldValues();
	public int identifier();
	public int count();
}