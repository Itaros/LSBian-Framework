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

	public LSBEntry getRoot() {
		return pointsTo;
	}

	public long write(RandomAccessFile writer) throws IOException {
		writer.write(EndianHelper.flipBytewise((int) uint_key));
		long offset = writer.getFilePointer();
		writer.write(EndianHelper.flipBytewise((int) 0));
		return offset;
	}

	public void setOffset(long filePointer) {
		uint_dataOffset=filePointer;	
	}	
	
}
