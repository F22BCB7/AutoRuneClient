package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="Signlink")
public class Signlink implements org.osrs.api.wrappers.Signlink{

	@BField
	public java.lang.Thread thread;
	@BGetter
	@Override
	public java.lang.Thread thread(){return thread;}
	@BField
	public boolean closed;
	@BGetter
	@Override
	public boolean closed(){return closed;}
	@BField
	public org.osrs.api.wrappers.Task currentTask;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Task currentTask(){return currentTask;}
	@BField
	public org.osrs.api.wrappers.Task cachedTask;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Task cachedTask(){return cachedTask;}
}