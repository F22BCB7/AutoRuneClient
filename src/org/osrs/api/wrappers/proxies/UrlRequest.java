package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="UrlRequest")
public class UrlRequest implements org.osrs.api.wrappers.UrlRequest{

	@BField
	public boolean done;
	@BGetter
	@Override
	public boolean done(){return done;}
	@BField
	public byte[] response;
	@BGetter
	@Override
	public byte[] response(){return response;}
	@BField
	public java.net.URL url;
	@BGetter
	@Override
	public java.net.URL url(){return url;}
}