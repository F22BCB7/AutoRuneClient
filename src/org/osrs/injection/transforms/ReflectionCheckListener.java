package org.osrs.injection.transforms;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.osrs.injection.FieldHook;
import org.osrs.injection.MethodHook;

public class ReflectionCheckListener {
	public void injectClass(ClassNode cn, MethodHook mh, FieldHook clientInstance) {
		for(MethodNode method : cn.methods){
			if(mh==null || clientInstance==null)
				return;
			if(cn.name.equals(mh.owner) && method.name.equals(mh.obfuscatedName) && method.desc.equals(mh.desc)){
				for(AbstractInsnNode insn : method.instructions.toArray()){
					if(insn.getOpcode()==Opcodes.CHECKCAST){
						AbstractInsnNode next = insn.getNext();
						if(next.getOpcode()==Opcodes.ASTORE){
							VarInsnNode var = (VarInsnNode)next;
		
							InsnList list = new InsnList();
							list.add(new FieldInsnNode(Opcodes.GETSTATIC, clientInstance.owner, clientInstance.obfuscatedName, "Lclient;"));
							list.add(new VarInsnNode(Opcodes.ALOAD, var.var));
		
							list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "org/osrs/injection/listeners/ReflectionCheckListener", "reflectionRequest", "(Ljava/lang/Object;Ljava/lang/Object;)V"));
							method.instructions.insert(next, list);
							System.out.println("Injected reflection check listener.");
						}
					}
				}
			}
		}
	}
}
