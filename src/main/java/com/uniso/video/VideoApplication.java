package com.uniso.video;

import com.uniso.video.sdk.Client;
import com.uniso.video.sdk.ClientFactory;
import com.uniso.video.service.StorageService;
import com.uniso.video.service.imlementation.StorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import thread.SaverThread;

@SpringBootApplication
public class VideoApplication {

	@Value("${api.video.sandbox}")
	private String sandboxKey;

	@Autowired
	private StorageService storageService;
	@Autowired
	private Client client;


	public static void main(String[] args) {
		SpringApplication.run(VideoApplication.class, args);
	}

	@Bean
	public Client createClient(){
		return new ClientFactory().createSandbox(sandboxKey);
	}

	@Bean(name = "executor")
	public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(4);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(20);
		executor.setThreadNamePrefix("default_task_executor_thread");
		executor.initialize();
		return executor;
	}

	@Bean
	public SaverThread saverThread (){
		return new SaverThread(storageService,client);
	}
}
