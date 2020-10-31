package org.osrs.api.wrappers;

public interface AudioInstrument{
	public AudioEnvelope pitch();
	public AudioEnvelope volume();
	public AudioEnvelope pitchModifier();
	public AudioEnvelope pitchModifierAmplitude();
	public AudioEnvelope volumeMultiplier();
	public AudioEnvelope volumeMultiplierAmplitude();
	public AudioEnvelope release();
	public AudioEnvelope gain();
	public int delayTime();
	public int delayDecay();
	public int duration();
	public int offset();
	public AudioEnvelope filterEnvelope();
	public int[] oscillatorVolume();
	public int[] oscillatorPitch();
	public int[] oscillatorDelays();
	public SoundFilter filter();
}