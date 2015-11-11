package Server;

import java.util.ArrayList;
import java.util.List;

public class Room {

	private String name;
	private String owner;
	private List<String> LUser;

	public Room(String name, String owner){
		this.name  = name;
		this.owner = owner;
		LUser = new ArrayList<String>();
		LUser.add(owner);
	}
}
