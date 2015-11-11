package Server;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {

	private ServerSocket server;
	private int port;
	private List<DataOutputStream> LClientOut;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			ChatServer chat = new ChatServer( Integer.parseInt(args[0]));
			chat.Connection(chat);
	}
	
	public ChatServer(int port){
		try{
			this.port = port;
			this. server = new ServerSocket(port);
			this.LClientOut = new ArrayList<DataOutputStream>();
		}catch(IOException ioe){
			System.err.println("Couldn't run server on port " + port);
		}
	}
	
	public void Connection(ChatServer chat){
		while(true){
			try{
				Socket connection = server.accept();
				ConnectionHandler handler = new ConnectionHandler(connection, chat);
				new Thread(handler).start(); 
			}
			catch(IOException ioe){
				System.err.println("Couldn't run server on port " + port);
				return;
			}
		}
	}
	
	public int addClient(DataOutputStream out){
		LClientOut.add(out);
		return LClientOut.size()-1;
	}
}
