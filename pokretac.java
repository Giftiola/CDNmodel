public class pokretac{
	
	private static final int Port = 9000;
	private static final int BufferSize = 64*1024;
	public static void main (String[] args) {
		FileTransferServer server = new FileTransferServer(Port,BufferSize,"C:\\projekat\\serveri\\server1\\Pyramaze - Caramon's Poem.mp3");
		server.start();
   }
}