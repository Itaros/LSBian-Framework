package ru.itaros.lsbrl.structure.convenience;

import java.util.ArrayList;
import java.util.Iterator;

import ru.itaros.lsbrl.structure.LSBEntry;
import ru.itaros.lsbrl.structure.LSBIdDict;
import ru.itaros.lsbrl.structure.LSBIdentifier;
import ru.itaros.lsbrl.structure.LSBNodeEntry;
import ru.itaros.lsbrl.structure.LSBRegion;

/*
 * Container for AssocPairs. Region resolver is included here
 */
public class LSBAssocGroup {

	ArrayList<LSBAssocPair> pairs = new ArrayList<LSBAssocPair>();
	
	private LSBIdDict dict;
	private LSBRegion[] regions;
	
	public LSBAssocGroup(LSBIdDict iddict, LSBRegion...regions){
		this.dict=iddict;
		this.regions=regions;
		search();
	}

	private void search() {
		for(LSBRegion r:regions){
			LSBEntry entry = r.getRoot();
			makePair(entry);
			recursiveSearch(entry);
		}
	}

	private void makePair(LSBEntry entry) {
		int id = (int) entry.getIdentifier();
		LSBIdentifier identifier = dict.getIdentifier(id);
		LSBAssocPair pair = new LSBAssocPair(identifier, entry);
		pairs.add(pair);
	}

	private void recursiveSearch(LSBEntry entry) {
		if(entry instanceof LSBNodeEntry){
			LSBNodeEntry e = (LSBNodeEntry)entry;
			Iterator<LSBEntry> i = e.getChildIterator();
			while(i.hasNext()){
				LSBEntry local=i.next();
				makePair(local);
				recursiveSearch(local);
			}
		}
		
	}

	public String getReport() {
		return "Generated group for "+pairs.size()+" association pairs!";
	}

	
	public LSBEntry getByName(String name){
		LSBIdentifier i = dict.getIdentifier(name);
		for(LSBAssocPair p:pairs){
			if(p.getIdentifier()==i){
				return p.getEntry();
			}
		}
		return null;
	}
	
	
}
