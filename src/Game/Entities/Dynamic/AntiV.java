package Game.Entities.Dynamic;

import Main.Handler;
import Resources.Images;

public class AntiV extends Client{

	public AntiV(int xPos, int yPos, Handler handler) {
		super(xPos, yPos, handler);
		this.sprite = Images.people[10];
		for(int i = 0; i < handler.getWorld().clients.size(); i++) {
			if(this.patience <= this.OGpatience - this.OGpatience*(8/100))  {
				handler.getWorld().clients.get(i).patience -= handler.getWorld().clients.get(i).patience*(4/100);
			}
			if(i % 2 == 0) {
				handler.getWorld().clients.get(i).patience -= handler.getWorld().clients.get(i).patience*(4/100);

			}
		}
	}
	public void tick(){
        patience--;
        if(patience==0){
            isLeaving=true;
}
	}
}
