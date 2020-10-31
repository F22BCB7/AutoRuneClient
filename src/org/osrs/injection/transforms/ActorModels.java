package org.osrs.injection.transforms;

import java.util.ListIterator;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.osrs.injection.FieldHook;

public class ActorModels {
	public ClassNode injectClass(ClassNode cn, String modelName, FieldHook clientInstance) {
		if(cn.interfaces.contains("org/osrs/api/wrappers/Player") || cn.interfaces.contains("org/osrs/api/wrappers/Npc")){
			if(modelName.equals("")){
				cn.visitEnd();
				return cn;
			}
			ListIterator<MethodNode> mli = cn.methods.listIterator();
			while (mli.hasNext()) {
				MethodNode mn = mli.next();
				if(!mn.desc.endsWith(")L"+modelName+";") || mn.desc.contains("()"))
					continue;
				ListIterator<AbstractInsnNode> ili = mn.instructions.iterator();
				boolean found=false;
				while (ili.hasNext()) {
					AbstractInsnNode ain = ili.next();
					if (ain == null || !ili.hasNext()){
						break;
					}
					if (ain.getOpcode() == Opcodes.ALOAD
							&& ain.getNext().getOpcode() == Opcodes.ARETURN) {
						found=true;
					}
					if(found){
						InsnList list = new InsnList();
						int locVarIndex = ((VarInsnNode)ain).var;
						list.add(new VarInsnNode(Opcodes.ALOAD, 0));
						list.add(new VarInsnNode(Opcodes.ALOAD, locVarIndex));
						list.add(new FieldInsnNode(Opcodes.GETSTATIC, clientInstance.owner, clientInstance.obfuscatedName, "Lclient;"));
						list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "org/osrs/injection/listeners/ModelListener", "updateModels", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V"));
						mn.instructions.insert(ain.getPrevious(), list);
						System.out.println("[CharacterModels] Detouring method return : "+cn.name+"."+mn.name+mn.desc);
						found=false;
					}
				}

			}	
		}
		cn.visitEnd();
		return cn;
	}
}
