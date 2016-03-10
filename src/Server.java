import java.net.*;
import java.util.Scanner;

public class Server {
	
	
	public static void main(String[] args) throws Exception {
		ServerSocket ss = null;
		//�򿪶˿�
		try{
			ss = new ServerSocket(7777);
		}catch (Exception e){
			System.out.println("������һ���������޷������˿�");
			System.exit(-1);
		}

		//��ȡ�����Ҵ�С
		Scanner in = new Scanner(System.in);
		System.out.println("�����������Ҵ�С");
		int size = in.nextInt();
		in.close();
		
		//�����ȴ����У��ʹ�ӡ����
		ServerPackedSocket[] sps = new ServerPackedSocket[size];
		for(int i = 0;i<size;i++){
			sps[i] = new ServerPackedSocket();
		}
		ServerWaiting[] sw = new ServerWaiting[size];
		ServerPrinter sp = new ServerPrinter(sps);
		for(int i = 0;i < size;i ++){
			sw[i] = new ServerWaiting(ss,sw,sps[i],i,sp);
		}
		//������һ���ȴ����н��̣���������ʽ����.
		//Thread t = new Thread(sw[0]);
		//t.start();
		System.out.println("������������ϣ����Խ���������");
		sw[0].run();
		
		
		
		
	}

}
