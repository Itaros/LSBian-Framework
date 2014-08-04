package ru.itaros.lsbrl.utils;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public class EndianHelper {

	private static final int LSB_UINT32_SIZE = 4;
	private static ByteBuffer buffer=ByteBuffer.allocate(LSB_UINT32_SIZE);
	
	public synchronized static int flip(int source){
		buffer.clear();
		buffer.putInt(source);
		buffer.flip();
		byte[] b = buffer.array();
		byte[] b2 = b.clone();
		//Flip \o/
		for(int i = 0;i<b.length;i++){
			b[i]=b2[b.length-1-i];
		}
		
		return buffer.getInt();
	}

	public static int fromReader(RandomAccessFile reader) throws IOException {
		return flip(reader.readInt());
	}
	
}
