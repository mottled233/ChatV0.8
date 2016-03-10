import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class ServerPrinter {
	ServerPackedSocket[] s = null;
	PrintWriter[] pw;

	
	public ServerPrinter(ServerPackedSocket[] s){
		this.s = s;
		pw = new PrintWriter[s.length];
		
		for(int i = 0;i<s.length;i++){
		
			
		}
	}
	
	public void print(String input,int id){
		for(int i = 0;i<pw.length;i++){
			if(pw[i] == null && s[i].getSocket() != null){
				
				try{
					pw[i] = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s[i].getSocket().getOutputStream())));
				}catch(IOException e){
					System.out.println("����idΪ" + i + "���û�λ�Ĵ�ӡͨ��ʱ����һ������\n������Ϣ��");
					e.getStackTrace();
				}
				
			}
		}
		for(int i = 0;i<pw.length;i++){
			if(pw[i] != null){
				pw[i].println(s[id].getName() + "˵��" + input);
				pw[i].flush();
			}
		}
	}
}
