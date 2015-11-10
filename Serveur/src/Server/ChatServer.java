package Server;
import java.io.*;
import java.net.*;

public class ChatServer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//int port = Integer.parseInt(args[0]);
		try{
			ServerSocket server = new ServerSocket(5555);
			while(true){
				try{
					System.out.println("alive");
					Socket connection = server.accept();
					System.out.println("Client connecté");
					ConnectionHandler handler = new ConnectionHandler(connection);
					new Thread(handler).start(); 
				}
				catch(IOException ioe1){
					
				}
			}
		}
		catch(IOException ioe){
			System.err.println("Couldn't run" + "server on port" + 5555);
			return;
		}
	}
}
