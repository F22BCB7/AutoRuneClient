package org.osrs.input.mouse;

import java.applet.Applet;
import java.awt.Component;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

import org.osrs.api.methods.MethodContext;
import org.osrs.api.methods.MethodDefinition;

public class EventFactory extends MethodDefinition {
    private MethodContext botEnv;

    public EventFactory(MethodContext botEnv) {
        super(botEnv);
        this.botEnv = botEnv;
    }

    public MouseEvent createMoveMouse(int x, int y) {
        return new MouseEvent(getSource(), MouseEvent.MOUSE_MOVED, System.currentTimeMillis(), 0, x, y, 0, false, MouseEvent.NOBUTTON);
    }

    public Component getSource() {
    	try{
    		return ((Applet)methods.botInstance).getComponent(0);
    	}
    	catch(Exception e){
    		return (Component)methods.botInstance;
    	}
    }

    public MouseEvent createMousePress(int x, int y, boolean button) {
        return new MouseEvent(getSource(), MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(), 0, x, y, 1, false, button ? MouseEvent.BUTTON1 : MouseEvent.BUTTON3);
    }

    public MouseEvent createMouseRelease(int x, int y, boolean button) {
        return new MouseEvent(getSource(), MouseEvent.MOUSE_RELEASED, System.currentTimeMillis(), 0, x, y, 1, false, button ? MouseEvent.BUTTON1 : MouseEvent.BUTTON3);
    }

    public MouseEvent createMouseClicked(int x, int y, boolean button) {
        return new MouseEvent(getSource(), MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), 0, x, y, 1, false, button ? MouseEvent.BUTTON1 : MouseEvent.BUTTON3);
    }

    public MouseEvent createMouseDragged(int x, int y, boolean button) {
        return new MouseEvent(getSource(), MouseEvent.MOUSE_DRAGGED, System.currentTimeMillis(), 0, x, y, 1, false, button ? MouseEvent.BUTTON1 : MouseEvent.BUTTON3);  
    }
}
