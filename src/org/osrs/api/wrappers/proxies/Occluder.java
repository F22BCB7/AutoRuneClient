package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="Occluder")
public class Occluder implements org.osrs.api.wrappers.Occluder{

	@BField
	public int direction;
	@BGetter
	@Override
	public int direction(){return direction;}
	@BField
	public int maxTileX;
	@BGetter
	@Override
	public int maxTileX(){return maxTileX;}
	@BField
	public int minTileY;
	@BGetter
	@Override
	public int minTileY(){return minTileY;}
	@BField
	public int maxTileY;
	@BGetter
	@Override
	public int maxTileY(){return maxTileY;}
	@BField
	public int type;
	@BGetter
	@Override
	public int type(){return type;}
	@BField
	public int minX;
	@BGetter
	@Override
	public int minX(){return minX;}
	@BField
	public int minNormalX;
	@BGetter
	@Override
	public int minNormalX(){return minNormalX;}
	@BField
	public int maxX;
	@BGetter
	@Override
	public int maxX(){return maxX;}
	@BField
	public int maxNormalX;
	@BGetter
	@Override
	public int maxNormalX(){return maxNormalX;}
	@BField
	public int minY;
	@BGetter
	@Override
	public int minY(){return minY;}
	@BField
	public int minNormalY;
	@BGetter
	@Override
	public int minNormalY(){return minNormalY;}
	@BField
	public int minZ;
	@BGetter
	@Override
	public int minZ(){return minZ;}
	@BField
	public int minNormalZ;
	@BGetter
	@Override
	public int minNormalZ(){return minNormalZ;}
	@BField
	public int maxZ;
	@BGetter
	@Override
	public int maxZ(){return maxZ;}
	@BField
	public int maxNormalZ;
	@BGetter
	@Override
	public int maxNormalZ(){return maxNormalZ;}
	@BField
	public int minTileX;
	@BGetter
	@Override
	public int minTileX(){return minTileX;}
	@BField
	public int maxY;
	@BGetter
	@Override
	public int maxY(){return maxY;}
	@BField
	public int maxNormalY;
	@BGetter
	@Override
	public int maxNormalY(){return maxNormalY;}
}