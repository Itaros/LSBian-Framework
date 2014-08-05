package ru.itaros.lsbrl.utils;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
	


	public static byte[] flip(byte[] array) {
	      if (array == null) {
	          return null;
	      }
	      int i = 0;
	      int j = array.length - 1;
	      byte tmp;
	      while (j > i) {
	          tmp = array[j];
	          array[j] = array[i];
	          array[i] = tmp;
	          j--;
	          i++;
	      }
	      return array;
	  }



	public static int intFromReader(RandomAccessFile reader) throws IOException {
		return flip(reader.readInt());
	}
	public static byte byteFromReader(RandomAccessFile reader) throws IOException {
		return reader.readByte();
	}	
	
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
}
