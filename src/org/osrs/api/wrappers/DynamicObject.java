package org.osrs.api.wrappers;

public interface DynamicObject extends RenderableNode{
	public int id();
	public int type();
	public int orientation();
	public int plane();
	public int x();
	public int y();
	public AnimationSequence animationSequence();
	public int animationFrame();
	public int animationCycleCount();
}