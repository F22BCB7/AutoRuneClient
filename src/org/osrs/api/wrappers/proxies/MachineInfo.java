package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BGetter;
@BClass(name="MachineInfo")
public class MachineInfo implements org.osrs.api.wrappers.MachineInfo{
	@BField
	public int unknownInt1;
	@BGetter
	@Override
	public int unknownInt1() {
		return unknownInt1;
	}
	@BField
	public int unknownInt2;
	@BGetter
	@Override
	public int unknownInt2() {
		return unknownInt2;
	}
	@BField
	public int unknownInt3;
	@BGetter
	@Override
	public int unknownInt3() {
		return unknownInt3;
	}
}
