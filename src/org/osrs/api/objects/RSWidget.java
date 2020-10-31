package org.osrs.api.objects;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import org.osrs.api.methods.MethodContext;
import org.osrs.api.methods.MethodDefinition;
import org.osrs.api.wrappers.HashTable;
import org.osrs.api.wrappers.Node;
import org.osrs.api.wrappers.Widget;
import org.osrs.api.wrappers.WidgetNode;
import org.osrs.input.mouse.MouseHoverJob;
import org.osrs.input.mouse.MouseJob;
import org.osrs.input.mouse.MouseMoveListener;
import org.osrs.input.mouse.MouseTarget;

public class RSWidget extends MethodDefinition{
	private int index;
	private RSInterface parentInterface;
	private RSWidget parentWidget;
	private RSWidget[] cachedChildren = new RSWidget[]{};
	private final HashMap<Integer, RSWidget> sparseMap = new HashMap<Integer, RSWidget>();
	public RSWidget(MethodContext context, RSInterface parent, int index){
		super(context);
		this.index=index;
		this.parentInterface=parent;
		this.parentWidget=null;
	}
	public RSWidget(MethodContext context, RSInterface parent, RSWidget parent2, int index){
		super(context);
		this.index=index;
		this.parentInterface=parent;
		this.parentWidget=parent2;
	}
	public Point getCenterPoint(){
		return new Point(this.getAbsoluteX()+(this.getWidth()/2), this.getAbsoluteY()+(this.getHeight()/2));
	}
	public RSWidget getChild(int ind){
		Widget iface = getInternal();
		if(iface==null)
			return null;
		Widget[] childs = iface.children();
		if(childs==null || childs.length<=ind)
			return null;
		RSWidget temp=null;
		int cacheLength = cachedChildren.length;
		if(ind<cacheLength){
			temp = cachedChildren[ind];
			if(temp==null){
				temp = new RSWidget(methods, parentInterface, this, ind);
				cachedChildren[ind] = temp;
			}
		}
		else{
			temp = sparseMap.get(ind);
			if(temp==null){
				enlargeCache();
				if(ind<cacheLength){
					temp = cachedChildren[ind];
					if(temp==null){
						temp = new RSWidget(methods, parentInterface, this, ind);
						cachedChildren[ind]=temp;
					}
				}
				else{
					temp = new RSWidget(methods, parentInterface, this, ind);
					sparseMap.put(ind, temp);
				}
			}
		}
		return temp;
	}
	public synchronized int getChildCount(){
		enlargeCache();
		return cachedChildren.length;
	}
	public RSWidget[] getChildren(){
		enlargeCache();
		Widget[] children = getChildrenInternal();
		if(children==null || children.length==0)
			return new RSWidget[]{};
		ArrayList<RSWidget> out = new ArrayList<RSWidget>();
		for(int i=0;i<children.length;++i){
			RSWidget tmp = getChild(i);
			if(tmp!=null)
				out.add(tmp);
		}
		return out.toArray(new RSWidget[]{});
	}
	public Widget[] getChildrenInternal(){
		Widget internal = getInternal();
		if(internal!=null)
			return internal.children();
		return null;
	}
	public int getIndex(){
		return index;
	}
	public int getID(){
		Widget internal = getInternal();
		if(internal!=null)
			return internal.id();
		return -1;
	}
	public int getContainerIndex(){
		int id = getID();
		if(id!=-1){
			return id >> 16;
		}
		return -1;
	}
	public int getWidgetIndex(){
		int id = getID();
		if(id!=-1){
			return id & 0xFFFF;
		}
		return -1;
	}
	public Widget getInternal(){
		if(parentWidget!=null){
			Widget p = parentWidget.getInternal();
			if(p!=null){
				Widget[] children = p.children();
				if(children!=null && index>=0 && index<children.length)
					return children[index];
			}
		}
		else{
			Widget[] children = parentInterface.getChildrenInternal();
			if(children!=null && index>=0 && index<children.length)
				return children[index];
		}
		return null;
	}
	public RSInterface getParentInterface(){
		return parentInterface;
	}
	public RSWidget getParentWidget(){
		return parentWidget;
	}
	public int getParentID(){
		Widget w = getInternal();
		if(w!=null){
			int id = w.parentID();
			if(id!=-1)
				return id;
		}
		HashTable table = methods.game.getClient().componentTable();
		if(table!=null){
			for(Node node : table.buckets()){
				if(node!=null){
					if(node instanceof WidgetNode){
						WidgetNode wNode = (WidgetNode)node;
						if(wNode.id()==getContainerIndex())
							return (int)wNode.uid();
					}
					for(Node next=node.next();next!=null && !next.equals(node);next=next.next()){
						if(next instanceof WidgetNode){
							WidgetNode wNode = (WidgetNode)next;
							if(wNode.id()==getContainerIndex())
								return (int)wNode.uid();
						}
					}
				}
			}
		}
		return -1;
	}
	public int getBoundsIndex(){
		Widget w = getInternal();
		if(w!=null){
			return w.boundsIndex();
		}
		return -1;
	}
	public int getRelativeX(){
		Widget w = getInternal();
		if(w!=null){
			return w.relativeX();
		}
		return -1;
	}
	public int getRelativeY(){
		Widget w = getInternal();
		if(w!=null){
			return w.relativeY();
		}
		return -1;
	}
	public int getScrollX(){
		Widget w = getInternal();
		if(w!=null){
			return w.scrollX();
		}
		return -1;
	}
	public int getScrollY(){
		Widget w = getInternal();
		if(w!=null){
			return w.scrollY();
		}
		return -1;
	}
	public int getHeight(){
		Widget w = getInternal();
		if(w!=null){
			return w.height();
		}
		return -1;
	}
	public int getWidth(){
		Widget w = getInternal();
		if(w!=null){
			return w.width();
		}
		return -1;
	}
	public Point getAbsolutePosition(){
		return new Point(getAbsoluteX(), getAbsoluteY());
	}
	public int getAbsoluteX(){
		int x = 0;
		int parentID = getParentID();
		if (parentID != -1) {
			int containerIndex = getParentID() >> 16;
			int widgetIndex = getParentID() & 0xFFFF;
			RSWidget parent = methods.widgets.getChild(containerIndex, widgetIndex);
			x = parent.getAbsoluteX() - getScrollX();
		} else {
			int posX[] = methods.game.getClient().widgetPositionsX();
			int bi = getBoundsIndex();
			if (bi != -1 && bi < posX.length) {
				return posX[bi];
			}
		}
		x += getRelativeX();
		return x;
	}
	public int getAbsoluteY(){
		int y = 0;
		int parentID = getParentID();
		if (parentID != -1) {
			int index1 = getParentID() >> 16;
			int index2 = getParentID() & 0xFFFF;
			RSWidget parent = methods.widgets.getChild(index1, index2);
			y = parent.getAbsoluteY() - getScrollY();
		} else {
			int posY[] = methods.game.getClient().widgetPositionsY();
			int bi = getBoundsIndex();
			if (bi != -1 && bi < posY.length) {
				return posY[bi];
			}
		}
		y += getRelativeY();
		return y;
	}
	public Rectangle getBounds(){
		return new Rectangle(getAbsoluteX(), getAbsoluteY(), getWidth(), getHeight());
	}
	public boolean isHovering(){
		return getBounds().contains(methods.mouse.getLocation());
	}
	public boolean isHovering(String action){
		if(getBounds().contains(methods.mouse.getLocation())){
			return methods.menu.contains(action);
		}
		return false;
	}
	public boolean isHovering(String action, String option){
		if(getBounds().contains(methods.mouse.getLocation())){
			return methods.menu.contains(action, option);
		}
		return false;
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
	public boolean click(){
		boolean[] ret = new boolean[]{false};
        MouseHoverJob mouseHoverJob = methods.mouse.mouseHandler.createMouseHoverJob(new MouseMoveListener() {
            int count = 0;
            public void onMouseOverTarget(MouseJob mouseJob) {
                count++;
                if(count > methods.calculations.random(3, 16)){
        			methods.mouse.click();
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
	public boolean click(String action){
		boolean[] ret = new boolean[]{false};
        MouseHoverJob mouseHoverJob = methods.mouse.mouseHandler.createMouseHoverJob(new MouseMoveListener() {
            int count = 0;
            public void onMouseOverTarget(MouseJob mouseJob) {
                count++;
                if(count > methods.calculations.random(3, 16)){
                	ret[0] = (methods.menu.click(action));
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
                	ret[0] = (methods.menu.click(action, option));
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
	public Point getRandomScreenPoint() {
		Rectangle bounds = getBounds();
		Point start = new Point(bounds.x + 4, bounds.y + 4);
		return new Point(start.x + methods.calculations.random(bounds.width-4)
				, start.y + methods.calculations.random(bounds.height-4));
	}
	public MouseTarget getTarget() {
	    return new MouseTarget() {
	    	Point target = getRandomScreenPoint();
	        public Point get() {
	        	return target;
	        }
	        public boolean isOver(int posX, int posY) {
	        	Rectangle bounds = getBounds();
	            return bounds.contains(new Point(posX, posY));
	        }
	    };
	}
	private synchronized void enlargeCache() {
		Widget[] childs = getChildrenInternal();
		if(childs!=null && cachedChildren.length<childs.length){
			cachedChildren = Arrays.copyOf(cachedChildren, childs.length);
			for(int i=0;i<cachedChildren.length;++i){
				RSWidget tmp = sparseMap.get(i);
				if(tmp!=null){
					sparseMap.remove(i);
					cachedChildren[i]=tmp;
				}
			}
		}
	}
}
