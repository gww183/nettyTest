package aio.client;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CountDownLatch;

public class AsyncTimeClientHandler implements
		CompletionHandler<Void, AsyncTimeClientHandler>, Runnable {
	int port;
	private AsynchronousSocketChannel client;
	private CountDownLatch latch;
	
	public AsyncTimeClientHandler(int port) {
		this.port = port;
		try {
			client = AsynchronousSocketChannel.open();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		latch = new CountDownLatch(1);
		client.connect(new InetSocketAddress("127.0.0.1",this.port), this, this);
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void completed(Void result, AsyncTimeClientHandler attachment) {
		byte[] req = new byte[1024];
		System.out.println("请输入：");
		InputStream inputStram = System.in;
		BufferedInputStream isr = new BufferedInputStream(inputStram);
		try {
			isr.read(req);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.out.println("客户端发送给服务端的消息" + new String(req).toString());
		ByteBuffer byteBuffer = ByteBuffer.allocate(req.length);
		byteBuffer.put(req);
		byteBuffer.flip();
		this.client.write(byteBuffer, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {

			@Override
			public void completed(Integer result, ByteBuffer attachment) {
				if(attachment.hasRemaining()) { // 写半包的情况下继续写
					client.write(attachment, attachment, this);
				} else { // 读开始
					ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
					client.read(byteBuffer, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {
						@Override
						public void completed(Integer result, ByteBuffer byteBuffer) {
							byteBuffer.flip();
							byte[] body = new byte[byteBuffer.remaining()];
							byteBuffer.get(body);
							System.out.println("new time is " + new String(body));
						}

						@Override
						public void failed(Throwable exc, ByteBuffer byteBuffer) {
							exc.printStackTrace();
							try {
								client.close();
								latch.countDown();
							} catch (IOException e) {
								e.printStackTrace();
							}
							
						}
					});
				}
			}

			@Override
			public void failed(Throwable exc, ByteBuffer attachment) {
				exc.printStackTrace();
				try {
					client.close();
					latch.countDown();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void failed(Throwable exc, AsyncTimeClientHandler attachment) {
		exc.printStackTrace();
		try {
			client.close();
			latch.countDown();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
