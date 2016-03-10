import java.net.Socket;
public class ServerPackedSocket {
	private Socket s;
	private String name;
	
	public String getName(){
		return name;
	}
	
	public Socket getSocket(){
		return s;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setSocket(Socket s){
		this.s = s;
	}
}
