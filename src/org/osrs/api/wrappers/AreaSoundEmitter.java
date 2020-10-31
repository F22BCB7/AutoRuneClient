package org.osrs.api.wrappers;

public interface AreaSoundEmitter extends Node{
	public int[] soundIDs();
	public int ambientSoundID();
	public ObjectDefinition emittingObject();
}