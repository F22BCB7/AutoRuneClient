package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="MouseWheelListener")
public class MouseWheelListener implements org.osrs.api.wrappers.MouseWheelListener{

	@BField
	public int rotation;
	@BGetter
	@Override
	public int rotation(){return rotation;}
}