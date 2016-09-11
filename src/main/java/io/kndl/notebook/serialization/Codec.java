package io.kndl.notebook.serialization;

/**
 * Created by skend on 9/3/2016.
 */
public interface Codec<T> {
    public byte[] serialize(T obj) throws CodecException;
    public T deserialize(byte[] bytes) throws CodecException;
}
