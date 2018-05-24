package msgpack;

import serializable.UserInfo;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;

public class EchoClientHandler extends ChannelHandlerAdapter implements
		ChannelInboundHandler {

	
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
//		super.exceptionCaught(ctx, cause);
	}

	@Override
	public void channelActive(ChannelHandlerContext channelhandlercontext)
			throws Exception {
		System.out.println("channelActive");
		UserInfo[] infos = getUserInfoArr();
		for(UserInfo userInfo: infos) {
			channelhandlercontext.write(userInfo);
		}
		channelhandlercontext.flush();
	}

	@Override
	public void channelInactive(ChannelHandlerContext channelhandlercontext)
			throws Exception {
		System.out.println("channelInactive");
	}

	@Override
	public void channelRead(ChannelHandlerContext channelhandlercontext,
			Object obj) throws Exception {
		System.out.println("channelRead");
		System.out.println("server receive " + obj);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext channelhandlercontext)
			throws Exception {
		System.out.println("channelReadComplete");
		channelhandlercontext.flush();
	}

	@Override
	public void channelRegistered(ChannelHandlerContext channelhandlercontext)
			throws Exception {
		System.out.println("channelRegistered");
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext channelhandlercontext)
			throws Exception {

	}

	@Override
	public void channelWritabilityChanged(
			ChannelHandlerContext channelhandlercontext) throws Exception {

	}

	@Override
	public void userEventTriggered(ChannelHandlerContext channelhandlercontext,
			Object obj) throws Exception {

	}
	
	
	private UserInfo[] getUserInfoArr() {
		UserInfo[] userInfoArr = new UserInfo[100];
		for(int i = 0; i < 100; i++) {
			UserInfo userInfo = new UserInfo();
			userInfo.buildUserId(i);
			userInfo.buildUserName("abcd:" + i);
			userInfoArr[i] = userInfo;
		}
		return userInfoArr;
	}
}
