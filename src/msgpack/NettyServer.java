package msgpack;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
	
	public static void main(String[] arg) {
		NettyServer nettyServer = new NettyServer(8080);
		try {
			nettyServer.run();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	private int port;
	
	public NettyServer(int port) {
		this.port = port;
	}
	
	/**
	 *
	 * @Title: run 
	 * @Description: 
	 * @author gww
	 * 2018年5月24日
	 * @throws InterruptedException 
	 */
	public void run() throws InterruptedException {
		NioEventLoopGroup boss = new NioEventLoopGroup();
		NioEventLoopGroup worker = new NioEventLoopGroup();
		
		ServerBootstrap serverBootStrap = new ServerBootstrap();
		serverBootStrap.group(boss, worker);
		serverBootStrap.channel(NioServerSocketChannel.class);
		serverBootStrap.option(ChannelOption.SO_BACKLOG, 1024);
		serverBootStrap.childHandler(new EchoServer());
		
		ChannelFuture future = serverBootStrap.bind(this.port).sync();
		
		future.channel().closeFuture().sync();
		worker.shutdownGracefully();
		boss.shutdownGracefully();
	}
	
}
