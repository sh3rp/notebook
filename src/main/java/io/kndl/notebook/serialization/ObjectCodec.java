package io.kndl.notebook.serialization;

import java.io.*;

/**
 * Created by skend on 9/4/2016.
 */
public class ObjectCodec<T> implements Codec<T> {
    public byte[] serialize(T obj) throws CodecException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new CodecException("serialize: " + e.getMessage());
        }
    }

    public T deserialize(byte[] bytes) throws CodecException {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (T)ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            throw new CodecException("deserialize: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new CodecException("deserialize: " + e.getMessage());
        }
    }
}
