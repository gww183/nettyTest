package block;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BlockIoClient {
	
	public static void main(String[] arg) {
		BlockIoClient bio = new BlockIoClient();
		try {
			bio.createBioClient("127.0.0.1", 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createBioClient(String ip, int port) throws UnknownHostException, IOException, InterruptedException {
		
		int i = 0;
		while(i < 1000) {
					// 创建socket 连接
			Socket socket;
			try {
				socket = new Socket(ip, port);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
				Thread.sleep(10000);
				System.out.println("+++" + Thread.currentThread().getName() + SimpleDateFormat.getTimeInstance().format(new Date()));
				printWriter.println("send " + i + " test");
				System.out.println("发送完毕");
				String resp = in.readLine();
				System.out.println(resp);
				System.out.println("---" + Thread.currentThread().getName() + SimpleDateFormat.getTimeInstance().format(new Date()));
				printWriter.close();
				in.close();
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			i ++;
		}
		
		
	}
	
	
}
