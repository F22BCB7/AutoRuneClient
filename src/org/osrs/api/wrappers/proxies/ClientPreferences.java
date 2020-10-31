package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BSetterDetour;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BGetterDetour;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="ClientPreferences")
public class ClientPreferences implements org.osrs.api.wrappers.ClientPreferences{

	/**------------------------------------------------------
	 * disableRoofs mod
	 -------------------------------------------------------*/
	@BField
	public boolean disableRoofs;
	@BGetter
	@Override
	public boolean disableRoofs(){return disableRoofs;}
	@BGetterDetour
	public boolean get_disableRoofs() {
		return true;//always disable roofs
	}
	@BSetterDetour
	public void set_disableRoofs(boolean a) {
		disableRoofs = a;
	}
	//-------------------------------------------------------
	
	@BField
	public boolean disableLoadingAudio;
	@BGetter
	@Override
	public boolean disableLoadingAudio(){return disableLoadingAudio;}
	@BField
	public int gameMode;
	@BGetter
	@Override
	public int gameMode(){return gameMode;}
	@BField
	public String rememberedUsername;
	@BGetter
	@Override
	public String rememberedUsername(){return rememberedUsername;}
	@BField
	public boolean hideUsername;
	@BGetter
	@Override
	public boolean hideUsername(){return hideUsername;}
	@BField
	public java.util.HashMap authTokens;
	@BGetter
	@Override
	public java.util.HashMap authTokens(){return authTokens;}
}