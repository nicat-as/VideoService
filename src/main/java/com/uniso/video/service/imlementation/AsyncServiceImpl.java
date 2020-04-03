package com.uniso.video.service.imlementation;

import com.uniso.video.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import thread.SaverThread;

import java.util.concurrent.Future;

@Service
public class AsyncServiceImpl implements AsyncService {
    @Autowired
    @Qualifier("executor")
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private SaverThread saverThread;

    @Override
    public Future<String> execute(MultipartFile file) {
        saverThread.setFile(file);
        return taskExecutor.submit(saverThread);
    }
}
