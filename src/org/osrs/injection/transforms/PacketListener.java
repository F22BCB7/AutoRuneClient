package org.osrs.injection.transforms;

import java.util.ListIterator;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.osrs.injection.ClassHook;
import org.osrs.injection.FieldHook;

public class PacketListener{
	public ClassNode injectClass(ClassNode cn, ClassHook packetContext, FieldHook clientInstance, FieldHook idlePulses) {
		if(cn.interfaces.contains("org/osrs/api/wrappers/Client")){
			//System.out.println("Attempting to inject packet listener.");
			ListIterator<MethodNode> mli = cn.methods.listIterator();
			while (mli.hasNext()) {
				MethodNode mn = mli.next();
				if(!mn.desc.startsWith("(L"+packetContext.obfuscatedName+";") || !mn.desc.endsWith(")Z") ||
						mn.desc.contains(";)Z"))
					continue;
				ListIterator<AbstractInsnNode> ili = mn.instructions.iterator();
				boolean found=false;
				while (ili.hasNext()) {
					AbstractInsnNode ain = ili.next();
					if (ain == null || !ili.hasNext()){
						break;
					}
					if (ain.getOpcode() == Opcodes.PUTFIELD) {
						FieldInsnNode field = (FieldInsnNode)ain;
						if(field.owner.equals(idlePulses.owner) && field.name.equals(idlePulses.obfuscatedName)){
							AbstractInsnNode next = field.getNext();
							for(int u=0;u<3;++u){
								if(next.getOpcode()==Opcodes.GETSTATIC){
									found=true;
									//System.out.println("idlePulse loc : "+mn.name+mn.desc+" : "+mn.instructions.indexOf(ain));
									break;
								}
								if(next.getOpcode()==Opcodes.GOTO)
									next = ((JumpInsnNode)next).label;
								next=next.getNext();
							}
						}
					}
					if(found){
						found=false;
						FieldInsnNode getfield = null;
						FieldInsnNode putfield = null;
						AbstractInsnNode next = ain.getNext();
						for(int l=0;l<30;++l){
							if(next instanceof FieldInsnNode){
								FieldInsnNode fin = (FieldInsnNode) next;
								if(!fin.desc.equals("I")){
									if(next.getOpcode()==Opcodes.PUTFIELD)
										putfield=fin;
									if(next.getOpcode()==Opcodes.GETFIELD)
										getfield=fin;
								}
							}
							next=next.getNext();
						}
						if(putfield!=null && getfield!=null){
							//System.out.println(mn.instructions.indexOf(putfield)+":"+mn.instructions.indexOf(getfield));
							//insert call
							InsnList list = new InsnList();
							list.add(new VarInsnNode(Opcodes.ALOAD, 1));
							list.add(new FieldInsnNode(Opcodes.GETSTATIC, clientInstance.owner, clientInstance.obfuscatedName, "Lclient;"));
							list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "org/osrs/injection/listeners/PacketListener", "logPacket", "(Ljava/lang/Object;Ljava/lang/Object;)V"));
							mn.instructions.insert(putfield, list);
							System.out.println("[PacketListener] Logging Packet Listener Injected : "+cn.name+"."+mn.name+mn.desc);
						}
					}
				}
				mn.visitMaxs(0, 0);
				mn.visitEnd();
			}	
		}
		cn.visitEnd();
		return cn;
	}
}
