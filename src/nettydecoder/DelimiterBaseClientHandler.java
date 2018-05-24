package nettydecoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class DelimiterBaseClientHandler extends
		ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel socketChannel) throws Exception {
		ByteBuf byteBuf = Unpooled.copiedBuffer("$_".getBytes());
		socketChannel.pipeline().
		addLast(new DelimiterBasedFrameDecoder(1024, byteBuf));
		socketChannel.pipeline().addLast(new StringDecoder());
		socketChannel.pipeline().addLast(new DelimiterBaseClietHandler1());
	}

}
