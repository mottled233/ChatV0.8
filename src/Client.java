import java.net.*;
import java.io.*;
import javax.swing.JPanel;

public class Client {
	Socket s = null;
	Client_Chat_Panel panel = null;
	String input = null;
	PrintWriter pw = null;
	BufferedReader in = null;
	
	public Client(Client_Chat_Panel panel){
		this.panel = panel;
	}
	
	public void launch(){
		//打开端口并尝试建立连接
		
		try {
			s = new Socket("120.27.109.96",7777);
		} catch (Exception e) {
			System.out.println("出现了一个错误，导致无法连接到服务器。请联系QQ631061840");
			e.printStackTrace();
			System.exit(-1);
		}

		
		//创建接受信息线程
		ClientWaiting cw = new ClientWaiting(this);
		Thread t = new Thread(cw);
		t.start();
		
		//创建输出流

		try{
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())));
		}catch(IOException e){
			System.out.println("与服务器建立数据连接 时发生错误\n错误信息：");
			e.getStackTrace();
		}
		
		//创建输入流，用于读取键盘信息
		in = new BufferedReader(new InputStreamReader(System.in));
		
		//输入用户名并发送给服务器
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		panel.updateText("请输入您要使用的昵称");
//		try {
//			input = in.readLine();
//			pw.println(input);
//			pw.flush();
//		} catch (IOException e) {
//			System.out.println("输入不合法！");
//			e.printStackTrace();
//			System.exit(-1);
//		}
		
	}
	public void sendMassage(String string){
		input = string;
		pw.println(input);
		pw.flush();
		
		if(input.equalsIgnoreCase("exit")){
			try{
				in.close();
				s.close();
				pw.close();
			}catch(IOException e){
				System.out.println("发生未知错误\n错误信息：");
				e.getStackTrace();
			}
			System.out.println("你已经退出了聊天室");
		}
	}

}

class ClientWaiting implements Runnable{
	Client c;
	
	public ClientWaiting(Client c){
		this.c = c;
	}
	public void run(){
		BufferedReader br = null;
		try{
			br = new BufferedReader(new InputStreamReader(c.s.getInputStream()));
		}catch(IOException e){
			System.out.println("创建与服务器的信息链接时出错！\n错误信息：");
			e.getStackTrace();
			System.exit(-1);
		}
		String input = null;
		while(true){
			
			try {
				input = br.readLine();
			} catch (IOException e) {
				System.out.println("接受服务器信息时出现错误。");
				e.printStackTrace();
				System.exit(-1);
			}
			if(input != null)
				c.panel.updateText(input);
			else
				continue;
		}
	}
}

