package org.osrs.loader;

import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.CheckboxMenuItem;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.prefs.Preferences;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.osrs.api.methods.MethodContext;
import org.osrs.api.objects.GameObject;
import org.osrs.api.objects.GroundItem;
import org.osrs.api.objects.RSNpc;
import org.osrs.api.objects.RSPlayer;
import org.osrs.api.wrappers.Canvas;
import org.osrs.api.wrappers.Client;
import org.osrs.api.wrappers.MouseTracker;
import org.osrs.debug.DebugContext;
import org.osrs.debug.scripts.BoundaryObjectDebug;
import org.osrs.debug.scripts.CameraDebug;
import org.osrs.debug.scripts.FloorDecorationDebug;
import org.osrs.debug.scripts.GroundItemDebug;
import org.osrs.debug.scripts.InteractableObjectDebug;
import org.osrs.debug.scripts.InventoryDebug;
import org.osrs.debug.scripts.LocationDebug;
import org.osrs.debug.scripts.MenuDebug;
import org.osrs.debug.scripts.MouseDebug;
import org.osrs.debug.scripts.NPCDebug;
import org.osrs.debug.scripts.TileDebug;
import org.osrs.debug.scripts.WallDecorationDebug;
import org.osrs.debug.scripts.WidgetDebug;
import org.osrs.randoms.RandomHandler;
import org.osrs.util.AccountManager;
import org.osrs.util.Data;
import org.osrs.util.LoginHandler;
import org.osrs.util.ReflectionAnalyzer;
import org.osrs.util.ScriptDef;
import org.osrs.util.Settings;

public class AppletFrame extends JFrame implements AppletStub, AppletContext, ComponentListener{
	private static final long serialVersionUID = 1L;
	public JTabbedPane tabPane;

	private AccountManager accountManagerGUI;
	private MenuBar menuBar;
	
	private Menu fileMenu;
	public MenuItem startScriptOption;
	public MenuItem pauseScriptOption;
	public MenuItem accountManagerOption;
	public MenuItem addTabOption;
	public MenuItem removeTabOption;
	
	private Menu viewMenu;
	public CheckboxMenuItem debugCameraOption;
	public CheckboxMenuItem debugGroundItemsOption;
	public CheckboxMenuItem debugInventoryOption;
	public CheckboxMenuItem debugLocationOption;
	public CheckboxMenuItem debugNpcsOption;
	public CheckboxMenuItem debugMenuOption;
	public CheckboxMenuItem debugMouseOption;
	public CheckboxMenuItem debugWidgetsOption;
	public CheckboxMenuItem debugTilesOption;
	public CheckboxMenuItem debugBoundaryObjectsOption;
	public CheckboxMenuItem debugFloorDecorationOption;
	public CheckboxMenuItem debugInteractableObjectsOption;
	public CheckboxMenuItem debugWallDecorationOption;
	public MenuItem reflectionAnalyzerOption;
	
	private Menu renderMenu;
	public CheckboxMenuItem disableRender;
	public MenuItem resetIgnoreRender;
	
