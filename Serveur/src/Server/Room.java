package Server;

import java.util.ArrayList;
import java.util.List;

public class Room {

	private String name;
	private ConnectionHandler owner;
	private ArrayList<ConnectionHandler> listClients;

	public Room(String name, ConnectionHandler owner){
		this.name  = name;
		this.owner = owner;
		this.listClients = new ArrayList<ConnectionHandler>();
		this.owner.setCurrentRoom(this);
		addUser(owner);
	}
	
	public void addUser(ConnectionHandler user){
		if(!listClients.contains(user)){
			listClients.add(user);
		}
	}
	
	public String getName(){
		return name;
	}
	
	public String getOwnerName(){
		return owner.getUsername();
	}
	
	public ArrayList<ConnectionHandler> getListClients(){
		return listClients;
	}
	
}
