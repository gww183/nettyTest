package block;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SocketHandleThread implements Runnable {

	private Socket socket;

	public SocketHandleThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			String body = null;
			System.out.println("开始获取内容" + Thread.currentThread().getName());
			while(true) {
				System.out.println(SimpleDateFormat.getTimeInstance().format(new Date()));
				body = in.readLine();
				Thread.sleep(2000);
				if(body == null) {
					break;
				}
				System.out.println(body);
				out.println("服务端已经收到" + Thread.currentThread().getName());
			}
		} catch (Exception e) {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
			if(out != null) {
				out.close();
			}
			
			if(this.socket != null) {
				try {
					this.socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			this.socket = null;
		}

	}
}
