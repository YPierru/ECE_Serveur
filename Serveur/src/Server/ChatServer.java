package Server;
import java.io.*;
import java.net.*;

public class ChatServer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//int port = Integer.parseInt(args[0]);
		try{
			ServerSocket server = new ServerSocket(5555);
			System.out.println("alive");
			while(true){
				try{
					Socket connection = server.accept();
					ConnectionHandler handler = new ConnectionHandler(connection);
					new Thread(handler).start(); 
				}
				catch(IOException ioe1){
					
				}
			}
		}
		catch(IOException ioe){
			System.err.println("Couldn't run server on port " + 5555);
			return;
		}
	}
}
