package org.osrs.api.objects;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.Random;

import org.osrs.api.constants.WidgetData;
import org.osrs.api.methods.MethodContext;
import org.osrs.api.methods.MethodProvider;
import org.osrs.api.wrappers.BoundaryObject;
import org.osrs.api.wrappers.Cache;
import org.osrs.api.wrappers.DynamicObject;
import org.osrs.api.wrappers.FloorDecoration;
import org.osrs.api.wrappers.InteractableObject;
import org.osrs.api.wrappers.ObjectDefinition;
import org.osrs.api.wrappers.RenderableNode;
import org.osrs.api.wrappers.WallDecoration;
import org.osrs.input.mouse.MouseHoverJob;
import org.osrs.input.mouse.MouseJob;
import org.osrs.input.mouse.MouseMoveListener;
import org.osrs.input.mouse.MouseTarget;
import org.osrs.api.wrappers.Model;
import org.osrs.api.wrappers.Node;

public class GameObject extends MethodProvider{
	private Object reference = null;
	private RSModel model = null;
	private ObjectDefinition definition = null;
	private RSTile location = null;
	private int orientation = 0;
	private long id = -1;
	private long definitionHash = -1;
	/**
	 * ref must be a InteractableObject, FloorDecoration, WallDecoration, or BoundaryObject
	 * @param ref
	 * @param loc
	 */
	public GameObject(MethodContext context, Object ref, RSTile loc) {
		super(context);
		reference = ref;
		location = loc;
		if (ref instanceof BoundaryObject) {
			RenderableNode tmp = ((BoundaryObject)ref).model();
			if(tmp!=null){
				if(tmp instanceof Model)
					model = new RSModel(methods, tmp);
				else{
					try{
						model = new RSModel(methods, tmp.invokeGetModel((int) methods.getPredicate("RenderableNode", "getModel", "(?)L*;", false)));
					}
					catch(Exception e){}
				}
			}
			else{
				tmp = ((BoundaryObject)ref).secondaryModel();
				if(tmp!=null && tmp instanceof Model)
					model = new RSModel(methods, tmp);
			}
			id = ((BoundaryObject) ref).id();
			orientation = ((BoundaryObject) ref).orientation();
		}
		if (ref instanceof FloorDecoration) {
			if(((FloorDecoration)ref).model() instanceof Model)
				model = new RSModel(methods, ((FloorDecoration)ref).model());
			else{
				RenderableNode obj = ((FloorDecoration)ref).model();
				try{
					model = new RSModel(methods, obj.invokeGetModel((int) methods.getPredicate("RenderableNode", "getModel", "(?)L*;", false)));
				}
				catch(Exception e){}
			}
			id = ((FloorDecoration) ref).hash();
			orientation = 0;
		}
		if (ref instanceof InteractableObject) {
			if(((InteractableObject)ref).model() instanceof Model)
				model = new RSModel(methods, ((InteractableObject)ref).model());
			else{
				RenderableNode obj = ((InteractableObject)ref).model();
				try{
					model = new RSModel(methods, obj.invokeGetModel((int) methods.getPredicate("RenderableNode", "getModel", "(?)L*;", false)));
				}
				catch(Exception e){}
			}
			id = ((InteractableObject) ref).hash();
			orientation = ((InteractableObject) ref).orientation();
			int x = (methods.game.getMapBaseX()+(((InteractableObject)ref).x()/128));
			int y = (methods.game.getMapBaseY()+(((InteractableObject)ref).y()/128));
			location = new RSTile(x, y, loc.getPlane());
		}
		if (ref instanceof WallDecoration) {
			if(((WallDecoration)ref).model() instanceof Model)
				model = new RSModel(methods, ((WallDecoration)ref).model());
			else{
				RenderableNode obj = ((WallDecoration)ref).model();
				try{
					model = new RSModel(methods, obj.invokeGetModel((int) methods.getPredicate("RenderableNode", "getModel", "(?)L*;", false)));
				}
				catch(Exception e){}
			}
			id = ((WallDecoration) ref).hash();
			orientation = ((WallDecoration) ref).orientation();
		}
		definitionHash = id >>> 17 & 0xFFFFFFFF;
		definition = methods.objectDefinitionCache.get(definitionHash);
	}
	public Object getAccessor(){
		return reference;
	}
	/**
	 * Returns the Objects definition
	 * @return objectDefinition
	 */
	public ObjectDefinition getDefinition() {
		if(definition==null)
			definition = methods.objectDefinitionCache.get((int)definitionHash);
		if(definition==null){
			Cache cache = methods.game.getClient().objectDefinitionCache();
			Node node = methods.nodes.lookup(cache.table(), definitionHash);
			if(node!=null){
				definition = (ObjectDefinition)node;
			}
		}
		return definition;
	}
	/**
	 * Returns the object ID
	 * @return id
	 */
	public long getID() {
		return definitionHash;
	}
	/**
	 * Returns the objects location
	 * @return location
	 */
	public RSTile getLocation() {
		return location;
	}
	public int getLocalX(){
		if (reference instanceof InteractableObject)
			return (((InteractableObject)reference).relativeX());
		return location.getX()-methods.game.getMapBaseX();
	}
	public int getLocalY(){
		if (reference instanceof InteractableObject)
			return (((InteractableObject)reference).relativeY());
		return location.getY()-methods.game.getMapBaseY();
	}
	/**
	 * Returns the objects model
	 * @return model
	 */
	public RSModel getModel() {
		return model;
	}
	/**
	 * Returns the object name from its definition
	 * @return name
	 */
	public String getName(){
		ObjectDefinition def = getDefinition();
		if(def!=null){
			return def.name();
		}
		return "nulldef";
	}
	/**
	 * Returns the objects orientation (facing/angle)
	 * @return orientation
	 */
	public int getOrientation() {
		return orientation;
	}
	public boolean click(String action){
		boolean[] ret = new boolean[]{false};
        MouseHoverJob mouseHoverJob = methods.mouse.mouseHandler.createMouseHoverJob(new MouseMoveListener() {
            int count = 0;
            public void onMouseOverTarget(MouseJob mouseJob) {
                count++;
                if(count > methods.calculations.random(3, 16)){
        			if(methods.menu.click(action)){
            			int state=methods.game.getClient().mouseCrosshairState();
            			for(int i=0;i<20 && state==0;++i){
            				methods.sleep(50);
            				state=methods.game.getClient().mouseCrosshairState();
            			}
        				ret[0]=state==2;
        			}
	                mouseJob.stop();
	                return;
                }
            }
            public void onFinished(MouseJob mouseJob) {
            }
        }, getTarget());
        mouseHoverJob.start();
        mouseHoverJob.join();
        return ret[0];
	}
	public boolean click(String action, String option){
		boolean[] ret = new boolean[]{false};
        MouseHoverJob mouseHoverJob = methods.mouse.mouseHandler.createMouseHoverJob(new MouseMoveListener() {
        	int count = 0;
            public void onMouseOverTarget(MouseJob mouseJob) {
                count++;
                if(count > methods.calculations.random(3, 16)){
        			if(methods.menu.click(action, option)){
            			int state=methods.game.getClient().mouseCrosshairState();
            			for(int i=0;i<20 && state==0;++i){
            				methods.sleep(50);
            				state=methods.game.getClient().mouseCrosshairState();
            			}
        				ret[0]=state==2;
        			}
	                mouseJob.stop();
	                return;
                }
            }
            public void onFinished(MouseJob mouseJob) {
            }
        }, getTarget());
        mouseHoverJob.start();
        mouseHoverJob.join();
        return ret[0];
	}
	public boolean click(){
		boolean[] ret = new boolean[]{false};
        MouseHoverJob mouseHoverJob = methods.mouse.mouseHandler.createMouseHoverJob(new MouseMoveListener() {
            int count = 0;
            public void onMouseOverTarget(MouseJob mouseJob) {
                count++;
                if(count > methods.calculations.random(3, 16)){
        			methods.mouse.click();
        			int state=methods.game.getClient().mouseCrosshairState();
        			for(int i=0;i<20 && state==0;++i){
        				methods.sleep(50);
        				state=methods.game.getClient().mouseCrosshairState();
        			}
    				ret[0]=state==2;
	                mouseJob.stop();
	                return;
                }
            }
			public void onFinished(MouseJob mouseJob) {
			}
        }, getTarget());
        mouseHoverJob.start();
        mouseHoverJob.join();
        return ret[0];
	}
	public boolean isHovering(String action){
		if(isHovering()){
			return methods.menu.contains(action);
		}
		return false;
	}
	public boolean isHovering(String action, String option){
		if(isHovering()){
			return methods.menu.contains(action, option);
		}
		return false;
	}
	public boolean isHovering(){
		if(reference instanceof InteractableObject){
			RSTile loc = getLocation();
			int localX = loc.getX()-methods.game.getMapBaseX();
			int localY = loc.getY()-methods.game.getMapBaseY();
			for(int i=0;i<methods.menu.getItemCount();++i){
				if(methods.game.getMenuPrimaryArgs()[i]==id){
					if(methods.game.getMenuSecondaryArgs()[i]==localX &&
					methods.game.getMenuTertiaryArgs()[i]==localY){
						return true;
					}
				}
			}
		}
		else{
			RSModel model = getModel();
			if(model!=null){
				model.getWireframe(getLocation());
				return model.containsMouse;
			}
		}
		return false;
	}
	public boolean hover(String action){
		boolean[] ret = new boolean[]{false};
        MouseHoverJob mouseHoverJob = methods.mouse.mouseHandler.createMouseHoverJob(new MouseMoveListener() {
            int count = 0;
            public void onMouseOverTarget(MouseJob mouseJob) {
                count++;
                if(count > methods.calculations.random(3, 16)){
                	if(isHovering(action))
                		ret[0]=true;
	                mouseJob.stop();
	                return;
                }
            }
            public void onFinished(MouseJob mouseJob) {
            }
        }, getTarget());
        mouseHoverJob.start();
        mouseHoverJob.join();
        return ret[0];
	}
	public boolean hover(String action, String option){
		boolean[] ret = new boolean[]{false};
        MouseHoverJob mouseHoverJob = methods.mouse.mouseHandler.createMouseHoverJob(new MouseMoveListener() {
            int count = 0;
            public void onMouseOverTarget(MouseJob mouseJob) {
                count++;
                if(count > methods.calculations.random(3, 16)){
                	if(isHovering(action, option))
                		ret[0]=true;
	                mouseJob.stop();
	                return;
                }
            }
            public void onFinished(MouseJob mouseJob) {
            }
        }, getTarget());
        mouseHoverJob.start();
        mouseHoverJob.join();
        return ret[0];
	}
	public boolean hover(){
		boolean[] ret = new boolean[]{false};
        MouseHoverJob mouseHoverJob = methods.mouse.mouseHandler.createMouseHoverJob(new MouseMoveListener() {
            int count = 0;
            public void onMouseOverTarget(MouseJob mouseJob) {
                count++;
                if(count > methods.calculations.random(3, 16)){
                	if(isHovering())
                		ret[0]=true;
	                mouseJob.stop();
	                return;
                }
            }
            public void onFinished(MouseJob mouseJob) {
            }
        }, getTarget());
        mouseHoverJob.start();
        mouseHoverJob.join();
        return ret[0];
	}
	public Point getRandomScreenPoint(){
		Polygon[] p = getWireframe();
		if(p.length<1)
			return new Point(-1, -1);
		Polygon pl = p[methods.calculations.random(p.length)];
		Rectangle r = pl.getBounds();
		if(r.width<1 || r.height<1)
			return new Point(r.x, r.y);
		return new Point(r.x+(methods.calculations.random(r.width)), r.y+(methods.calculations.random(r.height)));
	}
	public Point getScreenLocation(){
		double _x = -1;
		double _y = -1;
		if (reference instanceof BoundaryObject) {
			_x = ((BoundaryObject)reference).x();
			_y = ((BoundaryObject)reference).y();
		}
		if (reference instanceof FloorDecoration) {
			_x = ((FloorDecoration)reference).x();
			_y = ((FloorDecoration)reference).y();
		}
		if (reference instanceof InteractableObject) {
			_x = ((InteractableObject)reference).x();
			_y = ((InteractableObject)reference).y();
		}
		if (reference instanceof WallDecoration) {
			_x = ((WallDecoration)reference).x();
			_y = ((WallDecoration)reference).y();
		}
		return methods.calculations.worldToScreen(_x, _y, location.getPlane(), 0);
	}
	public boolean isOnMap(){
		Point p = methods.calculations.locationToMinimap(location);
		RSWidget w = methods.game.getClient().resizeMode()?
				methods.widgets.getChild(WidgetData.RESIZEMODE_MINIMAP[0], WidgetData.RESIZEMODE_MINIMAP[1]):
					methods.widgets.getChild(WidgetData.MINIMAP[0], WidgetData.MINIMAP[1]);
		if(!p.equals(new Point(-1, -1)) && (w!=null && w.getBounds().contains(p)))
			return true;
		return false;
	}
	public boolean isVisible(){
		Point pt = methods.calculations.locationToScreen(location);
		RSWidget viewport = null;
		if(methods.game.getClient().resizeMode()){
			viewport = methods.widgets.getChild(164, 7);
		}
		else{
			viewport = methods.widgets.getChild(163, 0);
		}
		return viewport!=null && viewport.getBounds().contains(pt);
	}
	public Point[] projectVertices(){
		if(model!=null){
			double _x = -1;
			double _y = -1;
			if (reference instanceof BoundaryObject) {
				_x = ((BoundaryObject)reference).x();
				_y = ((BoundaryObject)reference).y();
			}
			if (reference instanceof FloorDecoration) {
				_x = ((FloorDecoration)reference).x();
				_y = ((FloorDecoration)reference).y();
			}
			if (reference instanceof InteractableObject) {
				_x = ((InteractableObject)reference).x();
				_y = ((InteractableObject)reference).y();
			}
			if (reference instanceof WallDecoration) {
				_x = ((WallDecoration)reference).x();
				_y = ((WallDecoration)reference).y();
			}
			return model.projectVertices(location.getPlane(), _x, _y, orientation);
		}
		return new Point[]{};
	}
	public Polygon[] getWireframe(){
		if(model!=null){
			double _x = -1;
			double _y = -1;
			if (reference instanceof BoundaryObject) {
				_x = ((BoundaryObject)reference).x();
				_y = ((BoundaryObject)reference).y();
			}
			if (reference instanceof FloorDecoration) {
				_x = ((FloorDecoration)reference).x();
				_y = ((FloorDecoration)reference).y();
			}
			if (reference instanceof InteractableObject) {
				_x = ((InteractableObject)reference).x();
				_y = ((InteractableObject)reference).y();
			}
			if (reference instanceof WallDecoration) {
				_x = ((WallDecoration)reference).x();
				_y = ((WallDecoration)reference).y();
			}
			return model.getWireframe(location.getPlane(), _x, _y, orientation);
		}
		return new Polygon[]{};
	}
	public MouseTarget getTarget() {
		if (getModel() != null) {
		    return new MouseTarget() {
		    	Point target = getRandomScreenPoint();
		        public Point get() {
		        	return target;
		        }
		        public boolean isOver(int posX, int posY) {
		            return getModel().containsPoint(new Point(posX, posY), location);
		        }
		    };
		}
		return new MouseTarget() {
	    	Point target = getRandomScreenPoint();
	        public Point get() {
	        	return target;
	        }

		    public boolean isOver(int posX, int posY) {
		    	Point p = get();
		        return new Rectangle(p.x - 2, p.y - 2, 4, 4).contains(posX, posY);
		    }
		};
	}
	public void setIgnoreDisableRenderState(){
		RSModel model = this.model;
		if(model==null)
			return;
		Model accessor = model.accessor;
		if(accessor==null)
			return;
		accessor.setDisableRenderState();
	}
	public boolean getIgnoreDisableRenderState(){
		RSModel model = this.model;
		if(model==null)
			return false;
		Model accessor = model.accessor;
		if(accessor==null)
			return false;
		return accessor.getDisableRenderState();
	}
}
