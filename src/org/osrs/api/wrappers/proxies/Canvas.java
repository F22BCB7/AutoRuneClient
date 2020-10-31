package org.osrs.api.wrappers.proxies;

import org.osrs.api.methods.MethodContext;
import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BFunction;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BVar;
import org.osrs.util.Data;
import org.osrs.util.ReflectionAnalyzerSubFrame;
import org.osrs.util.ScriptDef;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;

@BClass(name="Canvas")
public class Canvas extends java.awt.Canvas implements org.osrs.api.wrappers.Canvas{

	@BField
	public java.awt.Component component;
	@BGetter
	@Override
	public java.awt.Component component(){return component;}

	@BVar
	public BufferedImage gameImage = new BufferedImage(1000, 700, BufferedImage.TYPE_INT_RGB);

	@BMethod(name="_paint")
	public void _paint(Graphics g){}
	@BDetour
	@Override
	public void paint(Graphics g){
        if (gameImage == null || gameImage.getWidth() != getWidth() || gameImage.getHeight() != getHeight()) {
            gameImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        }
        _paint(g);
	}
	@BFunction
	@Override
	public void applySize(int w, int h){
		this.setSize(w, h);
		gameImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	}
}