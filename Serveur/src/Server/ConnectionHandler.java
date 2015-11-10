package Server;
import java.io.*;
import java.net.*;

public class ConnectionHandler implements Runnable{
	private Socket connection;
	
	public ConnectionHandler(Socket connection){
		this.connection = connection;
	}
	
	public void run(){
		try{
			DataInputStream reader = new DataInputStream(connection.getInputStream());
			DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
			System.out.println(reader.readUTF());
		}
		catch(IOException ioe){
			
		}
	}
}
