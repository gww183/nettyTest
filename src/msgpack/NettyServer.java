package msgpack;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import netty.ChildChannelHandler;

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
		EventLoopGroup bossGruop = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGruop, workGroup)
        		.channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024).
                childHandler(new EchoServer());

        try {
            ChannelFuture future = bootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            bossGruop.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
	}
	
}
