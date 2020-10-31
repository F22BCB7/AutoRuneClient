package org.osrs.api.objects;

import java.lang.ref.SoftReference;
import org.osrs.api.methods.MethodContext;
import org.osrs.api.wrappers.Model;
import org.osrs.api.wrappers.Player;

public class RSPlayer extends RSActor{
	public SoftReference<Player> accessor;
	public int indice=-1;
	public RSPlayer(MethodContext context, Player p, int index){
		super(context);
		accessor = new SoftReference<Player>(p);
		indice=index;
	}
	public Player getAccessor(){
		return accessor.get();
	}
	public String getName(){
		return getAccessor().nameComposite().name();
	}
	public int getCombatLevel(){
		return getAccessor().combatLevel();
	}
	public int getPlane(){
		return getAccessor().plane();
	}
	@Override
	public RSTile getLocation(){
		Player p = accessor.get();
		if(p!=null){
			return new RSTile((methods.game.getClient().mapBaseX()+(p.x()>>7)), (methods.game.getClient().mapBaseY()+(p.y()>>7)), p.plane());
		}
		return new RSTile(-1, -1, -1);
	}
	public long calculateMenuUID() {
		return (0 & 127) << 0 | (0 & 127) << 7 | (0 & 3) << 14 | (indice & 4294967295L) << 17;
	}
}
