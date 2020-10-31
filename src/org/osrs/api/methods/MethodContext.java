package org.osrs.api.methods;

import java.awt.Point;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.osrs.api.objects.MenuAction;
import org.osrs.api.wrappers.ItemDefinition;
import org.osrs.api.wrappers.ObjectDefinition;
import org.osrs.api.wrappers.OutgoingPacket;
import org.osrs.injection.FieldHook;
import org.osrs.injection.MethodHook;
import org.osrs.input.MouseHandler;
import org.osrs.loader.Modscript;
import org.osrs.randoms.RandomHandler;
import org.osrs.util.BufferTracker;
import org.osrs.util.Data;
import org.osrs.util.LoginHandler;
import org.osrs.util.MultiboxHandler;
import org.osrs.util.ReflectionAnalyzerFrame;
import org.osrs.util.ReflectionAnalyzerSubFrame;

public class MethodContext {
	public ArrayList<MouseListener> registeredMouseListeners = new ArrayList<MouseListener>();
	public Object botInstance=null;
	public RandomHandler randomHandler=null;
	public LoginHandler loginHandler=null;
	public MultiboxHandler multiboxHandler=null;
	public ReflectionAnalyzerFrame reflectionAnalyzer=null;
	public ArrayList<ReflectionAnalyzerSubFrame> reflectionAnalyzerSubFrames=null;
	
	public HashMap<Integer, ItemDefinition> itemDefinitionCache;
	public HashMap<Integer, ObjectDefinition> objectDefinitionCache;
	
	public Bank bank = null;
	public Calculations calculations = null;
	public Camera camera = null;
	public Equipment equipment = null;
	public Game game = null;
	public GroundItems groundItems = null;
	public Inventory inventory = null;
	public Keyboard keyboard = null;
	public Menu menu = null;
	public Minimap minimap = null;
	public Mouse mouse = null;
	public MouseWheel mouseWheel = null;
	public Nodes nodes = null;
	public NPCs npcs = null;
	public Objects objects = null;
	public Players players = null;
	public Region region = null;
	public Skills skills = null;
	public Tabs tabs = null;
	public Varps varps = null;
	public Walking walking = null;
	public Widgets widgets = null;
	
	public MethodContext(Object client){
		botInstance=client;
		itemDefinitionCache = new HashMap<Integer, ItemDefinition>();
		objectDefinitionCache = new HashMap<Integer, ObjectDefinition>();
		
		bank = new Bank(this);
		calculations = new Calculations(this);
		camera = new Camera(this);
		equipment = new Equipment(this);
		game = new Game(this);
		groundItems = new GroundItems(this);
		inventory = new Inventory(this);
		keyboard = new Keyboard(this);
		menu = new Menu(this);
		minimap = new Minimap(this);
		mouse = new Mouse(this);
		mouseWheel = new MouseWheel(this);
		nodes = new Nodes(this);
		npcs = new NPCs(this);
		objects = new Objects(this);
		players = new Players(this);
		region = new Region(this);
		skills = new Skills(this);
		varps = new Varps(this);
		walking = new Walking(this);
		widgets = new Widgets(this);
		tabs = new Tabs(this);
		
		multiboxHandler = Data.multiboxHandler;
		reflectionAnalyzer = new ReflectionAnalyzerFrame(this);
		reflectionAnalyzerSubFrames = new ArrayList<ReflectionAnalyzerSubFrame>();
	}
	public Object getPredicate(String owner, String name, String desc, boolean isStatic){
		Modscript modscript = Data.clientModscripts.get(botInstance);
		if(modscript!=null){
			MethodHook mh = modscript.resolver.getMethodHook(owner, name, desc, isStatic);
			if(mh!=null){
				String pred = ""+mh.desc.charAt(mh.desc.indexOf(")")-1);
				if(pred.equals("B"))
					return (byte)mh.predicate;
				else if(pred.equals("S"))
					return (short)mh.predicate;
				else if(pred.equals("I"))
					return (int)mh.predicate;
				else
					System.err.println("Unhandled predicate type! "+pred);
			}
		}
		return null;
	}
	public Object getGetterMultiplier(String owner, String name, boolean isStatic){
		Modscript modscript = Data.clientModscripts.get(botInstance);
		if(modscript!=null){
			FieldHook fh = modscript.resolver.getFieldHook(owner, name, isStatic);
			if(fh!=null)
				return fh.dataType.equals("I")?(int)fh.multiplier:(long)fh.multiplier;
		}
		return null;
	}
	public Object getSetterMultiplier(String owner, String name, boolean isStatic){
		Modscript modscript = Data.clientModscripts.get(botInstance);
		if(modscript!=null){
			FieldHook fh = modscript.resolver.getFieldHook(owner, name, isStatic);
			if(fh!=null){
				if(fh.dataType.equals("I")){
					int odd = (int)fh.multiplier;
					int i = (3 * odd) ^ 2;
				    i *= 2 - odd * i;
				    i *= 2 - odd * i;
				    return (i * (2 - odd * i));
				}
				else if(fh.dataType.equals("J")){
					long odd = (long)fh.multiplier;
				    long l = (3L * odd) ^ 2L;
				    l *= 2L - odd * l;
				    l *= 2L - odd * l;
				    l *= 2L - odd * l;
				    return (l * (2L - odd * l));
				}
			}
		}
		return null;
	}
	/**
	 * Computes the multiplicative inverse of the odd integer.
	 * @param odd The odd integer to compute the inverse of.
	 * @return The multiplicative inverse.
	 */
	public int inverse(final int odd) {
	    int i = (3 * odd) ^ 2;
	    i *= 2 - odd * i;
	    i *= 2 - odd * i;
	    return i * (2 - odd * i);
	}
	/**
	 * Computes the multiplicative inverse of the odd long integer.
	 * @param odd The odd long integer to compute the inverse of.
	 * @return The multiplicative inverse.
	 */
	public long inverse(final long odd) {
	    long l = (3L * odd) ^ 2L;
	    l *= 2L - odd * l;
	    l *= 2L - odd * l;
	    l *= 2L - odd * l;
	    return l * (2L - odd * l);
	}
	public void addMouseListener(MouseListener listener){
		registeredMouseListeners.add(listener);
	}
	public void removeMouseListener(MouseListener listener){
		registeredMouseListeners.remove(listener);
	}
	/**
	 * Current instance script will 
	 * have its thread sleep for given millisecond 
	 * timeout.
	 * @param timeout
	 */
	public void sleep(int timeout){
		try{
			Thread.sleep(timeout);
		}
		catch(Exception e){
		}
	}
}