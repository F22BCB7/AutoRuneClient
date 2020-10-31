package org.osrs.api.methods;

import java.applet.Applet;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import org.osrs.api.wrappers.Client;
import org.osrs.injection.wrappers.MouseWheelListener;

public class MouseWheel extends MethodDefinition{
	public MouseWheel(MethodContext context) {
		super(context);
	}
	public void rotate(int rotation){
		Client client = methods.game.getClient();
		if(client!=null){
			MouseWheelListener mouse = (MouseWheelListener) client.mouseWheelListener();
			if(mouse!=null){
				Component target = getTarget();
				Point mouseLoc = methods.mouse.getLocation();
				MouseWheelEvent me = new MouseWheelEvent(target, MouseEvent.MOUSE_WHEEL, System.currentTimeMillis(), 0, mouseLoc.x, mouseLoc.y, 0, false, MouseWheelEvent.WHEEL_UNIT_SCROLL, 3, rotation);
				mouse.mouseWheelMoved(me);
			}
			else{
				System.out.println("Null MouseWheelListener");
			}
		}
	}
	public Component getTarget(){
		return ((Applet)methods.botInstance).getComponent(0);
	}
}
