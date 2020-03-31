package com.uniso.video.sdk.domain.video;

public interface UploadProgressListener {

    void onProgress(Long bytesWritten, Long totalBytes, int chunkCount, int chunkNum);

}
