package Server;

public class Room {

	private String name;
	private String owner;
	private String[] user;
	private boolean enable;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public Room(String name, String owner){
		
		this.name  = name;
		this.owner = owner;
		this.user[0] = owner;
		this.enable = true;
	}
	
	public void deleteRoom(String name, String requester){
		if(requester.equals(owner)){
			enable = false;
		}
	}
}
