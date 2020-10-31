package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="AnimationSequence")
public class AnimationSequence extends EntityNode implements org.osrs.api.wrappers.AnimationSequence{

	@BField
	public int precedenceAnimating;
	@BGetter
	@Override
	public int precedenceAnimating(){return precedenceAnimating;}
	@BField
	public int[] interleaveLeave;
	@BGetter
	@Override
	public int[] interleaveLeave(){return interleaveLeave;}
	@BField
	public int[] transformFrameIDs;
	@BGetter
	@Override
	public int[] transformFrameIDs(){return transformFrameIDs;}
	@BField
	public int[] frameLengths;
	@BGetter
	@Override
	public int[] frameLengths(){return frameLengths;}
	@BField
	public int[] sounds;
	@BGetter
	@Override
	public int[] sounds(){return sounds;}
	@BField
	public int frameStep;
	@BGetter
	@Override
	public int frameStep(){return frameStep;}
	@BField
	public int priority;
	@BGetter
	@Override
	public int priority(){return priority;}
	@BField
	public boolean stretches;
	@BGetter
	@Override
	public boolean stretches(){return stretches;}
	@BField
	public int leftHandItem;
	@BGetter
	@Override
	public int leftHandItem(){return leftHandItem;}
	@BField
	public int rightHandItem;
	@BGetter
	@Override
	public int rightHandItem(){return rightHandItem;}
	@BField
	public int maxLoops;
	@BGetter
	@Override
	public int maxLoops(){return maxLoops;}
	@BField
	public int forcedPriority;
	@BGetter
	@Override
	public int forcedPriority(){return forcedPriority;}
	@BField
	public int[] frameIDs;
	@BGetter
	@Override
	public int[] frameIDs(){return frameIDs;}
	@BField
	public int replyMode;
	@BGetter
	@Override
	public int replyMode(){return replyMode;}
}