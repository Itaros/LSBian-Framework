package ru.itaros.lsbrl.debug;

import ru.itaros.lsbrl.io.FileDescriptor;
import ru.itaros.lsbrl.io.LSBReader;
import ru.itaros.lsbrl.structure.LSBData;
import ru.itaros.lsbrl.utils.LSBLibException;

public class DebugEntrypoint {

	static LSBData data;
	static FileDescriptor file;
	
	public static void main(String[] args) throws LSBLibException {
		
		file = new FileDescriptor("testassets/umeta.lsb");
		LSBReader reader;
		try{
			reader = file.openToRead();
			reader.fill();
			reader.close();
		}catch(Exception e){
			throw new LSBLibException("Failed to open file",e);
		}
		
		data = reader.getData();
		file=null;
		
		//WE HAVE DATA! \o/
		System.out.println(data.getHeader().toString());
		System.out.println(data.getIdDict().toString());
		System.out.println(data.getReDict().toString());
	}

}
