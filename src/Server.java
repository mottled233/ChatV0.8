import java.net.*;
import java.util.Scanner;

public class Server {
	
	
	public static void main(String[] args) throws Exception {
		ServerSocket ss = null;
		//打开端口
		try{
			ss = new ServerSocket(7777);
		}catch (Exception e){
			System.out.println("出现了一个错误导致无法监听端口");
			System.exit(-1);
		}

		//获取聊天室大小
		Scanner in = new Scanner(System.in);
		System.out.println("请输入聊天室大小");
		int size = in.nextInt();
		in.close();
		
		//创建等待序列，和打印程序
		ServerPackedSocket[] sps = new ServerPackedSocket[size];
		for(int i = 0;i<size;i++){
			sps[i] = new ServerPackedSocket();
		}
		ServerWaiting[] sw = new ServerWaiting[size];
		ServerPrinter sp = new ServerPrinter(sps);
		for(int i = 0;i < size;i ++){
			sw[i] = new ServerWaiting(ss,sw,sps[i],i,sp);
		}
		//启动第一个等待序列进程，服务器正式运作.
		//Thread t = new Thread(sw[0]);
		//t.start();
		System.out.println("服务器创建完毕，可以进行连接了");
		sw[0].run();
		
		
		
		
	}

}
