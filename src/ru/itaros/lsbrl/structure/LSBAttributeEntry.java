package ru.itaros.lsbrl.structure;

import java.io.IOException;
import java.io.RandomAccessFile;

import ru.itaros.lsbrl.utils.EndianHelper;
import ru.itaros.lsbrl.utils.LSBLibException;
import ru.itaros.lsbrl.utils.Unsigned;

public class LSBAttributeEntry extends LSBEntry {
	
	private long uint_offset; 
	
	private AttributeContentType type;
	private byte[] rawdata;
	
	public static LSBAttributeEntry createFromOffset(RandomAccessFile reader, long offset, LSBIdDict iddict) throws IOException, LSBLibException{
		reader.seek(offset);
		return new LSBAttributeEntry(reader,offset,iddict);
	}	
	
	protected LSBAttributeEntry(RandomAccessFile reader, long offset, LSBIdDict iddict) throws IOException, LSBLibException{
		super((int)Unsigned.asUnsigned32(EndianHelper.intFromReader(reader)));
		
		uint_offset = offset;
		
		int recordtype = (int)Unsigned.asUnsigned32(EndianHelper.intFromReader(reader));
		
		type = AttributeContentType.getByRecordType(recordtype);
		if(type==null){
			throw new LSBLibException("Unknown type encountered at "+reader.getFilePointer());
		}
		
		byte[] ib;
		int expectation = type.getSize();//static type
		if(expectation<0){
			//dynamic type
			//Assuming all lengths are uint32
			expectation = (int)Unsigned.asUnsigned32(EndianHelper.intFromReader(reader));
			expectation*=type.getSizeMultiplier();
		}
		//reading
		ib = new byte[expectation];
		reader.read(ib);
		//ib=EndianHelper.flip(ib);//There is no need to flip raw data
		
		
		
	}
	
}
