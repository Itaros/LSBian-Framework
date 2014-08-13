package ru.itaros.lsbrl.structure;

import java.util.ArrayList;
import java.util.Iterator;

import ru.itaros.lsbrl.utils.UnresolveableInheritanceException;

public class LSBIdDict {

	private ArrayList<LSBIdentifier> collection = new ArrayList<LSBIdentifier>();
	
	
	public void add(LSBIdentifier identifier){
		collection.add(identifier);
	}


	@Override
	public String toString() {
		StringBuilder b = new StringBuilder("LSBIdDict:\n");
		b.append("Entries: "+collection.size()+"\n");
		for(LSBIdentifier id:collection){
			b.append(">");
			b.append(id.toString());
			b.append("\n");
		}
		return b.toString();
	}


	public LSBIdentifier link(int id, LSBEntryType type) throws UnresolveableInheritanceException {
		for(LSBIdentifier i:collection){
			if(i.getID()==id){
				i.setType(type);
				return i;
			}
		}
		throw new UnresolveableInheritanceException();
	}
	public LSBIdentifier link(int id, LSBEntry entry) throws UnresolveableInheritanceException {
		for(LSBIdentifier i:collection){
			if(i.getID()==id){
				i.setEntry(entry);
				return i;
			}
		}
		throw new UnresolveableInheritanceException();
	}	


	public LSBEntryType getType(int id) {
		for(LSBIdentifier i:collection){
			if(i.getID()==id){
				return i.getType();
			}
		}
		return null;
	}


	public LSBIdentifier getIdentifier(int id) {
		for(LSBIdentifier i:collection){
			if(i.getID()==id){
				return i;
			}
		}
		return null;
	}


	public LSBIdentifier getIdentifier(String name) {
		for(LSBIdentifier i:collection){
			if(i.getName().equals(name)){
				return i;
			}
		}
		return null;		
	}


	public int getSize() {
		return collection.size();
	}


	public Iterator<LSBIdentifier> getIterator() {
		return collection.iterator();
	}
	
	
	
	
}
