package Game.World;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import Game.Entities.Dynamic.AntiV;
import Game.Entities.Dynamic.Client;
import Game.Entities.Dynamic.Inspector;
import Game.Entities.Dynamic.Player;
import Game.Entities.Static.BaseCounter;
import Main.Handler;

public class BaseWorld {

    public BufferedImage Background;

    public BaseCounter Counters[];

    public Handler handler;

    public ArrayList<Client> clients = new ArrayList<>();

    public BaseWorld(BufferedImage Background, BaseCounter Counters[], Handler handler, Player player){
        this.Background = Background;
        this.Counters = Counters;
        this.handler=handler;
        handler.setWorld(this);
        handler.setPlayer(player);
    }

    public Client generateClient(){
        Client client =  new Client(0,96,handler);
        Inspector inspector = new Inspector(0,96,handler);
        AntiV antiV = new AntiV(0,96,handler);
        Random random = new Random();//makes the different types of clients spawn randomly
        int ran = random.nextInt(5);
        if(ran == 2) {
        	this.clients.add(inspector);
        	return inspector;
        } 
        if(ran == 3) {
        	this.clients.add(antiV);
        	return antiV;
        }
        this.clients.add(client);
        return client;
        }
    
    public void tick(){
    	
    }

    public void render(Graphics g){

    }
}
