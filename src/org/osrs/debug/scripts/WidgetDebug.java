package org.osrs.debug.scripts;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import org.osrs.api.methods.Camera;
import org.osrs.api.objects.RSInterface;
import org.osrs.api.objects.RSWidget;
import org.osrs.api.wrappers.Client;
import org.osrs.api.wrappers.Widget;
import org.osrs.util.ScriptDebugFrame;
import org.osrs.util.ScriptDef;

public class WidgetDebug extends ScriptDef{
	public WidgetDebugger debugger = null;
	public RSWidget interfaceToDisplay=null;
	@Override
	public void run() {
		debugger = new WidgetDebugger();
		debugger.setVisible(true);
		while(true){
			sleep(100);
		}
	}
	@Override
	public Graphics paint(Graphics g){
		int x=5;
		int y=45;
		if(interfaceToDisplay!=null){
			g.drawRect(interfaceToDisplay.getAbsoluteX(), interfaceToDisplay.getAbsoluteY(), interfaceToDisplay.getWidth(), interfaceToDisplay.getHeight());
		}
		return g;
	}
	@Override
	public void stopScript(){
		debugger.setVisible(false);
		debugger.dispose();
	}
	private class WidgetDebugger extends javax.swing.JFrame {
			private static final long serialVersionUID = 1L;
			public WidgetDebugger() {
				initComponents();
			}
			private void initComponents() {

				jLabel1 = new javax.swing.JLabel();
				jLabel5 = new javax.swing.JLabel();
				jButton3 = new javax.swing.JButton();
				jScrollPane1 = new javax.swing.JScrollPane();
				root = new DataNode("Runescape Widgets");
				treeModel = new DefaultTreeModel(root);
				createClassNodes();
				jTree1 = new javax.swing.JTree(root);
				jTree1.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
				jScrollPane2 = new javax.swing.JScrollPane();
				selectedInterfaceInformation = new javax.swing.JTextArea();
				selectedInterfaceInformation.setLineWrap(true);
				selectedInterfaceInformation.setWrapStyleWord(true);
				setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);

				jLabel1.setFont(new java.awt.Font("Trebuchet MS", 1, 14));
				jLabel1.setText("Marneus901's Widget Debugger");

				jLabel5.setText("Information");

