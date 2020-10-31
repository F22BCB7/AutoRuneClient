package org.osrs.injection.listeners;

import org.osrs.api.methods.MethodContext;
import org.osrs.api.wrappers.Client;
import org.osrs.api.wrappers.ItemDefinition;
import org.osrs.util.Data;

public class ItemDefinitionListener {
	public static void detourInitilization(Object defData, Object clientInstance){
		if(defData!=null && defData instanceof ItemDefinition && clientInstance instanceof Client){
			MethodContext context = Data.clientContexts.get(clientInstance);
			if(context!=null){
				ItemDefinition def = (ItemDefinition)defData;
				context.itemDefinitionCache.put(def.id(), def);
			}
		}
	}
}
