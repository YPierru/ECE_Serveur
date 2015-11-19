package serveur;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import serveur.commandes.Commandes;
import serveur.room.Room;

public abstract class ConnectionHandler implements Runnable{
	private String username;
	private ObjectInputStream reader;
	private ObjectOutputStream writer;
	private ArrayList<Room> listRooms;
	private ArrayList<ConnectionHandler> listUsers;
	private Room currentRoom;
	
	/**
	 * Constructeur : création des reader/Writer et récupération de l'username
	 * @param connection
	 */
	public ConnectionHandler(Socket connection){
		try{
			reader = new ObjectInputStream(connection.getInputStream());
			writer = new ObjectOutputStream(connection.getOutputStream());
			getUsernameAndNotify();
		}
		catch(IOException ioe){
			System.err.println(ioe);
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setListUsers(ArrayList<ConnectionHandler> lu){
		listUsers=lu;
	}
	
	public void sendListUserNames() throws IOException{
		ArrayList<String> names= new ArrayList<>();
		
		for(ConnectionHandler u: listUsers){
			names.add(u.getName());
		}

		writer.writeObject(Commandes.SEND_LIST_USERS);
		writer.flush();
		writer.writeObject(names);
		writer.flush();
	}
	
	public void setListRoom(ArrayList<Room> lr){
		listRooms=lr;
	}
	
	public void sendListRoomsName() throws IOException{
		
		ArrayList<String> names= new ArrayList<>();
		
		for(Room r: listRooms){
			names.add(r.getName());
		}
		writer.writeObject(Commandes.SEND_LIST_ROOMS);
		writer.flush();
		writer.writeObject(names);
		writer.flush();
	}
	
	public String getName(){
		return username;
	}
	
	/**
	 * Lis l'username envoyé par le client.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void getUsernameAndNotify() throws IOException, ClassNotFoundException{
		username=reader.readUTF();
		System.out.println(username+" est connecté");
	}
	
	
	/**
	 * Interface d'écoute du client. Parse le message reçu en fonction de son contenu
	 */
	public void run(){
		try{
			String msg = "";
			while(true){
				msg=reader.readUTF();
				
				if(msg.startsWith(Commandes.RECEIVE_NEW_ROOM)){
					String roomName=msg.replace(Commandes.RECEIVE_NEW_ROOM, "");
					boolean existing = false;
					Room originalR=null;
					for(Room r : listRooms){
						if(r.equals(roomName)){
							existing=true;
							originalR=r;
							break;
						}
					}
					if(!existing){
						createRoom(roomName);
					}else{
						currentRoom=originalR;
						currentRoom.addUser(this);
					}
					if(roomName.startsWith(Commandes.LABEL_ROOM_PVP)){
						String tmp=roomName.replace(Commandes.LABEL_ROOM_PVP, "");
						String userPVP = tmp.substring(0, tmp.indexOf("-"));
						
						for(ConnectionHandler user : listUsers){
							if(user.getName().equals(userPVP)){
								user.write(Commandes.SEND_OPEN_PVP+roomName);
								break;
							}
						}
					}
					
				}else if(msg.startsWith(Commandes.RECEIVE_SET_ROOM)){
					//On cherche la nouvelle current room avec son nom, on ajoute l'user à celle-ci
					for(Room r : listRooms){
						if(r.getName().equals(msg.replace(Commandes.RECEIVE_SET_ROOM, ""))){
							currentRoom=r;
							currentRoom.addUser(this);
							break;
						}
					}
				}else if(msg.startsWith(Commandes.RECEIVE_MESSAGE)){
					//On diffuse le message à tous les membres de la currentRoom
					broadcast(msg.replace(Commandes.RECEIVE_MESSAGE, ""), username, currentRoom);
				}
			}
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	public String getUsername(){
		return username;
	}
	
	public void write(String msg){
		try{
			writer.writeObject(msg);
			writer.flush();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void setCurrentRoom(Room room){
		currentRoom=room;
	}
	
	public abstract void broadcast(String msg, String usernameReceiver, Room roomToSend);
	public abstract void createRoom(String roomName);
	
}
