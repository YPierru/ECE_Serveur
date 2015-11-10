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
		}
		catch(IOException ioe){
			System.err.println(ioe.getStackTrace().toString());
		}
	}
	
	public void run(){
		try{
			username = reader.readUTF();
			String raw = "";
			while(true){
				raw = reader.readUTF();
				String[] tRaw = raw.split("/;");
				if(tRaw[0].equals("m")){
					distributeMessage(tRaw[2], tRaw[1]);
				}
				else if (tRaw[0].equals("cr")){
					
				}
				else if(tRaw[0].equals("dr")){
					
				}
			}
		}
		catch(IOException ioe){
			System.err.println(ioe.getStackTrace().toString());
		}
	}
	
	public void distributeMessage(String message, String to){
		try{
			
		}
		catch(IOException ioe)
		{
			System.err.println(ioe.getStackTrace().toString());
		}
	}
}
