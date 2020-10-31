package org.osrs.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;

public class ScriptDebug extends JPanel implements MouseListener, MouseMotionListener, KeyListener{
	private static final long serialVersionUID = 1L;
	private Point currentMouseLocation = new Point(-1, -1);
	private ScriptDef script = null;
	public ScriptDebug(ScriptDef script){
		this.script = script;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addKeyListener(this);
	}
	@Override
	public void paint(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.YELLOW);
		g.drawString("Debug", 15, 45);
		try{
			if(script!=null){
				script.paintDebug(g);
			}
		}
		catch(@SuppressWarnings("unused") Exception e){
			
		}
	}
	public void mouseClicked(MouseEvent arg0) {
		//Less java.awt.Point object instantiation
		currentMouseLocation.x=arg0.getX();
		currentMouseLocation.y=arg0.getY();
		
	}
	public void mouseEntered(MouseEvent arg0) {
		//Less java.awt.Point object instantiation
		currentMouseLocation.x=arg0.getX();
		currentMouseLocation.y=arg0.getY();	
	}
	public void mouseExited(MouseEvent arg0) {
		//Less java.awt.Point object instantiation
		currentMouseLocation.x=arg0.getX();
		currentMouseLocation.y=arg0.getY(); 
	}
	public void mousePressed(MouseEvent arg0) {
		//Less java.awt.Point object instantiation
		currentMouseLocation.x=arg0.getX();
		currentMouseLocation.y=arg0.getY();
	}
	public void mouseReleased(MouseEvent arg0) {
		//Less java.awt.Point object instantiation
		currentMouseLocation.x=arg0.getX();
		currentMouseLocation.y=arg0.getY();
	}
	public void mouseDragged(MouseEvent arg0) {
		//Less java.awt.Point object instantiation
		currentMouseLocation.x=arg0.getX();
		currentMouseLocation.y=arg0.getY();
	}
	public void mouseMoved(MouseEvent arg0) {
		//Less java.awt.Point object instantiation
		currentMouseLocation.x=arg0.getX();
		currentMouseLocation.y=arg0.getY();
	}
	public void keyPressed(KeyEvent arg0) {
	}
	public void keyReleased(KeyEvent arg0) {
	}
	public void keyTyped(KeyEvent arg0) {
	}
}
