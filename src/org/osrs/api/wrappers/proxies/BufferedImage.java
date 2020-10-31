package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="BufferedImage")
public class BufferedImage extends EntityNode implements org.osrs.api.wrappers.BufferedImage{

	@BField
	public int[] startPixelWidthIndex;
	@BGetter
	@Override
	public int[] startPixelWidthIndex(){return startPixelWidthIndex;}
	@BField
	public int width;
	@BGetter
	@Override
	public int width(){return width;}
	@BField
	public int[] endPixelWidthIndex;
	@BGetter
	@Override
	public int[] endPixelWidthIndex(){return endPixelWidthIndex;}
	@BField
	public int height;
	@BGetter
	@Override
	public int height(){return height;}
}