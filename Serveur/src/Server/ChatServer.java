package Server;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {

	private ServerSocket server;
	private int port;
	private List<ConnectionHandler> listClient;
	private List<Room> listRoom;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ChatServer chat = new ChatServer(Integer.parseInt(args[0]));
		chat.connection(chat);
	}
	
	public ChatServer(int port){
		try{
			this.port = port;
			this.server = new ServerSocket(port);
			this.listClient = new ArrayList<ConnectionHandler>();
		}catch(IOException ioe){
			System.err.println("Couldn't run server on port " + port);
		}
	}
	
	private void connection(ChatServer chat){
		while(true){
			try{
				Socket connection = server.accept();
				System.out.println("connection");
				ConnectionHandler handler = new ConnectionHandler(connection);
				listClient.add(handler);
				new Thread(handler).start();
			}
			catch(IOException ioe){
				System.err.println("Couldn't run server on port " + port);
				return;
			}
		}
	}	
	
	private void distributeMessage(String message, String to){
		
	}
	
	private void addClient(ConnectionHandler Client){
		listClient.add(Client);
	}
	
	private void delClient(ConnectionHandler Client){
		listClient.remove(Client);
	}
	
	private void addRoom(String name, String owner){
		Room r = new Room(name, owner);
		listRoom.add(r);
	}
	
	private void delRoom(Room r){
		listRoom.remove(r);
	}

	
}
