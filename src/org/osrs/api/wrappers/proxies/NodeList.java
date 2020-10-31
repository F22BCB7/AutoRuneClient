package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@BClass(name="NodeList")
public class NodeList implements org.osrs.api.wrappers.NodeList{

	@BField
	public org.osrs.api.wrappers.Node current;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Node current(){return current;}
	@BField
	public org.osrs.api.wrappers.Node head;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Node head(){return head;}
	@BMethod(name="getBack")
	public org.osrs.api.wrappers.Node _getBack(){return null;}
	@BDetour
	@Override
	public org.osrs.api.wrappers.Node getBack(){
		org.osrs.api.wrappers.Node node = _getBack();
		if(node!=null){
			/*if(node instanceof org.osrs.api.wrappers.ClassInfo){
				org.osrs.api.wrappers.ClassInfo ci = (org.osrs.api.wrappers.ClassInfo)node;
				System.out.println("Count:"+ci.count()+" Identifier:"+ci.identifier());
				System.out.println("UID:"+ci.uid());
				System.out.println("Fields:"+Arrays.toString(ci.fields()));
				System.out.println("Values:"+Arrays.toString(ci.fieldValues()));
				System.out.println("Types:"+Arrays.toString(ci.types()));
				System.out.println("Errors:"+Arrays.toString(ci.errorIdentifiers()));
				System.out.println("Methods:"+Arrays.toString(ci.methods()));
				System.out.println();
			}*/
		}
		return node;
	}
}