package netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;

import java.util.Date;

public class TimeServerHandler  extends ChannelHandlerAdapter implements ChannelInboundHandler {
	
	private int counter;
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelActive(ChannelHandlerContext arg0) throws Exception {
		System.out.println("channelActive");
	}

	@Override
	public void channelInactive(ChannelHandlerContext arg0) throws Exception {
		System.out.println("channelInactive");
	}

	@Override
	public void channelRead(ChannelHandlerContext context, Object msg)
			throws Exception {
		System.out.println("channelRead");
		String req = (String) msg;
//		byte[] req = new byte[byteBuff.readableBytes()];
//		byteBuff.readBytes(req);
		String body = req.substring(0,req.length() - System.getProperty("line.separator").length()+2);
		System.out.println("the time server reveive order :" + body + ", the count : " + ++counter);
		String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? 
				new Date().getTime() + "" : "time is error";
		currentTime = currentTime + System.getProperty("line.separator");
		ByteBuf response = Unpooled.copiedBuffer(currentTime.getBytes());
		context.writeAndFlush(response);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext context)
			throws Exception {
		System.out.println("channelReadComplete");
//		context.flush();
	}

	@Override
	public void channelRegistered(ChannelHandlerContext arg0) throws Exception {
		System.out.println("channelRegistered");
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext arg0)
			throws Exception {
		System.out.println("channelUnregistered");
	}

	@Override
	public void channelWritabilityChanged(ChannelHandlerContext arg0)
			throws Exception {
		System.out.println("channelWritabilityChanged");
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext arg0, Object arg1)
			throws Exception {
		System.out.println("userEventTriggered");
	}


	
}
