package ru.itaros.lsbrl.io;

import ru.itaros.lsbrl.structure.LSBData;

public abstract class LSBIO {

	protected LSBIO(){
		initLSBData();
	}
	
	private boolean isReady=false;
	
	protected LSBData data;
	
	private void initLSBData(){
		data = new LSBData();
	}

	public abstract void close();

	protected void markReady(){
		isReady=true;
	}
	
	public LSBData getData(){
		if(isReady){
			return data;
		}else{
			throw new IllegalStateException("Data not ready");
		}
	}
	
}
