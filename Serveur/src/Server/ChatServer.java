package Server;
import java.io.*;
import java.net.*;

public class ChatServer {

	private ServerSocket server;
	private int port;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			ChatServer chat = new ChatServer( Integer.parseInt(args[0]));
			chat.Connection();
	}
	
	public ChatServer(int port){
		try{
			this.port = port;
			this. server = new ServerSocket(port);
		}catch(IOException ioe){
			System.err.println("Couldn't run server on port " + port);
		}
	}
	
	public void Connection(){
		while(true){
			try{
				Socket connection = server.accept();
				ConnectionHandler handler = new ConnectionHandler(connection);
				new Thread(handler).start(); 
			}
			catch(IOException ioe){
				System.err.println("Couldn't run server on port " + port);
				return;
			}
	}
	}
}
