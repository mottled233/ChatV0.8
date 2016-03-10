import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;

public class Client_Chat_Panel extends JPanel{
	public static final int PANEL_HEIGHT = 600,PANEL_WIDTH = 800;
	Client client = null;
	JTextField input = null;
	TextArea output = null;
	SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	public static void main(String[] args){
		testP t = new testP();
	}
	
	public Client_Chat_Panel(){
		this.setSize(PANEL_WIDTH,PANEL_HEIGHT);
		this.setLayout(new BorderLayout());
		input = new JTextField("请在这里输入",20);
		output = new TextArea();
		this.add(input,BorderLayout.SOUTH);
		this.add(output, BorderLayout.CENTER);
		input.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				client.sendMassage(input.getText());;
				input.setText("");
				
			}
			
		});
		
	}
	
	public void setClient(Client c){
		client = c;
	}
	
	public void updateText (String s){
		if(output.getText().equals(""))
			output.setText(output.getText() + dateFormat.format(System.currentTimeMillis())+ "\n" + s);
		else
			output.setText(output.getText() + "\n" + dateFormat.format(System.currentTimeMillis()) + "\n" + s);
	}
	
}

class testP extends JFrame{
	Client_Chat_Panel ccp = null;
	Client c = null;
	public testP(){
		
		ccp = new Client_Chat_Panel();
		c = new Client(ccp);
		ccp.setClient(c);
		
		this.add(ccp);
		this.pack();
		this.setVisible(true);
		this.addWindowListener(new WindowAdapter(){

			public void windowClosing(WindowEvent e) {
				c.sendMassage("exit");
				System.exit(0);
			}
			
		});
		c.launch();
		
	}
}

