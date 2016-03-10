import java.net.*;
import java.io.*;

public class ServerWaiting implements Runnable{
	ServerSocket ss = null;
	ServerWaiting[] sw = null;
	int id;
	ServerPackedSocket s = null;
	ServerPrinter sp = null;
	
	public ServerWaiting (ServerSocket ss,ServerWaiting[] waitingLine,ServerPackedSocket s,int id,ServerPrinter sp){
		this.ss = ss;
		sw = waitingLine;
		this.id = id;
		this.s = s;
		this.sp = sp;
	}
	
	public void run(){
		
		//等待客户端连接
		try {
			s.setSocket(ss.accept());
		} catch (IOException e1) {
			System.out.println("一个客户端连接时发生错误。");
			e1.getStackTrace();
		}
		PrintWriter pw = null;
		//创建与客户端之间的信息通道。
		try {
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter (s.getSocket().getOutputStream())));
		} catch (IOException e) {
			System.out.println("创建信息通道时发生错误。");
		}
		
		
		System.out.println("欢迎信息已发送.");
		pw.println("信息通道已建立。欢迎您的加入！");
		pw.flush();
		
		
		System.out.println("与用户"+s.getSocket().getInetAddress()+"的连接已经建立。");
		
		//监听用户第一次输入，创建用户昵称并传入ServerPackedSocket中
		String input = null;
		BufferedReader br = null;
		try{
			br = new BufferedReader(new InputStreamReader(s.getSocket().getInputStream()));
			input = br.readLine();
			
		} catch (IOException e) {
			System.out.println("读取用户"+id+"信息错误\n错误信息：");
			e.printStackTrace();
		}

		
		s.setName(input);
		System.out.println(input+s.getName());
		pw.println("您好，" + input +"，欢迎您的加入，您可以开始输入了。");
		pw.flush();
		
		//启动下一等待线程
		
			if(id+1 < sw.length){
				Thread t = new Thread(sw[id+1]);
				t.start();
			}
		
		
		//开始监听用户输入
		while(true){
			try {
				input = br.readLine();
				System.out.println(id+"用户说："+input);
			} catch(SocketException se){
				System.out.println("用户"+id+"已退出！");
				break;
			}
			catch (IOException e) {
				System.out.println("从用户"+id+"处接受信息失败\n错误信息：");
				e.printStackTrace();
				System.exit(0);
			}
			if(input.equalsIgnoreCase("exit")){
				pw.close();
				try {
					br.close();
				} catch (IOException e) {
					System.out.println("关闭用户"+id+"时出现错误\n错误信息：");
					e.printStackTrace();
				}
				break;
			}
			sp.print(input,id);
		}
		this.run();
	
	}
	
}
