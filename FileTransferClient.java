/**
 * @(#)FileTransferClient.java
 *
 *
 * @author Darijo Tepic
 * @version 1.00 2019/8/23
 */
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.*;
import java.io.*;
import java.util.*;

public class FileTransferClient implements  Runnable {
	
	public static int BUFFER_SIZE;
	private static int port;
	private static String path;
	private static int Timeout;
	private int procenat = 0;
	public boolean konekcija = true;

    public FileTransferClient(int port, int BUFFER_SIZE,String path, int Timeout) {
    	
    	this.BUFFER_SIZE = BUFFER_SIZE;
    	this.port = port;
    	this.path = path;
    	this.Timeout = Timeout;
    	this.procenat = procenat;
    	
    }
    
	public static final int MAX_CAPACITY = 7148493;
	
	public void run() {
		
		Socket socket = new Socket();
		SocketAddress address = new InetSocketAddress("localhost", this.port); //"localhost" 127.0.0.1
		try {
			
			    socket.connect(address);
			    socket.setSoTimeout(this.Timeout);// Timeout. Za koliko milisekundi client soket se zatvara ako ne dobije podatke kada se poveze sa serverom
			
			    byte[] buffer;
                buffer = new byte[this.BUFFER_SIZE];
                BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
                //File file = new File("C:\\projekat\\klijenti\\klijent2\\Pyramaze - Caramon's Poem.mp3"); Drugaciji nacin za citanje file-a
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(this.path));
                
                // Kanonsko citanje i pisanje iz bafera u javi, implementirano uz procenat %
                long current = 0;
                int size = this.BUFFER_SIZE;
                
                int len = 0;
                while((len = in.read(buffer)) > 0){
            	
                    if(MAX_CAPACITY - current >= size){
                    	current += size;
                    	procenat = (current*100)/MAX_CAPACITY;  
                    }
                	        
                    else{
                	    //size = (int)(MAX_CAPACITY - current); 
                        current = MAX_CAPACITY;
                        procenat = (current*100)/MAX_CAPACITY;
                    } 
                    //buffer = new byte[size]; 
                    //bis.read(contents, 0, size); 
                    out.write(buffer,0,len);
                }
            
                System.out.println(" "+(current*100)/MAX_CAPACITY+"%");
                //int len = 0;
                //while ((len = in.read(buffer)) > 0) {
                //     out.write(buffer, 0, len);            Ovako bi citali i pisali da ne treba procenat
                //     System.out.print("#");
                //}
                out.flush();
                in.close();
                out.close();
                System.out.println("\nZavrsen download!");
		
		  }catch (IOException ex) {
			  System.err.println(ex);
			  konekcija = false;
			  System.out.println("Konekcija odbijena!");
		  }finally {
			  if (socket != null){
				  try{
					  System.out.println("Klijent zatvoren!");
					  socket.close();
					
				  }catch (IOException ex) {
					  //ignore
				  }
			  }
		  }
	}
	
	public int Procenat() {
		return this.procenat;
	}
	
	public boolean Konekcija() {
		return this.konekcija;
	}
	
	
}