package org.osrs.api.wrappers;

public interface PendingSpawn extends Node{
	public int hash();
	public int face();
	public int configID();
	public int level();
	public int type();
	public int x();
	public int y();
	public int id();
	public int modelID();
	public int orientation();
	public int delay();
	public int hitpoints();
}