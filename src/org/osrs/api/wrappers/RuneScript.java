package org.osrs.api.wrappers;

public interface RuneScript extends EntityNode{
	public int intStackCount();
	public int[] intOperands();
	public int localStringCount();
	public int localIntCount();
	public FixedSizeDeque[] switches();
	public int[] instructions();
	public int stringStackCount();
	public String[] stringOperands();
}