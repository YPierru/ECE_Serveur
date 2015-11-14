package Server;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {

	private ServerSocket server;
	private int port;
	private ArrayList<ConnectionHandler> listClient;
	private ArrayList<Room> listRoom;
	//private RoomManager roomManager;
	
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
			listRoom = new ArrayList<>();
			//roomManager = new RoomManager();
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
					public void broadcast(String msg, String usernameReceiver, Room roomToSend) {
						distributeMessage(msg,usernameReceiver,roomToSend);
					}
					
					@Override
					public void createRoom(String roomName) {
						listRoom.add(new Room(roomName, this));
						//roomManager.addRoom(newR);
						try {
							for(ConnectionHandler client : listClient){
								client.setListRoom(listRoom);
								client.sendListRoomsName();
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				};
				
				handler.setListRoom(listRoom);

				if(listRoom.size()>0){
					handler.sendListRoomsName();
				}
				
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
	private void distributeMessage(String message,String usernameSender,Room roomToSend){
		String msg;
		
		
		for(ConnectionHandler ch : roomToSend.getListClients()){
			if(!ch.getUsername().equals(usernameSender)){
				System.out.println("envoi depuis le serveur");
				msg="["+roomToSend.getName()+"]"+"<"+usernameSender+">"+message;
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
	
	private void delRoom(Room r){
		//listRoom.remove(r);
	}

	
}
