package org.osrs.api.wrappers;

import java.awt.Graphics;

public interface ComponentProducer extends Producer{
	public java.awt.Image image();
	public java.awt.Component component();
	void drawGraphics(Graphics g, int a, int b, int c);
	void drawGraphics(int a, int b, int c);
	void drawClippedGraphics(Graphics g, int a, int b, int c, int d, int e);
	void drawClippedGraphics(int a, int b, int c, int d, short e);
}