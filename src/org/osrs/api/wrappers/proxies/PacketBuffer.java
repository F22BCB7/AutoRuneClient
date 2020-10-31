package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="PacketBuffer")
public class PacketBuffer extends ByteBuffer implements org.osrs.api.wrappers.PacketBuffer{

	@BField
	public int bitOffset;
	@BGetter
	@Override
	public int bitOffset(){return bitOffset;}
	@BField
	public org.osrs.api.wrappers.ISAACCipher cipher;
	@BGetter
	@Override
	public org.osrs.api.wrappers.ISAACCipher cipher(){return cipher;}
}