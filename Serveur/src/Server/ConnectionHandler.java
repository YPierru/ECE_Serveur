package Server;
import java.io.*;
import java.net.*;

public class ConnectionHandler implements Runnable{
	private String username;
	private Socket connection;
	private DataInputStream reader;
	private DataOutputStream writer;
	private ChatServer server;
	
	public ConnectionHandler(Socket connection, ChatServer server){
		this.connection = connection;
		try{
			DataInputStream reader = new DataInputStream(connection.getInputStream());
			DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
			username = reader.readUTF();
			writer.writeUTF("Hello "+username);
			writer.flush();
		}
		catch(IOException ioe){
			System.err.println(ioe.getStackTrace().toString());
		}
	}
	
	public void run(){
		try{
			String raw = "";
			server.addClient(this);
			while(true){
				raw = reader.readUTF();
				if(!raw.isEmpty()){
					String[] tRaw = raw.split("/;");
					if(tRaw[0].equals("m")){
						server.distributeMessage(tRaw[2], tRaw[1]);
					}
					else if (tRaw[0].equals("cr")){
					}
					else if(tRaw[0].equals("dr")){
						
					}
				}
			}
		}
		catch(IOException ioe){
			System.err.println(ioe.getStackTrace().toString());
		}
	}
	
}
