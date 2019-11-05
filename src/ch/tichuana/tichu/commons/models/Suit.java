package ch.tichuana.tichu.commons.models;

public enum Suit {
	Jade, Pagodas, Stars, Swords;

    /**
     * Implement Suits
     * @author
     * @return
     */
	public String toString() {
	    String suit = "";
	    switch(this){
            case Jade: suit = "jade"; break;
            case Stars: suit = "starts"; break;
            case Swords: suit = "sworts"; break;
            case Pagodas: suit = "padogas"; break;
        }
		return suit;
	}

}