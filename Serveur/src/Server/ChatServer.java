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
	
	/**
	 * Le main se charge uniquement de d�marrer le serveur
	 * @param args
	 */
	public static void main(String[] args) {
		new ChatServer(Integer.parseInt(args[0]));
	}
	
	/**
	 * Cr�ation de la socket server
	 * @param port
	 */
	public ChatServer(int port){
		try{
			this.port = port;
			this.server = new ServerSocket(port);
			this.listClient = new ArrayList<ConnectionHandler>();
		}catch(IOException ioe){
			System.err.println("Couldn't run server on port " + port);
		}
		
		connection();
	}
	
	/**
	 * Protocole d'�change c�t� client
	 */
	private void connection(){
		while(true){
			try{
				//Nouveau client connect�
				Socket connection = server.accept();
				
				//On instancie un handler en d�finissant la fonction abstraite broadcast
				ConnectionHandler handler = new ConnectionHandler(connection) {
					
					@Override
					public void broadcast(String msg, String usernameReceiver) {
						distributeMessage(msg,usernameReceiver);
					}
				};
				
				//Ajout du client � la liste
				listClient.add(handler);
				
				new Thread(handler).start();
			}
			catch(IOException ioe){
				System.err.println("Couldn't run server on port " + port);
				return;
			}
		}
	}	
	
	/**
	 * Diffuse le message re�u en param�tre � tous les clients connect�s, sauf celui ayant envoy� le message
	 * @param message
	 * @param usernameReceiver
	 */
	private void distributeMessage(String message,String usernameReceiver){
		String msg;
		for(ConnectionHandler ch : listClient){
			if(!ch.getUsername().equals(usernameReceiver)){
				msg=usernameReceiver+" : "+message;
				ch.write(msg);
			}
		}
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
