package msgpack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

import org.msgpack.MessagePack;

public class MsgpackDecoder extends MessageToMessageDecoder<ByteBuf> {

	@Override
	protected void decode(ChannelHandlerContext context, ByteBuf msg,
			List<Object> out) throws Exception {
		final int length = msg.readableBytes();
        byte[] b = new byte[length];
        msg.getBytes(msg.readerIndex(), b,0,length);
        MessagePack msgpack = new MessagePack();
        out.add(msgpack.read(b));
	}

}
