package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="AudioTrack")
public class AudioTrack extends Node implements org.osrs.api.wrappers.AudioTrack{

	@BField
	public byte[] buffer;
	@BGetter
	@Override
	public byte[] buffer(){return buffer;}
	@BField
	public org.osrs.api.wrappers.HashTable cache;
	@BGetter
	@Override
	public org.osrs.api.wrappers.HashTable cache(){return cache;}
}