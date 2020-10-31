package org.osrs.api.wrappers;

public interface AbstractSoundSystem{
	public TaskDataNode dataNode();
	public int[] samples();
	public TaskDataNode[] cachedDataNodes();
	public TaskDataNode[] dataNodes();
}