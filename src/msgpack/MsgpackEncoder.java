package msgpack;

import org.msgpack.MessagePack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MsgpackEncoder extends MessageToByteEncoder {

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out)
			throws Exception {
		System.out.println("encode");
		 MessagePack msgpack = new MessagePack();
	     out.writeBytes(msgpack.write(msg));
	}

}
