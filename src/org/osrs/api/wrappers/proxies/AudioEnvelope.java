package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="AudioEnvelope")
public class AudioEnvelope implements org.osrs.api.wrappers.AudioEnvelope{

	@BField
	public int step;
	@BGetter
	@Override
	public int step(){return step;}
	@BField
	public int form;
	@BGetter
	@Override
	public int form(){return form;}
	@BField
	public int segments;
	@BGetter
	@Override
	public int segments(){return segments;}
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
	public int amplitude;
	@BGetter
	@Override
	public int amplitude(){return amplitude;}
	@BField
	public int ticks;
	@BGetter
	@Override
	public int ticks(){return ticks;}
	@BField
	public int phaseIndex;
	@BGetter
	@Override
	public int phaseIndex(){return phaseIndex;}
	@BField
	public int[] phases;
	@BGetter
	@Override
	public int[] phases(){return phases;}
	@BField
	public int[] durations;
	@BGetter
	@Override
	public int[] durations(){return durations;}
	@BField
	public int max;
	@BGetter
	@Override
	public int max(){return max;}
}