package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="AudioRunnable")
public class AudioRunnable implements org.osrs.api.wrappers.AudioRunnable{

	@BField
	public org.osrs.api.wrappers.AbstractSoundSystem[] audioSystems;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AbstractSoundSystem[] audioSystems(){return audioSystems;}
}