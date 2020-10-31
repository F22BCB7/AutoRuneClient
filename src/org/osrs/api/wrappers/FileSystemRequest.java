package org.osrs.api.wrappers;

public interface FileSystemRequest extends Node{
	public int type();
	public byte[] buffer();
	public ArchiveDisk archiveDisk();
	public Archive archive();
}