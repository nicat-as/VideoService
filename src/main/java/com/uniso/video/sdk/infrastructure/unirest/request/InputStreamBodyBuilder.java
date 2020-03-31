package com.uniso.video.sdk.infrastructure.unirest.request;

import kong.unirest.HttpRequest;
import kong.unirest.HttpRequestWithBody;
import kong.unirest.MultipartBody;
import com.uniso.video.sdk.domain.video.UploadProgressListener;

import java.io.InputStream;

public class InputStreamBodyBuilder implements BodyBuilder {
    private final String                 filename;
    private final InputStream            inputStream;
    private final int                    chunkCount;
    private final int                    chunkNum;
    private       UploadProgressListener progressListener;

    public InputStreamBodyBuilder(String filename, InputStream inputStream, int chunkCount, int chunkNum, UploadProgressListener progressListener) {
        this.filename         = filename;
        this.inputStream      = inputStream;
        this.chunkCount       = chunkCount;
        this.chunkNum         = chunkNum;
        this.progressListener = progressListener;
    }

    @Override
    public HttpRequest build(HttpRequestWithBody request) {
        MultipartBody body = request.field("file", inputStream, filename);

        if (progressListener != null) {
            body.uploadMonitor(
                    (field, fileName, bytesWritten, totalBytes) -> progressListener.onProgress(bytesWritten, totalBytes, chunkCount, chunkNum)
            );
        }

        return body;
    }
}
