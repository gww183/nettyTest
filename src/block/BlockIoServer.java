package block;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BlockIoServer {
	
	public static void main(String[] arg) {
		BlockIoServer bio = new BlockIoServer();
		try {
			bio.createBlockIoSocket(1000);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createBlockIoSocket(int port) throws IOException {
		
		// 创建serverSocket用于绑定地址和启动监听端口
		ServerSocket serverSocket = new ServerSocket(port);
		
		System.out.println("serverSocket 创建完毕");
		
		// 创建Socket 用于建立连接
		Socket socket = null;
		
		ExecutorService executors = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), 50, 50L, TimeUnit.SECONDS, 
				new ArrayBlockingQueue<Runnable>(1000));
		
		while(true) {
			// 检测连接是否成功，如果成功则取消塞
			socket = serverSocket.accept();
			// 为每个连接分配线程
			executors.execute(new Thread(new SocketHandleThread(socket)));
		}
		
	}
	
}
