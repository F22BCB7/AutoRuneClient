package org.osrs.api.methods;

import java.awt.Point;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import org.osrs.api.objects.RSTile;

import com.google.gson.reflect.TypeToken;

import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

public class Region extends MethodDefinition{
	public Region(MethodContext context){
		super(context);
	}
	public int[][][] getTileHeights(){
		return methods.game.getClient().tileHeights();
	}
	public void clickMap(RSTile tile){
		if(methods.calculations.onMap(tile)){
			Point pt = methods.calculations.locationToMinimap(tile);
			if(!pt.equals(new Point(-1, -1))){
				methods.mouse.move(pt);
				try{Thread.sleep(new Random().nextInt(40)+10);}catch(@SuppressWarnings("unused") Exception e){}
				methods.mouse.click();
			}
		}
	}
	public void loadXteas(){
		HttpUrl url = RuneLiteAPI.getXteaBase().newBuilder().build();
		Request request = new Request.Builder().url(url).build();
		try (Response response = RuneLiteAPI.CLIENT.newCall(request).execute())
		{
			InputStream in = response.body().byteStream();
			// CHECKSTYLE:OFF
			HashMap<Integer, Integer[]> keys = RuneLiteAPI.GSON.fromJson(new InputStreamReader(in), new TypeToken<HashMap<Integer, Integer[]>>() {}.getType());
			System.out.println(Arrays.toString(keys.keySet().toArray()));
			for(Integer[] i : keys.values()){
				System.out.println(Arrays.toString(i));
			}
			for(Integer regionID : keys.keySet()){
				Integer[] xteaKey = keys.get(regionID);
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
			}
			// CHECKSTYLE:ON
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
