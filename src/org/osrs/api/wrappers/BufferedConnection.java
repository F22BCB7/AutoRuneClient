package org.osrs.api.wrappers;

public interface BufferedConnection extends AbstractSocket{
	public Signlink signlink();
	public java.io.OutputStream outputStream();
	public java.net.Socket socket();
	public boolean closed();
	public int bufferPosition();
	public Task task();
	public boolean ioError();
	public int writerPosition();
	public java.io.InputStream inputStream();
	public int bufferSize();
	public int writerOffset();
	public byte[] buffer();
}