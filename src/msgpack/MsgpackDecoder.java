package msgpack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

import org.msgpack.MessagePack;

public class MsgpackDecoder extends MessageToMessageDecoder<ByteBuf> {

	@Override
	protected void decode(ChannelHandlerContext content, ByteBuf arg1, List<Object> arg2)
			throws Exception {
		final byte[] array;
		final int length = arg1.readableBytes();
		array = new byte[length];
		arg1.getBytes(arg1.readerIndex(), array, 0, length);
		MessagePack messagePack = new MessagePack();
		arg2.add(messagePack.read(array));
	}

}
