package org.osrs.api.wrappers;

public interface MouseTracker{
	public int index();
	public long[] timestamps();
	public java.lang.Object objectLock();
	public boolean isRunning();
	public int[] trackedX();
	public int[] trackedY();
	public void setIsRunning(boolean flag);
	public void setIndex(int i);
	public void setTimestamp(int index, long stamp);
	public void setTrackedX(int index, int x);
	public void setTrackedY(int index, int y);
}