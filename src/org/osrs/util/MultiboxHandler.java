package org.osrs.util;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import org.osrs.api.wrappers.Client;

public class MultiboxHandler{
	public ArrayList<ScriptDef> scripts = new ArrayList<ScriptDef>();
	public void register(ScriptDef script){
		scripts.add(script);
	}
	public void subActions(Object client, int a, int b, int c, int d, String e, String f, int g, int h){
		if(!client.equals(Data.currentInstance))
			return;
		boolean check=false;
		for(ScriptDef script : scripts){
			if(script.methods.game.getClientInstance().equals(client))
				check=true;
		}
		if(check){
			for(ScriptDef script : scripts){
				if(script!=null && script.isAlive()){
					if(script.methods.botInstance.equals(client))
						continue;
					script.methods.game.overrideProcessAction(a, b, c, d, e, f, g, h);
				}
			}
		}
	}
	public void keyPressed(KeyEvent arg0) {
		for(ScriptDef script : scripts){
			if(script!=null && script.isAlive())
				script.methods.game.getKeyListener()._keyPressed(arg0);
		}
	}
	public void keyReleased(KeyEvent arg0) {
		for(ScriptDef script : scripts){
			if(script!=null && script.isAlive())
				script.methods.game.getKeyListener()._keyReleased(arg0);
		}
	}
	public void keyTyped(KeyEvent arg0) {
		for(ScriptDef script : scripts){
			if(script!=null && script.isAlive())
				script.methods.game.getKeyListener()._keyTyped(arg0);
		}
	}
	public void mouseClicked(MouseEvent arg0) {
		for(ScriptDef script : scripts){
			if(script!=null && script.isAlive())
				script.methods.game.getMouseListener().sendEvent(arg0);
		}
	}
	public void mouseEntered(MouseEvent arg0) {
		for(ScriptDef script : scripts){
			if(script!=null && script.isAlive())
				script.methods.game.getMouseListener().sendEvent(arg0);
		}
	}
	public void mouseExited(MouseEvent arg0) {
		for(ScriptDef script : scripts){
			if(script!=null && script.isAlive())
				script.methods.game.getMouseListener().sendEvent(arg0);
		}
	}
	public void mousePressed(MouseEvent arg0) {
		for(ScriptDef script : scripts){
			if(script!=null && script.isAlive())
				script.methods.game.getMouseListener().sendEvent(arg0);
		}
	}
	public void mouseReleased(MouseEvent arg0) {
		for(ScriptDef script : scripts){
			if(script!=null && script.isAlive())
				script.methods.game.getMouseListener().sendEvent(arg0);
		}
	}
	public void mouseDragged(MouseEvent arg0) {
		for(ScriptDef script : scripts){
			if(script!=null && script.isAlive())
				script.methods.game.getMouseListener().sendEvent(arg0);
		}
	}
	public void mouseMoved(MouseEvent arg0) {
		for(ScriptDef script : scripts){
			if(script!=null && script.isAlive())
				script.methods.game.getMouseListener().sendEvent(arg0);
		}
	}
}
