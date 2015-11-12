package Server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public abstract class ConnectionHandler implements Runnable{
	private String username;
	private Socket connection;
	private DataInputStream reader;
	private DataOutputStream writer;
	
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
		broadcast("connecté", username);
	}
	
	/**
	 * Reçoit un message et le diffuse aux autres clients
	 */
	public void run(){
		try{
			String raw = "";
			while(true){
				raw=reader.readUTF();
				System.out.println(username+" : "+raw);
				broadcast(raw,username);
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
	
	public abstract void broadcast(String msg, String usernameReceiver);
	
}
