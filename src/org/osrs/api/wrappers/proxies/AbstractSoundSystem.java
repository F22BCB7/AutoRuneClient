package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="AbstractSoundSystem")
public class AbstractSoundSystem implements org.osrs.api.wrappers.AbstractSoundSystem{

	@BField
	public org.osrs.api.wrappers.TaskDataNode dataNode;
	@BGetter
	@Override
	public org.osrs.api.wrappers.TaskDataNode dataNode(){return dataNode;}
	@BField
	public int[] samples;
	@BGetter
	@Override
	public int[] samples(){return samples;}
	@BField
	public org.osrs.api.wrappers.TaskDataNode[] cachedDataNodes;
	@BGetter
	@Override
	public org.osrs.api.wrappers.TaskDataNode[] cachedDataNodes(){return cachedDataNodes;}
	@BField
	public org.osrs.api.wrappers.TaskDataNode[] dataNodes;
	@BGetter
	@Override
	public org.osrs.api.wrappers.TaskDataNode[] dataNodes(){return dataNodes;}
}