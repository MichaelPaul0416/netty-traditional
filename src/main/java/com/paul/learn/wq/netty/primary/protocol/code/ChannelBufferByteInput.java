package com.paul.learn.wq.netty.primary.protocol.code;

import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.ByteInput;

import java.io.IOException;

/**
 * @Author:wangqiang20995
 * @description:
 * @Date:2017/9/17
 */

/**
 * {@link ByteInput} implementation which reads its data from a {@link ByteBuf}
 */
public class ChannelBufferByteInput implements ByteInput {
    private final ByteBuf buffer;

    public ChannelBufferByteInput(ByteBuf buffer) {
        this.buffer = buffer;
    }

    public void close() throws IOException {
    }

    public int available() throws IOException {
        return this.buffer.readableBytes();
    }

    public int read() throws IOException {
        return this.buffer.isReadable()?this.buffer.readByte() & 255:-1;
    }

    public int read(byte[] array) throws IOException {
        return this.read(array, 0, array.length);
    }

    public int read(byte[] dst, int dstIndex, int length) throws IOException {
        int available = this.available();
        if(available == 0) {
            return -1;
        } else {
            length = Math.min(available, length);
            this.buffer.readBytes(dst, dstIndex, length);
            return length;
        }
    }

    public long skip(long bytes) throws IOException {
        int readable = this.buffer.readableBytes();
        if((long)readable < bytes) {
            bytes = (long)readable;
        }

        this.buffer.readerIndex((int)((long)this.buffer.readerIndex() + bytes));
        return bytes;
    }
}
