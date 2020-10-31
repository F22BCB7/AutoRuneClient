package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="AudioRequestNode")
public class AudioRequestNode extends TaskDataNode implements org.osrs.api.wrappers.AudioRequestNode{

	@BField
	public int startPosition;
	@BGetter
	@Override
	public int startPosition(){return startPosition;}
	@BField
	public int endPosition;
	@BGetter
	@Override
	public int endPosition(){return endPosition;}
	@BField
	public boolean incomplete;
	@BGetter
	@Override
	public boolean incomplete(){return incomplete;}
}