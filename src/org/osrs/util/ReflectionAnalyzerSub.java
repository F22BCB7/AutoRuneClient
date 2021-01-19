package org.osrs.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;

import org.osrs.api.methods.MethodContext;
import org.osrs.injection.ClassResolver;
import org.osrs.loader.RSClassLoader;

public class ReflectionAnalyzerSub extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener{
	private static final long serialVersionUID = 1L;
	private Object parentObject = null;
	private Point currentMouseLocation = new Point(-1, -1);
	private MethodContext context= null;
	private int scrollbarX = 0;
	private int scrollbarY = 0;
	private int scrollbarHeight = 0;
	private int currentPixelScroll=0;
	private ArrayList<Long> scrollTimestamps = new ArrayList<Long>();
	private int maxPixelScroll=0;
	private double scrollPixelRatio=0;
	private int currentItemScroll=0;
	private int maxItemScroll=0;
	private int itemPerScroll=0;
	private int pixelPerScroll=0;
	private int lastClickX=0;
	private int lastClickY=0;
	private long lastClickTime=0;
	private Point doubleClickedPoint = new Point(-1, -1);
	public ReflectionAnalyzerSub(MethodContext context, Object parentObj){
		this.context = context;
		this.parentObject=parentObj;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
		this.addKeyListener(this);
		//36 items shown at a time
	}
	private ArrayList<Object> runtimeObjects = new ArrayList<Object>();
	@Override
	public void setVisible(boolean b){
		Class<?> clazz = parentObject.getClass();
		if(clazz.isArray()){
			for(int i=0;i<Array.getLength(parentObject);++i){
				Object data = Array.get(parentObject, i);
				runtimeObjects.add(data);
			}
		}
		else{
			for(Class<?> cl=clazz;!cl.getName().equals("java.lang.Object");cl=cl.getSuperclass()){
				System.out.println("Checking class : "+cl.getName());
				for(Field f : cl.getDeclaredFields()){
					if(Modifier.isStatic(f.getModifiers()))
						continue;
					System.out.println("Loading refl field : "+f.getName());
					runtimeObjects.add(f);
				}
			}
		}
		System.out.println("Loaded "+runtimeObjects.size()+" objects.");
		maxItemScroll = runtimeObjects.size()-36;
		if(maxItemScroll<0)
			maxItemScroll=0;
		double ratio = runtimeObjects.size()/36.0;
		if(ratio>1.0){
			scrollbarHeight = (int)(540/ratio);
			maxPixelScroll = 540-scrollbarHeight;
			scrollPixelRatio = scrollbarHeight/36.0;//pixels per item
			if(scrollPixelRatio<1.0){
				pixelPerScroll=1;
				itemPerScroll=(int) Math.ceil(1.0/scrollPixelRatio);
			}
			else{
				itemPerScroll=1;
				pixelPerScroll = (int) Math.ceil(scrollPixelRatio);
			}
		}
		else{
			scrollbarHeight = 540;
		}

		System.out.println("Fields:"+runtimeObjects.size()+" MaxPixelScroll:"+maxPixelScroll+" PixelRatio:"+scrollPixelRatio);
		System.out.println("MaxItemScroll:"+maxItemScroll+" PixelPerScroll:"+pixelPerScroll+" ItemPerScroll:"+itemPerScroll);
		

		super.setVisible(b);
	}
	@Override
	public void paint(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.YELLOW);
		int y = 45;
		boolean isArray = parentObject.getClass().isArray();
		ClassResolver resolver = Data.clientModscripts.get(context.botInstance).resolver;
		String refactoredClass = resolver.getRefactoredClassName(parentObject.getClass().getSimpleName());
		g.drawString("Reflection Analyzer : "+parentObject+" "+(refactoredClass!=null?(!parentObject.getClass().isArray()?"("+refactoredClass+")":""):""), 15, y);
		y+=15;
		try{
			{//Draw scrollbar
				int y2 = y;
				int x2 = this.getSize().width-30;
				g.drawRect(x2, y2, 15, this.getSize().height-150);
				scrollbarX = x2+5;
				scrollbarY = y2+5+currentPixelScroll;
				g.fillRect(scrollbarX, scrollbarY, 5, scrollbarHeight);
				

				for(int i=0;i<((int)((this.getSize().height-150)/15));++i){
					try{
						if(y2-15<lastClickY && y2>lastClickY &&
								lastClickX>20 && lastClickX<scrollbarX){
							Color old = g.getColor();
							g.setColor(new Color(255, 255, 0, 50));
							g.fillRect(18, y2-16, 444, 17);
							g.setColor(old);
						}
						int idx = currentItemScroll+i;
						if(idx>=runtimeObjects.size())
							break;
						Object obj = runtimeObjects.get(idx);
						if(obj instanceof Field){
							Field f = (Field)obj;
							if(!f.isAccessible())
								f.setAccessible(true);
							String refactoredField = resolver.getRefactoredFieldName(f.getDeclaringClass().getSimpleName(), f.getName(), Modifier.isStatic(f.getModifiers()));
							Object data = f.get(parentObject);
							Object decodedData = data;
							if(data!=null){
								if(data instanceof Integer){
									Object multi=resolver.getFieldMultiplier(refactoredClass, refactoredField, Modifier.isStatic(f.getModifiers()));
									if(multi==null)
										multi = Data.intMultipliers.get(f.getDeclaringClass().getSimpleName()+"."+f.getName());
									if(multi!=null)
										decodedData = ((int)data) * ((int)multi);
								}
								else if(data instanceof Long){
									Object multi=resolver.getFieldMultiplier(refactoredClass, refactoredField, Modifier.isStatic(f.getModifiers()));
									if(multi==null)
										multi = Data.longMultipliers.get(f.getDeclaringClass().getSimpleName()+"."+f.getName());
									if(multi!=null)
										decodedData = ((long)data) * ((long)multi);
								}
							}
							g.drawString(("["+idx+"] ")+(refactoredField!=null?(f.getDeclaringClass().getSimpleName()+"."+refactoredField+" = "+decodedData):(f.getDeclaringClass().getSimpleName()+"."+f.getName()+" = "+data)), 20, y2);
						}
						else{
							g.drawString(("["+idx+"] ")+obj, 20, y2);
						}
						if(y2-15<currentMouseLocation.y && y2>currentMouseLocation.y &&
								currentMouseLocation.x>20 && currentMouseLocation.x<460){
							g.drawRect(18, y2-16, 444, 17);
						}
					}
					catch(Exception e){
						g.drawString("\tERROR:"+e.getMessage(), 20, y2);
						for(StackTraceElement trace : e.getStackTrace()){
							g.drawString(""+trace.toString(), 20, y2);
							y2+=15;
						}
					}
					y2+=15;
				}
			}
		}
		catch(@SuppressWarnings("unused") Exception e){
			int y2=460;
			g.drawString("\tERROR:"+e.getMessage(), 20, y2);
			y2+=15;
			for(StackTraceElement trace : e.getStackTrace()){
				g.drawString(""+trace.toString(), 20, y2);
				y2+=15;
			}
		}
	}
	public void doubleClicked(){
		try{
			System.out.println("DoubleClicked : "+doubleClickedPoint.toString());
			int y2 = 60;
			int x2 = 460;
			for(int i=0;i<36;++i){
				int idx = currentItemScroll+i;
				if(y2-15<doubleClickedPoint.y && y2>doubleClickedPoint.y &&
						doubleClickedPoint.x>20 && doubleClickedPoint.x<460){
					Object f = runtimeObjects.get(idx);
					if(f instanceof Field){
						new ReflectionAnalyzerSubFrame(context, ((Field)f).get(parentObject)).setVisible(true);
					}
					else{
						new ReflectionAnalyzerSubFrame(context, f).setVisible(true);
					}
					System.out.println("DoubleClicked : "+f);
				}
				y2+=15;
			}
		}
		catch(Exception e){
			
		}
	}
	public void mouseClicked(MouseEvent arg0) {
		//Less java.awt.Point object instantiation
		currentMouseLocation.x=arg0.getX();
		currentMouseLocation.y=arg0.getY();
		long curr = System.currentTimeMillis();
		if(curr-lastClickTime<1000){
			Point last = new Point(lastClickX, lastClickY);
			if(context.calculations.distanceBetween(currentMouseLocation, last)<10){
				doubleClickedPoint.x = (currentMouseLocation.x+last.x)/2;
				doubleClickedPoint.y = (currentMouseLocation.y+last.y)/2;
				doubleClicked();
			}
		}
		lastClickTime = System.currentTimeMillis();
		lastClickX = arg0.getX();
		lastClickY = arg0.getY();
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
	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		scrollTimestamps.add(arg0.getWhen());
		ArrayList<Long> remove = new ArrayList<Long>();
		for(long l : scrollTimestamps){
			if(System.currentTimeMillis()-l>1000)
				remove.add(l);
		}
		for(long l : remove)
			scrollTimestamps.remove(l);
		int rot = (arg0.getWheelRotation()*scrollTimestamps.size())*pixelPerScroll;
		System.out.println(currentPixelScroll+":"+rot);
		if(currentPixelScroll+rot<=0)
			currentPixelScroll=0;
		else if(currentPixelScroll+rot>=maxPixelScroll)
			currentPixelScroll=maxPixelScroll;
		else
			currentPixelScroll+=rot;
		rot = (arg0.getWheelRotation()*scrollTimestamps.size())*itemPerScroll;
		System.out.println(currentItemScroll+":"+rot);
		if(currentItemScroll+rot<=0)
			currentItemScroll=0;
		else if(currentItemScroll+rot>=maxItemScroll)
			currentItemScroll=maxItemScroll;
		else
			currentItemScroll+=rot;
	}
}
