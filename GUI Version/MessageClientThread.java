
 import java.io.*;
 import java.util.*;

public class MessageClientThread extends Thread{
	ArrayList<PrintWriter> pwriter;
	ArrayList<String> nname;
	PrintWriter out;
	BufferedReader in;
	String nickname;
	


	public MessageClientThread(BufferedReader in, ArrayList<PrintWriter> pwriter, ArrayList<String> nname) {
		this.in=in;
		out=pwriter.get(pwriter.size()-1);
		nickname = nname.get(nname.size()-1);
		this.pwriter = pwriter;
		this.nname = nname;
	}



	public void run() {
	     try {
	      while(true) {
	        String line = in.readLine();
	        if (line.equals("4")) {
	        	int size = pwriter.size();
	        	for(int i=0; i<size; i++) {
	        		if(pwriter.get(i)==out) { //this way if there are two members with the same nickname I won't remove the wrong one
	        			pwriter.remove(i);
	        			nname.remove(i);
	        		} 
	        		else pwriter.get(i).println(nickname + "has left the chat.");
	        	} //for loop
	        	return;
	        } // if 4
	        
	        else if(line.equals("3")) { //send a message to all
	        	String message = nickname + ": " + in.readLine();
	        for(int i=0; i< pwriter.size(); i++){
	           PrintWriter o = (PrintWriter)pwriter.get(i);	//recovering the O.Stream
	           o.println(message); 					//sending the line
	        	} //for loop
	        } //else if
	        
	        else if(line.equals("2")) { // send a message to one or more members
	        	String recipients = in.readLine();
	        	String message = nickname + ": " + in.readLine();
			    String[] strs = recipients.trim().split("\\s+");
			    for(int j=0; j<strs.length; j++) {
			    	 for(int i=0; i< pwriter.size(); i++) {
			    		 if(strs[j].equals(nname.get(i))) {
			  	           PrintWriter o = (PrintWriter)pwriter.get(i);	//recovering the O.Stream
				           o.println(message); 					//sending the line			    			 
			    		 }
			    	 } //for loop
			    } //for loop
	        	
	        } //else if
	        
	        else if(line.equals("1")) { //get the list of nicknames
	        	out.println("The chat members are: ");
	        	for(int i=0; i< nname.size(); i++){
	        		out.println(nname.get(i));
	        	}
	        } //else
	        
	      }  //while loop
	     } catch (Exception e) {System.out.println(e); System.out.println("client ended conversation");  return; }
	   } //run method

}
