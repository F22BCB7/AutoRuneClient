package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="AudioEffect")
public class AudioEffect implements org.osrs.api.wrappers.AudioEffect{

	@BField
	public int start;
	@BGetter
	@Override
	public int start(){return start;}
	@BField
	public int end;
	@BGetter
	@Override
	public int end(){return end;}
	@BField
	public org.osrs.api.wrappers.AudioInstrument[] instruments;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AudioInstrument[] instruments(){return instruments;}
}