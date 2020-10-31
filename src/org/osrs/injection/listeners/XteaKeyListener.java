package org.osrs.injection.listeners;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.Arrays;

import org.osrs.api.methods.MethodContext;
import org.osrs.util.Data;
import org.osrs.util.ScriptDef;

public class XteaKeyListener {
	public static void xteaChanged(Object clientInstance){
		if(clientInstance==null)
			return;
		MethodContext context = Data.clientContexts.get(clientInstance);
		if(context==null)
			return;
		
		/**
		 * Xtea Key Recorder
		 */
		int[] ids = context.game.getClient().mapRegionIDs();
		int[][] xteas = context.game.getXteaKeys();
		if(ids==null || xteas==null)
			return;
		for(int i=0;i<ids.length;++i){
			int regionID = ids[i];
			int[] xteaKey = xteas[i];
			try {
				File directory = new File(System.getProperty("user.home") + "\\osrs\\region\\");
				if(!directory.exists())
					directory.mkdirs();
				File profile = new File(System.getProperty("user.home") + "\\osrs\\region\\" + regionID+".dat");
				if(!profile.exists())
					profile.createNewFile();
				FileOutputStream file = new FileOutputStream(profile.getPath());
				for (int key = 0; key < xteaKey.length; key++)
					file.write(xteaKey[key]);
				file.close();
			} catch (Exception e) {
				System.out.println("Error writing region xtea key - " + e.toString());
			}
			//int regionX = regionID >> 8 & 255;
			//int regionY = regionID & 255;
			//int worldX = 64 * (regionID >> 8);
			//int worldY = (regionID & 255) * 64;
			//System.out.print(regionID+":("+regionX+":"+regionY+"::"+worldX+":"+worldY+") ");
		}
		
		ScriptDef script = Data.currentScripts.get(clientInstance);
		if(script!=null)
			script.xteaChanged();
	}
}
