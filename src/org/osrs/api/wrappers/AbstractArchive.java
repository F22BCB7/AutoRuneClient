package org.osrs.api.wrappers;

public interface AbstractArchive{
	public boolean discardingEntryBuffers();
	public int entryIndexCount();
	public IdentityTable entryIdentityTable();
	public int[][] childIndices();
	public int[] entryChildCounts();
	public int[] childIndexCounts();
	public int[] entryCrcs();
	public int[][] childIdentifiers();
	public IdentityTable[] childIdentityTables();
	public java.lang.Object[] entryBuffers();
	public java.lang.Object[][] childBuffers();
	public int[] entryIdentifiers();
	public int discardUnpacked();
	public boolean encrypted();
	public int[] entryIndices();
	public int invokeGetEntryIdentifier(String s, short predicate);
	public byte[] invokeGetFile4(int a, int b, byte c);
	public byte[] invokeGetFile3(int a, int b, int[] c, int d);
}