package nettydecoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class DelimiterBaseClient {
	
	public static void main(String[] arg) {
		DelimiterBaseClient client = 
				new DelimiterBaseClient();
		client.createDelimiterBase();
	}
		
	public void createDelimiterBase() {
		NioEventLoopGroup nioEventLoopGroup = 
				new NioEventLoopGroup();
		try{
			Bootstrap bootStrap = new Bootstrap();
			bootStrap.group(nioEventLoopGroup);
			bootStrap.option(ChannelOption.TCP_NODELAY, true);
			bootStrap.handler(new DelimiterBaseClientHandler());
			bootStrap.channel(NioSocketChannel.class);
			
			ChannelFuture future = bootStrap.connect("127.0.0.1", 8080).sync();
			future.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			nioEventLoopGroup.shutdownGracefully();
		}
	}
	
}
