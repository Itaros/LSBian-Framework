package ru.itaros.lsbrl.io;

import java.io.IOException;

import ru.itaros.lsbrl.structure.LSBData;

public class FileDescriptor {

	private  boolean isOpened=false;
	
	private String path;
	
	private LSBIO io;
	
	public FileDescriptor(String filepath){
		path=filepath;
	}
	
	public LSBReader openToRead() throws IOException{
		io =  new LSBReader(path);
		if(io!=null){isOpened=true;}
		return (LSBReader) io;
	}
	
	//public LSBWriter openToWrite() throws IOException{
	//	
	//}	
	
	public void close() throws IOException{
		if(isOpened){
			io.close();
			isOpened=false;
		}
	}

	public LSBWriter openToWrite(LSBData data) throws IOException {
		io =  new LSBWriter(data, path);
		if(io!=null){isOpened=true;}
		return (LSBWriter) io;		
	}
	
}
