package netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledDirectByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;

public class TimeClientHandler extends ChannelHandlerAdapter implements ChannelInboundHandler {
	
	private byte[] req;
	private int count;
	
	public TimeClientHandler() {
		req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext channelhandlercontext,
			Throwable throwable) throws Exception {
		System.out.println("exceptionCaught");
	}

	@Override
	public void handlerAdded(ChannelHandlerContext channelhandlercontext)
			throws Exception {

	}

	@Override
	public void handlerRemoved(ChannelHandlerContext channelhandlercontext)
			throws Exception {

	}

	@Override
	public void channelActive(ChannelHandlerContext channelhandlercontext)
			throws Exception {
		ByteBuf byteBuf = null;
		for(int i = 1; i <= 100; i++) {
			byteBuf = Unpooled.buffer(req.length);
			byteBuf.writeBytes(this.req);
			channelhandlercontext.writeAndFlush(byteBuf);
			Thread.sleep(100);
		}
	}

	@Override
	public void channelInactive(ChannelHandlerContext channelhandlercontext)
			throws Exception {
		System.out.println("channelInactive");
	}

	@Override
	public void channelRead(ChannelHandlerContext channelhandlercontext, Object obj) throws Exception {
		System.out.println("channelRead");
		/*ByteBuf byteBuf = (ByteBuf) obj;
		byte[] req = new byte[byteBuf.readableBytes()];
		byteBuf.readBytes(req);*/
//		String body = new String(req, "UTF-8");
		String body = (String)obj;
		System.out.println("NOW IS :" + body + ",count :" + ++count);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext channelhandlercontext)
			throws Exception {
		System.out.println("channelReadComplete");
	}

	@Override
	public void channelRegistered(ChannelHandlerContext channelhandlercontext)
			throws Exception {
		System.out.println("channelRegistered");
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext channelhandlercontext)
			throws Exception {
		System.out.println("channelUnregistered");
	}

	@Override
	public void channelWritabilityChanged(
			ChannelHandlerContext channelhandlercontext) throws Exception {
		System.out.println("channelWritabilityChanged");
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext channelhandlercontext,
			Object obj) throws Exception {
		
	}

}
