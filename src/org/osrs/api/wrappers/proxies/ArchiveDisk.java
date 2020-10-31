package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="ArchiveDisk")
public class ArchiveDisk implements org.osrs.api.wrappers.ArchiveDisk{

	@BField
	public org.osrs.api.wrappers.AccessFileHandler indexFile;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AccessFileHandler indexFile(){return indexFile;}
	@BField
	public org.osrs.api.wrappers.AccessFileHandler dataFile;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AccessFileHandler dataFile(){return dataFile;}
	@BField
	public int maxSize;
	@BGetter
	@Override
	public int maxSize(){return maxSize;}
	@BField
	public int index;
	@BGetter
	@Override
	public int index(){return index;}
}