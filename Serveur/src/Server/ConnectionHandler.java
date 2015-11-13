package Server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public abstract class ConnectionHandler implements Runnable{
	private String username;
	private Socket connection;
	private DataInputStream reader;
	private DataOutputStream writer;
	private ArrayList<Room> listRooms;
	private Room currentRoom;
	
	/**
	 * Constructeur : création des reader et récupération de l'username
	 * @param connection
	 */
	public ConnectionHandler(Socket connection){
		this.connection = connection;
		try{
			reader = new DataInputStream(connection.getInputStream());
			writer = new DataOutputStream(connection.getOutputStream());
			getUsernameAndNotify();
		}
		catch(IOException ioe){
			System.err.println(ioe);
		}
	}
	
	private void getUsernameAndNotify() throws IOException{
		username=reader.readUTF();
		System.out.println(username+" est connecté");
		//broadcast("connecté", username);
	}
	
	/**
	 * Reçoit un message et le diffuse aux autres clients
	 */
	public void run(){
		try{
			String raw = "";
			while(true){
				raw=reader.readUTF();
				System.out.println(raw);
				if(raw.startsWith("[NewRoom]")){
					createRoom(raw.replace("[NewRoom]", ""));
					
				}else if(raw.startsWith("[SetCurrentRoom]")){
					for(Room r : listRooms){
						if(r.getName().equals(raw.replace("[SetCurrentRoom]", ""))){
							currentRoom=r;
							break;
						}
					}
					
				}else{
					broadcast(raw, username, currentRoom);
				}
				//broadcast(raw,username);
				/*
				if(!raw.isEmpty()){
					String[] tRaw = raw.split("/;");
					if(tRaw[0].equals("m")){
						System.out.println(username+" : "+tRaw[2]);
					}
					else if (tRaw[0].equals("cr")){
					}
					else if(tRaw[0].equals("dr")){
						
					}
				}*/
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
			writer.writeUTF(msg);
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
