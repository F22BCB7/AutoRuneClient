package org.osrs.api.wrappers;

public interface DefinitionProperty extends EntityNode{
	public char type();
	public int defaultInteger();
	public String defaultString();
	public boolean deleteOnUse();
}