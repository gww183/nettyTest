package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class TimeClient {
	
	public static void main(String[] arg) {
		try {
			TimeClient client = new TimeClient();
			client.connect(8080);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void connect(int port) throws InterruptedException  {
		EventLoopGroup group = new NioEventLoopGroup();
		try{
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group);
			bootstrap.channel(NioSocketChannel.class);
			bootstrap.option(ChannelOption.TCP_NODELAY, true);
			bootstrap.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel socketChannel) throws Exception {
					socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
					socketChannel.pipeline().addLast(new StringDecoder());
					socketChannel.pipeline().addLast(new  TimeClientHandler());
				}
			});
			
			ChannelFuture future = bootstrap.connect("127.0.0.1", port).sync();
			
			future.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully();
		}
		
	}
	
}
