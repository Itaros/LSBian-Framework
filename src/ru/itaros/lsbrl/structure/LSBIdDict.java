package ru.itaros.lsbrl.structure;

import java.util.ArrayList;

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


	public LSBEntryType getType(int id) {
		for(LSBIdentifier i:collection){
			if(i.getID()==id){
				return i.getType();
			}
		}
		return null;
	}
	
	
	
	
}
