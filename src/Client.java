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
		//�򿪶˿ڲ����Խ�������
		
		try {
			s = new Socket("120.27.109.96",7777);
		} catch (Exception e) {
			System.out.println("������һ�����󣬵����޷����ӵ�������������ϵQQ631061840");
			e.printStackTrace();
			System.exit(-1);
		}

		
		//����������Ϣ�߳�
		ClientWaiting cw = new ClientWaiting(this);
		Thread t = new Thread(cw);
		t.start();
		
		//���������

		try{
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())));
		}catch(IOException e){
			System.out.println("������������������� ʱ��������\n������Ϣ��");
			e.getStackTrace();
		}
		
		//���������������ڶ�ȡ������Ϣ
		in = new BufferedReader(new InputStreamReader(System.in));
		
		//�����û��������͸�������
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		panel.updateText("��������Ҫʹ�õ��ǳ�");
//		try {
//			input = in.readLine();
//			pw.println(input);
//			pw.flush();
//		} catch (IOException e) {
//			System.out.println("���벻�Ϸ���");
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
				System.out.println("����δ֪����\n������Ϣ��");
				e.getStackTrace();
			}
			System.out.println("���Ѿ��˳���������");
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
			System.out.println("���������������Ϣ����ʱ����\n������Ϣ��");
			e.getStackTrace();
			System.exit(-1);
		}
		String input = null;
		while(true){
			
			try {
				input = br.readLine();
			} catch (IOException e) {
				System.out.println("���ܷ�������Ϣʱ���ִ���");
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

