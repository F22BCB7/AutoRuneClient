package org.osrs.util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.lang.reflect.Field;

import javax.swing.JFrame;

import org.osrs.api.methods.MethodContext;
import org.osrs.loader.RSClassLoader;

public class ReflectionAnalyzerSubFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	public static final Dimension MINIMUM_SIZE = new Dimension(500, 700);
	public ReflectionAnalyzerSub canvas = null;
	public ReflectionAnalyzerSubFrame(MethodContext context, Object parent){
		setSize(MINIMUM_SIZE);
		setMinimumSize(MINIMUM_SIZE);
		setLocationRelativeTo(null);
        setLayout(new BorderLayout());
		setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
		canvas = new ReflectionAnalyzerSub(context, parent);
		this.addMouseListener(canvas);
		this.addMouseMotionListener(canvas);
		this.addKeyListener(canvas);
		add(canvas, BorderLayout.CENTER);
		context.reflectionAnalyzerSubFrames.add(this);
	}
	@Override
	public void setVisible(boolean b){
		canvas.setVisible(b);
		super.setVisible(b);
	}
}
