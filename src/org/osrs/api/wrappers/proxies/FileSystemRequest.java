package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="FileSystemRequest")//Rune-LIte's ArchiveDiskAction
public class FileSystemRequest extends Node implements org.osrs.api.wrappers.FileSystemRequest{

	@BField
	public int type;
	@BGetter
	@Override
	public int type(){return type;}
	@BField
	public byte[] buffer;
	@BGetter
	@Override
	public byte[] buffer(){return buffer;}
	@BField
	public org.osrs.api.wrappers.ArchiveDisk archiveDisk;
	@BGetter
	@Override
	public org.osrs.api.wrappers.ArchiveDisk archiveDisk(){return archiveDisk;}
	@BField
	public org.osrs.api.wrappers.Archive archive;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Archive archive(){return archive;}
}