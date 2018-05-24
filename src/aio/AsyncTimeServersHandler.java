package aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

public class AsyncTimeServersHandler implements Runnable {
	
	private int port;
	CountDownLatch latch;
	AsynchronousServerSocketChannel asynchronouseServerSocketChannel;
	
	public AsyncTimeServersHandler(int port) throws IOException {
		this.port = port;
		asynchronouseServerSocketChannel = AsynchronousServerSocketChannel.open();
		asynchronouseServerSocketChannel.bind(new InetSocketAddress(port));
		System.out.println("The time server is start in port :" + this.port);
	}

	@Override
	public void run() {
		this.latch = new CountDownLatch(1);
		doAccept();
		try {
			this.latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	private void doAccept() {
		asynchronouseServerSocketChannel.accept(this, new AcceptCompletionHandler());
	}

}
