package org.osrs.injection.transforms;

import java.util.ListIterator;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class FocusListener {
	public ClassNode injectClass(ClassNode cn) {
		if(cn.access==1057){
            for(MethodNode mn : cn.methods){
            	if(mn.name.startsWith("focus")){
            		if(!cn.superName.equals("org/osrs/injection/wrappers/FocusListener")){
            			cn.superName="org/osrs/injection/wrappers/FocusListener";
            			System.out.println("Changed superclass of : "+cn.name+" = "+cn.superName);
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
                                        	System.out.println("Changed initialization owner. ("+min.owner+")");
	                                        min.owner = "org/osrs/injection/wrappers/FocusListener";
	                                        break;
                                        }
                                    }
                                }
                                break;
                            }
                        }
            		}
            		mn.name="_"+mn.name;
            		System.out.println("Hooked method : "+cn.name+"."+mn.name+mn.desc);
            	}
            }
		}
		return cn;
	}
}
