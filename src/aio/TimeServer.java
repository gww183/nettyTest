package aio;

import java.io.IOException;

public class TimeServer {
	
	public static void main(String[] arg) {
		 int port = 8080;
		 try {
			new Thread(new AsyncTimeServersHandler(port)).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
