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
		
		//�ȴ��ͻ�������
		try {
			s.setSocket(ss.accept());
		} catch (IOException e1) {
			System.out.println("һ���ͻ�������ʱ��������");
			e1.getStackTrace();
		}
		PrintWriter pw = null;
		//������ͻ���֮�����Ϣͨ����
		try {
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter (s.getSocket().getOutputStream())));
		} catch (IOException e) {
			System.out.println("������Ϣͨ��ʱ��������");
		}
		
		
		System.out.println("��ӭ��Ϣ�ѷ���.");
		pw.println("��Ϣͨ���ѽ�������ӭ���ļ��룡");
		pw.flush();
		
		
		System.out.println("���û�"+s.getSocket().getInetAddress()+"�������Ѿ�������");
		
		//�����û���һ�����룬�����û��ǳƲ�����ServerPackedSocket��
		String input = null;
		BufferedReader br = null;
		try{
			br = new BufferedReader(new InputStreamReader(s.getSocket().getInputStream()));
			input = br.readLine();
			
		} catch (IOException e) {
			System.out.println("��ȡ�û�"+id+"��Ϣ����\n������Ϣ��");
			e.printStackTrace();
		}

		
		s.setName(input);
		System.out.println(input+s.getName());
		pw.println("���ã�" + input +"����ӭ���ļ��룬�����Կ�ʼ�����ˡ�");
		pw.flush();
		
		//������һ�ȴ��߳�
		
			if(id+1 < sw.length){
				Thread t = new Thread(sw[id+1]);
				t.start();
			}
		
		
		//��ʼ�����û�����
		while(true){
			try {
				input = br.readLine();
				System.out.println(id+"�û�˵��"+input);
			} catch(SocketException se){
				System.out.println("�û�"+id+"���˳���");
				break;
			}
			catch (IOException e) {
				System.out.println("���û�"+id+"��������Ϣʧ��\n������Ϣ��");
				e.printStackTrace();
				System.exit(0);
			}
			if(input.equalsIgnoreCase("exit")){
				pw.close();
				try {
					br.close();
				} catch (IOException e) {
					System.out.println("�ر��û�"+id+"ʱ���ִ���\n������Ϣ��");
					e.printStackTrace();
				}
				break;
			}
			sp.print(input,id);
		}
		this.run();
	
	}
	
}
