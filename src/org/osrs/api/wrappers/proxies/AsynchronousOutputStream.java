package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="AsynchronousOutputStream")
public class AsynchronousOutputStream implements org.osrs.api.wrappers.AsynchronousOutputStream{

	@BField
	public java.lang.Thread thread;
	@BGetter
	@Override
	public java.lang.Thread thread(){return thread;}
	@BField
	public byte[] buffer;
	@BGetter
	@Override
	public byte[] buffer(){return buffer;}
	@BField
	public java.io.OutputStream output;
	@BGetter
	@Override
	public java.io.OutputStream output(){return output;}
	@BField
	public java.io.IOException exception;
	@BGetter
	@Override
	public java.io.IOException exception(){return exception;}
	@BField
	public boolean stopped;
	@BGetter
	@Override
	public boolean stopped(){return stopped;}
	@BField
	public int offset;
	@BGetter
	@Override
	public int offset(){return offset;}
	@BField
	public int writeIndex;
	@BGetter
	@Override
	public int writeIndex(){return writeIndex;}
	@BField
	public int size;
	@BGetter
	@Override
	public int size(){return size;}
}