	private static long runtime;
	public AppletFrame(Applet applet){
		long start = System.currentTimeMillis();
		System.out.println("\n[ - Applet Loader - ]");

		accountManagerGUI=new AccountManager();
		this.setSize(1000, 700);
		this.setResizable(true);
		setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Old School Runescape Game");

		menuBar = new MenuBar();
		fileMenu = new Menu("File");
		accountManagerOption = new MenuItem("Account Manager");
		accountManagerOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accountManagerOptionActionPerformed(evt);
            }
        });
		fileMenu.add(accountManagerOption);
		startScriptOption = new MenuItem("Start Script");
		startScriptOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startScriptOptionActionPerformed(evt);
            }
        });
		fileMenu.add(startScriptOption);
		pauseScriptOption = new MenuItem("Pause Script");
		pauseScriptOption.setEnabled(false);
		pauseScriptOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseScriptOptionActionPerformed(evt);
            }
        });
		fileMenu.add(pauseScriptOption);
		addTabOption = new MenuItem("Add Tab");
		addTabOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addTab();
                startScriptOption.setLabel("Start Script");
                pauseScriptOption.setLabel("Pause Script");
                pauseScriptOption.setEnabled(false);
            }
        });
		fileMenu.add(addTabOption);
		removeTabOption = new MenuItem("Remove Tab");
		removeTabOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeTab();
            }
        });
		removeTabOption.setEnabled(false);
		fileMenu.add(removeTabOption);
		menuBar.add(fileMenu);
		
		Data.debugContext = new DebugContext();
		viewMenu = new Menu("Debug");
		debugCameraOption = new CheckboxMenuItem("Camera Info");
		debugCameraOption.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                if(debugCameraOption.getState()){
                	MethodContext context = Data.debugContext.cameraDebug.methods;
                	Data.debugContext.cameraDebug = new CameraDebug();
                	Data.debugContext.cameraDebug.setContext(context);
                	Data.debugContext.cameraDebug.start();
                }
                else
                	Data.debugContext.cameraDebug.stopScript();
            }
        });
		viewMenu.add(debugCameraOption);
		debugGroundItemsOption = new CheckboxMenuItem("Ground Items");
		debugGroundItemsOption.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                if(debugGroundItemsOption.getState()){
                	MethodContext context = Data.debugContext.groundItemDebug.methods;
                	Data.debugContext.groundItemDebug = new GroundItemDebug();
                	Data.debugContext.groundItemDebug.setContext(context);
                	Data.debugContext.groundItemDebug.start();
                }
                else
                	Data.debugContext.groundItemDebug.stopScript();
            }
        });
		viewMenu.add(debugGroundItemsOption);
		debugLocationOption = new CheckboxMenuItem("Location Info");
		debugLocationOption.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                if(debugLocationOption.getState()){
                	MethodContext context = Data.debugContext.locationDebug.methods;
                	Data.debugContext.locationDebug = new LocationDebug();
                	Data.debugContext.locationDebug.setContext(context);
                	Data.debugContext.locationDebug.start();
                }
                else
                	Data.debugContext.locationDebug.stopScript();
            }
        });
		viewMenu.add(debugLocationOption);
		debugMenuOption = new CheckboxMenuItem("Menu Info");
		debugMenuOption.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                if(debugMenuOption.getState()){
                	MethodContext context = Data.debugContext.menuDebug.methods;
                	Data.debugContext.menuDebug = new MenuDebug();
                	Data.debugContext.menuDebug.setContext(context);
                	Data.debugContext.menuDebug.start();
                }
                else
                	Data.debugContext.menuDebug.stopScript();
            }
        });
		viewMenu.add(debugMenuOption);
		debugNpcsOption = new CheckboxMenuItem("Npcs");
		debugNpcsOption.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                if(debugNpcsOption.getState()){
                	MethodContext context = Data.debugContext.npcDebug.methods;
                	Data.debugContext.npcDebug = new NPCDebug();
                	Data.debugContext.npcDebug.setContext(context);
                	Data.debugContext.npcDebug.start();
                }
                else
                	Data.debugContext.npcDebug.stopScript();
            }
        });
		viewMenu.add(debugNpcsOption);
		debugMouseOption = new CheckboxMenuItem("Mouse Info");
		debugMouseOption.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                if(debugMouseOption.getState()){
                	MethodContext context = Data.debugContext.mouseDebug.methods;
                	Data.debugContext.mouseDebug = new MouseDebug();
                	Data.debugContext.mouseDebug.setContext(context);
                	Data.debugContext.mouseDebug.start();
                }
                else
                	Data.debugContext.mouseDebug.stopScript();
            }
        });
		viewMenu.add(debugMouseOption);
		debugInventoryOption = new CheckboxMenuItem("Inventory");
		debugInventoryOption.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                if(debugInventoryOption.getState()){
                	MethodContext context = Data.debugContext.inventoryDebug.methods;
                	Data.debugContext.inventoryDebug = new InventoryDebug();
                	Data.debugContext.inventoryDebug.setContext(context);
                	Data.debugContext.inventoryDebug.start();
                }
                else
                	Data.debugContext.inventoryDebug.stopScript();
            }
        });
		viewMenu.add(debugInventoryOption);
		debugWidgetsOption = new CheckboxMenuItem("Widgets");
		debugWidgetsOption.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
            	System.out.println("Handling event");
                if(debugWidgetsOption.getState()){
                	MethodContext context = Data.debugContext.widgetDebug.methods;
                	Data.debugContext.widgetDebug = new WidgetDebug();
                	Data.debugContext.widgetDebug.setContext(context);
                	Data.debugContext.widgetDebug.start();
                }
                else
                	Data.debugContext.widgetDebug.stopScript();
            }
        });
		viewMenu.add(debugWidgetsOption);
		debugTilesOption = new CheckboxMenuItem("Tiles");
		debugTilesOption.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
            	System.out.println("Handling event");
                if(debugTilesOption.getState()){
                	MethodContext context = Data.debugContext.tileDebug.methods;
                	Data.debugContext.tileDebug = new TileDebug();
                	Data.debugContext.tileDebug.setContext(context);
                	Data.debugContext.tileDebug.start();
                }
                else
                	Data.debugContext.tileDebug.stopScript();
            }
        });
		viewMenu.add(debugTilesOption);
		debugBoundaryObjectsOption = new CheckboxMenuItem("Boundary Objects");
		debugBoundaryObjectsOption.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
            	System.out.println("Handling event");
                if(debugBoundaryObjectsOption.getState()){
                	MethodContext context = Data.debugContext.boundaryObjectDebug.methods;
                	Data.debugContext.boundaryObjectDebug = new BoundaryObjectDebug();
                	Data.debugContext.boundaryObjectDebug.setContext(context);
                	Data.debugContext.boundaryObjectDebug.start();
                }
                else
                	Data.debugContext.boundaryObjectDebug.stopScript();
            }
        });
		viewMenu.add(debugBoundaryObjectsOption);
		debugFloorDecorationOption = new CheckboxMenuItem("Floor Decorations");
		debugFloorDecorationOption.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
            	System.out.println("Handling event");
                if(debugFloorDecorationOption.getState()){
                	MethodContext context = Data.debugContext.floorDecorationDebug.methods;
                	Data.debugContext.floorDecorationDebug = new FloorDecorationDebug();
                	Data.debugContext.floorDecorationDebug.setContext(context);
                	Data.debugContext.floorDecorationDebug.start();
                }
                else
                	Data.debugContext.floorDecorationDebug.stopScript();
            }
        });
		viewMenu.add(debugFloorDecorationOption);
		debugInteractableObjectsOption = new CheckboxMenuItem("Interactable Objects");
		debugInteractableObjectsOption.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
            	System.out.println("Handling event");
                if(debugInteractableObjectsOption.getState()){
                	MethodContext context = Data.debugContext.interactableObjectDebug.methods;
                	Data.debugContext.interactableObjectDebug = new InteractableObjectDebug();
                	Data.debugContext.interactableObjectDebug.setContext(context);
                	Data.debugContext.interactableObjectDebug.start();
                }
                else
                	Data.debugContext.interactableObjectDebug.stopScript();
            }
        });
		viewMenu.add(debugInteractableObjectsOption);
		debugWallDecorationOption = new CheckboxMenuItem("Wall Decorations");
		debugWallDecorationOption.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
            	System.out.println("Handling event");
                if(debugWallDecorationOption.getState()){
                	MethodContext context = Data.debugContext.wallDecorationDebug.methods;
                	Data.debugContext.wallDecorationDebug = new WallDecorationDebug();
                	Data.debugContext.wallDecorationDebug.setContext(context);
                	Data.debugContext.wallDecorationDebug.start();
                }
                else
                	Data.debugContext.wallDecorationDebug.stopScript();
            }
        });
		viewMenu.add(debugWallDecorationOption);
		reflectionAnalyzerOption = new MenuItem("Reflection Analyzer");
		reflectionAnalyzerOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	MethodContext context = Data.currentContext;
            	if(context!=null){
            		context.reflectionAnalyzer.setVisible(true);
            		context.reflectionAnalyzer.requestFocus();
            	}
            }
        });
		viewMenu.add(reflectionAnalyzerOption);
		menuBar.add(viewMenu);

		renderMenu = new Menu("Render");
		disableRender = new CheckboxMenuItem("Disable Render");
		resetIgnoreRender = new MenuItem("Reset Ignore Disable");
		resetIgnoreRender.setEnabled(false);
		disableRender.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
            	Client c = Data.currentContext.game.getClient();
            	if(c!=null){
	            	if(disableRender.getState()){
	            		if(!c.getDisableRenderingState())
	            			c.setDisableRenderingState();
	            		resetIgnoreRender.setEnabled(true);
	            	}
	            	else{
	            		if(c.getDisableRenderingState())
	            			c.setDisableRenderingState();
	            		resetIgnoreRender.setEnabled(false);
	            	}
            	}
            }
        });
		renderMenu.add(disableRender);
		resetIgnoreRender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	MethodContext context = Data.currentContext;
            	if(context!=null){
	                for(GameObject go : context.objects.getAllObjects()){
	                	if(go.getIgnoreDisableRenderState())
	                		go.setIgnoreDisableRenderState();
	                }
	                for(RSPlayer p : context.players.getAllPlayers()){
	                	if(p.getDisableRenderState())
	                		p.setDisableRenderState();
	                }
	                for(RSNpc npc : context.npcs.getAll()){
	                	if(npc.getDisableRenderState())
	                		npc.setDisableRenderState();
	                }
	                for(GroundItem gi : context.groundItems.getAllItems()){
	                	if(gi.getDisableRenderState())
	                		gi.setDisableRenderState();
	                }
            	}
            }
        });
		renderMenu.add(resetIgnoreRender);
		menuBar.add(renderMenu);
		
		setMenuBar(menuBar);
		JTabbedPane tabs = new JTabbedPane();
		tabPane=tabs;
		add(tabs, BorderLayout.CENTER);
        tabs.add("Tab 1", applet);
        tabPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                Object client = Data.clientInstances.get(tabPane.getSelectedIndex());
                Data.currentInstance = client;
                if(client!=null){
                	ScriptDef script = Data.currentScripts.get(client);
                	Data.currentContext = Data.clientContexts.get(client);
                	Client c = Data.currentContext.game.getClient();
                	if(c!=null){
    	            	if(!c.getDisableRenderingState()){
    	            		disableRender.setState(false);
    	            		resetIgnoreRender.setEnabled(false);
    	            	}
    	            	else{
    	            		disableRender.setState(true);
    	            		resetIgnoreRender.setEnabled(true);
    	            	}
                	}
    		    	Data.debugContext.updateContext(Data.currentContext);
                	if(script!=null){
                        pauseScriptOption.setEnabled(true);
                        startScriptOption.setLabel("Stop Script");
                        if(script.isPaused)
                        	pauseScriptOption.setLabel("Resume Script");
                        else
                            pauseScriptOption.setLabel("Pause Script");
                	}
                	else{
                        startScriptOption.setLabel("Start Script");
                        pauseScriptOption.setLabel("Pause Script");
                        pauseScriptOption.setEnabled(false);
                	}
                }
                else
                	System.out.println("ERROR WITH CLIENT->TAB SYNC");
            }
        });

    	Data.debugContext.updateContext(Data.currentContext);
		
		System.out.println("Starting applet...");
		applet.setStub(this);
		applet.setVisible(true);
		applet.setSize(new Dimension(765, 545));
		applet.init();
		applet.start();
		System.out.println("Succesfully started applet in : "+(System.currentTimeMillis()-start)+"ms");
		Data.currentContext.randomHandler = new RandomHandler((org.osrs.api.wrappers.Client)applet);
		Data.currentContext.randomHandler.start();
		Data.currentContext.loginHandler = new LoginHandler();
		Data.currentContext.loginHandler.setContext(Data.currentContext);
		Data.currentContext.loginHandler.start();
		Data.currentContext.game.getMouseListener().setContext(Data.currentContext);
		runtime=System.currentTimeMillis();
        revalidate();
	}
	public void removeTab(){
		try{
			Object client = Data.currentInstance;
			if(client!=null){
				ScriptDef script = Data.currentScripts.get(client);
				if(script!=null){
					script.stopScript();
					script=null;
				}
				Data.currentScripts.remove(client);
				Data.clientContexts.remove(client);
				Data.clientModscripts.remove(client);
				Data.clientInstances.remove(client);
				Data.clientParameters.remove(client);
				((Applet)client).destroy();
			}
			int index=tabPane.getSelectedIndex();
			tabPane.setSelectedIndex(index-1);
			tabPane.remove(index);
			client = Data.clientInstances.get(tabPane.getSelectedIndex());
            Data.currentInstance = client;
            if(client!=null){
            	ScriptDef script = Data.currentScripts.get(client);
            	Data.currentContext = Data.clientContexts.get(client);
            	Client c = Data.currentContext.game.getClient();
            	if(c!=null){
	            	if(!c.getDisableRenderingState()){
	            		disableRender.setState(false);
	            		resetIgnoreRender.setEnabled(false);
	            	}
	            	else{
	            		disableRender.setState(true);
	            		resetIgnoreRender.setEnabled(true);
	            	}
            	}
		    	Data.debugContext.updateContext(Data.currentContext);
            	if(script!=null){
                    pauseScriptOption.setEnabled(true);
                    startScriptOption.setLabel("Stop Script");
                    if(script.isPaused)
                    	pauseScriptOption.setLabel("Resume Script");
                    else
                        pauseScriptOption.setLabel("Pause Script");
            	}
            	else{
                    startScriptOption.setLabel("Start Script");
                    pauseScriptOption.setLabel("Pause Script");
                    pauseScriptOption.setEnabled(false);
            	}
            }
            if(tabPane.getTabCount()==1)
            	removeTabOption.setEnabled(false);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public void addTab(){
		try{
			long start = System.currentTimeMillis();
			PageParser parser = new PageParser();
			JarDownloader downloader = new JarDownloader(parser.jarLocation);
			if(downloader.downloaded){
				
				Modscript modscript = new Modscript();
				modscript.loadModscript();
				
				ArrayList<ClassNode> classNodes = new ArrayList<ClassNode>();
				File file = new File("runescape.jar");
				JarFile jar = new JarFile(file);
	            Enumeration<?> enumeration = jar.entries();
	            while (enumeration.hasMoreElements()) {
	                JarEntry entry = (JarEntry) enumeration.nextElement();
	                if (entry.getName().endsWith(".class")) {
	                    ClassReader classReader = new ClassReader(jar.getInputStream(entry));
	                    ClassNode classNode = new ClassNode();
	                    classReader.accept(classNode, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
	                    classNodes.add(classNode);
	                }
	            }
	            jar.close();
	            
                modscript.runInjection(classNodes);
                                
	            System.out.println("Injected "+modscript.injectedClasses+" classes!");
	            System.out.println("Injected "+modscript.injectedGetters+" getters!");
	            
	            RSClassLoader classLoader = new RSClassLoader(file, classNodes, parser.parameters.get("codebase"));
				Class<?> client = classLoader.loadClass("client");
				Object instance = client.newInstance();
		    	Data.currentInstance = instance;
		    	Data.clientInstances.add(instance);
		    	Data.currentScripts.put(instance, null);
		    	MethodContext context = new MethodContext(instance);
		    	Data.currentContext = context;
		    	Data.debugContext.updateContext(context);
				Data.clientContexts.put(instance, context);
				Data.clientClassloaders.put(instance, classLoader);
				Data.clientParameters.put(instance, parser.parameters);
				Applet applet = ((Applet)instance);
		        tabPane.add("Tab "+(tabPane.getTabCount()+1), applet);
		        tabPane.setSelectedIndex(tabPane.getTabCount()-1);
				System.out.println("Starting applet...");
				applet.setStub(this);
				applet.setVisible(true);
				applet.setSize(new Dimension(765, 545));
				applet.init();
				applet.start();
				System.out.println("Succesfully started applet in : "+(System.currentTimeMillis()-start)+"ms");
            	context.randomHandler = new RandomHandler((org.osrs.api.wrappers.Client)applet);
            	context.randomHandler.start();
        		context.loginHandler = new LoginHandler();
        		context.loginHandler.setContext(context);
        		context.loginHandler.start();
            	context.game.getMouseListener().setContext(context);
            	removeTabOption.setEnabled(true);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void appletResize(int arg0, int arg1) {
		if(Data.currentInstance!=null){
			Canvas canvas = (Canvas) ((org.osrs.api.wrappers.Client)Data.currentInstance).canvas();
			if(canvas!=null)
				canvas.applySize(arg0, arg1);
		}
	}
	public static Applet getCurrentApplet(){
		if(Data.currentInstance!=null)
			return ((Applet)Data.currentInstance);
		return null;
	}
	public AppletContext getAppletContext() {
		return this;
	}
	public URL getCodeBase() {
		try {
			return new URL(PageParser.BASE_LINK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public URL getDocumentBase() {
		try {
			return new URL(PageParser.BASE_LINK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public String getParameter(String s){
		return Data.clientParameters.get(Data.currentInstance).get(s);
	}
	@Override
	public void setSize(int x, int y){
		super.setSize(x, y);
		appletResize(x, y);
	}
	public void accountManagerOptionActionPerformed(java.awt.event.ActionEvent evt) {
		if(!accountManagerGUI.isVisible())
			accountManagerGUI.setVisible(true);
	}
	public void pauseScriptOptionActionPerformed(java.awt.event.ActionEvent evt) {
		Object client = Data.currentInstance;
		if(client!=null){
			ScriptDef script = Data.currentScripts.get(client);
			if(script!=null){
				if(pauseScriptOption.getLabel().contains("Pause")){
					pauseScriptOption.setLabel("Resume Script");
					script.pause();
				}
				else{
					pauseScriptOption.setLabel("Pause Script");
					script.unpause();
				}
			}
			else{
				startScriptOption.setLabel("Start Script");
				pauseScriptOption.setLabel("Pause Script");
				pauseScriptOption.setEnabled(false);
			}
		}
	}
    @SuppressWarnings("deprecation")
	private void startScriptOptionActionPerformed(java.awt.event.ActionEvent evt) {
		Object client = Data.currentInstance;
		if(client!=null){
			ScriptDef script = Data.currentScripts.get(client);
			if(script!=null){
				startScriptOption.setLabel("Start Script");
				pauseScriptOption.setLabel("Pause Script");
				pauseScriptOption.setEnabled(false);
	    		script.stopScript();
	    		Data.multiboxHandler.scripts.remove(script);
	    		script=null;
	    		Data.currentScripts.put(client, null);
				/*Data.clientContexts.get(client).game.getMouseTracker().setIsRunning(true);
	    		new Thread((Runnable) Data.clientContexts.get(client).game.getMouseTracker()).start();*/
	    		tabPane.setTitleAt(tabPane.getSelectedIndex(), ("Tab "+(tabPane.getSelectedIndex()+1)));
	    		Runtime.getRuntime().gc();
			}
			else{
	    		/*if(Data.account == null) {
	    			System.out.println("You need an account first!");
	    			AccountSelector as = new AccountSelector();
	    			as.setVisible(true);
	    			
	    			return;
	    		}*/
				JFileChooser c = new JFileChooser();    
				c.setCurrentDirectory(new java.io.File(Settings.getLastScriptDirectory()));
				c.setDialogTitle("Select script...");
				c.setFileSelectionMode(JFileChooser.FILES_ONLY);
				c.setFileFilter(new FileFilter() {
			        public boolean accept(File f) {
			            return (f.getName().toLowerCase().endsWith(".class") && !f.getName().contains("$")) ||
			            f.getName().toLowerCase().endsWith(".jar")
			                || f.isDirectory();
			          }
		
			          public String getDescription() {
			            return "AutoRune Scripts";
			          }
			        });
				c.setAcceptAllFileFilterUsed(false);
				if (c.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
					Settings.writeToRegistry("LastScriptLocation", c.getSelectedFile().toString());
					try {
						ClassLoader loader = new ScriptLoader(c.getSelectedFile());
						if(loader!=null){
							Class<?> scriptClass = loader.loadClass(c.getSelectedFile().getName().substring(0, c.getSelectedFile().getName().indexOf(".")));
							Object scriptObject = scriptClass.newInstance();
							if(scriptObject instanceof ScriptDef){
								script = (ScriptDef)scriptObject;
								script.setContext(Data.clientContexts.get(client));
								script.start();
								/*Data.clientContexts.get(client).game.getMouseTracker().setIsRunning(false);
								Thread thread = new Thread(){
								    public void run(){
								    	MouseTracker tracker = Data.clientContexts.get(client).game.getMouseTracker();
								    	for(int i=0;i<Math.min(tracker.trackedX().length,  tracker.trackedY().length);++i){
								    		tracker.setTrackedX(i, 0);
								    		tracker.setTrackedY(i, 0);
								    	}
								    	int index=tracker.index();
								        for(; !tracker.isRunning(); Data.clientContexts.get(client).sleep(50)) {
								        	Object lock = tracker.objectLock();
								            synchronized(tracker.objectLock()){}
								            if(index < 40) {
								            	tracker.setTrackedX(index, 0);
								            	tracker.setTrackedY(index, 0);
								            	tracker.setTimestamp(index, System.currentTimeMillis());
									            tracker.setIndex((index)*(-536157107));
									            index++;
								            }
								            else
								            	index=0;
								         }
								    }
								};
								thread.start();*/
								Data.currentScripts.put(client, script);
								startScriptOption.setLabel("Stop Script");
								pauseScriptOption.setLabel("Pause Script");
								pauseScriptOption.setEnabled(true);
				        		tabPane.setTitleAt(tabPane.getSelectedIndex(), script.getClass().getSimpleName());
				        		
							}
							else{
								System.out.println("Selected file is not a valid script!");
							}
						} 
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
    }
	public void writeToRegistry(String key, String value){
		Preferences userPref = Preferences.userRoot();
		userPref.put(key, value);
	}
	public String readRegistry(String key){
		Preferences userPref = Preferences.userRoot();
		String s = userPref.get(key, "null");
		return s;
	}
	public String getCurrentDirectory(){
		if(readRegistry("LastScriptLocation").equals("null")){
			writeToRegistry("LastScriptLocation", getDefaultDirectory());
		}
		return readRegistry("LastScriptLocation");
	}
	public String getDefaultDirectory(){
		try {
			return new File(".").getCanonicalPath();
		} catch (@SuppressWarnings("unused") Exception e) {
			return System.getProperty("user.dir");
		}
	}
	public long getRuntime(){
		return System.currentTimeMillis()-runtime;
	}
	
	
	
	private final Map<URL, WeakReference<Image>> IMAGE_CACHE = new HashMap<URL, WeakReference<Image>>();
	private final Map<String, InputStream> INPUT_CACHE = Collections.synchronizedMap(new HashMap<String, InputStream>(2));
	
	public Applet getApplet(final String name) {
		return (Applet)Data.currentInstance;
	}
	public Enumeration<Applet> getApplets() {
		final Vector<Applet> apps = new Vector<Applet>();
		apps.add((Applet)Data.currentInstance);
		return apps.elements();
	}
	public AudioClip getAudioClip(final URL url) {
		throw new UnsupportedOperationException("NOT YET IMPLEMENTED getAudioClip=" + url);
	}
	public Image getImage(final URL url) {
		synchronized (IMAGE_CACHE) {
			WeakReference<Image> ref = IMAGE_CACHE.get(url);
			Image img;
			if (ref == null || (img = ref.get()) == null) {
				img = Toolkit.getDefaultToolkit().createImage(url);
				ref = new WeakReference<Image>(img);
				IMAGE_CACHE.put(url, ref);
			}
			return img;
		}
	}

	public InputStream getStream(final String key) {
		return INPUT_CACHE.get(key);
	}


	public Iterator<String> getStreamKeys() {
		return Collections.unmodifiableSet(INPUT_CACHE.keySet()).iterator();
	}

	
	public void setStream(final String key, final InputStream stream) throws IOException {
		INPUT_CACHE.put(key, stream);
	}
	
	public void showDocument(final URL url) {
		showDocument(url, "");
	}

	
	public void showDocument(final URL url, final String target) {
		
	}

	
	public void showStatus(final String status) {
	}
	
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void componentResized(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		appletResize(arg0.getComponent().getWidth(), arg0.getComponent().getHeight());
	}
	
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}