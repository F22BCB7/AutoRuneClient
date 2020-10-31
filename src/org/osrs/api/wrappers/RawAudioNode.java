package org.osrs.api.wrappers;

public interface RawAudioNode extends AbstractRawAudioNode{
	public boolean incomplete();
	public byte[] buffer();
	public int startPosition();
	public int endPosition();
	public int sampleRate();
}