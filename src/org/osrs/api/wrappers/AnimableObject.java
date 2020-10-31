package org.osrs.api.wrappers;

public interface AnimableObject extends RenderableNode{
	public int currentFrameIndex();
	public int currentFrameLength();
	public boolean finished();
	public int id();
	public int plane();
	public int x();
	public int y();
	public int height();
	public int startCycle();
	public AnimationSequence animationSequence();
}