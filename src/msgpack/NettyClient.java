package msgpack;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
	
	public static void main(String[] arg) {
		NettyClient nettyClient = new NettyClient();
		try {
			nettyClient.createConnet(8080);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
	}
	
	public void  createConnet(int  port) throws InterruptedException {
			
		EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group).channel(NioSocketChannel.class)
        .option(ChannelOption.TCP_NODELAY, true).handler(new EchoClient());
        
        try {
            ChannelFuture f = b.connect("127.0.0.1", port).sync();
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally{
            group.shutdownGracefully();
        }
	}
	
}	
