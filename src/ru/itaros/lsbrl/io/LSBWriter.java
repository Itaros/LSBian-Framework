package ru.itaros.lsbrl.io;

import java.io.IOException;
import java.io.RandomAccessFile;

import ru.itaros.lsbrl.structure.LSBData;
import ru.itaros.lsbrl.utils.LSBLibException;
import ru.itaros.lsbrl.utils.UnresolveableInheritanceException;

public class LSBWriter extends LSBIO {

	private RandomAccessFile writer;
	
	private LSBFragmentEncoder encoder;
	
	public LSBWriter(LSBData data, String path) throws IOException{
		super(data);
		writer = new RandomAccessFile(path,"rw");
		//TODO: Make sure file is empty
		
		encoder = new LSBFragmentEncoder(writer);
	}
	
	@Override
	public void close() {
		try {
			writer.close();
		} catch (IOException e) {
			//How this can even happen?
			throw new RuntimeException(e);
		}
	}
	
	
	public void fill() throws IOException, UnresolveableInheritanceException, LSBLibException{
		encoder.preWriteHeader(data);
		encoder.encodeIdDict(data);
		long[] regionsOffsetsPostPoints = encoder.encodeRegions(data);
		encoder.encodeData(data);
		encoder.postEncodeRegions(data,regionsOffsetsPostPoints);
	}

}