				jButton3.setText("Refresh");
				jButton3.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						jButton3ActionPerformed(evt);
					}
				});

				jTree1.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
					public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
						jTree1ValueChanged(evt);
					}
				});
				jScrollPane1.setViewportView(jTree1);

				selectedInterfaceInformation.setColumns(20);
				selectedInterfaceInformation.setRows(5);
				jScrollPane2.setViewportView(selectedInterfaceInformation);

				javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
				getContentPane().setLayout(layout);
				layout.setHorizontalGroup(
						layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(layout.createSequentialGroup()
												.addComponent(jLabel1)
												.addGap(18, 18, 18)
												.addComponent(jButton3)
												.addContainerGap(243, Short.MAX_VALUE))
												.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
														.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
														.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																.addComponent(jLabel5)
																.addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE))
																.addContainerGap())))
						);
				layout.setVerticalGroup(
						layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jLabel1)
										.addComponent(jButton3))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(layout.createSequentialGroup()
														.addComponent(jLabel5)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE))
														.addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE))
														.addContainerGap())
						);

				pack();
			}
			private javax.swing.JButton jButton3;
			private javax.swing.JLabel jLabel1;
			private javax.swing.JLabel jLabel5;
			private javax.swing.JScrollPane jScrollPane1;
			private javax.swing.JScrollPane jScrollPane2;
			private javax.swing.JTree jTree1;
			private javax.swing.JTextArea selectedInterfaceInformation;
			private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
				jTree1.clearSelection();
				root = new DataNode("Runescape Widgets");
				for(RSInterface i : methods.widgets.getAll()){
					if(i!=null){
						DataNode n = new DataNode(""+i.index, i);
						boolean add=false;
						for(RSWidget ic : i.getChildren()){
							if(ic!=null && ic.isDisplayed()){
								DataNode n2 = new DataNode(""+ic.getIndex(), ic);
								for(RSWidget ic2 : ic.getChildren()){
									if(ic2!=null)
										n2.addChild(new DataNode(""+ic2.getIndex(), ic2));
								}
								add=true;
								n.addChild(n2);
							}
						}
						if(add)
							root.addChild(n);
					}
				}
				if(root.getChildCount()<1)
					root.addChild(new DataNode("No Interfaces Found - Possible Invalid Hooks"));
				treeModel = new DefaultTreeModel(root);
				jTree1.setModel(treeModel);
				selectedInterfaceInformation.setText("");
				interfaceToDisplay=null;
			}                                        

			private void jTree1ValueChanged(javax.swing.event.TreeSelectionEvent evt) {
				if(jTree1.getSelectionPath()!=null){
					DataNode selected = (DataNode) jTree1.getSelectionPath().getLastPathComponent();
					RSWidget ic = selected.currentInterfaceChild;
					if(ic!=null){
						selectedInterfaceInformation.setText("");
						selectedInterfaceInformation.append("Index : "+ic.getIndex()+"\n");
						selectedInterfaceInformation.append("Parent ID : "+ic.getParentID()+"\n");
						selectedInterfaceInformation.append("Absolute Location : "+ic.getAbsolutePosition()+"\n");
						selectedInterfaceInformation.append("Relative Location : "+ic.getRelativeX()+","+ic.getRelativeY()+"\n");
						selectedInterfaceInformation.append("Width : "+ic.getWidth()+"\n");
						selectedInterfaceInformation.append("Height : "+ic.getHeight()+"\n");
						selectedInterfaceInformation.append("Scroll : "+ic.getScrollX()+", "+ic.getScrollY()+"\n");
						Widget w = ic.getInternal();
						if(w!=null){
							selectedInterfaceInformation.append("Alpha : "+w.alpha()+"\n");
							selectedInterfaceInformation.append("Border Thickness : "+w.borderThickness()+"\n");
							selectedInterfaceInformation.append("Bounds Index : "+w.boundsIndex()+"\n");
							selectedInterfaceInformation.append("Changed Skills Count : "+w.changedSkillsCount()+"\n");
							selectedInterfaceInformation.append("Child Model Rotation Hash : "+w.childModelRotationHash()+"\n");
							selectedInterfaceInformation.append("Click Mask : "+w.clickMask()+"\n");
							selectedInterfaceInformation.append("Content Type : "+w.contentType()+"\n");
							selectedInterfaceInformation.append("Current Frame Index : "+w.currentFrameIndex()+"\n");
							selectedInterfaceInformation.append("Current Frame Length : "+w.currentFrameLength()+"\n");
							selectedInterfaceInformation.append("Default Margin : "+w.defaultMargin()+"\n");
							selectedInterfaceInformation.append("Disabled Animation ID : "+w.disabledAnimationID()+"\n");
							selectedInterfaceInformation.append("Disabled Color : "+w.disabledColor()+"\n");
							selectedInterfaceInformation.append("Disabled Hover Color : "+w.disabledHoverColor()+"\n");
							selectedInterfaceInformation.append("Disabled Media ID : "+w.disabledMediaID()+"\n");
							selectedInterfaceInformation.append("Disabled Media Type : "+w.disabledMediaType()+"\n");
							selectedInterfaceInformation.append("Disabled Text : "+w.disabledText()+"\n");
							selectedInterfaceInformation.append("Display Cycle : "+w.displayCycle()+" (Game Cycle : "+methods.game.getClient().gameCycle()+")\n");
							selectedInterfaceInformation.append("Drag Dead Time : "+w.dragDeadTime()+"\n");
							selectedInterfaceInformation.append("Drag Dead Zone : "+w.dragDeadZone()+"\n");
							selectedInterfaceInformation.append("Dynamic Height : "+w.dynamicHeight()+"\n");
							selectedInterfaceInformation.append("Dynamic Width : "+w.dynamicWidth()+"\n");
							selectedInterfaceInformation.append("Dynamic X : "+w.dynamicX()+"\n");
							selectedInterfaceInformation.append("Dynamic Y : "+w.dynamicY()+"\n");
							selectedInterfaceInformation.append("Enabled Animation ID : "+w.enabledAnimationID()+"\n");
							selectedInterfaceInformation.append("Enabled Color : "+w.enabledColor()+"\n");
							selectedInterfaceInformation.append("Enabled Hover Color : "+w.enabledHoverColor()+"\n");
							selectedInterfaceInformation.append("Enabled Media ID : "+w.enabledMediaID()+"\n");
							selectedInterfaceInformation.append("Enabled Media Type : "+w.enabledMediaType()+"\n");
							selectedInterfaceInformation.append("Enabled Sprite ID : "+w.enabledSpriteID()+"\n");
							selectedInterfaceInformation.append("Enabled Text : "+w.enabledText()+"\n");
							selectedInterfaceInformation.append("Font ID : "+w.fontID()+"\n");
							selectedInterfaceInformation.append("Height : "+w.height()+"\n");
							selectedInterfaceInformation.append("Horizontal Margin : "+w.horizontalMargin()+"\n");
							selectedInterfaceInformation.append("ID : "+w.id()+"\n");
							selectedInterfaceInformation.append("Index : "+w.index()+"\n");
							selectedInterfaceInformation.append("Item ID : "+w.itemID()+"\n");
							selectedInterfaceInformation.append("Item Quantity : "+w.itemQuantity()+"\n");
							selectedInterfaceInformation.append("Item Stack Type : "+w.itemStackType()+"\n");
							selectedInterfaceInformation.append("Line Width : "+w.lineWidth()+"\n");
							selectedInterfaceInformation.append("Menu Type : "+w.menuType()+"\n");
							selectedInterfaceInformation.append("Model Offset X : "+w.modelOffsetX()+"\n");
							selectedInterfaceInformation.append("Model Offset Y : "+w.modelOffsetY()+"\n");
							selectedInterfaceInformation.append("Model Zoom : "+w.modelZoom()+"\n");
							selectedInterfaceInformation.append("Old Height : "+w.oldHeight()+"\n");
							selectedInterfaceInformation.append("Old Width : "+w.oldWidth()+"\n");
							selectedInterfaceInformation.append("Opbase : "+w.opbase()+"\n");
							selectedInterfaceInformation.append("Original Height : "+w.originalHeight()+"\n");
							selectedInterfaceInformation.append("Original Width : "+w.originalWidth()+"\n");
							selectedInterfaceInformation.append("Original X : "+w.originalX()+"\n");
							selectedInterfaceInformation.append("Original Y : "+w.originalY()+"\n");
							selectedInterfaceInformation.append("Padding X : "+w.paddingX()+"\n");
							selectedInterfaceInformation.append("Padding Y : "+w.paddingY()+"\n");
							selectedInterfaceInformation.append("Parent ID : "+w.parentID()+"\n");
							selectedInterfaceInformation.append("Pending Item Trigger Count : "+w.pendingItemTriggerCount()+"\n");
							selectedInterfaceInformation.append("Pending Varbit Count : "+w.pendingVarbitCount()+"\n");
							selectedInterfaceInformation.append("Relative X : "+w.relativeX()+"\n");
							selectedInterfaceInformation.append("Relative Y : "+w.relativeY()+"\n");
							selectedInterfaceInformation.append("Rotation X : "+w.rotationX()+"\n");
							selectedInterfaceInformation.append("Rotation Y : "+w.rotationY()+"\n");
							selectedInterfaceInformation.append("Rotation Z : "+w.rotationZ()+"\n");
							selectedInterfaceInformation.append("Scroll Height : "+w.scrollHeight()+"\n");
							selectedInterfaceInformation.append("Scroll Width : "+w.scrollWidth()+"\n");
							selectedInterfaceInformation.append("Scroll X : "+w.scrollX()+"\n");
							selectedInterfaceInformation.append("Scroll Y : "+w.scrollY()+"\n");
							selectedInterfaceInformation.append("Shadow Color : "+w.shadowColor()+"\n");
							selectedInterfaceInformation.append("Spell Name : "+w.spellName()+"\n");
							selectedInterfaceInformation.append("Sprite ID : "+w.spriteID()+"\n");
							selectedInterfaceInformation.append("Target Verb : "+w.targetVerb()+"\n");
							selectedInterfaceInformation.append("Texture ID : "+w.textureID()+"\n");
							selectedInterfaceInformation.append("Tooltip : "+w.tooltip()+"\n");
							selectedInterfaceInformation.append("Type : "+w.type()+"\n");
							selectedInterfaceInformation.append("UID : "+w.uid()+"\n");
							selectedInterfaceInformation.append("Vertical Margin : "+w.verticalMargin()+"\n");
							selectedInterfaceInformation.append("Width : "+w.width()+"\n");
							selectedInterfaceInformation.append("Actions : "+Arrays.toString(w.actions())+"\n");
							selectedInterfaceInformation.append("Config Actions : "+Arrays.toString(w.configActions())+"\n");
							selectedInterfaceInformation.append("Drag Render Behavior : "+w.dragRenderBehavior()+"\n");
							selectedInterfaceInformation.append("Filled : "+w.filled()+"\n");
							selectedInterfaceInformation.append("Flipped Horizontally : "+w.flippedHorizontally()+"\n");
							selectedInterfaceInformation.append("Flipped Vertically : "+w.flippedVertically()+"\n");
							selectedInterfaceInformation.append("Has Listener : "+w.hasListener()+"\n");
							selectedInterfaceInformation.append("Has Script : "+w.hasScript()+"\n");
							selectedInterfaceInformation.append("Hovering : "+w.hovering()+"\n");
							selectedInterfaceInformation.append("Is Click Down : "+w.isClickDown()+"\n");
							selectedInterfaceInformation.append("Is Hidden : "+w.isHidden()+"\n");
							selectedInterfaceInformation.append("Item IDs : "+Arrays.toString(w.itemIDs())+"\n");
							selectedInterfaceInformation.append("Item Quantities : "+Arrays.toString(w.itemQuantities())+"\n");
							selectedInterfaceInformation.append("No Click Through : "+w.noClickThrough()+"\n");
							selectedInterfaceInformation.append("No Scroll Through : "+w.noScrollThrough()+"\n");
							selectedInterfaceInformation.append("Sprite IDs : "+Arrays.toString(w.spriteIDs())+"\n");
							selectedInterfaceInformation.append("Table Actions : "+Arrays.toString(w.tableActions())+"\n");
							selectedInterfaceInformation.append("Visible Cycle : "+w.visibleCycle()+" (WidgetCycle : "+methods.game.getClient().widgetVisibleCycle()+")\n");
							selectedInterfaceInformation.append("Widget Varps : "+Arrays.toString(w.widgetVarps())+"\n");
							selectedInterfaceInformation.append("X Sprites : "+Arrays.toString(w.xSprites())+"\n");
							selectedInterfaceInformation.append("Y Sprites : "+Arrays.toString(w.ySprites())+"\n");
							
							//selectedInterfaceInformation.append("ID : "+w+"\n");
							selectedInterfaceInformation.append("On Cam Finished Listeners : "+Arrays.toString(w.onCamFinishedListener())+"\n");
							selectedInterfaceInformation.append("On Chat Transmit Listeners : "+Arrays.toString(w.onChatTransmitListener())+"\n");
							selectedInterfaceInformation.append("On Clan Transmit Listeners : "+Arrays.toString(w.onClanTransmitListener())+"\n");
							selectedInterfaceInformation.append("On Click Listener : "+Arrays.toString(w.onClickListener())+"\n");
							selectedInterfaceInformation.append("On Dialog Abort Listener : "+Arrays.toString(w.onDialogAbortListener())+"\n");
							selectedInterfaceInformation.append("On Drag Complete Listener : "+Arrays.toString(w.onDragCompleteListener())+"\n");
							selectedInterfaceInformation.append("On Drag Listener : "+Arrays.toString(w.onDragListener())+"\n");
							selectedInterfaceInformation.append("On Dialog Abort Listener : "+Arrays.toString(w.onFriendTransmitListener())+"\n");
							selectedInterfaceInformation.append("On Dialog Abort Listener : "+Arrays.toString(w.onHoldListener())+"\n");
							selectedInterfaceInformation.append("On Dialog Abort Listener : "+Arrays.toString(w.onInvTransmitListener())+"\n");
							selectedInterfaceInformation.append("On Dialog Abort Listener : "+Arrays.toString(w.onKeyListener())+"\n");
							selectedInterfaceInformation.append("On Dialog Abort Listener : "+Arrays.toString(w.onLoadListener())+"\n");
							selectedInterfaceInformation.append("On Dialog Abort Listener : "+Arrays.toString(w.onMiscTransmitListener())+"\n");
							selectedInterfaceInformation.append("On Dialog Abort Listener : "+Arrays.toString(w.onMouseLeaveListener())+"\n");
							selectedInterfaceInformation.append("On Dialog Abort Listener : "+Arrays.toString(w.onOpListener())+"\n");
							selectedInterfaceInformation.append("On Dialog Abort Listener : "+Arrays.toString(w.onReleaseListener())+"\n");
							selectedInterfaceInformation.append("On Dialog Abort Listener : "+Arrays.toString(w.onResizeListener())+"\n");
							selectedInterfaceInformation.append("On Dialog Abort Listener : "+Arrays.toString(w.onScrollWheelListener())+"\n");
							selectedInterfaceInformation.append("On Dialog Abort Listener : "+Arrays.toString(w.onStatTransmitListener())+"\n");
							selectedInterfaceInformation.append("On Dialog Abort Listener : "+Arrays.toString(w.onStockTransmitListener())+"\n");
							selectedInterfaceInformation.append("On Dialog Abort Listener : "+Arrays.toString(w.onSubChangeListener())+"\n");
							selectedInterfaceInformation.append("On Dialog Abort Listener : "+Arrays.toString(w.onTargetEnterListener())+"\n");
							selectedInterfaceInformation.append("On Dialog Abort Listener : "+Arrays.toString(w.onTargetLeaveListener())+"\n");
							selectedInterfaceInformation.append("On Dialog Abort Listener : "+Arrays.toString(w.onTimerListener())+"\n");
							selectedInterfaceInformation.append("On Dialog Abort Listener : "+Arrays.toString(w.onVarTransmitListener())+"\n");
							selectedInterfaceInformation.append("On Dialog Abort Listener : "+Arrays.toString(w.statTransmitTriggers())+"\n");
							selectedInterfaceInformation.append("On Dialog Abort Listener : "+Arrays.toString(w.varTransmitTriggers())+"\n");
							selectedInterfaceInformation.append("On Dialog Abort Listener : "+Arrays.toString(w.onDialogAbortListener())+"\n");
							selectedInterfaceInformation.append("On Dialog Abort Listener : "+Arrays.toString(w.onDialogAbortListener())+"\n");
						}
						selectedInterfaceInformation.setCaretPosition(0);
						interfaceToDisplay=ic;
					}
					else{
						selectedInterfaceInformation.setText("");
						interfaceToDisplay=null;
					}
				}
			}
			private DataNode root;
			private DefaultTreeModel treeModel;
			public class DataNode extends DefaultMutableTreeNode {
				private static final long serialVersionUID = 1L;
				private boolean areChildrenDefined = false;
				private int numChildren;
				public ArrayList<DataNode> childrenArray = new ArrayList<DataNode>();
				public String currentString;//Current name of class/field/method

				public RSInterface currentInterface;
				public RSWidget currentInterfaceChild;

				public DataNode(String s) {
					super(s);
					numChildren = 0;
					currentString=s;
				}
				public DataNode(String s, RSInterface i){
					super(s);
					numChildren=0;
					currentString=s;
					currentInterface=i;
				}
				public DataNode(String s, RSWidget i){
					super(s);
					numChildren=0;
					currentString=s;
					currentInterfaceChild=i;
				}
				@Override
				public boolean isLeaf() {
					return (currentInterfaceChild!=null && currentInterfaceChild.getChildren().length==0);
				}
				public int getNodeCount(){
					if(childrenArray.size()>0){
						int count=1;
						for(DataNode n : childrenArray)
							count+=n.getNodeCount();
						return count;
					}
					return 1;
				}
				@Override
				public int getChildCount() {
					if (!areChildrenDefined)
						return 0;
					return numChildren;
				}
				public DataNode getChildByText(String s){
					for(DataNode n : childrenArray)
						if(n.toString().equals(s))
							return n;
					return null;
				}
				public void removeChildren(){
					childrenArray=new ArrayList<DataNode>();
					numChildren=0;
					removeAllChildren();
					areChildrenDefined=false;
				}
				public void addChild(DataNode d){
					areChildrenDefined=true;
					add(d);
					childrenArray.add(d);
					numChildren++;	
				}
				public String toString() {
					return currentString;
				}
			}

			public void createClassNodes(){
				root.setAllowsChildren(true);
				for(RSInterface i : methods.widgets.getAll()){
					if(i!=null){
						DataNode n = new DataNode(""+i.index, i);
						boolean add=false;
						for(RSWidget ic : i.getChildren()){
							if(ic!=null && ic.isDisplayed()){
								DataNode n2 = new DataNode(""+ic.getIndex(), ic);
								for(RSWidget ic2 : ic.getChildren()){
									if(ic2!=null)
										n2.addChild(new DataNode(""+ic2.getIndex(), ic2));
								}
								add=true;
								n.addChild(n2);
							}
						}
						if(add)
							root.addChild(n);
					}
				}
				treeModel.reload(root);
			}
		}
}
