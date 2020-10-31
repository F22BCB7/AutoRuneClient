package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="Archive")
public class Archive extends AbstractArchive implements org.osrs.api.wrappers.Archive{

	@BField
	public boolean requestNetData;
	@BGetter
	@Override
	public boolean requestNetData(){return requestNetData;}
	@BField
	public org.osrs.api.wrappers.ArchiveDisk referenceStore;
	@BGetter
	@Override
	public org.osrs.api.wrappers.ArchiveDisk referenceStore(){return referenceStore;}
	@BField
	public int indexReferenceVersion;
	@BGetter
	@Override
	public int indexReferenceVersion(){return indexReferenceVersion;}
	@BField
	public org.osrs.api.wrappers.ArchiveDisk indexStore;
	@BGetter
	@Override
	public org.osrs.api.wrappers.ArchiveDisk indexStore(){return indexStore;}
	@BField
	public boolean[] validArchives;
	@BGetter
	@Override
	public boolean[] validArchives(){return validArchives;}
	@BField
	public boolean finished;
	@BGetter
	@Override
	public boolean finished(){return finished;}
	@BField
	public int crcValue;
	@BGetter
	@Override
	public int crcValue(){return crcValue;}
	@BField
	public int index;
	@BGetter
	@Override
	public int index(){return index;}
	@BField
	public int currentArchiveIndex;
	@BGetter
	@Override
	public int currentArchiveIndex(){return currentArchiveIndex;}
	
	
	/*
	 	MethodHook not implemented! : ArchiveDisk.write(I[BII)Z
		MethodHook not implemented! : ArchiveDisk.read(II)[B
		MethodHook not implemented! : ArchiveDisk.writeData(I[BIZI)Z
		MethodHook not implemented! : Archive.isFinished(I)Z
		MethodHook not implemented! : Archive.write(I[BZZI)V
		MethodHook not implemented! : Archive.setInformation(IIB)V
		MethodHook not implemented! : Archive.loadAllLocals(I)V
		MethodHook not implemented! : Archive.isArchiveValid(IB)Z
		MethodHook not implemented! : Archive.childExists(II)Z
		MethodHook not implemented! : Archive.archiveLoadPercent(IB)I
		MethodHook not implemented! : Archive.load(Llq;I[BZI)V
		MethodHook not implemented! : Archive.loadPercent(B)I
		MethodHook not implemented! : Archive.percentage(I)I
	 */
}