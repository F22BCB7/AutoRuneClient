package org.osrs.api.wrappers;

public interface SoundSystem extends AbstractSoundSystem{
	public byte[] bytes();
	public javax.sound.sampled.SourceDataLine dataline();
	public int length();
	public javax.sound.sampled.AudioFormat format();
}