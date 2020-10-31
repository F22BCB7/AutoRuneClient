package org.osrs.api.wrappers;

public interface TaskDataNode extends Node{
	public int taskID();
	public boolean hasArray();
	public AbstractRawAudioNode audioNode();
	public TaskDataNode taskNode();
}