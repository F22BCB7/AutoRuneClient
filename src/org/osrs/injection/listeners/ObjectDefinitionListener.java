package org.osrs.injection.listeners;

import org.osrs.api.methods.MethodContext;
import org.osrs.api.wrappers.Client;
import org.osrs.api.wrappers.ObjectDefinition;
import org.osrs.util.Data;

public class ObjectDefinitionListener {
	public static void detourInitilization(Object defData, Object clientInstance){
		if(defData!=null && defData instanceof ObjectDefinition && clientInstance instanceof Client){
			MethodContext context = Data.clientContexts.get(clientInstance);
			if(context!=null){
				ObjectDefinition def = (ObjectDefinition)defData;
				context.objectDefinitionCache.put(def.id(), def);
			}
		}
	}
}
