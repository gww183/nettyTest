package nettydecoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;

public class DelimiterBaseHandler extends ChannelHandlerAdapter implements
		ChannelInboundHandler {

	private int count;
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
	
	@Override
	public void channelActive(ChannelHandlerContext channelhandlercontext)
			throws Exception {

	}

	@Override
	public void channelInactive(ChannelHandlerContext channelhandlercontext)
			throws Exception {

	}

	@Override
	public void channelRead(ChannelHandlerContext channelhandlercontext,
			Object obj) throws Exception {
		System.out.println("channelRead");
		String body = (String) obj;
		System.out.println("服务器收到的信息是： " + body + ",count :" + ++count);
		byte[] response = ("我已收到" +  Thread.currentThread().getName() + "$_").getBytes();
		ByteBuf byteBuf = Unpooled.copiedBuffer(response);
		channelhandlercontext.writeAndFlush(byteBuf);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext channelhandlercontext)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void channelRegistered(ChannelHandlerContext channelhandlercontext)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void channelUnregistered(ChannelHandlerContext channelhandlercontext)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void channelWritabilityChanged(
			ChannelHandlerContext channelhandlercontext) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void userEventTriggered(ChannelHandlerContext channelhandlercontext,
			Object obj) throws Exception {
		// TODO Auto-generated method stub

	}

}
