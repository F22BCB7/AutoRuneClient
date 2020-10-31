package org.osrs.api.wrappers;

public interface ClientPreferences{
	public boolean disableLoadingAudio();
	public int gameMode();
	public String rememberedUsername();
	public boolean disableRoofs();
	public boolean hideUsername();
	public java.util.HashMap authTokens();
}