package msgpack;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import netty.TimeServerHandler;

public class EchoServer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline().addLast("frameDecoder",new LengthFieldBasedFrameDecoder(1024, 0, 2,0,2));
        ch.pipeline().addLast("msgpack decoder",new MsgpackDecoder());
        ch.pipeline().addLast("frameEncoder",new LengthFieldPrepender(2));
        ch.pipeline().addLast("msgpack encoder",new MsgpackEncoder());
        ch.pipeline().addLast(new EchoServerHandler());
	}

}
