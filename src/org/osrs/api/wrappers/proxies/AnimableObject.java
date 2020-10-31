package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="AnimableObject")
public class AnimableObject extends RenderableNode implements org.osrs.api.wrappers.AnimableObject{

	@BField
	public int currentFrameIndex;
	@BGetter
	@Override
	public int currentFrameIndex(){return currentFrameIndex;}
	@BField
	public int currentFrameLength;
	@BGetter
	@Override
	public int currentFrameLength(){return currentFrameLength;}
	@BField
	public boolean finished;
	@BGetter
	@Override
	public boolean finished(){return finished;}
	@BField
	public int id;
	@BGetter
	@Override
	public int id(){return id;}
	@BField
	public int plane;
	@BGetter
	@Override
	public int plane(){return plane;}
	@BField
	public int x;
	@BGetter
	@Override
	public int x(){return x;}
	@BField
	public int y;
	@BGetter
	@Override
	public int y(){return y;}
	@BField
	public int height;
	@BGetter
	@Override
	public int height(){return height;}
	@BField
	public int startCycle;
	@BGetter
	@Override
	public int startCycle(){return startCycle;}
	@BField
	public org.osrs.api.wrappers.AnimationSequence animationSequence;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AnimationSequence animationSequence(){return animationSequence;}
}