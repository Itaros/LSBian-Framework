package ru.itaros.lsbrl.io;

import java.io.IOException;
import java.io.RandomAccessFile;

import ru.itaros.lsbrl.structure.LSBData;
import ru.itaros.lsbrl.structure.LSBEntryType;
import ru.itaros.lsbrl.structure.LSBHeader;
import ru.itaros.lsbrl.structure.LSBIdDict;
import ru.itaros.lsbrl.structure.LSBIdentifier;
import ru.itaros.lsbrl.structure.LSBNodeEntry;
import ru.itaros.lsbrl.structure.LSBReDict;
import ru.itaros.lsbrl.structure.LSBRegion;
import ru.itaros.lsbrl.utils.EndianHelper;
import ru.itaros.lsbrl.utils.LSBLibException;
import ru.itaros.lsbrl.utils.UnresolveableInheritanceException;
import ru.itaros.lsbrl.utils.Unsigned;

public class LSBFragmentDecoder {

	private RandomAccessFile reader;
	
	LSBFragmentDecoder(RandomAccessFile reader){
		this.reader=reader;
	}
	
	public void decodeHeader(LSBData data) throws IOException {
		//Setuping container
		LSBHeader header = data.getHeader();
		int[] raws = new int[HEADER_DATACOUNT];
		//Moving pointer
		reader.seek(0);
		//Reading Data
		for(int i=0;i<HEADER_DATACOUNT;i++){
			raws[i]=readInt();
		}
		header.unwrapRaw(raws);
	}
	
	private int readInt() throws IOException{
		return EndianHelper.intFromReader(reader);
	}
	
	
	private static final int HEADER_DATACOUNT=10;
	private static final long HEADER_STRIDE=4*HEADER_DATACOUNT;

	public void decodeIdDict(LSBData data) throws IOException {
		LSBIdDict iddict = data.getIdDict();
		//Moving pointer
		reader.seek(0x28);
		//Evaluating Expectations
		int passes = (int)Unsigned.asUnsigned32(readInt());
		//Consequental Reading
		for(int i = 0; i<passes;i++){
			LSBIdentifier id = new LSBIdentifier(reader);
			iddict.add(id);
		}
		offset_after_iddict=reader.getFilePointer();
	}
	
	
	private long offset_after_iddict=-1;
	private long offset_after_redict=-1;

	public void decodeRegions(LSBData data) throws IOException {
		if(offset_after_iddict<0){throw new IllegalStateException("Can't read Regions without Identifier Dictionary");}
		LSBReDict redict = data.getReDict();
		//Moving pointer
		reader.seek(offset_after_iddict);
		//Evaluating Expectations
		int passes = (int)Unsigned.asUnsigned32(readInt());
		//Consequental Reading
		for(int i = 0; i<passes;i++){
			LSBRegion re = new LSBRegion(reader);
			redict.add(re);
		}
		offset_after_redict=reader.getFilePointer();		
		
		
	}

	public static int peekAtID(RandomAccessFile reader) throws IOException{
		int id = EndianHelper.intFromReader(reader);
		reader.seek(reader.getFilePointer()-4);//backstep
		return id;
	}
	
	private void evaluateIdDictTypeLink(LSBIdDict dict, long offset, LSBEntryType type) throws IOException, UnresolveableInheritanceException{
		reader.seek(offset);
		int id = peekAtID(reader);
		dict.link(id,type);
	}
	
	public void decodeData(LSBData data) throws IOException, UnresolveableInheritanceException, LSBLibException {
		if(offset_after_redict<0){throw new IllegalStateException("Can't read Data without Regions");}
		//Moving pointer
		reader.seek(offset_after_redict);	
		//There are no expectations :(
		LSBIdDict iddict = data.getIdDict();
		LSBReDict redict = data.getReDict();
		//We need to fill ID->Type assocs
		for(LSBRegion r:redict.getAllRegions()){
			//Assuming all Regions point to NODEs
			evaluateIdDictTypeLink(iddict,r.getOffset(),LSBEntryType.NODE);
			LSBNodeEntry entry = LSBNodeEntry.createFromOffset(reader, r.getOffset(),iddict);
			r.attachEntry(entry);
		}
		
	}
	
}
