package msgpack;

import serializable.UserInfo;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoClientHandler extends ChannelInboundHandlerAdapter {
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelActive");
		UserInfo[] userInfoArr = getUserInfoArr();
		for(UserInfo userInfo : userInfoArr) {
			ctx.writeAndFlush(userInfo);
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		System.out.println("Client receive the msgpack message : " + msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	private UserInfo[] getUserInfoArr() {
		UserInfo[] userInfoArr = new UserInfo[1];
		for(int i = 0; i < 1; i++) {
			UserInfo userInfo = new UserInfo();
			userInfo.buildUserId(i);
			userInfo.buildUserName("abcd:" + i);
			userInfoArr[i] = userInfo;
		}
		return userInfoArr;
	}
}
