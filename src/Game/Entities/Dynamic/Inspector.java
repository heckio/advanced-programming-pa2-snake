package Game.Entities.Dynamic;

import Main.Handler;
import Resources.Images;

public class Inspector extends Client{

	public Inspector(int xPos, int yPos, Handler handler) {
		super(xPos, yPos, handler);
		this.sprite = Images.people[9];
		
		if(patience <= 0) {
			order.value = order.value/2;
			OGpatience -= (6/100)*patience;
		}
		if(patience > 0) {
			patience += (12/100)*patience;
			OGpatience += (10/100)*patience;
		}
	}
	public void tick(){
        patience--;
        if(patience==0){
            isLeaving=true;
}
	}
}