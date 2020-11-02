package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import org.osrs.util.Data;

import java.util.HashMap;
import java.util.Map;

@BClass(name="Region")
public class Region implements org.osrs.api.wrappers.Region{


	@BMethod(name="drawRegion")
	public void _drawRegion(int a, int b, int c, int d, int e, int f){}
	@BDetour
	public void drawRegion(int a, int b, int c, int d, int e, int f){
		if(!Client.disableRendering){
			_drawRegion(a, b, c, d, e, f);
		}
	}
	@BMethod(name="processRegionClick")
	public void _processRegionClick(int a, int b, int c, boolean d){}
	@BDetour
	public void processRegionClick(int a, int b, int c, boolean d){
		if(Client.clientInstance.overridingProcessAction()){//Bot forcing args
			//System.out.println("[processRegionClick] Ignoring region click : "+a+":"+b+":"+c+":"+d);
		}
		else{
			//System.out.println("[processRegionClick] "+a+" : "+b+" : "+c+" : "+d);
			_processRegionClick(a, b, c, d);
		}
	}

	@BField
	public int maxPlane;
	@BGetter
	@Override
	public int maxPlane(){return maxPlane;}
	@BField
	public org.osrs.api.wrappers.Tile[][][] tiles;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Tile[][][] tiles(){return tiles;}
	@BField
	public int entityCount;
	@BGetter
	@Override
	public int entityCount(){return entityCount;}
	@BField
	public int[][][] tileHeights;
	@BGetter
	@Override
	public int[][][] tileHeights(){return tileHeights;}
	@BField
	public int maxY;
	@BGetter
	@Override
	public int maxY(){return maxY;}
	@BField
	public int minPlane;
	@BGetter
	@Override
	public int minPlane(){return minPlane;}
	@BField
	public org.osrs.api.wrappers.InteractableObject[] objects;
	@BGetter
	@Override
	public org.osrs.api.wrappers.InteractableObject[] objects(){return objects;}
	@BField
	public int[][][] tileCycles;
	@BGetter
	@Override
	public int[][][] tileCycles(){return tileCycles;}
	@BField
	public int[][] tileRotations;
	@BGetter
	@Override
	public int[][] tileRotations(){return tileRotations;}
	@BField
	public int maxX;
	@BGetter
	@Override
	public int maxX(){return maxX;}
	@BField
	public int[][] tileMasks;
	@BGetter
	@Override
	public int[][] tileMasks(){return tileMasks;}
}