import java.applet.Applet;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.swing.SwingUtilities;

import org.objectweb.asm.Assembly;
import org.objectweb.asm.Mask;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.osrs.api.methods.MethodContext;
import org.osrs.loader.AppletFrame;
import org.osrs.loader.JarDownloader;
import org.osrs.loader.Modscript;
import org.osrs.loader.PageParser;
import org.osrs.loader.RSClassLoader;
import org.osrs.util.Data;

public class Boot {
	public static void main(String[] args) {
		System.out.println("Booting OSRSInjectionBotV2...");
		try{
			PageParser parser = new PageParser();
			JarDownloader downloader = new JarDownloader(parser.jarLocation);
			if(downloader.downloaded){
				ArrayList<ClassNode> classNodes = new ArrayList<ClassNode>();
				File file = new File("runescape.jar");
				System.out.println();
				JarFile jar = new JarFile(file);
				Enumeration<?> enumeration = jar.entries();
				while (enumeration.hasMoreElements()) {
					JarEntry entry = (JarEntry) enumeration.nextElement();
					if (entry.getName().endsWith(".class")) {
						ClassReader classReader = new ClassReader(jar.getInputStream(entry));
						ClassNode classNode = new ClassNode();
						classReader.accept(classNode, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
						classNodes.add(classNode);
						if(classNode.name.equals("client")){
							for (MethodNode mn : classNode.methods) {
								if (mn.name.equals("init")) {List<AbstractInsnNode> pattern = Assembly.find(mn,
										Mask.SIPUSH.operand(765),//Applet width
										Mask.SIPUSH.operand(503),//Applet height
										//Client version
										//Dummy parameter
										Mask.INVOKEVIRTUAL.distance(3)//initializeApplet
								);
								if (pattern != null) {
									IntInsnNode appHeight = (IntInsnNode)pattern.get(1);
									AbstractInsnNode clientVersion = appHeight.getNext();
									if(clientVersion instanceof IntInsnNode){
										Data.jarRevision = ((IntInsnNode)clientVersion).operand;
										break;
									}
								}
								break;
							}
						}
					}
					}
				}
				jar.close();
				System.out.println("Loaded client r"+Data.jarRevision);
				Modscript modscript = new Modscript();
				modscript.loadModscript();

				//Run injection here; on-the-fly
				//Doesn't modify the File on Disk
				//But modifies the classes before loaded by the ClassLoader
				modscript.runInjection(classNodes);
				
				//Load multipliers for reflection analyzer


				RSClassLoader classLoader = new RSClassLoader(file, classNodes, parser.parameters.get("codebase"));
				Class<?> client = classLoader.loadClass("client");
				if(client!=null){
					System.out.println("Loaded client class!");
					Object instance = client.newInstance();
					Data.currentInstance = instance;
					Data.clientInstances.add(instance);
					Data.currentScripts.put(instance, null);
					MethodContext context = new MethodContext(instance);
					Data.currentContext = context;
					Data.clientContexts.put(instance, context);
					Data.clientClassloaders.put(instance, classLoader);
					Data.clientParameters.put(instance, parser.parameters);
					Data.clientModscripts.put(instance, modscript);

					SwingUtilities.invokeLater(() -> {
						AppletFrame frame = new AppletFrame((Applet)instance);
						frame.setVisible(true);
					});
				}
				else
					System.out.println("Failed to load class<?> client!");
			}
		}
		catch(Exception e){
			e.toString();
		}
	}
}
