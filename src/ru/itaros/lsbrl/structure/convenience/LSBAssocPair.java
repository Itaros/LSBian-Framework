package ru.itaros.lsbrl.structure.convenience;

import ru.itaros.lsbrl.structure.LSBEntry;
import ru.itaros.lsbrl.structure.LSBIdentifier;

/*
 * Assoc pair is read-only class provided to give access to 
 * idDict and corresponding entries
 */
public class LSBAssocPair {
	
	private LSBIdentifier identifier;
	private LSBEntry entry;
	
	public final LSBIdentifier getIdentifier() {
		return identifier;
	}
	public final LSBEntry getEntry() {
		return entry;
	}
	
	LSBAssocPair(LSBIdentifier identifier, LSBEntry entry) {
		super();
		this.identifier = identifier;
		this.entry = entry;
	}
	
	

}
