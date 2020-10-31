package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BFunction;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="DynamicObject")
public class DynamicObject extends RenderableNode implements org.osrs.api.wrappers.DynamicObject{

	@BField
	public int id;
	@BGetter
	@Override
	public int id(){return id;}
	@BField
	public int type;
	@BGetter
	@Override
	public int type(){return type;}
	@BField
	public int orientation;
	@BGetter
	@Override
	public int orientation(){return orientation;}
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
	public org.osrs.api.wrappers.AnimationSequence animationSequence;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AnimationSequence animationSequence(){return animationSequence;}
	@BField
	public int animationFrame;
	@BGetter
	@Override
	public int animationFrame(){return animationFrame;}
	@BField
	public int animationCycleCount;
	@BGetter
	@Override
	public int animationCycleCount(){return animationCycleCount;}
}