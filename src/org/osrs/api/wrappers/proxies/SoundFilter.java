package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="SoundFilter")
public class SoundFilter implements org.osrs.api.wrappers.SoundFilter{

	@BField
	public int[] unity;
	@BGetter
	@Override
	public int[] unity(){return unity;}
	@BField
	public int[] pairs;
	@BGetter
	@Override
	public int[] pairs(){return pairs;}
}