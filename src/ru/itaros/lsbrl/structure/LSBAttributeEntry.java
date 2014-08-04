package ru.itaros.lsbrl.structure;

import java.io.IOException;
import java.io.RandomAccessFile;

import ru.itaros.lsbrl.utils.EndianHelper;
import ru.itaros.lsbrl.utils.Unsigned;

public class LSBAttributeEntry extends LSBEntry {
	
	private long uint_offset; 
	
	public static LSBAttributeEntry createFromOffset(RandomAccessFile reader, long offset, LSBIdDict iddict) throws IOException{
		reader.seek(offset);
		return new LSBAttributeEntry(reader,offset,iddict);
	}	
	
	protected LSBAttributeEntry(RandomAccessFile reader, long offset, LSBIdDict iddict) throws IOException{
		super((int)Unsigned.asUnsigned32(EndianHelper.fromReader(reader)));
		
		uint_offset = offset;
		
		int recordtype = (int)Unsigned.asUnsigned32(EndianHelper.fromReader(reader));
		
		
		
	}
	
}
