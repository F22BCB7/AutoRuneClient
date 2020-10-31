package org.osrs.api.wrappers;

import java.awt.Graphics;

public interface Canvas{
	public java.awt.Component component();

	public void paint(Graphics g);

	void applySize(int w, int h);
}