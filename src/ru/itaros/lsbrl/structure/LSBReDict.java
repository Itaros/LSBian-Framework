package ru.itaros.lsbrl.structure;

import java.util.ArrayList;

public class LSBReDict {

	private ArrayList<LSBRegion> collection = new ArrayList<LSBRegion>();
	
	public void add(LSBRegion region){
		collection.add(region);
	}
	
	public LSBRegion[] getAllRegions(){
		LSBRegion[] a = new LSBRegion[collection.size()];
		return collection.toArray(a);
	}
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder("LSBReDict:\n");
		b.append("Entries: "+collection.size()+"\n");
		for(LSBRegion id:collection){
			b.append(">");
			b.append(id.toString());
			b.append("\n");
		}
		return b.toString();
	}	
	
}
