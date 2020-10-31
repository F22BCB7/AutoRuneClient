package org.osrs.api.objects;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.stream.LongStream;

import org.osrs.api.constants.WidgetData;
import org.osrs.api.methods.MethodContext;
import org.osrs.api.methods.Skills;
import org.osrs.api.wrappers.Actor;
import org.osrs.api.wrappers.CombatBar;
import org.osrs.api.wrappers.CombatBarData;
import org.osrs.api.wrappers.LinkedList;
import org.osrs.api.wrappers.Model;
import org.osrs.api.wrappers.Node;
import org.osrs.api.wrappers.NodeList;
import org.osrs.api.wrappers.Npc;
import org.osrs.api.wrappers.Player;
import org.osrs.input.mouse.MTimer;
import org.osrs.input.mouse.MouseHoverJob;
import org.osrs.input.mouse.MouseJob;
import org.osrs.input.mouse.MouseMoveListener;
import org.osrs.input.mouse.MouseTarget;

public abstract class RSActor extends RSRenderable{
	public abstract Actor getAccessor();
	public RSActor(MethodContext context){
		super(context);
	}
	public int getAnimationID(){
		return getAccessor().animationID();
	}
	public boolean isMoving(){
		Actor a = getAccessor();
		if(a!=null){
			return a.movementSpeed()>0;
		}
		return false;
	}
	public boolean isIdle(){
		return !isMoving() && getAnimationID()==-1;
	}
	public CombatBarData getCombatData(){
		int gameCycle = methods.game.getClient().gameCycle();
		Actor a = getAccessor();
		if(a!=null){
			NodeList nl = a.combatInfoList();
			if(nl!=null){
				Node head = nl.head();
				if(head!=null){
					for(Node node=head.previous();node!=null && !node.equals(head);node=node.previous()){
						if(node instanceof CombatBar){
							CombatBar bar = (CombatBar)node;
							NodeList data = bar.combatDataList();
							if(data!=null){
								Node node2=data.head();
								if(node2!=null){
									for(Node node3=node2.previous();node3!=null && !node3.equals(node2);node3=node3.previous()){
										if(node3 instanceof CombatBarData){
											CombatBarData barData = (CombatBarData)node3;
											if(barData.cycle()<=gameCycle){
												return barData;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return null;
	}
	public int getHPPercent(){
		Actor p = getAccessor();
		if(p!=null){
			if(p instanceof Player){
				try{//Attempts to get realtime HP for local player
					//To ignore the hitbar update lag
					RSPlayer local = methods.players.getLocalPlayer();
					if(local!=null){
						if(local.getAccessor().equals(p)){//its local player
							int curr = methods.skills.getSkillLevel(Skills.CONSTITUTION_INDEX);
							int max = methods.skills.getSkillMaxLevel(Skills.CONSTITUTION_INDEX);
							if(max>=10 && curr>=0){
								DecimalFormat df = new DecimalFormat("#.##");
								double d = Double.parseDouble(df.format(((curr*1.0)/(max*1.0))));
								return (int)(d*100);
							}
						}
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
			CombatBarData data = getCombatData();
			if(data!=null){
				return (int)(data.health()*(10.0/3));
			}
			return 100;
		}
		return -1;
	}
	public boolean isInteractingWith(RSActor actor){
		if(actor==null)
			return false;
		int id = getAccessor().interactingID();
		if(id==-1)
			return false;
		Actor actor2 = null;
		if(id>=32768){
			id-=32768;
			Player[] players = methods.game.getClient().players();
			if(players.length>id){
				Player player = players[id];
				if(player!=null)
					actor2=player;
			}
		}
		else{
			Npc[] npcs = methods.game.getClient().npcs();
			if(npcs.length>id){
				Npc npc = npcs[id];
				if(npc!=null)
					actor2=npc;
			}
		}
		if(actor2!=null){
			return actor2.equals(actor.getAccessor());
		}
		return false;
	}
	public RSPlayer getInteractingPlayer(){
		int id = getAccessor().interactingID();
		if(id >= 32768){
			id-=32768;
			Player[] players = methods.game.getClient().players();
			if(players.length>id){
				Player player = players[id];
				if(player!=null)
					return new RSPlayer(methods, player, id);
			}
		}
		return null;
	}
	public int getInteractingID(){
		return getAccessor().interactingID();
	}
	public RSNpc getInteractingNpc(){
		int id = getAccessor().interactingID();
		if(id==-1)
			return null;
		if(id < 32768){
			Npc[] npcs = methods.game.getClient().npcs();
			if(npcs.length>id){
				Npc npc = npcs[id];
				if(npc!=null)
					return new RSNpc(methods, npc, id);
			}
		}
		return null;
	}
	public RSTile getLocation(){
		Actor p = getAccessor();
		if(p!=null){
			return new RSTile((methods.game.getClient().mapBaseX()+(p.x()>>7)), (methods.game.getClient().mapBaseY()+(p.y()>>7)));
		}
		return new RSTile(-1, -1, -1);
	}
	public int getLocalX(){
		return getLocation().getX()-methods.game.getMapBaseX();
	}
	public int getLocalY(){
		return getLocation().getY()-methods.game.getMapBaseY();
	}
	public RSModel getModel(){
		RSModel temp = getAccessor().getTempModel();
		if(temp!=null)
			return temp;
		RSModel model = getAccessor().getCachedModel();
		if(model!=null)
			return model;
		return null;
	}
	public int getOrientation(){
		return (int)((getAccessor().orientation()/128)*22.5);
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
		//if(LongStream.of(methods.game.getOnCursorUIDs()).anyMatch(x -> x == calculateMenuUID())){
			RSModel model = this.getModel();
			if(model!=null){
				model.getWireframe(getLocation());//so mouse is checked
				return model.containsMouse;
			}
		//}
		return false;
	}
	public boolean hover(String action){
		boolean[] ret = new boolean[]{false};
        MouseHoverJob mouseHoverJob = methods.mouse.mouseHandler.createMouseHoverJob(new MouseMoveListener() {
            int count = 0;
            public void onMouseOverTarget(MouseJob mouseJob) {
                count++;
                if(count > methods.calculations.random(3, 16)){
	                mouseJob.stop();
	                return;
                }
            }
            public void onFinished(MouseJob mouseJob) {
            	if(isHovering(action))
            		ret[0]=true;
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
	                mouseJob.stop();
	                return;
                }
            }
            public void onFinished(MouseJob mouseJob) {
            	if(isHovering(action, option))
            		ret[0]=true;
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
	                mouseJob.stop();
	                return;
                }
            }
            public void onFinished(MouseJob mouseJob) {
            	if(isHovering())
            		ret[0]=true;
            }
        }, getTarget());
        mouseHoverJob.start();
        mouseHoverJob.join();
        return ret[0];
	}
	public Point getCenterPoint(){
		Actor acc = getAccessor();
		if(acc!=null){
			RSModel model = getModel();
			if(model!=null){
				Point center = model.getCenterPoint(getLocation().getPlane(), getAccessor().x(), getAccessor().y(), getOrientation(), getHeight());
				return center;
			}
		}
		return getScreenLocation();
	}
	public Point getRandomScreenPoint(){
		Polygon[] p = getWireframe();
		if(p.length<1)
			return new Point(-1, -1);
		Polygon pl = p[methods.calculations.random(p.length)];
		Rectangle r = pl.getBounds();
		return new Point(r.x+(methods.calculations.random(Math.abs(r.width)+1)), r.y+(methods.calculations.random(Math.abs(r.height)+1)));
	}
	public Point getScreenLocation(){
		Actor a = getAccessor();
		if(a!=null){
			return methods.calculations.worldToScreen(a.x(), a.y(), getLocation().getPlane(), a.heightOffset());
		}
		return null;
	}
	public MouseTarget getTarget() {
		return new MouseTarget() {
	    	Point target = getRandomScreenPoint();
	        public Point get() {
	        	return target;
	        }
		    public boolean isOver(int posX, int posY) {
				Point target = getCenterPoint();
		    	return isHovering() || new Rectangle(target.x - 2, target.y - 2, 4, 4).contains(posX, posY);
		    }
		};
	}
	public boolean isOnMap(){
		Point p = methods.calculations.locationToMinimap(getLocation());
		RSWidget w = methods.game.getClient().resizeMode()?
				methods.widgets.getChild(WidgetData.RESIZEMODE_MINIMAP[0], WidgetData.RESIZEMODE_MINIMAP[1]):
					methods.widgets.getChild(WidgetData.MINIMAP[0], WidgetData.MINIMAP[1]);
		if(!p.equals(new Point(-1, -1)) && (w!=null && w.getBounds().contains(p)))
			return true;
		return false;
	}
	public boolean isVisible(){
		Point pt = methods.calculations.locationToScreen(getLocation());
		RSWidget viewport = null;
		if(methods.game.getClient().resizeMode()){
			viewport = methods.widgets.getChild(164, 8);
		}
		else{
			viewport = methods.widgets.getChild(163, 0);
		}
		return viewport!=null && viewport.getBounds().contains(pt);
	}
	public Point[] projectVertices(){
		Actor acc = getAccessor();
		if(acc!=null){
			RSModel model = getModel();
			if(model!=null){
				return model.projectVertices(getLocation().getPlane(), acc.x(), acc.y(), (int)((acc.orientation()/128)*22.5), acc.heightOffset());
			}
		}
		return new Point[]{};
	}
	public Polygon[] getWireframe(){
		Actor acc = getAccessor();
		if(acc!=null){
			RSModel model = getModel();
			if(model!=null){
				return model.getWireframe(getLocation().getPlane(), acc.x(), acc.y(), (int)((acc.orientation()/128)*22.5), acc.heightOffset());
			}
		}
		return new Polygon[]{};
	}
	public abstract long calculateMenuUID();
	public int calculateMenuTag() {
		return (int)(calculateMenuUID() >>> 17 & 4294967295L);
	}
}
