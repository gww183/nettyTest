package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioServer {

	public static void main(String[] args) {
		NioServer 	nioServer = new NioServer();
		nioServer.createNio();
	}
	
	public void createNio() {
		try {
			// 打开ServerSocketChannel 检测 客户端连接
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			// 绑定端口，并设置为非阻塞模式
			serverSocketChannel.socket().bind(new InetSocketAddress(10001));
			serverSocketChannel.configureBlocking(false);
			// 创建多路复用selector并启动线程
			Selector selector = Selector.open();
			System.out.println("服务器已经启动");
			// 将serverSocket注册到selector,监听ACCEPT事件
			SelectionKey selectKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			
			while(true) {
				int num = selector.select();
				Iterator<SelectionKey> selectkeys = selector.selectedKeys().iterator();
				while(selectkeys.hasNext()) {
					SelectionKey key = selectkeys.next();
					if(key.isAcceptable()) {
						handleAccept(key, selector);
					} else if(key.isReadable()) {
						handleReadable(key, selector);
					}
					selectkeys.remove();
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 
	 * @Title: handleReadable 
	 * @Description: 
	 * @param key
	 * @author gww
	 * 2018年5月16日
	 * @throws IOException 
	 */
	private void handleReadable(SelectionKey key, Selector selector) throws IOException {
		System.out.println("有数据进来了");
		SocketChannel channel = (SocketChannel) key.channel();
		ByteBuffer readBuffer = ByteBuffer.allocate(1024);
		int readBytes = channel.read(readBuffer);
		if(readBytes > 0) {
			readBuffer.flip();
			byte[] bytes = new byte[readBuffer.remaining()];
			readBuffer.get(bytes);
			String body = new String(bytes, "UTF-8");
			System.out.println("the time server receive order :" + body);
			ByteBuffer bufferWrite = ByteBuffer.allocate(1024);
			bufferWrite.put("handle success".getBytes());
			bufferWrite.flip();
			channel.write(bufferWrite);
		} else {
			key.cancel();
			channel.close();
		}
	}

	//处理连接
	private void handleAccept(SelectionKey key, Selector selector) throws IOException {
		ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
		// 调用accept 建立物理链路
		SocketChannel socketChannel = serverSocketChannel.accept();
		// 设置客户端链路为非阻塞模式
		socketChannel.configureBlocking(false);
		// 设置为重新使用的
		socketChannel.socket().setReuseAddress(true);
		System.out.println("新的客户端链接进来");
		socketChannel.register(selector, SelectionKey.OP_READ);
	}

}
