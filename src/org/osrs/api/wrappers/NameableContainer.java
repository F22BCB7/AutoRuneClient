package org.osrs.api.wrappers;

public interface NameableContainer{
	public Nameable[] nameables();
	public java.util.Comparator comparator();
	public int count();
	public java.util.HashMap friendNameTable();
	public java.util.HashMap clanmateNameTable();
	public int maxSize();
}