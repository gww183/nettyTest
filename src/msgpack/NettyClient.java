package msgpack;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
	
	public static void main(String[] arg) {
		NettyClient nettyClient = new NettyClient();
		try {
			nettyClient.createConnet(8080);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void  createConnet(int  port) throws InterruptedException {
			
		NioEventLoopGroup group = new NioEventLoopGroup();
		
		Bootstrap boot = new Bootstrap();
		boot.group(group);
		boot.channel(NioSocketChannel.class);
		boot.option(ChannelOption.TCP_NODELAY, true);
		boot.handler(new EchoClient());
		
		ChannelFuture future = boot.connect("127.0.0.1", port).sync();
		
		future.channel().closeFuture().sync();
		group.shutdownGracefully();
		
	}
	
}	
