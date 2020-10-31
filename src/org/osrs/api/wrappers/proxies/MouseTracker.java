package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BFunction;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@BClass(name="MouseTracker")
public class MouseTracker implements org.osrs.api.wrappers.MouseTracker{

	@BField
	public int index;
	@BGetter
	@Override
	public int index(){return index;}
	@BFunction
	@Override
	public void setIndex(int i) {
		index=i;
	}
	@BField
	public long[] timestamps;
	@BGetter
	@Override
	public long[] timestamps(){return timestamps;}
	@BFunction
	@Override
	public void setTimestamp(int index, long stamp) {
		timestamps[index]=stamp;
	}
	@BField
	public java.lang.Object objectLock;
	@BGetter
	@Override
	public java.lang.Object objectLock(){return objectLock;}
	@BField
	public boolean isRunning;
	@BGetter
	@Override
	public boolean isRunning(){return isRunning;}
	@BFunction
	@Override
	public void setIsRunning(boolean flag) {
		isRunning=flag;
	}
	@BField
	public int[] trackedX;
	@BGetter
	@Override
	public int[] trackedX(){return trackedX;}
	@BFunction
	@Override
	public void setTrackedX(int index, int x) {
		trackedX[index]=x;
	}
	@BField
	public int[] trackedY;
	@BGetter
	@Override
	public int[] trackedY(){return trackedY;}
	@BFunction
	@Override
	public void setTrackedY(int index, int y) {
		trackedY[index]=y;
	}
}