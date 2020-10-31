package org.osrs.api.wrappers.proxies;

import java.applet.Applet;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.datatransfer.Clipboard;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;

import org.osrs.api.methods.MethodContext;
import org.osrs.api.wrappers.MouseWheelListener;
import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BGetterDetour;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BSetterDetour;
import org.osrs.injection.bytescript.BVar;
import org.osrs.util.Data;
import org.osrs.util.ReflectionAnalyzerSubFrame;
import org.osrs.util.ScriptDef;

@BClass(name = "GameShell")
public class GameShell extends Applet implements org.osrs.api.wrappers.GameShell{	
	/** Field hook mappings **/
	@BField
	public boolean dumpRequested;
	@BField
	public Clipboard clipboard;
	@BField
	public Canvas canvas;
	@BField
	public boolean clearScreen;
	@BField
	public MouseWheelListener mouseWheelListener;
	@BField
	public Frame frame;
	
	/** Field hook getter methods **/
	@BField
	public boolean awtFocus;//maps the hook
	@BGetter
	@Override
	public boolean awtFocus() {//gets the hooks value (debugging)
		return this.awtFocus;
	}
	@BGetterDetour
	public boolean get_awtFocus() {//whenever the client 'gets' the field
		//this method is called instead
		return true;
	}
	@BSetterDetour
	public void set_awtFocus(boolean a) {//whenever the client 'sets' the field
		//this method is called instead
		this.awtFocus = true;
	}
	@BGetter
	@Override
	public boolean dumpRequested() {
		return dumpRequested;
	}
	@BGetter
	@Override
	public Clipboard clipboard() {
		return clipboard;
	}
	@BGetter
	@Override
	public Canvas canvas() {
		return canvas;
	}
	@BGetter
	@Override
	public MouseWheelListener mouseWheelListener() {
		return mouseWheelListener;
	}
	@BGetter
	@Override
	public Frame frame() {
		return frame;
	}

	@BMethod(name="clearBackround")
	public void _clearBackround(int a){}
	@BDetour
	@Override
	public void clearBackround(int a){
		_clearBackround(a);
	}
	
	@BMethod(name="processGraphics")
	public void _processGraphics(int a){}
	@BDetour
	@Override
	public void processGraphics(int a){
		HashMap<org.osrs.api.wrappers.Widget, Integer> dticks = new HashMap<org.osrs.api.wrappers.Widget, Integer>();
		org.osrs.api.wrappers.Widget[][] widgets = Client.clientInstance.widgets();
		if(widgets!=null){
			for(int k=0;k<widgets.length;++k){
				org.osrs.api.wrappers.Widget[] widgets2 = widgets[k];
				if(widgets2!=null){
					for(int k2=0;k2<widgets2.length;++k2){
						org.osrs.api.wrappers.Widget widget = widgets2[k2];
						if(widget!=null){
							dticks.put(widget, widget.displayCycle());
							org.osrs.api.wrappers.Widget[] widgets3 = widget.children();
							if(widgets3!=null){
								for(int k3=0;k3<widgets3.length;++k3){
									org.osrs.api.wrappers.Widget widgetb = widgets3[k3];
									if(widgetb!=null){
										dticks.put(widgetb, widgetb.displayCycle());
									}
								}
							}
						}
					}
				}
			}
		}
		_processGraphics(a);
		if(widgets!=null){
			for(int k=0;k<widgets.length;++k){
				org.osrs.api.wrappers.Widget[] widgets2 = widgets[k];
				if(widgets2!=null){
					for(int k2=0;k2<widgets2.length;++k2){
						org.osrs.api.wrappers.Widget widget = widgets2[k2];
						if(widget!=null){
							int tick = dticks.containsKey(widget)?dticks.get(widget):-1;
							widget.setIsDisplayed(tick!=widget.displayCycle() && tick!=-1);
							org.osrs.api.wrappers.Widget[] widgets3 = widget.children();
							if(widgets3!=null){
								for(int k3=0;k3<widgets3.length;++k3){
									org.osrs.api.wrappers.Widget widgetb = widgets3[k3];
									if(widgetb!=null){
										tick = dticks.containsKey(widgetb)?dticks.get(widgetb):-1;
										widgetb.setIsDisplayed(tick!=widgetb.displayCycle() && tick!=-1);
									}
								}
							}
						}
					}
				}
			}
		}
	}
	/** Method hooks **/
	@BMethod(name="clearClipboardData")
	public void _clearClipboardData(String s, byte b){}
	@BDetour
	@Override
	public void clearClipboardData(String s, byte b){
		//Method hook detour; invoked prior to original method, and recieves params
		System.out.println("[clearClipboardData] "+s+" : "+b);
		_clearClipboardData(s, b);
	}
	@BMethod(name="requestShutdown")
	public void _requestShutdown(String s, byte b){}
	@BDetour
	@Override
	public void requestShutdown(String s, byte b){
		System.out.println("[requestShutdown] "+s+" : "+b);
		//_requestShutdown(s, b);
	}
	
	/** clientFocus update bypass **/
	@BMethod(name="_focusLost")
	public void _focusLost(FocusEvent event){return;}
	@BDetour
	@Override
	public void focusLost(FocusEvent event){
		awtFocus=true;
		//System.out.println("Blocked FocusLost Event! ["+awtFocus+"]"+"["+Client.clientFocused+"]"+"["+Client.awtFocusCheck+"]"+"["+Client.awtFocusCheck2+"]");
	}
	@BMethod(name="_focusGained")
	public void _focusGained(FocusEvent event){return;}
	@BDetour
	@Override
	public void focusGained(FocusEvent event){
		if(!awtFocus){
			_focusGained(event);
			//System.out.println("[focusGained] "+awtFocus);
		}
		else{
			awtFocus=true;
			//System.out.println("Blocked FocusGained Event! ["+awtFocus+"]"+"["+Client.clientFocused+"]"+"["+Client.awtFocusCheck+"]"+"["+Client.awtFocusCheck2+"]");
			//BLOCK DAT SHIT BOY
		}
	}
}
