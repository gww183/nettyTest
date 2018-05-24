package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioClientTread implements Runnable {

	private String ip;
	private int port;
	private Selector selector;
	private SocketChannel socketChannel;

	public NioClientTread(String ip, int port) {
		this.ip = ip;
		this.port = port;
		try {
			selector = Selector.open();
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	@Override
	public void run() {
		try {
			doConnect();
			while(true) {
				this.selector.select();
				Set<SelectionKey> selectorKey = this.selector.selectedKeys();
				Iterator<SelectionKey> it = selectorKey.iterator();
				while(it.hasNext()) {
					SelectionKey key = it.next();
					it.remove();
					try {
						handleInput(key);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	/**
	 * 
	 * @Title: handleInput 
	 * @Description: 
	 * @param key
	 * @author gww
	 * 2018年5月17日
	 * @throws IOException 
	 */
	private void handleInput(SelectionKey key) throws IOException {
		if(key.isValid()) {
			SocketChannel sc =(SocketChannel)key.channel();
			if(key.isConnectable()) {
				if(sc.finishConnect()) {
					sc.register(this.selector, SelectionKey.OP_READ);
					doWrite(sc);
				} else {
					System.exit(-1);
				}
			} 
			if(key.isReadable()) {
				ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
				int readNum = sc.read(byteBuffer);
				if(readNum > 0) {
					byteBuffer.flip();
					byte[] bytes = byteBuffer.array();
					String body = new String(bytes,"UTF-8");
					System.out.println(body);
				} else if(readNum < 0) {
					key.cancel();
					sc.close();
				}
			}
		}
	}

	private void doConnect() throws IOException {
		boolean connected = socketChannel.connect(new InetSocketAddress(ip, port));
		if(connected) {
			socketChannel.register(this.selector, SelectionKey.OP_READ);
			doWrite(this.socketChannel);
		} else {
			socketChannel.register(this.selector, SelectionKey.OP_CONNECT);
		}
	}
	
	
	private void doWrite(SocketChannel socketChannel) throws IOException {
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		byteBuffer.put("send test Resoponse".getBytes());
		byteBuffer.flip();
		socketChannel.write(byteBuffer);
	}

}
