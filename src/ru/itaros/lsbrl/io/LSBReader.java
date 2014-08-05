package ru.itaros.lsbrl.io;

import java.io.IOException;
import java.io.RandomAccessFile;

import ru.itaros.lsbrl.utils.LSBLibException;
import ru.itaros.lsbrl.utils.UnresolveableInheritanceException;

public final class LSBReader extends LSBIO{

	private RandomAccessFile reader;
	//private InputStream buffer;
	
	private LSBFragmentDecoder decoder;
	
	public LSBReader(String path) throws IOException{
		super();
		reader = new RandomAccessFile(path,"r");
		//buffer = new InputStream(reader);
		
		decoder = new LSBFragmentDecoder(reader);
	}

	@Override
	public void close() {
		try {
			decoder=null;//Ensure GC
			reader.close();
		} catch (IOException e) {
			//How this can even happen?
			throw new RuntimeException(e);
		}
	}

	public void fill() throws IOException, UnresolveableInheritanceException, LSBLibException{
		
		decoder.decodeHeader(data);
		decoder.decodeIdDict(data);
		decoder.decodeRegions(data);
		decoder.decodeData(data);
		
		this.markReady();
	}

	
	
	
}
