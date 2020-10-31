package org.osrs.api.wrappers;

public interface Archive extends AbstractArchive{
	public boolean requestNetData();
	public ArchiveDisk referenceStore();
	public int indexReferenceVersion();
	public ArchiveDisk indexStore();
	public boolean[] validArchives();
	public boolean finished();
	public int crcValue();
	public int index();
	public int currentArchiveIndex();
}