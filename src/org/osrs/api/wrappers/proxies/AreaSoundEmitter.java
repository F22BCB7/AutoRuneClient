package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="AreaSoundEmitter")
public class AreaSoundEmitter extends Node implements org.osrs.api.wrappers.AreaSoundEmitter{

	@BField
	public int[] soundIDs;
	@BGetter
	@Override
	public int[] soundIDs(){return soundIDs;}
	@BField
	public int ambientSoundID;
	@BGetter
	@Override
	public int ambientSoundID(){return ambientSoundID;}
	@BField
	public org.osrs.api.wrappers.ObjectDefinition emittingObject;
	@BGetter
	@Override
	public org.osrs.api.wrappers.ObjectDefinition emittingObject(){return emittingObject;}
}