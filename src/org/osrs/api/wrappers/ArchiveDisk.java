package org.osrs.api.wrappers;

public interface ArchiveDisk{
	public AccessFileHandler indexFile();
	public AccessFileHandler dataFile();
	public int maxSize();
	public int index();
}