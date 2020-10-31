package org.osrs.api.objects;

import java.lang.ref.SoftReference;

import org.osrs.api.methods.MethodContext;
import org.osrs.api.wrappers.Model;
import org.osrs.api.wrappers.NPCDefinition;
import org.osrs.api.wrappers.Npc;

public class RSNpc extends RSActor{
	public SoftReference<Npc> accessor;
	public int indice=-1;
	public RSNpc(MethodContext context, Npc p, int index){
		super(context);
		accessor = new SoftReference<Npc>(p);
		indice=index;
	}
	public Npc getAccessor(){
		return accessor.get();
	}
	public NPCDefinition getDefinition(){
		return getAccessor().definition();
	}
	public int getID(){
		Npc npc = getAccessor();
		if(npc!=null){
			NPCDefinition def = npc.definition();
			if(def!=null){
				return def.id();
			}
		}
		return -1;
	}
	public String getName(){
		Npc npc = getAccessor();
		if(npc!=null){
			NPCDefinition def = npc.definition();
			if(def!=null){
				return def.name();
			}
		}
		return "";
	}
	@Override
	public RSTile getLocation(){
		Npc p = accessor.get();
		if(p!=null){
			return new RSTile((methods.game.getClient().mapBaseX()+(p.x()>>7)), 
					(methods.game.getClient().mapBaseY()+(p.y()>>7)), 
					methods.game.getCurrentPlane());
		}
		return new RSTile(-1, -1, -1);
	}
	public long calculateMenuUID() {
		return (0 & 127) << 0 | (0 & 127) << 7 | (1 & 3) << 14 | (indice & 4294967295L) << 17;
	}
}
