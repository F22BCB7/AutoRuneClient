package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="ClassInfo")
public class ClassInfo extends Node implements org.osrs.api.wrappers.ClassInfo{

	@BField
	public byte[][][] args;
	@BGetter
	@Override
	public byte[][][] args(){return args;}
	@BField
	public java.lang.reflect.Method[] methods;
	@BGetter
	@Override
	public java.lang.reflect.Method[] methods(){return methods;}
	@BField
	public int[] types;
	@BGetter
	@Override
	public int[] types(){return types;}
	@BField
	public int[] errorIdentifiers;
	@BGetter
	@Override
	public int[] errorIdentifiers(){return errorIdentifiers;}
	@BField
	public java.lang.reflect.Field[] fields;
	@BGetter
	@Override
	public java.lang.reflect.Field[] fields(){return fields;}
	@BField
	public int[] fieldValues;
	@BGetter
	@Override
	public int[] fieldValues(){return fieldValues;}
	@BField
	public int identifier;
	@BGetter
	@Override
	public int identifier(){return identifier;}
	@BField
	public int count;
	@BGetter
	@Override
	public int count(){return count;}
}