package aio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Date;

public class ReadCompletionHandler implements
		CompletionHandler<Integer, ByteBuffer> {

	private AsynchronousSocketChannel  asynchronousChannel;

	public ReadCompletionHandler(AsynchronousSocketChannel asynchronousChannel) {
		this.asynchronousChannel = asynchronousChannel;
	}

	@Override
	public void completed(Integer result, ByteBuffer attachment) {
		attachment.flip();
		byte[] body = new byte[attachment.remaining()];
		attachment.get(body);
		try{
			String req = new String(body,"UTF-8");
			System.out.println("the time server receive order:" + req);
			String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(req) ? 
					new Date(System.currentTimeMillis()).toString() :"BAD ORDER";
			doWrite(currentTime);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void doWrite(String currentTime) {
		byte[] bytes = currentTime.getBytes();
		ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
		buffer.put(bytes);
		buffer.flip();
		asynchronousChannel.write(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {

			@Override
			public void completed(Integer result, ByteBuffer attachment) {
				if(attachment.hasRemaining()) {
					asynchronousChannel.write(attachment, attachment, this);
				}
			}

			@Override
			public void failed(Throwable exc, ByteBuffer attachment) {
				try {
					asynchronousChannel.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void failed(Throwable exc, ByteBuffer attachment) {
		try {
			asynchronousChannel.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
