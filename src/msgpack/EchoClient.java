package msgpack;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

public class EchoClient extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline().addLast("frameDecoder",new LengthFieldBasedFrameDecoder(1024, 0, 2,0,2));
        //增加解码器
        ch.pipeline().addLast("msgpack decoder",new MsgpackDecoder());
        //这里设置读取报文的包头长度来避免粘包
        ch.pipeline().addLast("frameEncoder",new LengthFieldPrepender(2));
        //增加编码器
        ch.pipeline().addLast("msgpack encoder",new MsgpackEncoder());
        ch.pipeline().addLast(new EchoClientHandler());
	}

}
