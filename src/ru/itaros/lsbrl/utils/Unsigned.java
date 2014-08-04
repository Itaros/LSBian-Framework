package ru.itaros.lsbrl.utils;

public class Unsigned {

	public static final long UNSIGNED_MASK = 0xffffffffl;
	
	public static long asUnsigned32(int raw){
		return raw & UNSIGNED_MASK;
	}

	public static int fromBinary(byte[] bytes, int offset) {
		int signed = bytes[offset+0] << 24 | (bytes[offset+1] & 0xFF) << 16 | (bytes[offset+2] & 0xFF) << 8 | (bytes[offset+3] & 0xFF);
		return (int) asUnsigned32(signed);
	}
	
	
}
