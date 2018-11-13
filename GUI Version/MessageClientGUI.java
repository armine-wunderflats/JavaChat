
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;

import javax.swing.JOptionPane;

public class MessageClientGUI implements ActionListener, Runnable{

	Frame f = new Frame("Fancy Chat Tool");
    static String host;
    TextField in = new TextField(50);
    TextArea out = new TextArea(23,15);
    Button all = new Button("Send to All");
    Button some = new Button("Send to Some");
    Button end = new Button("End");
    Button names = new Button("Members");
    Panel p = new Panel();
    String mynumber; 
    String myname;
	Socket s;
	PrintWriter o;
	BufferedReader n;
    Thread t = new Thread(this);
	
    java.awt.List connected = new java.awt.List(10);
    
    MessageClientGUI(String hostname, String nickname) throws Exception{
    	
    	String host = hostname;
        s = new Socket(host,7777);
	    System.out.println("Chat Server Located...");
	    
        o = new PrintWriter(s.getOutputStream(),true);
        n = new BufferedReader( new InputStreamReader(s.getInputStream()));
        
        o.println(nickname);
        
	    out.setEditable(false);
	    f.setBackground(new Color(100,100,100));
        FlowLayout fl = new FlowLayout();
	    fl.setVgap(20);
        p.setLayout(fl);
        p.add(in); p.add(all); p.add(some); p.add(names); p.add(end); 
        BorderLayout gl = new BorderLayout();
	    f.setLayout(gl);
	    f.add(out,"Center"); f.add(p,"South");
        all.addActionListener(this); //the buttons will react to events through actionlistener
	    end.addActionListener(this);
	    some.addActionListener(this);
	    names.addActionListener(this);
	    in.addActionListener(this);
	    f.setVisible(true); f.pack();
         t.start();
         
    } //constructor
    
    public void run() {
    	try {
	     while(true) {
		       String inline = n.readLine();
			 out.append("\n"+inline); //append the message to the text area
	           }
	           } catch (Exception e) {System.out.println("Problems "+e);}
		  }

	

     public void actionPerformed(ActionEvent e) { //what to do when buttons are pressed
    	 try {
    			if (e.getSource() == end) {
    		       o.println("4");
    			   System.exit(0);
    		     }
    			else if(e.getSource() == all) {
    				String message = in.getText();
    				in.setText(" ");
    				o.println("3");
    				o.println(message);
    			}
    			else if(e.getSource() == some) {
    				o.println("2");
    				String message = in.getText();
    				in.setText(" ");
    				String recipients = JOptionPane.showInputDialog("Recipients: ");
    				o.println(recipients);
    				o.println(message);
    			}
    			else if(e.getSource() == names) 		o.println("1");
    	 }
    	 catch (Exception ex) { System.out.println("Problem: "+ex); } 
    			
 
 }
    
    
    static public void main(String args[]) throws Exception {
		    
	    host = JOptionPane.showInputDialog("Hostname: ");
	    String nickname = JOptionPane.showInputDialog("Nickname: ");
		
	    new MessageClientGUI(host, nickname);
	    
    } //main    
}
