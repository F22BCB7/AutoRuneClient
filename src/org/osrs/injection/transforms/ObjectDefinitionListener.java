package org.osrs.injection.transforms;

import java.util.ListIterator;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.osrs.injection.FieldHook;

public class ObjectDefinitionListener {
	public ClassNode injectClass(ClassNode cn, String objectDefName, FieldHook clientInstance) {
		if(objectDefName.equals("")){
			cn.visitEnd();
			return cn;
		}
		ListIterator<MethodNode> mli = cn.methods.listIterator();
		while (mli.hasNext()) {
			MethodNode mn = mli.next();
			if(!mn.desc.endsWith(")L"+objectDefName+";") || mn.desc.contains("(I)"))
				continue;
			AbstractInsnNode invoke = null;
			for(AbstractInsnNode insn : mn.instructions.toArray()){
				if(insn instanceof MethodInsnNode){
					MethodInsnNode inv = (MethodInsnNode)insn;
					if(inv.desc.endsWith("J)V"))
						invoke=inv;
				}
			}
			if(invoke!=null){
				InsnList list = new InsnList();
				list.add(new VarInsnNode(Opcodes.ALOAD, 2));
				list.add(new FieldInsnNode(Opcodes.GETSTATIC, clientInstance.owner, clientInstance.obfuscatedName, "Lclient;"));
				list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "org/osrs/injection/listeners/ObjectDefinitionListener", "detourInitilization", "(Ljava/lang/Object;Ljava/lang/Object;)V"));
				mn.instructions.insert(invoke, list);
				System.out.println("[ObjectDefinition] Detouring method : "+cn.name+"."+mn.name+mn.desc);
			}
		}
		cn.visitEnd();
		return cn;
	}
}
