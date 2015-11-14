package Server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public abstract class ConnectionHandler implements Runnable{
	private String username;
	private ObjectInputStream reader;
	private ObjectOutputStream writer;
	private ArrayList<Room> listRooms;
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
	
	public void setListRoom(ArrayList<Room> lr){
		listRooms=lr;
	}
	
	public void sendListRoomsName() throws IOException{
		
		ArrayList<String> names= new ArrayList<>();
		
		for(Room r: listRooms){
			names.add(r.getName());
		}
		
		writer.writeObject(names);
		writer.flush();
	}
	
	/**
	 * Lis l'username envoyé par le client.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void getUsernameAndNotify() throws IOException, ClassNotFoundException{
		username=reader.readUTF();
		System.out.println(username+" est connecté");
		//broadcast("connecté", username);
	}
	
	
	/**
	 * Interface d'écoute du client. Parse le message reçu en fonction de son contenu
	 */
	public void run(){
		try{
			String msg = "";
			while(true){
				msg=reader.readUTF();
				
				if(msg.startsWith("[NewRoom]")){
					createRoom(msg.replace("[NewRoom]", ""));
					
				}else if(msg.startsWith("[SetCurrentRoom]")){
					//On cherche la nouvelle current room avec son nom, on ajoute l'user à celle-ci
					for(Room r : listRooms){
						if(r.getName().equals(msg.replace("[SetCurrentRoom]", ""))){
							currentRoom=r;
							currentRoom.addUser(this);
							break;
						}
					}
				}else{
					//On diffuse le message à tous les membres de la currentRoom
					broadcast(msg, username, currentRoom);
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
