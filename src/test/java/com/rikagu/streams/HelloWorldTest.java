package com.rikagu.streams;

import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.messages.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HelloWorldTest {

    @Autowired
    private MinioClient minioClient;

    @Test
    public void test() {
        String bucketName = "streams";
        Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder()
                .bucket(bucketName)
                .build());

        for (Result<Item> result : results) {
            try {
                System.out.println(result.get().objectName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
