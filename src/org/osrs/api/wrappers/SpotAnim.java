package org.osrs.api.wrappers;

public interface SpotAnim extends EntityNode{
	public int animationID();
	public int widthScale();
	public int heightScale();
	public int orientation();
	public int ambient();
	public int contrast();
	public short[] recolorToFind();
	public short[] recolorToReplace();
	public short[] textureToFind();
	public short[] textureToReplace();
	public int id();
	public int referenceTableIndex();
}