package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="AudioTask")
public class AudioTask extends TaskDataNode implements org.osrs.api.wrappers.AudioTask{

	@BField
	public org.osrs.api.wrappers.HashTable table;
	@BGetter
	@Override
	public org.osrs.api.wrappers.HashTable table(){return table;}
}