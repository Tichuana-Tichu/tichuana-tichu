package ch.tichuana.tichu.commons.models;

public enum Rank {
	dog, majhong, two, three, four, five, six, seven, eight, nine, ten, Jack, Queen, King, Ace, phoenix, dragon;


    /**
     * Give every Card an ordinal
     * @author
     * @return
     */
	public String toString() {
		String str = "";

		int ordinal = this.ordinal();
		if(ordinal == 0){
		    str = "dog";
        } else if(ordinal == 1){
		    str = "majhong";
        } else if(ordinal <= 8){
		    str = Integer.toString(ordinal+2);
        } else if(ordinal == 9){
		    str = "jack";
        }else if(ordinal == 10){
		    str = "queen";
        }else if (ordinal == 11){
		    str = "king";
        }else if (ordinal == 12){
		    str = "ace";
        }else if (ordinal == 13){
		    str = "phoenix";
        }else if(ordinal == 14){
		    str = "dragon";
        }
          return str;
	}
}