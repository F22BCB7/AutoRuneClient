/******************************************************
* Created by Marneus901                                *
* © 2013 MarneusScripts.com                            *
* **************************************************** *
* Access to this source is unauthorized without prior  *
* authorization from its appropriate author(s).        *
* You are not permitted to release, nor distribute this* 
* work without appropriate author(s) authorization.    *
********************************************************/
package org.osrs.injection.transforms;

import java.util.ListIterator;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class KeyListener{
	public ClassNode injectClass(ClassNode cn) {
		if(cn.access==49){
            for(MethodNode mn : cn.methods){
            	if(mn.name.startsWith("key") && !mn.name.contains("keyboard")){
            		if(!cn.superName.equals("org/osrs/injection/wrappers/KeyListener")){
            			cn.superName="org/osrs/injection/wrappers/KeyListener";
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
	                                        min.owner = "org/osrs/injection/wrappers/KeyListener";
	                                        break;
                                        }
                                    }
                                }
                                break;
                            }
                        }
            		}
            		mn.name="_"+mn.name;
            		//System.out.println("Hooked method : "+mn.name);
            	}
            }
		}
		return cn;
	}
}
