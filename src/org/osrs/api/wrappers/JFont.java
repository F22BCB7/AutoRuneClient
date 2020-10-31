package org.osrs.api.wrappers;

public interface JFont extends JGraphics{
	public int verticalSpacing();
	public int[] characterScreenWidths();
	public int[] characterHeights();
	public int[] characterYOffsets();
	public int[] characterWidths();
	public int topPadding();
	public int bottomPadding();
	public byte[] glyphWidths();
	public byte[][] glyphSpacing();
	public int[] characterXOffsets();
}