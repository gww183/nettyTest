package nettydecoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel socketChannel) throws Exception {
		ByteBuf bytebuf = Unpooled.copiedBuffer("$_".getBytes());
		socketChannel.pipeline().addLast(new 
				DelimiterBasedFrameDecoder(1024, bytebuf));
		socketChannel.pipeline().addLast(new StringDecoder());
		socketChannel.pipeline().addLast(new DelimiterBaseHandler());
	}


}
