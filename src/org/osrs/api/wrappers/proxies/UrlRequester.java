package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="UrlRequester")
public class UrlRequester implements org.osrs.api.wrappers.UrlRequester{

	@BField
	public boolean closed;
	@BGetter
	@Override
	public boolean closed(){return closed;}
	@BField
	public java.util.Queue requests;
	@BGetter
	@Override
	public java.util.Queue requests(){return requests;}
	@BField
	public java.lang.Thread thread;
	@BGetter
	@Override
	public java.lang.Thread thread(){return thread;}
}