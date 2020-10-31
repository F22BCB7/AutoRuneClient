package org.osrs.injection.transforms;

import java.util.ArrayList;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.objectweb.asm.util.Printer;
import org.osrs.injection.FieldHook;

public class MessageListener {
	public void injectMethod(MethodNode mn, FieldHook clientInstance){
		System.out.println("Hooking addChatMessage : "+mn.name+mn.desc);
		int hookedIndex=-1;
		for(AbstractInsnNode ain : mn.instructions.toArray()) {
			if(ain.getOpcode()==Opcodes.INVOKEVIRTUAL && ((MethodInsnNode)ain).desc.contains("String")){
				AbstractInsnNode index = ain.getNext();
				hookedIndex=((VarInsnNode)index).var;
				System.out.println("Hooking index : "+hookedIndex);
				break;
			}
		}
		AbstractInsnNode invoke = null;
		for(AbstractInsnNode ain : mn.instructions.toArray()) {
			if(ain.getOpcode()==Opcodes.INVOKEVIRTUAL){
				MethodInsnNode m = (MethodInsnNode)ain;
				if(m.desc.contains("Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;"))
					invoke=ain;
			}
		}
		if(invoke!=null){
			for(AbstractInsnNode insn = invoke.getNext();insn!=null;insn=insn.getNext()){
				if(insn.getOpcode()==Opcodes.RETURN){
					insn=insn.getPrevious();
					InsnList list = new InsnList();
					list.add(new VarInsnNode(Opcodes.ALOAD, hookedIndex));
					list.add(new FieldInsnNode(Opcodes.GETSTATIC, clientInstance.owner, clientInstance.obfuscatedName, "Lclient;"));
					list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "org/osrs/injection/listeners/MessageListener", "incomingMessage", "(Ljava/lang/Object;Ljava/lang/Object;)V"));
					mn.instructions.insert(insn, list);
					System.out.println("Injected message listener "+hookedIndex);
					break;
				}
				else if(insn instanceof JumpInsnNode){
					insn = ((JumpInsnNode)insn).label;
				}
			}
		}
		mn.visitEnd();
	}
}
