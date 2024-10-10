package Game.GameStates;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import Display.UI.ClickListlener;
import Display.UI.UIImageButton;
import Display.UI.UIManager;
import Main.Handler;
import Resources.Images;

public class PauseState extends State {

    int clientsServed;
    int babyClients;
    private UIManager uiManager;

    public PauseState(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUimanager(uiManager);
        
        uiManager.addObjects(new UIImageButton(600, 200, 140, 80, Images.home, new ClickListlener() {
            @Override
            public void onClick() {
                handler.getMouseManager().setUimanager(null);
                handler.getGame().reStart();
                State.setState(handler.getGame().menuState); 
            }
        }));
    

        uiManager.addObjects(new UIImageButton(600, 400, 140, 80,  Images.restart, new ClickListlener(){
			@Override
			public void onClick() {
            handler.getMouseManager().setUimanager(null);
            handler.getGame();
            State.setState(handler.getGame().gameState);
			}
        }));
    }

    @Override
    public void tick() {
        handler.getMouseManager().setUimanager(uiManager);
        uiManager.tick();
        }

    @Override
    public void render(Graphics g) {
        g.drawImage(Images.Pause,0,0,800,800,null);
        uiManager.Render(g);
        g.setColor(Color.black);
        g.setFont(new Font("Showcard Gothic", Font.BOLD, 30 ));
        g.drawString("Taking a break ? ", handler.getWidth()/2 -125, 30);
        
   }
}