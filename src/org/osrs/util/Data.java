package org.osrs.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.osrs.api.methods.MethodContext;
import org.osrs.api.wrappers.OutgoingPacket;
import org.osrs.debug.DebugContext;
import org.osrs.loader.Modscript;
import org.osrs.loader.RSClassLoader;

public class Data {
	public static HashMap<Object, HashMap<String, String>> clientParameters = new HashMap<Object, HashMap<String, String>>();
	public static ArrayList<Object> clientInstances = new ArrayList<Object>();
	public static HashMap<Object, MethodContext> clientContexts = new HashMap<Object, MethodContext>();
	public static HashMap<Object, Modscript> clientModscripts = new HashMap<Object, Modscript>();
	public static HashMap<Object, RSClassLoader> clientClassloaders = new HashMap<Object, RSClassLoader>();
	public static HashMap<Object, ScriptDef> currentScripts = new HashMap<Object, ScriptDef>();
	public static Object currentInstance = null;
	public static MethodContext currentContext = null;
	public static ClassLoader scriptClassloader;
	public static DebugContext debugContext=null;
	public static int jarRevision=-1;
	public static MultiboxHandler multiboxHandler = new MultiboxHandler();
	public static byte[] randomSeed = new byte[8];
	public static int randomSeedIndice = 0;
	public static ArrayList<Account> loadedAccounts = new ArrayList<Account>();
	public static HashMap<String, Integer> intMultipliers = new HashMap<String, Integer>();
	public static HashMap<String, Long> longMultipliers = new HashMap<String, Long>();
}
