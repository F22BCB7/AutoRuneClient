package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="SoundSystem")
public class SoundSystem extends AbstractSoundSystem implements org.osrs.api.wrappers.SoundSystem{

	@BField
	public byte[] bytes;
	@BGetter
	@Override
	public byte[] bytes(){return bytes;}
	@BField
	public javax.sound.sampled.SourceDataLine dataline;
	@BGetter
	@Override
	public javax.sound.sampled.SourceDataLine dataline(){return dataline;}
	@BField
	public int length;
	@BGetter
	@Override
	public int length(){return length;}
	@BField
	public javax.sound.sampled.AudioFormat format;
	@BGetter
	@Override
	public javax.sound.sampled.AudioFormat format(){return format;}
}