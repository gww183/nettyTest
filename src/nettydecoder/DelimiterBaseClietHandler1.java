package nettydecoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;

public class DelimiterBaseClietHandler1 extends ChannelHandlerAdapter implements
		ChannelHandler, ChannelInboundHandler {

	private int count;

	private byte[] req;

	@Override
	public void channelRegistered(ChannelHandlerContext channelhandlercontext)
			throws Exception {
		req = "我要发送消息了$_".getBytes();
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext channelhandlercontext)
			throws Exception {

	}

	@Override
	public void channelActive(ChannelHandlerContext channelhandlercontext)
			throws Exception {
		for (int i = 0; i < 100; i++) {
			ByteBuf byteBuf = Unpooled.copiedBuffer(req);
			channelhandlercontext.writeAndFlush(byteBuf);
		}
	}

	@Override
	public void channelInactive(ChannelHandlerContext channelhandlercontext)
			throws Exception {

	}

	@Override
	public void channelRead(ChannelHandlerContext channelhandlercontext,
			Object obj) throws Exception {
		String body = (String) obj;
		System.out.println("客户端收到消息" + body + ",count:" + ++count);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext channelhandlercontext)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void userEventTriggered(ChannelHandlerContext channelhandlercontext,
			Object obj) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void channelWritabilityChanged(
			ChannelHandlerContext channelhandlercontext) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
	}
	
	
}
