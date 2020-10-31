package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import org.osrs.util.Data;
import org.osrs.util.ScriptDef;

import java.util.HashMap;
import java.util.Map;

@BClass(name="Socket")
public class Socket extends AbstractSocket implements org.osrs.api.wrappers.Socket{

	@BField
	public org.osrs.api.wrappers.AsynchronousOutputStream output;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AsynchronousOutputStream output(){return output;}
	@BField
	public java.net.Socket socket;
	@BGetter
	@Override
	public java.net.Socket socket(){return socket;}
	@BField
	public org.osrs.api.wrappers.AsynchronousInputStream input;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AsynchronousInputStream input(){return input;}
}