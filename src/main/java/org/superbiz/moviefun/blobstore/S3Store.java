package org.superbiz.moviefun.blobstore;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

public class S3Store implements  BlobStore {
    AmazonS3Client amazonS3Client;
    String photoStorageBucket;

    public S3Store(AmazonS3Client amazonS3Client, String photoStorageBucket) {
        this.amazonS3Client = amazonS3Client;
        this.photoStorageBucket = photoStorageBucket;
    }

    @Override
    public void put(Blob blob) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(blob.contentType);
        amazonS3Client.putObject(photoStorageBucket, blob.name, blob.inputStream, objectMetadata);
    }

    @Override
    public Optional<Blob> get(String name) throws IOException {
        S3Object s3Object = null;
        try {
            s3Object = amazonS3Client.getObject(photoStorageBucket, name);
            if (s3Object == null) {
                throw new FileNotFoundException("File not found in S3 Store");
            }

        } catch (Exception e) {

        }
        return Optional.of(new Blob(s3Object.getKey(), s3Object.getObjectContent(), s3Object.getObjectMetadata().getContentType()));
    }

    @Override
    public void deleteAll() {
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(photoStorageBucket, "");


    }


    /*public PutObjectResult putObject(String bucketName,String key,File file)
            throws SdkClientException,
            AmazonServiceException*/
}
