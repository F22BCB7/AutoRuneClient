package org.osrs.injection.transforms;

import java.util.ListIterator;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class MouseWheelListener {
	public ClassNode injectClass(ClassNode cn) {
		if(cn.access==49){
            for(MethodNode mn : cn.methods){
            	if(mn.name.startsWith("mouseWheel") && !mn.desc.equals("()I")){//Find Listener methods but filter MouseWheel
            		if(cn!=null && !cn.superName.equals("org/osrs/injection/wrappers/MouseWheelListener")){
            			cn.superName="org/osrs/injection/wrappers/MouseWheelListener";
            			//System.out.println("Changed superclass of : "+cn.name+" = "+cn.superName);
                        ListIterator<MethodNode> mli = cn.methods.listIterator();
                        while (mli.hasNext()) {
                            MethodNode node = mli.next();
                            if (node.name.equals("<init>")) {
                                ListIterator<AbstractInsnNode> ili = node.instructions.iterator();
                                while (ili.hasNext()) {
                                    AbstractInsnNode ain = ili.next();
                                    if (ain.getOpcode() == Opcodes.INVOKESPECIAL) {
                                        MethodInsnNode min = (MethodInsnNode) ain;
                                        if(min.owner.equals("java/lang/Object")){
                                        	//System.out.println("Changed initialization owner.");
	                                        min.owner = "org/osrs/injection/wrappers/MouseWheelListener";
	                                        break;
                                        }
                                    }
                                }
                                break;
                            }
                        }
            		}
            		mn.name="_"+mn.name;
            		//System.out.println("Hooked method : "+cn.name+"."+mn.name+mn.desc);
            	}
            }
		}
		return cn;
	}
}