package org.osrs.loader;

import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.osrs.api.wrappers.proxies.GameShell;
import org.osrs.injection.ClassHook;
import org.osrs.injection.ClassResolver;
import org.osrs.injection.FieldHook;
import org.osrs.injection.MethodHook;
import org.osrs.util.Data;

public class Modscript {
	public ClassResolver resolver = null;
	public boolean exists=false;
	public ArrayList<ClassHook> classHooks = new ArrayList<ClassHook>();
	public ArrayList<FieldHook> staticFields = new ArrayList<FieldHook>();
	public ArrayList<MethodHook> staticMethods = new ArrayList<MethodHook>();
	public int injectedClasses = 0;
	public int injectedGetters = 0;
	public Modscript(){
		File file = new File("modscript.ms");
		if(file.exists() && !file.isDirectory()){
			//check hashcode in-case of needed update
			//if all uptodate
			exists=true;
		}
		else{
			System.out.println("Need to download modscript from server!");

			//download from web server

			//check if new download file exists locally
			//if(newFileExists)exists=true;

			System.exit(0);//remove when server is usable
		}
	}
	public void loadModscript(){
		if(!exists){
			System.out.println("No modscript data found! Unable to load.");
			System.exit(0);
		}
		int fields=0, methods=0, classes=0, revision=-1;
		try{
			BufferedReader br = null;
			String currLine;
			ClassHook currentClass = null;
			br = new BufferedReader(new FileReader("modscript.ms"));
			while ((currLine = br.readLine()) != null) {
				if(currLine.contains("{multipliers}")){
					while ((currLine = br.readLine()) != null) {
						String[] info = currLine.split(":");
						if(info[1].equals("int")){
							Data.intMultipliers.put(info[0], Integer.parseInt(info[2]));
						}
						else if(info[1].equals("long")){
							Data.longMultipliers.put(info[0], Long.parseLong(info[2]));
						}
					}
					break;
				}
				else if (currLine.contains("class_hook")) {
					//class_hook{name=xxx:interface_name=LinkedListNode}
					String data = currLine.replace("class_hook{name=", "").replace("interface_name=", "").replace("}", "");
					String[] classContainerInformation = data.split(":");
					currentClass = new ClassHook(classContainerInformation[0],
							classContainerInformation[1]);
					classHooks.add(currentClass);
					classes++;
				}
				else if (currLine.contains("field_hook")) {
					String fieldData = "";
					if(currLine.contains("intmulti=")){
						fieldData = currLine.replace("field_hook{owner=", "").replace("getter_name=", "").
								replace("name=", "").replace("desc=", "").replace("intmulti=", "").replace("}", "");
						String[] fieldHookInformation = fieldData.split(":");  
						currentClass.addField(fieldHookInformation[0],
								fieldHookInformation[1],
								fieldHookInformation[2],
								fieldHookInformation[3],
								Integer.parseInt(fieldHookInformation[4]));     
						fields++;               	
					}
					else if(currLine.contains("longmulti=")){
						fieldData = currLine.replace("field_hook{owner=", "").replace("getter_name=", "").
								replace("name=", "").replace("desc=", "").replace("longmulti=", "").replace("}", "");
						String[] fieldHookInformation = fieldData.split(":");  
						currentClass.addField(fieldHookInformation[0],
								fieldHookInformation[1],
								fieldHookInformation[2],
								fieldHookInformation[3],
								Long.parseLong(fieldHookInformation[4]));
						fields++;
					}
					else{
						fieldData = currLine.replace("field_hook{owner=", "").replace("getter_name=", "").
								replace("name=", "").replace("desc=", "").replace("}", "");
						String[] fieldHookInformation = fieldData.split(":");  
						currentClass.addField(fieldHookInformation[0],
								fieldHookInformation[1],
								fieldHookInformation[2],
								fieldHookInformation[3],
								null);
						fields++;
					}
				}
				else if (currLine.contains("method_hook")) {
					String methodData = "";
					methodData = currLine.replace("method_hook{owner=", "").replace("rename=", "")
							.replace("name=", "").replace("desc=", "").replace("predicate=", "").replace("predicateType=", "");
					String[] mhi = methodData.split(":");
					Object pred = -1;
					if(mhi[5].equals("B"))
						pred=Byte.parseByte(mhi[4]);
					else if(mhi[5].equals("I"))
						pred=Integer.parseInt(mhi[4]);
					else if(mhi[5].equals("S"))
						pred=Short.parseShort(mhi[4]);
					currentClass.addMethod(mhi[0], mhi[1], mhi[2], mhi[3], pred);
					methods++;
				}
				else if (currLine.contains("client_revision")) {
					String revisionData = "";
					revisionData = currLine.replace("client_revision{", "").replace("}", "");
					revision = Integer.parseInt(revisionData);
					if(Data.jarRevision!=revision){
						System.err.println("Modscript file out of date!");
						System.exit(0);
					}
					System.out.println("Loading modscript for client r"+revisionData);
				}
			}
			System.out.println("Loaded modscript : r"+revision);
			System.out.println("Classes : "+classes);
			System.out.println("Fields : "+fields);
			System.out.println("Methods : "+methods);
			br.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		resolver = new ClassResolver(this);
	}
	public void runInjection(ArrayList<ClassNode> classNodes){
		try{
			//prep focuslistener
			String gameShellName = resolver.getObfuscatedClassName("GameShell");
			String mouseName = resolver.getObfuscatedClassName("MouseListener");
			String canvas = resolver.getObfuscatedClassName("Canvas");
			for(ClassNode node : classNodes){
				if(node.name.equals(gameShellName)){
					ArrayList<MethodNode> rename = new ArrayList<MethodNode>();
					for(MethodNode mn : node.methods){
						if(mn.name.equals("focusGained") || mn.name.equals("focusLost"))
							rename.add(mn);
					}
					for(MethodNode mn : rename){
						mn.name=mn.name+"0";
					}
				}
				if(node.name.equals(mouseName)){
					ArrayList<MethodNode> rename = new ArrayList<MethodNode>();
					for(MethodNode mn : node.methods){
						if(mn.name.startsWith("mouse"))
							rename.add(mn);
					}
					for(MethodNode mn : rename){
						mn.name=mn.name+"0";
					}
				}
				if(node.name.equals(canvas)){
					ArrayList<MethodNode> rename = new ArrayList<MethodNode>();
					for(MethodNode mn : node.methods){
						if(mn.name.equals("paint"))
							rename.add(mn);
					}
					for(MethodNode mn : rename){
						mn.name=mn.name+"0";
					}
				}
			}
			//Hotfix hooks
			for(ClassHook ch : classHooks){
				
				/** DO NOT REMOVE - part of FocusListener bypass**/
				if(ch.refactoredName.equals("GameShell")){
					ch.methodHooks.add(new MethodHook(ch.obfuscatedName, "focusLost0", "focusLost", "(Ljava/awt/event/FocusEvent;)V", -1));
					ch.methodHooks.add(new MethodHook(ch.obfuscatedName, "focusGained0", "focusGained", "(Ljava/awt/event/FocusEvent;)V", -1));
				}
				/*************************************************/
				if(ch.refactoredName.equals("MouseListener")){
					ch.methodHooks.add(new MethodHook(ch.obfuscatedName, "mouseDragged0", "mouseDragged", "(Ljava/awt/event/MouseEvent;)V", -1));
					ch.methodHooks.add(new MethodHook(ch.obfuscatedName, "mouseEntered0", "mouseEntered", "(Ljava/awt/event/MouseEvent;)V", -1));
					ch.methodHooks.add(new MethodHook(ch.obfuscatedName, "mouseExited0", "mouseExited", "(Ljava/awt/event/MouseEvent;)V", -1));
					ch.methodHooks.add(new MethodHook(ch.obfuscatedName, "mousePressed0", "mousePressed", "(Ljava/awt/event/MouseEvent;)V", -1));
					ch.methodHooks.add(new MethodHook(ch.obfuscatedName, "mouseReleased0", "mouseReleased", "(Ljava/awt/event/MouseEvent;)V", -1));
					ch.methodHooks.add(new MethodHook(ch.obfuscatedName, "mouseClicked0", "mouseClicked", "(Ljava/awt/event/MouseEvent;)V", -1));
					ch.methodHooks.add(new MethodHook(ch.obfuscatedName, "mouseMoved0", "mouseMoved", "(Ljava/awt/event/MouseEvent;)V", -1));
				}
				if(ch.refactoredName.equals("Canvas")){
					ch.methodHooks.add(new MethodHook(ch.obfuscatedName, "paint0", "paint", "(Ljava/awt/Graphics;)V", -1));
				}
			}
			System.out.println("Compiling mods...");
			Package pack = GameShell.class.getPackage();
			URL root = GameShell.class.getClassLoader().getResource(pack.getName().replace(".", "/"));
			//System.out.println("Root url : "+root.toString());
			
			//Load proxy classes to be injected into client
			ArrayList<ClassNode> proxyClasses = new ArrayList<ClassNode>();
			
			if(root.toString().startsWith("jar:file:")){//Loading via jar
				JarFile jar = new JarFile(root.toString().substring(10, root.toString().indexOf("!")));
				//System.out.println("Loaded root jar file!");
				Enumeration<?> en = jar.entries();
				while (en.hasMoreElements()) {
					JarEntry entry = (JarEntry) en.nextElement();
					if(!entry.getName().endsWith(".class"))
						continue;
					if(!entry.getName().startsWith("org/osrs/api/wrappers/proxies"))
						continue;
					//System.out.println("Attempting to load class : "+entry.getName());
					byte[] buffer = new byte[1024];
					int read;
					InputStream is = jar.getInputStream(entry);
					byte[] allByteData = new byte[0];
					while ((read = is.read(buffer)) != -1){
						byte[] tempBuff = new byte[read+allByteData.length];
						for(int i=0;i<allByteData.length;++i)
							tempBuff[i]=allByteData[i];
						for(int i=0;i<read;++i)
							tempBuff[i+allByteData.length]=buffer[i];
						allByteData=tempBuff;
					}
					ClassReader cr = new ClassReader(allByteData);
					ClassNode cn = new ClassNode();
					cr.accept(cn, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
					proxyClasses.add(cn);
				}
			}
			else{//Loading via IDE
				// Filter .class files.
				File[] files = new File(root.toURI()).listFiles(new FilenameFilter() {
				    public boolean accept(File dir, String name) {
				    	//System.out.println("dir : "+dir + " name : "+name);
				        return name.endsWith(".class");
				    }
				});
				System.out.println("Loaded "+files.length+" file mods...");
				for (File file : new File(root.getFile()).listFiles(new FilenameFilter(){public boolean accept(File dir, String name){return name.endsWith(".class");}})) {
					try{
					    InputStream in = new FileInputStream(file);
					    ClassNode cn = new ClassNode();
					    ClassReader reader = new ClassReader(in);
					    reader.accept(cn, ClassReader.EXPAND_FRAMES);
					    proxyClasses.add(cn);
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}
			}
			System.out.println("Loaded "+proxyClasses.size()+" proxy classes...");
			ArrayList<String> injected = new ArrayList<String>();
			int subclassed=0, resolvedInsn=0, fieldGetters=0, newFields=0, newMethods=0, methodDetours=0; 
			int lastPercentage=0;
			for(ClassNode source : proxyClasses){
				int percentage = (int)((subclassed*1D/proxyClasses.size()*1D)*100);
				if(percentage/10!=lastPercentage/10){
					lastPercentage=percentage;
					System.out.println(percentage+"%");
				}
				String sourceName = getAnnotation(source);
				String targetName = resolver.getObfuscatedClassName(sourceName);
				if(targetName==null){
					System.out.println("Missing target class name for : "+sourceName+" : "+source.name);
					continue;
				}
				ClassHook ch = resolver.getClassHook(sourceName);
				if(ch!=null)
					ch.hooked=true;
				ClassNode target = null;
				for(ClassNode cn : classNodes){
					if(!cn.name.equals(targetName))
						continue;
					target=cn;
				}
				if(target!=null){
					target.interfaces.add("org/osrs/api/wrappers/" + sourceName);
					subclassed++;
					//System.out.println("Subclassed "+target.name+" : org/osrs/api/wrappers/" + sourceName);
					for(FieldNode fn : source.fields){
						if(hasAnnotation(fn, "Lorg/osrs/injection/bytescript/BVar;")){
							target.fields.add(fn);
							newFields++;
							//System.out.println("Injected field "+source.name+"."+fn.name+" -> "+target.name+"."+fn.name);
						}
					}
					for(MethodNode nodeMethod : source.methods){
						if(hasAnnotation(nodeMethod, "Lorg/osrs/injection/bytescript/BFunction;")){
							for(AbstractInsnNode insn : nodeMethod.instructions.toArray()){
								if(insn instanceof FieldInsnNode){
									FieldInsnNode fin = (FieldInsnNode)insn;
									FieldInsnNode clone = (FieldInsnNode)fin.clone(null);
									String oldOwner = fin.owner.replace("org/osrs/api/wrappers/proxies/", "");
									String oldName = fin.name;
									String newOwner = resolver.getObfuscatedFieldOwner(oldOwner, oldName, (insn.getOpcode()==Opcodes.GETSTATIC||insn.getOpcode()==Opcodes.PUTSTATIC));
									if(newOwner!=null && !newOwner.equals("null"))
										clone.owner = newOwner;
									else{
										newOwner = resolver.getObfuscatedClassName(oldOwner);
										if(newOwner!=null && !newOwner.equals("null"))
											clone.owner = newOwner;
										else
											clone.owner = fin.owner;
									}
									String newName = resolver.getObfuscatedFieldName(oldOwner, oldName, (insn.getOpcode()==Opcodes.GETSTATIC||insn.getOpcode()==Opcodes.PUTSTATIC));
									if(newName!=null && !newName.equals("null"))
										clone.name = newName;
									else
										clone.name = oldName;
									String oldDesc = fin.desc.replace("org/osrs/api/wrappers/proxies/", "");
									String newDesc = oldDesc.startsWith("L")?resolver.getObfuscatedType(oldDesc):oldDesc;
									if(newDesc!=null && !newDesc.equals("null"))
										clone.desc = newDesc;
									else
										clone.desc = oldDesc;
									FieldHook fh = resolver.getFieldHook(oldOwner, oldName, (insn.getOpcode()==Opcodes.GETSTATIC||insn.getOpcode()==Opcodes.PUTSTATIC));
									if(fh!=null){
										clone.owner=fh.owner;
										clone.name=fh.obfuscatedName;
										clone.desc=fh.dataType;
									}
									nodeMethod.instructions.set(insn, clone);
									resolvedInsn++;
									//System.out.println("Resolved FieldInsn : "+oldOwner+"."+oldName+" "+oldDesc+" : "+clone.owner+"."+clone.name+" "+clone.desc);
								}
								else if(insn instanceof MethodInsnNode){
									MethodInsnNode min = (MethodInsnNode)insn;
									MethodInsnNode clone = (MethodInsnNode)min.clone(null);
									String oldOwner = min.owner.replace("org/osrs/api/wrappers/proxies/", "");
									String oldName = min.name;
									String oldDesc = min.desc;
									String newOwner = resolver.getObfuscatedMethodOwner(oldOwner, oldName.replace("_", ""), oldDesc, (insn.getOpcode()==Opcodes.INVOKESTATIC));
									if(newOwner!=null && !newOwner.equals("null"))
										clone.owner = newOwner;
									else{
										newOwner = resolver.getObfuscatedClassName(oldOwner);
										if(newOwner!=null && !newOwner.equals("null"))
											clone.owner = newOwner;
										else
											clone.owner = min.owner;
									}
									String newDesc = resolver.getObfuscatedDesc(oldDesc);
									if(newDesc!=null && !newDesc.equals("null"))
										clone.desc = newDesc;
									else
										clone.desc = oldDesc;
									String newName = resolver.getObfuscatedMethodName(oldOwner, oldName.replace("_", ""), oldDesc, (insn.getOpcode()==Opcodes.INVOKESTATIC));
									if(newName==null && oldOwner.equals("Client"))//static hooks
										newName = resolver.getObfuscatedMethodName(oldOwner, oldName.replace("_", ""), oldDesc, true);
									if(newName!=null && !newName.equals("null"))
										clone.name = newName;
									else
										clone.name = oldName;
									nodeMethod.instructions.set(insn, clone);
									resolvedInsn++;
									//System.out.println("Resolved MethodInsn : "+oldOwner+"."+oldName+" "+oldDesc+" : "+clone.owner+"."+clone.name+" "+clone.desc);
								}
							}
							String obfClass = targetName;
							String obfMethod = resolver.getObfuscatedMethodName(sourceName, nodeMethod.name, nodeMethod.desc, nodeMethod.isStatic());
							String obfDesc = resolver.getObfuscatedDesc(nodeMethod.desc);
							for(ClassNode cn : classNodes){
								for(MethodNode mn : cn.methods){
									for(AbstractInsnNode insn : mn.instructions.toArray()){
										if(insn instanceof MethodInsnNode){
											MethodInsnNode min = (MethodInsnNode)insn;
											if(min.owner.equals(obfClass) && min.name.equals(obfMethod) && min.desc.equals(obfDesc)){
												min.name = nodeMethod.name;
												min.desc = nodeMethod.desc;
											}
										}
									}
								}
							}
							target.methods.add(nodeMethod);
							newMethods++;
							//System.out.println("Injected method "+sourceName+"."+nodeMethod.name+nodeMethod.desc+" -> "+target.name+"."+nodeMethod.name+nodeMethod.desc);
						}
						else if(hasAnnotation(nodeMethod, "Lorg/osrs/injection/bytescript/BDetour;")){
							String obfClass = resolver.getObfuscatedMethodOwner(sourceName, nodeMethod.name, nodeMethod.desc, nodeMethod.isStatic());
							String obfMethod = resolver.getObfuscatedMethodName(sourceName, nodeMethod.name, nodeMethod.desc, nodeMethod.isStatic());
							String obfDesc = resolver.getObfuscatedDesc(nodeMethod.desc);
							MethodHook mh = resolver.getMethodHook(sourceName, nodeMethod.name, obfDesc, nodeMethod.isStatic());
							if(mh==null){
								System.out.println("Failed to find method hook : "+sourceName+"."+nodeMethod.name+nodeMethod.desc+":"+nodeMethod.isStatic());
								continue;
							}
							else
								mh.hooked=true;
							//System.out.println("Detouring Method Invokes : "+sourceName+"."+nodeMethod.name+nodeMethod.desc+" -> "+obfClass+"."+obfMethod+obfDesc);
							for(ClassNode cn : classNodes){
								for(MethodNode mn : cn.methods){
									if(isBytescript(mn) ||
											mn.name.equals("<init>"))
										continue;
									for(AbstractInsnNode insn : mn.instructions.toArray()){
										if(insn instanceof MethodInsnNode){
											MethodInsnNode min = (MethodInsnNode)insn;
											if(min.owner.equals(mh.owner) && min.name.equals(mh.obfuscatedName) && min.desc.equals(mh.desc)){
												//System.out.println("Detoured method invoke : "+min.owner+"."+min.name+min.desc+" -> "+((sourceName.equals("Client"))?"client":min.owner)+"."+nodeMethod.name+nodeMethod.desc);
												if(sourceName.equals("Client"))
													min.owner = "client";
												min.name = nodeMethod.name;
												min.desc = nodeMethod.desc;
											}
											else{//check subclasses
												ClassNode owner = null;
												for(ClassNode cn2 : classNodes){
													if(cn2.superName.equals(obfClass)){
														owner=cn2;
														break;
													}
												}
												if(owner!=null){
													if(min.owner.equals(owner.name) && min.name.equals(mh.obfuscatedName) && min.desc.equals(mh.desc)){
														//System.out.println("Detoured method invoke : "+min.owner+"."+min.name+min.desc+" -> "+((sourceName.equals("Client"))?"client":min.owner)+"."+nodeMethod.name+nodeMethod.desc);
														if(sourceName.equals("Client"))
															min.owner = "client";
														min.name = nodeMethod.name;
														min.desc = nodeMethod.desc;
													}
												}
											}
										}
									}
								}
							}
							for(AbstractInsnNode insn : nodeMethod.instructions.toArray()){
								if(insn instanceof FieldInsnNode){
									FieldInsnNode fin = (FieldInsnNode)insn;
									FieldInsnNode clone = (FieldInsnNode)fin.clone(null);
									String oldOwner = fin.owner.replace("org/osrs/api/wrappers/proxies/", "");
									String oldName = fin.name;
									FieldHook fh = resolver.getFieldHook(oldOwner, oldName, (insn.getOpcode()==Opcodes.GETSTATIC||insn.getOpcode()==Opcodes.PUTSTATIC));
									String newOwner = resolver.getObfuscatedFieldOwner(oldOwner, oldName, (insn.getOpcode()==Opcodes.GETSTATIC||insn.getOpcode()==Opcodes.PUTSTATIC));
									if(newOwner!=null && !newOwner.equals("null"))
										clone.owner = newOwner;
									else{
										newOwner = resolver.getObfuscatedClassName(oldOwner);
										if(newOwner!=null && !newOwner.equals("null"))
											clone.owner = newOwner;
										else
											clone.owner = fin.owner;
									}
									String newName = resolver.getObfuscatedFieldName(oldOwner, oldName, (insn.getOpcode()==Opcodes.GETSTATIC||insn.getOpcode()==Opcodes.PUTSTATIC));
									if(newName!=null && !newName.equals("null"))
										clone.name = newName;
									else
										clone.name = oldName;
									String oldDesc = fin.desc.replace("org/osrs/api/wrappers/proxies/", "");
									String newDesc = null;
									if(fh!=null)
										newDesc = fh.dataType;
									else{
										newDesc = oldDesc.startsWith("L")?resolver.getObfuscatedType(oldDesc):oldDesc;
									}
									if(newDesc!=null && !newDesc.equals("null"))
										clone.desc = newDesc;
									else
										clone.desc = oldDesc;
									nodeMethod.instructions.set(insn, clone);
									resolvedInsn++;
									//System.out.println("Resolved FieldInsn : "+oldOwner+"."+oldName+" "+oldDesc+" : "+clone.owner+"."+clone.name+" "+clone.desc);
								}
								else if(insn instanceof MethodInsnNode){
									MethodInsnNode min = (MethodInsnNode)insn;
									MethodInsnNode clone = (MethodInsnNode)min.clone(null);
									String oldOwner = min.owner.replace("org/osrs/api/wrappers/proxies/", "");
									String oldName = min.name;
									String oldDesc = min.desc;
									String newOwner = resolver.getObfuscatedMethodOwner(oldOwner, oldName.replace("_", ""), oldDesc, (insn.getOpcode()==Opcodes.INVOKESTATIC));
									if(newOwner!=null && !newOwner.equals("null"))
										clone.owner = newOwner;
									else{
										newOwner = resolver.getObfuscatedClassName(oldOwner);
										if(newOwner!=null && !newOwner.equals("null"))
											clone.owner = newOwner;
										else
											clone.owner = min.owner;
									}
									String newDesc = resolver.getObfuscatedMethodDesc(oldOwner, oldName.replace("_", ""), oldDesc, (insn.getOpcode()==Opcodes.INVOKESTATIC));
									if(newDesc!=null && !newDesc.equals("null"))
										clone.desc = newDesc;
									else{
										clone.desc = oldDesc;
									}
									String newName = resolver.getObfuscatedMethodName(oldOwner, oldName.replace("_", ""), oldDesc, (insn.getOpcode()==Opcodes.INVOKESTATIC));
									if(newName==null && oldOwner.equals("Client"))//static hooks
										newName = resolver.getObfuscatedMethodName(oldOwner, oldName.replace("_", ""), oldDesc, true);
									if(newName!=null && !newName.equals("null"))
										clone.name = newName;
									else
										clone.name = oldName;
									nodeMethod.instructions.set(insn, clone);
									resolvedInsn++;
									//System.out.println("Resolved MethodInsn : "+oldOwner+"."+oldName+" "+oldDesc+" : "+clone.owner+"."+clone.name+" "+clone.desc);
								}
							}
							target.methods.add(nodeMethod);
							methodDetours++;
							//System.out.println("Injected method detour "+sourceName+"."+nodeMethod.name+nodeMethod.desc+" -> "+target.name+"."+nodeMethod.name+nodeMethod.desc);
						}
						else if(hasAnnotation(nodeMethod, "Lorg/osrs/injection/bytescript/BGetterDetour;")){
							String refName = nodeMethod.name.replace("get_", "");
							String refOwner = source.name.replace("org/osrs/api/wrappers/proxies/", "");
							String refType = resolver.getObfuscatedFieldDesc(refOwner, refName, nodeMethod.isStatic());
							FieldHook fh = resolver.getFieldHook(refOwner, refName, nodeMethod.isStatic());
							if(fh==null){
								System.out.println("Failed to find field hook : "+refOwner+"."+refName);
							}
							else{
								//System.out.println("Trying getter detour : "+source.name+" "+refType);
								nodeMethod.desc = nodeMethod.desc.replaceAll("proxies/", "");
								for(AbstractInsnNode insn : nodeMethod.instructions.toArray()){
									if(insn instanceof FieldInsnNode){
										FieldInsnNode fin = (FieldInsnNode)insn;
										FieldInsnNode clone = (FieldInsnNode)fin.clone(null);
										String oldOwner = fin.owner.replace("org/osrs/api/wrappers/proxies/", "");
										String oldName = fin.name;
										String oldDesc = fin.desc.replace("org/osrs/api/wrappers/", "");
										String newOwner = resolver.getObfuscatedFieldOwner(oldOwner, oldName, (insn.getOpcode()==Opcodes.GETSTATIC||insn.getOpcode()==Opcodes.PUTSTATIC));
										if(newOwner!=null && !newOwner.equals("null")){
											clone.owner = newOwner;
										}
										else{
											newOwner = resolver.getObfuscatedClassName(oldOwner);
											if(newOwner!=null && !newOwner.equals("null"))
												clone.owner = newOwner;
											else
												clone.owner = fin.owner;
										}
										String newName = resolver.getObfuscatedFieldName(oldOwner, oldName, (insn.getOpcode()==Opcodes.GETSTATIC||insn.getOpcode()==Opcodes.PUTSTATIC));
										clone.name = oldName;
										if(newName!=null)
											clone.name = newName;
										String newDesc = resolver.getObfuscatedFieldDesc(oldOwner, oldName, (insn.getOpcode()==Opcodes.GETSTATIC||insn.getOpcode()==Opcodes.PUTSTATIC));
										clone.desc = oldDesc;
										if(newDesc!=null)
											clone.desc = newDesc;
										nodeMethod.instructions.set(insn, clone);
										resolvedInsn++;
										Object multiplier = resolver.getFieldMultiplier(newOwner, newName, (insn.getOpcode()==Opcodes.GETSTATIC||insn.getOpcode()==Opcodes.PUTSTATIC));
										if(multiplier!=null){
											if(oldDesc.equals("I")){
												if(((Integer)multiplier)!=0){
													LdcInsnNode ldc = new LdcInsnNode(multiplier);
													nodeMethod.instructions.insert(clone, ldc);
													nodeMethod.instructions.insert(ldc, new InsnNode(Opcodes.IMUL));
													//System.out.println("Inserted multiplier : I-"+multiplier);
												}
											}
											if(oldDesc.equals("J")){
												if(((Long)multiplier)!=0){
													LdcInsnNode ldc = new LdcInsnNode(multiplier);
													nodeMethod.instructions.insert(clone, ldc);
													nodeMethod.instructions.insert(ldc, new InsnNode(Opcodes.LMUL));
													//System.out.println("Inserted multiplier : L-"+multiplier);
												}
											}
										}
										//System.out.println("Resolved FieldInsn : "+oldOwner+"."+oldName+" "+oldDesc+" : "+newOwner+"."+newName+" "+newDesc);
									}
								}
								target.methods.add(nodeMethod);
								for(ClassNode cn : classNodes){
									for(MethodNode mn : cn.methods){
										if(isBytescript(mn) ||
												mn.name.equals("<init>") ||
												mn.name.equals("<clinit>"))
											continue;
										ArrayList<AbstractInsnNode> replace = new ArrayList<AbstractInsnNode>();
										for(AbstractInsnNode insn : mn.instructions.toArray()){
											if(insn.getOpcode()==Opcodes.GETFIELD || insn.getOpcode()==Opcodes.GETSTATIC){
												FieldInsnNode fin = (FieldInsnNode)insn;
												if(fin.owner.equals(fh.owner) && fin.name.equals(fh.obfuscatedName)){
													replace.add(insn);
												}
											}
										}
										for(AbstractInsnNode insn : replace){
											mn.instructions.set(insn, new MethodInsnNode(nodeMethod.isStatic()?Opcodes.INVOKESTATIC:Opcodes.INVOKEVIRTUAL, target.name, nodeMethod.name, nodeMethod.desc, nodeMethod.isStatic()));
										}
										//System.out.println("Replaced "+replace.size()+" insns.");
									}
								}
							}
						}
						else if(hasAnnotation(nodeMethod, "Lorg/osrs/injection/bytescript/BSetterDetour;")){
							String refName = nodeMethod.name.replace("set_", "");
							String refOwner = source.name.replace("org/osrs/api/wrappers/proxies/", "");
							String refType = resolver.getObfuscatedFieldDesc(refOwner, refName, nodeMethod.isStatic());
							//System.out.println("Trying setter detour : "+refOwner+"."+refName);
							FieldHook fh = resolver.getFieldHook(refOwner, refName, nodeMethod.isStatic());
							if(fh==null){
								System.out.println("Failed to find field hook : "+refOwner+"."+refName);
							}
							else{
								for(AbstractInsnNode insn : nodeMethod.instructions.toArray()){
									if(insn instanceof FieldInsnNode){
										FieldInsnNode fin = (FieldInsnNode)insn;
										FieldInsnNode clone = (FieldInsnNode)fin.clone(null);
										String oldOwner = fin.owner.replace("org/osrs/api/wrappers/proxies/", "");
										String oldName = fin.name;
										String oldDesc = fin.desc.replace("org/osrs/api/wrappers/", "");
										String newOwner = resolver.getObfuscatedFieldOwner(oldOwner, oldName, (insn.getOpcode()==Opcodes.GETSTATIC||insn.getOpcode()==Opcodes.PUTSTATIC));
										if(newOwner!=null && !newOwner.equals("null")){
											clone.owner = newOwner;
										}
										else{
											newOwner = resolver.getObfuscatedClassName(oldOwner);
											if(newOwner!=null && !newOwner.equals("null"))
												clone.owner = newOwner;
											else
												clone.owner = fin.owner;
										}
										String newName = resolver.getObfuscatedFieldName(oldOwner, oldName, (insn.getOpcode()==Opcodes.GETSTATIC||insn.getOpcode()==Opcodes.PUTSTATIC));
										clone.name = oldName;
										if(newName!=null)
											clone.name = newName;
										String newDesc = resolver.getObfuscatedFieldDesc(oldOwner, oldName, (insn.getOpcode()==Opcodes.GETSTATIC||insn.getOpcode()==Opcodes.PUTSTATIC));
										clone.desc = oldDesc;
										if(newDesc!=null)
											clone.desc = newDesc;
										nodeMethod.instructions.set(insn, clone);
										resolvedInsn++;
										Object multiplier = resolver.getFieldMultiplier(newOwner, newName, (insn.getOpcode()==Opcodes.GETSTATIC||insn.getOpcode()==Opcodes.PUTSTATIC));
										if(multiplier!=null){
											if(oldDesc.equals("I")){
												if(((Integer)multiplier)!=0){
													LdcInsnNode ldc = new LdcInsnNode(multiplier);
													nodeMethod.instructions.insert(clone, ldc);
													nodeMethod.instructions.insert(ldc, new InsnNode(Opcodes.IMUL));
													//System.out.println("Inserted multiplier : I-"+multiplier);
												}
											}
											if(oldDesc.equals("J")){
												if(((Long)multiplier)!=0){
													LdcInsnNode ldc = new LdcInsnNode(multiplier);
													nodeMethod.instructions.insert(clone, ldc);
													nodeMethod.instructions.insert(ldc, new InsnNode(Opcodes.LMUL));
													//System.out.println("Inserted multiplier : L-"+multiplier);
												}
											}
										}
										//System.out.println("Resolved FieldInsn : "+oldOwner+"."+oldName+" "+oldDesc+" : "+newOwner+"."+newName+" "+newDesc);
									}
								}
								target.methods.add(nodeMethod);
								//System.out.println("Setter detour injected : "+refOwner+"."+refName);
								for(ClassNode cn : classNodes){
									for(MethodNode mn : cn.methods){
										if(isBytescript(mn) ||
												mn.name.equals("<init>") ||
												mn.name.equals("<clinit>"))
											continue;
										ArrayList<AbstractInsnNode> replace = new ArrayList<AbstractInsnNode>();
										for(AbstractInsnNode insn : mn.instructions.toArray()){
											if(insn.getOpcode()==Opcodes.PUTFIELD || insn.getOpcode()==Opcodes.PUTSTATIC){
												FieldInsnNode fin = (FieldInsnNode)insn;
												if(fin.owner.equals(fh.owner) && fin.name.equals(fh.obfuscatedName)){
													replace.add(insn);
												}
											}
										}
										for(AbstractInsnNode insn : replace){
											mn.instructions.set(insn, new MethodInsnNode(nodeMethod.isStatic()?Opcodes.INVOKESTATIC:Opcodes.INVOKEVIRTUAL, target.name, nodeMethod.name, nodeMethod.desc, nodeMethod.isStatic()));
										}
										//System.out.println("Replaced "+replace.size()+" insns.");
									}
								}
							}
						}
						else if(hasAnnotation(nodeMethod, "Lorg/osrs/injection/bytescript/BGetter;")){
							nodeMethod.desc = nodeMethod.desc.replaceAll("proxies/", "");
							for(AbstractInsnNode insn : nodeMethod.instructions.toArray()){
								if(insn instanceof FieldInsnNode){
									FieldInsnNode fin = (FieldInsnNode)insn;
									FieldInsnNode clone = (FieldInsnNode)fin.clone(null);
									String oldOwner = fin.owner.replace("org/osrs/api/wrappers/proxies/", "");
									String oldName = fin.name;
									FieldHook fh = resolver.getFieldHook(oldOwner, oldName, insn.getOpcode()==Opcodes.GETSTATIC||insn.getOpcode()==Opcodes.PUTSTATIC);
									if(fh==null){
										System.out.println("Failed to find field hook : "+oldOwner+"."+oldName);
									}
									else{
										fh.hooked=true;
									}
									String oldDesc = fin.desc.replace("org/osrs/api/wrappers/proxies/", "");
									String newOwner = resolver.getObfuscatedFieldOwner(oldOwner, oldName, (insn.getOpcode()==Opcodes.GETSTATIC||insn.getOpcode()==Opcodes.PUTSTATIC));
									if(newOwner!=null && !newOwner.equals("null")){
										clone.owner = newOwner;
									}
									else{
										newOwner = resolver.getObfuscatedClassName(oldOwner);
										if(newOwner!=null && !newOwner.equals("null"))
											clone.owner = newOwner;
										else
											clone.owner = fin.owner;
									}
									String newName = resolver.getObfuscatedFieldName(oldOwner, oldName, (insn.getOpcode()==Opcodes.GETSTATIC||insn.getOpcode()==Opcodes.PUTSTATIC));
									clone.name = oldName;
									if(newName!=null)
										clone.name = newName;
									String newDesc = resolver.getObfuscatedFieldDesc(oldOwner, oldName, (insn.getOpcode()==Opcodes.GETSTATIC||insn.getOpcode()==Opcodes.PUTSTATIC));
									clone.desc = oldDesc;
									if(newDesc!=null)
										clone.desc = newDesc;
									nodeMethod.instructions.set(insn, clone);
									resolvedInsn++;
									Object multiplier = resolver.getFieldMultiplier(oldOwner, oldName, (insn.getOpcode()==Opcodes.GETSTATIC||insn.getOpcode()==Opcodes.PUTSTATIC));
									if(multiplier!=null){
										if(oldDesc.equals("I")){
											if(((Integer)multiplier)!=0){
												LdcInsnNode ldc = new LdcInsnNode(multiplier);
												nodeMethod.instructions.insert(clone, ldc);
												nodeMethod.instructions.insert(ldc, new InsnNode(Opcodes.IMUL));
												//System.out.println("Inserted multiplier : I-"+multiplier);
											}
										}
										if(oldDesc.equals("J")){
											if(((Long)multiplier)!=0){
												LdcInsnNode ldc = new LdcInsnNode(multiplier);
												nodeMethod.instructions.insert(clone, ldc);
												nodeMethod.instructions.insert(ldc, new InsnNode(Opcodes.LMUL));
												//System.out.println("Inserted multiplier : L-"+multiplier);
											}
										}
									}
									//System.out.println("Resolved FieldInsn : "+oldOwner+"."+oldName+" "+oldDesc+" : "+newOwner+"."+newName+" "+newDesc);
								}
							}
							target.methods.add(nodeMethod);
							fieldGetters++;
							if(injected.contains(sourceName+"."+nodeMethod.name+nodeMethod.desc))
								System.out.println("Duplicate getter injected! "+sourceName+"."+nodeMethod.name+nodeMethod.desc);
							//System.out.println("Injected field getter "+sourceName+"."+nodeMethod.name+nodeMethod.desc+" -> "+target.name+"."+nodeMethod.name+nodeMethod.desc);
						}
					}
				}
			}
			/*for(ClassHook ch : this.classHooks){
				if(!ch.hooked){
					System.out.println("ClassHook not implemented! : "+ch.refactoredName);
				}
				for(FieldHook fh : ch.fieldHooks){
					if(!fh.hooked){
						System.out.println("FieldHook not implemented! : "+ch.refactoredName+"."+fh.refactoredName);
					}
				}
				for(MethodHook mh : ch.methodHooks){
					if(!mh.hooked){
						System.out.println("MethodHook not implemented! : "+ch.refactoredName+"."+mh.refactoredName+mh.desc);
					}
				}
			}
			for(FieldHook fh : this.staticFields){
				if(!fh.hooked){
					System.out.println("FieldHook not implemented! (static) : "+fh.refactoredName);
				}
			}
			for(MethodHook mh : this.staticMethods){
				if(!mh.hooked){
					System.out.println("MethodHook not implemented! (static) : "+mh.refactoredName+mh.desc);
				}
			}*/
			System.out.println("Done injecting proxy class data!");
			System.out.println("Classes : "+subclassed);
			System.out.println("Field Getters : "+fieldGetters);
			System.out.println("New Fields : "+newFields);
			System.out.println("New Methods : "+newMethods);
			System.out.println("Method Detours : "+methodDetours);
			System.out.println("Resolved Instructions : "+resolvedInsn);
			org.osrs.injection.transforms.MouseWheelListener mouseWheelListener = new org.osrs.injection.transforms.MouseWheelListener();
			org.osrs.injection.transforms.KeyListener keyListener = new org.osrs.injection.transforms.KeyListener();
			org.osrs.injection.transforms.ItemDefinitionListener itemDefListener = new org.osrs.injection.transforms.ItemDefinitionListener();
			org.osrs.injection.transforms.ObjectDefinitionListener objectDefListener = new org.osrs.injection.transforms.ObjectDefinitionListener();
			org.osrs.injection.transforms.ActorModels actorModelListener = new org.osrs.injection.transforms.ActorModels();
			org.osrs.injection.transforms.PacketListener packetListener = new org.osrs.injection.transforms.PacketListener();
			org.osrs.injection.transforms.ReflectionCheckListener reflectionListener = new org.osrs.injection.transforms.ReflectionCheckListener();
			for(ClassNode cn : classNodes){
				mouseWheelListener.injectClass(cn);
				keyListener.injectClass(cn);
				actorModelListener.injectClass(cn, resolver.getObfuscatedClassName("Model"), resolver.getFieldHook("Client", "clientInstance", true));
				itemDefListener.injectClass(cn, resolver.getObfuscatedClassName("ItemDefinition"), resolver.getFieldHook("Client", "clientInstance", true));
				objectDefListener.injectClass(cn, resolver.getObfuscatedClassName("ObjectDefinition"), resolver.getFieldHook("Client", "clientInstance", true));
				packetListener.injectClass(cn, resolver.getClassHook("PacketContext"), resolver.getFieldHook("Client", "clientInstance", true), resolver.getFieldHook("PacketContext", "idleReadPulses", false));
				reflectionListener.injectClass(cn, resolver.getMethodHook("STATICHOOKS", "encodeReflectionCheck", "", true), resolver.getFieldHook("Client", "clientInstance", true));
			}
			dumpClasses("injected.jar", classNodes.toArray(new ClassNode[]{}));
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public boolean containsAnnotation(MethodNode method, String annotation){
		if(method!=null){
			if(method.visibleAnnotations==null)
				return false;
			for(AnnotationNode an : method.visibleAnnotations){
				if(an.desc.contains(annotation))
					return true;
			}
		}
		return false;
	}
	public String getAnnotation(ClassNode clazz){
		if(clazz!=null && clazz.visibleAnnotations!=null){
			for(AnnotationNode an : clazz.visibleAnnotations){
				if(an.values.get(0).equals("name"))
					//System.out.println(clazz.name+":"+an.values.get(1).toString());
					return an.values.get(1).toString();
			}
		}
		return null;
	}
	public boolean hasAnnotation(FieldNode field, String annotation){
		if(field!=null){
			if(field.visibleAnnotations==null)
				return false;
			for(AnnotationNode an : field.visibleAnnotations){
				if(an.desc.equals(annotation))
					return true;
			}
		}
		return false;
	}
	public boolean hasAnnotation(MethodNode method, String annotation){
		if(method!=null){
			if(method.visibleAnnotations==null)
				return false;
			for(AnnotationNode an : method.visibleAnnotations){
				if(an.desc.equals(annotation))
					return true;
			}
		}
		return false;
	}
	public boolean isBytescript(MethodNode method){
		if(hasAnnotation(method, "Lorg/osrs/injection/bytescript/BMethod;") ||
				hasAnnotation(method, "Lorg/osrs/injection/bytescript/BDetour;") ||
				hasAnnotation(method, "Lorg/osrs/injection/bytescript/BFunction;") ||
				hasAnnotation(method, "Lorg/osrs/injection/bytescript/BGetter;") ||
				hasAnnotation(method, "Lorg/osrs/injection/bytescript/BGetterDetour;") ||
				hasAnnotation(method, "Lorg/osrs/injection/bytescript/BSetterDetour;"))
			return true;
		return false;
	}
	public boolean isBytescript(FieldNode field){
		if(hasAnnotation(field, "Lorg/osrs/injection/bytescript/BVar;") ||
				hasAnnotation(field, "Lorg/osrs/injection/bytescript/BField;"))
			return true;
		return false;
	}
	protected String[] parseArguments(String desc){
		ArrayList<String> args = new ArrayList<String>();
		desc = desc.substring(desc.indexOf("(")+1, desc.indexOf(")"));
		while(desc!=null && !desc.equals("") && desc.length()>0){
			if(desc.charAt(0)=='B' || desc.charAt(0)=='C' || desc.charAt(0)=='D' || desc.charAt(0)=='F' || desc.charAt(0)=='I' || desc.charAt(0)=='J' || desc.charAt(0)=='S' || desc.charAt(0)=='Z'){
				args.add(desc.charAt(0)+"");
				desc=desc.substring(1, desc.length());
			}
			else if(desc.startsWith("[")){
				String arg = "[";
				desc=desc.substring(1, desc.length());
				while(desc.startsWith("[")){
					arg+="[";
					desc=desc.substring(1, desc.length());
				}
				if(desc.charAt(0)=='B' || desc.charAt(0)=='C' || desc.charAt(0)=='D' || desc.charAt(0)=='F' || desc.charAt(0)=='I' || desc.charAt(0)=='J' || desc.charAt(0)=='S' || desc.charAt(0)=='Z'){
					args.add(arg+desc.charAt(0)+"");
					desc=desc.substring(1, desc.length());
				}
				else{
					args.add(arg+desc.substring(0, desc.indexOf(";")+1));
					desc=desc.substring(desc.indexOf(";")+1, desc.length());
				}
			}
			else{
				args.add(desc.substring(0, desc.indexOf(";")+1));
				desc=desc.substring(desc.indexOf(";")+1, desc.length());
			}
		}
		return args.toArray(new String[]{});
	}
	private static void dumpClasses(final String jarName, final ClassNode[] classes) {
		try {
			final File file = new File(jarName);
			final JarOutputStream out = new JarOutputStream(new FileOutputStream(file));
			for (final ClassNode node : classes) {
				final JarEntry entry = new JarEntry(node.name + ".class");
				out.putNextEntry(entry);
				final ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
				//System.out.println(node.name);
				node.accept(writer);
				out.write(writer.toByteArray());
			}
			out.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
}
