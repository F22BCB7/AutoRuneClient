package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="Task")
public class Task implements org.osrs.api.wrappers.Task{

	@BField
	public org.osrs.api.wrappers.Task task;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Task task(){return task;}
	@BField
	public int status;
	@BGetter
	@Override
	public int status(){return status;}
	@BField
	public int type;
	@BGetter
	@Override
	public int type(){return type;}
	@BField
	public int intArgument;
	@BGetter
	@Override
	public int intArgument(){return intArgument;}
	@BField
	public java.lang.Object objectArgument;
	@BGetter
	@Override
	public java.lang.Object objectArgument(){return objectArgument;}
	@BField
	public java.lang.Object value;
	@BGetter
	@Override
	public java.lang.Object value(){return value;}
}