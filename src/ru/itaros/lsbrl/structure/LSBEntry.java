package ru.itaros.lsbrl.structure;

import java.io.IOException;
import java.io.RandomAccessFile;

import ru.itaros.lsbrl.io.LSBFragmentDecoder;
import ru.itaros.lsbrl.utils.EndianHelper;
import ru.itaros.lsbrl.utils.LSBLibException;
import ru.itaros.lsbrl.utils.UnresolveableInheritanceException;

public abstract class LSBEntry {

	private long uint_identifier;
	
	protected LSBEntry(long identifier){
		uint_identifier=identifier;
	}
	
	public long getIdentifier(){
		return uint_identifier;
	}

	public static LSBEntry createFromOffset(RandomAccessFile reader,
			long filePointer, LSBIdDict iddict, LSBEntryType type) throws IOException, UnresolveableInheritanceException, LSBLibException {

		int id = EndianHelper.intFromReader(reader);
		
		LSBEntry e=null;
		switch(type){
		case ATTRIBUTE:
			e =  LSBAttributeEntry.createFromOffset(reader, filePointer, iddict);
			break;
		case NODE:
			e = LSBNodeEntry.createFromOffset(reader, filePointer, iddict);
			break;
		case UNKNOWN:
			throw new IllegalStateException("Non-referenced data detected!");
		}
		if(e==null){
			throw new IllegalStateException("Non-unknown unknown type @_@");
		}
		iddict.link(id, e);
		return e;
	}

	public void writeExpectations(RandomAccessFile writer) throws IOException {
		writer.write(EndianHelper.flipBytewise((int) uint_identifier));
	}
	
}
