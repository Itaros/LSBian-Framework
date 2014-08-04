package ru.itaros.lsbrl.io;

import java.io.IOException;

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
	
}
