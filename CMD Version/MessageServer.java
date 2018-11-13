 
import java.net.*;
import java.io.*;
import java.util.*;
 

public class MessageServer extends Thread{
	 static ServerSocket serverSocket;
	 static ArrayList<BufferedReader>  breader = new ArrayList<BufferedReader>();  //holds the printwriters and nicknames of clients
	 static ArrayList<PrintWriter>  pwriter = new ArrayList<PrintWriter>();
	 static ArrayList<String>  nname = new ArrayList<String>();

	   public void run(){
		   int n = 0;
		   try {
			   serverSocket=new ServerSocket(7777); 
	     }catch(IOException e){
	         System.out.println("Colud not listen on port : 7777");
	         System.exit(-1);
	      }		     
		         while(true){
		        	 try {
		        		 System.out.println("waiting for clients");
			        	 Socket cs=serverSocket.accept(); 	//waiting for client
			        	 System.out.println("Accepted client on local port: "+cs.getLocalPort());
			        	 n++;
			        	 
			             PrintWriter out=new PrintWriter(cs.getOutputStream(),true);
			             BufferedReader in = new BufferedReader(new InputStreamReader(cs.getInputStream()));
			             		             		             
			             String outputLine = "Hello! You are client number "+n;
			             out.println(outputLine);
			             out.println("What do you want your nickname to be?");
			             String nickname = in.readLine();
			             nname.add(nickname);
			             pwriter.add(out);		
			             
			             outputLine = nickname + " has now joined the chat.";
			             for(int i=0; i< pwriter.size(); i++){
			  	           PrintWriter o = (PrintWriter)pwriter.get(i);	//recovering the O.Stream
			  	           o.println(outputLine); 					//sending the line
			  	        	}
			             
			             new MessageClientThread(in, pwriter, nname).start();

		         } //try 
		   catch(IOException e) {  }
		   catch(Exception e) {
			   System.out.println("Exception: " + e);
			   }
		     }// while loop
		   }// run


	   
	   public static void main(String args[]) throws IOException {
		  new MessageServer().start();
		   
	   }
	   
}
