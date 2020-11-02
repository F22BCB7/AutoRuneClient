package org.osrs.api.wrappers.proxies;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import org.osrs.api.objects.MenuAction;
import org.osrs.api.wrappers.IncomingPacketMeta;
import org.osrs.api.wrappers.OutgoingPacketMeta;
import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BFunction;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BGetterDetour;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BSetterDetour;
import org.osrs.injection.bytescript.BVar;
import org.osrs.util.Data;
import org.osrs.util.MultiboxHandler;

@BClass(name = "Client")
public class Client extends GameShell implements org.osrs.api.wrappers.Client{
//TODO SETUP SPOOFING
	/**
	 * random.setSeed(hash(username));

        os = random.nextInt(4) + 1;
        is64Bit = random.nextBoolean();

        switch (os) {
            case 1:
                versionType = random.nextInt(11) + 1;
                break;
            case 2:
                versionType = random.nextInt(10) + 20;
                break;
        }

        vendorType = random.nextInt(4) + 1;

        //TODO not hardcode this
        javaVersionMajor = 11;
        javaVersionMinor = 0;
        javaVersionPatch = 4;

        int[] memorySizes = {256, 512, 1024};
        maxMemory = memorySizes[random.nextInt(3)];

        if (javaVersionMajor > 3) {
            int num = random.nextInt(3);
            switch (num) {
                case 0:
                    processorCount = 8;
                    break;
                case 1:
                    processorCount = 4;
                    break;
                case 2:
                    processorCount = 2;
                    break;
            }
        }
	 * @param a
	 */
	
	@BFunction
	@Override
	public void writeWalkTileMinimap(org.osrs.api.wrappers.ByteBuffer buffer, int tileX, int tileY, int relativeScreenX, int relativeScreenY) {
		buffer.mockupWrite(18, -1);
		buffer.mockupWrite(tileX, -1);
		buffer.mockupWrite(0, -1);
		buffer.mockupWrite(tileY, -1);
		buffer.mockupWrite(relativeScreenX, -1);
		buffer.mockupWrite(relativeScreenY, -1);
		buffer.mockupWrite(Client.mapRotation, -1);
		buffer.mockupWrite(57, -1);
		buffer.mockupWrite(0, -1);
		buffer.mockupWrite(0, -1);
		buffer.mockupWrite(89, -1);
		buffer.mockupWrite(Client.localPlayer.x(), -1);
		buffer.mockupWrite(Client.localPlayer.y(), -1);
		buffer.mockupWrite(63, -1);
	}
	
	@BMethod(name="doCycle")
	public void _doCycle(int a){}
	@BDetour
	public void doCycle(int a){
		HashMap<org.osrs.api.wrappers.Widget, Integer> vticks = new HashMap<org.osrs.api.wrappers.Widget, Integer>();
		org.osrs.api.wrappers.Widget[][] widgets = Client.clientInstance.widgets();
		if(widgets!=null){
			for(int k=0;k<widgets.length;++k){
				org.osrs.api.wrappers.Widget[] widgets2 = widgets[k];
				if(widgets2!=null){
					for(int k2=0;k2<widgets2.length;++k2){
						org.osrs.api.wrappers.Widget widget = widgets2[k2];
						if(widget!=null){
							vticks.put(widget, widget.visibleCycle());
							org.osrs.api.wrappers.Widget[] widgets3 = widget.children();
							if(widgets3!=null){
								for(int k3=0;k3<widgets3.length;++k3){
									org.osrs.api.wrappers.Widget widgetb = widgets3[k3];
									if(widgetb!=null){
										vticks.put(widgetb, widgetb.visibleCycle());
									}
								}
							}
						}
					}
				}
			}
		}
		_doCycle(a);
		if(widgets!=null){
			for(int k=0;k<widgets.length;++k){
				org.osrs.api.wrappers.Widget[] widgets2 = widgets[k];
				if(widgets2!=null){
					for(int k2=0;k2<widgets2.length;++k2){
						org.osrs.api.wrappers.Widget widget = widgets2[k2];
						if(widget!=null){
							int tick = vticks.containsKey(widget)?vticks.get(widget):-1;
							widget.setIsVisible(tick!=widget.visibleCycle() && tick!=-1);
							org.osrs.api.wrappers.Widget[] widgets3 = widget.children();
							if(widgets3!=null){
								for(int k3=0;k3<widgets3.length;++k3){
									org.osrs.api.wrappers.Widget widgetb = widgets3[k3];
									if(widgetb!=null){
										tick = vticks.containsKey(widgetb)?vticks.get(widgetb):-1;
										widgetb.setIsVisible(tick!=widgetb.visibleCycle() && tick!=-1);
									}
								}
							}
						}
					}
				}
			}
		}
	}
	/**------------------------------------------------------
	 * Session Tracking
	 -------------------------------------------------------*/
	@BVar
	public ArrayList<Point> sessionTrackedClicks = new ArrayList<Point>();
	@BVar
	public ArrayList<MenuAction> sessionTrackedActions = new ArrayList<MenuAction>();
	@BFunction
	@Override
	public ArrayList<Point> getSessionTrackedClicks() {
		return sessionTrackedClicks;
	}
	@BFunction
	@Override
	public void addSessionClick(int x, int y) {
		if(sessionTrackedClicks==null)
			sessionTrackedClicks = new ArrayList<Point>();
		sessionTrackedClicks.add(new Point(x, y));
	}
	@BFunction
	@Override
	public ArrayList<MenuAction> getSessionTrackedActions() {
		return sessionTrackedActions;
	}
	@BFunction
	@Override
	public void addSessionAction(MenuAction action) {
		if(sessionTrackedActions==null)
			sessionTrackedActions = new ArrayList<MenuAction>();
		sessionTrackedActions.add(action);
	}
	//-------------------------------------------------------

	/**------------------------------------------------------
	 * Disable rendering mod
	 -------------------------------------------------------*/
	@BVar
	public static boolean disableRendering=false;
	@BFunction
	@Override
	public boolean getDisableRenderingState(){
		return disableRendering;
	}
	@BFunction
	@Override
	public void setDisableRenderingState(){
		disableRendering=!disableRendering;
	}
	//-------------------------------------------------------

	/**------------------------------------------------------
	 * gcStat mod
	 -------------------------------------------------------*/
	@BMethod(name="getGCStats")
	public static int _getGCStats(int a){return -1;}
	@BDetour
	public static int getGCStats(int a){
		int ret = _getGCStats(a);
		if(ret!=0){
			System.out.println("GC Stats returned flagged! Killing...");
		}
		return 0;//never return flagged
	}
	//-------------------------------------------------------

