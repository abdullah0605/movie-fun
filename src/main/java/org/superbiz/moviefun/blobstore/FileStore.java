package org.superbiz.moviefun.blobstore;


import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.util.IOUtils;
import org.apache.tika.Tika;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static java.lang.ClassLoader.getSystemResource;
import static java.lang.String.format;
import static java.nio.file.Files.readAllBytes;


public class FileStore implements BlobStore {


    private final Tika tika=new Tika();

    @Override
    public void put(Blob blob) throws IOException {
        saveUploadToFile(blob);
    }

    @Override
    public Optional<Blob> get(String name) throws IOException {

       File file=new File(name);
       if(!file.exists())
       {
           return Optional.empty();
       }
       return Optional.of(new Blob(name,new FileInputStream(file),tika.detect(file)));
    }

    @Override
    public void deleteAll() {
        // ...
    }



    private void saveUploadToFile(Blob blob) throws IOException {
        File targetFile = new File(blob.name);
        targetFile.delete();
        targetFile.getParentFile().mkdirs();
        targetFile.createNewFile();
        try (FileOutputStream out = new FileOutputStream(targetFile)) {
            IOUtils.copy(blob.inputStream, out);
        }
    }

}