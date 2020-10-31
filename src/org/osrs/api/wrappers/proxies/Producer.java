package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="Producer")
public class Producer implements org.osrs.api.wrappers.Producer{

	@BField
	public int[] pixels;
	@BGetter
	@Override
	public int[] pixels(){return pixels;}
	@BField
	public int width;
	@BGetter
	@Override
	public int width(){return width;}
	@BField
	public int height;
	@BGetter
	@Override
	public int height(){return height;}
}