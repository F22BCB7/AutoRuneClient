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

public abstract class FocusListener implements java.awt.event.FocusListener{
	public abstract void _focusGained(FocusEvent fe);
	public abstract void _focusLost(FocusEvent fe);
	
	public void focusGained(FocusEvent arg0) {
		_focusGained(arg0);
	}
	
	public void focusLost(FocusEvent arg0) {
		_focusLost(arg0);
	}
}
