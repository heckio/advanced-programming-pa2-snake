package Game.Entities.Dynamic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import Game.Entities.Static.*;
import Game.GameStates.State;
import Main.Handler;
import Resources.Animation;
import Resources.Images;

public class Player extends BaseDynamicEntity {
	Item item;
	public float money;
	int speed = 4;
	public int babyClients;
    public boolean goneClients = false;
	private Burger burger;
	private String direction = "right";
	private int interactionCounter = 0;
	public int clientsServed = 0;
	private Animation playerAnim;
	int serving;
	public Player(BufferedImage sprite, int xPos, int yPos, Handler handler) {
		super(sprite, xPos, yPos,82,112, handler);
		createBurger();
		playerAnim = new Animation(120,Images.chef);
	}

	public void createBurger(){
		burger = new Burger(handler.getWidth() - 110, 100, 100, 50);

	}

	public void tick(){
		playerAnim.tick();
		
		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_SHIFT) && speed > 1) {
			speed--;
		}
		else if ((handler.getKeyManager().keyJustPressed(KeyEvent.VK_EQUALS)) && speed < 10) {
			speed++;
		}
		if(xPos + width >= handler.getWidth()){
			direction = "left";

		} else if(xPos <= 0){
			direction = "right";
		}
		if (direction.equals("right")){
			xPos+=speed;
		} else{
			xPos-=speed;
		}
		if (interactionCounter > 15 && handler.getKeyManager().attbut){
			interact();
			interactionCounter = 0;
		} else {
			interactionCounter++;
		}
		if(handler.getKeyManager().fattbut){
			for(BaseCounter counter: handler.getWorld().Counters){
				if (counter instanceof PlateCounter && counter.isInteractable()){
					createBurger();
				}
			}
		}
		if(handler.getKeyManager().fattbut){
			for(BaseCounter counter: handler.getWorld().Counters){
				if (counter instanceof PatienceCounter && counter.isInteractable()){
					Client.patience = 10800;
				}
			}
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_1)) {
			serving = 0;
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_2)) {
			serving = 1;
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_3)) {
			serving = 2;
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_4)) {
			serving = 3;
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_5)) {
			serving = 4;
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {//cheat insta-win
			State.setState(handler.getGame().winState);
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_L)) {//non-cheat insta-loss
			State.setState(handler.getGame().loseState);
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_P)) {//pause
			State.setState(handler.getGame().pauseState);
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_B)) {//makes selected client go
			handler.getWorld().clients.get(serving).patience = 1;
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_R)){
			for(BaseCounter counter: handler.getWorld().Counters) {
				if (counter instanceof PlateCounter && counter.isInteractable()) {
					ringCustomer(serving);
				}
			}
		}
		//winning condition 
		if(money >= 50) {
			State.setState(handler.getGame().winState);
		}
		//losing condition 
		for(Client client: handler.getWorld().clients) {
			if (client.babyClients == 10) {
				State.setState(handler.getGame().loseState);
			}
		}
		 for(int i =0; i < handler.getWorld().clients.size(); i++) {
         	if(handler.getWorld().clients.get(i).isLeaving && handler.getWorld().clients.get(i).patience == 0) {
         		goneClients = true;
         	}
         }
         if(goneClients) 
         	babyClients++;
         goneClients = false;
     
 }
	

	private void ringCustomer(int serving) {

		for(Client client: handler.getWorld().clients){
			boolean matched = ((Burger)handler.getWorld().clients.get(serving).order.food).equals(handler.getCurrentBurger());
			if(matched){
				clientsServed++;
				money+=client.order.value;
				for(int i = 0; i < handler.getWorld().clients.size(); i++) {//a clients patience is r
					handler.getWorld().clients.get(i).patience += handler.getWorld().clients.get(i).patience * (1/4);
				}
				handler.getWorld().clients.get(serving).isLeaving = true;
				handler.getPlayer().createBurger();
				System.out.println("Total money earned: " + String.valueOf(money));
				//gives a tip of .15  % if the customer is served before reaching half the patience
				if(matched && client.patience > client.patience/2) {
					money += 0.15 * client.order.value;
				}
				//gives an extra .15 if the burger is cocked within the range of .48 and .53 patience
				if(handler.getStoveCounter().getTint()*(48/100) <= handler.getStoveCounter().getTint()*(53/100)) {
					money += money*(15/100);
				}
				return;
			}
			
		}
	}

	public void render(Graphics g) {
		if(direction=="right") {
			g.drawImage(playerAnim.getCurrentFrame(), xPos, yPos, width, height, null);
		}
		
		else{
			g.drawImage(playerAnim.getCurrentFrame(), xPos+width, yPos, -width, height, null);

		}
		g.setColor(Color.black);
		burger.render(g);
		g.setColor(Color.black);
		g.setColor(Color.white);
		g.setFont(new Font("ComicSans", Font.BOLD, 24));
		g.drawString("Bling Earned: " + money, handler.getWidth()/2 -200, 30);
		g.drawString("Clients satisfied: " + clientsServed, handler.getWidth()/2 +100, 30);
		g.drawString("Crybabys gone: " + babyClients,handler.getWidth()/2 +100, 60);
	}
	

	public void interact(){
		for(BaseCounter counter: handler.getWorld().Counters){
			if (counter.isInteractable()){
				counter.interact();
			}
		}
	}
	public Burger getBurger(){
		return this.burger;
	}
}
