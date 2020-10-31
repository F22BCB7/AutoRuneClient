package org.osrs.api.wrappers;

public interface Resampler{
	public int storedSampleRateRatio();
	public int playbackSampleRateRatio();
	public int[][] resampleTable();
}