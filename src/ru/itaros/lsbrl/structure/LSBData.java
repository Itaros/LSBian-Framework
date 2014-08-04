package ru.itaros.lsbrl.structure;

public class LSBData {

	private LSBHeader header = new LSBHeader();
	private LSBIdDict iddict = new LSBIdDict();
	private LSBReDict redict = new LSBReDict();
	
	public LSBHeader getHeader() {
		return header;
	}
	
	public LSBIdDict getIdDict() {
		return iddict;
	}

	public LSBReDict getReDict() {
		return redict;
	}
	
	
	
}
