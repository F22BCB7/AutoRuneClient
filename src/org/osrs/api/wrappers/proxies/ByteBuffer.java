package org.osrs.api.wrappers.proxies;

import org.osrs.api.methods.MethodContext;
import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BFunction;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import org.osrs.util.BufferTracker;
import org.osrs.util.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@BClass(name="ByteBuffer")
public class ByteBuffer extends Node implements org.osrs.api.wrappers.ByteBuffer{
	
	@BVar
	public BufferTracker bufferTracker; 
	@BFunction
	@Override
	public void initTracker(){
		bufferTracker = new BufferTracker();
	}
	@BFunction
	@Override
	public BufferTracker getTracker(){
		return bufferTracker;
	}
	@BField
	public int offset;
	@BGetter
	@Override
	public int offset(){return offset;}
	@BField
	public byte[] bytes;
	@BGetter
	@Override
	public byte[] bytes(){return bytes;}
	
	/**
	 * Do not enable tracking for all methods; it will guarentee 
	 * a crash after a minute or so. Just enable what you need
	 * for the packet you want to track.
	 */
/*
	@BMethod(name="writeShort")
	public void _writeShort(int a, byte b){}
	@BDetour
	public void writeShort(int a, byte b){
		if(bufferTracker!=null){
			bufferTracker.writeData(a);
		}
		_writeShort(a, b);
	}
	@BMethod(name="writeInt")
	public void _writeInt(int a, byte b){}
	@BDetour
	public void writeInt(int a, byte b){
		if(bufferTracker!=null){
			bufferTracker.writeData(a);
		}
		_writeInt(a, b);
	}
	@BMethod(name="writeByte")
	public void _writeByte(int a, int b){}
	@BDetour
	public void writeByte(int a, int b){
		if(bufferTracker!=null){
			bufferTracker.writeData(a);
		}
		_writeByte(a, b);
	}

	@BMethod(name="write40")
	public void _write40(long l){}
	@BDetour
	public void write40(long l){
		if(bufferTracker!=null){
			bufferTracker.writeData(l);
		}
		_write40(l);
	}
	@BMethod(name="writeBoolean")
	public void _writeBoolean(boolean a, int b){}
	@BDetour
	public void writeBoolean(boolean a, int b){
		if(bufferTracker!=null){
			bufferTracker.writeData(a);
		}
		_writeBoolean(a, b);
	}
	@BMethod(name="writeCString")
	public void _writeCString(String a, int b){}
	@BDetour
	public void writeCString(String a, int b){
		if(bufferTracker!=null){
			bufferTracker.writeData(a);
		}
		_writeCString(a, b);
	}
	@BMethod(name="writeStringInteger")
	public void _writeStringInteger(CharSequence a, int b){}
	@BDetour
	public void writeStringInteger(CharSequence a, int b){
		if(bufferTracker!=null){
			bufferTracker.writeData(a);
		}
		_writeStringInteger(a, b);
	}
	@BMethod(name="writeSizeInt")
	public void _writeSizeInt(int a, int b){}
	@BDetour
	public void writeSizeInt(int a, int b){
		if(bufferTracker!=null){
			bufferTracker.writeData(a);
		}
		_writeSizeInt(a, b);
	}
	@BMethod(name="writeSizeByte")
	public void _writeSizeByte(int a, int b){}
	@BDetour
	public void writeSizeByte(int a, int b){
		if(bufferTracker!=null){
			bufferTracker.writeData(a);
		}
		_writeSizeByte(a, b);
	}
	@BMethod(name="writeUSmart")
	public void _writeUSmart(int a, int b){}
	@BDetour
	public void writeUSmart(int a, int b){
		if(bufferTracker!=null){
			bufferTracker.writeData(a);
		}
		_writeUSmart(a, b);
	}
	@BMethod(name="writeStringAsInteger")
	public void _writeStringAsInteger(int a, byte b){}
	@BDetour
	public void writeStringAsInteger(int a, byte b){
		if(bufferTracker!=null){
			bufferTracker.writeData(a);
		}
		_writeStringAsInteger(a, b);
	}
	@BMethod(name="writeString")
	public void _writeString(String a, int b){}
	@BDetour
	public void writeString(String a, int b){
		if(bufferTracker!=null){
			bufferTracker.writeData(a);
		}
		_writeString(a, b);
	}
	@BMethod(name="writeByteA")
	public void _writeByteA(int a, int b){}
	@BDetour
	public void writeByteA(int a, int b){
		if(bufferTracker!=null){
			bufferTracker.writeData(a);
		}
		_writeByteA(a, b);
	}
	@BMethod(name="writeNegatedByte")
	public void _writeNegatedByte(int a, byte b){}
	@BDetour
	public void writeNegatedByte(int a, byte b){
		if(bufferTracker!=null){
			bufferTracker.writeData(a);
		}
		_writeNegatedByte(a, b);
	}
	@BMethod(name="writeLEShort")
	public void _writeLEShort(int a, byte b){}
	@BDetour
	public void writeLEShort(int a, byte b){
		if(bufferTracker!=null){
			bufferTracker.writeData(a);
		}
		_writeLEShort(a, b);
	}
	@BMethod(name="writeShortA")
	public void _writeShortA(int a, int b){}
	@BDetour
	public void writeShortA(int a, int b){
		if(bufferTracker!=null){
			bufferTracker.writeData(a);
		}
		_writeShortA(a, b);
	}
	@BMethod(name="writeLEInt")
	public void _writeLEInt(int a, int b){}
	@BDetour
	public void writeLEInt(int a, int b){
		if(bufferTracker!=null){
			bufferTracker.writeData(a);
		}
		_writeLEInt(a, b);
	}
	@BMethod(name="writeMEInt")
	public void _writeMEInt(int a, short b){}
	@BDetour
	public void writeMEInt(int a, short b){
		if(bufferTracker!=null){
			bufferTracker.writeData(a);
		}
		_writeMEInt(a, b);
	}
	@BMethod(name="writeLong")
	public void _writeLong(long l){}
	@BDetour
	public void writeLong(long l){
		if(bufferTracker!=null){
			bufferTracker.writeData(l);
		}
		_writeLong(l);
	}
	@BMethod(name="writeByteS")
	public void _writeByteS(int a, int b){}
	@BDetour
	public void writeByteS(int a, int b){
		if(bufferTracker!=null){
			bufferTracker.writeData(a);
		}
		_writeByteS(a, b);
	}
	@BMethod(name="writeIMEInt")
	public void _writeIMEInt(int a, byte b){}
	@BDetour
	public void writeIMEInt(int a, byte b){
		if(bufferTracker!=null){
			bufferTracker.writeData(a);
		}
		_writeIMEInt(a, b);
	}
	@BMethod(name="writeTriByte")
	public void _writeTriByte(int a, byte b){}
	@BDetour
	public void writeTriByte(int a, byte b){
		if(bufferTracker!=null){
			bufferTracker.writeData(a);
		}
		_writeTriByte(a, b);
	}
	@BMethod(name="writeLEShortA")
	public void _writeLEShortA(int a, int b){}
	@BDetour
	public void writeLEShortA(int a, int b){
		if(bufferTracker!=null){
			bufferTracker.writeData(a);
		}
		_writeLEShortA(a, b);
	}
	@BMethod(name="writeSizeShort")
	public void _writeSizeShort(int a, int b){}
	@BDetour
	public void writeSizeShort(int a, int b){
		if(bufferTracker!=null){
			bufferTracker.writeData(a);
		}
		_writeSizeShort(a, b);
	}*/
	
/*
	@BMethod(name="readString")
	public String _readString(int a){return null;}
	@BDetour
	public String readString(int a){
		String s = _readString(a);
		if(bufferTracker!=null){
			bufferTracker.readData(s);
		}
		return s;
	}
	@BMethod(name="readInt")
	public int _readInt(int a){return -1;}
	@BDetour
	public int readInt(int a){
		int s = _readInt(a);
		if(bufferTracker!=null){
			bufferTracker.readData(s);
		}
		return s;
	}
	@BMethod(name="readUByte")
	public int _readUByte(byte a){return -1;}
	@BDetour
	public int readUByte(byte a){
		int s = _readUByte(a);
		if(bufferTracker!=null){
			bufferTracker.readData(s);
		}
		return s;
	}
	@BMethod(name="readByte")
	public byte _readByte(byte a){return -1;}
	@BDetour
	public byte readByte(byte a){
		byte s = _readByte(a);
		if(bufferTracker!=null){
			bufferTracker.readData(s);
		}
		return s;
	}
	@BMethod(name="readTriByte")
	public int _readTriByte(int a){return -1;}
	@BDetour
	public int readTriByte(int a){
		int s = _readTriByte(a);
		if(bufferTracker!=null){
			bufferTracker.readData(s);
		}
		return s;
	}
	@BMethod(name="readLong")
	public long _readLong(int a){return -1;}
	@BDetour
	public long readLong(int a){
		long s = _readLong(a);
		if(bufferTracker!=null){
			bufferTracker.readData(s);
		}
		return s;
	}
	@BMethod(name="readBoolean")
	public boolean _readBoolean(int a){return false;}
	@BDetour
	public boolean readBoolean(int a){
		boolean s = _readBoolean(a);
		if(bufferTracker!=null){
			bufferTracker.readData(s);
		}
		return s;
	}
	@BMethod(name="readStringFast")
	public String _readStringFast(int a){return null;}
	@BDetour
	public String readStringFast(int a){
		String s = _readStringFast(a);
		if(bufferTracker!=null){
			bufferTracker.readData(s);
		}
		return s;
	}
	@BMethod(name="readByteA")
	public byte _readByteA(int a){return -1;}
	@BDetour
	public byte readByteA(int a){
		byte s = _readByteA(a);
		if(bufferTracker!=null){
			bufferTracker.readData(s);
		}
		return s;
	}
	@BMethod(name="readShort")
	public int _readShort(int a){return -1;}
	@BDetour
	public int readShort(int a){
		int s = _readShort(a);
		if(bufferTracker!=null){
			bufferTracker.readData(s);
		}
		return s;
	}
	@BMethod(name="readSmarts")
	public int _readSmarts(int a){return -1;}
	@BDetour
	public int readSmarts(int a){
		int s = _readSmarts(a);
		if(bufferTracker!=null){
			bufferTracker.readData(s);
		}
		return s;
	}
	@BMethod(name="readLargeSmart")
	public int _readLargeSmart(int a){return -1;}
	@BDetour
	public int readLargeSmart(int a){
		int s = _readLargeSmart(a);
		if(bufferTracker!=null){
			bufferTracker.readData(s);
		}
		return s;
	}
	@BMethod(name="readShorts")
	public int _readShorts(int a){return -1;}
	@BDetour
	public int readShorts(int a){
		int s = _readShorts(a);
		if(bufferTracker!=null){
			bufferTracker.readData(s);
		}
		return s;
	}
	@BMethod(name="readUByteA")
	public int _readUByteA(int a){return -1;}
	@BDetour
	public int readUByteA(int a){
		int s = _readUByteA(a);
		if(bufferTracker!=null){
			bufferTracker.readData(s);
		}
		return s;
	}
	@BMethod(name="readUByteS")
	public int _readUByteS(int a){return -1;}
	@BDetour
	public int readUByteS(int a){
		int s = _readUByteS(a);
		if(bufferTracker!=null){
			bufferTracker.readData(s);
		}
		return s;
	}
	@BMethod(name="readNegatedByte")
	public byte _readNegatedByte(int a){return -1;}
	@BDetour
	public byte readNegatedByte(int a){
		byte s = _readNegatedByte(a);
		if(bufferTracker!=null){
			bufferTracker.readData(s);
		}
		return s;
	}
	@BMethod(name="readByteS")
	public byte _readByteS(int a){return -1;}
	@BDetour
	public byte readByteS(int a){
		byte s = _readByteS(a);
		if(bufferTracker!=null){
			bufferTracker.readData(s);
		}
		return s;
	}
	@BMethod(name="readLEUShort")
	public int _readLEUShort(int a){return -1;}
	@BDetour
	public int readLEUShort(int a){
		int s = _readLEUShort(a);
		if(bufferTracker!=null){
			bufferTracker.readData(s);
		}
		return s;
	}
	@BMethod(name="readLEUShortA")
	public int _readLEUShortA(int a){return -1;}
	@BDetour
	public int readLEUShortA(int a){
		int s = _readLEUShortA(a);
		if(bufferTracker!=null){
			bufferTracker.readData(s);
		}
		return s;
	}
	@BMethod(name="readLEShort")
	public int _readLEShort(int a){return -1;}
	@BDetour
	public int readLEShort(int a){
		int s = _readLEShort(a);
		if(bufferTracker!=null){
			bufferTracker.readData(s);
		}
		return s;
	}
	@BMethod(name="readShortA")
	public int _readShortA(int a){return -1;}
	@BDetour
	public int readShortA(int a){
		int s = _readShortA(a);
		if(bufferTracker!=null){
			bufferTracker.readData(s);
		}
		return s;
	}
	@BMethod(name="readUTriByte")
	public int _readUTriByte(byte a){return -1;}
	@BDetour
	public int readUTriByte(byte a){
		int s = _readUTriByte(a);
		if(bufferTracker!=null){
			bufferTracker.readData(s);
		}
		return s;
	}
	@BMethod(name="readInverseInt")
	public int _readInverseInt(int a){return -1;}
	@BDetour
	public int readInverseInt(int a){
		int s = _readInverseInt(a);
		if(bufferTracker!=null){
			bufferTracker.readData(s);
		}
		return s;
	}
	@BMethod(name="readIMEInt")
	public int _readIMEInt(int a){return -1;}
	@BDetour
	public int readIMEInt(int a){
		int s = _readIMEInt(a);
		if(bufferTracker!=null){
			bufferTracker.readData(s);
		}
		return s;
	}
	@BMethod(name="readMEInt")
	public int _readMEInt(int a){return -1;}
	@BDetour
	public int readMEInt(int a){
		int s = _readMEInt(a);
		if(bufferTracker!=null){
			bufferTracker.readData(s);
		}
		return s;
	}
	@BMethod(name="readUShort")
	public int _readUShort(int a){return -1;}
	@BDetour
	public int readUShort(int a){
		int s = _readUShort(a);
		if(bufferTracker!=null){
			bufferTracker.readData(s);
		}
		return s;
	}
	@BMethod(name="readSmart")
	public int _readSmart(int a){return -1;}
	@BDetour
	public int readSmart(int a){
		int s = _readSmart(a);
		if(bufferTracker!=null){
			bufferTracker.readData(s);
		}
		return s;
	}
	@BMethod(name="readSmart32")
	public int _readSmart32(byte a){return -1;}
	@BDetour
	public int readSmart32(byte a){
		int s = _readSmart32(a);
		if(bufferTracker!=null){
			bufferTracker.readData(s);
		}
		return s;
	}
	@BMethod(name="readUShortA")
	public int _readUShortA(byte a){return -1;}
	@BDetour
	public int readUShortA(byte a){
		int s = _readUShortA(a);
		if(bufferTracker!=null){
			bufferTracker.readData(s);
		}
		return s;
	}
	@BMethod(name="readUSmart")
	public int _readUSmart(byte a){return -1;}
	@BDetour
	public int readUSmart(byte a){
		int s = _readUSmart(a);
		if(bufferTracker!=null){
			bufferTracker.readData(s);
		}
		return s;
	}
	@BMethod(name="readPrefixedString")
	public String _readPrefixedString(byte a){return null;}
	@BDetour
	public String readPrefixedString(byte a){
		String s = _readPrefixedString(a);
		if(bufferTracker!=null){
			bufferTracker.readData(s);
		}
		return s;
	}
	@BMethod(name="readNegatedUByte")
	public int _readNegatedUByte(int a){return -1;}
	@BDetour
	public int readNegatedUByte(int a){
		int s = _readNegatedUByte(a);
		if(bufferTracker!=null){
			bufferTracker.readData(s);
		}
		return s;
	}*/
}