package org.osrs.injection.listeners;

import org.osrs.api.methods.MethodContext;
import org.osrs.api.wrappers.ClassInfo;
import org.osrs.util.Data;
import org.osrs.util.ScriptDef;

public class ReflectionCheckListener {
	public static void reflectionRequest(Object clientInstance, Object data){
		if(clientInstance==null)
			return;
		MethodContext context = Data.clientContexts.get(clientInstance);
		if(context==null)
			return;
		if(data instanceof ClassInfo){
			ClassInfo info = (ClassInfo)data;
			ScriptDef script = Data.currentScripts.get(clientInstance);
			if(script!=null)
				script.reflectionRequested(info);
		}
	}
}
