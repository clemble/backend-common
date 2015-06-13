package com.clemble.casino.server.buffer;

import java.nio.ByteBuffer;

public interface ByteBufferStream<T> {

    ByteBuffer write(T value, ByteBuffer writeBuffer);

    T read(ByteBuffer readBuffer);

}
