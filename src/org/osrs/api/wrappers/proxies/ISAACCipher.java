package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="ISAACCipher")
public class ISAACCipher implements org.osrs.api.wrappers.ISAACCipher{

	@BField
	public int[] results;
	@BGetter
	@Override
	public int[] results(){return results;}
	@BField
	public int accumulator;
	@BGetter
	@Override
	public int accumulator(){return accumulator;}
	@BField
	public int count;
	@BGetter
	@Override
	public int count(){return count;}
	@BField
	public int[] memory;
	@BGetter
	@Override
	public int[] memory(){return memory;}
	@BField
	public int last;
	@BGetter
	@Override
	public int last(){return last;}
	@BField
	public int counter;
	@BGetter
	@Override
	public int counter(){return counter;}
}