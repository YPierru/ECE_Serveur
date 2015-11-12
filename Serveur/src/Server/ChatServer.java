package Server;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {

	private ServerSocket server;
	private int port;
	private List<ConnectionHandler> LClient;
	private List<Room> LRoom;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			ChatServer chat = new ChatServer( Integer.parseInt(args[0]));
			chat.Connection(chat);
	}
	
	public ChatServer(int port){
		try{
			this.port = port;
			this. server = new ServerSocket(port);
			this.LClient = new ArrayList<ConnectionHandler>();
		}catch(IOException ioe){
			System.err.println("Couldn't run server on port " + port);
		}
	}
	
	public void Connection(ChatServer chat){
		while(true){
			try{
				Socket connection = server.accept();
				System.out.println("connection");
				ConnectionHandler handler = new ConnectionHandler(connection);
				addClient(handler);
				int index = LClient.indexOf(handler);
				new Thread(LClient.get(index)).start();
			}
			catch(IOException ioe){
				System.err.println("Couldn't run server on port " + port);
				return;
			}
		}
	}	
	
	public void distributeMessage(String message, String to){
		
	}
	
	public void addClient(ConnectionHandler Client){
		LClient.add(Client);
	}
	
	public void delClient(ConnectionHandler Client){
		LClient.remove(Client);
	}
	
	public void addRoom(String name, String owner){
		Room r = new Room(name, owner);
		LRoom.add(r);
	}
	
	public void delRoom(Room r){
		LRoom.remove(r);
	}

	
}
