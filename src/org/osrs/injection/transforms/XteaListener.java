package org.osrs.injection.transforms;

import java.util.ArrayList;
import java.util.HashMap;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Wildcard;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.osrs.injection.FieldHook;

public class XteaListener {
	public void injectMethod(MethodNode method, FieldHook clientInstance) {
		ArrayList<AbstractInsnNode> spots = new ArrayList<AbstractInsnNode>();
		HashMap<String, FieldInsnNode> byteArrayFields = new HashMap<String, FieldInsnNode>();
		for(AbstractInsnNode insn : method.instructions.toArray()){
			if(insn.getOpcode()==Opcodes.INVOKESTATIC){
				MethodInsnNode min = (MethodInsnNode)insn;
				if(new Wildcard("(IIZ?)V").matches(min.desc)){
					spots.add(insn);
				}
			}
		}
		
		for(AbstractInsnNode insn : spots){
			InsnList list = new InsnList();
			list.add(new FieldInsnNode(Opcodes.GETSTATIC, clientInstance.owner, clientInstance.obfuscatedName, "Lclient;"));
			for(String s : byteArrayFields.keySet()){
				FieldInsnNode fin = byteArrayFields.get(s);
				if(fin!=null)
					list.add(new FieldInsnNode(Opcodes.GETSTATIC, fin.owner, fin.name, fin.desc));
			}
			list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "org/osrs/injection/listeners/XteaKeyListener", "xteaChanged", "(Ljava/lang/Object;)V"));
			method.instructions.insert(insn, list);
			System.out.println("Injected region xtea listener!");
		}
	}
}
