package org.osrs.api.wrappers;

public interface AudioRequestNode extends TaskDataNode{
	public int startPosition();
	public int endPosition();
	public boolean incomplete();
}