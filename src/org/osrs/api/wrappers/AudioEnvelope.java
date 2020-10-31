package org.osrs.api.wrappers;

public interface AudioEnvelope{
	public int step();
	public int form();
	public int segments();
	public int start();
	public int end();
	public int amplitude();
	public int ticks();
	public int phaseIndex();
	public int[] phases();
	public int[] durations();
	public int max();
}