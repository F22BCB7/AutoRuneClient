package org.osrs.api.methods;

import java.util.ArrayList;

import org.osrs.api.objects.RSPlayer;
import org.osrs.api.wrappers.Client;
import org.osrs.api.wrappers.Player;

public class Players extends MethodDefinition{
	public Players(MethodContext context){
		super(context);
	}
	public RSPlayer[] getAllPlayers(){
		Client client = methods.game.getClient();
		if(client!=null){
			Player[] ps =  client.players();
			if(ps!=null){
				ArrayList<RSPlayer> ret = new ArrayList<RSPlayer>();
				for(int i=0;i<ps.length;++i){
					Player p = ps[i];
					if(p!=null)
						ret.add(new RSPlayer(methods, p, i));
				}
				return ret.toArray(new RSPlayer[]{});
			}
		}
		return new RSPlayer[]{};
	}
	public RSPlayer getLocalPlayer(){
		Client client = methods.game.getClient();
		if(client!=null){
			Player p =  client.localPlayer();
			if(p!=null){
				return new RSPlayer(methods, p, p.playerID());
			}
		}
		return null;
	}
}
