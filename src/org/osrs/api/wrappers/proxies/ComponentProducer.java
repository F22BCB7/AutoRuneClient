package org.osrs.api.wrappers.proxies;

import org.osrs.api.methods.MethodContext;
import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BFunction;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import org.osrs.util.Data;
import org.osrs.util.ReflectionAnalyzerSubFrame;
import org.osrs.util.ScriptDef;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@BClass(name="ComponentProducer")
public class ComponentProducer extends Producer implements org.osrs.api.wrappers.ComponentProducer{
	@BMethod(name="drawGraphics")
	public void _drawGraphics(Graphics g, int a, int b, int c){}
	@BDetour
	@Override
	public void drawGraphics(Graphics g, int a, int b, int c){
		repaint();
		_drawGraphics(g, a, b, c);
	}
	@BMethod(name="drawGraphics")
	public void _drawGraphics(int a, int b, int c){}
	@BDetour
	@Override
	public void drawGraphics(int a, int b, int c){
		_drawGraphics(a, b, c);
	}
	@BMethod(name="drawClippedGraphics")
	public void _drawClippedGraphics(Graphics g, int a, int b, int c, int d, int e){}
	@BDetour
	@Override
	public void drawClippedGraphics(Graphics g, int a, int b, int c, int d, int e){
		repaint();
		_drawClippedGraphics(g, a, b, c, d, e);
	}
	@BMethod(name="drawClippedGraphics")
	public void _drawClippedGraphics(int a, int b, int c, int d, short e){}
	@BDetour
	@Override
	public void drawClippedGraphics(int a, int b, int c, int d, short e){
		_drawGraphics(a, b, c);
	}
	@BField
	public java.awt.Image image;
	@BGetter
	@Override
	public java.awt.Image image(){return image;}
	@BField
	public java.awt.Component component;
	@BGetter
	@Override
	public java.awt.Component component(){return component;}
	
	@BFunction
	public void repaint(){
		Graphics g = image.getGraphics();
		try{	
			int x=5;
			int y=15;
			g.setColor(Color.RED);
			g.drawString("AutoRune ALPHA [WIP]", x, y);
			y+=15;
			MethodContext context = Data.clientContexts.get(Client.clientInstance);
			if(context!=null){
				context.mouse.paint(g);
				if(Data.debugContext.cameraDebug.isAlive())
					Data.debugContext.cameraDebug.paint(g);
				if(Data.debugContext.locationDebug.isAlive())
					Data.debugContext.locationDebug.paint(g);
				if(Data.debugContext.menuDebug.isAlive())
					Data.debugContext.menuDebug.paint(g);
				if(Data.debugContext.mouseDebug.isAlive())
					Data.debugContext.mouseDebug.paint(g);
				if(Data.debugContext.inventoryDebug.isAlive())
					Data.debugContext.inventoryDebug.paint(g);
				if(Data.debugContext.npcDebug.isAlive())
					Data.debugContext.npcDebug.paint(g);
				if(Data.debugContext.groundItemDebug.isAlive())
					Data.debugContext.groundItemDebug.paint(g);
				if(Data.debugContext.tileDebug.isAlive())
					Data.debugContext.tileDebug.paint(g);
				if(Data.debugContext.widgetDebug.isAlive())
					Data.debugContext.widgetDebug.paint(g);
				if(Data.debugContext.boundaryObjectDebug.isAlive())
					Data.debugContext.boundaryObjectDebug.paint(g);
				if(Data.debugContext.floorDecorationDebug.isAlive())
					Data.debugContext.floorDecorationDebug.paint(g);
				if(Data.debugContext.interactableObjectDebug.isAlive())
					Data.debugContext.interactableObjectDebug.paint(g);
				if(Data.debugContext.wallDecorationDebug.isAlive())
					Data.debugContext.wallDecorationDebug.paint(g);
				ScriptDef script = Data.currentScripts.get(Client.clientInstance);
				if(script!=null){
					script.paint(g);
				}
				if(context.reflectionAnalyzer!=null){
					if(context.reflectionAnalyzer.isVisible())
						context.reflectionAnalyzer.repaint();
				}
				ReflectionAnalyzerSubFrame[] frames = context.reflectionAnalyzerSubFrames.toArray(new ReflectionAnalyzerSubFrame[]{});
				for(ReflectionAnalyzerSubFrame frame : frames){
					if(frame.isVisible())
						frame.repaint();
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		
		}
	}
}