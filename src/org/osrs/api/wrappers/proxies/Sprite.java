package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="Sprite")
public class Sprite extends JGraphics implements org.osrs.api.wrappers.Sprite{

	@BField
	public int[] pixels;
	@BGetter
	@Override
	public int[] pixels(){return pixels;}
	@BField
	public int maxWidth;
	@BGetter
	@Override
	public int maxWidth(){return maxWidth;}
	@BField
	public int width;
	@BGetter
	@Override
	public int width(){return width;}
	@BField
	public int maxHeight;
	@BGetter
	@Override
	public int maxHeight(){return maxHeight;}
	@BField
	public int height;
	@BGetter
	@Override
	public int height(){return height;}
	@BField
	public int offsetY;
	@BGetter
	@Override
	public int offsetY(){return offsetY;}
	@BField
	public int offsetX;
	@BGetter
	@Override
	public int offsetX(){return offsetX;}
}