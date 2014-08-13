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
		if(type==AttributeContentType.STRING_LOCALIZED){
			throw new UnsupportedOperationException("STRING_LOCALIZED(occurence at "+reader.getFilePointer()+") datatype is not supported. I am sorry");
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
		rawdata=ib;
		//ib=EndianHelper.flip(ib);//There is no need to flip raw data
		
		
		
	}

	
	
	
	@Override
	public void writeExpectations(RandomAccessFile writer) throws IOException {
		super.writeExpectations(writer);
		
		writer.write(EndianHelper.flipBytewise((int) type.ordinal()));
		
		//Evaluating if size is dynamic xD
		if(type.getSize()<=0){
			//Dynamic
			writer.write(EndianHelper.flipBytewise((int) rawdata.length*type.getSizeMultiplier()));
			//Datadump
			writer.write(rawdata);
		}else{
			//Static
			if(rawdata.length!=type.getSize()){
				throw new IOException("Faulty rawdata size expectations!");
			}
			writer.write(rawdata);
		}
		
	}

	public AttributeContentType getType() {
		return type;
	}

	public int getDataSize() {
		return rawdata.length;
	}

	public byte[] getRawData() {
		return rawdata.clone();
	}
	
}
