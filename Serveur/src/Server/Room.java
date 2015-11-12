package Server;

import java.util.ArrayList;
import java.util.List;

public class Room {

	private String name;
	private String owner;
	private List<ConnectionHandler> LClient;

	public Room(String name, String owner){
		this.name  = name;
		this.owner = owner;
	}
}
