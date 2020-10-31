package org.osrs.api.methods;

import java.applet.Applet;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Keyboard extends MethodDefinition{
	public Keyboard(MethodContext context){
		super(context);
	}
	private Component getTarget(){
		return ((Applet)methods.botInstance).getComponent(0);
	}
	public int getLocation(final char ch) {
		if (ch >= KeyEvent.VK_SHIFT && ch <= KeyEvent.VK_ALT) {
			return new Random().nextInt((KeyEvent.KEY_LOCATION_RIGHT + 1)-KeyEvent.KEY_LOCATION_LEFT)+KeyEvent.KEY_LOCATION_LEFT;
		}
		return KeyEvent.KEY_LOCATION_STANDARD;
	}
	public void pressKey(char s){
		int code = s;
		if (s >= 'a' && s <= 'z') {
			code -= 32;
		}
		Component keyboardTarget = getTarget();
		KeyEvent event = new KeyEvent(keyboardTarget, KeyEvent.KEY_PRESSED, 0, 0, code, s, getLocation(s));
		//keyboardTarget.dispatchEvent(event);
		methods.game.getKeyListener().keyPressed(event);
		event = new KeyEvent(keyboardTarget, KeyEvent.KEY_TYPED, 0, 0, KeyEvent.VK_UNDEFINED, s, 0);
		//keyboardTarget.dispatchEvent(event);
		methods.game.getKeyListener().keyTyped(event);
	}
	public void releaseKey(char s){
		int code = s;
		if (s >= 'a' && s <= 'z') {
			code -= 32;
		}
		Component keyboardTarget = getTarget();
		KeyEvent event = new KeyEvent(keyboardTarget, KeyEvent.KEY_RELEASED, 0, 0, code, s, getLocation(s));
		//keyboardTarget.dispatchEvent(event);
		methods.game.getKeyListener().keyReleased(event);
		
	}
	public void sendKey(char s){
		int code = s;
		if (s >= 'a' && s <= 'z') {
			code -= 32;
		}
		Component keyboardTarget = getTarget();
		KeyEvent event = new KeyEvent(keyboardTarget, KeyEvent.KEY_PRESSED, 0, 0, code, s, getLocation(s));
		//keyboardTarget.dispatchEvent(event);
		methods.game.getKeyListener().keyPressed(event);
		
		event = new KeyEvent(keyboardTarget, KeyEvent.KEY_TYPED, 0, 0, KeyEvent.VK_UNDEFINED, s, 0);
		//keyboardTarget.dispatchEvent(event);
		methods.game.getKeyListener().keyTyped(event);
		
		event = new KeyEvent(keyboardTarget, KeyEvent.KEY_RELEASED, 0, 0, code, s, getLocation(s));
		//keyboardTarget.dispatchEvent(event);
		methods.game.getKeyListener().keyReleased(event);
		
	}
	public void sendKeys(String str){
		for(int i=0;i<str.length();++i){
			sendKey(str.charAt(i));
			try{
				Thread.sleep(new Random().nextInt(100));
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}

