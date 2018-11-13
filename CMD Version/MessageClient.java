
import java.awt.*;
import java.net.*;
import java.io.*;
import java.awt.event.*;
import java.util.*;

public class MessageClient implements Runnable{
	BufferedReader in; 
	   
    MessageClient(BufferedReader x) throws Exception {
          in = x;   
    }

    public void run() {
       try {
          while(true) {
             String inline = in.readLine();
             System.out.println(inline);
          }
       } catch (Exception e) {System.out.println("Problems "+e);}
    }


    static public void main(String args[]) throws Exception {
        Scanner C = new Scanner(System.in);
        System.out.print("Hostname : ");
        String host = C.nextLine();
        
        Socket s = new Socket(host,7777);
		System.out.println("remote port "+s.getPort());
        System.out.println("Chat Server Located...");
        PrintWriter out = new PrintWriter(s.getOutputStream(),true);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        MessageClient c = new MessageClient(in); 
        Thread t = new Thread(c); 
        t.start();
        
        String nickname = C.nextLine();
        out.println(nickname);
    
       
        while(true) {
           String inline = C.nextLine();
           out.println(inline);
           if (inline.equals("4")) {
		      out.println("bye");
			  s.close();
			  break; 
			} //if
           else if(inline.equals("3")) {
        	    System.out.println("Message: ");
        	    String message = C.nextLine();
        		out.println(message);
        } //else if
           else if(inline.equals("2")) {
        	   System.out.println("Recipients: ");
        	   String recipients = C.nextLine();
        	   out.println(recipients);
        	   System.out.println("Message: ");
	       	   String message = C.nextLine();
	       	   out.println(message);        	  
           } //else if
           else if(inline.equals("1")) {
        	   out.println(inline);
        } //else
   
    } //while loop 
        s.close(); 
        C.close();
    } //main

}
