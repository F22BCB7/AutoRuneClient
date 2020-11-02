package org.osrs.injection.transforms;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class MinimapWalkCreation {
	public ClassNode injectClass(ClassNode cn, String[] methodOrder) {
		if(cn.name.equals("client")){
            for(MethodNode mn : cn.methods){
            	if(mn.name.startsWith("writeWalkTileMinimap")){
            		int currIndex = 0;
            		for(AbstractInsnNode insn : mn.instructions.toArray()){
            			if(insn instanceof MethodInsnNode){
            				MethodInsnNode min = (MethodInsnNode)insn;
            				if(min.name.equals("mockupWrite")){
            					String method = methodOrder[currIndex];
            					String owner = method.substring(0, method.indexOf("."));
            					String name = method.substring(method.indexOf(".")+1, method.indexOf("("));
            					String desc = method.substring(method.indexOf("("), method.indexOf(":"));
            					String newPredicate = method.substring(method.indexOf(":")+1, method.length());
            					AbstractInsnNode oldPredicate = insn.getPrevious();
            					mn.instructions.set(insn, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, owner, name, desc));
            					mn.instructions.set(oldPredicate, new LdcInsnNode(newPredicate));
            					currIndex++;
            				}
            			}
            		}
            	}
            }
		}
		return cn;
	}
}
