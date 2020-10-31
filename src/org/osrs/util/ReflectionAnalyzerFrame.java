package org.osrs.util;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import org.osrs.api.methods.MethodContext;
import org.osrs.loader.RSClassLoader;

public class ReflectionAnalyzerFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	public static final Dimension MINIMUM_SIZE = new Dimension(500, 700);
	public ReflectionAnalyzer canvas = null;
	public ReflectionAnalyzerFrame(MethodContext context){
		setSize(MINIMUM_SIZE);
		setMinimumSize(MINIMUM_SIZE);
		setLocationRelativeTo(null);
        setLayout(new BorderLayout());
		setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
		canvas = new ReflectionAnalyzer(context);
		this.addMouseListener(canvas);
		this.addMouseMotionListener(canvas);
		this.addKeyListener(canvas);
		add(canvas, BorderLayout.CENTER);
	}
	@Override
	public void setVisible(boolean b){
		canvas.setVisible(b);
		super.setVisible(b);
	}
}
