package org.osrs.api.wrappers;

import java.awt.Frame;
import java.awt.datatransfer.Clipboard;
import java.awt.event.FocusEvent;

public interface GameShell{
	public boolean awtFocus();
	public boolean dumpRequested();
	public Clipboard clipboard();
	public java.awt.Canvas canvas();
	public MouseWheelListener mouseWheelListener();
	public Frame frame();
	
	
	public void clearClipboardData(String s, byte b);
	public void focusGained(FocusEvent event);
	public void focusLost(FocusEvent event);
	public void requestShutdown(String s, byte b);
	public void processGraphics(byte a);
	public void clearBackround(int a);
}