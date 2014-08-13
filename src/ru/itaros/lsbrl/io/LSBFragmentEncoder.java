package ru.itaros.lsbrl.io;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;

import ru.itaros.lsbrl.structure.LSBAttributeEntry;
import ru.itaros.lsbrl.structure.LSBData;
import ru.itaros.lsbrl.structure.LSBEntry;
import ru.itaros.lsbrl.structure.LSBHeader;
import ru.itaros.lsbrl.structure.LSBIdDict;
import ru.itaros.lsbrl.structure.LSBIdentifier;
import ru.itaros.lsbrl.structure.LSBNodeEntry;
import ru.itaros.lsbrl.structure.LSBReDict;
import ru.itaros.lsbrl.structure.LSBRegion;
import ru.itaros.lsbrl.utils.EndianHelper;

public class LSBFragmentEncoder {

	private static final int HEADER_DATACOUNT=10;
	private static final long HEADER_STRIDE=4*HEADER_DATACOUNT;	
	
	private RandomAccessFile writer;
	
	LSBFragmentEncoder(RandomAccessFile writer){
		this.writer=writer;
	}

	/*
	 * Maps all the data
	 */
	public void preWriteHeader(LSBData data) throws IOException {
		writer.seek(0);
		LSBHeader header = data.getHeader();
		int[] raws = header.wrapRaw();
		for(int i:raws){
			writeInt(i);
		}
	}

	private void writeInt(int i) throws IOException {
		writer.write(EndianHelper.flipBytewise(i));
		//writer.writeInt(i);
	}

	public void encodeIdDict(LSBData data) throws IOException {
		LSBIdDict dict = data.getIdDict();
		//Moving pointer
		writer.seek(0x28);
		//Writing expectations
		writer.write(EndianHelper.flipBytewise(dict.getSize()));
		//Dumping
		Iterator<LSBIdentifier> i=dict.getIterator();
		while(i.hasNext()){
			LSBIdentifier thing = i.next();
			thing.write(writer);
		}
		
		offset_after_iddict=writer.getFilePointer();
	}
	
	private long offset_after_iddict=-1;
	private long offset_after_redict=-1;

	public long[] encodeRegions(LSBData data) throws IOException {
		if(offset_after_iddict<0){throw new IllegalStateException("Can't write Regions without Identifier Dictionary");}
		LSBReDict redict = data.getReDict();
		LSBRegion[] regs = redict.getAllRegions();
		
		long[] offsetPointers = new long[regs.length];
		//Moving pointer
		writer.seek(offset_after_iddict);
				
		//Writing expectations
		writer.write(EndianHelper.flipBytewise(regs.length));
		//Consequental Writing
		for(int i = 0; i<regs.length;i++){
			LSBRegion c = regs[i];
			offsetPointers[i]=c.write(writer);
		}
		
		offset_after_redict=writer.getFilePointer();
		
		return offsetPointers;
	}

	public void encodeData(LSBData data) throws IOException {
		if(offset_after_redict<0){throw new IllegalStateException("Can't write Data without Regions");}
		//Moving pointer
		writer.seek(offset_after_redict);		
		
		LSBReDict redict = data.getReDict();
		LSBRegion[] regs = redict.getAllRegions();
		//Consequental Writing
		for(int i = 0; i<regs.length;i++){		
			LSBRegion cr = regs[i];
			cr.setOffset(writer.getFilePointer());
			
			LSBEntry root = cr.getRoot();
			sequentalRecursiveDump(root);
		}
	}

	private void sequentalRecursiveDump(LSBEntry entry) throws IOException {
		if(entry instanceof LSBNodeEntry){
			LSBNodeEntry node = (LSBNodeEntry)entry;
			node.writeExpectations(writer);
			Iterator<LSBEntry> internalCollectionIterator = node.getChildIterator();
			//Now we need to go 2 times: one for attribs and second one for nodes
			while(internalCollectionIterator.hasNext()){
				LSBEntry ec = internalCollectionIterator.next();
				if(ec instanceof LSBAttributeEntry){
					sequentalRecursiveDump(ec);
				}
			}
			internalCollectionIterator = node.getChildIterator();
			while(internalCollectionIterator.hasNext()){
				LSBEntry ec = internalCollectionIterator.next();
				if(ec instanceof LSBNodeEntry){
					sequentalRecursiveDump(ec);
				}
			}			
		}else if(entry instanceof LSBAttributeEntry){
			LSBAttributeEntry attrib = (LSBAttributeEntry)entry;
			attrib.writeExpectations(writer);
		}
		
	}

	public void postEncodeRegions(LSBData data, long[] regionsOffsetsPostPoints) throws IOException {
		if(offset_after_iddict<0){throw new IllegalStateException("Wtf are you doing?");}
		LSBReDict redict = data.getReDict();
		LSBRegion[] regs = redict.getAllRegions();
		for(int i = 0; i < regionsOffsetsPostPoints.length ; i++){
			writer.seek(regionsOffsetsPostPoints[i]);
			writer.write(EndianHelper.flipBytewise((int) regs[i].getOffset()));
		}
		
	}	
	
	
}
