package org.osrs.injection.transforms;

import java.util.ArrayList;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Wildcard;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.objectweb.asm.util.Printer;

public class ItemSprites {
	public void injectMethod(MethodNode method){
		System.out.println("Patching getItemSprite : "+method.name+method.desc);
		ArrayList<AbstractInsnNode> remove = new ArrayList<AbstractInsnNode>();
		for(AbstractInsnNode insn : method.instructions.toArray()){
			if(insn instanceof MethodInsnNode){
				MethodInsnNode invoke = (MethodInsnNode)insn;
				if(new Wildcard("(L*;J)V").matches(invoke.desc)){
					System.out.println("Found invoke : "+method.instructions.indexOf(insn)+" "+invoke.desc);
					AbstractInsnNode prev = insn.getPrevious();
					for(int i=0;i<20;++i){
						if(prev.getOpcode()==Opcodes.ILOAD){
							if(((VarInsnNode)prev).var==5){
								remove.add(prev);
								remove.add(prev.getNext());
								break;
							}
						}
						prev=prev.getPrevious();
					}
				}
			}
		}
		System.out.println("Removing "+remove.size()+" instructions");
		for(AbstractInsnNode insn : remove)
			method.instructions.remove(insn);
		method.visitEnd();
	}
}
