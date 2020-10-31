package org.osrs.debug;

import org.osrs.api.methods.MethodContext;
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

public class DebugContext{
	public CameraDebug cameraDebug;
	public MenuDebug menuDebug;
	public MouseDebug mouseDebug;
	public InventoryDebug inventoryDebug;
	public LocationDebug locationDebug;
	public NPCDebug npcDebug;
	public GroundItemDebug groundItemDebug;
	public TileDebug tileDebug;
	public WidgetDebug widgetDebug;
	public BoundaryObjectDebug boundaryObjectDebug;
	public FloorDecorationDebug floorDecorationDebug;
	public InteractableObjectDebug interactableObjectDebug;
	public WallDecorationDebug wallDecorationDebug;
	public DebugContext(){
		cameraDebug = new CameraDebug();
		menuDebug = new MenuDebug();
		mouseDebug = new MouseDebug();
		inventoryDebug = new InventoryDebug();
		locationDebug = new LocationDebug();
		npcDebug = new NPCDebug();
		groundItemDebug = new GroundItemDebug();
		tileDebug = new TileDebug();
		widgetDebug = new WidgetDebug();
		boundaryObjectDebug = new BoundaryObjectDebug();
		floorDecorationDebug = new FloorDecorationDebug();
		interactableObjectDebug = new InteractableObjectDebug();
		wallDecorationDebug = new WallDecorationDebug();
	}
	public void updateContext(MethodContext context){
		cameraDebug.setContext(context);
		menuDebug.setContext(context);
		mouseDebug.setContext(context);
		inventoryDebug.setContext(context);
		locationDebug.setContext(context);
		npcDebug.setContext(context);
		groundItemDebug.setContext(context);
		tileDebug.setContext(context);
		widgetDebug.setContext(context);
		boundaryObjectDebug.setContext(context);
		floorDecorationDebug.setContext(context);
		interactableObjectDebug.setContext(context);
		wallDecorationDebug.setContext(context);
	}
}