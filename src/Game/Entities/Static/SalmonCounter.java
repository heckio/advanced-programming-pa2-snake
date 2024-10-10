package Game.Entities.Static;
import Main.Handler;
import Resources.Images;

public class SalmonCounter extends BaseCounter {
    public SalmonCounter(int xPos, int yPos, Handler handler) {
        super(Images.kitchenCounter[3], BaseCounter.DEFAULTCOUNTERWIDTH*4, 600,96,102,handler);
        item = Item.salmon;
    }
}
