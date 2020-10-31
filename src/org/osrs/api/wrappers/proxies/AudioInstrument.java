package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="AudioInstrument")
public class AudioInstrument implements org.osrs.api.wrappers.AudioInstrument{

	@BField
	public org.osrs.api.wrappers.AudioEnvelope pitch;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AudioEnvelope pitch(){return pitch;}
	@BField
	public org.osrs.api.wrappers.AudioEnvelope volume;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AudioEnvelope volume(){return volume;}
	@BField
	public org.osrs.api.wrappers.AudioEnvelope pitchModifier;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AudioEnvelope pitchModifier(){return pitchModifier;}
	@BField
	public org.osrs.api.wrappers.AudioEnvelope pitchModifierAmplitude;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AudioEnvelope pitchModifierAmplitude(){return pitchModifierAmplitude;}
	@BField
	public org.osrs.api.wrappers.AudioEnvelope volumeMultiplier;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AudioEnvelope volumeMultiplier(){return volumeMultiplier;}
	@BField
	public org.osrs.api.wrappers.AudioEnvelope volumeMultiplierAmplitude;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AudioEnvelope volumeMultiplierAmplitude(){return volumeMultiplierAmplitude;}
	@BField
	public org.osrs.api.wrappers.AudioEnvelope release;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AudioEnvelope release(){return release;}
	@BField
	public org.osrs.api.wrappers.AudioEnvelope gain;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AudioEnvelope gain(){return gain;}
	@BField
	public int delayTime;
	@BGetter
	@Override
	public int delayTime(){return delayTime;}
	@BField
	public int delayDecay;
	@BGetter
	@Override
	public int delayDecay(){return delayDecay;}
	@BField
	public int duration;
	@BGetter
	@Override
	public int duration(){return duration;}
	@BField
	public int offset;
	@BGetter
	@Override
	public int offset(){return offset;}
	@BField
	public org.osrs.api.wrappers.AudioEnvelope filterEnvelope;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AudioEnvelope filterEnvelope(){return filterEnvelope;}
	@BField
	public int[] oscillatorVolume;
	@BGetter
	@Override
	public int[] oscillatorVolume(){return oscillatorVolume;}
	@BField
	public int[] oscillatorPitch;
	@BGetter
	@Override
	public int[] oscillatorPitch(){return oscillatorPitch;}
	@BField
	public int[] oscillatorDelays;
	@BGetter
	@Override
	public int[] oscillatorDelays(){return oscillatorDelays;}
	@BField
	public org.osrs.api.wrappers.SoundFilter filter;
	@BGetter
	@Override
	public org.osrs.api.wrappers.SoundFilter filter(){return filter;}
}