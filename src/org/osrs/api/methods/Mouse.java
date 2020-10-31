package org.osrs.api.methods;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import org.osrs.api.wrappers.Client;
import org.osrs.api.wrappers.MouseListener;
import org.osrs.input.MouseHandler;
import org.osrs.input.mouse.MouseHoverJob;
import org.osrs.input.mouse.MouseJob;
import org.osrs.input.mouse.MouseMoveListener;
import org.osrs.input.mouse.MouseTarget;

public class Mouse extends MethodDefinition{
	public MouseHandler mouseHandler = null;
	public Mouse(MethodContext context){
		super(context);
		mouseHandler = new MouseHandler(context);
	}	
	public static final int LEFT_BUTTON = MouseEvent.BUTTON1;
	public static final int MIDDLE_BUTTON = MouseEvent.BUTTON2;
	public static final int RIGHT_BUTTON = MouseEvent.BUTTON3;
	public static int getButtonModifiers(int button) throws IllegalArgumentException {
		switch (button) {
		case LEFT_BUTTON:
			return InputEvent.BUTTON1_MASK;
		case MIDDLE_BUTTON:
			return InputEvent.BUTTON2_MASK;
		case RIGHT_BUTTON:
			return InputEvent.BUTTON3_MASK;
		default:
			throw new IllegalArgumentException("Not a valid button choice.");
		}
	}    
	public void click(){
		mouseHandler.clickMouse(true);
	}
	public void click(int x, int y){
		MouseTarget mouseTarget = new MouseTarget() {
            public Point get() {
                return new Point(x, y);
            }
            public boolean isOver(int posX, int posY) {
                return posX > x-1 && posX < x+1 && posY > y-1 && posY < y+1;
            }
        };
        MouseHoverJob mouseHoverJob = mouseHandler.createMouseHoverJob(new MouseMoveListener() {
            int count = 0;
            public void onMouseOverTarget(MouseJob mouseJob) {
                count++;
                if(count > random(3, 16)){
                    mouseJob.stop();
                    return;
                }
            }

            public void onFinished(MouseJob mouseJob) {
            }
        }, mouseTarget);
        mouseHoverJob.start();
        mouseHoverJob.join();
        
        mouseHandler.clickMouse(true);
	}
	public void drag(int x, int y){
		//TODO
	}
	public Point getLocation(){
		Client client = methods.game.getClient();
		if(client!=null){
			MouseListener mouse = client.mouseListener();
			if(mouse!=null)
				return new Point(mouse.getX(), mouse.getY());
		}
		return new Point(-1, -1);
	}
	public Point getRealLocation(){
		Client client = methods.game.getClient();
		if(client!=null){
			MouseListener mouse = client.mouseListener();
			if(mouse!=null)
				return new Point(mouse.getRealX(), mouse.getRealY());
		}
		return new Point(-1, -1);
	}
	public double getSpeed(){
		return mouseHandler.getDefaultSpeed();
	}
	public Component getTarget(){
		return ((Applet)methods.botInstance).getComponent(0);
	}
	public boolean isPressed(){
		Client client = methods.game.getClient();
		if(client!=null){
			MouseListener mouse = client.mouseListener();
			if(mouse!=null)
				return mouse.isPressed();
		}
		return false;
	}
	public boolean isRealPressed(){
		Client client = methods.game.getClient();
		if(client!=null){
			MouseListener mouse = client.mouseListener();
			if(mouse!=null)
				return mouse.isRealPressed();
		}
		return false;
	}
	public void move(Point p1){
		mouseHandler.moveMouse(p1);
	}
	public void move(int x, int y){
		mouseHandler.moveMouse(x, y);
	}
	public void paint(Graphics g){
		if(isPressed())
			g.setColor(new Color(255, 127, 0, 155));
		else
			g.setColor(new Color(0, 255, 0, 155));
		Point last = getLocation();
		g.fillOval(last.x-10, last.y-3, 20, 6);
		g.fillOval(last.x-3, last.y-10, 6, 20);
		g.setColor(Color.CYAN);
	}
	public void press(int button){
		Point last = getLocation();
		press(last.x, last.y, button);
	}
	public void press(int x, int y, int button){
		Client client = methods.game.getClient();
		if(client!=null){
			MouseListener mouse = client.mouseListener();
			if(mouse!=null){
				int buttonModifiers = getButtonModifiers(button);
				Component target = getTarget();
				mouse.sendEvent(new MouseEvent(target, MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(), buttonModifiers, x, y, 1, false, button));
				for(int i=0;i<20;++i){
					if(isPressed())
						break;
					sleep(random(10, 20));
				}
			}
		}
	}
	public void release(int button){
		Point last = getLocation();
		release(last.x, last.y, button);
	}
	public void release(int x, int y, int button){
		Client client = methods.game.getClient();
		if(client!=null){
			MouseListener mouse = client.mouseListener();
			if(mouse!=null){
				int buttonModifiers = getButtonModifiers(button);
				Component target = getTarget();
				mouse.sendEvent(new MouseEvent(target, MouseEvent.MOUSE_RELEASED, System.currentTimeMillis(), buttonModifiers, x, y, 1, false, button));
				for(int i=0;i<20;++i){
					if(!isPressed())
						break;
					sleep(random(10, 20));
				}
				mouse.sendEvent(new MouseEvent(target, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), buttonModifiers, x, y, 1, false, button));
			}
		}
	}
	public boolean rightClick(){
		Client client = methods.game.getClient();
		if(client!=null){
			MouseListener mouse = client.mouseListener();
			if(mouse!=null){
				boolean ret = false;
				press(RIGHT_BUTTON);
				for(int i=0;i<20;++i){
					if(mouse.isPressed()){
						ret=true;
						break;
					}
				}
				release(RIGHT_BUTTON);
				return ret;
			}
		}
		return false;
	}
	/**
	 * Does a right-click, but invokes ignoreNextMenuOpenRequest mod
	 * Usefull for doAction emulating right-clicks.
	 * @return
	 */
	public boolean rightClickNoMenu(){
		Client client = methods.game.getClient();
		if(client!=null){
			MouseListener mouse = client.mouseListener();
			if(mouse!=null){
				methods.game.getClient().ignoreNextOpenMenuRequest();//ignores menu open
				boolean ret = false;
				press(RIGHT_BUTTON);
				for(int i=0;i<20;++i){
					if(mouse.isPressed()){
						ret=true;
						break;
					}
				}
				release(RIGHT_BUTTON);
				return ret;
			}
		}
		return false;
	}
	public void setSpeed(double speed){
		mouseHandler.setDefaultSpeed(speed);
	}
}
