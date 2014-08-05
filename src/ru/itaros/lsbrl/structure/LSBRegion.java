package ru.itaros.lsbrl.structure;

import java.io.IOException;
import java.io.RandomAccessFile;

import ru.itaros.lsbrl.utils.EndianHelper;
import ru.itaros.lsbrl.utils.Unsigned;

public class LSBRegion {

	private long uint_key;
	private long uint_dataOffset;

	private LSBEntry pointsTo;
	
	
	public LSBRegion(RandomAccessFile reader) throws IOException {
		uint_key = (int) Unsigned.asUnsigned32(EndianHelper.intFromReader(reader));
		uint_dataOffset = (int) Unsigned.asUnsigned32(EndianHelper.intFromReader(reader));
	}
	
	public LSBRegion(long key, long dataOffset){
		uint_key=key;
		uint_dataOffset=dataOffset;
	}



	@Override
	public String toString() {
		return uint_key+"->0x"+Long.toHexString(uint_dataOffset);
	}

	public long getOffset() {
		return uint_dataOffset;
	}

	public void attachEntry(LSBNodeEntry target) {
		pointsTo=target;
	}	
	
}
