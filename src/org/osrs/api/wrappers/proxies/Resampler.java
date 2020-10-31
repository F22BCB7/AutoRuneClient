package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="Resampler")
public class Resampler implements org.osrs.api.wrappers.Resampler{

	@BField
	public int storedSampleRateRatio;
	@BGetter
	@Override
	public int storedSampleRateRatio(){return storedSampleRateRatio;}
	@BField
	public int playbackSampleRateRatio;
	@BGetter
	@Override
	public int playbackSampleRateRatio(){return playbackSampleRateRatio;}
	@BField
	public int[][] resampleTable;
	@BGetter
	@Override
	public int[][] resampleTable(){return resampleTable;}
}