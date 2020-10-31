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
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import org.osrs.api.methods.MethodContext;
import org.osrs.api.wrappers.Client;
import org.osrs.injection.ClassResolver;
import org.osrs.loader.RSClassLoader;
import org.pf.reflect.Modifiers;

public class ReflectionAnalyzer extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener{
	private static final long serialVersionUID = 1L;
	private String currentSearch="";
	private Point currentMouseLocation = new Point(-1, -1);
	private MethodContext context= null;
	private HashMap<String, Object> multipliers = new HashMap<String, Object>();
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
	
	public ReflectionAnalyzer(MethodContext context){
		this.context = context;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
		this.addKeyListener(this);
		//36 items shown at a time
	}
	private ArrayList<Field> runtimeFields = new ArrayList<Field>();
	@Override
	public void setVisible(boolean b){
		RSClassLoader loader = Data.clientClassloaders.get(context.botInstance);
		Object[] keys = loader.classes.keySet().toArray();
		for(Object key : keys){
			Class<?> clazz = loader.classes.get(key);
			if(clazz!=null){
				for(Field f : clazz.getDeclaredFields()){
					if(Modifier.isStatic(f.getModifiers())){
						runtimeFields.add(f);
					}
				}
			}
		}
		maxItemScroll = runtimeFields.size()-36;
		if(maxItemScroll<0)
			maxItemScroll=0;
		double ratio = runtimeFields.size()/36.0;
		if(ratio>1.0){
			scrollbarHeight = (int)(540/ratio);
			maxPixelScroll = 540-scrollbarHeight;
			scrollPixelRatio = scrollbarHeight/36.0;//pixels per item
			if(scrollPixelRatio<1.0){
				pixelPerScroll=1;
				itemPerScroll=(int) Math.ceil(1.0/scrollPixelRatio);
			}
		}
		else{
			scrollbarHeight = 540;
		}

		System.out.println("Fields:"+runtimeFields.size()+" MaxPixelScroll:"+maxPixelScroll+" PixelRatio:"+scrollPixelRatio);
		System.out.println("MaxItemScroll:"+maxItemScroll+" PixelPerScroll:"+pixelPerScroll+" ItemPerScroll:"+itemPerScroll);
		

		super.setVisible(b);
	}
	@Override
	public void paint(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.YELLOW);
		int y = 45;
		ClassResolver resolver = Data.clientModscripts.get(context.botInstance).resolver;
		g.drawString("Reflection Analyzer", 15, y);
		g.drawString("Current Search : "+currentSearch+"*", 150, y);
		y+=15;
		try{
			{//Draw scrollbar
				int y2 = y;
				int x2 = 460;
				g.drawRect(x2, y2, 15, 550);
				scrollbarX = x2+5;
				scrollbarY = y2+5+currentPixelScroll;
				g.fillRect(scrollbarX, scrollbarY, 5, scrollbarHeight);
				

				for(int i=0;i<36;++i){
					if(y2-15<lastClickY && y2>lastClickY &&
							lastClickX>20 && lastClickX<460){
						Color old = g.getColor();
						g.setColor(new Color(255, 255, 0, 50));
						g.fillRect(18, y2-16, 444, 17);
						g.setColor(old);
					}
					int idx = currentItemScroll+i;
					Field f = runtimeFields.get(idx);
					if(!f.isAccessible())
						f.setAccessible(true);
					try{
						Object data = f.get(context.botInstance);
						String refactoredField = resolver.getRefactoredFieldName(f.getDeclaringClass().getSimpleName(), f.getName(), Modifier.isStatic(f.getModifiers()));
						Object decodedData = data;
						if(data!=null){
							if(data instanceof Integer){
								Object multi = resolver.getFieldMultiplier("", refactoredField, Modifier.isStatic(f.getModifiers()));
								if(multi==null)
									multi = Data.intMultipliers.get(f.getDeclaringClass().getSimpleName()+"."+f.getName());
								if(multi!=null)
									decodedData = ((int)data) * ((int)multi);
							}
							else if(data instanceof Long){
								Object multi = resolver.getFieldMultiplier("", refactoredField, Modifier.isStatic(f.getModifiers()));
								if(multi==null)
									multi = Data.longMultipliers.get(f.getDeclaringClass().getSimpleName()+"."+f.getName());
								if(multi!=null)
									decodedData = ((long)data) * ((long)multi);
							}
						}
						g.drawString(refactoredField!=null?(f.getDeclaringClass().getSimpleName()+"."+refactoredField+" = "+decodedData):(f.getDeclaringClass().getSimpleName()+"."+f.getName()+" = "+data+((decodedData!=null&&(decodedData instanceof Integer||decodedData instanceof Long))?(" : "+decodedData):"")), 20, y2);
						if(y2-15<currentMouseLocation.y && y2>currentMouseLocation.y &&
								currentMouseLocation.x>20 && currentMouseLocation.x<460){
							g.drawRect(18, y2-16, 444, 17);
						}
					}
					catch(Exception e){
						g.drawString("\tERROR:"+e.getMessage(), 20, y2);
					}
					y2+=15;
				}
			}
		}
		catch(@SuppressWarnings("unused") Exception e){
			
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
					Field f = runtimeFields.get(idx);
					if(!f.getType().isPrimitive()){
						String type = f.getType().toString().replace("class ", "");
						System.out.println("DoubleClicked : "+f.getDeclaringClass().getSimpleName()+"."+f.getName()+" "+type+" "+f.getType().isPrimitive());
						new ReflectionAnalyzerSubFrame(context, f.get(context.botInstance)).setVisible(true);
					}
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
		if(arg0.getKeyCode()==KeyEvent.VK_PAGE_UP){
			int rot = (-30)*pixelPerScroll;
			System.out.println(currentPixelScroll+":"+rot);
			if(currentPixelScroll+rot<=0)
				currentPixelScroll=0;
			else if(currentPixelScroll+rot>=maxPixelScroll)
				currentPixelScroll=maxPixelScroll;
			else
				currentPixelScroll+=rot;
			rot = (-30)*itemPerScroll;
			System.out.println(currentItemScroll+":"+rot);
			if(currentItemScroll+rot<=0)
				currentItemScroll=0;
			else if(currentItemScroll+rot>=maxItemScroll)
				currentItemScroll=maxItemScroll;
			else
				currentItemScroll+=rot;
		} 
		else if(arg0.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
			int rot = (30)*pixelPerScroll;
			System.out.println(currentPixelScroll+":"+rot);
			if(currentPixelScroll+rot<=0)
				currentPixelScroll=0;
			else if(currentPixelScroll+rot>=maxPixelScroll)
				currentPixelScroll=maxPixelScroll;
			else
				currentPixelScroll+=rot;
			rot = (30)*itemPerScroll;
			System.out.println(currentItemScroll+":"+rot);
			if(currentItemScroll+rot<=0)
				currentItemScroll=0;
			else if(currentItemScroll+rot>=maxItemScroll)
				currentItemScroll=maxItemScroll;
			else
				currentItemScroll+=rot;
		}
		else if(arg0.getKeyCode()==KeyEvent.VK_HOME){
				currentPixelScroll=0;
				currentItemScroll=0;
		}
		else if(arg0.getKeyCode()==KeyEvent.VK_END){
			currentPixelScroll=maxPixelScroll;
			currentItemScroll=maxItemScroll;
		}
		else if(arg0.getKeyCode()==KeyEvent.VK_PERIOD && currentSearch.length()>1){
			if(!currentSearch.contains("."))
				currentSearch=currentSearch.concat(".");
		}
		else if(arg0.getKeyCode()==KeyEvent.VK_BACK_SPACE)
			currentSearch="";
		else{
			if(arg0.getKeyCode()==46){
				if(currentSearch.length()>1 && !currentSearch.contains("."))
					currentSearch=currentSearch.concat(".");
			}
			else{
				String s = KeyEvent.getKeyText(arg0.getKeyCode());
				System.out.println("Pressed : '"+arg0.getKeyChar()+"' : "+arg0.getKeyCode());
				if(s!=null && s.length()>0){
					currentSearch=currentSearch.concat(arg0.getKeyChar()+"");
				}
				else{
					
				}
				for(int i=0;i<runtimeFields.size();++i){
					Field f = runtimeFields.get(i);
					if(f!=null){
						String key = f.getDeclaringClass().getSimpleName()+"."+f.getName();
						if(currentSearch.equals(key)){
							currentItemScroll=i;
							currentPixelScroll=(currentItemScroll/itemPerScroll)*pixelPerScroll;
							currentSearch="";
							break;
						}
					}
				}
			}
		}
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
