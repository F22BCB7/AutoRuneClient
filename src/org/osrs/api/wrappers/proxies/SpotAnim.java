package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="SpotAnim")
public class SpotAnim extends EntityNode implements org.osrs.api.wrappers.SpotAnim{

	@BField
	public int animationID;
	@BGetter
	@Override
	public int animationID(){return animationID;}
	@BField
	public int widthScale;
	@BGetter
	@Override
	public int widthScale(){return widthScale;}
	@BField
	public int heightScale;
	@BGetter
	@Override
	public int heightScale(){return heightScale;}
	@BField
	public int orientation;
	@BGetter
	@Override
	public int orientation(){return orientation;}
	@BField
	public int ambient;
	@BGetter
	@Override
	public int ambient(){return ambient;}
	@BField
	public int contrast;
	@BGetter
	@Override
	public int contrast(){return contrast;}
	@BField
	public short[] recolorToFind;
	@BGetter
	@Override
	public short[] recolorToFind(){return recolorToFind;}
	@BField
	public short[] recolorToReplace;
	@BGetter
	@Override
	public short[] recolorToReplace(){return recolorToReplace;}
	@BField
	public short[] textureToFind;
	@BGetter
	@Override
	public short[] textureToFind(){return textureToFind;}
	@BField
	public short[] textureToReplace;
	@BGetter
	@Override
	public short[] textureToReplace(){return textureToReplace;}
	@BField
	public int id;
	@BGetter
	@Override
	public int id(){return id;}
	@BField
	public int referenceTableIndex;
	@BGetter
	@Override
	public int referenceTableIndex(){return referenceTableIndex;}
}