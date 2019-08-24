/**
 * @(#)FileTransferServer.java
 *
 *
 * @author Darijo Tepic
 * @version 1.00 2019/8/22
 */

import java.io.*; 
import java.net.*;
//import java.io.BufferedOutputStream;
import java.io.File;
//import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*; 
import java.util.concurrent.*;

public class FileTransferServer {
	
	//Velicina buffer x*1024 x kilobyte
	private static int BUFFER_SIZE;
	private static int port;
	private static String path;

    public FileTransferServer(int port, int BUFFER_SIZE, String path) {
    	this.port = port;
    	this.BUFFER_SIZE = BUFFER_SIZE;
    	this.path = path;
    }
    
	//Modifikovani ImputStream koji sluzi za ogranjicavanje protoka podataka sa servera (ogranicen download)
	public static class CustomInputStream extends InputStream {

        private final int MAX_SPEED = BUFFER_SIZE;
        private final long ONE_SECOND = 1000;
        private long downloadedWhithinOneSecond = 0L;
        private long lastTime = System.currentTimeMillis();

        private InputStream inputStream;

        public CustomInputStream(InputStream inputStream) {
            this.inputStream = inputStream;
            lastTime = System.currentTimeMillis();
        }

        @Override
        public int read() throws IOException {
            long currentTime;
            if (downloadedWhithinOneSecond >= MAX_SPEED
                    && (((currentTime = System.currentTimeMillis()) - lastTime) < ONE_SECOND)) {
                try {
                    Thread.sleep(ONE_SECOND - (currentTime - lastTime));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                downloadedWhithinOneSecond = 0;
                lastTime = System.currentTimeMillis();
            }
            int res = inputStream.read();
            if (res >= 0) {
                downloadedWhithinOneSecond++;
            }
            return res;
        }

        @Override
        public int available() throws IOException {
            return inputStream.available();
        }

        @Override
        public void close() throws IOException {
            inputStream.close();
        }
    }
    
    public void start() {
    	
    	//public final static int PORT = 9000;
    	
    	ExecutorService pool = Executors.newFixedThreadPool(2);
    	
    	try(ServerSocket server = new ServerSocket(this.port)){
    		
    		while(true){
    			try{
    				Socket connection = server.accept();
    				Callable<Void> task = new FileTransferTask(connection);
    				pool.submit(task);
    				
    			}catch (IOException ex){
    				
    			}
    		}
    	}catch (IOException ex){
    		System.err.println("Couldn't start server"); 
    	}
    }
    
    private static class FileTransferTask implements Callable<Void> {
    		
    	private final Socket connection;
    	private final int BufferSize = BUFFER_SIZE;
    	private final String PATH = path;
    	
    		
    	FileTransferTask(Socket connection){
    			this.connection = connection;
    	}
    		
    	@Override
    	public Void call() throws IOException {
    		
    		try{
    			byte[] buffer;
    			buffer = new byte[this.BufferSize];
    			System.out.println(this.BufferSize);
    			System.out.println(BUFFER_SIZE);
    			File file = new File(this.PATH);
    			BufferedInputStream in = new BufferedInputStream(new CustomInputStream(new FileInputStream(file)));
    			BufferedOutputStream out = new BufferedOutputStream(connection.getOutputStream());
    			
    			int len = 0;
    			while (((len = in.read(buffer)) > 0)) {
    				out.write(buffer, 0, len);
    			}
    			out.flush();
    		}catch (IOException ex) {
    			System.err.println(ex);
    		}
    		finally{
    			try{
    				connection.close();
    			}catch (IOException ex){
    				//nebitno
    			}
    		}
    		return null;
    	}	
    }
    
}
