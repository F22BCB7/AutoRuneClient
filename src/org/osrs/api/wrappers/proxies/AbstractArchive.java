package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BFunction;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="AbstractArchive")
public class AbstractArchive implements org.osrs.api.wrappers.AbstractArchive{

	@BField
	public boolean discardingEntryBuffers;
	@BGetter
	@Override
	public boolean discardingEntryBuffers(){return discardingEntryBuffers;}
	@BField
	public int entryIndexCount;
	@BGetter
	@Override
	public int entryIndexCount(){return entryIndexCount;}
	@BField
	public org.osrs.api.wrappers.IdentityTable entryIdentityTable;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IdentityTable entryIdentityTable(){return entryIdentityTable;}
	@BField
	public int[][] childIndices;
	@BGetter
	@Override
	public int[][] childIndices(){return childIndices;}
	@BField
	public int[] entryChildCounts;
	@BGetter
	@Override
	public int[] entryChildCounts(){return entryChildCounts;}
	@BField
	public int[] childIndexCounts;
	@BGetter
	@Override
	public int[] childIndexCounts(){return childIndexCounts;}
	@BField
	public int[] entryCrcs;
	@BGetter
	@Override
	public int[] entryCrcs(){return entryCrcs;}
	@BField
	public int[][] childIdentifiers;
	@BGetter
	@Override
	public int[][] childIdentifiers(){return childIdentifiers;}
	@BField
	public org.osrs.api.wrappers.IdentityTable[] childIdentityTables;
	@BGetter
	@Override
	public org.osrs.api.wrappers.IdentityTable[] childIdentityTables(){return childIdentityTables;}
	@BField
	public java.lang.Object[] entryBuffers;
	@BGetter
	@Override
	public java.lang.Object[] entryBuffers(){return entryBuffers;}
	@BField
	public java.lang.Object[][] childBuffers;
	@BGetter
	@Override
	public java.lang.Object[][] childBuffers(){return childBuffers;}
	@BField
	public int[] entryIdentifiers;
	@BGetter
	@Override
	public int[] entryIdentifiers(){return entryIdentifiers;}
	@BField
	public int discardUnpacked;
	@BGetter
	@Override
	public int discardUnpacked(){return discardUnpacked;}
	@BField
	public boolean encrypted;
	@BGetter
	@Override
	public boolean encrypted(){return encrypted;}
	@BField
	public int[] entryIndices;
	@BGetter
	@Override
	public int[] entryIndices(){return entryIndices;}

	@BMethod(name="getEntryIdentifier")
	public int _getEntryIdentifier(String s, short a){return -1;}
	@BDetour
	public int getEntryIdentifier(String s, short a){
		//System.out.println("[AbstractArchive.getEntryIdentifier] "+s);
		return _getEntryIdentifier(s, a);
	}
	@BFunction
	@Override
	public int invokeGetEntryIdentifier(String s, short predicate){
		return getEntryIdentifier(s, predicate);
	}
	@BMethod(name="getFile4")
	public byte[] _getFile4(int a, int b, byte c){return new byte[]{};}
	@BDetour
	public byte[] getFile4(int a, int b, byte c){
		return _getFile4(a, b, c);
	}
	@BFunction
	@Override
	public byte[] invokeGetFile4(int a, int b, byte c){
		return _getFile4(a, b, c);
	}
	@BMethod(name="getFile3")
	public byte[] _getFile3(int a, int b, int[] c, int d){return new byte[]{};}
	@BDetour
	public byte[] getFile3(int a, int b, int[] c, int d){
		return _getFile3(a, b, c, d);
	}
	@BFunction
	@Override
	public byte[] invokeGetFile3(int a, int b, int[] c, int d){
		return getFile3(a, b, c, d);
	}
	/*
	 	MethodHook not implemented! : AbstractArchive.unpackTable([BI)V
		MethodHook not implemented! : AbstractArchive.getFileBytes(Ljava/lang/String;Ljava/lang/String;I)[B
		MethodHook not implemented! : AbstractArchive.hasChildFileBuffer(II)Z
		MethodHook not implemented! : AbstractArchive.filesCompleted(I)Z
		MethodHook not implemented! : AbstractArchive.getChildBufferLengthAtIndex(IB)I
		MethodHook not implemented! : AbstractArchive.getChildIdentifier(ILjava/lang/String;I)I
		MethodHook not implemented! : AbstractArchive.getChildIndices(II)[I
		MethodHook not implemented! : AbstractArchive.getChildBufferLength(B)I
		MethodHook not implemented! : AbstractArchive.clearEntryBuffers(I)V
		MethodHook not implemented! : AbstractArchive.clearChildBuffer(II)V
		MethodHook not implemented! : AbstractArchive.hasEntryBuffer(III)Z
		MethodHook not implemented! : AbstractArchive.loadBuffer(IB)V
		MethodHook not implemented! : AbstractArchive.hasFileLoaded(Ljava/lang/String;Ljava/lang/String;I)Z
		MethodHook not implemented! : AbstractArchive.prepareChildBuffers(I[II)Z
		MethodHook not implemented! : AbstractArchive.archiveLoadPercent(IB)I
		MethodHook not implemented! : AbstractArchive.hasEntryFileBuffer(IB)Z
		MethodHook not implemented! : AbstractArchive.endRead(IB)V
		MethodHook not implemented! : AbstractArchive.getEntrySize(Ljava/lang/String;B)I
		MethodHook not implemented! : AbstractArchive.getFile3(II[II)[B
		MethodHook not implemented! : AbstractArchive.getFile4(IIB)[B
		MethodHook not implemented! : AbstractArchive.getFile5(II)[B
		MethodHook not implemented! : AbstractArchive.clearChildBuffers(B)V
		MethodHook not implemented! : AbstractArchive.getFile2(III)[B
		MethodHook not implemented! : AbstractArchive.getFile(IB)[B
	 */
}