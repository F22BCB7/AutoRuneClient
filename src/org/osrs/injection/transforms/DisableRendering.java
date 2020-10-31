package org.osrs.injection.transforms;

import java.util.ArrayList;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.osrs.injection.ClassHook;

public class DisableRendering {
	public void injectMethod(ArrayList<ClassNode> nodes, ClassHook ch, MethodNode mn){
		for(ClassNode node : nodes){
			if(node.name.equals("client")){
				node.fields.add(new FieldNode(9, "disableRendering", "Z", null, 1));
				for(MethodNode mn2 : node.methods){
					if(mn2.name.equals("<clinit>")){
						AbstractInsnNode ret = null;
						for(AbstractInsnNode insn : mn2.instructions.toArray()){
							if(insn.getOpcode()==Opcodes.RETURN){
								ret=insn;
								break;
							}
						}
						if(ret!=null){
							InsnList list = new InsnList();
							list.add(new InsnNode(Opcodes.ICONST_0));
							list.add(new FieldInsnNode(Opcodes.PUTSTATIC, "client", "disableRendering", "Z"));
							mn2.instructions.insertBefore(ret, list);
							System.out.println("Injected static block disableRendering.");
						}
					}
				}
				System.out.println("Injected disableRendering field!");
				
				MethodNode get = new MethodNode(1, "getDisableRenderingState", "()Z", null, null);
				get.instructions.add(new VarInsnNode(Opcodes.ALOAD, 0));
				get.instructions.add(new FieldInsnNode(Opcodes.GETSTATIC, "client", "disableRendering", "Z"));
				get.instructions.add(new InsnNode(Opcodes.IRETURN));			
				node.methods.add(get);
				System.out.println("Injected disableRendering getter!");
				
				MethodNode set = new MethodNode(1, "setDisableRenderingState", "()V", null, null);
				LabelNode newLabel = new LabelNode();
				LabelNode newLabel2 = new LabelNode();
				set.instructions.add(new VarInsnNode(Opcodes.ALOAD, 0));
				set.instructions.add(new FieldInsnNode(Opcodes.GETSTATIC, "client", "disableRendering", "Z"));
				set.instructions.add(new JumpInsnNode(Opcodes.IFEQ, newLabel));
				set.instructions.add(new InsnNode(Opcodes.ICONST_0));
				set.instructions.add(new JumpInsnNode(Opcodes.GOTO, newLabel2));
				set.instructions.add(newLabel);
				set.instructions.add(new InsnNode(Opcodes.ICONST_1));
				set.instructions.add(newLabel2);
				set.instructions.add(new FieldInsnNode(Opcodes.PUTSTATIC, "client", "disableRendering", "Z"));
				set.instructions.add(new InsnNode(Opcodes.RETURN));
				node.methods.add(set);
				System.out.println("Injected disableRendering setter!");
				node.visitEnd();
				break;
			}
		}
		mn.instructions.get(0);
		InsnList list = new InsnList();
		LabelNode newLabel = new LabelNode();
		list.add(new FieldInsnNode(Opcodes.GETSTATIC, "client", "disableRendering", "Z"));
		list.add(new JumpInsnNode(Opcodes.IFEQ, newLabel));
		list.add(new VarInsnNode(Opcodes.ALOAD, 0));
		list.add(new FieldInsnNode(Opcodes.GETFIELD, ch.obfuscatedName, "ignoreDisableRendering", "Z"));
		list.add(new JumpInsnNode(Opcodes.IFNE, newLabel));
		list.add(new InsnNode(Opcodes.RETURN));
		list.add(newLabel);
		mn.instructions.insert(list);
	}
}
