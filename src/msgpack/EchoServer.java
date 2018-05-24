package msgpack;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class EchoServer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel socketChannel) throws Exception {
		socketChannel.pipeline().addLast("msgpack decoder", new MsgpackDecoder());
		socketChannel.pipeline().addLast("msgpack encoder", new MsgpackEncoder());
		socketChannel.pipeline().addLast(new EchoServerHandler());
	}

}
