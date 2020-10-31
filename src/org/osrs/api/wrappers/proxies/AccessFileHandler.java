package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="AccessFileHandler")
public class AccessFileHandler implements org.osrs.api.wrappers.AccessFileHandler{

	@BField
	public int currentReadPosition;
	@BGetter
	@Override
	public int currentReadPosition(){return currentReadPosition;}
	@BField
	public long startReadPosition;
	@BGetter
	@Override
	public long startReadPosition(){return startReadPosition;}
	@BField
	public byte[] readPayload;
	@BGetter
	@Override
	public byte[] readPayload(){return readPayload;}
	@BField
	public byte[] writePayload;
	@BGetter
	@Override
	public byte[] writePayload(){return writePayload;}
	@BField
	public long length;
	@BGetter
	@Override
	public long length(){return length;}
	@BField
	public long startWritePosition;
	@BGetter
	@Override
	public long startWritePosition(){return startWritePosition;}
	@BField
	public int currentWritePosition;
	@BGetter
	@Override
	public int currentWritePosition(){return currentWritePosition;}
	@BField
	public long filePointer;
	@BGetter
	@Override
	public long filePointer(){return filePointer;}
	@BField
	public org.osrs.api.wrappers.AccessFile accessFile;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AccessFile accessFile(){return accessFile;}
	@BField
	public long capacity;
	@BGetter
	@Override
	public long capacity(){return capacity;}
	@BField
	public long readPosition;
	@BGetter
	@Override
	public long readPosition(){return readPosition;}
}