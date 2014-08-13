package ru.itaros.lsbrl.structure;

import java.lang.reflect.Field;

import ru.itaros.lsbrl.utils.Unsigned;

public class LSBHeader {

	long uint_header;
	long uint_fileLength;
	long uint_endianess;
	long uint_unknownFlag;
	long uint_timestamp;
	
	long uint_version_major;
	long uint_version_minor;
	long uint_version_build;
	long uint_version_revision;
	
	public void unwrapRaw(int[] raws) {
		
	uint_header				=	Unsigned.asUnsigned32(raws[0]);
	uint_fileLength			=	Unsigned.asUnsigned32(raws[1]);
	uint_endianess			=	Unsigned.asUnsigned32(raws[2]);
	uint_unknownFlag		=	Unsigned.asUnsigned32(raws[3]);
	uint_timestamp			=	-1;//Unsigned.asUnsigned32(raws[4]);//INVALID UINT64
	
	uint_version_major		=	Unsigned.asUnsigned32(raws[6]);
	uint_version_minor		=	Unsigned.asUnsigned32(raws[7]);
	uint_version_build		=	Unsigned.asUnsigned32(raws[8]);
	uint_version_revision	=	Unsigned.asUnsigned32(raws[9]);		
	
	}

	@Override
	public String toString() {
		Field[] fields = this.getClass().getDeclaredFields();
		StringBuilder b = new StringBuilder("LSBHeader:\n");
		for (Field field:fields) {
			b.append(">");
			b.append(field.getName());
			b.append("=");
			try{
				long val = field.getLong(this);
				b.append(val>=0?val:"[CORRUPT]");
			}catch(Exception e){
				b.append("[EVALUATION FAILED]");	
			}
			b.append("\n");
		}
		return b.toString();
	}

	public int[] wrapRaw() {
		int[] raws = new int[10];

		raws[0]		=	(int)uint_header;
		raws[1]		=	(int)uint_fileLength;
		raws[2]		=	(int)uint_endianess;
		raws[3]		=	(int)uint_unknownFlag;
		raws[4]								=	-1;//Unsigned.asUnsigned32(raws[4]);//INVALID UINT64
		raws[6]		=	(int) uint_version_major;
		raws[7]		=	(int)uint_version_minor;
		raws[8]		=	(int)uint_version_build;
		raws[9]		=	(int)uint_version_revision;
		
		return raws;
	}
	
	
	
	
	
	
}
