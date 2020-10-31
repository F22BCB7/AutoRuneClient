package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="ChatboxMessage")
public class ChatboxMessage extends EntityNode implements org.osrs.api.wrappers.ChatboxMessage{

	@BField
	public org.osrs.api.wrappers.NameComposite nameComposite;
	@BGetter
	@Override
	public org.osrs.api.wrappers.NameComposite nameComposite(){return nameComposite;}
	@BField
	public int id;
	@BGetter
	@Override
	public int id(){return id;}
	@BField
	public int cycle;
	@BGetter
	@Override
	public int cycle(){return cycle;}
	@BField
	public int type;
	@BGetter
	@Override
	public int type(){return type;}
	@BField
	public String name;
	@BGetter
	@Override
	public String name(){return name;}
	@BField
	public String sender;
	@BGetter
	@Override
	public String sender(){return sender;}
	@BField
	public String message;
	@BGetter
	@Override
	public String message(){return message;}
	@BField
	public org.osrs.api.wrappers.ChatSetting chatSetting1;
	@BGetter
	@Override
	public org.osrs.api.wrappers.ChatSetting chatSetting1(){return chatSetting1;}
	@BField
	public org.osrs.api.wrappers.ChatSetting chatSetting2;
	@BGetter
	@Override
	public org.osrs.api.wrappers.ChatSetting chatSetting2(){return chatSetting2;}
}