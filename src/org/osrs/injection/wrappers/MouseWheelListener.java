package org.osrs.injection.wrappers;

import java.awt.event.MouseWheelEvent;

import org.osrs.util.Data;

public abstract class MouseWheelListener implements java.awt.event.MouseWheelListener{
	private int clientRotation=0;
	private int realRotation=0;
	public abstract void _mouseWheelMoved(MouseWheelEvent e);
	@Override
	public void mouseWheelMoved(final MouseWheelEvent e) {
		//System.out.println(e.toString());
		try {
			realRotation=e.getWheelRotation();
			if(Data.currentInstance!=null){
				clientRotation=e.getWheelRotation();
				_mouseWheelMoved(e);
			}
		} catch (final AbstractMethodError ame) {
			ame.printStackTrace();
			// it might not be implemented!
		}
		e.consume();
	}
	public void sendEvent(MouseWheelEvent e){
		_mouseWheelMoved(e);
	}
	public int getClientRotation(){
		return clientRotation;
	}
	public int getRealRotation(){
		return realRotation;
	}
}
