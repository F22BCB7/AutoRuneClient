/******************************************************
* Created by Marneus901                                *
* © 2013 MarneusScripts.com                            *
* **************************************************** *
* Access to this source is unauthorized without prior  *
* authorization from its appropriate author(s).        *
* You are not permitted to release, nor distribute this* 
* work without appropriate author(s) authorization.    *
********************************************************/
package org.osrs.injection.wrappers;

import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import org.osrs.api.methods.MethodContext;
import org.osrs.api.wrappers.ClassInfo;
import org.osrs.api.wrappers.Client;
import org.osrs.util.Data;
import org.osrs.util.ScriptDef;

public abstract class KeyListener extends FocusListener implements java.awt.event.KeyListener{
	public abstract void _keyPressed(KeyEvent e);
	public abstract void _keyReleased(KeyEvent e);
	public abstract void _keyTyped(KeyEvent e);
	public boolean isShiftPressed = false;
	
	public void keyPressed(KeyEvent e) {
		if(e.isShiftDown() || e.getKeyCode()==KeyEvent.VK_SHIFT)
			isShiftPressed=true;
		_keyPressed(e);
	}
	
	public void keyReleased(KeyEvent e) {
		if(!e.isShiftDown() || e.getKeyCode()==KeyEvent.VK_SHIFT)
			isShiftPressed=false;
		_keyReleased(e);
	}
	
	public void keyTyped(KeyEvent e) {
		_keyTyped(e);
	}

    public void focusGained(FocusEvent focusEvent) {
    	/*System.out.println("Gained focus");
    	MethodContext context = Data.clientContexts.get((Client)this);
		if(context!=null){
			System.out.println("Got context!");
		}*/
    	_focusGained(focusEvent);
    }
    public void focusLost(FocusEvent focusEvent) {
    	/*System.out.println("Lost focus");
    	MethodContext context = Data.clientContexts.get((Client)this);
		if(context!=null){
			System.out.println("Got context!");
		}*/
    	_focusLost(focusEvent);
    }
}
