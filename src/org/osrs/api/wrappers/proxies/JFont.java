package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="JFont")
public class JFont extends JGraphics implements org.osrs.api.wrappers.JFont{

	@BField
	public int verticalSpacing;
	@BGetter
	@Override
	public int verticalSpacing(){return verticalSpacing;}
	@BField
	public int[] characterScreenWidths;
	@BGetter
	@Override
	public int[] characterScreenWidths(){return characterScreenWidths;}
	@BField
	public int[] characterHeights;
	@BGetter
	@Override
	public int[] characterHeights(){return characterHeights;}
	@BField
	public int[] characterYOffsets;
	@BGetter
	@Override
	public int[] characterYOffsets(){return characterYOffsets;}
	@BField
	public int[] characterWidths;
	@BGetter
	@Override
	public int[] characterWidths(){return characterWidths;}
	@BField
	public int topPadding;
	@BGetter
	@Override
	public int topPadding(){return topPadding;}
	@BField
	public int bottomPadding;
	@BGetter
	@Override
	public int bottomPadding(){return bottomPadding;}
	@BField
	public byte[] glyphWidths;
	@BGetter
	@Override
	public byte[] glyphWidths(){return glyphWidths;}
	@BField
	public byte[][] glyphSpacing;
	@BGetter
	@Override
	public byte[][] glyphSpacing(){return glyphSpacing;}
	@BField
	public int[] characterXOffsets;
	@BGetter
	@Override
	public int[] characterXOffsets(){return characterXOffsets;}
}