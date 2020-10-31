package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="RSException")
public class RSException implements org.osrs.api.wrappers.RSException{

	@BField
	public String text;
	@BGetter
	@Override
	public String text(){return text;}
	@BField
	public java.lang.Throwable throwable;
	@BGetter
	@Override
	public java.lang.Throwable throwable(){return throwable;}
}