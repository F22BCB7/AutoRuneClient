package org.osrs.injection.transforms;

import java.util.ListIterator;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.osrs.injection.FieldHook;

public class ItemDefinitionListener {
	public ClassNode injectClass(ClassNode cn, String itemDefName, FieldHook clientInstance) {
		if(itemDefName.equals("")){
			cn.visitEnd();
			return cn;
		}
		ListIterator<MethodNode> mli = cn.methods.listIterator();
		while (mli.hasNext()) {
			MethodNode mn = mli.next();
			if(!mn.desc.endsWith(")L"+itemDefName+";") || mn.desc.contains("(I)"))
				continue;
			ListIterator<AbstractInsnNode> ili = mn.instructions.iterator();
			boolean found=false;
			while (ili.hasNext()) {
				AbstractInsnNode ain = ili.next();
				if (ain == null || ain.getNext()==null)
					break;
				if (ain.getOpcode() == Opcodes.IMUL &&
						ain.getNext().getOpcode() == Opcodes.PUTFIELD) {
					found=true;
					for(int i=0;i<5;++i){
						if(ain.getNext()==null){
							found=false;
							break;
						}
						ain=ain.getNext();
						if (ain.getOpcode() == Opcodes.PUTFIELD)break;
					}
					if (ain.getOpcode() != Opcodes.PUTFIELD)
						found=false;
				}
				if(found){
					InsnList list = new InsnList();
					list.add(new VarInsnNode(Opcodes.ALOAD, 2));
					list.add(new FieldInsnNode(Opcodes.GETSTATIC, clientInstance.owner, clientInstance.obfuscatedName, "Lclient;"));
					list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "org/osrs/injection/listeners/ItemDefinitionListener", "detourInitilization", "(Ljava/lang/Object;Ljava/lang/Object;)V"));
					mn.instructions.insert(ain, list);
					System.out.println("[ItemDefinition] Detouring method : "+cn.name+"."+mn.name+mn.desc);
					break;
				}
			}
		}
		cn.visitEnd();
		return cn;
	}
}
