package org.osrs.injection.transforms;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.osrs.injection.FieldHook;

public class OutgoingPacketListener {
	public void injectMethod(MethodNode method, FieldHook clientInstance) {
		if(method==null || clientInstance==null)
			return;
		InsnList list = new InsnList();
		list.add(new VarInsnNode(Opcodes.ALOAD, 0));
		list.add(new FieldInsnNode(Opcodes.GETSTATIC, clientInstance.owner, clientInstance.obfuscatedName, "Lclient;"));
		list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "org/osrs/injection/listeners/PacketListener", "logPacket", "(Ljava/lang/Object;Ljava/lang/Object;)V"));
		method.instructions.insert(list);
		System.out.println("Injected outgoing packet listener.");
	}
}
