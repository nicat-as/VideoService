package thread;

import com.uniso.video.sdk.Client;
import com.uniso.video.sdk.domain.exception.ResponseException;
import com.uniso.video.sdk.domain.video.Video;
import com.uniso.video.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.concurrent.Callable;


@Component
@Scope("prototype")
public class SaverThread implements Callable<String> {

    private final StorageService storageService;

    private final Client client;

    private MultipartFile file;

    @Autowired
    public SaverThread(StorageService storageService, Client client) {
        this.storageService = storageService;
        this.client = client;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }


    @Override
    public String call() throws Exception {
        String filePath;
        Video video = null;
        try {
            filePath = storageService.store(this.file);
            video = client.videos.upload(new File(filePath));
            storageService.deleteFile(filePath);
        } catch (ResponseException e) {
            e.printStackTrace();
        } finally {
            return video.videoId;
        }

    }
}

