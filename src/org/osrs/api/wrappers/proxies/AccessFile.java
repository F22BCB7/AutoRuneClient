package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="AccessFile")
public class AccessFile implements org.osrs.api.wrappers.AccessFile{

	@BField
	public long length;
	@BGetter
	@Override
	public long length(){return length;}
	@BField
	public long position;
	@BGetter
	@Override
	public long position(){return position;}
	@BField
	public java.io.RandomAccessFile file;
	@BGetter
	@Override
	public java.io.RandomAccessFile file(){return file;}
}