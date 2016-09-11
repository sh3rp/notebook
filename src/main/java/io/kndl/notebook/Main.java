package io.kndl.notebook;

import io.minio.MinioClient;
import io.minio.Result;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import io.minio.messages.Item;

import java.util.List;

/**
 * Created by skend on 9/3/2016.
 */
public class Main {
    public static void main(String[] args) {
        String access = "98X1J4WUBE0TB28CNKZQ";
        String secret = "Z6OrnSUE+BnB61X3zFDR2kBffUYpOptpq3X9kZiA";
        MinioClient client = null;
        try {
            client = new MinioClient("https://kndl.io:9000",access,secret);
        } catch (InvalidPortException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (InvalidEndpointException e) {
            e.printStackTrace();
            System.exit(1);
        }
        try {
            if(!client.bucketExists("notebooks")) {
                client.makeBucket("notebooks");
            }
            Iterable<Result<Item>> notebooks = client.listObjects("notebooks");
            for(Result<Item> item : notebooks) {
                System.out.println(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(2);
        }
    }
}
