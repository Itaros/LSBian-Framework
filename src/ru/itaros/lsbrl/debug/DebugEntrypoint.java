package ru.itaros.lsbrl.debug;

import java.io.UnsupportedEncodingException;

import ru.itaros.lsbrl.io.FileDescriptor;
import ru.itaros.lsbrl.io.LSBReader;
import ru.itaros.lsbrl.structure.AttributeContentType;
import ru.itaros.lsbrl.structure.LSBAttributeEntry;
import ru.itaros.lsbrl.structure.LSBData;
import ru.itaros.lsbrl.structure.LSBEntry;
import ru.itaros.lsbrl.structure.convenience.LSBAssocGroup;
import ru.itaros.lsbrl.utils.LSBLibException;

public class DebugEntrypoint {

	static LSBData data;
	static LSBAssocGroup group;
	
	static FileDescriptor file;
	
	public static void main(String[] args) throws LSBLibException {
		
		file = new FileDescriptor("testassets/mmeta.lsb");
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
		
		group = new LSBAssocGroup(data.getIdDict(),data.getReDict().getAllRegions());
		
		
		//WE HAVE DATA! \o/
		System.out.println(data.getHeader().toString());
		System.out.println(data.getIdDict().toString());
		System.out.println(data.getReDict().toString());
		
		System.out.println(group.getReport());
		
		//Testing data acquisition
		System.out.println("\nPerforming testread of 'name' attributes:");
		LSBEntry[] levels = group.getByName("Name");
		for(LSBEntry level:levels){
			LSBAttributeEntry asA_Level = (LSBAttributeEntry)level;
			byte[] raw = asA_Level.getRawData();
			int size = asA_Level.getDataSize();
			AttributeContentType type = asA_Level.getType();
			
			System.out.println("Level Attribute datatype: "+type.toString());
			System.out.println("Level Attribute datasize: "+size);
			//Performing encoding with blind assumption of STRING_UTF8_UINT32_3
			try {
				System.out.println("Level Attribute string: "+new String(raw,"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				System.out.println(e.getMessage());
			}
		}
		
		
	}

}
