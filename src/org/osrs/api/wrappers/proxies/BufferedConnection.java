package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="BufferedConnection")
public class BufferedConnection extends AbstractSocket implements org.osrs.api.wrappers.BufferedConnection{

	@BField
	public org.osrs.api.wrappers.Signlink signlink;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Signlink signlink(){return signlink;}
	@BField
	public java.io.OutputStream outputStream;
	@BGetter
	@Override
	public java.io.OutputStream outputStream(){return outputStream;}
	@BField
	public java.net.Socket socket;
	@BGetter
	@Override
	public java.net.Socket socket(){return socket;}
	@BField
	public boolean closed;
	@BGetter
	@Override
	public boolean closed(){return closed;}
	@BField
	public int bufferPosition;
	@BGetter
	@Override
	public int bufferPosition(){return bufferPosition;}
	@BField
	public org.osrs.api.wrappers.Task task;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Task task(){return task;}
	@BField
	public boolean ioError;
	@BGetter
	@Override
	public boolean ioError(){return ioError;}
	@BField
	public int writerPosition;
	@BGetter
	@Override
	public int writerPosition(){return writerPosition;}
	@BField
	public java.io.InputStream inputStream;
	@BGetter
	@Override
	public java.io.InputStream inputStream(){return inputStream;}
	@BField
	public int bufferSize;
	@BGetter
	@Override
	public int bufferSize(){return bufferSize;}
	@BField
	public int writerOffset;
	@BGetter
	@Override
	public int writerOffset(){return writerOffset;}
	@BField
	public byte[] buffer;
	@BGetter
	@Override
	public byte[] buffer(){return buffer;}
}