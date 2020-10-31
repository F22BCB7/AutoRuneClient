package org.osrs.api.wrappers;

public interface AnimationSequence extends EntityNode{
	public int precedenceAnimating();
	public int[] interleaveLeave();
	public int[] transformFrameIDs();
	public int[] frameLengths();
	public int[] sounds();
	public int frameStep();
	public int priority();
	public boolean stretches();
	public int leftHandItem();
	public int rightHandItem();
	public int maxLoops();
	public int forcedPriority();
	public int[] frameIDs();
	public int replyMode();
}