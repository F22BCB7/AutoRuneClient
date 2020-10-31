package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="MenuRowContext")
public class MenuRowContext implements org.osrs.api.wrappers.MenuRowContext{

	@BField
	public String option;
	@BGetter
	@Override
	public String option(){return option;}
	@BField
	public int arg1;
	@BGetter
	@Override
	public int arg1(){return arg1;}
	@BField
	public int type;
	@BGetter
	@Override
	public int type(){return type;}
	@BField
	public int arg0;
	@BGetter
	@Override
	public int arg0(){return arg0;}
	@BField
	public int identifier;
	@BGetter
	@Override
	public int identifier(){return identifier;}
}