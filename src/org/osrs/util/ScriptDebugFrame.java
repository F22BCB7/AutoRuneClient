package org.osrs.util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;

public class ScriptDebugFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	public static final Dimension MINIMUM_SIZE = new Dimension(800, 600);
	public ScriptDebug canvas = null;
	public ScriptDebugFrame(ScriptDef script){
		setSize(MINIMUM_SIZE);
		setMinimumSize(MINIMUM_SIZE);
		setLocationRelativeTo(null);
        setLayout(new BorderLayout());
		setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
		canvas = new ScriptDebug(script);
		this.addMouseListener(canvas);
		this.addMouseMotionListener(canvas);
		this.addKeyListener(canvas);
		add(canvas, BorderLayout.CENTER);
	}
}
