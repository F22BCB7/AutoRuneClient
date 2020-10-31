package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="TaskDataNode")
public class TaskDataNode extends Node implements org.osrs.api.wrappers.TaskDataNode{

	@BField
	public int taskID;
	@BGetter
	@Override
	public int taskID(){return taskID;}
	@BField
	public boolean hasArray;
	@BGetter
	@Override
	public boolean hasArray(){return hasArray;}
	@BField
	public org.osrs.api.wrappers.AbstractRawAudioNode audioNode;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AbstractRawAudioNode audioNode(){return audioNode;}
	@BField
	public org.osrs.api.wrappers.TaskDataNode taskNode;
	@BGetter
	@Override
	public org.osrs.api.wrappers.TaskDataNode taskNode(){return taskNode;}
}