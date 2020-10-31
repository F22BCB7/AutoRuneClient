package org.osrs.api.wrappers;

public interface IndexedImage extends JGraphics{
	public int width();
	public byte[] pixels();
	public int[] palette();
	public int height();
	public int offsetX();
	public int offsetY();
	public int maxWidth();
	public int maxHeight();
}