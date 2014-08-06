package ru.itaros.lsbrl.structure;

import java.io.IOException;
import java.io.RandomAccessFile;

import ru.itaros.lsbrl.utils.EndianHelper;
import ru.itaros.lsbrl.utils.Unsigned;

public class LSBIdentifier {

	private String ascii_value;
	private long uint_key;
	
	private LSBEntryType type=LSBEntryType.UNKNOWN;
	private LSBEntry entry;
	
	/*
	 * From binary blob
	 */
	public LSBIdentifier(RandomAccessFile reader) throws IOException{
		//Reading size
		int size = (int) Unsigned.asUnsigned32(EndianHelper.intFromReader(reader));
		//Extracting value
		byte[] tvalue = new byte[size];
		reader.read(tvalue);
		ascii_value=new String(tvalue,"US-ASCII");
		//Cutting required data off
		uint_key = (int) Unsigned.asUnsigned32(EndianHelper.intFromReader(reader));	
	}
	
	/*
	 * Manual
	 */
	public LSBIdentifier(String value, long key){
		ascii_value=value;
		uint_key=key;
	}

	@Override
	public String toString() {
		return "\""+uint_key+"\"="+ascii_value;
	}

	public int getID() {
		return (int) uint_key;
	}

	public void setType(LSBEntryType type) {
		this.type=type;
	}

	public LSBEntryType getType() {
		return type;
	}

	public void setEntry(LSBEntry entry) {
		this.entry=entry;
	}

	public Object getName() {
		return ascii_value;
	}
	
	
	
	
}
