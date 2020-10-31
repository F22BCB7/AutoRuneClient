package org.osrs.api.wrappers;

import java.util.ArrayList;

import org.osrs.util.BufferTracker;

public interface ByteBuffer extends Node{
	public int offset();
	public byte[] bytes();
	public void initTracker();
	public BufferTracker getTracker();
}