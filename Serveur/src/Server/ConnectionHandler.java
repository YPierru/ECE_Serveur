package Server;
import java.io.*;
import java.net.*;

public class ConnectionHandler implements Runnable{
	private String username;
	private Socket connection;
	private DataInputStream reader;
	private DataOutputStream writer;
	
	public ConnectionHandler(Socket connection){
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
			while(true){
				raw = reader.readUTF();
				if(!raw.isEmpty()){
					String[] tRaw = raw.split("/;");
					if(tRaw[0].equals("m")){
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
