package org.osrs.api.wrappers;

public interface Sprite extends JGraphics{
	public int[] pixels();
	public int maxWidth();
	public int width();
	public int maxHeight();
	public int height();
	public int offsetY();
	public int offsetX();
}