package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="AsynchronousInputStream")
public class AsynchronousInputStream implements org.osrs.api.wrappers.AsynchronousInputStream{

	@BField
	public java.io.InputStream input;
	@BGetter
	@Override
	public java.io.InputStream input(){return input;}
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
	public java.io.IOException exception;
	@BGetter
	@Override
	public java.io.IOException exception(){return exception;}
	@BField
	public int readIndex;
	@BGetter
	@Override
	public int readIndex(){return readIndex;}
	@BField
	public int offset;
	@BGetter
	@Override
	public int offset(){return offset;}
	@BField
	public int size;
	@BGetter
	@Override
	public int size(){return size;}
}