	/**------------------------------------------------------
	 * randomDat mod
	 -------------------------------------------------------*/
	@BMethod(name="doLoggedOutCycle")
	public void _doLoggedOutCycle(int a){}
	@BDetour
	public void doLoggedOutCycle(int a){
		randomDatData = new byte[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
		File directory = new File(System.getProperty("user.home") + "\\osrs\\profiles\\");
		if(!directory.exists())
			directory.mkdirs();
		File profile = new File(System.getProperty("user.home") + "\\osrs\\profiles\\" + username.split("@")[0] + ".dat");
		if(profile.exists()) {
			try {
				FileInputStream file = new FileInputStream(profile.getPath());
				file.read(randomDatData);
				file.close();
			} catch (Exception ex) {
				ex.printStackTrace();
				randomDatData = new byte[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
			}
		}
		_doLoggedOutCycle(a);
	}
	@BMethod(name="writeToRandomDat")
	public static void _writeToRandomDat(ByteBuffer buffer, int a, short s){}
	@BDetour
	public static void writeToRandomDat(ByteBuffer buffer, int a, short s){
		//_writeToRandomDat(buffer, a, s);
		try{
			File directory = new File(System.getProperty("user.home") + "\\osrs\\profiles\\");
			if(!directory.exists())
				directory.mkdirs();
			File profile = new File(System.getProperty("user.home") + "\\osrs\\profiles\\" + username.split("@")[0] + ".dat");
			if(!profile.exists())
				profile.createNewFile();
			FileOutputStream file = new FileOutputStream(profile.getPath());
			file.write(randomDatData);
			file.close();
			System.out.println("Updated profile : "+System.getProperty("user.home") + "\\osrs\\profiles\\");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	
	}
	@BField
	public static byte[] randomDatData;
	@BGetter
	@Override
	public byte[] randomDatData(){return randomDatData;}
	@BMethod(name="loadRandomDatFileHandler")
	public static void _loadRandomDatFileHandler(int a){}
	@BDetour
	public static void loadRandomDatFileHandler(int a){
		_loadRandomDatFileHandler(a);
	}
	//-------------------------------------------------------

	/**------------------------------------------------------
	 * LoginHandler mod
	 -------------------------------------------------------*/
	@BField
	public static String username;
	@BGetter
	@Override
	public String username(){return username;}
	@BFunction
	@Override
	public void setUsername(String name){
		username = name;
	}
	@BField
	public static String password;
	@BGetter
	@Override
	public String password(){return password;}
	@BFunction
	@Override
	public void setPassword(String pass){
		password=pass;
	}

	@BMethod(name="loadWorlds")
	public static boolean _loadWorlds(int a){return false;}
	@BDetour
	public static boolean loadWorlds(int a){
		return _loadWorlds(a);
	}
	@BFunction
	@Override
	public boolean invokeLoadWorlds(int a){
		return loadWorlds(a);
	}
	@BMethod(name="setLoginMessages")
	public static void _setLoginMessages(String a, String b, String c, byte d){}
	@BDetour
	public static void setLoginMessages(String a, String b, String c, byte d){
		_setLoginMessages(a, b, c, d);
	}
	@BFunction
	@Override
	public void setLoginMessages(String a, String b, String c){
		loginMessage1 = a;
		loginMessage2 = b;
		loginMessage3 = c;
	}
	@BField
	public static String loginMessage3;
	@BGetter
	@Override
	public String loginMessage3(){return loginMessage3;}
	@BField
	public static String loginMessage2;
	@BGetter
	@Override
	public String loginMessage2(){return loginMessage2;}
	@BField
	public static String loginMessage1;
	@BGetter
	@Override
	public String loginMessage1(){return loginMessage1;}
	@BFunction
	@Override
	public void setLoginState(int state){
		loginState=state;
	}
	@BField
	public static int loginState;
	@BGetter
	@Override
	public int loginState(){return loginState;}
	@BField
	public static int centerLoginScreenX;
	@BGetter
	@Override
	public int centerLoginScreenX(){return centerLoginScreenX;}
	@BField
	public static boolean loginWorldSelectorOpen;
	@BGetter
	@Override
	public boolean loginWorldSelectorOpen(){return loginWorldSelectorOpen;}
	//-------------------------------------------------------

	/**------------------------------------------------------
	 * xteaListener mod
	 -------------------------------------------------------*/
	@BMethod(name="xteaKeyChange")
	public static void _xteaKeyChange(boolean a, org.osrs.api.wrappers.PacketBuffer b, int c){}
	@BDetour
	public static void xteaKeyChange(boolean a, org.osrs.api.wrappers.PacketBuffer b, int c){
		_xteaKeyChange(a, b, c);
		org.osrs.injection.listeners.XteaKeyListener.xteaChanged(org.osrs.api.wrappers.proxies.Client.clientInstance);
	}
	//-------------------------------------------------------

	/**------------------------------------------------------
	 * addGameMessage mod
	 -------------------------------------------------------*/
	@BMethod(name="addChatMessage")
	public static void _addChatMessage(int a, String b, String c, String d, int e){}
	@BDetour
	public static void addChatMessage(int a, String b, String c, String d, int e){
		_addChatMessage(a, b, c, d, e);
	}
	@BFunction
	@Override //Our wrapper access to invoke the hooked method from scripts
	public void invokeAddChatMessage(int a, String b, String c, String d, int e){
		addChatMessage(a, b, c, d, e);
	}
	//-------------------------------------------------------

	/**------------------------------------------------------
	 * getItemSprite mod
	 -------------------------------------------------------*/
	@BMethod(name="getItemSprite")
	public static org.osrs.api.wrappers.Sprite _getItemSprite(int a, int b, int c, int d, int e, boolean f, int g){return null;}
	@BDetour
	public static org.osrs.api.wrappers.Sprite getItemSprite(int a, int b, int c, int d, int e, boolean f, int g){
		return _getItemSprite(a, b, c, d, e, f, g);
	}
	@BFunction
	@Override
	public org.osrs.api.wrappers.Sprite invokeGetItemSprite(int a, int b, int c, int d, int e, boolean f, int g){
		return getItemSprite(a, b, c, d, e, f, g);
	}
	//-------------------------------------------------------

	/**------------------------------------------------------
	 * getVarp mod
	 -------------------------------------------------------*/
	@BMethod(name="getVarp")
	public static int _getVarp(int a, int b){return -1;}
	@BDetour
	public static int getVarp(int a, int b){
		return _getVarp(a, b);
	}
	@BFunction
	@Override
	public int invokeGetVarp(int var, int predicate){
		return getVarp(var, predicate);
	}
	//-------------------------------------------------------

	/**------------------------------------------------------
	 * setWorld mod
	 -------------------------------------------------------*/
	@BMethod(name="setWorld")
	public static void _setWorld(org.osrs.api.wrappers.Server a, byte b){}
	@BDetour
	public static void setWorld(org.osrs.api.wrappers.Server a, byte b){
		//System.out.println("Server changed to : "+a.number()+" : "+a.members()+" : "+a.activity()+" : "+a.location()+" : "+a.domain()+" : "+a.population());
		_setWorld(a, b);
	}
	@BFunction
	@Override
	public void invokeSetWorld(org.osrs.api.wrappers.Server a, byte b){
		setWorld(a, b);
	}
	//-------------------------------------------------------

	/**------------------------------------------------------
	 * setGameState mod
	 -------------------------------------------------------*/
	@BMethod(name="setGameState")
	public static void _setGameState(int a, int b){}
	@BDetour
	public static void setGameState(int a, int b){
		_setGameState(a, b);
	}
	@BFunction
	@Override
	public void invokeSetGameState(int a, int b){
		setGameState(a, b);
	}
	//-------------------------------------------------------

	/**------------------------------------------------------
	 * getObjectDefinition mod
	 -------------------------------------------------------*/
	@BMethod(name="getObjectDefinition")
	public static org.osrs.api.wrappers.ObjectDefinition _getObjectDefinition(int a, int b){
		return null;
	}
	@BDetour
	public static org.osrs.api.wrappers.ObjectDefinition getObjectDefinition(int a, int b){
		return _getObjectDefinition(a, b);
	}
	@BFunction
	@Override
	public org.osrs.api.wrappers.ObjectDefinition invokeGetObjectDefinition(int a, int b){
		return getObjectDefinition(a, b);
	}
	//-------------------------------------------------------

	/**------------------------------------------------------
	 * getAnimationSequence mod
	 -------------------------------------------------------*/
	@BMethod(name="getAnimationSequence")
	public static org.osrs.api.wrappers.AnimationSequence _getAnimationSequence(int a, int b){
		return null;
	}
	@BDetour
	public static org.osrs.api.wrappers.AnimationSequence getAnimationSequence(int a, int b){
		return _getAnimationSequence(a, b);
	}
	@BFunction
	@Override
	public org.osrs.api.wrappers.AnimationSequence invokeGetAnimationSequence(int a, int b){
		return getAnimationSequence(a, b);
	}
	//-------------------------------------------------------

	/**------------------------------------------------------
	 * getItemDefinition mod
	 -------------------------------------------------------*/
	@BMethod(name="getItemDefinition")
	public static org.osrs.api.wrappers.ItemDefinition _getItemDefinition(int a, int b){
		return null;
	}
	@BDetour
	public static org.osrs.api.wrappers.ItemDefinition getItemDefinition(int a, int b){
		return _getItemDefinition(a, b);
	}
	@BFunction
	@Override
	public org.osrs.api.wrappers.ItemDefinition invokeGetItemDefinition(int a, int b){
		return getItemDefinition(a, b);
	}
	//-------------------------------------------------------

	/**------------------------------------------------------
	 * getNPCDefinition mod
	 -------------------------------------------------------*/
	@BMethod(name="getNPCDefinition")
	public static org.osrs.api.wrappers.NPCDefinition _getNPCDefinition(int a, int b){
		return null;
	}
	@BDetour
	public static org.osrs.api.wrappers.NPCDefinition getNPCDefinition(int a, int b){
		return _getNPCDefinition(a, b);
	}
	@BFunction
	@Override
	public org.osrs.api.wrappers.NPCDefinition invokeGetNPCDefinition(int a, int b){
		return getNPCDefinition(a, b);
	}
	//-------------------------------------------------------

	/**------------------------------------------------------
	 * getHitsplatDefinition mod
	 -------------------------------------------------------*/
	@BMethod(name="getHitsplatDefinition")
	public static org.osrs.api.wrappers.Hitsplat _getHitsplatDefinition(int a, int b){
		return null;
	}
	@BDetour
	public static org.osrs.api.wrappers.Hitsplat getHitsplatDefinition(int a, int b){
		return _getHitsplatDefinition(a, b);
	}
	@BFunction
	@Override
	public org.osrs.api.wrappers.Hitsplat invokeGetHitsplatDefinition(int a, int b){
		return getHitsplatDefinition(a, b);
	}
	//-------------------------------------------------------

	/**------------------------------------------------------
	 * processAction mod
	 -------------------------------------------------------*/
	@BMethod(name="processAction")
	public static void _processAction(int a, int b, int c, int d, String e, String f, int g, int h, int i){}
	@BDetour
	public static void processAction(int a, int b, int c, int d, String e, String f, int g, int h, int i){
		if(clientInstance.overridingProcessAction()){//Bot forcing args
			if(e.equals("BLOCK") && f.equals("ACTION")){//consume click
				System.out.println("Blocked process action invoke!");
				return;
			}
			//System.out.println("Overriding processAction!");
			a = clientInstance.getSubDoActionArgA();
			b = clientInstance.getSubDoActionArgB();
			c = clientInstance.getSubDoActionArgC();
			d = clientInstance.getSubDoActionArgD();
			e = clientInstance.getSubDoActionArgE();
			f = clientInstance.getSubDoActionArgF();
			g = clientInstance.getSubDoActionArgG();
			h = clientInstance.getSubDoActionArgH();
		}
		else{//Legit input and lets log the args
			//System.out.println("[processAction] "+a+", "+b+", "+c+", "+d+", "+e+", "+f+", "+g+", "+h+", "+i);
		}
		if(Data.multiboxHandler!=null){//forward to multiboxed accounts if enabled
			//Data.multiboxHandler.subActions(clientInstance, a, b, c, d, e, f, g, h);
		}
		//clientInstance.addSessionAction(new MenuAction(a, b, c, d, e, f, g, h));//tracking client actions+clicks for analysis
		_processAction(a, b, c, d, e, f, g, h, i);//and allow the original processAction to execute
		clientInstance.turnOffProcessActionOverride();
	}
	@BFunction
	@Override
	public void invokeProcessAction(int a, int b, int c, int d, String e, String f, int g, int h, int i){
		processAction(a, b, c, d, e, f, g, h, i);
	}
	@BFunction
	@Override
	public void overrideProcessAction(int a, int b, int c, int d, String e, String f, int g, int h){
		subDoActionArgA = a;
		subDoActionArgB = b;
		subDoActionArgC = c;
		subDoActionArgD = d;
		subDoActionArgE = e;
		subDoActionArgF = f;
		subDoActionArgG = g;
		subDoActionArgH = h;
		overrideProcessAction=true;
	}
	@BField
	public static int selectedRegionTileX;
	@BGetter
	@Override
	public int selectedRegionTileX(){return selectedRegionTileX;}
	@BFunction
	@Override
	public void setSelectedRegionTileX(int localX){
		selectedRegionTileX=localX;
	}
	@BField
	public static int selectedRegionTileY;
	@BGetter
	@Override
	public int selectedRegionTileY(){return selectedRegionTileY;}
	@BFunction
	@Override
	public void setSelectedRegionTileY(int localY){
		selectedRegionTileY=localY;
	}
	@BField
	public static boolean viewportWalking;
	@BGetter
	@Override
	public boolean viewportWalking(){return viewportWalking;}
	@BFunction
	@Override
	public void setViewportWalking(boolean flag){
		viewportWalking=flag;
	}
	@BFunction
	@Override
	public boolean overridingProcessAction(){
		return overrideProcessAction;
	}
	@BFunction
	@Override
	public void turnOffProcessActionOverride(){
		overrideProcessAction=false;
	}
	@BVar
	public boolean overrideProcessAction=false;
	@BVar
	public int subDoActionArgA = -1;
	@BVar
	public int subDoActionArgB = -1;
	@BVar
	public int subDoActionArgC = -1;
	@BVar
	public int subDoActionArgD = -1;
	@BVar
	public String subDoActionArgE = "";
	@BVar
	public String subDoActionArgF = "";
	@BVar
	public int subDoActionArgG = -1;
	@BVar
	public int subDoActionArgH = -1;
	@BFunction
	@Override
	public int getSubDoActionArgA(){return subDoActionArgA;}
	@BFunction
	@Override
	public int getSubDoActionArgB(){return subDoActionArgB;}
	@BFunction
	@Override
	public int getSubDoActionArgC(){return subDoActionArgC;}
	@BFunction
	@Override
	public int getSubDoActionArgD(){return subDoActionArgD;}
	@BFunction
	@Override
	public String getSubDoActionArgE(){return subDoActionArgE;}
	@BFunction
	@Override
	public String getSubDoActionArgF(){return subDoActionArgF;}
	@BFunction
	@Override
	public int getSubDoActionArgG(){return subDoActionArgG;}
	@BFunction
	@Override
	public int getSubDoActionArgH(){return subDoActionArgH;}

	//-------------------------------------------------------

	/**------------------------------------------------------
	 * addMenuItem mod
	 -------------------------------------------------------*/
	@BMethod(name="addMenuItem")
	public static void _addMenuItem(String a, String b, int c, int d, int e, int f, boolean g, int h){}
	@BDetour
	public static void addMenuItem(String a, String b, int c, int d, int e, int f, boolean g, int h){
		//System.out.println("[addMenuItem] "+a+", "+b+", "+c+", "+d+", "+e+", "+f+", "+g+", "+h);
		_addMenuItem(a, b, c, d, e, f, g, h);
	}
	//-------------------------------------------------------

	/**------------------------------------------------------
	 * getSpotAnim mod
	 -------------------------------------------------------*/
	@BMethod(name="getSpotAnim")
	public static org.osrs.api.wrappers.SpotAnim _getSpotAnim(int a, int b){
		return null;
	}
	@BDetour
	public static org.osrs.api.wrappers.SpotAnim getSpotAnim(int a, int b){
		return _getSpotAnim(a, b);
	}
	@BFunction
	@Override
	public org.osrs.api.wrappers.SpotAnim invokeGetSpotAnim(int a, int b){
		return getSpotAnim(a, b);
	}
	//-------------------------------------------------------

	/**------------------------------------------------------
	 * worldToScreen mod
	 -------------------------------------------------------*/
	@BMethod(name="worldToScreen")
	public static void _worldToScreen(int a, int b, int c, int d){}
	@BDetour
	public static void worldToScreen(int a, int b, int c, int d){
		_worldToScreen(a, b, c, d);
	}
	@BFunction
	@Override
	public void invokeWorldToScreen(int a, int b, int c, int d){
		worldToScreen(a, b, c, d);
	}
	//-------------------------------------------------------

	/**------------------------------------------------------
	 * getTileHeight mod
	 -------------------------------------------------------*/
	@BMethod(name="getTileHeight")
	public static int _getTileHeight(int a, int b, int c, int d){return -1;}
	@BDetour
	public static int getTileHeight(int a, int b, int c, int d){
		return _getTileHeight(a, b, c, d);
	}
	@BFunction
	@Override
	public int invokeGetTileHeight(int a, int b, int c, int d){
		return getTileHeight(a, b, c, d);
	}
	//-------------------------------------------------------

	/**------------------------------------------------------
	 * fireScriptEvent mod
	 -------------------------------------------------------*/
	@BMethod(name="fireScriptEvent")
	public static void _fireScriptEvent(org.osrs.api.wrappers.ScriptEvent a, int b){}
	@BDetour
	public static void fireScriptEvent(org.osrs.api.wrappers.ScriptEvent a, int b){
		Object[] args = a.args();
		if(!clientInstance.isHighAlchModeEnabled() ||
				(clientInstance.isHighAlchModeEnabled() && !args[0].equals(915) && !args[0].equals(2617)))//add conditional switch instead of this
			_fireScriptEvent(a, b);
	}
	@BVar
	public boolean highAlchModeEnabled=false;
	@BFunction
	@Override
	public void setHighAlchMode(boolean enable){
		highAlchModeEnabled=enable;
	}
	@BFunction
	@Override
	public boolean isHighAlchModeEnabled(){
		return highAlchModeEnabled;
	}
	@BFunction
	@Override
	public void invokeFireScriptEvent(org.osrs.api.wrappers.ScriptEvent a, int b){
		fireScriptEvent(a, b);
	}
	@BFunction
	@Override
	public void runScript(Object[] args, int b){
		ScriptEvent e = new ScriptEvent();
		e.args = args;
		fireScriptEvent(e, b);
	}
	//-------------------------------------------------------

	/**------------------------------------------------------
	 * createOutgoingPacket mod
	 -------------------------------------------------------*/
	@BMethod(name="createOutgoingPacket")
	public static org.osrs.api.wrappers.OutgoingPacket _createOutgoingPacket(org.osrs.api.wrappers.OutgoingPacketMeta a, org.osrs.api.wrappers.ISAACCipher b, byte c){
		return null;
	}
	@BDetour
	public static org.osrs.api.wrappers.OutgoingPacket createOutgoingPacket(org.osrs.api.wrappers.OutgoingPacketMeta a, org.osrs.api.wrappers.ISAACCipher b, byte c){
		//System.out.println(a.id());
		org.osrs.api.wrappers.OutgoingPacket packet = _createOutgoingPacket(a, b, c);
		if(a.id()==76)//minimap walk
			packet.buffer().initTracker();
		return packet;
	}
	@BFunction
	@Override
	public org.osrs.api.wrappers.OutgoingPacket invokeCreateOutgoingPacket(org.osrs.api.wrappers.OutgoingPacketMeta a, org.osrs.api.wrappers.ISAACCipher b, byte c){
		return createOutgoingPacket(a, b, c);
	}
	//-------------------------------------------------------

	/**------------------------------------------------------
	 * getDefinitionProperty mod
	 -------------------------------------------------------*/
	@BMethod(name="getDefinitionProperty")
	public static org.osrs.api.wrappers.DefinitionProperty _getDefinitionProperty(int a, int b){
		return null;
	}
	@BDetour
	public static org.osrs.api.wrappers.DefinitionProperty getDefinitionProperty(int a, int b){
		return _getDefinitionProperty(a, b);
	}
	@BFunction
	@Override
	public org.osrs.api.wrappers.DefinitionProperty invokeGetDefinitionProperty(int a, int b){
		return getDefinitionProperty(a, b);
	}
	//-------------------------------------------------------

	/**------------------------------------------------------
	 * shutdownClient mod
	 -------------------------------------------------------*/
	@BMethod(name="shutdownClient")
	public static void _shutdownClient(int a){}
	@BDetour
	public static void shutdownClient(int a){
		_shutdownClient(a);
	}
	@BFunction
	@Override
	public void invokeShutdownClient(int a){
		shutdownClient(a);
	}
	//-------------------------------------------------------

	/**------------------------------------------------------
	 * redrawMode mod
	 -------------------------------------------------------*/
	@BField
	public static int redrawMode;
	@BGetter
	@Override
	public int redrawMode(){return redrawMode;}
	@BGetterDetour
	public int get_redrawMode() {
		return 2;//always repaint everything
	}
	@BSetterDetour
	public void set_redrawMode(int a) {
		redrawMode = a;
	}
	//-------------------------------------------------------
	
	/**------------------------------------------------------
	 * openMenu mod
	 -------------------------------------------------------*/
	@BField
	public static boolean menuOpen;
	@BGetter
	@Override
	public boolean menuOpen(){return menuOpen;}
	@BGetterDetour
	public static boolean get_menuOpen() {
		if(ignoreNextOpen){
			return false;
		}
		return menuOpen;
	}
	@BSetterDetour
	public static void set_menuOpen(boolean a) {
		if(!a || !ignoreNextOpen){
			menuOpen = a;
		}
		else{
			ignoreNextOpen=false;
		}
	}
	@BFunction
	@Override
	public void setMenu(boolean a) {
		menuOpen = a;
	}
	@BVar
	public static boolean ignoreNextOpen=false;
	@BFunction
	@Override
	public void ignoreNextOpenMenuRequest(){
		ignoreNextOpen=true;
	}
	//-------------------------------------------------------

	@BMethod(name="updateNpcs")
	public static void _updateNpcs(boolean a, org.osrs.api.wrappers.PacketBuffer b, short c){}
	@BDetour
	public static void updateNpcs(boolean a, org.osrs.api.wrappers.PacketBuffer b, short c){
		_updateNpcs(a, b, c);
	}
	@BMethod(name="initializeGPI")
	public static void _initializeGPI(org.osrs.api.wrappers.PacketBuffer a, byte b){}
	@BDetour
	public static void initializeGPI(org.osrs.api.wrappers.PacketBuffer a, byte b){
		_initializeGPI(a, b);
	}
	@BMethod(name="processWidgetEvents")
	public static void _processWidgetEvents(org.osrs.api.wrappers.Widget[] a, int b, int c, int d, int e, int f, int g, int h, int i){}
	@BDetour
	public static void processWidgetEvents(org.osrs.api.wrappers.Widget[] a, int b, int c, int d, int e, int f, int g, int h, int i){
		_processWidgetEvents(a, b, c, d, e, f, g, h, i);
	}
	@BMethod(name="buildWidgetMenu")
	public static void _buildWidgetMenu(org.osrs.api.wrappers.Widget a, int b, int c, byte d){}
	@BDetour
	public static void buildWidgetMenu(org.osrs.api.wrappers.Widget a, int b, int c, byte d){
		_buildWidgetMenu(a, b, c, d);
	}
	@BMethod(name="updateNpcs")
	public static void _updateNpcs(boolean a, org.osrs.api.wrappers.PacketBuffer b, int c){}
	@BDetour
	public static void updateNpcs(boolean a, org.osrs.api.wrappers.PacketBuffer b, int c){
		_updateNpcs(a, b, c);
	}
	@BMethod(name="spawnObject")
	public static void _spawnObject(int a, int b, int c, int d, int e, int f, int g, int h, int i, byte j){}
	@BDetour
	public static void spawnObject(int a, int b, int c, int d, int e, int f, int g, int h, int i, byte j){
		_spawnObject(a, b, c, d, e, f, g, h, i, j);
	}
	@BMethod(name="setDraggedWidget")
	public static void _setDraggedWidget(org.osrs.api.wrappers.Widget a, int b, int c, int d){}
	@BDetour
	public static void setDraggedWidget(org.osrs.api.wrappers.Widget a, int b, int c, int d){
		_setDraggedWidget(a, b, c, d);
	}
	@BMethod(name="processWidgetRendering")
	public static void _processWidgetRendering(int a, int b, int c, int d, int e, int f, int g, int h){}
	@BDetour
	public static void processWidgetRendering(int a, int b, int c, int d, int e, int f, int g, int h){
		_processWidgetRendering(a, b, c, d, e, f, g, h);
	}
	@BMethod(name="resetDrawingArea")
	public static void _resetDrawingArea(){}
	@BDetour
	public static void resetDrawingArea(){
		_resetDrawingArea();
	}

	@BField
	public static org.osrs.api.wrappers.Sprite[] worldSpriteBackground;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Sprite[] worldSpriteBackground(){return worldSpriteBackground;}
	@BField
	public static org.osrs.api.wrappers.IndexedImage worldIndexImageRightArrow;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IndexedImage worldIndexImageRightArrow(){return worldIndexImageRightArrow;}
	@BField
	public static org.osrs.api.wrappers.IndexedImage[] worldIndexImageArrows;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IndexedImage[] worldIndexImageArrows(){return worldIndexImageArrows;}
	@BField
	public static org.osrs.api.wrappers.IndexedImage worldIndexImageLeftArrow;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IndexedImage worldIndexImageLeftArrow(){return worldIndexImageLeftArrow;}
	@BField
	public static org.osrs.api.wrappers.IndexedImage[] worldIndexImageFlags;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IndexedImage[] worldIndexImageFlags(){return worldIndexImageFlags;}
	@BField
	public static org.osrs.api.wrappers.IndexedImage[] worldIndexImageStars;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IndexedImage[] worldIndexImageStars(){return worldIndexImageStars;}
	@BField
	public static org.osrs.api.wrappers.Inflater inflater;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Inflater inflater(){return inflater;}
	@BField
	public static org.osrs.api.wrappers.ByteBuffer cacheBuffer;
	@BGetter
	@Override
	public org.osrs.api.wrappers.ByteBuffer cacheBuffer(){return cacheBuffer;}
	@BField
	public static org.osrs.api.wrappers.ByteBuffer responseHeaderBuffer;
	@BGetter
	@Override
	public org.osrs.api.wrappers.ByteBuffer responseHeaderBuffer(){return responseHeaderBuffer;}
	@BField
	public static org.osrs.api.wrappers.ByteBuffer responseArchiveBuffer;
	@BGetter
	@Override
	public org.osrs.api.wrappers.ByteBuffer responseArchiveBuffer(){return responseArchiveBuffer;}
	@BField
	public static org.osrs.api.wrappers.Producer producer;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Producer producer(){return producer;}
	@BField
	public static org.osrs.api.wrappers.HashTable componentTable;
	@BGetter
	@Override
	public org.osrs.api.wrappers.HashTable componentTable(){return componentTable;}
	@BField
	public static org.osrs.api.wrappers.HashTable widgetFlags;
	@BGetter
	@Override
	public org.osrs.api.wrappers.HashTable widgetFlags(){return widgetFlags;}
	@BField
	public static org.osrs.api.wrappers.HashTable netPendingWrites;
	@BGetter
	@Override
	public org.osrs.api.wrappers.HashTable netPendingWrites(){return netPendingWrites;}
	@BField
	public static org.osrs.api.wrappers.HashTable netPendingPriorityResponses;
	@BGetter
	@Override
	public org.osrs.api.wrappers.HashTable netPendingPriorityResponses(){return netPendingPriorityResponses;}
	@BField
	public static org.osrs.api.wrappers.HashTable netPendingResponses;
	@BGetter
	@Override
	public org.osrs.api.wrappers.HashTable netPendingResponses(){return netPendingResponses;}
	@BField
	public static org.osrs.api.wrappers.HashTable netPendingPriorityWrites;
	@BGetter
	@Override
	public org.osrs.api.wrappers.HashTable netPendingPriorityWrites(){return netPendingPriorityWrites;}
	@BField
	public static org.osrs.api.wrappers.HashTable itemContainers;
	@BGetter
	@Override
	public org.osrs.api.wrappers.HashTable itemContainers(){return itemContainers;}
	@BField
	public static org.osrs.api.wrappers.FixedSizeDeque chatlineMessages;
	@BGetter
	@Override
	public org.osrs.api.wrappers.FixedSizeDeque chatlineMessages(){return chatlineMessages;}
	@BField
	public static org.osrs.api.wrappers.NodeList classInfoList;
	@BGetter
	@Override
	public org.osrs.api.wrappers.NodeList classInfoList(){return classInfoList;}
	@BField
	public static org.osrs.api.wrappers.DoublyNode chatlineMessageNodeList;
	@BGetter
	@Override
	public org.osrs.api.wrappers.DoublyNode chatlineMessageNodeList(){return chatlineMessageNodeList;}
	@BField
	public static org.osrs.api.wrappers.ClientPreferences preferences;
	@BGetter
	@Override
	public org.osrs.api.wrappers.ClientPreferences preferences(){return preferences;}
	@BField
	public static org.osrs.api.wrappers.Queue netPendingWritesQueue;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Queue netPendingWritesQueue(){return netPendingWritesQueue;}
	@BField
	public static org.osrs.api.wrappers.Cache objectDefinitionCache;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Cache objectDefinitionCache(){return objectDefinitionCache;}
	@BField
	public static org.osrs.api.wrappers.Cache varpbitCache;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Cache varpbitCache(){return varpbitCache;}
	@BField
	public static org.osrs.api.wrappers.Cache objectModelHeaderCache;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Cache objectModelHeaderCache(){return objectModelHeaderCache;}
	@BField
	public static org.osrs.api.wrappers.Cache objectBaseModelCache;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Cache objectBaseModelCache(){return objectBaseModelCache;}
	@BField
	public static org.osrs.api.wrappers.Cache objectAnimatedModelCache;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Cache objectAnimatedModelCache(){return objectAnimatedModelCache;}
	@BField
	public static org.osrs.api.wrappers.Cache playerModelCache;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Cache playerModelCache(){return playerModelCache;}
	@BField
	public static org.osrs.api.wrappers.Cache inventoryDefinitionCache;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Cache inventoryDefinitionCache(){return inventoryDefinitionCache;}
	@BField
	public static org.osrs.api.wrappers.Cache npcDefinitionCache;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Cache npcDefinitionCache(){return npcDefinitionCache;}
	@BField
	public static org.osrs.api.wrappers.Cache npcModelCache;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Cache npcModelCache(){return npcModelCache;}
	@BField
	public static org.osrs.api.wrappers.Cache combatBarSpriteCache;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Cache combatBarSpriteCache(){return combatBarSpriteCache;}
	@BField
	public static org.osrs.api.wrappers.Cache widgetSpriteCache;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Cache widgetSpriteCache(){return widgetSpriteCache;}
	@BField
	public static org.osrs.api.wrappers.Cache widgetBufferedImageCache;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Cache widgetBufferedImageCache(){return widgetBufferedImageCache;}
	@BField
	public static org.osrs.api.wrappers.Cache widgetModelCache;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Cache widgetModelCache(){return widgetModelCache;}
	@BField
	public static org.osrs.api.wrappers.Cache widgetFontCache;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Cache widgetFontCache(){return widgetFontCache;}
	@BField
	public static org.osrs.api.wrappers.Cache varPlayerTypeCache;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Cache varPlayerTypeCache(){return varPlayerTypeCache;}
	@BField
	public static org.osrs.api.wrappers.Cache spotAnimCache;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Cache spotAnimCache(){return spotAnimCache;}
	@BField
	public static org.osrs.api.wrappers.Cache spotAnimModelCache;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Cache spotAnimModelCache(){return spotAnimModelCache;}
	@BField
	public static org.osrs.api.wrappers.Cache animationSequenceCache;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Cache animationSequenceCache(){return animationSequenceCache;}
	@BField
	public static org.osrs.api.wrappers.Cache animationSkeletonsCache;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Cache animationSkeletonsCache(){return animationSkeletonsCache;}
	@BField
	public static org.osrs.api.wrappers.Cache itemSpriteCache;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Cache itemSpriteCache(){return itemSpriteCache;}
	@BField
	public static org.osrs.api.wrappers.Cache itemDefinitionCache;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Cache itemDefinitionCache(){return itemDefinitionCache;}
	@BField
	public static org.osrs.api.wrappers.Cache itemModelCache;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Cache itemModelCache(){return itemModelCache;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_APPLET_FRAMED;
	@BGetter
	@Override
	public OutgoingPacketMeta OUTGOING_PACKET_APPLET_FRAMED() {return OUTGOING_PACKET_APPLET_FRAMED;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_BUTTON_INPUT;
	@BGetter
	@Override
	public OutgoingPacketMeta OUTGOING_PACKET_BUTTON_INPUT() {return OUTGOING_PACKET_BUTTON_INPUT;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_IDLE_LOGOUT;
	@BGetter
	@Override
	public OutgoingPacketMeta OUTGOING_PACKET_IDLE_LOGOUT() {return OUTGOING_PACKET_IDLE_LOGOUT;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_ITEM_SWAP;
	@BGetter
	@Override
	public OutgoingPacketMeta OUTGOING_PACKET_ITEM_SWAP() {return OUTGOING_PACKET_ITEM_SWAP;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_IF_BUTTON_1;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_IF_BUTTON_1(){return OUTGOING_PACKET_IF_BUTTON_1;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_IF_BUTTON_2;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_IF_BUTTON_2(){return OUTGOING_PACKET_IF_BUTTON_2;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_IF_BUTTON_3;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_IF_BUTTON_3(){return OUTGOING_PACKET_IF_BUTTON_3;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_IF_BUTTON_4;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_IF_BUTTON_4(){return OUTGOING_PACKET_IF_BUTTON_4;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_IF_BUTTON_5;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_IF_BUTTON_5(){return OUTGOING_PACKET_IF_BUTTON_5;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_IF_BUTTON_6;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_IF_BUTTON_6(){return OUTGOING_PACKET_IF_BUTTON_6;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_IF_BUTTON_7;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_IF_BUTTON_7(){return OUTGOING_PACKET_IF_BUTTON_7;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_IF_BUTTON_8;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_IF_BUTTON_8(){return OUTGOING_PACKET_IF_BUTTON_8;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_IF_BUTTON_9;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_IF_BUTTON_9(){return OUTGOING_PACKET_IF_BUTTON_9;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_IF_BUTTON_10;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_IF_BUTTON_10(){return OUTGOING_PACKET_IF_BUTTON_10;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_BUTTON_CLOSE;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_BUTTON_CLOSE(){return OUTGOING_PACKET_BUTTON_CLOSE;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_RESUME_PAUSE;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_RESUME_PAUSE(){return OUTGOING_PACKET_RESUME_PAUSE;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_RESUME_DIALOG_1;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_RESUME_DIALOG_1(){return OUTGOING_PACKET_RESUME_DIALOG_1;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_RESUME_DIALOG_2;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_RESUME_DIALOG_2(){return OUTGOING_PACKET_RESUME_DIALOG_2;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_RESUME_DIALOG_OBJECT;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_RESUME_DIALOG_OBJECT(){return OUTGOING_PACKET_RESUME_DIALOG_OBJECT;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_RESUME_DIALOG_COUNT;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_RESUME_DIALOG_COUNT(){return OUTGOING_PACKET_RESUME_DIALOG_COUNT;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_SPACEBAR_CONTINUE;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_SPACEBAR_CONTINUE(){return OUTGOING_PACKET_SPACEBAR_CONTINUE;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_KEY_HELD;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_KEY_HELD(){return OUTGOING_PACKET_KEY_HELD;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_PING_STATS_RESPONSE;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_PING_STATS_RESPONSE(){return OUTGOING_PACKET_PING_STATS_RESPONSE;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_CONSOLE_COMMAND;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_CONSOLE_COMMAND(){return OUTGOING_PACKET_CONSOLE_COMMAND;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_SEND_ABUSE_REPORT;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_SEND_ABUSE_REPORT(){return OUTGOING_PACKET_SEND_ABUSE_REPORT;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_SEND_PUBLIC_MESSAGE;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_SEND_PUBLIC_MESSAGE(){return OUTGOING_PACKET_SEND_PUBLIC_MESSAGE;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_SEND_PRIVATE_MESSAGE;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_SEND_PRIVATE_MESSAGE(){return OUTGOING_PACKET_SEND_PRIVATE_MESSAGE;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_BUG_REPORT;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_BUG_REPORT(){return OUTGOING_PACKET_BUG_REPORT;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_UPDATE_APPEARANCE;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_UPDATE_APPEARANCE(){return OUTGOING_PACKET_UPDATE_APPEARANCE;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_FILTER_PUBLIC_CHAT;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_FILTER_PUBLIC_CHAT(){return OUTGOING_PACKET_FILTER_PUBLIC_CHAT;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_FOCUS_CHANGE;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_FOCUS_CHANGE(){return OUTGOING_PACKET_FOCUS_CHANGE;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_WINDOW_MODE_UPDATE;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_WINDOW_MODE_UPDATE(){return OUTGOING_PACKET_WINDOW_MODE_UPDATE;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_REFLECTION_RESPONSE;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_REFLECTION_RESPONSE(){return OUTGOING_PACKET_REFLECTION_RESPONSE;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_MOD_CLIENT_DETECTED;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_MOD_CLIENT_DETECTED(){return OUTGOING_PACKET_MOD_CLIENT_DETECTED;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_REGION_UPDATE_RESPONSE;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_REGION_UPDATE_RESPONSE(){return OUTGOING_PACKET_REGION_UPDATE_RESPONSE;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_MAP_BUILD_COMPLETE;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_MAP_BUILD_COMPLETE(){return OUTGOING_PACKET_MAP_BUILD_COMPLETE;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_KEEP_ALIVE;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_KEEP_ALIVE(){return OUTGOING_PACKET_KEEP_ALIVE;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_CAMERA_ROTATION;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_CAMERA_ROTATION(){return OUTGOING_PACKET_CAMERA_ROTATION;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_MOUSE_CLICK;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_MOUSE_CLICK(){return OUTGOING_PACKET_MOUSE_CLICK;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_MOUSE_POSITION;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_MOUSE_POSITION(){return OUTGOING_PACKET_MOUSE_POSITION;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_MOUSE_IDLE_TICKS;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_MOUSE_IDLE_TICKS(){return OUTGOING_PACKET_MOUSE_IDLE_TICKS;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_WALK_HERE;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_WALK_HERE(){return OUTGOING_PACKET_WALK_HERE;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_MINIMAP_WALK;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_MINIMAP_WALK(){return OUTGOING_PACKET_MINIMAP_WALK;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_WALK_DEADMAN;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_WALK_DEADMAN(){return OUTGOING_PACKET_WALK_DEADMAN;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_DRAG_ITEM;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_DRAG_ITEM(){return OUTGOING_PACKET_DRAG_ITEM;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_NPC_EXAMINE;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_NPC_EXAMINE(){return OUTGOING_PACKET_NPC_EXAMINE;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_ITEM_ON_OBJECT;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_ITEM_ON_OBJECT(){return OUTGOING_PACKET_ITEM_ON_OBJECT;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_SPELL_ON_OBJECT;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_SPELL_ON_OBJECT(){return OUTGOING_PACKET_SPELL_ON_OBJECT;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_OBJECT_ACTION_0;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_OBJECT_ACTION_0(){return OUTGOING_PACKET_OBJECT_ACTION_0;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_OBJECT_ACTION_1;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_OBJECT_ACTION_1(){return OUTGOING_PACKET_OBJECT_ACTION_1;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_OBJECT_ACTION_2;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_OBJECT_ACTION_2(){return OUTGOING_PACKET_OBJECT_ACTION_2;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_OBJECT_ACTION_3;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_OBJECT_ACTION_3(){return OUTGOING_PACKET_OBJECT_ACTION_3;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_ITEM_ON_NPC;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_ITEM_ON_NPC(){return OUTGOING_PACKET_ITEM_ON_NPC;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_SPELL_ON_NPC;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_SPELL_ON_NPC(){return OUTGOING_PACKET_SPELL_ON_NPC;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_NPC_ACTION_0;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_NPC_ACTION_0(){return OUTGOING_PACKET_NPC_ACTION_0;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_NPC_ACTION_1;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_NPC_ACTION_1(){return OUTGOING_PACKET_NPC_ACTION_1;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_NPC_ACTION_2;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_NPC_ACTION_2(){return OUTGOING_PACKET_NPC_ACTION_2;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_NPC_ACTION_3;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_NPC_ACTION_3(){return OUTGOING_PACKET_NPC_ACTION_3;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_NPC_ACTION_4;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_NPC_ACTION_4(){return OUTGOING_PACKET_NPC_ACTION_4;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_ITEM_ON_PLAYER;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_ITEM_ON_PLAYER(){return OUTGOING_PACKET_ITEM_ON_PLAYER;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_SPELL_ON_PLAYER;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_SPELL_ON_PLAYER(){return OUTGOING_PACKET_SPELL_ON_PLAYER;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_ITEM_ON_GROUND_ITEM;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_ITEM_ON_GROUND_ITEM(){return OUTGOING_PACKET_ITEM_ON_GROUND_ITEM;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_SPELL_ON_GROUND_ITEM;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_SPELL_ON_GROUND_ITEM(){return OUTGOING_PACKET_SPELL_ON_GROUND_ITEM;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_GROUND_ITEM_ACTION_0;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_GROUND_ITEM_ACTION_0(){return OUTGOING_PACKET_GROUND_ITEM_ACTION_0;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_GROUND_ITEM_ACTION_1;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_GROUND_ITEM_ACTION_1(){return OUTGOING_PACKET_GROUND_ITEM_ACTION_1;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_GROUND_ITEM_ACTION_2;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_GROUND_ITEM_ACTION_2(){return OUTGOING_PACKET_GROUND_ITEM_ACTION_2;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_GROUND_ITEM_ACTION_3;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_GROUND_ITEM_ACTION_3(){return OUTGOING_PACKET_GROUND_ITEM_ACTION_3;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_GROUND_ITEM_ACTION_4;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_GROUND_ITEM_ACTION_4(){return OUTGOING_PACKET_GROUND_ITEM_ACTION_4;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_ITEM_ON_ITEM;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_ITEM_ON_ITEM(){return OUTGOING_PACKET_ITEM_ON_ITEM;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_SPELL_ON_ITEM;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_SPELL_ON_ITEM(){return OUTGOING_PACKET_SPELL_ON_ITEM;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_ITEM_ACTION_0;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_ITEM_ACTION_0(){return OUTGOING_PACKET_ITEM_ACTION_0;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_ITEM_ACTION_1;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_ITEM_ACTION_1(){return OUTGOING_PACKET_ITEM_ACTION_1;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_ITEM_ACTION_2;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_ITEM_ACTION_2(){return OUTGOING_PACKET_ITEM_ACTION_2;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_ITEM_ACTION_3;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_ITEM_ACTION_3(){return OUTGOING_PACKET_ITEM_ACTION_3;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_ITEM_ACTION_4;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_ITEM_ACTION_4(){return OUTGOING_PACKET_ITEM_ACTION_4;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_TABLE_ACTION_0;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_TABLE_ACTION_0(){return OUTGOING_PACKET_TABLE_ACTION_0;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_TABLE_ACTION_1;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_TABLE_ACTION_1(){return OUTGOING_PACKET_TABLE_ACTION_1;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_TABLE_ACTION_2;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_TABLE_ACTION_2(){return OUTGOING_PACKET_TABLE_ACTION_2;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_TABLE_ACTION_3;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_TABLE_ACTION_3(){return OUTGOING_PACKET_TABLE_ACTION_3;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_TABLE_ACTION_4;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_TABLE_ACTION_4(){return OUTGOING_PACKET_TABLE_ACTION_4;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_PLAYER_ACTION_0;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_PLAYER_ACTION_0(){return OUTGOING_PACKET_PLAYER_ACTION_0;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_PLAYER_ACTION_1;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_PLAYER_ACTION_1(){return OUTGOING_PACKET_PLAYER_ACTION_1;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_PLAYER_ACTION_2;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_PLAYER_ACTION_2(){return OUTGOING_PACKET_PLAYER_ACTION_2;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_PLAYER_ACTION_3;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_PLAYER_ACTION_3(){return OUTGOING_PACKET_PLAYER_ACTION_3;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_PLAYER_ACTION_4;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_PLAYER_ACTION_4(){return OUTGOING_PACKET_PLAYER_ACTION_4;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_PLAYER_ACTION_5;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_PLAYER_ACTION_5(){return OUTGOING_PACKET_PLAYER_ACTION_5;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_PLAYER_ACTION_6;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_PLAYER_ACTION_6(){return OUTGOING_PACKET_PLAYER_ACTION_6;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_PLAYER_ACTION_7;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_PLAYER_ACTION_7(){return OUTGOING_PACKET_PLAYER_ACTION_7;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_SPELL_ON_WIDGET;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_SPELL_ON_WIDGET(){return OUTGOING_PACKET_SPELL_ON_WIDGET;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_OBJECT_ACTION_4;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_OBJECT_ACTION_4(){return OUTGOING_PACKET_OBJECT_ACTION_4;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_OBJECT_EXAMINE;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_OBJECT_EXAMINE(){return OUTGOING_PACKET_OBJECT_EXAMINE;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_ITEM_EXAMINE;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_ITEM_EXAMINE(){return OUTGOING_PACKET_ITEM_EXAMINE;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_JOIN_CLAN;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_JOIN_CLAN(){return OUTGOING_PACKET_JOIN_CLAN;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_CLAN_RANK;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_CLAN_RANK(){return OUTGOING_PACKET_CLAN_RANK;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_ADD_FRIEND;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_ADD_FRIEND(){return OUTGOING_PACKET_ADD_FRIEND;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_REMOVE_FRIEND;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_REMOVE_FRIEND(){return OUTGOING_PACKET_REMOVE_FRIEND;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_ADD_IGNORE;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_ADD_IGNORE(){return OUTGOING_PACKET_ADD_IGNORE;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_REMOVE_IGNORE;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_REMOVE_IGNORE(){return OUTGOING_PACKET_REMOVE_IGNORE;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_UNUSED_1;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_UNUSED_1(){return OUTGOING_PACKET_UNUSED_1;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_UNUSED_2;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_UNUSED_2(){return OUTGOING_PACKET_UNUSED_2;}
	@BField
	public static org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_UNUSED_3;
	@BGetter
	@Override
	public org.osrs.api.wrappers.OutgoingPacketMeta OUTGOING_PACKET_UNUSED_3(){return OUTGOING_PACKET_UNUSED_3;}
	@BField
	public static org.osrs.api.wrappers.RegionUpdatePacketHeader REMOVE_GROUND_ITEM;
	@BGetter
	@Override
	public org.osrs.api.wrappers.RegionUpdatePacketHeader REMOVE_GROUND_ITEM(){return REMOVE_GROUND_ITEM;}
	@BField
	public static org.osrs.api.wrappers.RegionUpdatePacketHeader ADD_GROUND_ITEM;
	@BGetter
	@Override
	public org.osrs.api.wrappers.RegionUpdatePacketHeader ADD_GROUND_ITEM(){return ADD_GROUND_ITEM;}
	@BField
	public static org.osrs.api.wrappers.RegionUpdatePacketHeader AREA_SOUNDS;
	@BGetter
	@Override
	public org.osrs.api.wrappers.RegionUpdatePacketHeader AREA_SOUNDS(){return AREA_SOUNDS;}
	@BField
	public static org.osrs.api.wrappers.RegionUpdatePacketHeader ADD_PENDING_SPAWN;
	@BGetter
	@Override
	public org.osrs.api.wrappers.RegionUpdatePacketHeader ADD_PENDING_SPAWN(){return ADD_PENDING_SPAWN;}
	@BField
	public static org.osrs.api.wrappers.RegionUpdatePacketHeader ADD_PROJECTILE;
	@BGetter
	@Override
	public org.osrs.api.wrappers.RegionUpdatePacketHeader ADD_PROJECTILE(){return ADD_PROJECTILE;}
	@BField
	public static org.osrs.api.wrappers.RegionUpdatePacketHeader ANIMATE_OBJECT;
	@BGetter
	@Override
	public org.osrs.api.wrappers.RegionUpdatePacketHeader ANIMATE_OBJECT(){return ANIMATE_OBJECT;}
	@BField
	public static org.osrs.api.wrappers.RegionUpdatePacketHeader REMOVE_PENDING_SPAWN;
	@BGetter
	@Override
	public org.osrs.api.wrappers.RegionUpdatePacketHeader REMOVE_PENDING_SPAWN(){return REMOVE_PENDING_SPAWN;}
	@BField
	public static org.osrs.api.wrappers.RegionUpdatePacketHeader PERFORM_LOC_GRAPHIC;
	@BGetter
	@Override
	public org.osrs.api.wrappers.RegionUpdatePacketHeader PERFORM_LOC_GRAPHIC(){return PERFORM_LOC_GRAPHIC;}
	@BField
	public static org.osrs.api.wrappers.RegionUpdatePacketHeader ADD_INTERACTABLE;
	@BGetter
	@Override
	public org.osrs.api.wrappers.RegionUpdatePacketHeader ADD_INTERACTABLE(){return ADD_INTERACTABLE;}
	@BField
	public static org.osrs.api.wrappers.RegionUpdatePacketHeader UPDATE_GROUND_ITEM;
	@BGetter
	@Override
	public org.osrs.api.wrappers.RegionUpdatePacketHeader UPDATE_GROUND_ITEM(){return UPDATE_GROUND_ITEM;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_UPDATE_CLAN_CHAT;
	@BGetter
	@Override
	public IncomingPacketMeta INCOMING_PACKET_UPDATE_CLAN_CHAT() {return INCOMING_PACKET_UPDATE_CLAN_CHAT;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_SET_WIDGET_MODEL;
	@BGetter
	@Override
	public IncomingPacketMeta INCOMING_PACKET_SET_WIDGET_MODEL() {return INCOMING_PACKET_SET_WIDGET_MODEL;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_OCULUS_ORB_TOGGLE;
	@BGetter
	@Override
	public IncomingPacketMeta INCOMING_PACKET_OCULUS_ORB_TOGGLE() {return INCOMING_PACKET_OCULUS_ORB_TOGGLE;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_OCULUS_ORB_ON;
	@BGetter
	@Override
	public IncomingPacketMeta INCOMING_PACKET_OCULUS_ORB_ON() {return INCOMING_PACKET_OCULUS_ORB_ON;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_SET_PLAYER_CHATHEAD;
	@BGetter
	@Override
	public IncomingPacketMeta INCOMING_PACKET_SET_PLAYER_CHATHEAD() {return INCOMING_PACKET_SET_PLAYER_CHATHEAD;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_REFLECTION_CHECK;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_REFLECTION_CHECK(){return INCOMING_PACKET_REFLECTION_CHECK;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_UPDATE_DESTINATION;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_UPDATE_DESTINATION(){return INCOMING_PACKET_UPDATE_DESTINATION;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_PRIVATE_MESSAGE;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_PRIVATE_MESSAGE(){return INCOMING_PACKET_PRIVATE_MESSAGE;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_SET_ANGLE;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_SET_ANGLE(){return INCOMING_PACKET_SET_ANGLE;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_MIDI_SONG;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_MIDI_SONG(){return INCOMING_PACKET_MIDI_SONG;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_FRIENDS_CHAT_MESSAGE;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_FRIENDS_CHAT_MESSAGE(){return INCOMING_PACKET_FRIENDS_CHAT_MESSAGE;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_REBUILD_NORMAL;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_REBUILD_NORMAL(){return INCOMING_PACKET_REBUILD_NORMAL;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_REBUILD_REGION;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_REBUILD_REGION(){return INCOMING_PACKET_REBUILD_REGION;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_NPC_INFO_SMALL;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_NPC_INFO_SMALL(){return INCOMING_PACKET_NPC_INFO_SMALL;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_MOVE_SUB;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_MOVE_SUB(){return INCOMING_PACKET_MOVE_SUB;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_UPDATE_INVENTORY_FULL;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_UPDATE_INVENTORY_FULL(){return INCOMING_PACKET_UPDATE_INVENTORY_FULL;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_RUN_CLIENTSCRIPT;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_RUN_CLIENTSCRIPT(){return INCOMING_PACKET_RUN_CLIENTSCRIPT;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_UPDATE_INVENTORY_PARTIAL;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_UPDATE_INVENTORY_PARTIAL(){return INCOMING_PACKET_UPDATE_INVENTORY_PARTIAL;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_CAM_LOOK_AT;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_CAM_LOOK_AT(){return INCOMING_PACKET_CAM_LOOK_AT;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_PING_STATISTICS_REQUEST;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_PING_STATISTICS_REQUEST(){return INCOMING_PACKET_PING_STATISTICS_REQUEST;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_SET_EVENTS;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_SET_EVENTS(){return INCOMING_PACKET_SET_EVENTS;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_GAME_MESSAGE;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_GAME_MESSAGE(){return INCOMING_PACKET_GAME_MESSAGE;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_LOGOUT_TRANSFER;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_LOGOUT_TRANSFER(){return INCOMING_PACKET_LOGOUT_TRANSFER;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_SET_POSITION;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_SET_POSITION(){return INCOMING_PACKET_SET_POSITION;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_OPEN_SUB;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_OPEN_SUB(){return INCOMING_PACKET_OPEN_SUB;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_HINT_ARROW;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_HINT_ARROW(){return INCOMING_PACKET_HINT_ARROW;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_CAM_MOVE_TO;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_CAM_MOVE_TO(){return INCOMING_PACKET_CAM_MOVE_TO;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_PRIVATE_MESSAGE_ECHO;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_PRIVATE_MESSAGE_ECHO(){return INCOMING_PACKET_PRIVATE_MESSAGE_ECHO;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_UPDATE_ZONE_FULL_FOLLOWS;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_UPDATE_ZONE_FULL_FOLLOWS(){return INCOMING_PACKET_UPDATE_ZONE_FULL_FOLLOWS;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_UPDATE_ZONE_PARTIAL_ENCLOSED;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_UPDATE_ZONE_PARTIAL_ENCLOSED(){return INCOMING_PACKET_UPDATE_ZONE_PARTIAL_ENCLOSED;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_SET_TEXT;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_SET_TEXT(){return INCOMING_PACKET_SET_TEXT;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_SET_SCROLL_POSITION;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_SET_SCROLL_POSITION(){return INCOMING_PACKET_SET_SCROLL_POSITION;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_SET_ANIMATION;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_SET_ANIMATION(){return INCOMING_PACKET_SET_ANIMATION;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_OPEN_TOP;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_OPEN_TOP(){return INCOMING_PACKET_OPEN_TOP;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_SET_COLOR;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_SET_COLOR(){return INCOMING_PACKET_SET_COLOR;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_UPDATE_STAT;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_UPDATE_STAT(){return INCOMING_PACKET_UPDATE_STAT;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_URL_OPEN;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_URL_OPEN(){return INCOMING_PACKET_URL_OPEN;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_SET_OBJECT;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_SET_OBJECT(){return INCOMING_PACKET_SET_OBJECT;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_SYNTH_SOUND;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_SYNTH_SOUND(){return INCOMING_PACKET_SYNTH_SOUND;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_CAM_SHAKE;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_CAM_SHAKE(){return INCOMING_PACKET_CAM_SHAKE;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_CLOSE_SUB;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_CLOSE_SUB(){return INCOMING_PACKET_CLOSE_SUB;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_MIDI_JINGLE;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_MIDI_JINGLE(){return INCOMING_PACKET_MIDI_JINGLE;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_UPDATE_REBOOT_TIMER;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_UPDATE_REBOOT_TIMER(){return INCOMING_PACKET_UPDATE_REBOOT_TIMER;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_UPDATE_FRIENDS_LIST;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_UPDATE_FRIENDS_LIST(){return INCOMING_PACKET_UPDATE_FRIENDS_LIST;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_SYNC_CLIENT_VARCACHE;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_SYNC_CLIENT_VARCACHE(){return INCOMING_PACKET_SYNC_CLIENT_VARCACHE;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_TRIGGER_ON_DIALOG_ABORT;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_TRIGGER_ON_DIALOG_ABORT(){return INCOMING_PACKET_TRIGGER_ON_DIALOG_ABORT;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_FRIENDS_LIST_LOADED;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_FRIENDS_LIST_LOADED(){return INCOMING_PACKET_FRIENDS_LIST_LOADED;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_CAM_RESET;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_CAM_RESET(){return INCOMING_PACKET_CAM_RESET;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_RESET_ANIMS;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_RESET_ANIMS(){return INCOMING_PACKET_RESET_ANIMS;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_RESET_CLIENT_VARCACHE;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_RESET_CLIENT_VARCACHE(){return INCOMING_PACKET_RESET_CLIENT_VARCACHE;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_PLAYER_INFO;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_PLAYER_INFO(){return INCOMING_PACKET_PLAYER_INFO;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_SET_NPC_HEAD;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_SET_NPC_HEAD(){return INCOMING_PACKET_SET_NPC_HEAD;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_SET_PLAYER_OP;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_SET_PLAYER_OP(){return INCOMING_PACKET_SET_PLAYER_OP;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_SET_HIDE;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_SET_HIDE(){return INCOMING_PACKET_SET_HIDE;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_VARP_SMALL;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_VARP_SMALL(){return INCOMING_PACKET_VARP_SMALL;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_VARP_LARGE;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_VARP_LARGE(){return INCOMING_PACKET_VARP_LARGE;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_UPDATE_CLAN_CHAT_SINGLE_USER;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_UPDATE_CLAN_CHAT_SINGLE_USER(){return INCOMING_PACKET_UPDATE_CLAN_CHAT_SINGLE_USER;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_UPDATE_INVENTORY_STOP_TRANSMIT;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_UPDATE_INVENTORY_STOP_TRANSMIT(){return INCOMING_PACKET_UPDATE_INVENTORY_STOP_TRANSMIT;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_UPDATE_PLAYER_WEIGHT;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_UPDATE_PLAYER_WEIGHT(){return INCOMING_PACKET_UPDATE_PLAYER_WEIGHT;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_UPDATE_PLAYER_ENERGY;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_UPDATE_PLAYER_ENERGY(){return INCOMING_PACKET_UPDATE_PLAYER_ENERGY;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_SET_PRIVATE_CHAT_FILTER;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_SET_PRIVATE_CHAT_FILTER(){return INCOMING_PACKET_SET_PRIVATE_CHAT_FILTER;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_UPDATE_SITE_SETTINGS;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_UPDATE_SITE_SETTINGS(){return INCOMING_PACKET_UPDATE_SITE_SETTINGS;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_UPDATE_ZONE_PARTIAL_FOLLOWS;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_UPDATE_ZONE_PARTIAL_FOLLOWS(){return INCOMING_PACKET_UPDATE_ZONE_PARTIAL_FOLLOWS;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_SET_CHAT_FILTER;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_SET_CHAT_FILTER(){return INCOMING_PACKET_SET_CHAT_FILTER;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_UPDATE_UID;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_UPDATE_UID(){return INCOMING_PACKET_UPDATE_UID;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_UPDATE_IGNORE_LIST;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_UPDATE_IGNORE_LIST(){return INCOMING_PACKET_UPDATE_IGNORE_LIST;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_MINIMAP_TOGGLE;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_MINIMAP_TOGGLE(){return INCOMING_PACKET_MINIMAP_TOGGLE;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_NPC_INFO_LARGE;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_NPC_INFO_LARGE(){return INCOMING_PACKET_NPC_INFO_LARGE;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_UPDATE_WIDGETS;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_UPDATE_WIDGETS(){return INCOMING_PACKET_UPDATE_WIDGETS;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_UPDATE_EXCHANGE_OFFERS;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_UPDATE_EXCHANGE_OFFERS(){return INCOMING_PACKET_UPDATE_EXCHANGE_OFFERS;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_SET_WIDGET_ITEMS;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_SET_WIDGET_ITEMS(){return INCOMING_PACKET_SET_WIDGET_ITEMS;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_SET_WIDGET_MODEL_ROTATION;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_SET_WIDGET_MODEL_ROTATION(){return INCOMING_PACKET_SET_WIDGET_MODEL_ROTATION;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_DISCONNECTED;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_DISCONNECTED(){return INCOMING_PACKET_DISCONNECTED;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_UNKNOWN_3;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_UNKNOWN_3(){return INCOMING_PACKET_UNKNOWN_3;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_EXCHANGE_HISTORY;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_EXCHANGE_HISTORY(){return INCOMING_PACKET_EXCHANGE_HISTORY;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_OBJ_ADD;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_OBJ_ADD(){return INCOMING_PACKET_OBJ_ADD;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_SOUND_AREA;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_SOUND_AREA(){return INCOMING_PACKET_SOUND_AREA;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_LOC_DEL;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_LOC_DEL(){return INCOMING_PACKET_LOC_DEL;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_LOC_GRAPHIC;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_LOC_GRAPHIC(){return INCOMING_PACKET_LOC_GRAPHIC;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_OBJ_COUNT;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_OBJ_COUNT(){return INCOMING_PACKET_OBJ_COUNT;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_MAP_PROJ;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_MAP_PROJ(){return INCOMING_PACKET_MAP_PROJ;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_LOC_ANIM;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_LOC_ANIM(){return INCOMING_PACKET_LOC_ANIM;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_MAP_ANIM;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_MAP_ANIM(){return INCOMING_PACKET_MAP_ANIM;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_LOGOUT_FULL;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_LOGOUT_FULL(){return INCOMING_PACKET_LOGOUT_FULL;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_OBJ_DEL;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_OBJ_DEL(){return INCOMING_PACKET_OBJ_DEL;}
	@BField
	public static org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_LOC_ADD;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IncomingPacketMeta INCOMING_PACKET_LOC_ADD(){return INCOMING_PACKET_LOC_ADD;}
	@BField
	public static org.osrs.api.wrappers.AbstractSocket gameConnectionSocket;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AbstractSocket gameConnectionSocket(){return gameConnectionSocket;}
	@BField
	public static org.osrs.api.wrappers.AbstractSocket lastConnectionSocket;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AbstractSocket lastConnectionSocket(){return lastConnectionSocket;}
	@BField
	public static org.osrs.api.wrappers.PacketContext gameConnection;
	@BGetter
	@Override
	public org.osrs.api.wrappers.PacketContext gameConnection(){return gameConnection;}
	@BField
	public static org.osrs.api.wrappers.AbstractArchive objectDefinitionFileSystem;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AbstractArchive objectDefinitionFileSystem(){return objectDefinitionFileSystem;}
	@BField
	public static org.osrs.api.wrappers.AbstractArchive objectDefinitionModelFileSystem;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AbstractArchive objectDefinitionModelFileSystem(){return objectDefinitionModelFileSystem;}
	@BField
	public static org.osrs.api.wrappers.AbstractArchive animationSkeletonFileSystem;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AbstractArchive animationSkeletonFileSystem(){return animationSkeletonFileSystem;}
	@BField
	public static org.osrs.api.wrappers.AbstractArchive widgetModelFileSystem;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AbstractArchive widgetModelFileSystem(){return widgetModelFileSystem;}
	@BField
	public static org.osrs.api.wrappers.AbstractArchive npcModelFileSystem;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AbstractArchive npcModelFileSystem(){return npcModelFileSystem;}
	@BField
	public static org.osrs.api.wrappers.AbstractArchive npcDefinitionFileSystem;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AbstractArchive npcDefinitionFileSystem(){return npcDefinitionFileSystem;}
	@BField
	public static org.osrs.api.wrappers.AbstractArchive combatBarSpriteFileSystem;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AbstractArchive combatBarSpriteFileSystem(){return combatBarSpriteFileSystem;}
	@BField
	public static org.osrs.api.wrappers.AbstractArchive varPlayerTypeFileSystem;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AbstractArchive varPlayerTypeFileSystem(){return varPlayerTypeFileSystem;}
	@BField
	public static org.osrs.api.wrappers.AbstractArchive identityKitNodeFileSystem;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AbstractArchive identityKitNodeFileSystem(){return identityKitNodeFileSystem;}
	@BField
	public static org.osrs.api.wrappers.AbstractArchive inventoryDefinitionFileSystem;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AbstractArchive inventoryDefinitionFileSystem(){return inventoryDefinitionFileSystem;}
	@BField
	public static org.osrs.api.wrappers.AbstractArchive spotAnimModelFileSystem;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AbstractArchive spotAnimModelFileSystem(){return spotAnimModelFileSystem;}
	@BField
	public static org.osrs.api.wrappers.AbstractArchive spotAnimFileSystem;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AbstractArchive spotAnimFileSystem(){return spotAnimFileSystem;}
	@BField
	public static org.osrs.api.wrappers.AbstractArchive animationSequenceFileSystem;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AbstractArchive animationSequenceFileSystem(){return animationSequenceFileSystem;}
	@BField
	public static org.osrs.api.wrappers.AbstractArchive animationSkinFileSystem;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AbstractArchive animationSkinFileSystem(){return animationSkinFileSystem;}
	@BField
	public static org.osrs.api.wrappers.AbstractArchive grandExchangeItemFileSystem;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AbstractArchive grandExchangeItemFileSystem(){return grandExchangeItemFileSystem;}
	@BField
	public static org.osrs.api.wrappers.AbstractArchive itemDefinitionFileSystem;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AbstractArchive itemDefinitionFileSystem(){return itemDefinitionFileSystem;}
	@BField
	public static org.osrs.api.wrappers.AccessFileHandler idx255FileHandler;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AccessFileHandler idx255FileHandler(){return idx255FileHandler;}
	@BField
	public static org.osrs.api.wrappers.AccessFileHandler dat2FileHandler;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AccessFileHandler dat2FileHandler(){return dat2FileHandler;}
	@BField
	public static org.osrs.api.wrappers.AccessFileHandler randomDatFileHandler;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AccessFileHandler randomDatFileHandler(){return randomDatFileHandler;}
	@BField
	public static org.osrs.api.wrappers.AccessFileHandler[] indexFileHandlers;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AccessFileHandler[] indexFileHandlers(){return indexFileHandlers;}
	@BField
	public static org.osrs.api.wrappers.Resampler resampler;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Resampler resampler(){return resampler;}
	@BField
	public static org.osrs.api.wrappers.Task loginConnectionTask;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Task loginConnectionTask(){return loginConnectionTask;}
	@BField
	public static org.osrs.api.wrappers.Task gameConnectionTask;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Task gameConnectionTask(){return gameConnectionTask;}
	@BField
	public static org.osrs.api.wrappers.Signlink taskManager;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Signlink taskManager(){return taskManager;}
	@BField
	public static org.osrs.api.wrappers.Deque widgetTimerScriptEventDeque;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Deque widgetTimerScriptEventDeque(){return widgetTimerScriptEventDeque;}
	@BField
	public static org.osrs.api.wrappers.Deque mouseReleaseScriptEventDeque;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Deque mouseReleaseScriptEventDeque(){return mouseReleaseScriptEventDeque;}
	@BField
	public static org.osrs.api.wrappers.Deque mouseClickScriptEventDeque;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Deque mouseClickScriptEventDeque(){return mouseClickScriptEventDeque;}
	@BField
	public static org.osrs.api.wrappers.Deque[][][] itemPileDeque;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Deque[][][] itemPileDeque(){return itemPileDeque;}
	@BField
	public static org.osrs.api.wrappers.Deque pendingSpawnDeque;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Deque pendingSpawnDeque(){return pendingSpawnDeque;}
	@BField
	public static org.osrs.api.wrappers.Deque animableObjectDeque;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Deque animableObjectDeque(){return animableObjectDeque;}
	@BField
	public static org.osrs.api.wrappers.Deque projectileDeque;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Deque projectileDeque(){return projectileDeque;}
	@BField
	public static org.osrs.api.wrappers.Deque fileSystemRequestDeque;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Deque fileSystemRequestDeque(){return fileSystemRequestDeque;}
	@BField
	public static org.osrs.api.wrappers.Deque fileSystemResponseDeque;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Deque fileSystemResponseDeque(){return fileSystemResponseDeque;}
	@BField
	public static org.osrs.api.wrappers.Deque drawnTileDeque;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Deque drawnTileDeque(){return drawnTileDeque;}
	@BField
	public static org.osrs.api.wrappers.Deque areaSoundEmitterDeque;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Deque areaSoundEmitterDeque(){return areaSoundEmitterDeque;}
	@BField
	public static org.osrs.api.wrappers.MouseTracker mouseTracker;
	@BGetter
	@Override
	public org.osrs.api.wrappers.MouseTracker mouseTracker(){return mouseTracker;}
	@BField
	public static org.osrs.api.wrappers.CollisionMap[] collisionMaps;
	@BGetter
	@Override
	public org.osrs.api.wrappers.CollisionMap[] collisionMaps(){return collisionMaps;}
	@BField
	public static org.osrs.api.wrappers.AudioEffect[] audioEffects;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AudioEffect[] audioEffects(){return audioEffects;}
	@BField
	public static org.osrs.api.wrappers.Npc[] npcs;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Npc[] npcs(){return npcs;}
	@BField
	public static org.osrs.api.wrappers.ChatSetting CHAT_SETTING_PUBLIC;
	@BGetter
	@Override
	public org.osrs.api.wrappers.ChatSetting CHAT_SETTING_PUBLIC(){return CHAT_SETTING_PUBLIC;}
	@BField
	public static org.osrs.api.wrappers.ChatSetting CHAT_SETTING_OFF;
	@BGetter
	@Override
	public org.osrs.api.wrappers.ChatSetting CHAT_SETTING_OFF(){return CHAT_SETTING_OFF;}
	@BField
	public static org.osrs.api.wrappers.ChatSetting CHAT_SETTING_PRIVATE;
	@BGetter
	@Override
	public org.osrs.api.wrappers.ChatSetting CHAT_SETTING_PRIVATE(){return CHAT_SETTING_PRIVATE;}
	@BField
	public static org.osrs.api.wrappers.Player[] players;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Player[] players(){return players;}
	@BField
	public static org.osrs.api.wrappers.Player localPlayer;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Player localPlayer(){return localPlayer;}
	@BField
	public static org.osrs.api.wrappers.MenuRowContext topMenuRowContext;
	@BGetter
	@Override
	public org.osrs.api.wrappers.MenuRowContext topMenuRowContext(){return topMenuRowContext;}
	@BField
	public static org.osrs.api.wrappers.AttackOptionSetting npcAttackOptionSetting;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AttackOptionSetting npcAttackOptionSetting(){return npcAttackOptionSetting;}
	@BField
	public static org.osrs.api.wrappers.AttackOptionSetting playerAttackOptionSetting;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AttackOptionSetting playerAttackOptionSetting(){return playerAttackOptionSetting;}
	@BField
	public static org.osrs.api.wrappers.GrandExchangeOffer[] exchangeOffers;
	@BGetter
	@Override
	public org.osrs.api.wrappers.GrandExchangeOffer[] exchangeOffers(){return exchangeOffers;}
	@BField
	public static org.osrs.api.wrappers.MouseListener mouseListener;
	@BGetter
	@Override
	public org.osrs.api.wrappers.MouseListener mouseListener(){return mouseListener;}
	@BField
	public static org.osrs.api.wrappers.KeyboardListener keyboardListener;
	@BGetter
	@Override
	public org.osrs.api.wrappers.KeyboardListener keyboardListener(){return keyboardListener;}
	@BField
	public static org.osrs.api.wrappers.Region region;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Region region(){return region;}
	@BField
	public static org.osrs.api.wrappers.Widget pleaseWaitWidget;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Widget pleaseWaitWidget(){return pleaseWaitWidget;}
	@BField
	public static org.osrs.api.wrappers.Widget mouseCrosshairWidget;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Widget mouseCrosshairWidget(){return mouseCrosshairWidget;}
	@BField
	public static org.osrs.api.wrappers.Widget[] cachedWidgets;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Widget[] cachedWidgets(){return cachedWidgets;}
	@BField
	public static org.osrs.api.wrappers.Widget[][] widgets;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Widget[][] widgets(){return widgets;}
	@BField
	public static org.osrs.api.wrappers.RuneScriptVM runescriptVM;
	@BGetter
	@Override
	public org.osrs.api.wrappers.RuneScriptVM runescriptVM(){return runescriptVM;}
	@BField
	public static org.osrs.api.wrappers.Server[] servers;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Server[] servers(){return servers;}
	@BField
	public static org.osrs.api.wrappers.RSShadowedFont fontBold12pt;
	@BGetter
	@Override
	public org.osrs.api.wrappers.RSShadowedFont fontBold12pt(){return fontBold12pt;}
	@BField
	public static org.osrs.api.wrappers.RSShadowedFont fontPlain12pt;
	@BGetter
	@Override
	public org.osrs.api.wrappers.RSShadowedFont fontPlain12pt(){return fontPlain12pt;}
	@BField
	public static org.osrs.api.wrappers.RSShadowedFont fontPlain11pt;
	@BGetter
	@Override
	public org.osrs.api.wrappers.RSShadowedFont fontPlain11pt(){return fontPlain11pt;}
	@BField
	public static org.osrs.api.wrappers.RSShadowedFont itemAmountFont;
	@BGetter
	@Override
	public org.osrs.api.wrappers.RSShadowedFont itemAmountFont(){return itemAmountFont;}
	@BField
	public static org.osrs.api.wrappers.AudioRunnable audioRunnable;
	@BGetter
	@Override
	public org.osrs.api.wrappers.AudioRunnable audioRunnable(){return audioRunnable;}
	@BField
	public static org.osrs.api.wrappers.FriendManager friendManager;
	@BGetter
	@Override
	public org.osrs.api.wrappers.FriendManager friendManager(){return friendManager;}
	@BField
	public static org.osrs.api.wrappers.ClanContainer clanContainer;
	@BGetter
	@Override
	public org.osrs.api.wrappers.ClanContainer clanContainer(){return clanContainer;}
	@BField
	public static org.osrs.api.wrappers.Occluder[] regionOccluders;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Occluder[] regionOccluders(){return regionOccluders;}
	@BField
	public static org.osrs.api.wrappers.Occluder[][] loadedOccluders;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Occluder[][] loadedOccluders(){return loadedOccluders;}
	@BField
	public static org.osrs.api.wrappers.PlatformInfo platformInfo;
	@BGetter
	@Override
	public org.osrs.api.wrappers.PlatformInfo platformInfo(){return platformInfo;}
	@BField
	public static org.osrs.api.wrappers.Client clientInstance;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Client clientInstance(){return clientInstance;}
	@BField
	public static boolean clientFocused;
	@BGetter
	@Override
	public boolean clientFocused(){return clientFocused;}
	@BField
	public static int itemSelectionState;
	@BGetter
	@Override
	public int itemSelectionState(){return itemSelectionState;}
	@BField
	public static String[] menuTargets;
	@BGetter
	@Override
	public String[] menuTargets(){return menuTargets;}
	@BField
	public static int[] menuOpcodes;
	@BGetter
	@Override
	public int[] menuOpcodes(){return menuOpcodes;}
	@BField
	public static int[] menuTertiaryArgs;
	@BGetter
	@Override
	public int[] menuTertiaryArgs(){return menuTertiaryArgs;}
	@BField
	public static int[] menuSecondaryArgs;
	@BGetter
	@Override
	public int[] menuSecondaryArgs(){return menuSecondaryArgs;}
	@BField
	public static String[] menuActions;
	@BGetter
	@Override
	public String[] menuActions(){return menuActions;}
	@BField
	public static boolean[] menuShiftClickActions;
	@BGetter
	@Override
	public boolean[] menuShiftClickActions(){return menuShiftClickActions;}
	@BField
	public static int rootWidgetIndex;
	@BGetter
	@Override
	public int rootWidgetIndex(){return rootWidgetIndex;}
	@BField
	public static int playerWeight;
	@BGetter
	@Override
	public int playerWeight(){return playerWeight;}
	@BField
	public static int runEnergy;
	@BGetter
	@Override
	public int runEnergy(){return runEnergy;}
	@BField
	public static int[] widgetWidths;
	@BGetter
	@Override
	public int[] widgetWidths(){return widgetWidths;}
	@BField
	public static int[] widgetPositionsY;
	@BGetter
	@Override
	public int[] widgetPositionsY(){return widgetPositionsY;}
	@BField
	public static int[] widgetHeights;
	@BGetter
	@Override
	public int[] widgetHeights(){return widgetHeights;}
	@BField
	public static int[] widgetPositionsX;
	@BGetter
	@Override
	public int[] widgetPositionsX(){return widgetPositionsX;}
	@BField
	public static boolean resizeMode;
	@BGetter
	@Override
	public boolean resizeMode(){return resizeMode;}
	@BField
	public static int mapRotation;
	@BGetter
	@Override
	public int mapRotation(){return mapRotation;}
	@BField
	public static int mouseCrosshairState;
	@BGetter
	@Override
	public int mouseCrosshairState(){return mouseCrosshairState;}
	@BField
	public static boolean[] playerActionPriorities;
	@BGetter
	@Override
	public boolean[] playerActionPriorities(){return playerActionPriorities;}
	@BField
	public static int[] currentSkillLevels;
	@BGetter
	@Override
	public int[] currentSkillLevels(){return currentSkillLevels;}
	@BField
	public static int[] skillExperiences;
	@BGetter
	@Override
	public int[] skillExperiences(){return skillExperiences;}
	@BField
	public static int[] absoluteSkillLevels;
	@BGetter
	@Override
	public int[] absoluteSkillLevels(){return absoluteSkillLevels;}
	@BField
	public static String[] playerActions;
	@BGetter
	@Override
	public String[] playerActions(){return playerActions;}
	@BField
	public static int[] mapRegionIDs;
	@BGetter
	@Override
	public int[] mapRegionIDs(){return mapRegionIDs;}
	@BField
	public static int menuItemCount;
	@BGetter
	@Override
	public int menuItemCount(){return menuItemCount;}
	@BField
	public static int menuHeight;
	@BGetter
	@Override
	public int menuHeight(){return menuHeight;}
	@BField
	public static int menuWidth;
	@BGetter
	@Override
	public int menuWidth(){return menuWidth;}
	@BField
	public static int menuY;
	@BGetter
	@Override
	public int menuY(){return menuY;}
	@BField
	public static int menuX;
	@BGetter
	@Override
	public int menuX(){return menuX;}
	@BField
	public static int[] menuPrimaryArgs;
	@BGetter
	@Override
	public int[] menuPrimaryArgs(){return menuPrimaryArgs;}
	@BField
	public static boolean spellSelected;
	@BGetter
	@Override
	public boolean spellSelected(){return spellSelected;}
	@BField
	public static int drawingAreaTop;
	@BGetter
	@Override
	public int drawingAreaTop(){return drawingAreaTop;}
	@BField
	public static int drawingAreaBottom;
	@BGetter
	@Override
	public int drawingAreaBottom(){return drawingAreaBottom;}
	@BField
	public static int[] drawingAreaPixels;
	@BGetter
	@Override
	public int[] drawingAreaPixels(){return drawingAreaPixels;}
	@BField
	public static int drawingAreaHeight;
	@BGetter
	@Override
	public int drawingAreaHeight(){return drawingAreaHeight;}
	@BField
	public static int drawingAreaWidth;
	@BGetter
	@Override
	public int drawingAreaWidth(){return drawingAreaWidth;}
	@BField
	public static int tradeChatMode;
	@BGetter
	@Override
	public int tradeChatMode(){return tradeChatMode;}
	@BField
	public static int publicChatMode;
	@BGetter
	@Override
	public int publicChatMode(){return publicChatMode;}
	@BField
	public static int audioEffectCount;
	@BGetter
	@Override
	public int audioEffectCount(){return audioEffectCount;}
	@BField
	public static boolean cameraLocked;
	@BGetter
	@Override
	public boolean cameraLocked(){return cameraLocked;}
	@BField
	public static int mapState;
	@BGetter
	@Override
	public int mapState(){return mapState;}
	@BField
	public static int viewportHeight;
	@BGetter
	@Override
	public int viewportHeight(){return viewportHeight;}
	@BField
	public static int viewportWidth;
	@BGetter
	@Override
	public int viewportWidth(){return viewportWidth;}
	@BField
	public static int bootState;
	@BGetter
	@Override
	public int bootState(){return bootState;}
	@BField
	public static java.util.HashMap fontHashMap;
	@BGetter
	@Override
	public java.util.HashMap fontHashMap(){return fontHashMap;}
	@BField
	public static int[] npcIndices;
	@BGetter
	@Override
	public int[] npcIndices(){return npcIndices;}
	@BField
	public static int[][][] regionHashes;
	@BGetter
	@Override
	public int[][][] regionHashes(){return regionHashes;}
	@BField
	public static boolean inInstancedScene;
	@BGetter
	@Override
	public boolean inInstancedScene(){return inInstancedScene;}
	@BField
	public static int currentWorld;
	@BGetter
	@Override
	public int currentWorld(){return currentWorld;}
	@BField
	public static boolean membersWorld;
	@BGetter
	@Override
	public boolean membersWorld(){return membersWorld;}
	@BField
	public static int currentWorldMask;
	@BGetter
	@Override
	public int currentWorldMask(){return currentWorldMask;}
	@BField
	public static int gameCycle;
	@BGetter
	@Override
	public int gameCycle(){return gameCycle;}
	@BField
	public static int hintArrowHeight;
	@BGetter
	@Override
	public int hintArrowHeight(){return hintArrowHeight;}
	@BField
	public static int hintArrowX;
	@BGetter
	@Override
	public int hintArrowX(){return hintArrowX;}
	@BField
	public static int hintArrowY;
	@BGetter
	@Override
	public int hintArrowY(){return hintArrowY;}
	@BField
	public static int hintArrowSubX;
	@BGetter
	@Override
	public int hintArrowSubX(){return hintArrowSubX;}
	@BField
	public static int hintArrowSubY;
	@BGetter
	@Override
	public int hintArrowSubY(){return hintArrowSubY;}
	@BField
	public static int hintArrowType;
	@BGetter
	@Override
	public int hintArrowType(){return hintArrowType;}
	@BField
	public static int hintArrowNpcIndex;
	@BGetter
	@Override
	public int hintArrowNpcIndex(){return hintArrowNpcIndex;}
	@BField
	public static int hintArrowPlayerIndex;
	@BGetter
	@Override
	public int hintArrowPlayerIndex(){return hintArrowPlayerIndex;}
	@BField
	public static short[] exchangeSearchResults;
	@BGetter
	@Override
	public short[] exchangeSearchResults(){return exchangeSearchResults;}
	@BField
	public static int canvasWidth;
	@BGetter
	@Override
	public int canvasWidth(){return canvasWidth;}
	@BField
	public static byte[][] xteaByteArray1;
	@BGetter
	@Override
	public byte[][] xteaByteArray1(){return xteaByteArray1;}
	@BField
	public static int lastMouseButton;
	@BGetter
	@Override
	public int lastMouseButton(){return lastMouseButton;}
	@BField
	public static int currentMouseButton;
	@BGetter
	@Override
	public int currentMouseButton(){return currentMouseButton;}
	@BField
	public static long lastMouseMoveTime;
	@BGetter
	@Override
	public long lastMouseMoveTime(){return lastMouseMoveTime;}
	@BField
	public static int pendingMouseClickY;
	@BGetter
	@Override
	public int pendingMouseClickY(){return pendingMouseClickY;}
	@BField
	public static int pendingMouseX;
	@BGetter
	@Override
	public int pendingMouseX(){return pendingMouseX;}
	@BField
	public static int pendingMouseClickX;
	@BGetter
	@Override
	public int pendingMouseClickX(){return pendingMouseClickX;}
	@BField
	public static int mouseIdleTicks;
	@BGetter
	@Override
	public int mouseIdleTicks(){return mouseIdleTicks;}
	@BField
	public static long lastMouseClickTime;
	@BGetter
	@Override
	public long lastMouseClickTime(){return lastMouseClickTime;}
	@BField
	public static int pendingMouseY;
	@BGetter
	@Override
	public int pendingMouseY(){return pendingMouseY;}
	@BField
	public static int mouseY;
	@BGetter
	@Override
	public int mouseY(){return mouseY;}
	@BField
	public static int mouseX;
	@BGetter
	@Override
	public int mouseX(){return mouseX;}
	@BField
	public static byte[][] xteaByteArray2;
	@BGetter
	@Override
	public byte[][] xteaByteArray2(){return xteaByteArray2;}
	@BField
	public static int lastSelectedItemIndex;
	@BGetter
	@Override
	public int lastSelectedItemIndex(){return lastSelectedItemIndex;}
	@BField
	public static int[] landscapeIndices;
	@BGetter
	@Override
	public int[] landscapeIndices(){return landscapeIndices;}
	@BField
	public static int[][][] tileHeights;
	@BGetter
	@Override
	public int[][][] tileHeights(){return tileHeights;}
	@BField
	public static byte[][][] sceneRenderRules;
	@BGetter
	@Override
	public byte[][][] sceneRenderRules(){return sceneRenderRules;}
	@BField
	public static int[] regionArchiveIDs;
	@BGetter
	@Override
	public int[] regionArchiveIDs(){return regionArchiveIDs;}
	@BField
	public static int spellTargetFlags;
	@BGetter
	@Override
	public int spellTargetFlags(){return spellTargetFlags;}
	@BField
	public static int canvasHeight;
	@BGetter
	@Override
	public int canvasHeight(){return canvasHeight;}
	@BField
	public static int currentPlane;
	@BGetter
	@Override
	public int currentPlane(){return currentPlane;}
	@BField
	public static java.util.Map chatboxChannelMap;
	@BGetter
	@Override
	public java.util.Map chatboxChannelMap(){return chatboxChannelMap;}
	/*@BField
	public static long[] onCursorUIDs;
	@BGetter
	@Override
	public long[] onCursorUIDs(){return onCursorUIDs;}*/
	@BField
	public static int onCursorUIDCount;
	@BGetter
	@Override
	public int onCursorUIDCount(){return onCursorUIDCount;}
	@BField
	public static int gameState;
	@BGetter
	@Override
	public int gameState(){return gameState;}
	@BField
	public static String currentDomain;
	@BGetter
	@Override
	public String currentDomain(){return currentDomain;}
	@BField
	public static String authenticatorPin;
	@BGetter
	@Override
	public String authenticatorPin(){return authenticatorPin;}
	@BField
	public static int mapBaseX;
	@BGetter
	@Override
	public int mapBaseX(){return mapBaseX;}
	@BField
	public static int buildVersion;
	@BGetter
	@Override
	public int buildVersion(){return buildVersion;}
	@BField
	public static int mapBaseY;
	@BGetter
	@Override
	public int mapBaseY(){return mapBaseY;}
	@BField
	public static int[] varps;
	@BGetter
	@Override
	public int[] varps(){return varps;}
	@BField
	public static int[] tempVarps;
	@BGetter
	@Override
	public int[] tempVarps(){return tempVarps;}
	@BField
	public static char[] asciiExtentions;
	@BGetter
	@Override
	public char[] asciiExtentions(){return asciiExtentions;}
	@BField
	public static int[] playerIndices;
	@BGetter
	@Override
	public int[] playerIndices(){return playerIndices;}
	@BField
	public static int playerCount;
	@BGetter
	@Override
	public int playerCount(){return playerCount;}
	@BField
	public static int[][] xteaKeys;
	@BGetter
	@Override
	public int[][] xteaKeys(){return xteaKeys;}
	@BField
	public static boolean loadMembersItemDefinitions;
	@BGetter
	@Override
	public boolean loadMembersItemDefinitions(){return loadMembersItemDefinitions;}
	@BField
	public static int cameraX;
	@BGetter
	@Override
	public int cameraX(){return cameraX;}
	@BField
	public static int cameraZ;
	@BGetter
	@Override
	public int cameraZ(){return cameraZ;}
	@BField
	public static int cameraY;
	@BGetter
	@Override
	public int cameraY(){return cameraY;}
	@BField
	public static int cameraPitch;
	@BGetter
	@Override
	public int cameraPitch(){return cameraPitch;}
	@BField
	public static int cameraYaw;
	@BGetter
	@Override
	public int cameraYaw(){return cameraYaw;}
	@BField
	public static int viewportScale;
	@BGetter
	@Override
	public int viewportScale(){return viewportScale;}
	@BField
	public static boolean awtFocusCheck;
	@BGetter
	@Override
	public boolean awtFocusCheck(){return awtFocusCheck;}
	@BField
	public static boolean awtFocusCheck2;
	@BGetter
	@Override
	public boolean awtFocusCheck2(){return awtFocusCheck2;}
	@BField
	public static int destinationX;
	@BGetter
	@Override
	public int destinationX(){return destinationX;}
	@BFunction
	@Override
	public void setDestinationX(int destX){
		destinationX = destX;
	}
	@BField
	public static int destinationY;
	@BGetter
	@Override
	public int destinationY(){return destinationY;}
	@BFunction
	@Override
	public void setDestinationY(int destY){
		destinationY = destY;
	}
	@BField
	public static org.osrs.api.wrappers.Archive regionArchive;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Archive regionArchive(){return regionArchive;}
}
