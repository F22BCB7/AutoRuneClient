package org.osrs.api.wrappers;

public interface BufferedImage extends EntityNode{
	public int[] startPixelWidthIndex();
	public int width();
	public int[] endPixelWidthIndex();
	public int height();
}