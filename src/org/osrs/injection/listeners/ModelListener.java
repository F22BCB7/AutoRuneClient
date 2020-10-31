package org.osrs.injection.listeners;

import org.osrs.api.methods.MethodContext;
import org.osrs.api.objects.RSModel;
import org.osrs.api.wrappers.Actor;
import org.osrs.api.wrappers.Model;
import org.osrs.util.Data;

public class ModelListener {
	public static void updateModels(Object charData, Object modelData, Object clientInstance){
		if(charData==null || modelData==null || clientInstance==null)
			return;
		MethodContext context = Data.clientContexts.get(clientInstance);
		if(context==null)
			return;
		if(charData instanceof Actor && modelData instanceof Model){
			Actor actor = (Actor)charData;
			Model model = (Model)modelData;
			RSModel cachedModel = actor.getCachedModel();
			if(cachedModel!=null){
				cachedModel.updateModel(model);
			}
			else{
				cachedModel = new RSModel(context, model);
			}
			actor.setCachedModel(cachedModel);
		}
	}
}
