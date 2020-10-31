package org.osrs.api.methods;

import java.util.HashMap;
import java.util.Map;

import org.osrs.api.wrappers.*;
import org.osrs.injection.wrappers.KeyListener;
import org.osrs.injection.wrappers.MouseWheelListener;

public class Game extends MethodDefinition{
	public Game(MethodContext context){
		super(context);
	}
	public Client getClient(){
		return (Client)methods.botInstance;
	}
	
	public boolean getDisableRenderState(){
		Client client = getClient();
		if(client!=null){
			return client.getDisableRenderingState();
		}
		return false;
	}
	public void setDisableRenderState(boolean state){
		boolean curr = getDisableRenderState();
		if(curr!=state){
			Client client = getClient();
			if(client!=null){
				client.setDisableRenderingState();
			}
		}
	}
	public void addGameMessage(String message){
		addGameMessage(108, message);
	}
	public void addGameMessage(int type, String message){
		addGameMessage(type, message, null, "");
	}
	public void addGameMessage(int type, String message, String sender, String name){
		Client client = getClient();
		if(client!=null){
			client.invokeAddChatMessage(type, name, message, sender, (int) methods.getPredicate("", "addChatMessage", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;?)V", true));
		}
	}
	public Sprite getItemSprite(int a, int b, int c, int d, int e, boolean f){
		Client client = getClient();
		if(client!=null){
			return client.invokeGetItemSprite(a, b, c, d, e, f, (int) methods.getPredicate("", "getItemSprite", "(IIIIIZ?)L*;", true));
		}
		return null;
	}
	public int getVarp(int a){
		Client client = getClient();
		if(client!=null){
			return client.invokeGetVarp(a, (int) methods.getPredicate("", "getVarp", "(I?)I", true));
		}
		return -1;
	}
	public void setWorld(Server server){
		Client client = getClient();
		if(client!=null){
			client.invokeSetWorld(server, (byte) methods.getPredicate("", "setWorld", "(L*;?)V", true));
		}
	}
	public void setGameState(int state){
		Client client = getClient();
		if(client!=null){
			client.invokeSetGameState(state, (int) methods.getPredicate("", "setGameState", "(I?)V", true));
		}
	}
	public ObjectDefinition getObjectDefinition(int hashID){
		Client client = getClient();
		if(client!=null){
			return client.invokeGetObjectDefinition(hashID, (int) methods.getPredicate("", "getObjectDefinition", "(I?)L*;", true));
		}
		return null;
	}
	public AnimationSequence getAnimationSequence(int id){
		Client client = getClient();
		if(client!=null){
			return client.invokeGetAnimationSequence(id, (int) methods.getPredicate("", "getAnimationSequence", "(I?)L*;", true));
		}
		return null;
	}
	public ItemDefinition getItemDefinition(int hashID){
		Client client = getClient();
		if(client!=null){
			return client.invokeGetItemDefinition(hashID, (int) methods.getPredicate("", "getItemDefinition", "(I?)L*;", true));
		}
		return null;
	}
	public NPCDefinition getNPCDefinition(int hashID){
		Client client = getClient();
		if(client!=null){
			return client.invokeGetNPCDefinition(hashID, (int) methods.getPredicate("", "getNPCDefinition", "(I?)L*;", true));
		}
		return null;
	}
	public Hitsplat getHitsplatDefinition(int hashID){
		Client client = getClient();
		if(client!=null){
			return client.invokeGetHitsplatDefinition(hashID, (int) methods.getPredicate("", "getHitsplatDefinition", "(I?)L*;", true));
		}
		return null;
	}
	public void processAction(int a, int b, int c, int d, String e, String f, int g, int h){
		Client client = getClient();
		if(client!=null){
			client.invokeProcessAction(a, b, c, d, e, f, g, h, (int) methods.getPredicate("", "processAction", "(IIIILjava/lang/String;Ljava/lang/String;II?)V", true));
		}
	}
	public void overrideProcessAction(int a, int b, int c, int d, String e, String f, int g, int h){
		Client client = getClient();
		if(client!=null){
			client.overrideProcessAction(a, b, c, d, e, f, g, h);
		}
	}
	public SpotAnim getSpotAnim(int hashID){
		Client client = getClient();
		if(client!=null){
			return client.invokeGetSpotAnim(hashID, (int) methods.getPredicate("", "getSpotAnim", "(I?)L*;", true));
		}
		return null;
	}
	public void worldToScreen(int a, int b, int c){
		Client client = getClient();
		if(client!=null){
			client.invokeWorldToScreen(a, b, c, (int) methods.getPredicate("", "worldToScreen", "(III?)V", true));
		}
	}
	public int getTileHeight(int a, int b, int c){
		Client client = getClient();
		if(client!=null){
			return client.invokeGetTileHeight(a, b, c, (int) methods.getPredicate("", "getTileHeight", "(III?)I", true));
		}
		return -1;
	}
	public void fireScriptEvent(ScriptEvent event){
		Client client = getClient();
		if(client!=null){
			client.invokeFireScriptEvent(event, (int) methods.getPredicate("", "fireScriptEvent", "(L*;?)V", true));
		}
	}
	public void runScript(Object[] args){
		Client client = getClient();
		if(client!=null){
			client.runScript(args, (int) methods.getPredicate("", "fireScriptEvent", "(L*;?)V", true));
		}
	}
	public OutgoingPacket createOutgoingPacket(OutgoingPacketMeta a, ISAACCipher b){
		Client client = getClient();
		if(client!=null){
			return client.invokeCreateOutgoingPacket(a, b, (int) methods.getPredicate("", "createOutgoingPacket", "(L*;L*;?)L*;", true));
		}
		return null;
	}
	public DefinitionProperty getDefinitionProperty(int a){
		Client client = getClient();
		if(client!=null){
			return client.invokeGetDefinitionProperty(a, (int) methods.getPredicate("", "getDefinitionProperty", "(I?)L*;", true));
		}
		return null;
	}
	public boolean loadWorlds(){
		Client client = getClient();
		if(client!=null){
			return client.invokeLoadWorlds((int) methods.getPredicate("", "loadWorlds", "(?)Z", true));
		}
		return false;
	}
	public void setLoginMessages(String message1, String message2, String message3){
		Client client = getClient();
		if(client!=null){
			client.setLoginMessages(message1, message2, message3);
		}
	}
	public void setLoginState(int state){
		Client client = getClient();
		if(client!=null){
			client.setLoginState(state*((int)methods.getSetterMultiplier("", "loginState", true)));
		}
	}
	public void setMenuOpen(boolean state){
		Client client = getClient();
		if(client!=null){
			client.setMenu(state);
		}
	}

	public boolean isLoginWorldSelectorOpen(){
		Client client = getClient();
		if(client!=null){
			return client.loginWorldSelectorOpen();
		}
		return false;
	}
	public KeyListener getKeyListener(){
		Client client = getClient();
		if(client!=null){
			return (KeyListener) client.keyboardListener();
		}
		return null;
	}
	public MouseListener getMouseListener(){
		Client client = getClient();
		if(client!=null){
			return client.mouseListener();
		}
		return null;
	}
	public MouseWheelListener getMouseWheelListener(){
		Client client = getClient();
		if(client!=null){
			return (MouseWheelListener) client.mouseWheelListener();
		}
		return null;
	}
	
	public int[] getAbsoluteSkillLevels(){
		Client client = getClient();
		if(client!=null){
			return client.absoluteSkillLevels();
		}
		return new int[]{};
	}
	public Deque getAnimableObjectDeque(){
		Client client = getClient();
		if(client!=null){
			return client.animableObjectDeque();
		}
		return null;
	}
	public Cache getAnimationSequenceCache(){
		Client client = getClient();
		if(client!=null){
			return client.animationSequenceCache();
		}
		return null;
	}
	public AbstractArchive getAnimationSequenceFileSystem(){
		Client client = getClient();
		if(client!=null){
			return client.animationSequenceFileSystem();
		}
		return null;
	}
	public AbstractArchive getAnimationSkeletonFileSystem(){
		Client client = getClient();
		if(client!=null){
			return client.animationSkeletonFileSystem();
		}
		return null;
	}
	public Cache getAnimationSkeletonsCache(){
		Client client = getClient();
		if(client!=null){
			return client.animationSkeletonsCache();
		}
		return null;
	}
	public AbstractArchive getAnimationSkinFileSystem(){
		Client client = getClient();
		if(client!=null){
			return client.animationSkinFileSystem();
		}
		return null;
	}
	public Deque getAreaSoundEmitterDeque(){
		Client client = getClient();
		if(client!=null){
			return client.areaSoundEmitterDeque();
		}
		return null;
	}
	public char[] getAsciiExtentions(){
		Client client = getClient();
		if(client!=null){
			return client.asciiExtentions();
		}
		return new char[]{};
	}
	public int getAudioEffectCount(){
		Client client = getClient();
		if(client!=null){
			return client.audioEffectCount();
		}
		return -1;
	}
	public AudioEffect[] getAudioEffects(){
		Client client = getClient();
		if(client!=null){
			return client.audioEffects();
		}
		return new AudioEffect[]{};
	}
	public AudioRunnable getAudioRunnable(){
		Client client = getClient();
		if(client!=null){
			return client.audioRunnable();
		}
		return null;
	}
	public String getAuthenticatorPin(){
		Client client = getClient();
		if(client!=null){
			return client.authenticatorPin();
		}
		return "";
	}
	public int getBootState(){
		Client client = getClient();
		if(client!=null){
			return client.bootState();
		}
		return -1;
	}
	public int getBuildVersion(){
		Client client = getClient();
		if(client!=null){
			return client.buildVersion();
		}
		return -1;
	}
	public ByteBuffer getCacheBuffer(){
		Client client = getClient();
		if(client!=null){
			return client.cacheBuffer();
		}
		return null;
	}
	public Widget[] getCachedWidgets(){
		Client client = getClient();
		if(client!=null){
			return client.cachedWidgets();
		}
		return new Widget[]{};
	}
	public boolean getCameraLocked(){
		Client client = getClient();
		if(client!=null){
			return client.cameraLocked();
		}
		return false;
	}
	public int getCameraPitch(){
		Client client = getClient();
		if(client!=null){
			return client.cameraPitch();
		}
		return -1;
	}
	public int getCameraX(){
		Client client = getClient();
		if(client!=null){
			return client.cameraX();
		}
		return -1;
	}
	public int getCameraY(){
		Client client = getClient();
		if(client!=null){
			return client.cameraY();
		}
		return -1;
	}
	public int getCameraYaw(){
		Client client = getClient();
		if(client!=null){
			return client.cameraYaw();
		}
		return -1;
	}
	public int getCameraZ(){
		Client client = getClient();
		if(client!=null){
			return client.cameraZ();
		}
		return -1;
	}
	public int getCanvasHeight(){
		Client client = getClient();
		if(client!=null){
			return client.canvasHeight();
		}
		return -1;
	}
	public int getCanvasWidth(){
		Client client = getClient();
		if(client!=null){
			return client.canvasWidth();
		}
		return -1;
	}
	public int getCenterLoginScreenX(){
		Client client = getClient();
		if(client!=null){
			return client.centerLoginScreenX();
		}
		return -1;
	}
	public Map getChatboxChannelMap(){
		Client client = getClient();
		if(client!=null){
			return client.chatboxChannelMap();
		}
		return null;
	}
	public DoublyNode getChatlineMessageNodeList(){
		Client client = getClient();
		if(client!=null){
			return client.chatlineMessageNodeList();
		}
		return null;
	}
	public FixedSizeDeque getChatlineMessages(){
		Client client = getClient();
		if(client!=null){
			return client.chatlineMessages();
		}
		return null;
	}
	public ClanContainer getClanContainer(){
		Client client = getClient();
		if(client!=null){
			return client.clanContainer();
		}
		return null;
	}
	public NodeList getClassInfoList(){
		Client client = getClient();
		if(client!=null){
			return client.classInfoList();
		}
		return null;
	}
	public boolean getClientFocused(){
		Client client = getClient();
		if(client!=null){
			return client.clientFocused();
		}
		return false;
	}
	public Client getClientInstance(){
		Client client = getClient();
		if(client!=null){
			return client.clientInstance();
		}
		return null;
	}
	public CollisionMap[] getCollisionMaps(){
		Client client = getClient();
		if(client!=null){
			return client.collisionMaps();
		}
		return new CollisionMap[]{};
	}
	public Cache getCombatBarSpriteCache(){
		Client client = getClient();
		if(client!=null){
			return client.combatBarSpriteCache();
		}
		return null;
	}
	public AbstractArchive getCombatBarSpriteFileSystem(){
		Client client = getClient();
		if(client!=null){
			return client.combatBarSpriteFileSystem();
		}
		return null;
	}
	public HashTable getComponentTable(){
		Client client = getClient();
		if(client!=null){
			return client.componentTable();
		}
		return null;
	}
	public String getCurrentDomain(){
		Client client = getClient();
		if(client!=null){
			return client.currentDomain();
		}
		return "";
	}
	public int getCurrentMouseButton(){
		Client client = getClient();
		if(client!=null){
			return client.currentMouseButton();
		}
		return -1;
	}
	public int getCurrentPlane(){
		Client client = getClient();
		if(client!=null){
			return client.currentPlane();
		}
		return -1;
	}
	public int[] getCurrentSkillLevels(){
		Client client = getClient();
		if(client!=null){
			return client.currentSkillLevels();
		}
		return new int[]{};
	}
	public int getCurrentWorld(){
		Client client = getClient();
		if(client!=null){
			return client.currentWorld();
		}
		return -1;
	}
	public int getCurrentWorldMask(){
		Client client = getClient();
		if(client!=null){
			return client.currentWorldMask();
		}
		return -1;
	}
	public AccessFileHandler getDat2FileHandler(){
		Client client = getClient();
		if(client!=null){
			return client.dat2FileHandler();
		}
		return null;
	}
	public int getDestinationX(){
		Client client = getClient();
		if(client!=null){
			return client.destinationX();
		}
		return -1;
	}
	public int getDestinationY(){
		Client client = getClient();
		if(client!=null){
			return client.destinationY();
		}
		return -1;
	}
	public int getDrawingAreaBottom(){
		Client client = getClient();
		if(client!=null){
			return client.drawingAreaBottom();
		}
		return -1;
	}
	public int getDrawingAreaHeight(){
		Client client = getClient();
		if(client!=null){
			return client.drawingAreaHeight();
		}
		return -1;
	}
	public int[] getDrawingAreaPixels(){
		Client client = getClient();
		if(client!=null){
			return client.drawingAreaPixels();
		}
		return new int[]{};
	}
	public int getDrawingAreaTop(){
		Client client = getClient();
		if(client!=null){
			return client.drawingAreaTop();
		}
		return -1;
	}
	public int getDrawingAreaWidth(){
		Client client = getClient();
		if(client!=null){
			return client.drawingAreaWidth();
		}
		return -1;
	}
	public Deque getDrawnTileDeque(){
		Client client = getClient();
		if(client!=null){
			return client.drawnTileDeque();
		}
		return null;
	}
	public GrandExchangeOffer[] getExchangeOffers(){
		Client client = getClient();
		if(client!=null){
			return client.exchangeOffers();
		}
		return new GrandExchangeOffer[]{};
	}
	public short[] getExchangeSearchResults(){
		Client client = getClient();
		if(client!=null){
			return client.exchangeSearchResults();
		}
		return new short[]{};
	}
	public Deque getFileSystemRequestDeque(){
		Client client = getClient();
		if(client!=null){
			return client.fileSystemRequestDeque();
		}
		return null;
	}
	public Deque getFileSystemResponseDeque(){
		Client client = getClient();
		if(client!=null){
			return client.fileSystemResponseDeque();
		}
		return null;
	}
	public RSShadowedFont getFontBold12pt(){
		Client client = getClient();
		if(client!=null){
			return client.fontBold12pt();
		}
		return null;
	}
	public HashMap getFontHashMap(){
		Client client = getClient();
		if(client!=null){
			return client.fontHashMap();
		}
		return null;
	}
	public RSShadowedFont getFontPlain11pt(){
		Client client = getClient();
		if(client!=null){
			return client.fontPlain11pt();
		}
		return null;
	}
	public RSShadowedFont getFontPlain12pt(){
		Client client = getClient();
		if(client!=null){
			return client.fontPlain12pt();
		}
		return null;
	}
	public FriendManager getFriendManager(){
		Client client = getClient();
		if(client!=null){
			return client.friendManager();
		}
		return null;
	}
	public PacketContext getGameConnection(){
		Client client = getClient();
		if(client!=null){
			return client.gameConnection();
		}
		return null;
	}
	public AbstractSocket getGameConnectionSocket(){
		Client client = getClient();
		if(client!=null){
			return client.gameConnectionSocket();
		}
		return null;
	}
	public Task getGameConnectionTask(){
		Client client = getClient();
		if(client!=null){
			return client.gameConnectionTask();
		}
		return null;
	}
	public int getGameCycle(){
		Client client = getClient();
		if(client!=null){
			return client.gameCycle();
		}
		return -1;
	}
	public int getGameState(){
		Client client = getClient();
		if(client!=null){
			return client.gameState();
		}
		return -1;
	}
	public AbstractArchive getGrandExchangeItemFileSystem(){
		Client client = getClient();
		if(client!=null){
			return client.grandExchangeItemFileSystem();
		}
		return null;
	}
	public int getHintArrowNpcIndex(){
		Client client = getClient();
		if(client!=null){
			return client.hintArrowNpcIndex();
		}
		return -1;
	}
	public int getHintArrowPlayerIndex(){
		Client client = getClient();
		if(client!=null){
			return client.hintArrowPlayerIndex();
		}
		return -1;
	}
	public int getHintArrowType(){
		Client client = getClient();
		if(client!=null){
			return client.hintArrowType();
		}
		return -1;
	}
	public int getHintArrowHeight(){
		Client client = getClient();
		if(client!=null){
			return client.hintArrowHeight();
		}
		return -1;
	}
	public int getHintArrowX(){
		Client client = getClient();
		if(client!=null){
			return client.hintArrowX();
		}
		return -1;
	}
	public int getHintArrowY(){
		Client client = getClient();
		if(client!=null){
			return client.hintArrowY();
		}
		return -1;
	}
	public int getHintArrowSubX(){
		Client client = getClient();
		if(client!=null){
			return client.hintArrowSubX();
		}
		return -1;
	}
	public int getHintArrowSubY(){
		Client client = getClient();
		if(client!=null){
			return client.hintArrowSubY();
		}
		return -1;
	}
	public AbstractArchive getIdentityKitNodeFileSystem(){
		Client client = getClient();
		if(client!=null){
			return client.identityKitNodeFileSystem();
		}
		return null;
	}
	public AccessFileHandler getIdx255FileHandler(){
		Client client = getClient();
		if(client!=null){
			return client.idx255FileHandler();
		}
		return null;
	}
	public boolean getInInstancedScene(){
		Client client = getClient();
		if(client!=null){
			return client.inInstancedScene();
		}
		return false;
	}
	public AccessFileHandler[] getIndexFileHandlers(){
		Client client = getClient();
		if(client!=null){
			return client.indexFileHandlers();
		}
		return new AccessFileHandler[]{};
	}
	public Inflater getInflater(){
		Client client = getClient();
		if(client!=null){
			return client.inflater();
		}
		return null;
	}
	public Cache getInventoryDefinitionCache(){
		Client client = getClient();
		if(client!=null){
			return client.inventoryDefinitionCache();
		}
		return null;
	}
	public AbstractArchive getInventoryDefinitionFileSystem(){
		Client client = getClient();
		if(client!=null){
			return client.inventoryDefinitionFileSystem();
		}
		return null;
	}
	public RSShadowedFont getItemAmountFont(){
		Client client = getClient();
		if(client!=null){
			return client.itemAmountFont();
		}
		return null;
	}
	public HashTable getItemContainers(){
		Client client = getClient();
		if(client!=null){
			return client.itemContainers();
		}
		return null;
	}
	public Cache getItemDefinitionCache(){
		Client client = getClient();
		if(client!=null){
			return client.itemDefinitionCache();
		}
		return null;
	}
	public AbstractArchive getItemDefinitionFileSystem(){
		Client client = getClient();
		if(client!=null){
			return client.itemDefinitionFileSystem();
		}
		return null;
	}
	public Cache getItemModelCache(){
		Client client = getClient();
		if(client!=null){
			return client.itemModelCache();
		}
		return null;
	}
	public Deque[][][] getItemPileDeque(){
		Client client = getClient();
		if(client!=null){
			return client.itemPileDeque();
		}
		return new Deque[][][]{};
	}
	public int getItemSelectionState(){
		Client client = getClient();
		if(client!=null){
			return client.itemSelectionState();
		}
		return -1;
	}
	public Cache getItemSpriteCache(){
		Client client = getClient();
		if(client!=null){
			return client.itemSpriteCache();
		}
		return null;
	}
	public KeyboardListener getKeyboardListener(){
		Client client = getClient();
		if(client!=null){
			return client.keyboardListener();
		}
		return null;
	}
	public int[] getLandscapeIndices(){
		Client client = getClient();
		if(client!=null){
			return client.landscapeIndices();
		}
		return new int[]{};
	}
	public AbstractSocket getLastConnectionSocket(){
		Client client = getClient();
		if(client!=null){
			return client.lastConnectionSocket();
		}
		return null;
	}
	public int getLastMouseButton(){
		Client client = getClient();
		if(client!=null){
			return client.lastMouseButton();
		}
		return -1;
	}
	public long getLastMouseClickTime(){
		Client client = getClient();
		if(client!=null){
			return client.lastMouseClickTime();
		}
		return -1;
	}
	public long getLastMouseMoveTime(){
		Client client = getClient();
		if(client!=null){
			return client.lastMouseMoveTime();
		}
		return -1;
	}
	public int getLastSelectedItemIndex(){
		Client client = getClient();
		if(client!=null){
			return client.lastSelectedItemIndex();
		}
		return -1;
	}
	public boolean getLoadMembersItemDefinitions(){
		Client client = getClient();
		if(client!=null){
			return client.loadMembersItemDefinitions();
		}
		return false;
	}
	public Occluder[][] getLoadedOccluders(){
		Client client = getClient();
		if(client!=null){
			return client.loadedOccluders();
		}
		return new Occluder[][]{};
	}
	public Player getLocalPlayer(){
		Client client = getClient();
		if(client!=null){
			return client.localPlayer();
		}
		return null;
	}
	public Task getLoginConnectionTask(){
		Client client = getClient();
		if(client!=null){
			return client.loginConnectionTask();
		}
		return null;
	}
	public String getLoginMessage1(){
		Client client = getClient();
		if(client!=null){
			return client.loginMessage1();
		}
		return "";
	}
	public String getLoginMessage2(){
		Client client = getClient();
		if(client!=null){
			return client.loginMessage2();
		}
		return "";
	}
	public String getLoginMessage3(){
		Client client = getClient();
		if(client!=null){
			return client.loginMessage3();
		}
		return "";
	}
	public int getLoginState(){
		Client client = getClient();
		if(client!=null){
			return client.loginState();
		}
		return -1;
	}
	public int getMapBaseX(){
		Client client = getClient();
		if(client!=null){
			return client.mapBaseX();
		}
		return -1;
	}
	public int getMapBaseY(){
		Client client = getClient();
		if(client!=null){
			return client.mapBaseY();
		}
		return -1;
	}
	public int[] getMapRegionIDs(){
		Client client = getClient();
		if(client!=null){
			return client.mapRegionIDs();
		}
		return new int[]{};
	}
	public int getMapRotation(){
		Client client = getClient();
		if(client!=null){
			return client.mapRotation();
		}
		return -1;
	}
	public int getMapState(){
		Client client = getClient();
		if(client!=null){
			return client.mapState();
		}
		return -1;
	}
	public Archive getRegionArchive(){
		Client client = getClient();
		if(client!=null){
			return client.regionArchive();
		}
		return null;
	}
	public int[] getRegionArchiveIDs(){
		Client client = getClient();
		if(client!=null){
			return client.regionArchiveIDs();
		}
		return new int[]{};
	}
	public boolean getMembersWorld(){
		Client client = getClient();
		if(client!=null){
			return client.membersWorld();
		}
		return false;
	}
	public String[] getMenuActions(){
		Client client = getClient();
		if(client!=null){
			return client.menuActions();
		}
		return new String[]{};
	}
	public int getMenuHeight(){
		Client client = getClient();
		if(client!=null){
			return client.menuHeight();
		}
		return -1;
	}
	public int getMenuItemCount(){
		Client client = getClient();
		if(client!=null){
			return client.menuItemCount();
		}
		return -1;
	}
	public int[] getMenuOpcodes(){
		Client client = getClient();
		if(client!=null){
			return client.menuOpcodes();
		}
		return new int[]{};
	}
	public boolean getMenuOpen(){
		Client client = getClient();
		if(client!=null){
			return client.menuOpen();
		}
		return false;
	}
	public int[] getMenuPrimaryArgs(){
		Client client = getClient();
		if(client!=null){
			return client.menuPrimaryArgs();
		}
		return new int[]{};
	}
	public int[] getMenuSecondaryArgs(){
		Client client = getClient();
		if(client!=null){
			return client.menuSecondaryArgs();
		}
		return new int[]{};
	}
	public boolean[] getMenuShiftClickActions(){
		Client client = getClient();
		if(client!=null){
			return client.menuShiftClickActions();
		}
		return new boolean[]{};
	}
	public String[] getMenuTargetS(){
		Client client = getClient();
		if(client!=null){
			return client.menuTargets();
		}
		return new String[]{};
	}
	public int[] getMenuTertiaryArgs(){
		Client client = getClient();
		if(client!=null){
			return client.menuTertiaryArgs();
		}
		return new int[]{};
	}
	public int getMenuWidth(){
		Client client = getClient();
		if(client!=null){
			return client.menuWidth();
		}
		return -1;
	}
	public int getMenuX(){
		Client client = getClient();
		if(client!=null){
			return client.menuX();
		}
		return -1;
	}
	public int getMenuY(){
		Client client = getClient();
		if(client!=null){
			return client.menuY();
		}
		return -1;
	}
	public Deque getMouseClickScriptEventDeque(){
		Client client = getClient();
		if(client!=null){
			return client.mouseClickScriptEventDeque();
		}
		return null;
	}
	public int getMouseCrosshairState(){
		Client client = getClient();
		if(client!=null){
			return client.mouseCrosshairState();
		}
		return -1;
	}
	public Widget getMouseCrosshairWidget(){
		Client client = getClient();
		if(client!=null){
			return client.mouseCrosshairWidget();
		}
		return null;
	}
	public int getMouseIdleTicks(){
		Client client = getClient();
		if(client!=null){
			return client.mouseIdleTicks();
		}
		return -1;
	}
	public Deque getMouseReleaseScriptEventDeque(){
		Client client = getClient();
		if(client!=null){
			return client.mouseReleaseScriptEventDeque();
		}
		return null;
	}
	public MouseTracker getMouseTracker(){
		Client client = getClient();
		if(client!=null){
			return client.mouseTracker();
		}
		return null;
	}
	public int getMouseX(){
		Client client = getClient();
		if(client!=null){
			return client.mouseX();
		}
		return -1;
	}
	public int getMouseY(){
		Client client = getClient();
		if(client!=null){
			return client.mouseY();
		}
		return -1;
	}
	public HashTable getNetPendingPriorityResponses(){
		Client client = getClient();
		if(client!=null){
			return client.netPendingPriorityResponses();
		}
		return null;
	}
	public HashTable getNetPendingPriorityWrites(){
		Client client = getClient();
		if(client!=null){
			return client.netPendingPriorityWrites();
		}
		return null;
	}
	public HashTable getNetPendingResponses(){
		Client client = getClient();
		if(client!=null){
			return client.netPendingResponses();
		}
		return null;
	}
	public HashTable getNetPendingWrites(){
		Client client = getClient();
		if(client!=null){
			return client.netPendingWrites();
		}
		return null;
	}
	public Queue getNetPendingWritesQueue(){
		Client client = getClient();
		if(client!=null){
			return client.netPendingWritesQueue();
		}
		return null;
	}
	public AttackOptionSetting getNpcAttackOptionSetting(){
		Client client = getClient();
		if(client!=null){
			return client.npcAttackOptionSetting();
		}
		return null;
	}
	public Cache getNpcDefinitionCache(){
		Client client = getClient();
		if(client!=null){
			return client.npcDefinitionCache();
		}
		return null;
	}
	public AbstractArchive getNpcDefinitionFileSystem(){
		Client client = getClient();
		if(client!=null){
			return client.npcDefinitionFileSystem();
		}
		return null;
	}
	public int[] getNpcIndices(){
		Client client = getClient();
		if(client!=null){
			return client.npcIndices();
		}
		return new int[]{};
	}
	public Cache getNpcModelCache(){
		Client client = getClient();
		if(client!=null){
			return client.npcModelCache();
		}
		return null;
	}
	public AbstractArchive getNpcModelFileSystem(){
		Client client = getClient();
		if(client!=null){
			return client.npcModelFileSystem();
		}
		return null;
	}
	public Npc[] getNpcs(){
		Client client = getClient();
		if(client!=null){
			return client.npcs();
		}
		return new Npc[]{};
	}
	public Cache getObjectAnimatedModelCache(){
		Client client = getClient();
		if(client!=null){
			return client.objectAnimatedModelCache();
		}
		return null;
	}
	public Cache getObjectBaseModelCache(){
		Client client = getClient();
		if(client!=null){
			return client.objectBaseModelCache();
		}
		return null;
	}
	public Cache getObjectDefinitionCache(){
		Client client = getClient();
		if(client!=null){
			return client.objectDefinitionCache();
		}
		return null;
	}
	public AbstractArchive getObjectDefinitionFileSystem(){
		Client client = getClient();
		if(client!=null){
			return client.objectDefinitionFileSystem();
		}
		return null;
	}
	public AbstractArchive getObjectDefinitionModelFileSystem(){
		Client client = getClient();
		if(client!=null){
			return client.objectDefinitionModelFileSystem();
		}
		return null;
	}
	public Cache getObjectModelHeaderCache(){
		Client client = getClient();
		if(client!=null){
			return client.objectModelHeaderCache();
		}
		return null;
	}
	/*public long[] getOnCursorUIDs(){
		Client client = getClient();
		if(client!=null){
			long[] old = client.onCursorUIDs();
			long[] uids = new long[]{old.length};
			for(int i=0;i<uids.length;++i){
				if(i<old.length){
					uids[i]=old[i];
				}
			}
			return uids;
		}
		return new long[]{};
	}*/
	public int getOnCursorUIDCount(){
		Client client = getClient();
		if(client!=null){
			return client.onCursorUIDCount();
		}
		return -1;
	}
	public String getPassword(){
		Client client = getClient();
		if(client!=null){
			return client.password();
		}
		return "";
	}
	public int getPendingMouseClickX(){
		Client client = getClient();
		if(client!=null){
			return client.pendingMouseClickX();
		}
		return -1;
	}
	public int getPendingMouseClickY(){
		Client client = getClient();
		if(client!=null){
			return client.pendingMouseClickY();
		}
		return -1;
	}
	public int getPendingMouseX(){
		Client client = getClient();
		if(client!=null){
			return client.pendingMouseX();
		}
		return -1;
	}
	public int getPendingMouseY(){
		Client client = getClient();
		if(client!=null){
			return client.pendingMouseY();
		}
		return -1;
	}
	public Deque getPendingSpawnDeque(){
		Client client = getClient();
		if(client!=null){
			return client.pendingSpawnDeque();
		}
		return null;
	}
	public boolean[] getPlayerActionPriorities(){
		Client client = getClient();
		if(client!=null){
			return client.playerActionPriorities();
		}
		return new boolean[]{};
	}
	public String[] getPlayerActions(){
		Client client = getClient();
		if(client!=null){
			return client.playerActions();
		}
		return new String[]{};
	}
	public AttackOptionSetting getPlayerAttackOptionSetting(){
		Client client = getClient();
		if(client!=null){
			return client.playerAttackOptionSetting();
		}
		return null;
	}
	public int getPlayerCount(){
		Client client = getClient();
		if(client!=null){
			return client.playerCount();
		}
		return -1;
	}
	public int[] getPlayerIndices(){
		Client client = getClient();
		if(client!=null){
			return client.playerIndices();
		}
		return new int[]{};
	}
	public Cache getPlayerModelCache(){
		Client client = getClient();
		if(client!=null){
			return client.playerModelCache();
		}
		return null;
	}
	public int getPlayerWeight(){
		Client client = getClient();
		if(client!=null){
			return client.playerWeight();
		}
		return -1;
	}
	public Player[] getPlayers(){
		Client client = getClient();
		if(client!=null){
			return client.players();
		}
		return new Player[]{};
	}
	public Widget getPleaseWaitWidget(){
		Client client = getClient();
		if(client!=null){
			return client.pleaseWaitWidget();
		}
		return null;
	}
	public ClientPreferences getPreferences(){
		Client client = getClient();
		if(client!=null){
			return client.preferences();
		}
		return null;
	}
	public Producer getProducer(){
		Client client = getClient();
		if(client!=null){
			return client.producer();
		}
		return null;
	}
	public Deque getProjectileDeque(){
		Client client = getClient();
		if(client!=null){
			return client.projectileDeque();
		}
		return null;
	}
	public int getPublicChatMode(){
		Client client = getClient();
		if(client!=null){
			return client.publicChatMode();
		}
		return -1;
	}
	public AccessFileHandler getRandomDatFileHandler(){
		Client client = getClient();
		if(client!=null){
			return client.randomDatFileHandler();
		}
		return null;
	}
	public int getRedrawMode(){
		Client client = getClient();
		if(client!=null){
			return client.redrawMode();
		}
		return -1;
	}
	public org.osrs.api.wrappers.Region getRegion(){
		Client client = getClient();
		if(client!=null){
			return client.region();
		}
		return null;
	}
	public int[][][] getRegionHashes(){
		Client client = getClient();
		if(client!=null){
			return client.regionHashes();
		}
		return new int[][][]{};
	}
	public Occluder[] getRegionOccluders(){
		Client client = getClient();
		if(client!=null){
			return client.regionOccluders();
		}
		return new Occluder[]{};
	}
	public Resampler getResampler(){
		Client client = getClient();
		if(client!=null){
			return client.resampler();
		}
		return null;
	}
	public boolean getResizeMode(){
		Client client = getClient();
		if(client!=null){
			return client.resizeMode();
		}
		return false;
	}
	public ByteBuffer getResponseArchiveBuffer(){
		Client client = getClient();
		if(client!=null){
			return client.responseArchiveBuffer();
		}
		return null;
	}
	public ByteBuffer getResponseHeaderBuffer(){
		Client client = getClient();
		if(client!=null){
			return client.responseHeaderBuffer();
		}
		return null;
	}
	public int getRootWidgetIndex(){
		Client client = getClient();
		if(client!=null){
			return client.rootWidgetIndex();
		}
		return -1;
	}
	public int getRunEnergy(){
		Client client = getClient();
		if(client!=null){
			return client.runEnergy();
		}
		return -1;
	}
	public RuneScriptVM getRunescriptVM(){
		Client client = getClient();
		if(client!=null){
			return client.runescriptVM();
		}
		return null;
	}
	public byte[][][] getSceneRenderRules(){
		Client client = getClient();
		if(client!=null){
			return client.sceneRenderRules();
		}
		return new byte[][][]{};
	}
	public int getSelectedRegionTileX(){
		Client client = getClient();
		if(client!=null){
			return client.selectedRegionTileX();
		}
		return -1;
	}
	public int getSelectedRegionTileY(){
		Client client = getClient();
		if(client!=null){
			return client.selectedRegionTileY();
		}
		return -1;
	}
	public void setSelectedRegionTile(int localX, int localY){
		Client client = getClient();
		if(client!=null){
			client.setSelectedRegionTileX(localX);
			client.setSelectedRegionTileY(localY);
		}
	}
	public Server[] getServers(){
		Client client = getClient();
		if(client!=null){
			return client.servers();
		}
		return new Server[]{};
	}
	public int[] getSkillExperiences(){
		Client client = getClient();
		if(client!=null){
			return client.skillExperiences();
		}
		return new int[]{};
	}
	public boolean getSpellSelected(){
		Client client = getClient();
		if(client!=null){
			return client.spellSelected();
		}
		return false;
	}
	public int getSpellTargetFlags(){
		Client client = getClient();
		if(client!=null){
			return client.spellTargetFlags();
		}
		return -1;
	}
	public Cache getSpotAnimCache(){
		Client client = getClient();
		if(client!=null){
			return client.spotAnimCache();
		}
		return null;
	}
	public AbstractArchive getSpotAnimFileSystem(){
		Client client = getClient();
		if(client!=null){
			return client.spotAnimFileSystem();
		}
		return null;
	}
	public Cache getSpotAnimModelCache(){
		Client client = getClient();
		if(client!=null){
			return client.spotAnimModelCache();
		}
		return null;
	}
	public AbstractArchive getSpotAnimModelFileSystem(){
		Client client = getClient();
		if(client!=null){
			return client.spotAnimModelFileSystem();
		}
		return null;
	}
	public Signlink getTaskManager(){
		Client client = getClient();
		if(client!=null){
			return client.taskManager();
		}
		return null;
	}
	public int[] getTempVarps(){
		Client client = getClient();
		if(client!=null){
			return client.tempVarps();
		}
		return new int[]{};
	}
	public int[][][] getTileHeights(){
		Client client = getClient();
		if(client!=null){
			return client.tileHeights();
		}
		return new int[][][]{};
	}
	public MenuRowContext getTopMenuRowContext(){
		Client client = getClient();
		if(client!=null){
			return client.topMenuRowContext();
		}
		return null;
	}
	public int getTradeChatMode(){
		Client client = getClient();
		if(client!=null){
			return client.tradeChatMode();
		}
		return -1;
	}
	public String getUsername(){
		Client client = getClient();
		if(client!=null){
			return client.username();
		}
		return "";
	}
	public Cache getVarPlayerTypeCache(){
		Client client = getClient();
		if(client!=null){
			return client.varPlayerTypeCache();
		}
		return null;
	}
	public AbstractArchive getVarPlayerTypeFileSystem(){
		Client client = getClient();
		if(client!=null){
			return client.varPlayerTypeFileSystem();
		}
		return null;
	}
	public Cache getVarpbitCache(){
		Client client = getClient();
		if(client!=null){
			return client.varpbitCache();
		}
		return null;
	}
	public int[] getVarps(){
		Client client = getClient();
		if(client!=null){
			return client.varps();
		}
		return new int[]{};
	}
	public int getViewportHeight(){
		Client client = getClient();
		if(client!=null){
			return client.viewportHeight();
		}
		return -1;
	}
	public int getViewportScale(){
		Client client = getClient();
		if(client!=null){
			return client.viewportScale();
		}
		return -1;
	}
	public boolean getViewportWalking(){
		Client client = getClient();
		if(client!=null){
			return client.viewportWalking();
		}
		return false;
	}
	public void setViewportWalking(boolean flag){
		Client client = getClient();
		if(client!=null){
			client.setViewportWalking(flag);
		}
	}
	public int getViewportWidth(){
		Client client = getClient();
		if(client!=null){
			return client.viewportWidth();
		}
		return -1;
	}
	public Cache getWidgetBufferedImageCache(){
		Client client = getClient();
		if(client!=null){
			return client.widgetBufferedImageCache();
		}
		return null;
	}
	public HashTable getWidgetFlags(){
		Client client = getClient();
		if(client!=null){
			return client.widgetFlags();
		}
		return null;
	}
	public Cache getWidgetFontCache(){
		Client client = getClient();
		if(client!=null){
			return client.widgetFontCache();
		}
		return null;
	}
	public int[] getWidgetHeights(){
		Client client = getClient();
		if(client!=null){
			return client.widgetHeights();
		}
		return new int[]{};
	}
	public Cache getWidgetModelCache(){
		Client client = getClient();
		if(client!=null){
			return client.widgetModelCache();
		}
		return null;
	}
	public AbstractArchive getWidgetModelFileSystem(){
		Client client = getClient();
		if(client!=null){
			return client.widgetModelFileSystem();
		}
		return null;
	}
	public int[] getWidgetPositionsX(){
		Client client = getClient();
		if(client!=null){
			return client.widgetPositionsX();
		}
		return new int[]{};
	}
	public int[] getWidgetPositionsY(){
		Client client = getClient();
		if(client!=null){
			return client.widgetPositionsY();
		}
		return new int[]{};
	}
	public Cache getWidgetSpriteCache(){
		Client client = getClient();
		if(client!=null){
			return client.widgetSpriteCache();
		}
		return null;
	}
	public Deque getWidgetTimerScriptEventDeque(){
		Client client = getClient();
		if(client!=null){
			return client.widgetTimerScriptEventDeque();
		}
		return null;
	}
	public int[] getWidgetWidths(){
		Client client = getClient();
		if(client!=null){
			return client.widgetWidths();
		}
		return new int[]{};
	}
	public Widget[][] getWidgetS(){
		Client client = getClient();
		if(client!=null){
			return client.widgets();
		}
		return new Widget[][]{};
	}
	public IndexedImage[] getWorldIndexImageArrows(){
		Client client = getClient();
		if(client!=null){
			return client.worldIndexImageArrows();
		}
		return new IndexedImage[]{};
	}
	public IndexedImage[] getWorldIndexImageFlags(){
		Client client = getClient();
		if(client!=null){
			return client.worldIndexImageFlags();
		}
		return new IndexedImage[]{};
	}
	public IndexedImage getWorldIndexImageLeftArrow(){
		Client client = getClient();
		if(client!=null){
			return client.worldIndexImageLeftArrow();
		}
		return null;
	}
	public IndexedImage getWorldIndexImageRightArrow(){
		Client client = getClient();
		if(client!=null){
			return client.worldIndexImageRightArrow();
		}
		return null;
	}
	public IndexedImage[] getWorldIndexImageStars(){
		Client client = getClient();
		if(client!=null){
			return client.worldIndexImageStars();
		}
		return new IndexedImage[]{};
	}
	public Sprite[] getWorldSpriteBackground(){
		Client client = getClient();
		if(client!=null){
			return client.worldSpriteBackground();
		}
		return new Sprite[]{};
	}
	public byte[][] getXteaByteArray1(){
		Client client = getClient();
		if(client!=null){
			return client.xteaByteArray1();
		}
		return new byte[][]{};
	}
	public byte[][] getXteaByteArray2(){
		Client client = getClient();
		if(client!=null){
			return client.xteaByteArray2();
		}
		return new byte[][]{};
	}
	public int[][] getXteaKeys(){
		Client client = getClient();
		if(client!=null){
			return client.xteaKeys();
		}
		return new int[][]{};
	}
	public void setUsername(String username){
		Client client = getClient();
		if(client!=null){
			client.setUsername(username);
		}
	}
	public void setPassword(String password){
		Client client = getClient();
		if(client!=null){
			client.setPassword(password);
		}
	}
}
